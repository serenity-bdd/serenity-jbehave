package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.model.TestResult.FAILURE;
import static net.thucydides.core.model.TestResult.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithFailure extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {

        public AStorySample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void failing_stories_should_be_reported_as_failing() throws Throwable {

        // Given
        SerenityStories failingStory = newStory("aFailingBehavior.story");

        // When
        run(failingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.FAILURE));
    }

    @Test
    public void should_not_reset_steps_for_each_scenario_if_configured() throws Throwable {

        environmentVariables.setProperty("reset.steps.each.scenario", "false");
        // Given
        SerenityStories passingStory = newStory("aPassingBehaviorWithSeveralScenarios.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.FAILURE));
    }

    @Test
    public void variables_are_reset_between_steps_by_default() throws Throwable {

        // Given
        SerenityStories sharedVariablesStory = newStory("aBehaviorWithSharedVariables.story");

        // When
        run(sharedVariablesStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(FAILURE));
    }
}