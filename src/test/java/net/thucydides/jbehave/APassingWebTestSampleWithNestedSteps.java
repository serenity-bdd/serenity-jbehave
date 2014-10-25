package net.thucydides.jbehave;

public class APassingWebTestSampleWithNestedSteps extends ThucydidesJUnitStories {

    public APassingWebTestSampleWithNestedSteps() {
        runThucydides().withDriver("htmlunit");
        findStoriesCalled("APassingWebTestSampleWithNestedSteps.story");
    }
}

