<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title>Seguridad - Mantenedor Usuarios</title>

    <link type="text/css" rel="stylesheet" href="/reports14/css/redmond/jquery-ui-1.9.2.custom.min.css"/>
    <link type="text/css" rel="stylesheet" href="/reports14/css/reports.css"/>

    <script type="text/javascript" src="/reports14/js/ext/oop.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery-ui-1.9.2.custom.min.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery.numberformatter.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery.validator.js"></script>
    <script type="text/javascript" src="/reports14/js/utils/comboboxUtils.js"></script>

    <script type="text/javascript" src="/reports14/js/generic.js"></script>
    <script type="text/javascript" src="/reports14/js/maintainerReports.js"></script>

    <script type="text/javascript">
        function guardar() {
            document.getElementById("method").value = 'save';
            document.getElementById("pruebaFB").submit();
            /*
            $("#method").val('save');
            var parametro = {
                "method":"save"
                , "name":$("#name").val()
                , "areaId":$("#areaId").val()
                , "sqlDescription":$("#sqlDescription").val()
                , "sqlText":$("#sqlText").val()
            }
            */
        }

        function clean(){
            document.getElementById("method").value = 'clean';
            document.getElementById("pruebaFB").submit();
        }

        function guardarParametros(){
            $("#method").val('saveParameter');
            var boleano = true;
            var parametro = "";
            //if ($("#parameterName").val()==""){
            if ($("#parameterName").val() == "") {
                parametro=parametro+'- Nombre Parametro'+"<BR>";
                boleano=false;
            }else{}
            if ($("#parameterType").val() == (-1)) {
                parametro=parametro+'- Tipo parametro'+"<BR>";
                boleano=false;
            }else{}
            if (boleano == false){
                $(function() {
                    var div = '<div id="dialog-lackInformation" title="Falta informaci&oacute;n" style="display: none;">'+
                            '<p>'+
                            '<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>'+
                            'Debes ingresar los siguientes datos:'+'<BR>'+'<BR>'+parametro+
                            ' </p>'+
                            '</div>';
                    $(div).dialog({
                        modal: true,
                        buttons: {
                            Ok: function() {
                                $( this ).dialog( "close" );
                            }
                        }
                    });
                });
            }else{
                //$("#pruebaFB"). submit();
                var parameters = {
                    "method":"saveParameter"
                    , "parameterName":$("#parameterName").val()
                    , "parameterType":$("#parameterType").val()
                    , "parameterRequired":$("#parameterRequired").prop("checked")
                    , "parameterType":$("#parameterType option:selected").text()
                }



                $.ajax({
                    url: "/reports14/maintainerReports.do"
                    , data: parameters
                    , dataType:"html"
                    , success:function(html) {
                        //document.getElementById("param").innerHTML = html;
                        $("#parameter").html(html);
                        $("#parameterName").val("")
                        $("#parameterType").val(-1)
                        $("#parameterRequired").prop("checked",false)
                    }
                });

            }
            //document.getElementById("method").value='saveParameter';
            //document.getElementById("pruebaFB").submit();
        }

        function eliminarParametro(id){
            //document.getElementById("method").value="deleteParameter"
            //document.getElementById("pruebaFB").submit();
            $("#method").val('deleteParameter');
            $(function() {
                var div='<div id="dialog-confirm" title="Confirmar">'+
                        '<p>' +
                        '<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 10px 0;"></span>'+
                        '¿Desea eliminar el parametro?'+
                        '</p>'+
                        '</div>'

                $(div).dialog({
                    resizable: false,
                    height:230,
                    modal: true,
                    buttons: {
                        "Eliminar": function() {
                            $( this ).dialog( "close" );
                            var idParameter = {
                                "method":"deleteParameter"
                                , "parameterId":id
                            }

                            $.ajax({
                                url: "/reports14/maintainerReports.do"
                                , data: idParameter
                                , dataType:"html"
                                , success: function(html){
                                    $("#parameter").html(html);
                                }

                            });
                        },
                        Cancelar: function() {
                            $( this ).dialog( "close" );
                        }
                    }
                });
            });
        }

        function editarParametro(id){
            // retorno json
            var parameters = {
                "method":"editParameter"
                , "parameterId":id

            }

            $.ajax({
                 url: "/reports14/maintainerReports.do"
                 , data: parameters
                 , dataType:"json"
                 , success:function(json) {
                    $("#parameterName").val(json.name);
                    $("#parameterType").val(json.type);

                    if  (json.requiered==true){
                        $("#parameterRequired").prop('checked', true);
                    }else{
                        $("#parameterRequired").attr('checked', false);
                    }



                     //json.sql;
                 }
             });


        }



    </script>
</head>

<body style="display: none;" id="body">


<html:form action="/maintainerReports" styleId="pruebaFB">

    <input type="hidden" name="method" id="method"/>
    <div id="tabs">
        <ul>
            <li title="Formulario mantenedor de reportes"><a href="#tabs-1">Mantenedor de reportes</a></li>
            <li title="Listado de reportes agregados"><a href="#tabs-2">Listado reportes</a></li>
        </ul>
        <div id="tabs-1" align="center">

            <table id="tableDatos">
                <tr>
                    <td>Nombre</td>
                    <td>
                        <html:text property="name" styleId="name" size="33px;"/>
                    </td>
                </tr>
                <tr>
                    <td>Área</td>
                    <td>
                        <html:select property="areaId" styleId="areaId">
                            <html:option value="-1">Seleccione un &aacute;rea</html:option>
                            <logic:notEmpty name="maintainerReportsForm" property="areaList">
                                <logic:iterate id="dto" name="maintainerReportsForm" property="areaList">
                                    <option value="<bean:write name="dto" property="id"/>"><bean:write name="dto" property="name"/></option>
                                </logic:iterate>
                            </logic:notEmpty>
                        </html:select>

                    </td>
                </tr>
                <tr>
                    <td>Descripci&oacute;n</td>
                    <td>
                        <html:textarea property="sqlDescription" styleId="sqlDescription" rows="4" cols="50"></html:textarea>
                    </td>
                </tr>
                <tr>
                    <td>SQL</td>
                    <td>
                        <html:textarea property="sqlText" styleId="sqlText" rows="20" cols="67"></html:textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table id="tableDatos2">
                            <tr>
                                <td>Parametro</td>
                                <td>
                                    <html:text property="parameterName" styleId="parameterName"/>
                                </td>
                                <td>Tipo</td>
                                <td>
                                    <html:select property="parameterTypeId" styleId="parameterTypeId">
                                        <html:option value="-1">Seleccione un tipo</html:option>
                                        <logic:notEmpty name="maintainerReportsForm" property="parameterTypeList">
                                            <logic:iterate id="dto" name="maintainerReportsForm" property="parameterTypeList">
                                                <option value="<bean:write name="dto" property="id"/>"> <bean:write name="dto" property="name"/></option>
                                            </logic:iterate>
                                        </logic:notEmpty>
                                    </html:select>
                                </td>
                                <td>Requerido</td>
                                <td>
                                    <html:checkbox property="parameterRequired" styleClass="parameterRequired" styleId="parameterRequired" value="true"/>
                                </td>
                                </td>
                                <td><img src="/reports14/img/menu/more.gif" alt="Agrega" title="Agrega parametro"
                                         onclick="guardarParametros();"
                                         style="cursor:pointer;"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" id="parameter">
                        <jsp:include page="/jsp/jspf/parametersList.jsp" flush="true"/>
                    </td>
                </tr>

                <tr>
                    <td colspan="2">
                        <table id="tableDatos3" align="right">
                            <tr>
                                <td id="options" colspan="2" align="right">
                                    <table>
                                        <tr>
                                            <td>
                                                <button id="add" title="Agregar a la lista de reportes" onclick="guardar();">Agregar</button>
                                            </td>
                                            <td>
                                                <button id="clean" title="Limpia los datos del formulario" onclick="clean();">Limpiar</button>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <div id="tabs-2" align="center">

            <table id="list">
                <tr>
                    <th>Nombre</th>
                    <th width="80px;">Area</th>
                    <th align="center" width="60px;">Acciones</th>
                </tr>

                <tr>
                    <td>Query Cobranza</td>
                    <td>Cobranza</td>
                    <td align="center" class="acciones">
                        <img src="/reports14/img/pencil.gif" alt="Editar" title="Edita la query" style="cursor:pointer;"
                             onclick="MaintainerReports.nuevoMetodo(1,'',1);"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <img src="/reports14/img/icono_cancelar.gif" alt="Eliminar" title="Elimina la query"
                             onclick="MaintainerReports.init();"
                             style="cursor:pointer;"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <img src="/reports14/img/find.gif" alt="Eliminar" title="Muestra la query"
                             onclick="MaintainerReports.init();"
                             style="cursor:pointer;"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>

</html:form>
</body>
</html>