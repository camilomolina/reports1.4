package cl.bennu.reports.persistence.dao;

import cl.bennu.reports.commons.dto.ConexionDTO;

import java.util.List;

public interface IConexionDAO {

    List getAll() throws Exception;

    List getAllSummary() throws Exception;

    ConexionDTO getById(Long id) throws Exception;

    void insert(ConexionDTO conexionDTO) throws Exception;

    void update(ConexionDTO conexionDTO) throws Exception;

    void delete(Long id) throws Exception;

}
