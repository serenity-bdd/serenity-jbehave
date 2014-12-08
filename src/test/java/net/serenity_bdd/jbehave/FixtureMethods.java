package net.serenity_bdd.jbehave;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;

public class FixtureMethods {

    public static int beforeStoryCalledCount = 0;
    public static int beforeScenarioCalledCount = 0;

    @BeforeStory
    public void beforeStory() {
        beforeStoryCalledCount++;
    }

    @BeforeScenario
    public void beforeScenario() {
        beforeScenarioCalledCount++;
    }

}
