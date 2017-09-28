package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.matchers.PublicThucydidesMatchers.containsResults;
import static net.thucydides.core.model.TestResult.*;
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
    public void stories_with_errors_should_be_reported_as_failing() throws Throwable {

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
    public void stories_with_undefined_steps_should_be_reported_as_pending() throws Throwable {

        // Given
        SerenityStories failingStory = newStory("aBehaviorWithAnUndefinedStep.story");

        // When
        run(failingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    private void runStories(SerenityStories stories) throws Throwable {
        run(stories);
    }

    @Test
    public void errored_stories_should_be_reported_as_having_an_error() throws Throwable {

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
    public void a_test_running_a_failing_story_should_fail() throws Throwable {
        SerenityStories stories = new AFailingBehavior();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void a_test_running_a_failing_story_among_several_should_fail() throws Throwable {
        SerenityStories stories = new ASetOfBehaviorsContainingFailures();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void failing_stories_run_in_junit_should_fail() throws Throwable {

        // Given
        SerenityStories failingStory = newStory("aFailingBehavior.story");

        // When
        runStories(failingStory);

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void stories_with_errors_run_in_junit_should_fail() throws Throwable {

        // Given
        SerenityStories failingStory = newStory("aBehaviorThrowingAnException.story");

        // When
        runStories(failingStory);

        assert !raisedErrors.isEmpty();
    }
}