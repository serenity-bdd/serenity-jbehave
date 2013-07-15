package net.thucydides.jbehave.steps;

import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class CompositeSteps {

    @Given("G")
    public void dummyGiven() {
        System.out.println("--> dummyGiven()");
    }

    @When("W")
    public void dummyWhen() {
        System.out.println("--> dummyWhen()");
    }

    @Then("T")
    public void dummyThen() {
        System.out.println("--> dummyThen()");
    }

    @Given("GW")
    @Composite(steps = { "Given G", "When W" })
    public void dummyCompositeStep() {
        System.out.println("--> dummyCompositeStep()");
    }

    @When("W2")
    public void dummyWhen2() {
        System.out.println("--> dummyWhen2()");
    }

    @Then("T2")
    public void dummyThen2() {
        System.out.println("--> dummyThen2()");
    }
}
