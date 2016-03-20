package cl.bennu.reports.commons.enums;

import org.apache.commons.lang.enums.ValuedEnum;

import java.util.Iterator;
import java.util.List;

public class DateFormatEnum extends ValuedEnum {


    public static final DateFormatEnum DDMMYYYY = new DateFormatEnum("dd/MM/yyyy", 1);
    public static final DateFormatEnum MMYYYY = new DateFormatEnum("MM/yyyy", 2);
    public static final DateFormatEnum YYYY = new DateFormatEnum("yyyy", 2);

    protected DateFormatEnum(String name, int value) {
        super(name, value);
    }

    public static DateFormatEnum valueOf(String name) {
        Object objectEnum = getEnum(DateFormatEnum.class, name);
        if (objectEnum == null) {
            throw new IllegalArgumentException(name + " cannot be translated to enum.");
        }
        return (DateFormatEnum) objectEnum;
    }

    public static DateFormatEnum valueOf(Long value) {
        if (value == null) {
            throw new IllegalArgumentException(value + " cannot be translated to enum.");
        }
        Object objectEnum = getEnum(DateFormatEnum.class, value.intValue());
        if (objectEnum == null) {
            throw new IllegalArgumentException(value + " cannot be translated to enum.");
        }
        return (DateFormatEnum) objectEnum;
    }

    public static List getEnumList() {
        return getEnumList(DateFormatEnum.class);
    }

    public final Long getId() {
        return new Long(getValue());
    }

    public final String getEnumName() {
        return getName();
    }

    public static Iterator iterator() {
        return iterator(DateFormatEnum.class);
    }
}
