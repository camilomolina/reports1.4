package cl.bennu.reports.persistence.impl.ibatis;

import cl.bennu.reports.commons.dto.ParameterDTO;
import cl.bennu.reports.persistence.IbatisUtils;
import cl.bennu.reports.persistence.dao.IParameterDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Francisco
 * Date: 06-02-14
 * Time: 01:28 PM
 */
public class ParameterImpl extends IbatisUtils implements IParameterDAO {
    
    public List getAll() throws Exception {
        return getSqlMapClient().queryForList("getAllParameter", null);
    }

    public ParameterDTO getById(Long id) throws Exception {
        return (ParameterDTO) getSqlMapClient().queryForObject("getParameterById", id);
    }

    public void insert(ParameterDTO parameterDTO) throws Exception {
        Long id = (Long) getSqlMapClient().insert("insertParameter", parameterDTO);
        parameterDTO.setId(id);
    }

    public void update(ParameterDTO parameterDTO) throws Exception {
        getSqlMapClient().update("updateParameter", parameterDTO);
    }

    public void delete(Long id) throws Exception {
        getSqlMapClient().delete("deleteParameter", id);
    }

    public void deleteByReportId(Long id) throws SQLException {
        getSqlMapClient().delete("deleteParameterByReportId", id);
    }

}
