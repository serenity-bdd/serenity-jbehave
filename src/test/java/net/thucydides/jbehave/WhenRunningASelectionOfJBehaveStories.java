package net.thucydides.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningASelectionOfJBehaveStories extends AbstractJBehaveStory {

    final static class AllStoriesSample extends ThucydidesJUnitStories {}

    final static class AStorySample extends ThucydidesJUnitStories {

        public AStorySample() {
            super();
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void all_stories_on_the_classpath_should_be_run_by_default() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new AllStoriesSample();
        assertThat(stories.getRootPackage(), is("net.thucydides.jbehave"));
        assertThat(stories.getStoryPath(), is("**/*.story"));
    }

    final static class StoriesInTheSubsetFolderSample extends ThucydidesJUnitStories {
        StoriesInTheSubsetFolderSample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
            findStoriesIn("stories/subset");
        }
    }

    @Test
    public void a_subset_of_the_stories_can_be_run_individually() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new StoriesInTheSubsetFolderSample(environmentVariables);

        // When
        run(stories);

        // Then

        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(5));
    }

    @Test
    public void stories_with_a_matching_name_can_be_run() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = newStory("*PassingBehavior.story");

        // When
        run(stories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(6));
    }

    @Test
    public void environment_specific_stories_should_be_executed_if_the_corresponding_environment_variable_is_set() throws Throwable {

        // Given
        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "+environment uat");
        ThucydidesJUnitStories uatStory = new ABehaviorForUatOnly(systemConfiguration);
        uatStory.setSystemConfiguration(systemConfiguration);
        uatStory.setEnvironmentVariables(environmentVariables);

        // When
        run(uatStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }


    @Test
    public void should_be_possible_to_define_multiple_metafilters() throws Throwable {

        // Given
        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "+environment uat, +speed fast");
        ThucydidesJUnitStories allStories = new ThucydidesJUnitStories(systemConfiguration);
        allStories.setSystemConfiguration(systemConfiguration);
        allStories.setEnvironmentVariables(environmentVariables);

        // When
        run(allStories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(4));
    }

    @Test
    @Ignore("PENDING")
    public void should_be_possible_to_define_groovy_metafilters() throws Throwable {

        // Given
        systemConfiguration.getEnvironmentVariables().setProperty("webdriver.driver","htmlunit");
        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "groovy:true==false");
        ThucydidesJUnitStories allStories = new ThucydidesJUnitStories(systemConfiguration);
        allStories.setSystemConfiguration(systemConfiguration);
        allStories.setEnvironmentVariables(environmentVariables);

        // When
        run(allStories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(0));
    }

    @Test
    public void environment_specific_stories_should_not_be_executed_if_a_filter_excludes_it() throws Throwable {

        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "-environment uat");

        // Given
        ThucydidesJUnitStories uatStory = new ABehaviorForUatOnly(systemConfiguration);

        // When
        run(uatStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(0));
    }

    final class AnotherStorySample extends ThucydidesJUnitStories {

        public AnotherStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/*Behavior.story");
        }
    }

    @Test
    public void test_should_run_stories_in_a_specified_place() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AnotherStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(3));
    }

}
