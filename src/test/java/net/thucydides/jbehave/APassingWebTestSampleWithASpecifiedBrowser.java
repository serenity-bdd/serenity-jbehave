package net.thucydides.jbehave;

import net.thucydides.core.ThucydidesSystemProperty;

public class APassingWebTestSampleWithASpecifiedBrowser extends ThucydidesJUnitStories {

    public APassingWebTestSampleWithASpecifiedBrowser() {
        findStoriesCalled("aBehaviorWithSeleniumUsingADifferentBrowser.story");
        runThucydides().withDriver("htmlunit");
        runThucydides().withProperty(ThucydidesSystemProperty.UNIQUE_BROWSER).setTo(true);
    }


}

