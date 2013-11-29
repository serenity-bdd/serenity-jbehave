package net.thucydides.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class YearMonthConverter implements ParameterConverters.ParameterConverter {
    public static final DateTimeFormatter MONTH_YEAR_FORMAT_WITH_DASH = DateTimeFormat.forPattern("MM-yyyy");
    public static final DateTimeFormatter MONTH_YEAR_FORMAT_WITH_SLASH = DateTimeFormat.forPattern("MM/yyyy");


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
            return YearMonth.parse(value, MONTH_YEAR_FORMAT_WITH_DASH);
        }
        return YearMonth.parse(value, MONTH_YEAR_FORMAT_WITH_SLASH);
    }

    private boolean thereIsADashIn(String value) {
        return value.contains("-");
    }
}
