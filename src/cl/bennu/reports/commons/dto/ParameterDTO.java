package cl.bennu.reports.commons.dto;

import cl.bennu.reports.commons.dto.base.BaseDTO;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 14-11-13
 * Time: 11:22 AM
 */
public class ParameterDTO extends BaseDTO {

    private Long reportId;
    private String name;
    private Long type;
    private String typeName;
    private Boolean required;
    private Boolean like;

    // valor ingresado
    private Object value;
    private Object valueR1;
    private Object valueR2;

    private String data1;
    private String data2;

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public Object getValueR1() {
        return valueR1;
    }

    public void setValueR1(Object valueR1) {
        this.valueR1 = valueR1;
    }

    public Object getValueR2() {
        return valueR2;
    }

    public void setValueR2(Object valueR2) {
        this.valueR2 = valueR2;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean requiered) {
        this.required = requiered;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long idReport) {
        this.reportId = idReport;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
