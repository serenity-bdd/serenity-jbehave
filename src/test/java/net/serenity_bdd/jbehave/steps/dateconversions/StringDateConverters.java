package net.serenity_bdd.jbehave.steps.dateconversions;

import ch.lambdaj.function.convert.Converter;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.YearMonth;

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

    public static Converter<String, YearMonth> toYearMonths() {
        return new Converter<String,YearMonth>() {

            @Override
            public YearMonth convert(String from) {
                return new YearMonth(from);
            }
        };
    }

}
