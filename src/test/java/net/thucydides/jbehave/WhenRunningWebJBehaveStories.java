package net.thucydides.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
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

    @Test
    public void a_test_should_have_storywide_tags_defined_by_the_tag_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aPassingBehaviorWithSelenium.story");

        story.setSystemConfiguration(systemConfiguration);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void web_tests_should_take_screenshots() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aPassingBehaviorWithSelenium.story");

        story.setSystemConfiguration(systemConfiguration);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        TestStep given = givenStepIn(outcomes);
        assertThat(given.getScreenshots().size(), greaterThan(0));
    }

    private TestStep givenStepIn(List<TestOutcome> outcomes) {
        return givenStepIn(outcomes,0);
    }

    private TestStep givenStepIn(List<TestOutcome> outcomes, int index) {
        return outcomes.get(index).getTestSteps().get(0);
    }

    @Test(expected = AssertionError.class)
    public void a_failing_story_should_fail_in_junit() throws Throwable {

        ThucydidesJUnitStories story = new AStorySample("aFailingBehaviorWithSelenium.story");

        story.setSystemConfiguration(systemConfiguration);

        story.run();
    }


    @Test
    public void web_tests_should_take_screenshots_with_multiple_scenarios() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aPassingBehaviorWithSeleniumAndSeveralScenarios.story");

        story.setSystemConfiguration(systemConfiguration);

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
        ThucydidesJUnitStories story = new AStorySample("*PassingBehaviorWithSeleniumAndSeveralScenarios.story");
        story.setSystemConfiguration(systemConfiguration);

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
        ThucydidesJUnitStories story = new AStorySample("**/aPassingWebTestSampleWithNestedSteps.story");

        story.setSystemConfiguration(systemConfiguration);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        TestStep given = givenStepIn(outcomes);
        assertThat(given.getScreenshots().size(), greaterThan(0));

    }

    @Test
    public void a_test_should_use_a_different_browser_if_requested() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithSeleniumUsingADifferentBrowser.story");

        story.setSystemConfiguration(systemConfiguration);
//        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void a_jbehave_step_library_can_use_page_objects_directly() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithSeleniumPageObjects.story");

        story.setSystemConfiguration(systemConfiguration);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_be_able_to_specifiy_the_browser_in_the_base_test() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new APassingWebTestSampleWithASpecifiedBrowser();

        story.setSystemConfiguration(systemConfiguration);

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

        // When
        run(story);

        // Then

        assertThat(story.getSystemConfiguration().getBaseUrl(), is("some-base-url"));
        assertThat(story.getSystemConfiguration().getElementTimeout(), is(5));
        assertThat(story.getSystemConfiguration().getUseUniqueBrowser(), is(true));
    }

    @Test(expected = AssertionError.class)
    public void stories_with_errors_run_in_junit_should_fail() throws Throwable {

        // Given
        ThucydidesJUnitStories failingStory = new AStorySample("aFailingBehaviorWithSelenium.story");

        failingStory.setSystemConfiguration(systemConfiguration);

        // When
        failingStory.run();
    }

    @Test
    public void stories_with_errors_in_one_scenario_should_still_run_subsequent_scenarios() throws Throwable {

        environmentVariables.setProperty("webdriver.driver","htmlunit");

        // Given
        ThucydidesJUnitStories story = new AStorySample("failingAndPassingBehaviorsWithSelenium.story");
        story.setSystemConfiguration(systemConfiguration);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.FAILURE));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));

    }

    @Test
    public void stories_with_errors_in_one_external_data_scenario_should_still_run_subsequent_scenarios() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("dataDrivenBehaviorWithSelenium.story");

        story.setSystemConfiguration(systemConfiguration);

        // When
        run(story);

        // Then
        List<TestStep> outcomes = loadTestOutcomes().get(0).getTestSteps().get(2).getChildren();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.FAILURE));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));

    }

}
