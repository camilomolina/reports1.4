package cl.bennu.reports.web.action;

import cl.bennu.reports.commons.dto.ParameterDTO;
import cl.bennu.reports.commons.dto.ReportDTO;
import cl.bennu.reports.commons.dto.base.ResponseJSON;
import cl.bennu.reports.commons.enums.DateFormatEnum;
import cl.bennu.reports.commons.enums.ParameterTypeEnum;
import cl.bennu.reports.web.delegate.DynamicReportDelegate;
import cl.bennu.reports.web.form.ReportForm;
import net.sf.json.JSONObject;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ReportAction extends BaseAction {

    private static final String START_REPORT = "startReport";

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReportForm reportForm = (ReportForm)form;

        ReportDTO reportDTO = DynamicReportDelegate.getInstance().getReport(buildContext(request), reportForm.getReport());
        reportForm.setReportDTO(reportDTO);

        return mapping.findForward(START_REPORT);
    }

    public void generateBase64(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReportForm reportForm = (ReportForm)form;
        ReportDTO reportDTO = reportForm.getReportDTO();

        StringBuffer messages = new StringBuffer();
        boolean generate = true;

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");

        Iterator parameterIter = IteratorUtils.getIterator(reportDTO.getParameterList());
        while(parameterIter.hasNext()) {
            ParameterDTO parameterDTO = (ParameterDTO) parameterIter.next();
            String val = request.getParameter("parameter_"+parameterDTO.getId());

            String valR1 = request.getParameter("parameter_"+parameterDTO.getId()+"_init");
            String valR2 = request.getParameter("parameter_"+parameterDTO.getId()+"_final");

            if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.ALPHANUMERIC)
                    || parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.ALPHANUMERIC_PLUS_LIKE)) {
                parameterDTO.setValue(val);
            } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.DATE)) {
                if (!val.trim().equals("")) {
                    if (DateFormatEnum.MMYYYY.getId().toString().equals(parameterDTO.getData2())) {
                        parameterDTO.setValue(simpleDateFormat1.parse("01/"+val));
                    } else if (DateFormatEnum.YYYY.getId().toString().equals(parameterDTO.getData2())) {
                        parameterDTO.setValue(simpleDateFormat1.parse("01/01/"+val));
                    } else {
                        parameterDTO.setValue(simpleDateFormat1.parse(val));
                    }
                }
            } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.NUMERIC)) {
                if (!val.trim().equals("")) {
                    parameterDTO.setValue(new Long(val));
                }
            } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.BOOLEAN)) {
                parameterDTO.setValue(val == null ? Boolean.FALSE : Boolean.TRUE);
            } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.DATE_RANGE)) {
                Date date1 = null;
                Date date2 = null;
                if (!valR1.trim().equals("")) {
                    if (DateFormatEnum.MMYYYY.getId().toString().equals(parameterDTO.getData2())) {
                        date1 = simpleDateFormat1.parse("01/"+valR1);
                    } else if (DateFormatEnum.YYYY.getId().toString().equals(parameterDTO.getData2())) {
                        date1 = simpleDateFormat1.parse("01/01/"+valR1);
                    } else {
                        date1 = simpleDateFormat1.parse(valR1);
                    }
                    parameterDTO.setValueR1(date1);
                }
                if (!valR2.trim().equals("")) {
                    if (DateFormatEnum.MMYYYY.getId().toString().equals(parameterDTO.getData2())) {
                        date2 = simpleDateFormat1.parse("01/"+valR2);
                    } else if (DateFormatEnum.YYYY.getId().toString().equals(parameterDTO.getData2())) {
                        date2 = simpleDateFormat1.parse("01/01/"+valR2);
                    } else {
                        date2 = simpleDateFormat1.parse(valR2);
                    }
                    parameterDTO.setValueR2(date2);
                }

                if (date1 != null && date2 != null && StringUtils.isNotBlank(parameterDTO.getData1())) {
                    long days = Long.parseLong(parameterDTO.getData1());

                    long diff = date2.getTime() - date1.getTime();
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    if (diffDays > days) {
                        generate = false;
                        // error en el rango de dias

                        messages.append("Error en el rango maximo de fechas para " + parameterDTO.getName());
                        messages.append("\n");
                    }
                }
            }
        }

        if (generate) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) DynamicReportDelegate.getInstance().generate(buildContext(request), reportDTO);

                request.getSession().setAttribute("SESSION_ReportGenerate", byteArrayOutputStream);

                ResponseJSON responseJSON = new ResponseJSON();
                responseJSON.setResponseType(new Long(1));
                responseJSON.setResponse(Boolean.TRUE);

                JSONObject jsonObject = JSONObject.fromObject(responseJSON);
                ServletOutputStream servletOutputStream = response.getOutputStream();
                servletOutputStream.write(jsonObject.toString().getBytes());
            } catch (Exception e) {
                ResponseJSON responseJSON = new ResponseJSON();
                responseJSON.setResponseType(new Long(2));
                responseJSON.setResponse(Boolean.FALSE);
                responseJSON.setMessage("Error con la generacion del reporte");

                JSONObject jsonObject = JSONObject.fromObject(responseJSON);
                ServletOutputStream servletOutputStream = response.getOutputStream();
                servletOutputStream.write(jsonObject.toString().getBytes());
            }

        } else {
            ResponseJSON responseJSON = new ResponseJSON();
            responseJSON.setResponseType(new Long(2));
            responseJSON.setResponse(Boolean.FALSE);
            responseJSON.setMessage(messages.toString());

            JSONObject jsonObject = JSONObject.fromObject(responseJSON);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.write(jsonObject.toString().getBytes());
        }
    }

    public void generate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReportForm reportForm = (ReportForm) form;
        ReportDTO reportDTO = reportForm.getReportDTO();

        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("application/vnd.ms-excel");

        response.addHeader("Content-Disposition", "attachment; filename=" + reportDTO.getName() + ".xls");

        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream)request.getSession().getAttribute("SESSION_ReportGenerate");
        request.getSession().removeAttribute("SESSION_ReportGenerate");

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(byteArrayOutputStream.toByteArray());

        outputStream.flush();
        outputStream.close();
    }
}