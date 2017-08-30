package net.serenitybdd.jbehave;

import net.serenitybdd.jbehave.samples.levels.first.SerenityStorySampleForFistLevel;
import net.serenitybdd.jbehave.samples.levels.second.SerenityStorySampleForSecondLevel;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.reports.TestOutcomes;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WhenRunningASelectionOfJBehaveStories extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {

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
        SerenityStories first = new SerenityStorySampleForFistLevel();
        SerenityStories second = new SerenityStorySampleForSecondLevel();
        // Then
        assertThat(first.getRootPackage(), equalTo(second.getRootPackage()));
        assertThat(first.getStoryPath(), equalTo(second.getStoryPath()));
        assertThat(first.stepsFactory().createCandidateSteps().containsAll(second.stepsFactory().createCandidateSteps()), is(true));
        assertThat(first.getStoryPath(), is("**/*.story"));
    }

    final static class StoriesInTheSubsetFolderSample extends SerenityStories {
        StoriesInTheSubsetFolderSample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
            findStoriesIn("stories/subset");
        }
    }

    @Test
    public void a_subset_of_the_stories_can_be_run_individually() throws Throwable {

        // Given
        SerenityStories stories = new StoriesInTheSubsetFolderSample(environmentVariables);

        // When
        run(stories);

        // Then

        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(5));
    }

    @Test
    public void stories_with_a_matching_name_can_be_run() throws Throwable {

        // Given
        SerenityStories stories = newStory("*PassingBehavior.story");

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
        SerenityStories uatStory = new ASampleBehaviorForUatOnly(systemConfiguration);
//        uatStory.setSystemConfiguration(systemConfiguration);
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
        SerenityStories allStories = new SerenityStories(systemConfiguration);
        allStories.setSystemConfiguration(systemConfiguration);
        allStories.setEnvironmentVariables(environmentVariables);

        // When
        run(allStories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(excludeSkippedAndIgnored(outcomes).size(), is(4));
    }

    @Test
    public void should_be_possible_to_define_metafilters_in_annotations() throws Throwable {

        // Given
        SerenityStories allStories = new WithAnAnnotatedMetafilter();
        allStories.setSystemConfiguration(systemConfiguration);
        allStories.setEnvironmentVariables(environmentVariables);

        // When
        run(allStories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(excludeSkippedAndIgnored(outcomes).size(), is(2));
    }

    @Test
    public void system_property_metafilters_should_override_annotations() throws Throwable {

        // Given
        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "+environment uat, +speed fast");
        SerenityStories allStories = new WithAnAnnotatedMetafilter();
        allStories.setSystemConfiguration(systemConfiguration);
        allStories.setEnvironmentVariables(environmentVariables);

        // When
        run(allStories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(excludeSkippedAndIgnored(outcomes).size(), is(4));
    }

    @Test
    public void should_be_possible_to_define_groovy_metafilters() throws Throwable {

        // Given
        systemConfiguration.getEnvironmentVariables().setProperty("webdriver.driver", "htmlunit");
        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "groovy:('a'=='b')");
        SerenityStories allStories = new SerenityStories(systemConfiguration);
        allStories.setSystemConfiguration(systemConfiguration);
        allStories.setEnvironmentVariables(environmentVariables);

        // When
        run(allStories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(excludeSkippedAndIgnored(outcomes).size(), is(0));
    }

    @Test
    public void environment_specific_stories_should_not_be_executed_if_a_filter_excludes_it() throws Throwable {

        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "-environment uat");

        // Given
        SerenityStories uatStory = new ASampleBehaviorForUatOnly(systemConfiguration);

        // When
        run(uatStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(0));
    }

    final class AnotherStorySample extends SerenityStories {

        public AnotherStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/*Behavior.story");
        }
    }

    @Test
    public void test_should_run_stories_in_a_specified_place() throws Throwable {

        // Given
        SerenityStories story = new AnotherStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(3));
    }

    private List<? extends TestOutcome> excludeSkippedAndIgnored(final List<TestOutcome> source) {
        return TestOutcomes.of(source.stream()
                .filter(t -> {
                    TestResult result = t.getResult();
                    return TestResult.SKIPPED != result && TestResult.IGNORED != result;
                })
                .collect(Collectors.toList())).getOutcomes();
    }

}
