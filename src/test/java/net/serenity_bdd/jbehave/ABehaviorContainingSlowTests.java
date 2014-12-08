package net.serenity_bdd.jbehave;

import net.thucydides.core.webdriver.Configuration;

public class ABehaviorContainingSlowTests extends SerenityStories {
    public ABehaviorContainingSlowTests(Configuration configuration) {
        super(configuration);
        findStoriesCalled("**/aSlowBehavior.story");
    }

    public ABehaviorContainingSlowTests() {
        findStoriesCalled("**/aSlowBehavior.story");
    }

}
