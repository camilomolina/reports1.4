package cl.bennu.reports.web.action;


import cl.bennu.reports.commons.dto.ParameterDTO;
import cl.bennu.reports.commons.dto.ReportDTO;
import cl.bennu.reports.commons.enums.ParameterTypeEnum;
import cl.bennu.reports.web.delegate.DynamicReportDelegate;
import cl.bennu.reports.web.form.MaintainerReportsForm;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Francisco
 * Date: 29-01-14
 * Time: 01:46 PM
 */
public class MaintainerReportsAction extends BaseAction {

    private static final String START_MAINTAINER_REPORT = "startMaintainerReports";


    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;
        ((MaintainerReportsForm) form).setReportId(null);

        List areaList = DynamicReportDelegate.getInstance().getAllArea(buildContext(request));
        maintainerReportsForm.setAreaList(areaList);

        List enumList = ParameterTypeEnum.getEnumList();
        maintainerReportsForm.setParameterTypeList(enumList);

        List conexionList = DynamicReportDelegate.getInstance().getAllConexionSummary(buildContext(request));
        maintainerReportsForm.setConexionList(conexionList);

        List reportsList = DynamicReportDelegate.getInstance().getAllReport(buildContext(request));
        request.setAttribute("reportsList", reportsList);

        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("PARAMETERS_LIST");

        //Se setean los valores de reportes
        maintainerReportsForm.setReportId(null);
        maintainerReportsForm.setAreaId(null);
        maintainerReportsForm.setName("");
        maintainerReportsForm.setSqlDescription("");
        maintainerReportsForm.setSqlText("");

        //Se setean los valores de parametros
        maintainerReportsForm.setParameterName("");
        maintainerReportsForm.setParameterRequired(Boolean.FALSE);
        maintainerReportsForm.setParameterTypeId(null);
        maintainerReportsForm.setParameterTypeName("");

        return mapping.findForward(START_MAINTAINER_REPORT);
    }

    public ActionForward cleanTableParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


/*        MaintainerReportsForm formulario = (MaintainerReportsForm) form;


        formulario.setName("");
        formulario.setAreaId(new Long(-1));
        formulario.setSqlDescription("");
        formulario.setSqlText("");
        formulario.setParameterName("");
        formulario.setParameterTypeId(new Long(-1));
        formulario.setParameterRequired(Boolean.valueOf(false));
*/
        HttpSession httpSession = request.getSession();
        List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");
        parametersList.clear();
        httpSession.setAttribute("PARAMETERS_LIST", parametersList);

        System.out.println("Se limpio la tabla de parametros");
        return mapping.findForward("success_list");

    }

    public ActionForward addParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        //METODOS PARA INGRESAR DATOS
        ParameterDTO parameterDTO = new ParameterDTO();
        System.out.println("Este es el indice: " + maintainerReportsForm.getParameterId());
        parameterDTO.setName(maintainerReportsForm.getParameterName());
        parameterDTO.setTypeName(maintainerReportsForm.getParameterTypeName());
        parameterDTO.setType(maintainerReportsForm.getParameterTypeId());
        parameterDTO.setRequired(maintainerReportsForm.getParameterRequired());
        System.out.println("Este es el tipo " + maintainerReportsForm.getParameterTypeName());
        parameterDTO.setLike(Boolean.FALSE);


        if (maintainerReportsForm.getParameterId() == null) {

            HttpSession httpSession = request.getSession();
            List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");
            if (parametersList == null) parametersList = new ArrayList();
            System.out.println("Esta es la lista" + parametersList);
            parametersList.add(parameterDTO);

            httpSession.setAttribute("PARAMETERS_LIST", parametersList);

            maintainerReportsForm.setParameterId(null);

            System.out.println("Estoy agregando los parametros");

        } else {
            HttpSession httpSession = request.getSession();
            List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");
            parametersList.set(maintainerReportsForm.getParameterId().intValue(), parameterDTO);

            maintainerReportsForm.setParameterId(null);

            System.out.println("Edite los parametros");
        }


        return mapping.findForward("success_list");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        System.out.println(maintainerReportsForm.getName());
        System.out.println(maintainerReportsForm.getAreaId());
        System.out.println(maintainerReportsForm.getSqlDescription());
        System.out.println(maintainerReportsForm.getSqlText());

        //llamo a metodos para guardar en db

       /*SE REVISA EL selectAllReporte
        List li = DynamicReportDelegate.getInstance().getAllReport();
        ReportDTO reporteDTO = (ReportDTO) li.get(0);

        System.out.println("El nombre del primer reporte es: " + reporteDTO.getName());
        */

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setName(maintainerReportsForm.getName());
        reportDTO.setAreaId(maintainerReportsForm.getAreaId());
        reportDTO.setDescription(maintainerReportsForm.getSqlDescription());
        reportDTO.setSql(maintainerReportsForm.getSqlText());
        reportDTO.setConexionId(new Long(1));
        reportDTO.setActive(Boolean.TRUE);
        reportDTO.setId(maintainerReportsForm.getReportId());

        HttpSession httpSession = request.getSession();
        List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");

        reportDTO.setParameterList(parametersList);

        DynamicReportDelegate.getInstance().saveReport(buildContext(request), reportDTO);


        System.out.println("Guarde el reporte");


        //return mapping.findForward("success");
        return mapping.findForward(START_MAINTAINER_REPORT);
    }

    public ActionForward delParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm formulario = (MaintainerReportsForm) form;


        HttpSession httpSession = request.getSession();
        List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");

        parametersList.remove(formulario.getParameterId().intValue());

        httpSession.setAttribute("PARAMETERS_LIST", parametersList);

        formulario.setParameterId(null);

        //System.out.println("Borre el parametro");

        return mapping.findForward("success_list");
    }

    public void editParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm formulario = (MaintainerReportsForm) form;

        HttpSession httpSession = request.getSession();
        List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");

        ParameterDTO parameterDTO;
        parameterDTO = (ParameterDTO) parametersList.get(formulario.getParameterId().intValue());


        JSONObject jsonObject = JSONObject.fromObject(parameterDTO);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());

    }

    public ActionForward refresh (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("success_list");
    }

    public ActionForward refreshReport (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        List reportsList = DynamicReportDelegate.getInstance().getAllReport(buildContext(request));
        //maintainerReportsForm.setReportsList(reportsList);
        request.setAttribute("reportsList", reportsList);

        return mapping.findForward("success_report");
    }

    public ActionForward delReport (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        DynamicReportDelegate.getInstance().deleteReport(buildContext(request), maintainerReportsForm.getReportId());

        return mapping.findForward("succes_report");
    }

    public void editReport (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        ReportDTO reportDTO = DynamicReportDelegate.getInstance().getReportById(buildContext(request), maintainerReportsForm.getReportId());

        //Se agregan los parametros del reporte
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("PARAMETERS_LIST",reportDTO.getParameterList());

        JSONObject jsonObject = JSONObject.fromObject(reportDTO);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());

        System.out.println("Estoy editando el reporte");

        //return mapping.findForward("success_report");
        //return null;
    }

    public ActionForward clean (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

            ((MaintainerReportsForm) form).setReportId(null);
            ((MaintainerReportsForm) form).setAreaId(null);
            ((MaintainerReportsForm) form).setSqlDescription(null);
            ((MaintainerReportsForm) form).setSqlText(null);
            ((MaintainerReportsForm) form).setParameterId(null);

        }catch (Exception e){
            System.out.println("No se pudo limpiar el formulario");
        }

        return mapping.findForward(START_MAINTAINER_REPORT);
    }

}
