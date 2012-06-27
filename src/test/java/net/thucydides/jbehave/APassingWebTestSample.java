package net.thucydides.jbehave;

public class APassingWebTestSample extends JUnitThucydidesStories {

    public void configure() {
        findStoriesCalled("aPassingBehaviorWithSelenium.story");
    }
}

