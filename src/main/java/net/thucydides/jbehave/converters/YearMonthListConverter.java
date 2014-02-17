package net.thucydides.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.YearMonth;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.steps.ParameterConverters.trim;

public class YearMonthListConverter implements ParameterConverters.ParameterConverter {

    public static final String DEFAULT_LIST_SEPARATOR = ",";

    private final YearMonthConverter yearMonthConverter;
    private final String valueSeparator;

    public YearMonthListConverter() {
        this(DEFAULT_LIST_SEPARATOR);
    }

    /**
     * @param valueSeparator A regexp to use as list separate
     */
    public YearMonthListConverter(String valueSeparator) {
        this.yearMonthConverter = new YearMonthConverter();
        this.valueSeparator = valueSeparator;
    }

    public boolean accept(Type type) {
        if (type instanceof ParameterizedType) {
            Type rawType = rawType(type);
            Type argumentType = argumentType(type);
            return List.class.isAssignableFrom((Class<?>) rawType) && yearMonthConverter.accept(argumentType);
        }
        return false;
    }

    public Object convertValue(String value, Type type) {
        System.out.println(type);
        Type argumentType = argumentType(type);
        List<String> values = trim(asList(value.split(valueSeparator)));
        List<YearMonth> times = new ArrayList<YearMonth>();
        for (String string : values) {
            times.add((YearMonth) yearMonthConverter.convertValue(string, argumentType));
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