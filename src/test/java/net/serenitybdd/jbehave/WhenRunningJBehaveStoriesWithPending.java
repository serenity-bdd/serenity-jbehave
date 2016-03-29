package net.serenitybdd.jbehave;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.reports.TestOutcomes;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;

import java.util.List;

import static ch.lambdaj.Lambda.filter;
import static net.thucydides.core.matchers.PublicThucydidesMatchers.containsResults;
import static net.thucydides.core.model.TestResult.*;
import static net.thucydides.core.reports.matchers.TestOutcomeMatchers.withResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithPending extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {

        public AStorySample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void pending_stories_should_be_reported_as_pending() throws Throwable {

        // Given
        SerenityStories pendingStory = newStory("aPendingBehavior.story");

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    @Test
    public void pending_stories_should_report_the_given_when_then_steps() throws Throwable {

        // Given
        SerenityStories pendingStory = newStory("aPendingBehavior.story");

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getStepCount(), is(4));
    }

    @Test
    public void implemented_pending_stories_should_be_reported_as_pending() throws Throwable {

        // Given
        SerenityStories pendingStory = newStory("aPendingImplementedBehavior.story");

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    @Test
    public void a_test_with_a_pending_step_should_be_pending() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithAPendingStep.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        // And
        assertThat(outcomes.get(0).getResult(), is(PENDING));
        // And
        assertThat(outcomes.get(0), containsResults(SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS, PENDING, PENDING, PENDING));

    }

    @Test
    public void should_be_able_to_declare_a_story_as_pending_using_a_tag() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aTaggedPendingBehaviorWithSeveralScenarios.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
        assertThat(outcomes.get(1).getResult(), is(TestResult.PENDING));
    }

    @Test
    public void should_be_able_to_declare_a_scenario_as_pending_or_skipped_using_a_tag() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aBehaviorWithATaggedPendingAndSkippedScenarios.story");

        // When
        run(passingStory);


        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        List<? extends TestOutcome> success = TestOutcomes.of(filter(withResult(TestResult.SUCCESS), outcomes)).getTests();
        assertThat(success.size(), is(1));

        List<? extends TestOutcome> pending = TestOutcomes.of(filter(withResult(TestResult.PENDING), outcomes)).getTests();
        assertThat(pending.size(), is(1));

        List<? extends TestOutcome> skipped = TestOutcomes.of(filter(withResult(TestResult.SKIPPED), outcomes)).getTests();
        assertThat(skipped.size(), is(2));

        List<? extends TestOutcome> ignored = TestOutcomes.of(filter(withResult(TestResult.IGNORED), outcomes)).getTests();
        assertThat(ignored.size(), is(1));

        // And
        assertThat(outcomes.get(1).getStepCount(), is(4));
        assertThat(outcomes.get(2).getStepCount(), is(4));
        assertThat(outcomes.get(3).getStepCount(), is(4));
    }

    @Test
    public void a_tagged_pending_outcome_should_have_pending_steps() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aBehaviorWithATaggedPendingAndSkippedScenarios.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(1).getResult(), is(TestResult.PENDING));
        assertThat(outcomes.get(1).countTestSteps(), is(4));
        assertThat(outcomes.get(1).getTestSteps().get(0).getResult(), is(TestResult.PENDING));
        assertThat(outcomes.get(1).getTestSteps().get(1).getResult(), is(TestResult.PENDING));
        assertThat(outcomes.get(1).getTestSteps().get(2).getResult(), is(TestResult.PENDING));
        assertThat(outcomes.get(1).getTestSteps().get(3).getResult(), is(TestResult.PENDING));
    }
}