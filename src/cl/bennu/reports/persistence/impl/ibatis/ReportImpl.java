package cl.bennu.reports.persistence.impl.ibatis;

import cl.bennu.reports.commons.dto.ConexionDTO;
import cl.bennu.reports.commons.dto.ParameterDTO;
import cl.bennu.reports.commons.dto.ReportDTO;
import cl.bennu.reports.commons.enums.ParameterTypeEnum;
import cl.bennu.reports.commons.exception.DriverException;
import cl.bennu.reports.commons.exception.ExecuteException;
import cl.bennu.reports.commons.exception.ParameterException;
import cl.bennu.reports.persistence.IbatisUtils;
import cl.bennu.reports.persistence.dao.IReportDAO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Francisco
 * Date: 10-02-14
 * Time: 01:52 PM
 */
public class ReportImpl extends IbatisUtils implements IReportDAO {

    public List getAll() throws Exception {
        return getSqlMapClient().queryForList("getAllReport", null);
    }

    public ReportDTO getById(Long id) throws Exception {
        return (ReportDTO) getSqlMapClient().queryForObject("getReportById", id);
    }

    public ReportDTO getByName(String name) throws Exception {
        return (ReportDTO) getSqlMapClient().queryForObject("getReportByName", name);
    }

    public void insert(ReportDTO reportDTO) throws Exception {
        Long id = (Long) getSqlMapClient().insert("insertReport", reportDTO);
        reportDTO.setId(id);
    }

    public void update(ReportDTO reportDTO) throws Exception {
        getSqlMapClient().update("updateReport", reportDTO);
    }

    public void delete(Long id) throws Exception {
        getSqlMapClient().delete("deleteReport", id);
    }

    public List execute(ReportDTO reportDTO, ConexionDTO conexionDTO) throws Exception {
        System.out.println("-- REPORTS14 -- : Ejecutando reporte " + reportDTO.getDescription());

        String driverBD = "";
        String url = conexionDTO.getUrl();
        String user = conexionDTO.getUser();
        String pass = new String(Base64.decodeBase64(conexionDTO.getPass()));

        //Connection connection = getSqlMapClient().getDataSource().getConnection();
        if (conexionDTO.getControllerDTO().getId().equals(new Long(1))) {
            driverBD = "org.postgresql.Driver";
        } else if (conexionDTO.getControllerDTO().getId().equals(new Long(2))) {
            driverBD = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if (conexionDTO.getControllerDTO().getId().equals(new Long(3))) {
            driverBD = "com.mysql.jdbc.Driver";
        } else if (conexionDTO.getControllerDTO().getId().equals(new Long(4))) {
            driverBD = "oracle.jdbc.driver.OracleDriver";
        } else if (conexionDTO.getControllerDTO().getId().equals(new Long(5))) {
            driverBD = "net.sourceforge.jtds.jdbcx.JtdsDataSource";
        }

        Connection connection = null;
        try {
            Class.forName(driverBD);
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new DriverException(e);
        }
        String sqlF = "";
        PreparedStatement preparedStatement;
        try {
            // filtramos opcionales
            String sqlWithParameter = filterQueryForOptional(reportDTO);

            // creamos lista con parametros que encontramos y filtramos la query
            List parameters = new ArrayList();
            sqlWithParameter = filterQueryForParameters(sqlWithParameter, parameters);

            sqlF = sqlWithParameter;
            preparedStatement = connection.prepareStatement(sqlWithParameter);

            int i = 1;

            // iteramos los parametros encontrados
            Iterator parameterIter = parameters.iterator();
            while (parameterIter.hasNext()) {
                String parameter = (String) parameterIter.next();
                Iterator parameterReportIter = IteratorUtils.getIterator(reportDTO.getParameterList());

                while (parameterReportIter.hasNext()) {
                    // buscamos en lso parametros del reporte para optener el dato ingresado en el formulario
                    ParameterDTO parameterDTO = (ParameterDTO) parameterReportIter.next();

                    if (parameter.equalsIgnoreCase(parameterDTO.getName())) {
                        if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.ALPHANUMERIC)) {
                            preparedStatement.setString(i, parameterDTO.getValue().toString());
                        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.ALPHANUMERIC_PLUS_LIKE)) {
                            preparedStatement.setString(i, "%" + parameterDTO.getValue().toString() + "%");
                        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.DATE)) {
                            preparedStatement.setDate(i, new java.sql.Date(((Date) parameterDTO.getValue()).getTime()));
                        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.NUMERIC)) {
                            preparedStatement.setLong(i, Long.parseLong(parameterDTO.getValue().toString()));
                        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.BOOLEAN)) {
                            preparedStatement.setBoolean(i, ((Boolean) parameterDTO.getValue()).booleanValue());
                        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.DATE_RANGE)) {
                            preparedStatement.setDate(i, new java.sql.Date(((Date) parameterDTO.getValueR1()).getTime()));
                            i++;
                            preparedStatement.setDate(i, new java.sql.Date(((Date) parameterDTO.getValueR2()).getTime()));
                        }
                    }
                }

                i++;
            }
        } catch (Exception e) {
            throw new ParameterException(sqlF, e);
        }

        List result;
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            //System.out.println("-- REPORTS14 -- : Termino de ejecucion " + reportDTO.getDescription());

            result = new ArrayList();

            // retornamos la informacion en un hashmap ordenado
            int column;
            int top = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                Map map = new LinkedHashMap();

                boolean forward = true;
                column = 1;
                while (forward) {
                    map.put(resultSetMetaData.getColumnName(column), resultSet.getObject(column));
                    column++;

                    if (column > top) {
                        forward = false;
                    }
                }
                result.add(map);
            }

            // cerrando db
            try {
                resultSet.close();
                connection.close();
            } catch (Exception e){}
        } catch (Exception e) {
            throw new ExecuteException(sqlF, e);
        }

        System.out.println("-- REPORTS14 -- : Cierra conexion DB " + conexionDTO.getName());

        return result;
    }

    private String filterQueryForOptional(ReportDTO reportDTO) {
        String sql = reportDTO.getSql() + "\n";
        String sqlWithParameter = "";

        boolean ini = true;
        Iterator parameterOpReportIter = IteratorUtils.getIterator(reportDTO.getParameterList());

        while (parameterOpReportIter.hasNext()) {
            ParameterDTO parameterDTO = (ParameterDTO) parameterOpReportIter.next();
            if (!BooleanUtils.isTrue(parameterDTO.getRequired())) {
                String[] sqlO;
                if (ini) {
                    // primer caso toma el sql original
                    sqlO = sql.split("\\$O\\{" + parameterDTO.getName() + "\\}");
                    ini = false;
                } else {
                    // se basa en el sql que se va modificando segun los parametros opcionales
                    sqlO = sqlWithParameter.split("\\$O\\{" + parameterDTO.getName() + "\\}");
                }
                int f = sqlO.length;

                // se verifica si tiene algun dato agregado el parametro opcional
                // en caso de no tener un parametro, se elimina de la query
                if (withData(parameterDTO)) {
                    sqlWithParameter = "";
                    // con datos el parametro opcional, se eliminan los tag y se mantiene la consulta
                    for (int i = 0; i < sqlO.length; i++) {
                        String s = sqlO[i];
                        sqlWithParameter += s;
                    }
                } else {
                    // se elimina la consulta del parametro opcional
                    sqlWithParameter = sqlO[0] + sqlO[f - 1];
                }
            }
        }

        // sin opcionales, mantiene consulta
        if (ini) {
            sqlWithParameter = sql;
        }

        return sqlWithParameter;
    }

    private String filterQueryForParameters(String sqlWithParameter, List parameters) {
        String sqlWithParameterTmp = sqlWithParameter;
        String[] sqlP = sqlWithParameter.split("\\$P\\{");

        sqlWithParameter = "";
        for (int i = 0; i < sqlP.length; i++) {
            String s = sqlP[i];

            int top = s.indexOf("}");
            if (top != -1) {
                // parametro encontrado
                sqlWithParameter += s.substring(top + 1, s.length()) + "? ";

                // lista de parametros para luego cambiarlas por los valores ingresados en el formulario
                String param = s.substring(0, top);
                if (!parameters.contains(param)) {
                    parameters.add(param);
                }
            } else {
                // final de parametro y agrega ?
                sqlWithParameter += s + "? ";
            }
        }

        // si no hay parametros se deja la consulta original
        if (parameters.isEmpty()) {
            sqlWithParameter = sqlWithParameterTmp;
        } else {
            // elimina un ? adicional
            sqlWithParameter = sqlWithParameter.substring(0, sqlWithParameter.length() - 2);
        }

        return sqlWithParameter;
    }

    private boolean withData(ParameterDTO parameterDTO) {
        if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.ALPHANUMERIC)) {
            return parameterDTO.getValue() != null && !StringUtils.isBlank(parameterDTO.getValue().toString());
        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.ALPHANUMERIC_PLUS_LIKE)) {
            return parameterDTO.getValue() != null && !StringUtils.isBlank(parameterDTO.getValue().toString());
        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.DATE)) {
            return parameterDTO.getValue() != null;
        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.NUMERIC)) {
            return parameterDTO.getValue() != null;
        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.BOOLEAN)) {
            return parameterDTO.getValue() != null && BooleanUtils.isTrue((Boolean)parameterDTO.getValue());
        } else if (parameterDTO.getParameterTypeEnum().equals(ParameterTypeEnum.DATE_RANGE)) {
            return parameterDTO.getValueR1() != null && parameterDTO.getValueR2() != null;
        }

        return false;
    }

}
