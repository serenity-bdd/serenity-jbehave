package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestTag;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithTitle extends AbstractJBehaveStory {

    @Test
    public void for_stories_with_title_use_the_title_as_tag_name() {

        // Given
        SerenityStories storyWithTitle = newStory("aBehaviorWithATitle.story");

        // When
        run(storyWithTitle);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(),is(1));
        assertThat(outcomes.get(0).getTags().size(),is(1));
        TestTag[] testTags = outcomes.get(0).getTags().toArray(new TestTag[0]);
        assertThat(testTags[0].getName(),is("This is the story title"));
        assertThat(testTags[0].getType(),is("story"));
    }

    @Test
    public void for_stories_with_no_title_use_the_story_file_name_as_tag_name() {

        // Given
        SerenityStories storyWithTitle = newStory("aBehaviorWithNoTitle.story");

        // When
        run(storyWithTitle);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(),is(1));
        assertThat(outcomes.get(0).getTags().size(),is(1));
        TestTag[] testTags = outcomes.get(0).getTags().toArray(new TestTag[0]);
        assertThat(testTags[0].getName(),is("A behavior with no title"));
        assertThat(testTags[0].getType(),is("story"));
    }
}
