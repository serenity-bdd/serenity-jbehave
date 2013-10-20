package net.thucydides.jbehave;

import org.jbehave.core.annotations.BeforeScenario;

public class APassingWebTestSampleWithNestedSteps extends ThucydidesJUnitStories {

    public APassingWebTestSampleWithNestedSteps() {
        findStoriesCalled("APassingWebTestSampleWithNestedSteps.story");
    }
}

