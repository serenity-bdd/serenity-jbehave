package net.thucydides.jbehave;

public class ASetOfSeleniumTests extends ThucydidesJUnitStories {
    public ASetOfSeleniumTests() {
        runThucydides().inASingleSession();
        findStoriesCalled("**/*PassingBehaviorWithSeleniumAndSeveralScenarios.story");
    }
}
