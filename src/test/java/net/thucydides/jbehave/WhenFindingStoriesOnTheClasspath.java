package net.thucydides.jbehave;

import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class WhenFindingStoriesOnTheClasspath {

    @Mock
    EnvironmentVariables environmentVariables;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_find_stories_in_the_specfied_directory() {
        StoryPathFinder finder = new StoryPathFinder(environmentVariables, "stories/samples/*.story");
        Set<String> storyElements = finder.findAllElements();
        assertThat(storyElements).containsOnly("**/stories/samples/*.story");
    }

    @Test
    public void should_find_specific_stories_on_the_classpath() {
        StoryPathFinder finder = new StoryPathFinder(environmentVariables, "stories/samples/SomeBehavior.story");
        Set<String> storyElements = finder.findAllElements();
        assertThat(storyElements).containsOnly("stories/samples/SomeBehavior.story");
    }

    @Test
    public void should_add_wildcard_to_stories_not_found_on_the_classpath() {
        StoryPathFinder finder = new StoryPathFinder(environmentVariables, "samples/MoreBehavior.story");
        Set<String> storyElements = finder.findAllElements();
        assertThat(storyElements).contains("**/samples/MoreBehavior.story");
    }
}
