package cl.bennu.reports.business;

import cl.bennu.reports.commons.dto.*;
import cl.bennu.reports.commons.dto.base.ContextDTO;
import cl.bennu.reports.commons.enums.ParameterTypeEnum;
import cl.bennu.reports.commons.enums.ReportGenerateResponseEnum;
import cl.bennu.reports.commons.exception.DriverException;
import cl.bennu.reports.commons.exception.ExecuteException;
import cl.bennu.reports.commons.exception.ParameterException;
import cl.bennu.reports.persistence.dao.*;
import cl.bennu.reports.persistence.factory.AbstractFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

public class DynamicReportBusiness {

    private static final DynamicReportBusiness instance = new DynamicReportBusiness();

    private IAreaDAO areaDAO;
    private IParameterDAO parameterDAO;
    private IControllerDAO controladorDAO;
    private IParameterTypeDAO tipoParametroDAO;
    private IConexionDAO conexionDAO;
    private IReportDAO reportDAO;
    private ILogDAO logDAO;

    private DynamicReportBusiness() {
        areaDAO = AbstractFactory.getAreaDAO();
        parameterDAO = AbstractFactory.getParameterDAO();
        controladorDAO = AbstractFactory.getControllerDAO();
        tipoParametroDAO = AbstractFactory.getParameterTypeDAO();
        reportDAO = AbstractFactory.getReportDAO();
        conexionDAO = AbstractFactory.getConexionDAO();
        logDAO = AbstractFactory.getLogDAO();
    }

    public static DynamicReportBusiness getInstance() {
        return instance;
    }

    public List getAllArea(ContextDTO contextDTO) throws Exception {
        return areaDAO.getAll();
    }

    public AreaDTO getAreaById(ContextDTO contextDTO, Long areaId) throws Exception {
        return areaDAO.getById(areaId);
    }

    public void saveArea(ContextDTO contextDTO, AreaDTO areaDTO) throws Exception {
        if (areaDTO.getId() == null) {
            areaDTO.setCreate(new Date());
            areaDTO.setCreateUser(contextDTO.getUser());

            areaDAO.insert(areaDTO);
        } else {
            areaDTO.setUpdate(new Date());
            areaDTO.setUpdateUser(contextDTO.getUser());

            areaDAO.update(areaDTO);
        }
    }

    public List getAllController(ContextDTO contextDTO) throws Exception {
        return controladorDAO.getAll();
    }

    public ControllerDTO getControllerById(ContextDTO contextDTO, Long controladorId) throws Exception {
        return controladorDAO.getById(controladorId);
    }

    public void saveController(ContextDTO contextDTO, ControllerDTO controllerDTO) throws Exception {
        if (controllerDTO.getId() == null || controllerDTO.getId().equals(NumberUtils.LONG_ZERO)) {
            controllerDTO.setCreate(new Date());
            controllerDTO.setCreateUser(contextDTO.getUser());

            controladorDAO.insert(controllerDTO);
        } else {
            controllerDTO.setUpdate(new Date());
            controllerDTO.setUpdateUser(contextDTO.getUser());

            controladorDAO.update(controllerDTO);
        }
    }

    public void deleteController(ContextDTO contextDTO, Long controllerId) throws Exception {
        controladorDAO.delete(controllerId);
    }

    public void deleteArea(ContextDTO contextDTO, Long areaId) throws Exception {
        areaDAO.delete(areaId);
    }

    public List getAllParameter(ContextDTO contextDTO) throws Exception {
        return parameterDAO.getAll();
    }

    public ParameterDTO getParameterById(ContextDTO contextDTO, Long parametroId) throws Exception {
        return parameterDAO.getById(parametroId);
    }

    public void saveParameter(ContextDTO contextDTO, ParameterDTO parameterDTO) throws Exception {
        if (parameterDTO.getId() == null) {
            parameterDTO.setCreate(new Date());
            parameterDTO.setCreateUser(contextDTO.getUser());

            //parameterDAO.insert(parameterDTO);
        } else {
            parameterDTO.setUpdate(new Date());
            parameterDTO.setUpdateUser(contextDTO.getUser());

            //parameterDAO.update(parameterDTO);
        }
        parameterDAO.insert(parameterDTO);
    }

    public void deleteParameter(ContextDTO contextDTO, Long parameterId) throws Exception {
        parameterDAO.delete(parameterId);
    }

    public List getAllParameterType(ContextDTO contextDTO) throws Exception {
        return tipoParametroDAO.getAll();
    }

    public ParameterTypeDTO getParameterTypeById(ContextDTO contextDTO, Long parameterTypeId) throws Exception {
        return tipoParametroDAO.getById(parameterTypeId);
    }

    public void saveParameterType(ContextDTO contextDTO, ParameterTypeDTO parameterTypeDTO) throws Exception {
        if (parameterTypeDTO.getId() == null) {
            parameterTypeDTO.setCreate(new Date());
            parameterTypeDTO.setCreateUser(contextDTO.getUser());

            tipoParametroDAO.insert(parameterTypeDTO);
        } else {
            parameterTypeDTO.setUpdate(new Date());
            parameterTypeDTO.setUpdateUser(contextDTO.getUser());

            tipoParametroDAO.update(parameterTypeDTO);
        }
    }

    public void deleteParameterType(ContextDTO contextDTO, Long parameterTypeId) throws Exception {
        tipoParametroDAO.delete(parameterTypeId);
    }

    public List getAllConexion(ContextDTO contextDTO) throws Exception {
        return conexionDAO.getAll();
    }

    public List getAllConexionSummary(ContextDTO contextDTO) throws Exception {
        return conexionDAO.getAllSummary();
    }

    public ConexionDTO getConexionById(ContextDTO contextDTO, Long conexionId) throws Exception {
        return conexionDAO.getById(conexionId);
    }

    public void deleteConexion(ContextDTO contextDTO, Long conexionId) throws Exception {
        conexionDAO.delete(conexionId);
    }

    public void saveConexion(ContextDTO contextDTO, ConexionDTO conexionDTO) throws Exception {
        if (conexionDTO.getId() == null || conexionDTO.getId().equals(NumberUtils.LONG_ZERO)) {
            conexionDTO.setCreate(new Date());
            conexionDTO.setCreateUser(contextDTO.getUser());
            byte[] pass = conexionDTO.getPass().getBytes("UTF-8");
            conexionDTO.setPass(Base64.encodeBase64String(pass));
            conexionDAO.insert(conexionDTO);
        } else {
            conexionDTO.setUpdate(new Date());
            conexionDTO.setUpdateUser(contextDTO.getUser());
            byte[] pass = conexionDTO.getPass().getBytes("UTF-8");
            conexionDTO.setPass(Base64.encodeBase64String(pass));
            conexionDAO.update(conexionDTO);
        }
    }

    public void saveReport(ContextDTO contextDTO, ReportDTO reportDTO) throws Exception {
        try {
            if (reportDTO.getId() == null) {
                reportDAO.insert(reportDTO);
            } else {
                reportDAO.update(reportDTO);
            }
            Long id = reportDTO.getId();

            if (reportDTO.getId() != null) {
                parameterDAO.deleteByReportId(id);
            }

            Iterator iter = IteratorUtils.getIterator(reportDTO.getParameterList());
            ParameterDTO parameterDTO = null;
            while (iter.hasNext()) {
                parameterDTO = (ParameterDTO) iter.next();
                parameterDTO.setReportId(id);
                if (parameterDTO.getData1() == null) {
                    parameterDTO.setData1("");
                }
                if (parameterDTO.getData2() == null) {
                    parameterDTO.setData2("");
                }
                parameterDTO.setUpdate(new Date());
                parameterDTO.setUpdateUser(contextDTO.getUser());

                saveParameter(contextDTO, parameterDTO);
            }
        } catch (Exception e) {
            System.out.println("No se pudo ingresar el parametro");
        }
    }

    public List getAllReport(ContextDTO contextDTO) throws Exception {
        return reportDAO.getAll();
    }

    public ReportDTO getReportById(ContextDTO contextDTO, Long reportId) throws Exception {
        return reportDAO.getById(reportId);
    }

    public void deleteReport(ContextDTO contextDTO, Long reporteId) throws Exception {

        //before deleting the report parameters must be removed
        parameterDAO.deleteByReportId(reporteId);
        //now eliminate the report
        reportDAO.delete(reporteId);
    }

    public ReportDTO getReport(ContextDTO contextDTO, String name) throws Exception {
        return reportDAO.getByName(name);
    }

    public OutputStream generate(ContextDTO contextDTO, ReportDTO dto) throws Exception {
        ReportDTO reportDTO = reportDAO.getById(dto.getId());
        reportDTO.setParameterList(dto.getParameterList());
        ConexionDTO conexionDTO = getConexionById(contextDTO, reportDTO.getConexionId());

        String reportTitle = reportDTO.getName();
        Date now = new Date();

        LogDTO logDTO = new LogDTO();
        logDTO.setName(reportTitle);
        logDTO.setHost(contextDTO.getHost());
        logDTO.setUser(contextDTO.getUser());
        logDTO.setUrl(conexionDTO.getUrl());
        logDTO.setSql(reportDTO.getSql());
        logDTO.setReportGenerateResponseEnum(ReportGenerateResponseEnum.ERROR);

        logDTO.setStartSQL(now);
        logDAO.insert(logDTO);

        List list = null;
        try {
            list = reportDAO.execute(reportDTO, conexionDTO);
        } catch (DriverException e) {
            logDTO.setReportGenerateResponseEnum(ReportGenerateResponseEnum.UNSUPPORTED_DRIVER);
            logDTO.setRemark("Driver:" + conexionDTO.getName());
            logDAO.update(logDTO);
            throw e;
        } catch (ParameterException e) {
            logDTO.setReportGenerateResponseEnum(ReportGenerateResponseEnum.PARAMETER_ERROR);
            logDTO.setRemark("SQL:" + e.getSql());
            logDAO.update(logDTO);
            throw e;
        } catch (ExecuteException e) {
            logDTO.setReportGenerateResponseEnum(ReportGenerateResponseEnum.SQL_ERROR);
            logDTO.setRemark("SQL:" + e.getSql());
            logDAO.update(logDTO);
            throw e;
        }

        logDTO.setEndSQL(new Date());
        logDTO.setStartReport(new Date());
        logDAO.update(logDTO);

        try {
            HSSFWorkbook workBook = new HSSFWorkbook();

            HSSFFont fontTitle = workBook.createFont();
            fontTitle.setFontHeightInPoints((short) 14);
            fontTitle.setColor(HSSFColor.BLUE_GREY.index);
            fontTitle.setFontName(HSSFFont.FONT_ARIAL);

            HSSFFont fontHeader = workBook.createFont();
            fontHeader.setFontHeightInPoints((short) 8);
            fontHeader.setColor(HSSFColor.BLACK.index);
            fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFFont fontNormal = workBook.createFont();
            fontNormal.setColor(HSSFColor.BLACK.index);
            fontNormal.setFontHeightInPoints((short) 8);


            HSSFCellStyle cellStyleTitle = workBook.createCellStyle();
            cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            cellStyleTitle.setFont(fontTitle);

            HSSFCellStyle cellStyleHeader = workBook.createCellStyle();
            cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            //cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            //cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            //cellStyleHeader.setWrapText(false);
            //cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyleHeader.setFont(fontHeader);

            HSSFCellStyle cellStyleNormal = workBook.createCellStyle();
            //cellStyleNormal.setWrapText(false);
            //cellStyleNormal.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyleNormal.setFont(fontNormal);

            HSSFCellStyle cellStyleNumber = workBook.createCellStyle();
            //cellStyleNumber.setWrapText(true);
            cellStyleNumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyleNumber.setFont(fontNormal);
            cellStyleNumber.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.0000"));

            HSSFCellStyle cellStyleDate = workBook.createCellStyle();
            //cellStyleDate.setWrapText(true);
            cellStyleDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyleDate.setFont(fontNormal);
            cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

            HSSFSheet sheet = workBook.createSheet("Reporte");
            HSSFRow row;
            HSSFCell cell;

            sheet.setDefaultColumnWidth((short) 16);
            sheet.setDefaultRowHeight((short) 15);

            int i = 1;
            boolean header = true;
            Iterator iterReportList = IteratorUtils.getIterator(list);
            while (iterReportList.hasNext()) {
                Map map = (HashMap) iterReportList.next();
                Set set = map.keySet();

                int j = 0;

                // armamos la cabecera
                if (header) {
                    Iterator iterReportElement = IteratorUtils.getIterator(set);
                    while (iterReportElement.hasNext()) {
                        Object key = iterReportElement.next();
                        row = sheet.createRow(0);

                        cell = row.createCell((short) j);
                        cell.setCellStyle(cellStyleHeader);
                        cell.setCellValue(key.toString());

                        j++;
                    }

                    header = false;
                }

                row = sheet.createRow(i);

                j = 0;

                // armamos con los ressultados de la consulta
                Iterator iterReportElement = IteratorUtils.getIterator(set);
                while (iterReportElement.hasNext()) {
                    Object key = iterReportElement.next();
                    Object o = map.get(key);

                    cell = row.createCell((short) j);
                    if (o == null) {
                        cell.setCellStyle(cellStyleNormal);
                        cell.setCellValue("");
                    } else if (o instanceof String) {
                        cell.setCellStyle(cellStyleNormal);
                        cell.setCellValue(o.toString());
                    } else if (o instanceof Number) {
                        cell.setCellStyle(cellStyleNumber);
                        cell.setCellValue(Double.parseDouble(o.toString()));
                    } else if (o instanceof Date) {
                        cell.setCellStyle(cellStyleDate);
                        cell.setCellValue((Date) o);
                    } else {
                        cell.setCellStyle(cellStyleNormal);
                        cell.setCellValue(o.toString());
                    }

                    j++;
                }

                i++;
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workBook.write(os);

            Date end = new Date();

            if (now.getTime() + (60 * 1000 * 1) < end.getTime()) {
                logDTO.setReportGenerateResponseEnum(ReportGenerateResponseEnum.WARM);
            } else {
                logDTO.setReportGenerateResponseEnum(ReportGenerateResponseEnum.OK);
            }
            logDTO.setEndReport(end);
            logDAO.update(logDTO);

            return os;
        } catch (Exception e) {
            logDTO.setReportGenerateResponseEnum(ReportGenerateResponseEnum.ERROR_GENERATE);
            logDAO.update(logDTO);
            return null;
        }
    }
}