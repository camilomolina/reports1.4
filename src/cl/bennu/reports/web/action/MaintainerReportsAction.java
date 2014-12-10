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

        List areaList = DynamicReportDelegate.getInstance().getAllArea(buildContext(request));
        maintainerReportsForm.setAreaList(areaList);

        List enumList = ParameterTypeEnum.getEnumList();
        maintainerReportsForm.setParameterTypeList(enumList);

        List reportsList = DynamicReportDelegate.getInstance().getAllReport(buildContext(request));
        maintainerReportsForm.setReportsList(reportsList);
        request.setAttribute("reportsList", reportsList);

        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("PARAMETERS_LIST");


        //Se setean los valores de parametros
        maintainerReportsForm.setParameterName("");
        maintainerReportsForm.setParameterRequired(Boolean.FALSE);

        //Se setean los valores del reporte
        maintainerReportsForm.setName("");
        maintainerReportsForm.setSqlDescription("");
        maintainerReportsForm.setSqlText("");


        return mapping.findForward(START_MAINTAINER_REPORT);
    }

    public ActionForward limpiar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        MaintainerReportsForm formulario = (MaintainerReportsForm) form;

        formulario.setName("");
        formulario.setAreaId(new Long(-1));
        formulario.setSqlDescription("");
        formulario.setSqlText("");
        formulario.setParameterName("");
        formulario.setParameterTypeId(new Long(-1));
        formulario.setParameterRequired(Boolean.valueOf(false));

        /* Se muestran los datos que estan en area
        for (int i=1;i<=6;i++){
            List li = DynamicReportDelegate.getInstance().getAllArea();
            AreaDTO prueba = (AreaDTO) li.get(i);
            //System.out.println(li.get(1));

            System.out.println(prueba.getId() + " " + prueba.getName());
        }
        */

        /* SE UTILIZÓ PARA PROBAR SI FUNCIONABA SELECT BY ID
        int prueba = 1;
        AreaDTO area =  DynamicReportDelegate.getInstance().getAreaById(new Long(prueba));
        System.out.println(area.getName());
        */

        /* SE UTILIZÓ PARA PROBAR SI FUNCIONABA INSERT
        AreaDTO areaNombre = new AreaDTO();
        areaNombre.setName("nuevaArea");
        DynamicReportDelegate.getInstance().saveArea(areaNombre);
        */

        /*SE UTILIZÓ PARA PROBAR SI FUNCIONABA UPDATE
        AreaDTO areaNombre = new AreaDTO();
        areaNombre.setId(new Long(7));
        areaNombre.setName("DeNuevoConElNombre");
        DynamicReportDelegate.getInstance().updateArea(areaNombre);
        */

        /*SE UTILI´ZO PARA PROBAR DELETE
        int idArea=11;
        DynamicReportDelegate.getInstance().deleteArea(new Long(idArea));
        */

        /*SE UTILIZA PARA PROBAR CON DATOS OBTENIDOS DEL FORMULARIO
        AreaDTO area= DynamicReportDelegate.getInstance().getArea(new Long(formulario.getArea()));
        System.out.println(area.getName());
        */

        /*SE PRUEBA PARAMETRO BY ID
        int parametroId=4;
        ParameterDTO parametro = DynamicReportDelegate.getInstance().getParameterById(new Long(parametroId));

        System.out.println("El parametro con ID numero " + parametroId + " es " + parametro);
        */

        /*SE MUESTRAN LOS DATOS DE TIPO PARAMETRO
        List li = DynamicReportDelegate.getInstance().getAllParameterType();
        ParameterTypeDTO tipoParametroDTO = (ParameterTypeDTO) li.get(1);

        System.out.println(tipoParametroDTO.getName());
        */

        /*SE INSERTA UN TIPO DE PARAMETRO
        ParameterTypeDTO tipoParametroDTO = new ParameterTypeDTO();
        tipoParametroDTO.setName("prueba");

        DynamicReportDelegate.getInstance().saveParameterType(tipoParametroDTO);
        */

        System.out.println("Limpie el formulario");
        return mapping.findForward("success");

    }

    public ActionForward addParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm maintainerReportsForm = (MaintainerReportsForm) form;

        //METODOS PARA INGRESAR DATOS

        ParameterDTO parameterDTO = new ParameterDTO();
        //parameterDTO.setIndex(maintainerReportsForm.getParameterId());
        System.out.println("Este es el indice: " + maintainerReportsForm.getParameterId());
        parameterDTO.setName(maintainerReportsForm.getParameterName());
        parameterDTO.setTypeName(maintainerReportsForm.getParameterTypeName());
        parameterDTO.setType(maintainerReportsForm.getParameterTypeId());
        //parameterDTO.setName(maintainerReportsForm.getParameterTypeName());
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
        MaintainerReportsForm formulario = (MaintainerReportsForm) form;

        System.out.println(formulario.getName());
        System.out.println(formulario.getAreaId());
        System.out.println(formulario.getSqlDescription());
        System.out.println(formulario.getSqlText());

        //llamo a metodos para guardar en db

       /*SE REVISA EL selectAllReporte
        List li = DynamicReportDelegate.getInstance().getAllReport();
        ReportDTO reporteDTO = (ReportDTO) li.get(0);

        System.out.println("El nombre del primer reporte es: " + reporteDTO.getName());
        */

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setName(formulario.getName());
        reportDTO.setAreaId(formulario.getAreaId());
        reportDTO.setDescription(formulario.getSqlDescription());
        reportDTO.setSql(formulario.getSqlText());
        reportDTO.setConexionId(new Long(1));
        reportDTO.setActive(Boolean.TRUE);

        HttpSession httpSession = request.getSession();
        List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");

        reportDTO.setParameterList(parametersList);

        DynamicReportDelegate.getInstance().saveReport(buildContext(request), reportDTO);

        /*
        ParameterDTO reporteParametro = new ParameterDTO();


        for (int i = 0; i < parametersList.size(); i++) {
            ParameterDTO reporteParametrfhho =(ParameterDTO) parametersList.get(i);

        }

        //int count=0;

        Iterator iter = IteratorUtils.getIterator(parametersList);
        while (iter.hasNext()){
            reporteParametro = (ParameterDTO) iter.next();

            //DynamicReportDelegate.getInstance().saveParameter(reporteParametro);
            System.out.println(reporteParametro.getId());
            System.out.println(reporteParametro.getName());
            System.out.println(reporteParametro.getTypeName());
            System.out.println(reporteParametro.getRequired());
            //count+=1;

        }

        */

        System.out.println("Guarde el reporte");


        return mapping.findForward("success");
    }

    public ActionForward buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("estoy en buscar");
        return mapping.findForward("search");
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

    public ActionForward editParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintainerReportsForm formulario = (MaintainerReportsForm) form;

        HttpSession httpSession = request.getSession();
        List parametersList = (List) httpSession.getAttribute("PARAMETERS_LIST");

        ParameterDTO parameterDTO;
        parameterDTO = (ParameterDTO) parametersList.get(formulario.getParameterId().intValue());


        JSONObject jsonObject = JSONObject.fromObject(parameterDTO);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(jsonObject.toString().getBytes());


        //System.out.println("Estoy editando los parametros");

        return null;
        //return mapping.findForward("success");

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

}
