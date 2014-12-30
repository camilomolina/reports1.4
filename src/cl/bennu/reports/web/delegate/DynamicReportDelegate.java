package cl.bennu.reports.web.delegate;

import cl.bennu.reports.business.DynamicReportBusiness;
import cl.bennu.reports.commons.dto.*;
import cl.bennu.reports.commons.dto.base.ContextDTO;

import java.io.OutputStream;
import java.util.List;

public class DynamicReportDelegate {

    private static final DynamicReportDelegate instance = new DynamicReportDelegate();

    private DynamicReportDelegate() {
    }

    public static DynamicReportDelegate getInstance() {
        return instance;
    }

    public List getAllArea(ContextDTO contextDTO) throws Exception {
        return DynamicReportBusiness.getInstance().getAllArea(contextDTO);
    }

    public AreaDTO getAreaById(ContextDTO contextDTO, Long areaId) throws Exception {
        return DynamicReportBusiness.getInstance().getAreaById(contextDTO, areaId);
    }

    public void deleteArea(ContextDTO contextDTO, Long areaId) throws Exception {
        DynamicReportBusiness.getInstance().deleteArea(contextDTO, areaId);
    }

    public void saveArea(ContextDTO contextDTO, AreaDTO areaDTO) throws Exception {
        DynamicReportBusiness.getInstance().saveArea(contextDTO, areaDTO);
    }

    public List getAllController(ContextDTO contextDTO) throws Exception {
        return DynamicReportBusiness.getInstance().getAllController(contextDTO);
    }

    public ControllerDTO getControllerById(ContextDTO contextDTO, Long controllerId) throws Exception {
        return DynamicReportBusiness.getInstance().getControllerById(contextDTO, controllerId);
    }

    public void saveController(ContextDTO contextDTO, ControllerDTO controllerDTO) throws Exception {
        DynamicReportBusiness.getInstance().saveController(contextDTO, controllerDTO);
    }

    public void deleteController(ContextDTO contextDTO, Long controllerId) throws Exception {
        DynamicReportBusiness.getInstance().deleteController(contextDTO, controllerId);
    }

    public List getAllParameter(ContextDTO contextDTO) throws Exception {
        return DynamicReportBusiness.getInstance().getAllParameter(contextDTO);
    }

    public ParameterDTO getParameterById(ContextDTO contextDTO, Long parameterId) throws Exception {
        return DynamicReportBusiness.getInstance().getParameterById(contextDTO, parameterId);
    }

    public void saveParameter(ContextDTO contextDTO, ParameterDTO parameterDTO) throws Exception {
        DynamicReportBusiness.getInstance().saveParameter(contextDTO, parameterDTO);
    }

    public void deleteParameter(ContextDTO contextDTO, Long parametroId) throws Exception {
        DynamicReportBusiness.getInstance().deleteParameter(contextDTO, parametroId);
    }

    public List getAllParameterType(ContextDTO contextDTO) throws Exception {
        return DynamicReportBusiness.getInstance().getAllParameterType(contextDTO);
    }

    public ParameterTypeDTO getParameterTypeById(ContextDTO contextDTO, Long parameterTypeId) throws Exception {
        return DynamicReportBusiness.getInstance().getParameterTypeById(contextDTO, parameterTypeId);
    }

    public void saveParameterType(ContextDTO contextDTO, ParameterTypeDTO parameterTypeDTO) throws Exception {
        DynamicReportBusiness.getInstance().saveParameterType(contextDTO, parameterTypeDTO);
    }

    public void deleteParameterType(ContextDTO contextDTO, Long parameterTypeId) throws Exception {
        DynamicReportBusiness.getInstance().deleteParameterType(contextDTO, parameterTypeId);
    }

    public List getAllConexion(ContextDTO contextDTO) throws Exception {
        return DynamicReportBusiness.getInstance().getAllConexion(contextDTO);
    }

    public List getAllConexionSummary(ContextDTO contextDTO) throws Exception {
        return DynamicReportBusiness.getInstance().getAllConexionSummary(contextDTO);
    }

    public ConexionDTO getConexionById(ContextDTO contextDTO, Long conexionId) throws Exception {
        return DynamicReportBusiness.getInstance().getConexionById(contextDTO, conexionId);
    }

    public void deleteConexion(ContextDTO contextDTO, Long conexionId) throws Exception {
        DynamicReportBusiness.getInstance().deleteConexion(contextDTO, conexionId);
    }

    public void saveConexion(ContextDTO contextDTO, ConexionDTO conexionDTO) throws Exception {
        DynamicReportBusiness.getInstance().saveConexion(contextDTO, conexionDTO);
    }

    public void saveReport(ContextDTO contextDTO, ReportDTO reportDTO) throws Exception {
        DynamicReportBusiness.getInstance().saveReport(contextDTO, reportDTO);
    }

    public ReportDTO getReport(ContextDTO contextDTO, String name) throws Exception {
        return DynamicReportBusiness.getInstance().getReport(contextDTO, name);
    }

    public OutputStream generate(ContextDTO contextDTO, ReportDTO reportDTO) throws Exception {
        return DynamicReportBusiness.getInstance().generate(contextDTO, reportDTO);
    }

    public List getAllReport(ContextDTO contextDTO) throws Exception {
        return DynamicReportBusiness.getInstance().getAllReport(contextDTO);
    }

    public void deleteReport(ContextDTO contextDTO, Long reportId) throws Exception {
        DynamicReportBusiness.getInstance().deleteReport(contextDTO, reportId);
    }

    public ReportDTO getReportById(ContextDTO contextDTO, Long reportId) throws Exception {
        return DynamicReportBusiness.getInstance().getReportById(contextDTO,reportId);
    }
}
