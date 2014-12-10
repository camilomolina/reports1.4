package cl.bennu.reports.persistence.impl.ibatis;

import cl.bennu.reports.commons.dto.ConexionDTO;
import cl.bennu.reports.persistence.IbatisUtils;
import cl.bennu.reports.persistence.dao.IConexionDAO;

import java.util.List;


public class ConexionImpl extends IbatisUtils implements IConexionDAO {


    public List getAll() throws Exception {
        return getSqlMapClient().queryForList("getAllConexion", null);
    }

    public List getAllSummary() throws Exception {
        return getSqlMapClient().queryForList("getAllConexionSummary", null);
    }

    public ConexionDTO getById(Long id) throws Exception {
        return (ConexionDTO) getSqlMapClient().queryForObject("getConexionById", id);
    }

    public void insert(ConexionDTO conexionDTO) throws Exception {
        Long id = (Long)getSqlMapClient().insert("insertConexion", conexionDTO);
        conexionDTO.setId(id);
    }

    public void update(ConexionDTO conexionDTO) throws Exception {
        getSqlMapClient().update("updateConexion", conexionDTO);
    }

    public void delete(Long id) throws Exception {
        getSqlMapClient().delete("deleteConexion", id);
    }


}