package net.serenitybdd.jbehave.steps.dateconversions;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.joda.time.DateTime;

import java.util.List;

import static ch.lambdaj.Lambda.convert;
import static org.assertj.core.api.Assertions.assertThat;

public class DateTimeConversionSteps {

    DateTime dateTimeParameter;
    List<DateTime> dateTimeParameterList;
    @Given("I want to convert a list of string values to Joda DateTime objects")
    @Alias("I want to convert string values to Joda DateTime objects")
    public void converting_joda_datetime_objects() {}

    @When("I pass a DateTime parameter a value of <value>")
    public void convert_datetime(DateTime value) {
        dateTimeParameter = value;
    }

    @Then("the parameter should be converted to a DateTime with a value of <expectedValue>")
    public void should_get_expected_value(String expectedValue) {
        assertThat(dateTimeParameter).isEqualTo(new DateTime(expectedValue));
    }

    @When("I pass a DateTime List parameter a value of <value>")
    public void convert_datetimes(List<DateTime>  value) {
        dateTimeParameterList = value;
    }

    @Then("the parameter should be converted to a list of DateTimes with values <expected>")
    public void thenTheParameterShouldBeConvertedToAListOfDateTimesWithValues(List<String> expected) {
        assertThat(dateTimeParameterList).isEqualTo(convert(expected, StringDateConverters.toDateTimes()));
    }
}
