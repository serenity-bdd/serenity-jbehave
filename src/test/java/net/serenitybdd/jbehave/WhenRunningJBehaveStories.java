package net.serenitybdd.jbehave;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.matchers.PublicThucydidesMatchers.containsResults;
import static net.thucydides.core.model.TestResult.FAILURE;
import static net.thucydides.core.model.TestResult.IGNORED;
import static net.thucydides.core.model.TestResult.PENDING;
import static net.thucydides.core.model.TestResult.SKIPPED;
import static net.thucydides.core.model.TestResult.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStories extends AbstractJBehaveStory {

    final static class AllStoriesSample extends SerenityStories {
        AllStoriesSample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }
    }

    final static class AStorySample extends SerenityStories {

        public AStorySample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    final static class StoriesInTheSubsetFolderSample extends SerenityStories {
        StoriesInTheSubsetFolderSample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
            findStoriesIn("stories/subset");
        }
    }


    @Test
    public void a_story_should_read_properties_from_the_thucydides_properties_file() throws Throwable {
        EnvironmentVariables environmentVariables = Injectors.getInjector().getProvider(EnvironmentVariables.class).get();
        assertThat(environmentVariables.getProperty("environment.variables.are.set"), is("true"));
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
    public void stories_with_failing_assumptions_should_be_ignored() throws Throwable {

        // Given
        SerenityStories pendingStory = newStory("aBehaviorWithAFailingAssumption.story");

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.IGNORED));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
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
    public void passing_stories_should_be_reported_as_passing() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aPassingBehavior.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void state_should_be_conserved_between_steps() throws Throwable {

        // Given
        SerenityStories passingStoryWithState = newStory("aPassingBehaviorWithState.story");

        // When
        run(passingStoryWithState);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void state_should_not_be_conserved_between_stories() throws Throwable {

        // Given
        SerenityStories passingStoryWithState = newStory("*PassingBehaviorWithState.story");

        // When
        run(passingStoryWithState);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void a_passing_story_with_steps_should_record_the_steps() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aPassingBehaviorWithSteps.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(0).getNestedStepCount(), is(7));
    }

    @Test
    public void the_given_when_then_clauses_should_count_as_steps() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aPassingBehaviorWithSteps.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();

        List<TestStep> steps = outcomes.get(0).getTestSteps();
        assertThat(steps.get(0).getDescription(), is("Given I have an implemented JBehave scenario"));
        assertThat(steps.get(1).getDescription(), is("And the scenario has steps"));
        assertThat(steps.get(2).getDescription(), is("When I run the scenario"));
        assertThat(steps.get(3).getDescription(), is("Then the steps should appear in the outcome"));
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
    public void a_test_running_a_failing_story_should_fail() throws Throwable {
        SerenityStories stories = new AFailingBehavior();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);

        assert !raisedErrors.isEmpty();
    }

    private void runStories(SerenityStories stories) throws Throwable {
        run(stories);
    }

    @Test
    public void a_test_running_a_failing_story_should_not_fail_if_ignore_failures_in_stories_is_set_to_true() throws Throwable {

        systemConfiguration.getEnvironmentVariables().setProperty("ignore.failures.in.stories", "true");
        SerenityStories stories = new AFailingBehavior();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);
    }

    @Test
    public void a_test_running_a_failing_story_among_several_should_fail() throws Throwable {
        SerenityStories stories = new ASetOfBehaviorsContainingFailures();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void a_test_running_a_slow_story_should_not_fail_if_it_does_not_timeout() throws Throwable {
        systemConfiguration.getEnvironmentVariables().setProperty("story.timeout.in.secs", "100");
        SerenityStories stories = new ABehaviorContainingSlowTests(systemConfiguration);

        runStories(stories);
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

    final class AnotherStorySample extends SerenityStories {

        public AnotherStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/*Behavior.story");
        }
    }


    @Test
    public void should_mark_scenarios_with_failing_assumption_as_skipped() throws Throwable {

        // Given
        SerenityStories stories = new ABehaviorWithAFailingAssumption(environmentVariables);

        // When
        run(stories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(IGNORED));
        assertThat(outcomes.get(1).getResult(), is(SUCCESS));
    }


    final class AnotherSingleStorySample extends SerenityStories {

        public AnotherSingleStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/SomeMoreBehavior.story");
        }
    }

    @Test
    public void test_should_count_not_merge_main_step() throws Throwable {

        // Given
        SerenityStories story = new ASingleStoryWithGivenSample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        TestOutcome scenarioWithGivens = outcomes.get(0);
        assertThat(scenarioWithGivens.getTestSteps().size(), is(5));

        TestStep firstStep = scenarioWithGivens.getTestSteps().get(0);
        TestStep firstChildrenFirstStep = firstStep.getChildren().get(0);
        TestStep secondChildrenFirstStep = firstStep.getChildren().get(1);
        TestStep thirdChildrenFirstStep = firstStep.getChildren().get(2);
        TestStep secondStep = scenarioWithGivens.getTestSteps().get(1);
        TestStep firstChildrenSecondStep = secondStep.getChildren().get(0);
        TestStep secondChildrenSecondStep = secondStep.getChildren().get(1);
        TestStep thirdChildrenSecondStep = secondStep.getChildren().get(2);
        TestStep fourthChildrenSecondStep = secondStep.getChildren().get(3);
        TestStep scenarioFirstStep = scenarioWithGivens.getTestSteps().get(2);
        TestStep scenarioSecondStep = scenarioWithGivens.getTestSteps().get(3);
        TestStep scenarioThirdStep = scenarioWithGivens.getTestSteps().get(4);
        assertThat(firstStep.getDescription(), is("precondition description"));
        assertThat(firstChildrenFirstStep.getDescription(), is("Given some pre-precondition"));
        assertThat(secondChildrenFirstStep.getDescription(), is("When I set up my precondition"));
        assertThat(thirdChildrenFirstStep.getDescription(), is("Then I should be ready for the real test"));
        assertThat(secondStep.getDescription(), is("another precondition description"));
        assertThat(firstChildrenSecondStep.getDescription(), is("Given some other pre-precondition"));
        assertThat(secondChildrenSecondStep.getDescription(), is("When I set up my other precondition"));
        assertThat(thirdChildrenSecondStep.getDescription(), is("And I do some other stuff"));
        assertThat(fourthChildrenSecondStep.getDescription(), is("Then I should be even more ready for the real test"));
        assertThat(scenarioFirstStep.getDescription(), is("Given a system state"));
        assertThat(scenarioSecondStep.getDescription(), is("When I do something"));
        assertThat(scenarioThirdStep.getDescription(), is("Then system is in a different state"));
    }

    final class ASingleStoryWithGivenSample extends SerenityStories {

        public ASingleStoryWithGivenSample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/SomeMoreBehavior.story");
        }
    }

    @Test
    public void test_stories_should_be_named_after_the_main_behavior_when_given_stories_are_present() throws Throwable {

        // Given
        SerenityStories story = new AnotherStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(3));
        assertThat(outcomes.get(0).getUserStory().getName(), is("Some other behavior"));
        assertThat(outcomes.get(1).getUserStory().getName(), is("Some behavior"));
    }

    @Test
    public void a_scenario_should_convert_dates_and_times() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aBehaviorWithDatesAndTimes.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_reset_steps_for_each_scenario() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aPassingBehaviorWithSeveralScenarios.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
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
    public void should_be_able_to_declare_a_scenario_as_pending_or_skipped_using_a_tag() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aBehaviorWithATaggedPendingAndSkippedScenarios.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(5));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.PENDING));
        assertThat(outcomes.get(2).getResult(), is(TestResult.SKIPPED));
        assertThat(outcomes.get(3).getResult(), is(TestResult.SKIPPED));
        assertThat(outcomes.get(4).getResult(), is(TestResult.IGNORED));

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
    public void shared_variables_between_steps_are_allowed_if_configured() throws Throwable {

        // Given
        environmentVariables.setProperty("reset.steps.each.scenario", "false");
        SerenityStories sharedVariablesStory = newStory("aBehaviorWithSharedVariables.story");

        // When
        run(sharedVariablesStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(SUCCESS));
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
