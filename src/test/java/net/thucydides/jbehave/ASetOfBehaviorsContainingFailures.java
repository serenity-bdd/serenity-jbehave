package net.thucydides.jbehave;

import org.junit.runner.RunWith;

public class ASetOfBehaviorsContainingFailures extends ThucydidesJUnitStories {
    public ASetOfBehaviorsContainingFailures() {
        findStoriesCalled("**/a*Behavior.story");
    }
}
