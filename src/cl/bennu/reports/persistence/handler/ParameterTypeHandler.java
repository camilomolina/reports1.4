package cl.bennu.reports.persistence.handler;

import cl.bennu.reports.commons.enums.ParameterTypeEnum;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;

/**
 * Created by Mac on 19.10.2016.
 */
public class ParameterTypeHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        parameterSetter.setInt(o != null ? ((ParameterTypeEnum) o).getValue() : 0);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        return ParameterTypeEnum.valueOf(new Long(resultGetter.getLong()));
    }

    public Object valueOf(String s) {
        return s;
    }
}
