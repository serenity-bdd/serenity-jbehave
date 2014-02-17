package net.thucydides.jbehave;

import net.thucydides.core.webdriver.Configuration;

public class ASetOfBehaviorsContainingSlowTests extends ThucydidesJUnitStories {
    public ASetOfBehaviorsContainingSlowTests(Configuration configuration) {
        super(configuration);
        findStoriesCalled("**/a*SlowBehavior.story");
    }
}
