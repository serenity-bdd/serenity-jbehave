package net.serenitybdd.jbehave;

public class ASetOfBehaviorsContainingWebTestFailures extends SerenityStories {
    public ASetOfBehaviorsContainingWebTestFailures() {
        findStoriesCalled("**/*WithSelenium.story");
    }
}
