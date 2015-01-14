package cl.bennu.reports.business;

import cl.bennu.reports.commons.dto.*;
import cl.bennu.reports.commons.dto.base.ContextDTO;
import cl.bennu.reports.persistence.dao.*;
import cl.bennu.reports.persistence.factory.AbstractFactory;
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
    private IParameterDAO parametroDAO;
    private IControllerDAO controladorDAO;
    private IParameterTypeDAO tipoParametroDAO;
    private IConexionDAO conexionDAO;
    private IReportDAO reporteDAO;

    private static final String FONT_NAME = "Verdana";

    private DynamicReportBusiness() {
        areaDAO = AbstractFactory.getAreaDAO();
        parametroDAO = AbstractFactory.getParameterDAO();
        controladorDAO = AbstractFactory.getControllerDAO();
        tipoParametroDAO = AbstractFactory.getParameterTypeDAO();
        reporteDAO = AbstractFactory.getReportDAO();
        conexionDAO = AbstractFactory.getConexionDAO();
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
        return parametroDAO.getAll();
    }

    public ParameterDTO getParameterById(ContextDTO contextDTO, Long parametroId) throws Exception {
        return parametroDAO.getById(parametroId);
    }

    public void saveParameter(ContextDTO contextDTO, ParameterDTO parameterDTO) throws Exception {
        if (parameterDTO.getId() == null) {
            parameterDTO.setCreate(new Date());
            parameterDTO.setCreateUser(contextDTO.getUser());

            parametroDAO.insert(parameterDTO);
        } else {
            parameterDTO.setUpdate(new Date());
            parameterDTO.setUpdateUser(contextDTO.getUser());

            parametroDAO.update(parameterDTO);
        }
    }

    public void deleteParameter(ContextDTO contextDTO, Long parameterId) throws Exception {
        parametroDAO.delete(parameterId);
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
            String pass = conexionDTO.getPass();
            conexionDTO.setPass(pass);

            conexionDAO.insert(conexionDTO);
        } else {
            conexionDTO.setUpdate(new Date());
            conexionDTO.setUpdateUser(contextDTO.getUser());
            conexionDAO.update(conexionDTO);
        }
    }

    public void saveReport(ContextDTO contextDTO, ReportDTO reportDTO) throws Exception {
        if (reportDTO.getId() == null) {
            reporteDAO.insert(reportDTO);
        } else {
            reporteDAO.update(reportDTO);
        }
        Long id = reportDTO.getId();

        Iterator iter = IteratorUtils.getIterator(reportDTO.getParameterList());

        ParameterDTO reporteParametro = new ParameterDTO();


        while (iter.hasNext()) {
            reporteParametro = (ParameterDTO) iter.next();
            reporteParametro.setReportId(id);
            System.out.println(reporteParametro.getId());
            System.out.println(reporteParametro.getName());
            //System.out.println(reporteParametro.getTypeName());
            System.out.println(reporteParametro.getRequired());
            saveParameter(contextDTO, reporteParametro);
        }
    }

    public List getAllReport(ContextDTO contextDTO) throws Exception {
        return reporteDAO.getAll();
    }

    public ReportDTO getReportById(ContextDTO contextDTO, Long reportId) throws Exception {
        return reporteDAO.getById(reportId);
    }

    public void deleteReport(ContextDTO contextDTO, Long reporteId) throws Exception {

        //before deleting the report parameters must be removed
        parametroDAO.deleteByReportId(reporteId);
        //now eliminate the report
        reporteDAO.delete(reporteId);
    }

    public ReportDTO getReport(ContextDTO contextDTO, String name) throws Exception {
        return reporteDAO.getByName(name);
    }

    public OutputStream generate(ContextDTO contextDTO, ReportDTO reportDTO) throws Exception {
        List list = reporteDAO.execute(reportDTO);

//        String reportTitle = reportDTO.getName();

        HSSFWorkbook workBook = new HSSFWorkbook();

        HSSFFont fontTitle = workBook.createFont();
        fontTitle.setColor(HSSFColor.BLUE_GREY.index);
        fontTitle.setFontHeightInPoints((short) 14);
        fontTitle.setFontName(FONT_NAME);

        HSSFFont fontHeader = workBook.createFont();
        fontHeader.setColor(HSSFColor.WHITE.index);
        fontHeader.setFontHeightInPoints((short) 8);
        fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontHeader.setFontName(FONT_NAME);

        HSSFFont fontNormal = workBook.createFont();
        fontNormal.setColor(HSSFColor.GREY_80_PERCENT.index);
        fontNormal.setFontHeightInPoints((short) 8);
        fontNormal.setFontName(FONT_NAME);


        HSSFCellStyle cellStyleTitle = workBook.createCellStyle();
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyleTitle.setFont(fontTitle);

        HSSFCellStyle cellStyleHeader = workBook.createCellStyle();
        cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyleHeader.setFont(fontHeader);
        cellStyleHeader.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
        cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleHeader.setBottomBorderColor(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setTopBorderColor(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setLeftBorderColor(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setRightBorderColor(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle cellStyleNormal = workBook.createCellStyle();
        cellStyleNormal.setFont(fontNormal);

        HSSFCellStyle cellStyleNumber = workBook.createCellStyle();
        cellStyleNumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleNumber.setFont(fontNormal);
        cellStyleNumber.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.0000"));

        HSSFCellStyle cellStyleDate = workBook.createCellStyle();
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
                while(iterReportElement.hasNext()) {
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
            while(iterReportElement.hasNext()) {
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
                    cell.setCellValue((Date)o);
                } else {
                    cell.setCellStyle(cellStyleNormal);
                    cell.setCellValue(o.toString());
                }

                j++;
            }

            i++;
        }

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workBook.write(os);

            return os;
        } catch (Exception e) {
            return null;
        }
    }
}