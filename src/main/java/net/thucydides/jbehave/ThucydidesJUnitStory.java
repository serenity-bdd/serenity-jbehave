package net.thucydides.jbehave;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.Inflector;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.embedder.Embedder;

/**
 * Run an individual JBehave story in JUnit, where the name of the story is derived from the name of the test.
 * For example, a class called MyStory.java would run a JBehave story called "my_story.story" or MyStory.story.
 */
public class ThucydidesJUnitStory extends ThucydidesJUnitStories {
    public ThucydidesJUnitStory() {
        findStoriesCalled(storynamesDerivedFromClassName());
    }

    public ThucydidesJUnitStory(EnvironmentVariables environmentVariables) {
        super(environmentVariables);
        findStoriesCalled(storynamesDerivedFromClassName());
    }

    protected ThucydidesJUnitStory(net.thucydides.core.webdriver.Configuration configuration) {
        super(configuration);
        findStoriesCalled(storynamesDerivedFromClassName());
    }

    @Override
    public void run() throws Throwable {
        super.run();
    }

    protected String storynamesDerivedFromClassName() {
        return camelCaseStoryName() + ";" + underscoreStoryName() + ";" + camelCaseStartingWithLowercaseStoryName();
    }

    private String camelCaseStoryName() {
        return "**/" + simpleClassName() + ".story";
    }

    private String camelCaseStartingWithLowercaseStoryName() {
        return "**/" + StringUtils.uncapitalise(simpleClassName())  + ".story";
    }

    private String simpleClassName() {
        return this.getClass().getSimpleName();
    }

    private String underscoreStoryName() {
        return "**/" + Inflector.getInstance().of(simpleClassName()).withUnderscores() + ".story";
    }

}
