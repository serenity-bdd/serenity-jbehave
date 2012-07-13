package net.thucydides.jbehave;

public class ASetOfBehaviorsContainingWebTestFailures extends ThucydidesJUnitStories {
    public ASetOfBehaviorsContainingWebTestFailures() {
        findStoriesCalled("**/*WithSelenium.story");
    }
}
