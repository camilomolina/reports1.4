package cl.bennu.reports.persistence.dao;

import cl.bennu.reports.commons.dto.ConexionDTO;
import cl.bennu.reports.commons.dto.ReportDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Francisco
 * Date: 10-02-14
 * Time: 01:49 PM
 */
public interface IReportDAO {

    List getAll() throws Exception;

    ReportDTO getById(Long id) throws Exception;

    ReportDTO getByName(String name) throws Exception;

    void insert(ReportDTO reportDTO) throws Exception;

    void update(ReportDTO reportDTO) throws Exception;

    void delete(Long id) throws Exception;

    List execute(ReportDTO reportDTO, ConexionDTO conexionDTO) throws Exception;

}
