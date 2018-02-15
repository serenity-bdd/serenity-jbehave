package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningWebJBehaveStoriesUsingPageObjects extends AbstractJBehaveStory {

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
    public void a_jbehave_step_library_can_use_page_objects_directly() {

        // Given
        SerenityStories story = newStory("aBehaviorWithSeleniumPageObjects.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }
}
