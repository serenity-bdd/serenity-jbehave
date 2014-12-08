package net.serenity_bdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveFixtures extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {

        public AStorySample() {
            super();
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }


    @Test
    public void should_run_before_story_methods() throws Throwable {

        // Given
        SerenityStories stories = new AStorySample("aPassingBehavior.story");
        stories.setSystemConfiguration(systemConfiguration);

        FixtureMethods.beforeStoryCalledCount = 0;

        // When
        run(stories);

        // Then
        assertThat(FixtureMethods.beforeStoryCalledCount, is(1));
    }

    @Test
    public void should_run_before_scenario_methods() throws Throwable {

        // Given
        SerenityStories stories = new AStorySample("aPassingBehaviorWithSeveralScenarios.story");
        stories.setSystemConfiguration(systemConfiguration);

        FixtureMethods.beforeScenarioCalledCount = 0;

        // When
        run(stories);

        // Then
        assertThat(FixtureMethods.beforeScenarioCalledCount, is(2));
    }

    @Test
    public void should_run_before_scenario_methods_for_lots_of_stories() throws Throwable {

        // Given
        SerenityStories stories = new AStorySample("*PassingBehavior.story");
        stories.setSystemConfiguration(systemConfiguration);

        FixtureMethods.beforeScenarioCalledCount = 0;

        // When
        run(stories);

        // Then
        assertThat(FixtureMethods.beforeScenarioCalledCount, is(6));
    }


    final class AnotherStorySample extends SerenityStories {

        public AnotherStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/*Behavior.story");
        }
    }

    @Test
    public void should_not_run_given_stories_separately() throws Throwable {

        // Given
        SerenityStories story = new AnotherStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(3));

        TestOutcome scenarioWithGivens = outcomes.get(2);
        assertThat(scenarioWithGivens.getTestSteps().size(), is(5));

        TestStep firstStep = scenarioWithGivens.getTestSteps().get(0);
        TestStep secondStep = scenarioWithGivens.getTestSteps().get(1);
        TestStep scenarioStep = scenarioWithGivens.getTestSteps().get(2);
        assertThat(firstStep.getDescription(), is("precondition description"));
        assertThat(secondStep.getDescription(), is("another precondition description"));
        assertThat(scenarioStep.getDescription(), is("Given a system state"));

        assertThat(firstStep.getChildren().size(), is(3));
        assertThat(secondStep.getChildren().size(), is(4));


    }

    final class AnotherSingleStorySample extends SerenityStories {

        public AnotherSingleStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/SomeMoreBehavior.story");
        }
    }


    @Test
    public void should_count_preconditions_as_step() throws Throwable {

        // Given
        SerenityStories story = new AnotherSingleStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        TestOutcome scenarioWithGivens = outcomes.get(0);
        assertThat(scenarioWithGivens.getTestSteps().size(), is(5));

        TestStep firstStep = scenarioWithGivens.getTestSteps().get(0);
        TestStep secondStep = scenarioWithGivens.getTestSteps().get(1);
        TestStep scenarioStep = scenarioWithGivens.getTestSteps().get(2);
        assertThat(firstStep.getDescription(), is("precondition description"));
        assertThat(secondStep.getDescription(), is("another precondition description"));
        assertThat(scenarioStep.getDescription(), is("Given a system state"));

        assertThat(firstStep.getChildren().size(), is(3));
        assertThat(secondStep.getChildren().size(), is(4));


    }

    @Test
    public void should_run_stories_with_composite_steps() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithCompositeSteps.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
    }

}
