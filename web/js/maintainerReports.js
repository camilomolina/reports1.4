$(document).ready(function () {
    MaintainerReports.init();
});

var ERROR_MESSAGE = ["Debe ingresar los siguientes datos"];


var MaintainerReports = {
    init: function () {
        // $("#body").show();
        $("#tabs").tabs();
        $("input[type=button]").button().click(function (event) {
            event.preventDefault();
        });
    },
    validateParameter: function () {
        var forward = true;
        ERROR_MESSAGE = [];

        if ($("#parameterName").val() == "") {
            ERROR_MESSAGE.push("Debe ingresar nombre del parametro");
            forward = false;
        }
        if ($("#parameterTypeId").val() == -1) {
            ERROR_MESSAGE.push("Debe ingresar tipo del parametro");
            forward = false;
        }

        if (MaintainerReports.checkParameterType(false)) {
            if ($("#dateFormatId").val() == -1) {
                ERROR_MESSAGE.push("Debe ingresar formato del parametro");
                forward = false;
            }
        }

        return forward;
    },
    validateReport: function () {
        var forward = true;
        ERROR_MESSAGE = [];

        if ($("#name").val() == "") {
            ERROR_MESSAGE.push("Debe ingresar nombre del reporte");
            forward = false;
        }

        if ($("#conexionId").val() == -1) {
            ERROR_MESSAGE.push("Debe ingresar una conexi&oacute;");
            forward = false;
        }

        if ($("#areaId").val() == -1) {
            ERROR_MESSAGE.push("Debe ingresar el &aacute;rea");
            forward = false;
        }

        if ($("#sqlDescription").val() == "") {
            ERROR_MESSAGE.push("Debe ingresar descripci&oacute;n");
            forward = false;
        }

        if ($("#sqlText").val() == "") {
            ERROR_MESSAGE.push("Debe ingresar el script");
            forward = false;
        }

        return forward;
    },
    clean: function () {
        $("#name").val("");
        $("#conexionId").val(-1);
        $("#areaId").val(-1);
        $("#sqlDescription").val("");
        $("#sqlText").val("");
        $("#parameterName").val("");
        $("#parameterTypeId").val(-1);
        $("#parameterRequired").prop("checked", false);
        $("#dateFormatId").val(-1);

        $.ajax({
            url: "/reports14/maintainerReports.do"
            , method: "POST"
            , data: "method=clean"
            , dataType: "html"
            , success: function (html) {
                MaintainerReports.cleanTableParameter();
            }
            , error: function () {
                alert("No he podido limpiar todo");
            }
        });
    },
    cleanParameter: function () {
        $("#parameterName").val("");
        $("#parameterTypeId").val(-1);
        $("#dateFormatId").val(-1);
        $("#parameterRequired").prop("checked", false);
    },
    cleanTableParameter: function () {
        $.ajax({
            url: "/reports14/maintainerReports.do"
            , method: "POST"
            , data: "method=cleanTableParameter"
            , dataType: "html"
            , success: function (html) {
                $("#tableParameters").html(html);
            }
            , error: function () {
                alert("No pude actualizar la tabla de parametros");
            }
        });
    },
    addParameter: function () {
        if (MaintainerReports.validateParameter()) {
            var parameters = {
                "method": "addParameter"
                //, "parameterId":$("#id").val()
                , "parameterName": $("#parameterName").val()
                , "parameterTypeId": $("#parameterTypeId").val()
                , "dateFormatId": $("#dateFormatId").val() == -1 ? null : $("#dateFormatId").val()
                , "parameterRequired": $("#parameterRequired").prop("checked")
            };

            $.ajax({
                url: "/reports14/maintainerReports.do"
                , method: "POST"
                , data: parameters
                , dataType: "html"
                , success: function (html) {
                    $("#tableParameters").html(html);
                    MaintainerReports.cleanParameter();
                }
            });
        } else {
            Generic.errorDialog(ERROR_MESSAGE);
        }

    },
    getParameter: function (id) {
        var parameter = {
            "method": "getParameter"
            , "parameterId": id
        };

        $(function () {
            $.ajax({
                url: "/reports14/maintainerReports.do"
                , data: parameter
                , dataType: "json"
                , success: function (json) {
                    //$("#parameterId").val(json.id);
                    $("#parameterName").val(json.name);
                    $("#parameterTypeId").val(json.parameterTypeEnum.id);
                    $("#dateFormatId").val(json.data2);
                    if (json.required == true) {
                        $("#parameterRequired").prop('checked', true);
                    } else {
                        $("#parameterRequired").prop('checked', false);
                    }
                    MaintainerReports.checkParameterType(false);
                }
            });
        });
    },
    checkParameterType: function (cleanVal) {
        if ($("#parameterTypeId").val() == 2) {
            $("#dateFormatId").prop('disabled', false);
            return true;
        } else {
            $("#dateFormatId").prop('disabled', true);

            if (cleanVal) {
                $("#dateFormatId").val(-1);
            }
            return false;
        }
    },
    deleteParameter: function (id) {
        var parameter = {
            "method": "deleteParameter"
            , "parameterId": id
        };

        $(function () {
            //Se declara el texto
            var div = '<div id="dialog-confirm" title="Confirmar">' +
                '<p>' +
                '<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 10px 0;"></span>' +
                '¿Desea eliminar el parametro?' +
                '</p>' +
                '</div>';

            $(div).dialog({
                resizable: false,
                height: 230,
                modal: true,
                buttons: {
                    "Eliminar": function () {
                        $(this).dialog("close");
                        $.ajax({
                            url: "/reports14/maintainerReports.do"
                            , data: parameter
                            , dataType: "html"
                            , success: function (html) {
                                //$("#tableParameters").html(html)
                            }
                        });
                    },
                    Cancelar: function () {
                        $(this).dialog("close");
                    }
                }
            });
        });

    },
    parameterList: function () {
        $.ajax({
            url: "/reports14/maintainerReports.do"
            , method: "POST"
            , data: "method=parameterList"
            , dataType: "html"
            , success: function (html) {
                $("#tableParameters").html(html);
            }
        });
    },
    save: function () {
        if (MaintainerReports.validateReport()) {
            $("#method").val("save");
            $("#maintainerReportsFormId").submit();
            /*
            var report = {
                method: "save"
                , "name": $("#name").val()
                , "conexionId": $("#conexionId").val()
                , "areaId": $("#areaId").val()
                , "sqlDescription": $("#sqlDescription").val()
                , "sqlText": $("#sqlText").val()
            };

            $.ajax({
                url: "/reports14/maintainerReports.do"
                , contentType: "application/x-www-form-urlencoded; charset=UTF-8"
                , method: "POST"
                , data: report
                , dataType: "html"
                , success: function (html) {
                    MaintainerReports.refreshReport();
                    MaintainerReports.clean();
                    MaintainerReports.cleanTableParameter();
                }
            });
            */
        } else {
            Generic.errorDialog(ERROR_MESSAGE);
        }
    },
    delete: function (id) {
        var parameter = {
            "method": "delete"
            , "reportId": id
        };

        $(function () {
            //Se declara el texto
            var div = '<div id="dialog-confirm" title="Confirmar">' +
                '<p>' +
                '<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 10px 0;"></span>' +
                '¿Desea eliminar el Reporte?' +
                '</p>' +
                '</div>';

            $(div).dialog({
                resizable: false,
                height: 230,
                modal: true,
                buttons: {
                    "Eliminar": function () {
                        $(this).dialog("close");
                        $.ajax({
                            url: "/reports14/maintainerReports.do"
                            , data: parameter
                            , dataType: "html"
                            , success: function () {
                                MaintainerReports.refreshReport();
                            }
                            , error: function () {
                                alert("No se pudo eliminar el reporte");
                            }
                        });
                    },
                    Cancelar: function () {
                        $(this).dialog("close");
                    }
                }
            });
        });
    },
    refreshReport: function () {
        $.ajax({
            url: "/reports14/maintainerReports.do"
            , method: "POST"
            , data: "method=refreshReport"
            , dataType: "html"
            , success: function (html) {
                $("#tableReports").html(html);
                $("#name").val("");
                $("#conexionId").val(-1);
                $("#areaId").val(-1);
                $("#sqlDescription").val("");
                $("#sqlText").val("");
            }
            , error: function () {
                alert("No pude refrescar los reportes");
            }
        });
    },
    get: function (id) {
        var parameters = {
            "method": "get"
            , "reportId": id
        };

        $(function () {
            $.ajax({
                url: "/reports14/maintainerReports.do"
                , data: parameters
                , dataType: "json"
                , success: function (json) {
                    $("#name").val(json.name);
                    $("#conexionId").val(json.conexionId);
                    $("#areaId").val(json.areaId);
                    $("#sqlDescription").val(json.description);
                    $("#sqlText").val(json.sql);

                    MaintainerReports.parameterList();

                    $("#tabs").tabs("select", "maintainer");
                }
                , error: function () {
                    alert("No pude editar el reporte");
                }
            });
        });
    },
    link: function(url) {
        var a = document.createElement('a');
        a.target="_blank";
        a.href=url;
        a.click();
    }
};


