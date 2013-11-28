package net.thucydides.jbehave.steps.dateconversions;

import ch.lambdaj.function.convert.Converter;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class StringDateConverters {
    public static Converter<String, LocalTime> toLocalTimes() {
        return new Converter<String,LocalTime>() {

            @Override
            public LocalTime convert(String from) {
                return new LocalTime(from);
            }
        };
    }

    public static Converter<String, DateTime> toDateTimes() {
        return new Converter<String,DateTime>() {

            @Override
            public DateTime convert(String from) {
                return new DateTime(from);
            }
        };
    }
}
