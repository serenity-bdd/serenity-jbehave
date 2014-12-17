package net.serenity_bdd.jbehave.steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class EnterNamesStep {
    String firstname;
    String lastname;
    String expectedFirstname;
    String expectedLastname;

    @Steps
    SomeSeleniumSteps steps;

    @Step
    public void enterAndCheckName() {
        steps.whenIEnterTheFirstName(firstname);
        steps.whenIEnterTheLastName(lastname);
        steps.thenIShouldSeeInTheNamesFields(expectedFirstname, expectedLastname);
    }
}
