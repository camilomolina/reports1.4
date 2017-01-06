package cl.bennu.reports.persistence.impl.ibatis;

import cl.bennu.reports.commons.dto.LogDTO;
import cl.bennu.reports.persistence.IbatisUtils;
import cl.bennu.reports.persistence.dao.ILogDAO;


public class LogImpl extends IbatisUtils implements ILogDAO {


    public void insert(LogDTO logDTO) throws Exception {
        Long id = (Long) getSqlMapClient().insert("insertLog", logDTO);
        logDTO.setId(id);
    }

    public void update(LogDTO logDTO) throws Exception {
        getSqlMapClient().update("updateLog", logDTO);
    }


}
