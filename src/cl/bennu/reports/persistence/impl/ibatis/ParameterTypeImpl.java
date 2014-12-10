package cl.bennu.reports.persistence.impl.ibatis;

import cl.bennu.reports.commons.dto.ParameterTypeDTO;
import cl.bennu.reports.persistence.IbatisUtils;
import cl.bennu.reports.persistence.dao.IParameterTypeDAO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Francisco
 * Date: 07-02-14
 * Time: 12:42 PM
 */
public class ParameterTypeImpl extends IbatisUtils implements IParameterTypeDAO {
    
    public List getAll() throws Exception {
        return getSqlMapClient().queryForList("getAllParameterType", null);
    }

    public ParameterTypeDTO getById(Long tipoParametroId) throws Exception {
        return (ParameterTypeDTO) getSqlMapClient().queryForObject("getParameterTypeById", tipoParametroId);
    }

    public void insert(ParameterTypeDTO parameterTypeDTO) throws Exception {
        Long id = (Long) getSqlMapClient().insert("insertParameterType", parameterTypeDTO);
        parameterTypeDTO.setId(id);
    }

    public void update(ParameterTypeDTO parameterTypeDTO) throws Exception {
        getSqlMapClient().update("updateParameterType", parameterTypeDTO);
    }

    public void delete(Long id) throws Exception {
        getSqlMapClient().delete("deleteParameterType", id);
    }


}
