package net.serenitybdd.jbehave.converters;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.util.List;

public class DateTimeConverter implements ParameterConverters.ParameterConverter {

    public static final DateTimeFormatter CONVENTIONAL_FORMAT = DateTimeFormat.forPattern("ddMMyyyy");
    public static final DateTimeFormatter ISO_FORMAT = DateTimeFormat.forPattern("yyyyMMdd");

    private DateTimeFormatter dateFormat;

    public DateTimeConverter() {
        this(null);
    }

    public DateTimeConverter(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    private DateTimeFormatter getBestFormatterFor(String value) {
        if (dateFormat == null) {
            if (startsWithYear(value)) {
                return ISO_FORMAT;
            } else {
                return CONVENTIONAL_FORMAT;
            }
        }
        return dateFormat;
    }

    private boolean startsWithYear(String value) {
        return getDateElementsFrom(value).get(0).length() == 4;
    }

    private List<String> getDateElementsFrom(String value) {
        return Splitter.on(CharMatcher.anyOf("-/")).splitToList(value);
    }

    public boolean accept(Type type) {
        if (type instanceof Class<?>) {
            return DateTime.class.isAssignableFrom((Class<?>) type);
        }
        return false;
    }

    public Object convertValue(String value, Type type) {
        DateTimeFormatter formatter = getBestFormatterFor(value);
        return DateTime.parse(normalized(value), formatter);
    }

    private String normalized(String value) {
        return value.replaceAll("/", "").replaceAll("-", "");
    }
}
