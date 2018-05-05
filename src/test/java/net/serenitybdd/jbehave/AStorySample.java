package net.serenitybdd.jbehave;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.DriverConfiguration;

public class AStorySample extends SerenityStories {
    public AStorySample(String storyName, DriverConfiguration configuration, EnvironmentVariables environmentVariables) {
        this.setEnvironmentVariables(environmentVariables);
        setSystemConfiguration(configuration);
        findStoriesCalled(storyName);
    }
}

