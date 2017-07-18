package net.serenitybdd.jbehave.steps;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.StepEventBus;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.Date;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assume.assumeTrue;


public class StorySteps {

    @Steps
    SomeSerenitySteps steps;

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

    @Then("a step that is executed after each scenario regardless of outcome")
    public void afterLifecycle() {
        System.out.println("After the lifecycle");
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

    @When("the share is traded at <price>")
    public void whenTheShareIsTradedAtprice(@Named("price") String price) {

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



    Actor john = Actor.named("John");

    public static class BuyGizmos implements Task {

        private final int quantity;

        public BuyGizmos(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public <T extends Actor> void performAs(T actor) {
        }

        public static Performable times(int quantity) {
            return instrumented(BuyGizmos.class, quantity);
        }
    }

    public static class OrderGizmos implements Task {

        private final int quantity;

        public OrderGizmos(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public <T extends Actor> void performAs(T actor) {
            if (quantity == -1) {
                throw new RuntimeException("Oh crap!");
            }

        }

        public static Performable times(int quantity) {
            return instrumented(OrderGizmos.class, quantity);
        }
    }

    public static class TheTotalPrice implements Question<Integer> {

        private final int billedPrice;

        public TheTotalPrice(int billedPrice) {

            this.billedPrice = billedPrice;
        }

        @Override
        public Integer answeredBy(Actor actor) {
            return billedPrice;
        }

        public static Question<? extends Integer> ofTheGizmos(int billedPrice) {
            return new TheTotalPrice(billedPrice);
        }
    }

    private int quantity;
    private int cost;
    private int billedPrice;

    @Given("I want to purchase $quantity gizmos")
    public void wantToPurchaseGizmos(int quantity) {
        this.quantity = quantity;
    }

    @Given("a gizmo costs $cost")
    public void gizmosCost(int cost) {
        if (cost < 0) {
            throw new RuntimeException("Oh crap!");
        }
        this.cost = cost;
    }

    @When("I buy said gizmos")
    public void buyGizmo() {
        john.attemptsTo(BuyGizmos.times(quantity));
        billedPrice = cost * quantity;
    }

    @When("I order the gizmos")
    public void orderGizmo() {
        john.attemptsTo(OrderGizmos.times(quantity));
        billedPrice = cost * quantity;
    }

    @Then("I should pay $totalPrice")
    public void shouldPay(int totalPrice) {
        john.should(seeThat(TheTotalPrice.ofTheGizmos(billedPrice), equalTo(totalPrice)));
    }
}

