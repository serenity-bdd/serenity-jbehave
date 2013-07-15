package net.thucydides.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateTimeConverter implements ParameterConverters.ParameterConverter {

    public static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");

    private final DateTimeFormatter dateFormat;

    public DateTimeConverter() {
        this(DEFAULT_FORMAT);
    }

    public DateTimeConverter(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean accept(Type type) {
            if (type instanceof Class<?>) {
                return DateTime.class.isAssignableFrom((Class<?>) type);
            }
            return false;
        }

        public Object convertValue(String value, Type type) {
            return DateTime.parse(value, dateFormat);
        }
    }