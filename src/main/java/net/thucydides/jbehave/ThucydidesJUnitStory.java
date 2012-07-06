package net.thucydides.jbehave;

import net.thucydides.core.util.Inflector;

/**
 * Run an individual JBehave story in JUnit, where the name of the story is derived from the name of the test.
 * For example, a class called MyStory.java would run a JBehave story called "my_story.story" or MyStory.story.
 */
public class ThucydidesJUnitStory extends ThucydidesJUnitStories {
    public ThucydidesJUnitStory() {
        findStoriesCalled(storynamesDerivedFromClassName());
    }

    @Override
    public void run() throws Throwable {
        super.run();    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected String storynamesDerivedFromClassName() {
        return camelCaseStoryName() + ";" + underscoreStoryName();
    }

    private String camelCaseStoryName() {
        return "**/" + simpleClassName() + ".story";
    }

    private String simpleClassName() {
        return this.getClass().getSimpleName();
    }

    private String underscoreStoryName() {
        return "**/" + Inflector.getInstance().of(simpleClassName()).withUnderscores() + ".story";
    }

}
