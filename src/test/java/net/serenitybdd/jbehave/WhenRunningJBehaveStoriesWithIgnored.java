package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.matchers.PublicThucydidesMatchers.containsResults;
import static net.thucydides.core.model.TestResult.IGNORED;
import static net.thucydides.core.model.TestResult.PENDING;
import static net.thucydides.core.model.TestResult.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStoriesWithIgnored extends AbstractJBehaveStory {

    final static class AStorySample extends SerenityStories {

        public AStorySample(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    private void runStories(SerenityStories stories) throws Throwable {
        run(stories);
    }

    @Test
    public void a_test_running_a_failing_story_should_not_fail_if_ignore_failures_in_stories_is_set_to_true() throws Throwable {

        systemConfiguration.getEnvironmentVariables().setProperty("ignore.failures.in.stories", "true");
        SerenityStories stories = new AFailingBehavior();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);
    }

    @Test
    public void should_mark_scenarios_with_failing_assumption_as_skipped() throws Throwable {

        // Given
        SerenityStories stories = new ABehaviorWithAFailingAssumption(environmentVariables);

        // When
        run(stories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(IGNORED));
        assertThat(outcomes.get(1).getResult(), is(SUCCESS));
    }

    @Test
    public void stories_with_failing_assumptions_should_be_ignored() throws Throwable {

        // Given
        SerenityStories pendingStory = newStory("aBehaviorWithAFailingAssumption.story");

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getResult(), is(TestResult.IGNORED));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
    }
}