<%@ page import="java.util.Date" %>
<%@ page import="cl.bennu.reports.commons.enums.ParameterTypeEnum" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="Logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
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
    <script type="text/javascript" src="/reports14/js/maintainerReports.js?<%=(new Date()).getTime()%>"></script>
</head>

<body id="body">
<html:form action="/maintainerReports" styleId="maintainerReportsFormId">
    <input type="hidden" name="method" id="method" />
    <input type="hidden" name="id" id="id" />
    <input type="hidden" name="maintainerReportsId" id="maintainerReportsId" />

    <div id="tabs">
        <ul>
            <li title="Mantenedor"><a href="#maintainer">Crear reporte</a></li>
            <li title="Listado reportes"><a href="#listTable">Listado</a></li>
            <li title="Ayuda"><a href="#helpTable">Ayuda</a></li>
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
                    <td>Conexi&oacute;n</td>
                    <td>
                        <html:select property="conexionId" styleId="conexionId" title="Conexi&oacute;n a utilizar">
                            <html:option value="-1">Seleccione una conexi&oacute;n</html:option>
                            <logic:notEmpty name="maintainerReportsForm" property="conexionList">
                                <Logic:iterate id="conexion" name="maintainerReportsForm" property="conexionList">
                                    <option value="<bean:write name="conexion" property="id"/>"><bean:write name="conexion" property="name"/></option>
                                </Logic:iterate>
                            </logic:notEmpty>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td>&Aacute;rea</td>
                    <td>
                        <html:select property="areaId" styleId="areaId" title="&Aacute;rea a la que pertenece">
                            <html:option value="-1">Seleccione un &aacute;rea</html:option>
                            <logic:notEmpty name="maintainerReportsForm" property="areaList">
                                <logic:iterate id="area" name="maintainerReportsForm" property="areaList">
                                    <option value="<bean:write name="area" property="id"/>"> <bean:write name="area" property="name"/></option>
                                </logic:iterate>
                            </logic:notEmpty>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td>Descripci&oacute;n</td>
                    <td>
                        <html:textarea property="sqlDescription" styleId="sqlDescription" title="Descripci&oacute;n del reporte" rows="3" cols="60"></html:textarea>
                    </td>
                </tr>
                <tr>
                    <td>SQL</td>
                    <td>
                        <html:textarea property="sqlText" styleId="sqlText" title="C&oacute;digo del reporte" rows="10" cols="60"></html:textarea>
                    </td>
                </tr>
                <tr>
                    <table id = "tableDatos2">
                        <tr>
                            <td>Par&aacute;metro</td>
                            <td>
                                <html:text property="parameterName" styleId="parameterName" title="Nombre del parametro"></html:text>
                            </td>
                            <td>Tipo</td>
                            <td>
                                <html:select property="parameterTypeId" styleId="parameterTypeId" title="Tipo del parametro" onchange="MaintainerReports.checkParameterType(true);">
                                    <html:option value="-1">Seleccione un tipo</html:option>
                                    <logic:notEmpty name="maintainerReportsForm" property="parameterTypeList">
                                        <logic:iterate id="dtoParameters" name="maintainerReportsForm" property="parameterTypeList">
                                            <option  value="<bean:write name="dtoParameters" property="id"/>"><bean:write name="dtoParameters" property="name"/></option>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </html:select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Formato</td>
                            <td>
                                <html:select property="dateFormatId" styleId="dateFormatId" title="Formato de fecha" disabled="true">
                                    <html:option value="-1">Seleccione un tipo</html:option>
                                    <logic:notEmpty name="maintainerReportsForm" property="dateFormatList">
                                        <logic:iterate id="row" name="maintainerReportsForm" property="dateFormatList">
                                            <option  value="<bean:write name="row" property="id"/>"><bean:write name="row" property="name"/></option>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </html:select>
                            </td>
                            <td>Requerido</td>
                            <td>
                                <html:checkbox property="parameterRequired" styleId="parameterRequired" title="¿Es un parametro requerido?"></html:checkbox>
                            </td>
                            <td><img src="/reports14/img/menu/more.gif" alt="Agrega" title="Agrega parametro" onclick="MaintainerReports.addParameter()" style="cursor:pointer;"></td>
                        </tr>
                    </table>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="tableParameters" align="center">
                            <jsp:include page="/jsp/jspf/parametersList.jsp" flush="true"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table id="tableDatos3" align="right">
                            <tr>
                                <td id="options" colspan="2" >
                                    <table>
                                        <tr>
                                            <td>
                                                <input type="button" value="Agregar" id="add" class="Boton" onclick="MaintainerReports.save();"/>
                                            </td>
                                            <td>
                                                <input type="button" value="Limpiar" id="clean" class="Boton" onclick="MaintainerReports.clean();"/>
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
            <tr>
                <div id="tableReports" align="center">
                    <jsp:include page="/jsp/jspf/reportsList.jsp" flush="true"/>
                </div>
            </tr>
        </div>

        <div id="helpTable" align="center">
            <p>
                Los parametros deben ingresarse con la etiqueta $P{NOMBRE_PARAMETRO}
                <br/>
                Ejemplo:  <strong>WHERE ID_PRODUCTO = $P{idProducto}</strong>
                <br/><br/>

                Si el parametro es del tipo rango de fecha se debe repetir el parametro
                <br/>
                Ejemplo:   <strong>WHERE FECHA_INICIO BETWEEN $P{fecha} AND $P{fecha}</strong>
                <br/><br/>

                Si el parametro es opcional se debe cerrar toda la sentencia SQL con la etiqueta $O{NOMBRE_PARAMETRO}
                <br/>
                Ejemplo:  <strong>$O{valor} WHERE MONTO_PRODUCTO = $P{valor} $O{valor}</strong>
                <br/><br/>
                Si se selecciona algun formato de fecha, el programa ingresa automaticamente el primer dia del dia y/o del mes
            </p>
        </div>

        <fieldset style="display:none" id="loader">
            <span>Espere un Momento</span>
            <img src="/reports14/img/loading.gif"/>
        </fieldset>


    </div>
</html:form>
</body>
</html>
