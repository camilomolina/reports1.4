package cl.bennu.reports.persistence.handler;

import cl.bennu.reports.commons.enums.ReportGenerateResponseEnum;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;

public class ReportGenerateResponseHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        parameterSetter.setInt(o != null ? ((ReportGenerateResponseEnum) o).getValue() : 0);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        return ReportGenerateResponseEnum.valueOf(new Long(resultGetter.getLong()));
    }

    public Object valueOf(String s) {
        return s;
    }
}
