package net.serenitybdd.jbehave.steps;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by john on 8/10/2014.
 */
public class StepsWithSharedVariables {
    int value = 0;

    @When("a field is initialized to $n")
    public void whenAFieldIsInitializedTo(int n) {
        value = n;
    }

    @Then("it should have a value of $n")
    public void thenItShouldHaveAValueOf(int n) {
        assertThat(value).isEqualTo(n);
    }

    @When("the same field is incremented")
    public void incrementField() {
        value++;
    }
}
