<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>


<table id="list" id2="parametros">
    <tr>
        <th>Parametro</th>
        <th width="80px;">Tipo</th>
        <th width="80px;">R</th>
        <th align="center" width="60px;">Acciones</th>
    </tr>
    <logic:notEmpty  name="PARAMETERS_LIST" scope="session">
        <logic:iterate id="dtoParameters" name="PARAMETERS_LIST" scope="session" indexId="index" >
            <tr>
                <td><bean:write name="dtoParameters" property="name" /></td>
                <td><bean:write name="dtoParameters" property="parameterTypeEnum.name" /></td>
                <td>
                    <logic:equal value="true" property="required" name="dtoParameters">
                        <p>Si</p>
                    </logic:equal>
                    <logic:notEqual value="true" property="required" name="dtoParameters">
                        <p>No</p>
                    </logic:notEqual>
                </td>
                <td align="center" class="acciones">
                    <img src="/reports14/img/pencil.gif" alt="Editar" title="Edita el parametro" style="cursor:pointer;"
                         onclick="MaintainerReports.getParameter(<bean:write name="index"/>);"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <img src="/reports14/img/icono_cancelar.gif" alt="Eliminar" title="Elimina el parametro"
                         onclick="MaintainerReports.deleteParameter(<bean:write name="index"/>);"
                         style="cursor:pointer;"/>
                </td>
            </tr>
        </logic:iterate>
    </logic:notEmpty>
</table>