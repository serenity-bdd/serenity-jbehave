package net.thucydides.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.LocalTime;

import java.lang.reflect.Type;

public class TimeConverter implements ParameterConverters.ParameterConverter {

        public boolean accept(Type type) {
            if (type instanceof Class<?>) {
                return LocalTime.class.isAssignableFrom((Class<?>) type);
            }
            return false;
        }

        public Object convertValue(String value, Type type) {
            return LocalTime.parse(value);
        }
    }