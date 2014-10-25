package net.thucydides.jbehave;

import net.thucydides.core.ThucydidesSystemProperty;

public class APassingWebTestSampleWithASpecifiedBrowser extends ThucydidesJUnitStories {

    public APassingWebTestSampleWithASpecifiedBrowser() {
        findStoriesCalled("aPassingBehaviorWithSeleniumAndFirefox.story");
        runThucydides().withDriver("phantomjs");
        runThucydides().withProperty(ThucydidesSystemProperty.THUCYDIDES_USE_UNIQUE_BROWSER).setTo(true);
    }


}

