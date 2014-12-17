package net.serenity_bdd.jbehave;

public class APassingWebTestSample extends SerenityStories {

    public APassingWebTestSample() {
        runSerenity().withDriver("htmlunit")
                .withProperty("restart.browser.each.scenario").setTo(true);
        findStoriesCalled("aPassingBehaviorWithSelenium.story");
    }
}

