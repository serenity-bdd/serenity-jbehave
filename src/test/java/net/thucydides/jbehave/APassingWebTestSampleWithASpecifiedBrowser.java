package net.thucydides.jbehave;

public class APassingWebTestSampleWithASpecifiedBrowser extends ThucydidesJUnitStories {

    public APassingWebTestSampleWithASpecifiedBrowser() {
        findStoriesCalled("aBehaviorWithSeleniumUsingADifferentBrowser.story");
        runThucydides().withDriver("htmlunit");
        //runThucydides().withDriver("htmlunit").withScreenWidth(1000);
    }


}

