package net.serenitybdd.jbehave.steps.dateconversions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.joda.time.YearMonth;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class MonthYearConversionSteps {

    private YearMonth yearMonthParameter;
    private List<YearMonth> yearMonthList;

    @Given("I want to convert string values to Joda MonthYear objects")
    public void givenIWantToConvertStringValuesToJodaMonthYearObjects() {}

    @When("I pass a YearMonth parameter a value of <value>")
    public void whenIPassAMonthYearParameterAValueOf(YearMonth value) {
        yearMonthParameter = value;
    }

    @Then("the parameter should be converted to a YearMonth with a value of <expectedValue>")
    public void thenTheParameterShouldBeConvertedToAYearMonthWithAValueOf(String expectedValue) {
        YearMonth expectedYearMonthValue = new YearMonth(expectedValue);
        assertThat(yearMonthParameter).isEqualTo(expectedYearMonthValue);
    }

    @When("I pass a list of YearMonth parameter a value of <value>")
    public void passYearMonthParameterList(List<YearMonth> value) {
        this.yearMonthList = value;
    }

    @Then("the parameter should be converted to a list of YearMonth with a value of <expected>")
    public void convertToListOfYearMonths(List<String> expected) {
        assertThat(yearMonthList).isEqualTo(expected.stream().map(YearMonth::new).collect(toList()));
    }

}
