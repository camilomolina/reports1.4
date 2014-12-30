package cl.bennu.reports.commons.enums;

import org.apache.commons.lang.enums.ValuedEnum;

import java.util.Iterator;
import java.util.List;

public class ParameterTypeEnum extends ValuedEnum {


    public static final ParameterTypeEnum ALPHANUMERIC = new ParameterTypeEnum("AlfaNumerico", 1);
    public static final ParameterTypeEnum DATE = new ParameterTypeEnum("Fecha", 2);
    public static final ParameterTypeEnum NUMERIC = new ParameterTypeEnum("Numerico", 3);
    public static final ParameterTypeEnum BOOLEAN = new ParameterTypeEnum("Boleano", 4);
    public static final ParameterTypeEnum DATE_RANGE = new ParameterTypeEnum("Rango de Fechas", 5);

    protected ParameterTypeEnum(String name, int value) {
        super(name, value);
    }

    public static ParameterTypeEnum valueOf(String name) {
        Object objectEnum = getEnum(ParameterTypeEnum.class, name);
        if (objectEnum == null) {
            throw new IllegalArgumentException(name + " cannot be translated to enum.");
        }
        return (ParameterTypeEnum) objectEnum;
    }

    public static ParameterTypeEnum valueOf(Long value) {
        if (value == null) {
            throw new IllegalArgumentException(value + " cannot be translated to enum.");
        }
        Object objectEnum = getEnum(ParameterTypeEnum.class, value.intValue());
        if (objectEnum == null) {
            throw new IllegalArgumentException(value + " cannot be translated to enum.");
        }
        return (ParameterTypeEnum) objectEnum;
    }

    public static List getEnumList() {
        return getEnumList(ParameterTypeEnum.class);
    }

    public final Long getId() {
        return new Long(getValue());
    }

    public final String getEnumName() {
        return getName();
    }

    public static Iterator iterator() {
        return iterator(ParameterTypeEnum.class);
    }
}
