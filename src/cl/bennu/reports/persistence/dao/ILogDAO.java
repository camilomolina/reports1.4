package cl.bennu.reports.persistence.dao;

import cl.bennu.reports.commons.dto.LogDTO;
import cl.bennu.reports.commons.dto.ReportDTO;

/**
 * Created with IntelliJ IDEA.
 * User: Francisco
 * Date: 10-02-14
 * Time: 01:49 PM
 */
public interface ILogDAO {

    void insert(LogDTO logDTO) throws Exception;

    void update(LogDTO logDTO) throws Exception;

}
