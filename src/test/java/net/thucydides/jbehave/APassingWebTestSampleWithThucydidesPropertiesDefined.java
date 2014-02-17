package net.thucydides.jbehave;

import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.webdriver.Configuration;

import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

public class APassingWebTestSampleWithThucydidesPropertiesDefined extends ThucydidesJUnitStories {


    public APassingWebTestSampleWithThucydidesPropertiesDefined(Configuration systemConfiguration) {
        setSystemConfiguration(systemConfiguration);
        findStoriesCalled("aBehaviorWithSeleniumUsingADifferentBrowser.story");
        useFormats(TXT, XML, HTML);
        runThucydides().withProperty(ThucydidesSystemProperty.DRIVER).setTo("htmlunit");
        runThucydides().withProperty(ThucydidesSystemProperty.BASE_URL).setTo("some-base-url");
        runThucydides().withProperty(ThucydidesSystemProperty.ELEMENT_TIMEOUT).setTo(5);
        runThucydides().withProperty(ThucydidesSystemProperty.UNIQUE_BROWSER).setTo(true);
    }

}

