package cl.bennu.reports.persistence.dao;

import cl.bennu.reports.commons.dto.ParameterTypeDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Francisco
 * Date: 07-02-14
 * Time: 12:37 PM
 */
public interface IParameterTypeDAO {

    List getAll() throws Exception;

    ParameterTypeDTO getById(Long id) throws Exception;

    void insert(ParameterTypeDTO parameterTypeDTO) throws Exception;

    void update(ParameterTypeDTO parameterTypeDTO) throws Exception;

    void delete(Long id) throws Exception;
}
