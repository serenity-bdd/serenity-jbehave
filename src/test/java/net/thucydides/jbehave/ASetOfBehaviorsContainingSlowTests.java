package net.thucydides.jbehave;

import net.thucydides.core.util.EnvironmentVariables;

public class ASetOfBehaviorsContainingSlowTests extends ThucydidesJUnitStories {
    public ASetOfBehaviorsContainingSlowTests(EnvironmentVariables environmentVariables) {
        super(environmentVariables);
        findStoriesCalled("**/a*SlowBehavior.story");
    }
}
