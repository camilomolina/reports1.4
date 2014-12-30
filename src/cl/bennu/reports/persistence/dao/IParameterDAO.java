package cl.bennu.reports.persistence.dao;


import cl.bennu.reports.commons.dto.ParameterDTO;

import java.sql.SQLException;
import java.util.List;

public interface IParameterDAO {

    List getAll() throws Exception;

    ParameterDTO getById(Long id) throws Exception;

    void insert(ParameterDTO parameterDTO) throws Exception;

    void update(ParameterDTO parameterDTO) throws Exception;

    void delete(Long id) throws Exception;

    void deleteByReportId(Long reporteId) throws SQLException;

}
