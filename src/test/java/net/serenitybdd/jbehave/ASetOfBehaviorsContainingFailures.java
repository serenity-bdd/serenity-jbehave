package net.serenitybdd.jbehave;

public class ASetOfBehaviorsContainingFailures extends SerenityStories {
    public ASetOfBehaviorsContainingFailures() {
        findStoriesCalled("**/a*Behavior.story");
    }
}
