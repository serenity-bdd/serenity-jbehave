package net.serenity_bdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestTag;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.reports.matchers.TestOutcomeMatchers.havingTag;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithTags extends AbstractJBehaveStory {


    @Test
    public void a_test_should_be_associated_with_a_corresponding_issue_if_specified() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithAnIssue.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getIssueKeys(), hasItem("MYPROJ-456"));

    }

    @Test
    public void a_test_story_can_be_associated_with_several_issues() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithOneStoryAndMultipleIssues.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getIssueKeys(), hasItems("MYPROJ-6", "MYPROJ-7", "MYPROJ-8"));

    }

    @Test
    public void all_the_scenarios_in_a_story_should_be_associated_with_a_corresponding_issue_if_specified_at_the_story_level() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithIssues.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getIssueKeys(), hasItems("MYPROJ-123", "MYPROJ-456"));
        assertThat(outcomes.get(1).getIssueKeys(), hasItems("MYPROJ-123", "MYPROJ-789"));

        System.clearProperty("thucydides.project.key");
    }


    @Test
    public void a_test_should_have_a_story_tag_matching_the_jbehave_story() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithAnIssue.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("A behavior with an issue").andType("story")));
    }

    @Test
    public void a_test_should_have_features_defined_by_the_feature_meta_field() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithFeatures.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a feature").andType("feature")));
    }

    @Test
    public void a_test_should_have_features_defined_at_the_story_levelby_the_feature_meta_field() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithFeatures.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a feature").andType("feature")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("another feature").andType("feature")));
    }


    @Test
    public void a_test_should_have_multiple_features_defined_at_the_story_level_by_the_feature_meta_field() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithMultipleFeatures.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a feature").andType("feature")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("another feature").andType("feature")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("yet another feature").andType("feature")));
    }


    @Test
    public void a_test_should_have_tags_defined_by_the_tag_meta_field() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithTags.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a domain").andType("domain")));
    }

    @Test
    public void a_test_should_accept_boolean_tags() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithTags.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("true").andType("security")));
    }

    @Test
    public void a_test_should_have_storywide_tags_defined_by_the_tag_meta_field() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithTags.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a domain").andType("domain")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a capability").andType("capability")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("iteration 1").andType("iteration")));
    }

    @Test
    public void steps_should_have_access_to_meta_tags_specified_in_the_story_files() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithCustomMetaTags.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void steps_should_have_access_to_meta_tags_specified_in_the_story_files_at_the_story_level() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithCustomMetaTagsAtSeveralLevels.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void scenario_metatags_should_not_be_shared_between_scenarios() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithCustomMetaTagsInSeveralScenarios.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
    }

}
