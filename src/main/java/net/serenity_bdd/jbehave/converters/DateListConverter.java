package net.serenity_bdd.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.steps.ParameterConverters.trim;

public class DateListConverter implements ParameterConverters.ParameterConverter {

    public static final String DEFAULT_LIST_SEPARATOR = ",";

    private final ParameterConverters.DateConverter dateConverter;
    private final String valueSeparator;

    public DateListConverter() {
        this(DEFAULT_LIST_SEPARATOR);
    }

    /**
     * @param valueSeparator A regexp to use as list separate
     */
    public DateListConverter(String valueSeparator) {
        this.dateConverter = new ParameterConverters.DateConverter();
        this.valueSeparator = valueSeparator;
    }

    public boolean accept(Type type) {
        if (type instanceof ParameterizedType) {
            Type rawType = rawType(type);
            Type argumentType = argumentType(type);
            return List.class.isAssignableFrom((Class<?>) rawType) && dateConverter.accept(argumentType);
        }
        return false;
    }

    public Object convertValue(String value, Type type) {
        Type argumentType = argumentType(type);
        List<String> values = trim(asList(value.split(valueSeparator)));
        List<Date> dates = new ArrayList<Date>();
        for (String string : values) {
            dates.add((Date) dateConverter.convertValue(string, argumentType));
        }
        return dates;
    }

    private Type rawType(Type type) {
        return ((ParameterizedType) type).getRawType();
    }

    private Type argumentType(Type type) {
        return ((ParameterizedType) type).getActualTypeArguments()[0];
    }
}
