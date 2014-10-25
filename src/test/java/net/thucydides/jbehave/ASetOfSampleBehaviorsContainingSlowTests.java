package net.thucydides.jbehave;

import net.thucydides.core.webdriver.Configuration;

public class ASetOfSampleBehaviorsContainingSlowTests extends ThucydidesJUnitStories {
    public ASetOfSampleBehaviorsContainingSlowTests(Configuration configuration) {
        super(configuration);
        findStoriesCalled("**/a*SlowBehavior.story");
    }
}
