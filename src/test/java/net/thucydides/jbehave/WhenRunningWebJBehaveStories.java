package net.thucydides.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class WhenRunningWebJBehaveStories extends AbstractJBehaveStory {

    final static class AStorySample extends ThucydidesJUnitStories {
        AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void a_test_should_have_storywide_tags_defined_by_the_tag_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aPassingBehaviorWithSelenium.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

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
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getScreenshots().size(), greaterThan(0));
    }

    @Test
    public void web_tests_should_take_screenshots_with_multiple_scenarios() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aPassingBehaviorWithSeleniumAndSeveralScenarios.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getScreenshots().size(), greaterThan(0));
    }


    @Test
    public void web_tests_should_take_screenshots_for_multiple_tests() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("*PassingBehaviorWithSeleniumAndSeveralScenarios.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getScreenshots().size(), greaterThan(0));
        assertThat(outcomes.get(1).getScreenshots().size(), greaterThan(0));
    }

    @Test
    public void a_test_should_use_a_different_browser_if_requested() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithSeleniumUsingADifferentBrowser.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

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
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_be_able_to_set_thucydides_properties_in_the_base_test() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new APassingWebTestSampleWithThucydidesPropertiesDefined();

        //story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then

        assertThat(story.getSystemConfiguration().getBaseUrl(), is("some-base-url"));
        assertThat(story.getSystemConfiguration().getElementTimeout(), is(5));
        assertThat(story.getSystemConfiguration().getUseUniqueBrowser(), is(true));
    }


}
