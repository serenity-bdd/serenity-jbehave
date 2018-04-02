package net.serenitybdd.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class YearMonthConverter extends ParameterConverters.AbstractParameterConverter<YearMonth> {
    public static final DateTimeFormatter MONTH_YEAR_FORMAT_WITH_DASH = DateTimeFormat.forPattern("MM-yyyy");
    public static final DateTimeFormatter MONTH_YEAR_FORMAT_WITH_SLASH = DateTimeFormat.forPattern("MM/yyyy");
    public static final DateTimeFormatter YEAR_MONTH_FORMAT_WITH_DASH = DateTimeFormat.forPattern("yyyy-MM");
    public static final DateTimeFormatter YEAR_MONTH_FORMAT_WITH_SLASH = DateTimeFormat.forPattern("yyyy/MM");


    @Override
    public YearMonth convertValue(String value, Type type) {
        DateTimeFormatter formatter = hasDash(value) ? getFormatterWithDash(value) : getFormatterWithSlash(value);
        return YearMonth.parse(value, formatter);
    }

    private DateTimeFormatter getFormatterWithSlash(String value) {
        return value.trim().charAt(2) == '/' ? MONTH_YEAR_FORMAT_WITH_SLASH : YEAR_MONTH_FORMAT_WITH_SLASH;
    }

    private DateTimeFormatter getFormatterWithDash(String value) {
        return value.trim().charAt(2) == '-' ? MONTH_YEAR_FORMAT_WITH_DASH : YEAR_MONTH_FORMAT_WITH_DASH;
    }

    private boolean hasDash(String value) {
        return value.indexOf('-') > -1;
    }
}
