package net.serenity_bdd.jbehave.steps;

import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Step;

import static org.junit.Assume.assumeTrue;

public class SomeThucydidesSteps {

    @Step
    public void step1() {
        System.out.println("STEP 1");
    }

    @Step
    public void step2() {
        System.out.println("STEP 2");}

    @Step
    public void step3() {
        System.out.println("STEP 3");
    }

    @Pending
    @Step
    public void pendingStep() {
        System.out.println("PENDING STEP");
    }

    @Step
    public void failedAssumption() {
        assumeTrue(false);
    }
}
