package cl.bennu.reports.commons.dto;

import cl.bennu.reports.commons.dto.base.BaseDTO;
import cl.bennu.reports.commons.enums.ReportGenerateResponseEnum;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 14-11-13
 * Time: 11:22 AM
 */
public class LogDTO extends BaseDTO {

    private String name;
    private String user;
    private String host;
    private Date startSQL;
    private Date endSQL;
    private Date startReport;
    private Date endReport;
    private ReportGenerateResponseEnum reportGenerateResponseEnum;
    private String remark;
    private String sql;
    private String url;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartSQL() {
        return startSQL;
    }

    public void setStartSQL(Date startSQL) {
        this.startSQL = startSQL;
    }

    public Date getEndSQL() {
        return endSQL;
    }

    public void setEndSQL(Date endSQL) {
        this.endSQL = endSQL;
    }

    public Date getStartReport() {
        return startReport;
    }

    public void setStartReport(Date startReport) {
        this.startReport = startReport;
    }

    public Date getEndReport() {
        return endReport;
    }

    public void setEndReport(Date endReport) {
        this.endReport = endReport;
    }

    public ReportGenerateResponseEnum getReportGenerateResponseEnum() {
        return reportGenerateResponseEnum;
    }

    public void setReportGenerateResponseEnum(ReportGenerateResponseEnum reportGenerateResponseEnum) {
        this.reportGenerateResponseEnum = reportGenerateResponseEnum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
