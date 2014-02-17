package net.thucydides.jbehave;

import net.thucydides.core.webdriver.Configuration;

public class ABehaviorContainingSlowTests extends ThucydidesJUnitStories {
    public ABehaviorContainingSlowTests(Configuration configuration) {
        super(configuration);
        findStoriesCalled("**/aSlowBehavior.story");
    }

    public ABehaviorContainingSlowTests() {
        findStoriesCalled("**/aSlowBehavior.story");
    }

}
