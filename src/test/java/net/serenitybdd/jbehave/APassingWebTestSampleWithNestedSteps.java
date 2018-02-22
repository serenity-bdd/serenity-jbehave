package net.serenitybdd.jbehave;

public class APassingWebTestSampleWithNestedSteps extends SerenityStories {

    public APassingWebTestSampleWithNestedSteps() {
        runSerenity().withDriver("firefox");
        findStoriesCalled("APassingWebTestSampleWithNestedSteps.story");
    }
}

