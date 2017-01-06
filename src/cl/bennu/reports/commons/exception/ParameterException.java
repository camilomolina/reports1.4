package cl.bennu.reports.commons.exception;

public class ParameterException extends Exception {

    private String sql;

    public ParameterException(String sql, Throwable cause) {
        super(cause);
        this.sql = sql;
    }


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
