package net.thucydides.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningWebJBehaveStories extends AbstractJBehaveStory {

    final static class AStorySample extends ThucydidesJUnitStories {
        public AStorySample() {
            super();
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Before
    public void reset_driver() {
        environmentVariables.setProperty("webdriver.driver", "firefox");
    }

    @Test
    public void a_test_should_have_storywide_tags_defined_by_the_tag_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aPassingBehaviorWithSelenium.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test(expected = Throwable.class)
    public void a_failing_story_should_fail_in_junit() throws Throwable {

        ThucydidesJUnitStories story = newStory("aFailingBehaviorWithSelenium.story");

        story.run();

    }

    @Test
    public void a_test_should_use_a_different_browser_if_requested() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithSeleniumUsingADifferentBrowser.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void a_jbehave_step_library_can_use_page_objects_directly() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithSeleniumPageObjects.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_be_able_to_specify_the_browser_in_the_base_test() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new APassingWebTestSampleWithASpecifiedBrowser();
        story.setEnvironmentVariables(environmentVariables);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_be_able_to_set_thucydides_properties_in_the_base_test() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new APassingWebTestSampleWithThucydidesPropertiesDefined(systemConfiguration);
        story.setEnvironmentVariables(environmentVariables);

        // When
        run(story);

        // Then

        assertThat(story.getSystemConfiguration().getBaseUrl(), is("some-base-url"));
        assertThat(story.getSystemConfiguration().getElementTimeout(), is(5));
        assertThat(story.getSystemConfiguration().getUseUniqueBrowser(), is(true));
    }

    @Test(expected = Throwable.class)
    public void stories_with_errors_run_in_junit_should_fail() throws Throwable {

        // Given
        ThucydidesJUnitStories failingStory = newStory("aFailingBehaviorWithSelenium.story");

        // When
        failingStory.run();

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void stories_with_errors_in_one_scenario_should_still_run_subsequent_scenarios() throws Throwable {

        environmentVariables.setProperty("restart.browser.each.scenario","true");

        // Given
        ThucydidesJUnitStories story = newStory("failingAndPassingBehaviorsWithSelenium.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.FAILURE));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));

    }

    @Test
    public void data_driven_steps_should_appear_as_nested_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("dataDrivenBehavior.story");

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

    @Test
    public void browser_should_not_closed_between_given_stories_and_scenario_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithGivenStories.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void two_scenarii_using_the_same_given_story_should_return_two_test_outcomes() throws Throwable {
        ThucydidesJUnitStories story = newStory("LookupADefinitionSuite.story");
        run(story);
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
    }
}
