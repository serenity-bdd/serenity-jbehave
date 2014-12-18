package net.serenity_bdd.jbehave;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.Configuration;

public class AStorySample extends SerenityStories {
    public AStorySample(String storyName, Configuration configuration, EnvironmentVariables environmentVariables) {
        this.setEnvironmentVariables(environmentVariables);
        setSystemConfiguration(configuration);
        findStoriesCalled(storyName);
    }
}

