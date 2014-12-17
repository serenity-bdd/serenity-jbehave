package net.serenity_bdd.jbehave;

public class ASetOfBehaviorsContainingWebTestFailures extends SerenityStories {
    public ASetOfBehaviorsContainingWebTestFailures() {
        findStoriesCalled("**/*WithSelenium.story");
    }
}
