package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningWebJBehaveStoriesUsingDataDriven extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {
        public AStorySample() {
            super();
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Before
    public void reset_driver() {
        environmentVariables.setProperty("webdriver.driver", "phantomjs");
    }

    @Test
    public void data_driven_steps_should_appear_as_nested_steps() throws Throwable {

        // Given
        SerenityStories story = newStory("dataDrivenBehavior.story");

        // When
        run(story);

        // Then
        List<TestOutcome> allOutcomes = loadTestOutcomes();
        assertThat(allOutcomes.size(), is(1));

        List<TestStep> topLevelSteps = allOutcomes.get(0).getTestSteps();
        assertThat(topLevelSteps.size(), is(3));

        List<TestStep> nestedDataDrivenSteps = topLevelSteps.get(2).getChildren().get(0).getChildren();
        assertThat(nestedDataDrivenSteps.size(), is(3));

    }
}