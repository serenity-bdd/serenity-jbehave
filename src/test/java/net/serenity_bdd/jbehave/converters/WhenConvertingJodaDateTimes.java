package net.serenity_bdd.jbehave.converters;

import net.serenity_bdd.jbehave.converters.DateTimeConverter;
import net.serenity_bdd.jbehave.converters.TimeConverter;
import net.serenity_bdd.jbehave.converters.YearMonthConverter;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.YearMonth;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class WhenConvertingJodaDateTimes {

    @Test
    public void should_convert_time_string_to_LocalTimes() {
        TimeConverter converter = new TimeConverter();
        LocalTime convertedTime = (LocalTime) converter.convertValue("18:12", LocalTime.class);

        assertThat(convertedTime.getHourOfDay()).isEqualTo(18);
        assertThat(convertedTime.getMinuteOfHour()).isEqualTo(12);
    }

    @Test
    public void should_convert_date_string_to_LocalTimes() {
        DateTimeConverter converter = new DateTimeConverter();
        DateTime convertedTime = (DateTime) converter.convertValue("10/04/1942", LocalTime.class);

        assertThat(convertedTime.getDayOfMonth()).isEqualTo(10);
        assertThat(convertedTime.getMonthOfYear()).isEqualTo(4);
        assertThat(convertedTime.getYear()).isEqualTo(1942);
    }

    @Test
    public void should_convert_date_string_with_dashes_to_LocalTimes() {
        DateTimeConverter converter = new DateTimeConverter();
        DateTime convertedTime = (DateTime) converter.convertValue("10-04-1942", LocalTime.class);

        assertThat(convertedTime.getDayOfMonth()).isEqualTo(10);
        assertThat(convertedTime.getMonthOfYear()).isEqualTo(4);
        assertThat(convertedTime.getYear()).isEqualTo(1942);
    }

    @Test
    public void should_detect_iso_date_format() {
        DateTimeConverter converter = new DateTimeConverter();
        DateTime convertedTime = (DateTime) converter.convertValue("1942-04-10", LocalTime.class);

        assertThat(convertedTime.getDayOfMonth()).isEqualTo(10);
        assertThat(convertedTime.getMonthOfYear()).isEqualTo(4);
        assertThat(convertedTime.getYear()).isEqualTo(1942);
    }

    @Test
    public void should_accept_YearMonth_values() {
        YearMonthConverter converter = new YearMonthConverter();
        assertThat(converter.accept(YearMonth.class)).isTrue();
    }

    @Test
    public void should_not_accept_non_YearMonth_value() {
        YearMonthConverter converter = new YearMonthConverter();
        assertThat(converter.accept(DateTime.class)).isFalse();
    }

    @Test
    public void should_detect_YearMonth_format() {
        YearMonthConverter converter = new YearMonthConverter();
        YearMonth convertedTime = (YearMonth) converter.convertValue("10-1942", YearMonth.class);

        assertThat(convertedTime.getMonthOfYear()).isEqualTo(10);
        assertThat(convertedTime.getYear()).isEqualTo(1942);
    }

    @Test
    public void should_detect_MonthYear_format() {
        YearMonthConverter converter = new YearMonthConverter();
        YearMonth convertedTime = (YearMonth) converter.convertValue("1942-10", YearMonth.class);

        assertThat(convertedTime.getMonthOfYear()).isEqualTo(10);
        assertThat(convertedTime.getYear()).isEqualTo(1942);
    }

    @Test
    public void should_detect_YearMonth_format_with_slash() {
        YearMonthConverter converter = new YearMonthConverter();
        YearMonth convertedTime = (YearMonth) converter.convertValue("10/1942", YearMonth.class);

        assertThat(convertedTime.getMonthOfYear()).isEqualTo(10);
        assertThat(convertedTime.getYear()).isEqualTo(1942);
    }


    @Test
    public void should_detect_MonthYear_format_with_slash() {
        YearMonthConverter converter = new YearMonthConverter();
        YearMonth convertedTime = (YearMonth) converter.convertValue("1942/10", YearMonth.class);

        assertThat(convertedTime.getMonthOfYear()).isEqualTo(10);
        assertThat(convertedTime.getYear()).isEqualTo(1942);
    }
}
