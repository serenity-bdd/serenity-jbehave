package net.thucydides.jbehave;

public class APassingWebTestSample extends ThucydidesJUnitStories {

    public APassingWebTestSample() {
        runThucydides().withDriver("firefox")
                       .withProperty("restart.browser.each.scenario").setTo(true);
        findStoriesCalled("aPassingBehaviorWithSelenium.story");
    }
}

