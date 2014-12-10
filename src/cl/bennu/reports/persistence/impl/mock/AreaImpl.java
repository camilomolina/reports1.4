package cl.bennu.reports.persistence.impl.mock;

import cl.bennu.reports.commons.dto.AreaDTO;
import cl.bennu.reports.persistence.dao.IAreaDAO;

import java.util.ArrayList;
import java.util.List;


public class AreaImpl implements IAreaDAO {

    public List getAll() throws Exception {
        List list = new ArrayList();


        return list;
    }

    public AreaDTO getById(Long areaId) throws Exception {
        return null;
    }

    public void insert(AreaDTO areaDTO) throws Exception {
        return;
    }

    public void update(AreaDTO areaDTO) throws Exception {
        return;
    }

    public void delete(Long areaId) throws Exception {
        return;
    }


}
