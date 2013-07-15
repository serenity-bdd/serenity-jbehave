package net.thucydides.jbehave;

import com.beust.jcommander.internal.Lists;
import net.thucydides.jbehave.converters.TimeConverter;
import net.thucydides.jbehave.converters.TimeListConverter;
import org.joda.time.LocalTime;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class WhenConvertingTimes {

    @Test
    public void should_convert_time_string_to_LocalTimes() {
        TimeConverter converter = new TimeConverter();
        LocalTime convertedTime = (LocalTime) converter.convertValue("18:12", LocalTime.class);

        assertThat(convertedTime.getHourOfDay()).isEqualTo(18);
        assertThat(convertedTime.getMinuteOfHour()).isEqualTo(12);

    }

}
