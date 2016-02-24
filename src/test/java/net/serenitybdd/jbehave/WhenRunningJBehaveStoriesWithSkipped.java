package net.serenitybdd.jbehave;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.matchers.PublicThucydidesMatchers.containsResults;
import static net.thucydides.core.model.TestResult.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithSkipped extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {

        public AStorySample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void steps_after_a_failing_step_should_be_skipped() throws Throwable {

        // Given
        SerenityStories story = newStory("aComplexFailingBehavior.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        // And
        assertThat(outcomes.get(0), containsResults(SUCCESS, FAILURE, SKIPPED, SKIPPED, SKIPPED));
    }

    @Test
    public void a_tagged_skipped_outcome_should_have_skipped_steps() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aBehaviorWithATaggedPendingAndSkippedScenarios.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(2).getResult(), is(TestResult.SKIPPED));
        assertThat(outcomes.get(2).countTestSteps(), is(4));
        assertThat(outcomes.get(2).getTestSteps().get(0).getResult(), is(TestResult.SKIPPED));
        assertThat(outcomes.get(2).getTestSteps().get(1).getResult(), is(TestResult.SKIPPED));
        assertThat(outcomes.get(2).getTestSteps().get(2).getResult(), is(TestResult.SKIPPED));
        assertThat(outcomes.get(2).getTestSteps().get(3).getResult(), is(TestResult.SKIPPED));
    }

    @Test
    public void should_be_able_to_declare_a_story_as_wip_using_a_tag() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aTaggedWIPBehaviorWithSeveralScenarios.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SKIPPED));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SKIPPED));
    }

    @Test
    public void skipped_stories_should_not_be_executed() throws Throwable {

        // Given
        SerenityStories sharedVariablesStory = newStory("aSkippedBehavior.story");

        // When
        run(sharedVariablesStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(SKIPPED));
        assertThat(outcomes.get(1).getResult(), is(SKIPPED));
    }

    @Test
    public void wip_or_skipped_stories_should_not_be_executed() throws Throwable {

        // Given
        SerenityStories sharedVariablesStory = newStory("aWIPBehavior.story");

        // When
        run(sharedVariablesStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(SKIPPED));
        assertThat(outcomes.get(1).getResult(), is(SKIPPED));
    }
}