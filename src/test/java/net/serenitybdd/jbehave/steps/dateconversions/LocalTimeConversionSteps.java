package net.serenitybdd.jbehave.steps.dateconversions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.joda.time.LocalTime;

import java.util.List;

import static ch.lambdaj.Lambda.convert;
import static org.assertj.core.api.Assertions.assertThat;

public class LocalTimeConversionSteps {

    LocalTime localTimeParameter;
    List<LocalTime> localTimeParameterList;

    @Given("I want to convert string values to Joda LocalTime objects")
    public void converting_joda_datetime_objects() {}

    @When("I pass a LocalTime parameter a value of <value>")
    public void convert_localtime(LocalTime value) {
        localTimeParameter = value;
    }

    @Then("the parameter should be converted to a LocalTime with a value of <expected>")
    public void should_get_expected_localtime_value(String expected) {
        assertThat(localTimeParameter).isEqualTo(new LocalTime(expected));
    }

    @When("I pass a LocalTime List parameter a value of <value>")
    public void convert_localtimes(List<LocalTime>  value) {
        localTimeParameterList = value;
    }


    @Then("the parameter should be converted to a list of LocalTimes with values <expected>")
    public void should_get_expected_localtime_value_list(List<String> expected) {
        assertThat(localTimeParameterList).isEqualTo(convert(expected, StringDateConverters.toLocalTimes()));
    }


}
