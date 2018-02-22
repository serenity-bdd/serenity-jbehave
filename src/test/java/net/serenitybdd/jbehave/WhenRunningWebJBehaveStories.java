package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningWebJBehaveStories extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {
        public AStorySample() {
            super();
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void a_simple_web_test_should_run_correctly() {

        // Given
        SerenityStories story = newStory("aPassingBehaviorWithSelenium.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test(expected = Throwable.class)
    public void a_failing_story_should_fail_in_junit() throws Throwable {

        SerenityStories story = newStory("aFailingBehaviorWithSelenium.story");

        story.run();

    }

    @Test
    public void should_be_able_to_set_thucydides_properties_in_the_base_test() {

        // Given
        SerenityStories story = new APassingWebTestSampleWithThucydidesPropertiesDefined(systemConfiguration);
        story.setEnvironmentVariables(environmentVariables);

        // When
        run(story);

        // Then

        assertThat(story.getSystemConfiguration().getBaseUrl(), is("some-base-url"));
        assertThat(story.getSystemConfiguration().getElementTimeout(), is(5));
        assertThat(story.getSystemConfiguration().shouldUseAUniqueBrowser(), is(true));
    }

    @Test(expected = Throwable.class)
    public void stories_with_errors_run_in_junit_should_fail() throws Throwable {

        // Given
        SerenityStories failingStory = newStory("aFailingBehaviorWithSelenium.story");

        // When
        failingStory.run();

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void stories_with_errors_in_one_scenario_should_still_run_subsequent_scenarios() {

        //environmentVariables.setProperty("restart.browser.each.scenario","true");

        // Given
        SerenityStories story = newStory("failingAndPassingBehaviorsWithSelenium.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.FAILURE));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));

    }
}
