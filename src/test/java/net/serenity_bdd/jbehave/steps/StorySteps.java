package net.serenity_bdd.jbehave.steps;

import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.StepEventBus;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;


public class StorySteps {

    @Steps
    SomeThucydidesSteps steps;

    @Given("I have a failing precondition")
    public void failAPrecondition() {
        steps.failedAssumption();
    }

    @Given("I have a passing precondition")
    public void passAPrecondition() {
        assumeTrue(true);
    }

    @Given("a step that is executed before each scenario")
    public void beforeEachScenario() {
        System.out.println("Before each scenario");
    }

    @Given("I have an implemented JBehave scenario")
    public void givenIHaveAnImplementedJBehaveScenario() {
        assert steps != null;
    }

    @Given("I have a badly implemented JBehave scenario")
    public void givenIHaveABadlyImplementedJBehaveScenario() {
    }

    int counter = 0;

    @Given("the scenario works")
    public void givenTheScenarioWorks() {
    }

    @When("I run the scenario")
    public void whenIRunTheScenario() {
        counter++;
    }

    @Then("I should get a successful result")
    public void thenIShouldGetASuccessfulResult() {
        assertThat(counter).isEqualTo(1);
    }

    @Given("the scenario fails")
    public void givenTheScenarioFails() {
        System.out.println("Deliberately failing a scenario.");
        assertThat(true).isEqualTo(false);
    }

    @Given("the scenario contains an error")
    public void givenTheScenarioHasAnError() {
        System.out.println("Deliberate error");
        String s = null;
        s.length();
    }

    @Then("I should get a failed result")
    public void thenIShouldGetAFailedResult() {
    }

    @Then("I should skip subsequent results")
    public void andIShouldSkipSubsequentResults() {
    }

    @Given("a JBehave story with a pending implementation")
    @Pending
    public void aJBehaveStoryWithAPendingImplementation() {
    }


    @Given("a stock of <symbol> and a threshold of <threshold>")
    public void givenAStock(@Named("symbol") String symbol, @Named("threshold") double threshold) {
        System.out.println("Stock " + symbol);
    }

    @Given("I need this test to be pending")
    public void pendingTest() {
        StepEventBus.getEventBus().testPending();
    }

    @Given("I need this test to be ignore")
    public void ignoreTest() {
        StepEventBus.getEventBus().testIgnored();
    }

    @When("the stock is traded at <price>")
    public void whenTheStockIsTradedAtprice(@Named("price") String price) {
        System.out.println("Stock traded at: " + price);
    }

    @Then("the alert status should be <status>")
    public void thenTheAlertStatusShouldBestatus(@Named("status") String status) {
        System.out.println("Expected status: " + status);
        if (status.equals("FAIL")) {
            throw new AssertionError("Expected failure");
        }
    }

    @Then("some other stuff should also work")
    public void someOtherStuffShouldWork() {
    }

    @Given("the scenario runs slowly")
    public void givenTheScenarioRunsSlowly() throws InterruptedException {
        Thread.currentThread().sleep(2000);
    }

    @Then("the test should time out")
    public void thenTheTestShouldTimeOut() {
    }


    @Given("a date of $someDate")
    @Pending
    public void givenADateOf(Date someDate) {
    }

    @When("4 days pass")
    public void when4DaysPass() {
    }

    @Then("the date is $someDate")
    public void thenTheDateIs(Date someDate) {
    }

    @Then("some otherwise ambiguous two string step, with one and two as strings")
    public void thenSomeOtherwiseAmbiguousTwoStringStepWithOneAndTwoAsStrings() {
    }


}

