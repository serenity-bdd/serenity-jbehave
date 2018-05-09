package net.serenitybdd.jbehave;

import net.thucydides.core.webdriver.DriverConfiguration;

public class ASetOfSampleBehaviorsContainingSlowTests extends SerenityStories {
    public ASetOfSampleBehaviorsContainingSlowTests(DriverConfiguration configuration) {
        super(configuration);
        findStoriesCalled("**/a*SlowBehavior.story");
    }
}
