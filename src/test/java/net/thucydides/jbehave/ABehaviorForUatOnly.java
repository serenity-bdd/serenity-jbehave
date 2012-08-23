package net.thucydides.jbehave;

import net.thucydides.core.util.EnvironmentVariables;

public class ABehaviorForUatOnly extends ThucydidesJUnitStory {
    public ABehaviorForUatOnly() {
    }

    public ABehaviorForUatOnly(EnvironmentVariables environmentVariables) {
        super(environmentVariables);
    }
}
