package net.serenity_bdd.jbehave.steps;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class StoryStepsWithSerenitySteps {

    @Steps
    SomeSerenitySteps steps;

    @Given("the scenario has steps")
    public void givenTheScenarioHasSteps() {
        steps.step1();
        steps.step2();
        steps.step3();
    }

    @Then("the steps should appear in the outcome")
    public void thenTheStepsShouldAppearInTheOutcome() {
    }


    @When("one of the steps is pending")
    public void whenOneOfTheStepsIsPending() {
        steps.pendingStep();
    }

    @Then("the test outcome should be pending")
    public void thenTheTestOutcomeShouldBePending() {
    }
}
