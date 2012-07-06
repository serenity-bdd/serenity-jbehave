package net.thucydides.jbehave;

public class APassingWebTestSampleWithASpecifiedBrowser extends ThucydidesJUnitStories {

    public APassingWebTestSampleWithASpecifiedBrowser() {
        findStoriesCalled("aPassingBehaviorWithSelenium.story");
        useDriver("htmlunit");
    }
}

