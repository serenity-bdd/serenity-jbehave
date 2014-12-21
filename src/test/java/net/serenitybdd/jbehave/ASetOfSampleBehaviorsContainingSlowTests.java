package net.serenitybdd.jbehave;

import net.thucydides.core.webdriver.Configuration;

public class ASetOfSampleBehaviorsContainingSlowTests extends SerenityStories {
    public ASetOfSampleBehaviorsContainingSlowTests(Configuration configuration) {
        super(configuration);
        findStoriesCalled("**/a*SlowBehavior.story");
    }
}
