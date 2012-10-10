package net.thucydides.jbehave;

import net.thucydides.core.util.EnvironmentVariables;

public class ABehaviorContainingSlowTests extends ThucydidesJUnitStories {
    public ABehaviorContainingSlowTests(EnvironmentVariables environmentVariables) {
        super(environmentVariables);
        findStoriesCalled("**/aSlowBehavior.story");
    }

    public ABehaviorContainingSlowTests() {
        findStoriesCalled("**/aSlowBehavior.story");
    }

}
