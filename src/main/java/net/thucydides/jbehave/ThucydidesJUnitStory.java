package net.thucydides.jbehave;

import net.serenitybdd.jbehave.SerenityStory;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.Configuration;

/**
 * @deprecated Use SerenityStory instead
 * <p/>
 * Run an individual JBehave story in JUnit, where the name of the story is derived from the name of the test.
 * For example, a class called MyStory.java would run a JBehave story called "my_story.story" or MyStory.story.
 */
@Deprecated
public class ThucydidesJUnitStory extends SerenityStory {
    public ThucydidesJUnitStory() {
        super();
    }

    public ThucydidesJUnitStory(EnvironmentVariables environmentVariables) {
        super(environmentVariables);
    }

    protected ThucydidesJUnitStory(Configuration configuration) {
        super(configuration);
    }
}
