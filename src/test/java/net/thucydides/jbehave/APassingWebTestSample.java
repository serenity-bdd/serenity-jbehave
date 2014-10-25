package net.thucydides.jbehave;

public class APassingWebTestSample extends ThucydidesJUnitStories {

    public APassingWebTestSample() {
        runThucydides().withDriver("htmlunit")
                       .withProperty("restart.browser.each.scenario").setTo(true);
        findStoriesCalled("aPassingBehaviorWithSelenium.story");
    }
}

