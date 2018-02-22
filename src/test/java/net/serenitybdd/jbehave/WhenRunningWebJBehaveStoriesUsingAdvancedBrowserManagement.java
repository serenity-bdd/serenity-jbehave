package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.MockEnvironmentVariables;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningWebJBehaveStoriesUsingAdvancedBrowserManagement extends AbstractJBehaveStory {

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
    public void a_test_should_use_a_different_browser_if_requested() {

        // Given
        EnvironmentVariables environmentVariables = new MockEnvironmentVariables();
        SerenityStories story = newStory("aBehaviorWithSeleniumUsingADifferentBrowser.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_be_able_to_specify_the_browser_in_the_base_test() {

        // Given
        SerenityStories story = new APassingWebTestSampleWithASpecifiedBrowser();
        story.setEnvironmentVariables(environmentVariables);

        System.out.println("Output dir = " + outputDirectory.getAbsolutePath());
        // When
        run(story);

        // Then
        System.out.println("Loading from output dir = " + outputDirectory.getAbsolutePath());
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }
}
