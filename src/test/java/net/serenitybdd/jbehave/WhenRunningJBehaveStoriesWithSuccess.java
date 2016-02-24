package net.serenitybdd.jbehave;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;
import java.util.List;
import static net.thucydides.core.model.TestResult.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithSuccess extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {

        public AStorySample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void a_story_should_read_properties_from_the_thucydides_properties_file() throws Throwable {
        EnvironmentVariables environmentVariables = Injectors.getInjector().getProvider(EnvironmentVariables.class).get();
        assertThat(environmentVariables.getProperty("environment.variables.are.set"), is("true"));
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

    private void runStories(SerenityStories stories) throws Throwable {
        run(stories);
    }

    @Test
    public void a_test_running_a_slow_story_should_not_fail_if_it_does_not_timeout() throws Throwable {
        systemConfiguration.getEnvironmentVariables().setProperty("story.timeout.in.secs", "100");
        SerenityStories stories = new ABehaviorContainingSlowTests(systemConfiguration);

        runStories(stories);
    }

    final class AnotherStorySample extends SerenityStories {

        public AnotherStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/*Behavior.story");
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
}