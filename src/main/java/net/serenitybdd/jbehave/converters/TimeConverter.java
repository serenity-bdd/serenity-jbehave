package net.serenitybdd.jbehave.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.joda.time.LocalTime;

import java.lang.reflect.Type;

public class TimeConverter extends ParameterConverters.AbstractParameterConverter<LocalTime> {

        @Override
        public LocalTime convertValue(String value, Type type) {
            return LocalTime.parse(value);
        }
    }
