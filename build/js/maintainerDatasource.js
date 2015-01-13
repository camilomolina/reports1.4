$(document).ready(function () {
    MaintainerDatasource.init();
});

var ERROR_MESSAGE = [];
var MaintainerDatasource = {
    init: function () {
        $("#tabs").tabs();
        $("input[type=button]").button().click(function (event) {
            event.preventDefault();
        });
    },
    validate: function() {
        var forward = true;
        ERROR_MESSAGE = [];

        if ($("#name").val().trim() == "") {
            ERROR_MESSAGE.push("Debe ingresar un nombre");
            forward = false;
        }
        if ($("#controllerId").val() == -1) {
            ERROR_MESSAGE.push("Debe seleccionar un controlador");
            forward = false;
        }
        if ($("#url").val().trim() == "") {
            ERROR_MESSAGE.push("Debe inidicar una url de conexion");
            forward = false;
        }
        if ($("#user").val().trim() == "") {
            ERROR_MESSAGE.push("Debe ingresar un usuario");
            forward = false;
        }

        return forward;
    },
    save: function () {
        if (MaintainerDatasource.validate()) {
            var parameters = {
                "method": "save"
                , "id": $("#id").val()
                , "name": $("#name").val()
                , "user": $("#user").val()
                , "pass": $("#pass").val()
                , "url": $("#url").val()
                , "controllerId": $("#controllerId").val()
            };

            $.ajax({
                url: "/reports14/maintainerDatasource.do",
                method: "POST",
                data: parameters,
                dataType: "json",
                success: function (json) {
                    //Generic.dialogOk("Se ha guardado correctamente");
                    MaintainerDatasource.refresh();
                    MaintainerDatasource.clean();
                }
            });
        } else {
            Generic.errorDialog(ERROR_MESSAGE);
        }
    },
    refresh: function() {
        $.ajax({
            url: "/reports14/maintainerDatasource.do",
            method: "POST",
            data: "method=refresh",
            dataType: "html",
            success: function (html) {
                $("#listTable").html(html);
            }
        });
    },
    clean: function() {
        $("#maintainerDatasourceId").val("");
        $("#id").val("");
        $("#name").val("");
        $("#controllerId").val(-1);
        $("#url").val("");
        $("#user").val("");
        $("#pass").val("");
    },
    get: function(id) {
        var parameters = {
            "method": "get"
            , "maintainerDatasourceId": id
        };

        $.ajax({
            url: "/reports14/maintainerDatasource.do",
            method: "POST",
            data: parameters,
            dataType: "json",
            success: function (json) {
                $("#id").val(json.id);
                $("#name").val(json.name);
                $("#controllerId").val(json.controllerDTO.id);
                $("#url").val(json.url);
                $("#user").val(json.user);

                $("#tabs").tabs("select","maintainer");
            }
        });
    },
    deleteFn: function(id, name) {
        var parameters = {
            "method": "delete"
            , "maintainerDatasourceId": id
        };

        var div = "<div>Esta seguro de eliminar la conexion " + name + "<br></div>";
        $(div).dialog({
            title: "Mensaje",
            modal: true,
            draggable: false,
            buttons: [
                {
                    text: "Cerrar",
                    click: function () {
                        $(this).dialog("close");
                    }
                },
                {
                    text: "Eliminar",
                    click: function () {
                        $.ajax({
                            url: "/reports14/maintainerDatasource.do",
                            method: "POST",
                            data: parameters,
                            dataType: "json",
                            success: function (json) {
                                MaintainerDatasource.refresh();
                            }
                        });

                        $(this).dialog("close");
                    }
                }
            ]
        });
    },
    check: function() {
        var parameters = {
            "method": "check"
            , "name": $("#name").val()
            , "user": $("#user").val()
            , "pass": $("#pass").val()
            , "url": $("#url").val()
            , "controllerId": $("#controllerId").val()
        };

        $.ajax({
            url: "/reports14/maintainerDatasource.do",
            method: "POST",
            data: parameters,
            dataType: "json",
            success: function (json) {
                if (json.check) {
                    Generic.dialogOk("Conexion existosa");
                } else {
                    Generic.warningDialog("Conexion erronea");
                }
            }
        });
    }
}


