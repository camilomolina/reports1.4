package cl.bennu.reports.web.form;

import cl.bennu.reports.commons.dto.ReportDTO;

public class ReportForm extends BaseForm {
    private Long id;
    private String report;
    private ReportDTO reportDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public ReportDTO getReportDTO() {
        return reportDTO;
    }

    public void setReportDTO(ReportDTO reportDTO) {
        this.reportDTO = reportDTO;
    }
}
