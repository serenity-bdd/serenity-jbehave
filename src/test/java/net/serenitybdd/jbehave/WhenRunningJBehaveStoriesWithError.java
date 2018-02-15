package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithError extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {

        public AStorySample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void stories_with_errors_should_be_reported_as_failing() {

        // Given
        SerenityStories failingStory = newStory("aBehaviorThrowingAnException.story");

        // When
        run(failingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.ERROR));
    }

    @Test
    public void stories_with_undefined_steps_should_be_reported_as_pending() {

        // Given
        SerenityStories failingStory = newStory("aBehaviorWithAnUndefinedStep.story");

        // When
        run(failingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    private void runStories(SerenityStories stories) {
        run(stories);
    }

    @Test
    public void errored_stories_should_be_reported_as_having_an_error() {

        // Given
        SerenityStories failingStory = newStory("aBehaviorWithAnError.story");

        // When
        run(failingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.ERROR));
    }

    @Test
    public void a_test_running_a_failing_story_should_fail() {
        SerenityStories stories = newStory("aFailingBehavior.story");
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);

        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.FAILURE));
    }

}
