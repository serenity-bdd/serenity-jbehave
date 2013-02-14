package net.thucydides.jbehave;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.Configuration;

public class AStorySample extends ThucydidesJUnitStories {
    public AStorySample(String storyName, Configuration configuration, EnvironmentVariables environmentVariables) {
        this.setEnvironmentVariables(environmentVariables);
        setSystemConfiguration(configuration);
        findStoriesCalled(storyName);
    }
}

