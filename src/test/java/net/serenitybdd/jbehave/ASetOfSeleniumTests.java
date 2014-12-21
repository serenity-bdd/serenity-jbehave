package net.serenitybdd.jbehave;

public class ASetOfSeleniumTests extends SerenityStories {
    public ASetOfSeleniumTests() {
        runSerenity().inASingleSession();
        findStoriesCalled("**/*PassingBehaviorWithSeleniumAndSeveralScenarios.story");
    }
}
