package cl.bennu.reports.commons.dto;

import cl.bennu.reports.commons.dto.base.BaseDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 14-11-13
 * Time: 11:22 AM
 */
public class ReportDTO extends BaseDTO {

    private String name;
    private Long areaId;
    private String description;
    private String sql;
    private List parameterList;
    private Long conexionId;
    private Boolean active;
    private Boolean csv;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List getParameterList() {
        return parameterList;
    }

    public void setParameterList(List parameterList) {
        this.parameterList = parameterList;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

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
