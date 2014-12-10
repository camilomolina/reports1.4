<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Reportes</title>
    <link type="text/css" rel="stylesheet" href="/reports14/css/redmond/jquery-ui-1.9.2.custom.min.css"/>
    <link type="text/css" rel="stylesheet" href="/reports14/css/reports.css"/>
    <script type="text/javascript" src="/reports14/js/ext/oop.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery-ui-1.9.2.custom.min.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery.ui.datepicker-es.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery.numberformatter.js"></script>
    <script type="text/javascript" src="/reports14/js/ext/jquery/jquery.validator.js"></script>
    <script type="text/javascript" src="/reports14/js/utils/comboboxUtils.js"></script>

    <script type="text/javascript" src="/reports14/js/generic.js"></script>
    <script type="text/javascript" src="/reports14/js/report.js"></script>
</head>
<body id="body">
<html:form action="/report" styleId="reportForm">
    <input type="hidden" name="method" id="method" />

    <input type="hidden" name="id" id="id" />
    <input type="hidden" name="report" id="report" />

    <div id="dialogBase64" style="display: none;">
        Generando reporte ...
        <br/>
        <br/>
        <center><img src='/reports14/img/loading.gif'/></center>
        <br>
    </div>

    <div  align="center">
        <table id="tableDatos" >
            <tr>
                <td colspan="2" style="font-size:24px;"><bean:write name="SESSION_Report" property="description"/></td>
            </tr>
            <tr>
                <td colspan="2"></td>
            </tr>
            <logic:notEmpty name="SESSION_Report"  property="parameterList" scope="session">
                <logic:iterate id="parameter" name="SESSION_Report" property="parameterList" scope="session">
                    <tr>
                        <td><bean:write name="parameter" property="name"/></td>
                        <td style="display:inline;">
                            <logic:equal value="1" name="parameter" property="type">
                                <!--Alfanumerico-->
                                <input type="text" size="20" name="parameter_<bean:write name="parameter" property="id"/>" id="parameter_<bean:write name="parameter" property="id"/>_id" parameterRequired="<bean:write name="parameter" property="required" />" parameterType="<bean:write name="parameter" property="type" />" parameterName="<bean:write name="parameter" property="name" />" parameterData1="<bean:write name="parameter" property="data1" />" parameterData2="<bean:write name="parameter" property="data2" />" />
                            </logic:equal>
                            <logic:equal value="2" name="parameter" property="type">
                                <!--Fecha-->
                                <input type="text" size="8" name="parameter_<bean:write name="parameter" property="id"/>" id="parameter_<bean:write name="parameter" property="id"/>_id" parameterRequired="<bean:write name="parameter" property="required" />" parameterType="<bean:write name="parameter" property="type" />" parameterName="<bean:write name="parameter" property="name" />" parameterData1="<bean:write name="parameter" property="data1" />" parameterData2="<bean:write name="parameter" property="data2" />" />
                            </logic:equal>
                            <logic:equal value="3" name="parameter" property="type">
                                <!--Numerico-->
                                <input type="text" size="15" name="parameter_<bean:write name="parameter" property="id"/>" id="parameter_<bean:write name="parameter" property="id"/>_id" parameterRequired="<bean:write name="parameter" property="required" />" parameterType="<bean:write name="parameter" property="type" />" parameterName="<bean:write name="parameter" property="name" />" parameterData1="<bean:write name="parameter" property="data1" />" parameterData2="<bean:write name="parameter" property="data2" />" />
                            </logic:equal>
                            <logic:equal value="4" name="parameter" property="type">
                                <!--Booleano-->
                                <input type="checkbox" name="parameter_<bean:write name="parameter" property="id"/>" id="parameter_<bean:write name="parameter" property="id"/>_id" parameterRequired="<bean:write name="parameter" property="required" />" parameterType="<bean:write name="parameter" property="type" />" parameterName="<bean:write name="parameter" property="name" />" parameterData1="<bean:write name="parameter" property="data1" />" parameterData2="<bean:write name="parameter" property="data2" />" />
                            </logic:equal>
                            <logic:equal value="5" name="parameter" property="type">
                                <!--Rango Fechas-->
                                <input type="text" size="8" name="parameter_<bean:write name="parameter" property="id"/>_init" id="parameter_<bean:write name="parameter" property="id"/>_id_init" parameterRequired="<bean:write name="parameter" property="required" />" parameterType="<bean:write name="parameter" property="type" />" parameterName="<bean:write name="parameter" property="name" />" parameterData1="<bean:write name="parameter" property="data1" />" parameterData2="<bean:write name="parameter" property="data2" />"/>
                                -
                                <input type="text" size="8" name="parameter_<bean:write name="parameter" property="id"/>_final" id="parameter_<bean:write name="parameter" property="id"/>_id_final" parameterRequired="<bean:write name="parameter" property="required" />" parameterType="<bean:write name="parameter" property="type" />" parameterName="<bean:write name="parameter" property="name" />"  parameterData1="<bean:write name="parameter" property="data1" />" parameterData2="<bean:write name="parameter" property="data2" />"/>
                            </logic:equal>
                        </td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
            <tr>
                <td colspan="2">
                    <table id="tableDatos2">
                        <tr>
                            <td id="options" colspan="2" align="right">
                                <table>
                                    <tr>
                                        <td>
                                            <input type="button" value="Generar" id="generate" class="boton" onclick="Report.generate();"/>
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

    <fieldset style="display:none" id="loader">
        <span>Espere un Momento</span>
        <img src="/reports14/img/loading.gif"/>
    </fieldset>

</html:form>
</body>
</html>
