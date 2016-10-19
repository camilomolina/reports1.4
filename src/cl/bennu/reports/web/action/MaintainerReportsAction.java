package cl.bennu.reports.web.action;


import cl.bennu.reports.commons.dto.ParameterDTO;
import cl.bennu.reports.commons.dto.ReportDTO;
import cl.bennu.reports.commons.enums.DateFormatEnum;
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
    private static final String PARAMETER_LIST = "parametersList";
    private static final String REPORT_LIST = "reportList";


    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;
        ((MaintainerReportsForm) form).setReportId(null);

        List areaList = DynamicReportDelegate.getInstance().getAllArea(buildContext(request));
        maintainerReportsForm.setAreaList(areaList);

        List enumList = ParameterTypeEnum.getEnumList();
        maintainerReportsForm.setParameterTypeList(enumList);

        List dateFormatList = DateFormatEnum.getEnumList();
        maintainerReportsForm.setDateFormatList(dateFormatList);

        List conexionList = DynamicReportDelegate.getInstance().getAllConexionSummary(buildContext(request));
        maintainerReportsForm.setConexionList(conexionList);

        List reportsList = DynamicReportDelegate.getInstance().getAllReport(buildContext(request));
        request.setAttribute("reportsList", reportsList);

        request.getSession().removeAttribute("PARAMETERS_LIST");

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
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("PARAMETERS_LIST");

        return mapping.findForward(PARAMETER_LIST);
    }

    public ActionForward addParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;
        HttpSession httpSession = request.getSession();

        ParameterDTO parameterDTO = new ParameterDTO();
        parameterDTO.setName(maintainerReportsForm.getParameterName());
        parameterDTO.setParameterTypeEnum(ParameterTypeEnum.valueOf(maintainerReportsForm.getParameterTypeId()));
        parameterDTO.setRequired(maintainerReportsForm.getParameterRequired());
        parameterDTO.setData2(maintainerReportsForm.getDateFormatId() == null ? null : maintainerReportsForm.getDateFormatId().toString());

        if (maintainerReportsForm.getParameterId() == null) {
            List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");
            if (parametersList == null) parametersList = new ArrayList();
            parametersList.add(parameterDTO);

            httpSession.setAttribute("PARAMETERS_LIST", parametersList);

            maintainerReportsForm.setParameterId(null);
        } else {
            List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");
            parametersList.set(maintainerReportsForm.getParameterId().intValue(), parameterDTO);

            maintainerReportsForm.setParameterId(null);
        }

        return mapping.findForward(PARAMETER_LIST);
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setName(maintainerReportsForm.getName());
        reportDTO.setAreaId(maintainerReportsForm.getAreaId());
        reportDTO.setDescription(maintainerReportsForm.getSqlDescription());
        reportDTO.setSql(maintainerReportsForm.getSqlText());
        reportDTO.setActive(Boolean.TRUE);
        reportDTO.setId(maintainerReportsForm.getReportId());
        reportDTO.setConexionId(maintainerReportsForm.getConexionId());

        HttpSession httpSession = request.getSession();
        List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");

        reportDTO.setParameterList(parametersList);

        DynamicReportDelegate.getInstance().saveReport(buildContext(request), reportDTO);

        //return mapping.findForward(START_MAINTAINER_REPORT);
        return unspecified(mapping, form, request, response);
    }

    public ActionForward deleteParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        HttpSession httpSession = request.getSession();
        List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");

        parametersList.remove(maintainerReportsForm.getParameterId().intValue());

        httpSession.setAttribute("PARAMETERS_LIST", parametersList);

        maintainerReportsForm.setParameterId(null);

        return mapping.findForward(PARAMETER_LIST);
    }

    public void getParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        List parametersList = (List) request.getSession().getAttribute("PARAMETERS_LIST");

        ParameterDTO parameterDTO = (ParameterDTO) parametersList.get(maintainerReportsForm.getParameterId().intValue());

        JSONObject jsonObject = JSONObject.fromObject(parameterDTO);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());
    }

    public ActionForward parameterList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        return mapping.findForward(PARAMETER_LIST);
    }


    public ActionForward refreshReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        List reportsList = DynamicReportDelegate.getInstance().getAllReport(buildContext(request));
        request.setAttribute("reportsList", reportsList);

        return mapping.findForward(REPORT_LIST);
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        DynamicReportDelegate.getInstance().deleteReport(buildContext(request), maintainerReportsForm.getReportId());

        return mapping.findForward(REPORT_LIST);
    }

    public void get(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        ReportDTO reportDTO = DynamicReportDelegate.getInstance().getReportById(buildContext(request), maintainerReportsForm.getReportId());

        //Se agregan los parametros del reporte
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("PARAMETERS_LIST", reportDTO.getParameterList());

        JSONObject jsonObject = JSONObject.fromObject(reportDTO);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());
    }

    public ActionForward clean(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

            maintainerReportsForm.setReportId(null);
            maintainerReportsForm.setAreaId(null);
            maintainerReportsForm.setConexionId(null);
            maintainerReportsForm.setSqlDescription(null);
            maintainerReportsForm.setSqlText(null);
            maintainerReportsForm.setParameterId(null);
        } catch (Exception e) {
            System.out.println("No se pudo limpiar el formulario");
        }

        return mapping.findForward(START_MAINTAINER_REPORT);
    }

}
