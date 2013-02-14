package net.thucydides.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithScreenshots extends AbstractJBehaveStory {

    final static class AStorySample extends ThucydidesJUnitStories {
        public AStorySample() {
            super();
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void web_tests_should_take_screenshots() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aPassingBehaviorWithSeleniumAndFirefox.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        TestStep given = givenStepIn(outcomes);
        assertThat(given.getScreenshots().size(), greaterThan(0));
    }

    @Test
    public void web_tests_should_take_screenshots_with_multiple_scenarios() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aPassingBehaviorWithSeleniumAndSeveralScenarios.story");
        story.setEnvironmentVariables(environmentVariables);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        TestStep given = givenStepIn(outcomes);
        assertThat(given.getScreenshots().size(), greaterThan(0));
    }


    @Test
    public void web_tests_should_take_screenshots_for_multiple_tests() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("*PassingBehaviorWithSeleniumAndSeveralScenarios.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();

        TestStep given1 = givenStepIn(outcomes,0);
        TestStep given2 = givenStepIn(outcomes,1);
        TestStep given3 = givenStepIn(outcomes,2);
        TestStep given4 = givenStepIn(outcomes,3);

        assertThat(given1.getScreenshots().size(), greaterThan(0));
        assertThat(given2.getScreenshots().size(), greaterThan(0));
        assertThat(given3.getScreenshots().size(), greaterThan(0));
        assertThat(given4.getScreenshots().size(), greaterThan(0));
    }

    @Test
    public void web_tests_should_take_screenshots_with_nested_step_libraries() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("**/aPassingWebTestSampleWithNestedSteps.story");
        story.setEnvironmentVariables(environmentVariables);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        TestStep given = givenStepIn(outcomes);
        assertThat(given.getScreenshots().size(), greaterThan(0));

    }
}
