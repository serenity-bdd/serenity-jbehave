package net.thucydides.jbehave;

public class ASetOfBehaviorsContainingFailures extends ThucydidesJUnitStories {
    public ASetOfBehaviorsContainingFailures() {
        findStoriesCalled("**/a*Behavior.story");
    }
}
