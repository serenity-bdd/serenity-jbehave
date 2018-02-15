package net.serenitybdd.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.LocalTime;

import java.lang.reflect.Type;

public class TimeConverter implements ParameterConverters.ParameterConverter {

        public boolean accept(Type type) {
            return type instanceof Class<?> && LocalTime.class.isAssignableFrom((Class<?>) type);
        }

        public Object convertValue(String value, Type type) {
            return LocalTime.parse(value);
        }
    }
