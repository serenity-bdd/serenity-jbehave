package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenConfiguringJBehaveStorySteps extends AbstractJBehaveStory {

    @Test
    public void should_find_stories_in_a_jar_file() throws Throwable {

        // Given
        SerenityStories story = newStory("a_top_level_story.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
    }

    @Test
    public void should_find_stories_in_a_subdirectory_in_the_jar_file() throws Throwable {

        // Given
        SerenityStories story = newStory("annotatedstories/a_story_in_the_stories_directory.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
    }

    @Test
    public void spring_autowiring_in_jbehave_steps_should_be_supported() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aBehaviorWithSpringBeans.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_instantiate_page_objects_in_jbehave_steps() throws Throwable {

        // Given
        SerenityStories passingStory = newStory("aBehaviorWithSeleniumPageObjects.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }
}
