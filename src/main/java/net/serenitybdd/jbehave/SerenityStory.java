package net.serenitybdd.jbehave;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.Inflector;
import net.thucydides.core.webdriver.DriverConfiguration;
import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * Run an individual JBehave story in JUnit, where the name of the story is derived from the name of the test.
 * For example, a class called MyStory.java would run a JBehave story called "my_story.story" or MyStory.story.
 */
public class SerenityStory extends SerenityStories {
    public SerenityStory() {
        findStoriesCalled(storynamesDerivedFromClassName());
    }

    public SerenityStory(EnvironmentVariables environmentVariables) {
        super(environmentVariables);
        findStoriesCalled(storynamesDerivedFromClassName());
    }

    protected SerenityStory(DriverConfiguration configuration) {
        super(configuration);
        findStoriesCalled(storynamesDerivedFromClassName());
    }

    @Override
    public void run() throws Throwable {
        super.run();
    }

    protected String storynamesDerivedFromClassName() {

        List<String> storyNames = getStoryNameCandidatesFrom(startingWithUpperCase(simpleClassName()),
                                                             startingWithLowerCase(simpleClassName()),
                                                             underscoredTestName());
        return join(storyNames,";");
    }

    private List<String> getStoryNameCandidatesFrom(String... storyNameCandidates) {
        List<String> storyNames = new ArrayList<>();
        for(String storyName : storyNameCandidates) {
            if (storyNames.isEmpty()) {
                addIfPresent(storyNames, "/" + storyName + ".story");
                addIfPresent(storyNames, "stories/" + storyName + ".story");
            }
        }
        if (storyNames.isEmpty()) {
            for(String storyName : storyNameCandidates) {
                storyNames.add("**/" + storyName + ".story");
            }
        }
        return storyNames;
    }

    private String startingWithUpperCase(String storyName) {
        return StringUtils.capitalise(storyName);
    }

    private String startingWithLowerCase(String storyName) {
        return StringUtils.lowercaseFirstLetter(storyName);
    }

    private void addIfPresent(List<String> storyNames, String storyNameCandidate) {
        if (Thread.currentThread().getContextClassLoader().getResource(storyNameCandidate) != null) {
            storyNames.add(storyNameCandidate);
        }
    }

    private String simpleClassName() {
        return this.getClass().getSimpleName();
    }

    private String underscoredTestName() {
        return Inflector.getInstance().of(simpleClassName()).withUnderscores().toString();
    }

}
