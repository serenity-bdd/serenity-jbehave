package net.serenitybdd.jbehave.smoketests;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.assertj.core.api.Assertions.assertThat;

public class LifecycleStepDefinitions {

    static class Calculations {

        int total = 0;

        @Step
        public void add(int amount) {
            total += amount;
        }

        @Step
        public void substract(int amount) {
            total -= amount;
        }

        public int getTotal() { return total; }

    }

    @Steps
    Calculations calculations;

    @Given("I have a calculator")
    public void givenIHaveACalculator() {
    }

    @Given("I add $amount")
    public void givenIAdd(int amount) {
        calculations.add(amount);
    }

    @When("I add $amount")
    public void whenIAdd(int amount) {
        calculations.add(amount);
    }

    @When("I substract $amount")
    public void whenISubstract(int amount) {
        calculations.substract(amount);
    }

    @Then("the total should be $total")
    public void thenTheTotalShouldBe(int total) {
        assertThat(calculations.total).isEqualTo(total);
    }
}
