package net.serenitybdd.jbehave.steps;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;

import static net.thucydides.core.steps.stepdata.StepData.withTestDataFrom;

public class SomeNonWebDataDrivenSteps {

    @Steps
    CheckValuesStep checkValueSteps;

    String dataSource;

    @Given("the data in $dataSource")
    public void givenTheNamesIn(String dataSource) {
        this.dataSource = dataSource;
    }

    @When("we enter this data")
    public void whenWeEnterThisData() {}

    @Then("the values should be correct")
    public void thenTheValuesShouldBeCorrect() throws IOException {
        withTestDataFrom(dataSource).run(checkValueSteps).checkValues();
    }

}
