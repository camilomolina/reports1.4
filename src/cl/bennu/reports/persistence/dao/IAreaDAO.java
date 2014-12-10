package cl.bennu.reports.persistence.dao;

import cl.bennu.reports.commons.dto.AreaDTO;

import java.util.List;

public interface IAreaDAO {

    List getAll() throws Exception;

    AreaDTO getById(Long id) throws Exception;

    void insert(AreaDTO areaDTO) throws Exception;

    void update(AreaDTO areaDTO) throws Exception;

    void delete(Long id) throws Exception;

}