package smoketests.stepdefinitions;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.*;

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

    static class SelfishCalculations {

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

    @Steps(shared = true)
    Calculations calculations;

    @Steps
    SelfishCalculations selfishCalculations;

    @Given("I have a calculator")
    public void givenIHaveACalculator() {
        assertThat(calculations).isNotNull();
    }

    @AfterScenario
    public void afterScenario() {
        System.out.println("After scenario: " + calculations + "=>" + calculations.total);
    }

    @AfterStory
    public void afterStory() {
        System.out.println("After story: " + calculations + "=>" + calculations.total);
    }

    @AfterStories
    public void afterStories() {
        System.out.println("After stories: " + calculations + "=>" + calculations.total);
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

    @Then("the total should not be zero")
    public void thenTheTotalShouldNotBeZero() {
        assertThat(calculations.total).isNotZero();
    }

}
