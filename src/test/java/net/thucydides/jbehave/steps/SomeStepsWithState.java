package net.thucydides.jbehave.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class SomeStepsWithState {

    String field;

    @Given("I have a field")
    public void setupField() {
    }

    @When("I instantiate that field with value $value")
    public void instantiateFieldWith(String value) {
        this.field = value;

    }

    @Then("the field should be set to $value in the following steps")
    public void checkValueIsSet(String value) {
        assertThat(field, is(value));
    }

    @When("I instantiate do not that field")
    public void doNothing() {
    }

    @Then("the field should not be set")
    public void checkFieldIsNull() {
        assertThat(field, is(nullValue()));
    }
}
