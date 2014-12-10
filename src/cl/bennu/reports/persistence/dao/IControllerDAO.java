package cl.bennu.reports.persistence.dao;

import cl.bennu.reports.commons.dto.ControllerDTO;

import java.util.List;


public interface IControllerDAO {

    List getAll() throws Exception;

    ControllerDTO getById(Long id) throws Exception;

    void insert(ControllerDTO controllerDTO) throws Exception;

    void delete(Long id) throws Exception;

    void update(ControllerDTO controllerDTO) throws Exception;
}
