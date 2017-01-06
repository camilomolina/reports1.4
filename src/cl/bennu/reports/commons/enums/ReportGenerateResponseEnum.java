package cl.bennu.reports.commons.enums;

import org.apache.commons.lang.enums.ValuedEnum;

import java.util.Iterator;
import java.util.List;

public class ReportGenerateResponseEnum extends ValuedEnum {


    public static final ReportGenerateResponseEnum OK = new ReportGenerateResponseEnum("Ok", 1);
    public static final ReportGenerateResponseEnum ERROR = new ReportGenerateResponseEnum("Error", 2);
    public static final ReportGenerateResponseEnum WARM = new ReportGenerateResponseEnum("Alertado", 3);
    public static final ReportGenerateResponseEnum SQL_ERROR = new ReportGenerateResponseEnum("Error en construccion de consulta", 4);
    public static final ReportGenerateResponseEnum PARAMETER_ERROR = new ReportGenerateResponseEnum("Error en parametros", 5);
    public static final ReportGenerateResponseEnum ERROR_GENERATE = new ReportGenerateResponseEnum("Error generando reporte", 6);
    public static final ReportGenerateResponseEnum UNSUPPORTED_DRIVER = new ReportGenerateResponseEnum("Driver no soportado", 7);

    protected ReportGenerateResponseEnum(String name, int value) {
        super(name, value);
    }

    public static ReportGenerateResponseEnum valueOf(String name) {
        Object objectEnum = getEnum(ReportGenerateResponseEnum.class, name);
        if (objectEnum == null) {
            throw new IllegalArgumentException(name + " cannot be translated to enum.");
        }
        return (ReportGenerateResponseEnum) objectEnum;
    }

    public static ReportGenerateResponseEnum valueOf(Long value) {
        if (value == null) {
            throw new IllegalArgumentException(value + " cannot be translated to enum.");
        }
        Object objectEnum = getEnum(ReportGenerateResponseEnum.class, value.intValue());
        if (objectEnum == null) {
            throw new IllegalArgumentException(value + " cannot be translated to enum.");
        }
        return (ReportGenerateResponseEnum) objectEnum;
    }

    public static List getEnumList() {
        return getEnumList(ReportGenerateResponseEnum.class);
    }

    public final Long getId() {
        return new Long(getValue());
    }

    public final String getEnumName() {
        return getName();
    }

    public static Iterator iterator() {
        return iterator(ReportGenerateResponseEnum.class);
    }
}
