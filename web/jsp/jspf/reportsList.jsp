<%--
  Created by IntelliJ IDEA.
  User: Francisco
  Date: 22-10-2014
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<table id="list">
    <tr>
        <th>Nombre</th>
        <th>&Aacute;rea</th>
        <th>Acciones</th>
    </tr>
    <logic:notEmpty name="reportsList" >
        <logic:iterate id="dtoReportList" name="reportsList" >
            <tr>
                <td><bean:write name="dtoReportList" property="name"/></td>
                <td><bean:write name="dtoReportList" property="areaId"/></td>
                <td align="center" class="acciones">
                    <img src="/reports14/img/pencil.gif" alt="Editar" title="Edita el reporte" style="cursor:pointer;"
                         onclick="MaintainerReports.editReport(<bean:write name="dtoReportList" property="id"/>)"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <img src="/reports14/img/icono_cancelar.gif" alt="Eliminar" title="Elimina el reporte"
                         onclick="MaintainerReports.delReport(<bean:write name="dtoReportList" property="id"/>)"
                         style="cursor:pointer;"/>
                </td>
            </tr>
        </logic:iterate>
    </logic:notEmpty>
</table>
