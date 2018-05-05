package net.serenitybdd.jbehave;

import net.thucydides.core.webdriver.DriverConfiguration;

public class ABehaviorContainingSlowTests extends SerenityStories {
    public ABehaviorContainingSlowTests(DriverConfiguration configuration) {
        super(configuration);
        findStoriesCalled("**/aSlowBehavior.story");
    }

    public ABehaviorContainingSlowTests() {
        findStoriesCalled("**/aSlowBehavior.story");
    }

}
