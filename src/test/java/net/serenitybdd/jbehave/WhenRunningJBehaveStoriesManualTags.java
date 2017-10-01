package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestTag;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.reports.matchers.TestOutcomeMatchers.havingTag;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WhenRunningJBehaveStoriesManualTags extends AbstractJBehaveStory {

    @Test
    public void a_test_should_be_marked_as_manual_when_manual_tag_provided() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithManualScenario.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("Manual").andType("External Tests")));
        assertThat(outcomes.get(0).isManual(), equalTo(true));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    @Test
    public void steps_should_be_marked_manual_if_tag_provided_at_story_level() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithManualStory.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("Manual").andType("External Tests")));
        assertThat(outcomes.get(0).isManual(), equalTo(true));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
        assertThat(outcomes.get(1), havingTag(TestTag.withName("Manual").andType("External Tests")));
        assertThat(outcomes.get(1).isManual(), equalTo(true));
        assertThat(outcomes.get(1).getResult(), is(TestResult.PENDING));

    }

    @Test
    public void steps_should_be_marked_skipped_and_manual_if_annotated_skipped() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithSkippedManualScenario.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("Manual").andType("External Tests")));
        assertThat(outcomes.get(0).isManual(), equalTo(true));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SKIPPED));

    }

    @Test
    public void scenario_manual_tags_should_not_be_shared_between_scenarios() throws Throwable {

        // Given
        SerenityStories story = newStory("aBehaviorWithManualAndNotManualScenario.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("Manual").andType("External Tests")));
        assertThat(outcomes.get(0).isManual(), equalTo(true));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));

        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).isManual(), equalTo(false));
        assertThat(outcomes.get(1).getTags().size(), is(1));
    }
}