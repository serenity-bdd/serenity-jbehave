package net.serenitybdd.jbehave;

import net.thucydides.core.ThucydidesSystemProperty;

public class APassingWebTestSampleWithASpecifiedBrowser extends SerenityStories {

    public APassingWebTestSampleWithASpecifiedBrowser() {
        findStoriesCalled("aPassingBehaviorWithSeleniumAndFirefox.story");
        runSerenity().withDriver("phantomjs");
        runSerenity().withProperty(ThucydidesSystemProperty.THUCYDIDES_USE_UNIQUE_BROWSER).setTo(true);
    }


}

