package net.serenity_bdd.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class YearMonthConverter implements ParameterConverters.ParameterConverter {
    public static final DateTimeFormatter MONTH_YEAR_FORMAT_WITH_DASH = DateTimeFormat.forPattern("MM-yyyy");
    public static final DateTimeFormatter MONTH_YEAR_FORMAT_WITH_SLASH = DateTimeFormat.forPattern("MM/yyyy");
    public static final DateTimeFormatter YEAR_MONTH_FORMAT_WITH_DASH = DateTimeFormat.forPattern("yyyy-MM");
    public static final DateTimeFormatter YEAR_MONTH_FORMAT_WITH_SLASH = DateTimeFormat.forPattern("yyyy/MM");


    @Override
    public boolean accept(Type type) {
        if (type instanceof Class<?>) {
            return YearMonth.class.isAssignableFrom((Class<?>) type);
        } else {
            return false;
        }

    }

    @Override
    public Object convertValue(String value, Type type) {
        if (thereIsADashIn(value)) {
            return parseWithDash(value);
        }
        return parseWithSlash(value);
    }

    private YearMonth parseWithSlash(String value) {
        if (value.trim().substring(2,3).equals("/")) {
            return YearMonth.parse(value, MONTH_YEAR_FORMAT_WITH_SLASH);
        } else {
            return YearMonth.parse(value, YEAR_MONTH_FORMAT_WITH_SLASH);
        }
    }

    private YearMonth parseWithDash(String value) {
        if (value.trim().substring(2,3).equals("-")) {
            return YearMonth.parse(value, MONTH_YEAR_FORMAT_WITH_DASH);
        } else {
            return YearMonth.parse(value, YEAR_MONTH_FORMAT_WITH_DASH);
        }
    }

    private boolean thereIsADashIn(String value) {
        return value.contains("-");
    }
}
