<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Reportes - Mantenedor de conexiones</title>
    <link type="text/css" rel="stylesheet" href="/reports14/css/redmond/jquery-ui-1.9.2.custom.min.css"/>
    <link type="text/css" rel="stylesheet" href="/reports14/css/reports.css"/>
    <script type="text/javascript" src="/reports14/js/ext/oop.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery-ui-1.9.2.custom.min.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery.numberformatter.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery.validator.js"></script>
    <script type="text/javascript" src="/reports14/js/utils/comboboxUtils.js"></script>

    <script type="text/javascript" src="/reports14/js/generic.js"></script>
    <script type="text/javascript" src="/reports14/js/maintainerDatasource.js"></script>
</head>
<body id="body">
<html:form action="/maintainerDatasource" styleId="maintainerDatasourceFormId">
    <input type="hidden" name="method" id="method" />

    <input type="hidden" name="id" id="id" />
    <input type="hidden" name="maintainerDatasourceId" id="maintainerDatasourceId" />

    <div id="tabs">
        <ul>
            <li title="Agregar"><a href="#maintainer">Agregar</a></li>
            <li title="Listado "><a href="#listTable">Listado</a></li>
        </ul>
        <div id="maintainer" align="center">
            <table id="tableDatos" >
                <tr>
                    <td>Nombre</td>
                    <td>
                        <html:text property="name" styleId="name" title="Nombre de la conexion" />
                    </td>
                </tr>
                <tr>
                    <td>Driver</td>
                    <td>
                        <html:select property="controllerId" styleId="controllerId" >
                            <option value="-1">Seleccione un Driver</option>
                            <logic:notEmpty name="maintainerDatasourceForm" property="controllerList">
                                <logic:iterate id="dto" name="maintainerDatasourceForm" property="controllerList">
                                    <option value="<bean:write name="dto" property="id" />"><bean:write name="dto" property="name" /></option>
                                </logic:iterate>
                            </logic:notEmpty>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td>URL</td>
                    <td>
                        <html:text property="url" styleId="url" title="Debe ingresar una conexion de base de datos" size="80" />
                    </td>
                </tr>
                <tr>
                    <td>Usuario</td>
                    <td>
                        <html:text property="user" styleId="user"/>
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td>
                        <html:password property="pass" styleId="pass"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table id="tableDatos2">
                            <tr>
                                <td id="options" colspan="2" align="right">
                                    <table>
                                        <tr>
                                            <td>
                                                <input type="button" value="Agregar" id="add" class="boton" onclick="MaintainerDatasource.save();"/>
                                            </td>
                                            <td>
                                                <input type="button" value="Limpiar" id="clean" class="boton" onclick="MaintainerDatasource.clean();"/>
                                            </td>
                                            <td>
                                                <input type="button" value="Test" id="check" class="boton" onclick="MaintainerDatasource.check();"/>
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

        <div id="listTable" align="center">
            <jsp:include page="/jsp/jspf/datasourceList.jsp" flush="true"/>
        </div>

        <fieldset style="display:none" id="loader">
            <span>Espere un Momento</span>
            <img src="/reports14/img/loading.gif"/>
        </fieldset>


    </div>
</html:form>
</body>
</html>
