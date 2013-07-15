package net.thucydides.jbehave.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

public class ParameterizedSteps {

    Date date;
    List<Date> dates;

    DateTime dateTime;
    List<DateTime> dateTimes;

    LocalTime time;
    List<LocalTime> times;

    @Given("I have a date $date")
    public void singleDate(Date date) {
        this.date = date;
    }

    @Given("I have a list of dates $dateList")
    public void dateList(List<Date> dates) {
        this.dates = dates;
    }

    @Given("I have a joda date $date")
    public void singleJodaDate(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Given("I have a list of joda dates $dateList")
    public void jodaDateList(List<DateTime> dateTimes) {
        this.dateTimes = dateTimes;
    }


    @Given("I have a time $time")
    public void singleTime(LocalTime time) {
        this.time = time;
    }
    @Given("I have a list of times $times")
    public void listOfTimes(List<LocalTime> times) {
        this.times = times;
    }

    @When("I run the story")
    public void run() {}

    @Then("the parameters should be converted")
    public void parametersAreConverted() {
        assertNotNull(date);
        assertNotNull(dates);
        assertNotNull(dateTime);
        assertNotNull(dateTimes);
        assertNotNull(time);
        assertNotNull(times);
    }
}
