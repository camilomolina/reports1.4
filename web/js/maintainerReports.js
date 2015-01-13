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

    validateParameter: function(){
        var forward = true;
        ERROR_MESSAGE = [];

        if ($("#parameterName").val()==""){
            ERROR_MESSAGE.push("Debe ingresar nombre del parametro");
            forward = false;
        }
        if ($("#parameterTypeId").val()==-1){
            ERROR_MESSAGE.push("Debe ingresar tipo del parametro");
            forward = false;
        }

        return forward;
    },

    validateReport: function() {
        var forward = true;
        ERROR_MESSAGE = [];

        if ($("#name").val()==""){
            ERROR_MESSAGE.push("Debe ingresar nombre del reporte");
            forward = false;
        }

        if ($("#conexionId").val()==-1){
            ERROR_MESSAGE.push("Debe ingresar una conexi&oacute;");
            forward = false;
        }

        if ($("#areaId").val()==-1){
            ERROR_MESSAGE.push("Debe ingresar el &aacute;rea");
            forward = false;
        }

        if ($("#sqlDescription").val()==""){
            ERROR_MESSAGE.push("Debe ingresar descripci&oacute;n");
            forward = false;
        }

        if ($("#sqlText").val()==""){
            ERROR_MESSAGE.push("Debe ingresar el script");
            forward = false;
        }

        return forward;
    },

    clean: function(){
        $("#name").val("");
        $("#conexionId").val(-1);
        $("#areaId").val(-1);
        $("#sqlDescription").val("");
        $("#sqlText").val("");
        $("#parameterName").val("");
        $("#parameterTypeId").val(-1);
        $("#parameterRequired").prop("checked",false);

        $.ajax({
            url:"/reports14/maintainerReports.do"
            , method: "POST"
            , data: "method=clean"
            , dataType: "html"
            , success: function(html){
                MaintainerReports.cleanTableParameter();
            }
            , error: function(){
                alert("No he podido limpiar todo");
            }
        });
    },

    cleanParameter: function(){
        $("#parameterName").val("");
        $("#parameterTypeId").val(-1);
        $("#parameterRequired").prop("checked",false);
    },

    cleanTableParameter: function(){
        $.ajax({
            url:"/reports14/maintainerReports.do"
            , method:"POST"
            , data: "method=cleanTableParameter"
            , dataType: "html"
            , success: function(html){
                MaintainerReports.refresh();
            }
            , error: function(){
                alert("No pude actualizar la tabla de parametros");
            }
        });
    },

    addParameter: function(){
        if (MaintainerReports.validateParameter()){
            var parameters = {
                "method":"addParameter"
                //, "parameterId":$("#id").val()
                , "parameterName":$("#parameterName").val()
                , "parameterTypeId":$("#parameterTypeId").val()
                , "parameterRequired":$("#parameterRequired").prop("checked")
                , "parameterTypeName":$("#parameterTypeId option:selected").text()
            };

            $.ajax({
                url:"/reports14/maintainerReports.do"
                , method:"POST"
                , data: parameters
                , dataType:"html"
                , success:function(html){
                    MaintainerReports.refresh();
                    MaintainerReports.cleanParameter();
                }
            });
        }else{
            Generic.errorDialog(ERROR_MESSAGE);
        }

    },

    editParameter: function (id){
        var parameter = {
            "method": "editParameter"
            , "parameterId":id
        };

        $(function() {
            $.ajax({
                url:"/reports14/maintainerReports.do"
                , data: parameter
                , dataType: "json"
                , success:function(json){
                    //$("#parameterId").val(json.id);
                    $("#parameterName").val(json.name);
                    $("#parameterTypeId").val(json.type);
                    if (json.required==true){
                        $("#parameterRequired").prop('checked',true);
                    }else{
                        $("#parameterRequired").prop('checked',false);
                    }
                }
            });
        });
    },

    delParameter: function(id){
        var parameter = {
            "method": "delParameter"
            , "parameterId": id
        };

        $(function() {
            //Se declara el texto
            var div='<div id="dialog-confirm" title="Confirmar">'+
                '<p>' +
                '<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 10px 0;"></span>'+
                '¿Desea eliminar el parametro?'+
                '</p>'+
                '</div>';

            $(div).dialog({
                resizable: false,
                height:230,
                modal: true,
                buttons: {
                    "Eliminar": function() {
                        $( this ).dialog( "close" );
                        $.ajax({
                            url:"/reports14/maintainerReports.do"
                            , data: parameter
                            , dataType: "html"
                            , success: function(html){
                                //$("#tableParameters").html(html)
                                MaintainerReports.refresh();
                            }
                        });
                    },
                    Cancelar: function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
        });

    },

    saveReport: function(){
        if (MaintainerReports.validateReport()){
            var report = {
                method: "save"
                , "name":$("#name").val()
                , "conexionId":$("#conexionId").val()
                , "areaId":$("#areaId").val()
                , "sqlDescription":$("#sqlDescription").val()
                , "sqlText": $("#sqlText").val()
            };

            $.ajax({
                url:"/reports14/maintainerReports.do"
                , method: "POST"
                , data: report
                , dataType: "html"
                , success: function(html){
                    MaintainerReports.refreshReport();
                    MaintainerReports.clean();
                    MaintainerReports.cleanTableParameter();
                }
            });
        }else{
            Generic.errorDialog(ERROR_MESSAGE);
        }
    },

    delReport: function(id){
        var parameter = {
            "method": "delReport"
            , "reportId": id
        };

        $(function() {
            //Se declara el texto
            var div='<div id="dialog-confirm" title="Confirmar">'+
                '<p>' +
                '<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 10px 0;"></span>'+
                '¿Desea eliminar el Reporte?'+
                '</p>'+
                '</div>';

            $(div).dialog({
                resizable: false,
                height:230,
                modal: true,
                buttons: {
                    "Eliminar": function() {
                        $( this ).dialog( "close" );
                        $.ajax({
                            url: "/reports14/maintainerReports.do"
                            , data: parameter
                            , dataType: "html"
                            , success: function(){
                                MaintainerReports.refreshReport();
                            }
                            , error: function(){
                                alert("No se pudo eliminar el reporte") ;
                            }
                        });
                    },
                    Cancelar: function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
        });
    },

    refresh: function() {
        $.ajax({
            url: "/reports14/maintainerReports.do",
            method: "POST",
            data: "method=refresh",
            dataType: "html",
            success: function (html) {
                $("#tableParameters").html(html);
            }
        });
    },

    refreshReport: function(){
        $.ajax({
            url: "/reports14/maintainerReports.do"
            , method: "POST"
            , data: "method=refreshReport"
            , dataType: "html"
            , success: function(html){
                $("#tableReports").html(html);
                $("#name").val("");
                $("#conexionId").val(-1);
                $("#areaId").val(-1);
                $("#sqlDescription").val("");
                $("#sqlText").val("");
            }
            , error: function (){
                alert("No pude refrescar los reportes");
            }
        });
    },

    editReport: function(id){
        var parameters = {
            "method": "editReport"
            , "reportId": id
        };

        $(function() {
            $.ajax ({
                url: "/reports14/maintainerReports.do"
                , data: parameters
                , dataType: "json"
                , success: function(json){
                    $("#name").val(json.name);
                    $("#conexionId").val(json.conexionId);
                    $("#areaId").val(json.areaId);
                    $("#sqlDescription").val(json.description);
                    $("#sqlText").val(json.sql);
                    $("#tableParameters").val(json.parameterList);
                    $("#tabs").tabs("select","maintainer");
                    MaintainerReports.refresh();
                }
                , error: function(){
                    alert("No pude editar el reporte");
                }
            });
        });
    },

    get: function(id) {

    },

    nuevoMetodo: function (id, name, code) {

    }

};


