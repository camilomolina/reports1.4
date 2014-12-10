<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>


<table id="list" >
    <tr>
        <th>Nombre</th>
        <th width="70px;">URL</th>
        <th align="center" width="70px;" >Drivers</th>
        <th>Usuario</th>
        <th>Acciones</th>
    </tr>

    <logic:iterate id="dto" name="maintainerDatasourceForm" property="conexionList"  >
        <tr>
            <td><bean:write name="dto" property="name"/></td>
            <td><input value="<bean:write name="dto" property="url"/>" readonly="readonly"/></td>
            <td><bean:write name="dto" property="controllerDTO.name"/></td>
            <td><bean:write name="dto" property="user"/></td>

            <td align="center" class="acciones">
                <img src="/reports14/img/icono_modificar.gif" alt="Editar" title="Edita la conexion" onclick="MaintainerDatasource.get(<bean:write name="dto" property="id"/>);" style="cursor:pointer;" />
                &nbsp;&nbsp;&nbsp;&nbsp;
                <img src="/reports14/img/icono_cancelar.gif" alt="Eliminar" title="Elimina la conexion" onclick="MaintainerDatasource.deleteFn(<bean:write name="dto" property="id"/>, '<bean:write name="dto" property="name"/>');" style="cursor:pointer;" />
            </td>
        </tr>
    </logic:iterate>
</table>
