package net.serenity_bdd.jbehave;

public class APassingWebTestSampleWithNestedSteps extends SerenityStories {

    public APassingWebTestSampleWithNestedSteps() {
        runSerenity().withDriver("htmlunit");
        findStoriesCalled("APassingWebTestSampleWithNestedSteps.story");
    }
}

