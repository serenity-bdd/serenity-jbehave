package net.thucydides.jbehave;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.Inflector;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.join;

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

        List<String> storyNames = getStoryNameCandidatesFrom(startingWithUpperCase(simpleClassName()),
                                                             startingWithLowerCase(simpleClassName()),
                                                             underscoredTestName());
        return join(storyNames,";");
    }

    private List<String> getStoryNameCandidatesFrom(String... storyNameCandidates) {
        List<String> storyNames = Lists.newArrayList();
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
