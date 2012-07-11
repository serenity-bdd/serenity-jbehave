package net.thucydides.jbehave;

public class ASetOfSeleniumTests extends ThucydidesJUnitStories {
    public ASetOfSeleniumTests() {
        findStoriesCalled("**/*PassingBehaviorWithSeleniumAndSeveralScenarios.story");
    }
}
