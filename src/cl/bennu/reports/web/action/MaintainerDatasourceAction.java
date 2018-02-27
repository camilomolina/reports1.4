package cl.bennu.reports.web.action;

import cl.bennu.reports.commons.dto.ConexionDTO;
import cl.bennu.reports.commons.dto.ControllerDTO;
import cl.bennu.reports.web.delegate.DynamicReportDelegate;
import cl.bennu.reports.web.form.MaintainerDatasourceForm;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;


public class MaintainerDatasourceAction extends BaseAction {

    private static final String START_DATASOURCE = "startDatasource";
    private static final String DATASOURCE_LIST = "datasourceList";

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerDatasourceForm maintainerDatasourceForm = (MaintainerDatasourceForm) form;

        List conexionList = DynamicReportDelegate.getInstance().getAllConexionSummary(buildContext(request));
        maintainerDatasourceForm.setConexionList(conexionList);

        List controllerList = DynamicReportDelegate.getInstance().getAllController(buildContext(request));
        maintainerDatasourceForm.setControllerList(controllerList);

        return mapping.findForward(START_DATASOURCE);
    }

    private ConexionDTO fillConexionDTO(MaintainerDatasourceForm maintainerDatasourceForm) {
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setId(maintainerDatasourceForm.getControllerId());

        ConexionDTO conexionDTO = new ConexionDTO();
        conexionDTO.setControllerDTO(controllerDTO);
        conexionDTO.setUser(maintainerDatasourceForm.getUser());
        conexionDTO.setPass(maintainerDatasourceForm.getPass());
        conexionDTO.setName(maintainerDatasourceForm.getName());
        conexionDTO.setUrl(maintainerDatasourceForm.getUrl());
        conexionDTO.setId(maintainerDatasourceForm.getId());

        return conexionDTO;
    }

    public void save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerDatasourceForm maintainerDatasourceForm = (MaintainerDatasourceForm) form;

        ConexionDTO conexionDTO = fillConexionDTO(maintainerDatasourceForm);
        DynamicReportDelegate.getInstance().saveConexion(buildContext(request), conexionDTO);

        JSONObject jsonObject = JSONObject.fromObject(conexionDTO);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());
    }

    public void delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerDatasourceForm maintainerDatasourceForm = (MaintainerDatasourceForm) form;

        DynamicReportDelegate.getInstance().deleteConexion(buildContext(request), maintainerDatasourceForm.getMaintainerDatasourceId());

        JSONObject jsonObject = JSONObject.fromObject(new ConexionDTO());
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());
    }

    public ActionForward refresh(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerDatasourceForm maintainerDatasourceForm = (MaintainerDatasourceForm) form;

        List conexionList = DynamicReportDelegate.getInstance().getAllConexionSummary(buildContext(request));
        maintainerDatasourceForm.setConexionList(conexionList);

        return mapping.findForward(DATASOURCE_LIST);
    }

    public void get(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerDatasourceForm maintainerDatasourceForm = (MaintainerDatasourceForm) form;

        ConexionDTO conexionDTO = DynamicReportDelegate.getInstance().getConexionById(buildContext(request), maintainerDatasourceForm.getMaintainerDatasourceId());

        JSONObject jsonObject = JSONObject.fromObject(conexionDTO);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());
    }

    public void check(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerDatasourceForm maintainerDatasourceForm = (MaintainerDatasourceForm) form;

        String url = maintainerDatasourceForm.getUrl();
        String user = maintainerDatasourceForm.getUser();
        String pass = maintainerDatasourceForm.getPass();
        String driverBD = "";

        if (maintainerDatasourceForm.getControllerId().equals(new Long(1))) {
            driverBD = "org.postgresql.Driver";
        } else if (maintainerDatasourceForm.getControllerId().equals(new Long(2))) {
            driverBD = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if (maintainerDatasourceForm.getControllerId().equals(new Long(3))) {
            driverBD = "com.mysql.jdbc.Driver";
        } else if (maintainerDatasourceForm.getControllerId().equals(new Long(4))) {
            driverBD = "oracle.jdbc.driver.OracleDriver";
        } else if (maintainerDatasourceForm.getControllerId().equals(new Long(5))) {
            driverBD = "net.sourceforge.jtds.jdbcx.JtdsDataSource";
        }

        ConexionDTO conexionDTO = new ConexionDTO();
        Connection cn = null;
        try {
            Class.forName(driverBD);
            cn = DriverManager.getConnection(url, user, pass);
            conexionDTO.setCheck(Boolean.TRUE);
        } catch (Exception e) {
            conexionDTO.setCheck(Boolean.FALSE);
        } finally {
            if (cn != null) cn.close();
        }

        JSONObject jsonObject = JSONObject.fromObject(conexionDTO);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());
    }
}