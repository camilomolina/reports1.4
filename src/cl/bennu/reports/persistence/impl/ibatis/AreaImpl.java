package cl.bennu.reports.persistence.impl.ibatis;

import cl.bennu.reports.commons.dto.AreaDTO;
import cl.bennu.reports.persistence.IbatisUtils;
import cl.bennu.reports.persistence.dao.IAreaDAO;

import java.util.List;

public class AreaImpl extends IbatisUtils implements IAreaDAO {

    public List getAll() throws Exception {
        return getSqlMapClient().queryForList("getAllArea", null);
    }

    public AreaDTO getById(Long id) throws Exception {
        return (AreaDTO) getSqlMapClient().queryForObject("getAreaById", id);
    }

    public void insert(AreaDTO areaDTO) throws Exception {
        Long id = (Long) getSqlMapClient().insert("insertArea", areaDTO);
        areaDTO.setId(id);
    }

    public void update(AreaDTO areaDTO) throws Exception {
        getSqlMapClient().update("updateArea", areaDTO);
    }

    public void delete(Long id) throws Exception {
        getSqlMapClient().delete("deleteArea", id);
    }
}
