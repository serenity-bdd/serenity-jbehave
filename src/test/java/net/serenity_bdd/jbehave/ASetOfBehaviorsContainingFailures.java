package net.serenity_bdd.jbehave;

public class ASetOfBehaviorsContainingFailures extends SerenityStories {
    public ASetOfBehaviorsContainingFailures() {
        findStoriesCalled("**/a*Behavior.story");
    }
}
