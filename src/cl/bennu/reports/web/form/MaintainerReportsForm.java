package cl.bennu.reports.web.form;

import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Francisco
 * Date: 29-01-14
 * Time: 11:37 AM
 */
public class MaintainerReportsForm extends ActionForm {
    private String method;
    private String name;
    private Long areaId;
    private String sqlDescription;
    private String sqlText;
    private Boolean csv;
    private String parameterName;
    private Long parameterTypeId;
    private Long dateFormatId;
    private String parameterTypeName;
    private Boolean parameterRequired;
    private List areaList;
    private List parameterTypeList;
    private List dateFormatList;
    private Integer parameterId;
    private List reportsList;
    private Long reportId;
    private List conexionList;
    private Long conexionId;

    public Long getDateFormatId() {
        return dateFormatId;
    }

    public void setDateFormatId(Long dateFormatId) {
        this.dateFormatId = dateFormatId;
    }

    public List getDateFormatList() {
        return dateFormatList;
    }

    public void setDateFormatList(List dateFormatList) {
        this.dateFormatList = dateFormatList;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getSqlDescription() {
        return sqlDescription;
    }

    public void setSqlDescription(String sqlDescription) {
        this.sqlDescription = sqlDescription;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Long getParameterTypeId() {
        return parameterTypeId;
    }

    public void setParameterTypeId(Long parameterTypeId) {
        this.parameterTypeId = parameterTypeId;
    }

    public Boolean getParameterRequired() {
        return parameterRequired;
    }

    public void setParameterRequired(Boolean parameterRequired) {
        this.parameterRequired = parameterRequired;
    }

    public List getAreaList() {
        return areaList;
    }

    public void setAreaList(List areaList) {
        this.areaList = areaList;
    }

    public List getParameterTypeList() {
        return parameterTypeList;
    }

    public void setParameterTypeList(List parameterTypeList) {
        this.parameterTypeList = parameterTypeList;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterTypeName() {
        return parameterTypeName;
    }

    public void setParameterTypeName(String parameterTypeName) {
        this.parameterTypeName = parameterTypeName;
    }

    public List getReportsList() {
        return reportsList;
    }

    public void setReportsList(List reportsList) {
        this.reportsList = reportsList;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public List getConexionList() { return conexionList; }

    public void setConexionList(List conexionList) { this.conexionList = conexionList; }

    public Long getConexionId() {
        return conexionId;
    }

    public void setConexionId(Long conexionId) {
        this.conexionId = conexionId;
    }

    public Boolean getCsv() {
        return csv;
    }

    public void setCsv(Boolean csv) {
        this.csv = csv;
    }
}
