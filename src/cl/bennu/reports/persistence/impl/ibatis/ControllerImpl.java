package cl.bennu.reports.persistence.impl.ibatis;

import cl.bennu.reports.commons.dto.ControllerDTO;
import cl.bennu.reports.persistence.IbatisUtils;
import cl.bennu.reports.persistence.dao.IControllerDAO;

import java.util.List;

public class ControllerImpl extends IbatisUtils implements IControllerDAO {

    public List getAll() throws Exception {
        return getSqlMapClient().queryForList("getAllController", null);
    }

    public ControllerDTO getById(Long id) throws Exception {
        return (ControllerDTO) getSqlMapClient().queryForObject("getControllerById", id);
    }

    public void insert(ControllerDTO controllerDTO) throws Exception {
        Long id = (Long)getSqlMapClient().insert("insertController", controllerDTO);
        controllerDTO.setId(id);
    }

    public void delete(Long id) throws Exception {
        getSqlMapClient().delete("deleteController", id);
    }

    public void update(ControllerDTO controllerDTO) throws Exception {
        getSqlMapClient().update("updateController", controllerDTO);
    }

}