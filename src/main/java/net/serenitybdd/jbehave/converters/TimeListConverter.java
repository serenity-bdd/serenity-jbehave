package net.serenitybdd.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.LocalTime;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.steps.ParameterConverters.trim;

public class TimeListConverter implements ParameterConverters.ParameterConverter {

    public static final String DEFAULT_LIST_SEPARATOR = ",";

    private final TimeConverter timeConverter;
    private final String valueSeparator;

    public TimeListConverter() {
        this(DEFAULT_LIST_SEPARATOR);
    }

    /**
     * @param valueSeparator A regexp to use as list separate
     */
    public TimeListConverter(String valueSeparator) {
        this.timeConverter = new TimeConverter();
        this.valueSeparator = valueSeparator;
    }

    @Override
    public boolean accept(Type type) {
        if (type instanceof ParameterizedType) {
            Type rawType = rawType(type);
            Type argumentType = argumentType(type);
            return List.class.isAssignableFrom((Class<?>) rawType) && timeConverter.accept(argumentType);
        }
        return false;
    }

    @Override
    public Object convertValue(String value, Type type) {
        Type argumentType = argumentType(type);
        List<String> values = trim(asList(value.split(valueSeparator)));
        List<LocalTime> times = new ArrayList<>();
        for (String string : values) {
            times.add((LocalTime) timeConverter.convertValue(string, argumentType));
        }
        return times;
    }

    private Type rawType(Type type) {
        return ((ParameterizedType) type).getRawType();
    }

    private Type argumentType(Type type) {
        return ((ParameterizedType) type).getActualTypeArguments()[0];
    }
}
