package cl.bennu.reports.persistence;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 14-11-13
 * Time: 11:39 AM
 */
public abstract class IbatisUtils {
    private static final SqlMapClient sqlMapClient;

    static {
        SqlMapClient sqlMapClient1;
        String resource = "cl/bennu/reports/persistence/config/sqlMapConfig.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            sqlMapClient1 = SqlMapClientBuilder.buildSqlMapClient(reader);
        } catch (IOException e) {
            sqlMapClient1 = null;
            e.printStackTrace();
        }
        sqlMapClient = sqlMapClient1;
    }

    public static SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
}
