package net.serenitybdd.jbehave;

import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.webdriver.Configuration;

import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

public class APassingWebTestSampleWithThucydidesPropertiesDefined extends SerenityStories {


    public APassingWebTestSampleWithThucydidesPropertiesDefined(Configuration systemConfiguration) {
        setSystemConfiguration(systemConfiguration);
        findStoriesCalled("aBehaviorWithSeleniumUsingADifferentBrowser.story");
        useFormats(TXT, XML, HTML);
        runSerenity().withProperty(ThucydidesSystemProperty.DRIVER).setTo("htmlunit");
        runSerenity().withProperty(ThucydidesSystemProperty.WEBDRIVER_BASE_URL).setTo("some-base-url");
        runSerenity().withProperty(ThucydidesSystemProperty.THUCYDIDES_TIMEOUT).setTo(5);
        runSerenity().withProperty(ThucydidesSystemProperty.THUCYDIDES_USE_UNIQUE_BROWSER).setTo(true);
    }

}

