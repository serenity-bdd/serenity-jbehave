package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class WhenRunningJBehaveStoriesWithScreenshots extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {
        public AStorySample() {
            super();
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void web_tests_should_take_screenshots() {
        // Given
        SerenityStories story = newStory("aPassingBehaviorWithSeleniumAndFirefox.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getScreenshots().size(), greaterThan(0));
    }

    @Test
    public void web_tests_should_take_screenshots_with_multiple_scenarios() {

        // Given
        SerenityStories story = newStory("aPassingBehaviorWithSeleniumAndSeveralScenarios.story");
        story.setEnvironmentVariables(environmentVariables);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getScreenshots().size(), greaterThan(0));
    }


    @Test
    public void web_tests_should_take_screenshots_for_multiple_tests() {

        // Given
        SerenityStories story = newStory("*PassingBehaviorWithSeleniumAndSeveralScenarios.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getScreenshots().size(), greaterThan(0));
        assertThat(outcomes.get(1).getScreenshots().size(), greaterThan(0));
        assertThat(outcomes.get(2).getScreenshots().size(), greaterThan(0));
        assertThat(outcomes.get(3).getScreenshots().size(), greaterThan(0));
    }

    @Test
    public void web_tests_should_take_screenshots_with_nested_step_libraries() {

        // Given
        SerenityStories story = newStory("**/aPassingWebTestSampleWithNestedSteps.story");
        story.setEnvironmentVariables(environmentVariables);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getScreenshots().size(), greaterThan(0));
    }
}
