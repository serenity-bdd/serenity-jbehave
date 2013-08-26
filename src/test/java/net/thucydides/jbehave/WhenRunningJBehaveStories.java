package net.thucydides.jbehave;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.model.TestTag;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.matchers.PublicThucydidesMatchers.containsResults;
import static net.thucydides.core.model.TestResult.FAILURE;
import static net.thucydides.core.model.TestResult.PENDING;
import static net.thucydides.core.model.TestResult.SKIPPED;
import static net.thucydides.core.model.TestResult.SUCCESS;
import static net.thucydides.core.reports.matchers.TestOutcomeMatchers.havingTag;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStories extends AbstractJBehaveStory {

    final static class AllStoriesSample extends ThucydidesJUnitStories {}

    final static class AStorySample extends ThucydidesJUnitStories {

        public AStorySample() {
            super();
        }

        protected AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void all_stories_on_the_classpath_should_be_run_by_default() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new AllStoriesSample();
        assertThat(stories.getRootPackage(), is("net.thucydides.jbehave"));
        assertThat(stories.getStoryPath(), is("**/*.story"));
    }

    final static class StoriesInTheSubsetFolderSample extends ThucydidesJUnitStories {
        StoriesInTheSubsetFolderSample() {
            findStoriesIn("stories/subset");
        }
    }


    @Test
    public void a_story_should_read_properties_from_the_thucydides_properties_file() throws Throwable {
        EnvironmentVariables environmentVariables = Injectors.getInjector().getInstance(EnvironmentVariables.class);
        assertThat(environmentVariables.getProperty("environment.variables.are.set"), is("true"));
    }


    @Test
    public void a_subset_of_the_stories_can_be_run_individually() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new StoriesInTheSubsetFolderSample();
        stories.setSystemConfiguration(systemConfiguration);

        // When
        run(stories);

        // Then

        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(5));
    }

    @Test
    public void stories_with_a_matching_name_can_be_run() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = newStory("*PassingBehavior.story");

        // When
        run(stories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(6));
    }

    @Test
    public void pending_stories_should_be_reported_as_pending() throws Throwable {

        // Given
        ThucydidesJUnitStories pendingStory = newStory("aPendingBehavior.story");

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    @Test
    public void pending_stories_should_report_the_given_when_then_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories pendingStory = newStory("aPendingBehavior.story");

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getStepCount(), is(4));
    }

    @Test
    public void implemented_pending_stories_should_be_reported_as_pending() throws Throwable {

        // Given
        ThucydidesJUnitStories pendingStory = newStory("aPendingImplementedBehavior.story");

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    @Test
    public void passing_stories_should_be_reported_as_passing() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStory = newStory("aPassingBehavior.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void state_should_be_conserved_between_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStoryWithState = newStory("aPassingBehaviorWithState.story");

        // When
        run(passingStoryWithState);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void state_should_not_be_conserved_between_stories() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStoryWithState = newStory("*PassingBehaviorWithState.story");

        // When
        run(passingStoryWithState);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void a_passing_story_with_steps_should_record_the_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStory = newStory("aPassingBehaviorWithSteps.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(0).getNestedStepCount(), is(7));
    }

    @Test
    public void the_given_when_then_clauses_should_count_as_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStory = newStory("aPassingBehaviorWithSteps.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();

        List<TestStep> steps = outcomes.get(0).getTestSteps();
        assertThat(steps.get(0).getDescription(), is("Given I have an implemented JBehave scenario"));
        assertThat(steps.get(1).getDescription(), is("And the scenario has steps"));
        assertThat(steps.get(2).getDescription(), is("When I run the scenario"));
        assertThat(steps.get(3).getDescription(), is("Then the steps should appear in the outcome"));
    }

    @Test
    public void failing_stories_should_be_reported_as_failing() throws Throwable {

        // Given
        ThucydidesJUnitStories failingStory = newStory("aFailingBehavior.story");

        // When
        run(failingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.FAILURE));
    }

    @Test
    public void steps_after_a_failing_step_should_be_skipped() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aComplexFailingBehavior.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        // And
        assertThat(outcomes.get(0), containsResults(SUCCESS, FAILURE, SKIPPED, SKIPPED, SKIPPED));
    }

    @Test
    public void a_test_with_a_pending_step_should_be_pending() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithAPendingStep.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        // And
        assertThat(outcomes.get(0).getResult() , is(PENDING));
        // And
        assertThat(outcomes.get(0), containsResults(SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS, PENDING, PENDING, SUCCESS));

    }

    @Test
    public void a_test_should_be_associated_with_a_corresponding_issue_if_specified() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithAnIssue.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getIssueKeys(), hasItem("MYPROJ-456"));

    }

    @Test
    public void a_test_can_be_associated_with_several_issues() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithMultipleIssues.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getIssueKeys(), hasItems("MYPROJ-6", "MYPROJ-7"));

    }

    @Test
    public void a_test_running_a_failing_story_should_fail() throws Throwable {
        ThucydidesJUnitStories stories = new AFailingBehavior();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);

        assert !raisedErrors.isEmpty();
    }

    private void runStories(ThucydidesJUnitStories stories) throws Throwable {
        run(stories);
    }

    @Test
    public void a_test_running_a_failing_story_should_not_fail_if_ignore_failures_in_stories_is_set_to_true() throws Throwable {

        systemConfiguration.getEnvironmentVariables().setProperty("ignore.failures.in.stories","true");
        ThucydidesJUnitStories stories = new AFailingBehavior();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);
    }

    @Test
    public void a_test_running_a_failing_story_among_several_should_fail() throws Throwable {
        ThucydidesJUnitStories stories = new ASetOfBehaviorsContainingFailures();
        stories.setSystemConfiguration(systemConfiguration);
        runStories(stories);

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void a_test_running_a_slow_story_should_not_fail_if_it_does_not_timeout() throws Throwable {
        systemConfiguration.getEnvironmentVariables().setProperty("story.timeout.in.secs", "100");
        ThucydidesJUnitStories stories = new ABehaviorContainingSlowTests(systemConfiguration);

        runStories(stories);
    }

    @Ignore("Will run JBehave stories individually one day, maybe")
    @Test
    public void timeouts_refer_only_in_individual_stories() throws Throwable {
        systemConfiguration.getEnvironmentVariables().setProperty("story.timeout.in.secs", "3");
        ThucydidesJUnitStories stories = new ASetOfBehaviorsContainingSlowTests(systemConfiguration);

        runStories(stories);
    }

    @Test
    public void a_test_story_can_be_associated_with_several_issues() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithOneStoryAndMultipleIssues.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getIssueKeys(), hasItems("MYPROJ-6","MYPROJ-7","MYPROJ-8"));

    }
    @Test
    public void all_the_scenarios_in_a_story_should_be_associated_with_a_corresponding_issue_if_specified_at_the_story_level() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithIssues.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getIssueKeys(), hasItems("MYPROJ-123", "MYPROJ-456"));
        assertThat(outcomes.get(1).getIssueKeys(), hasItems("MYPROJ-123", "MYPROJ-789"));
    }


    @Test
    public void a_test_should_have_a_story_tag_matching_the_jbehave_story() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithAnIssue.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("A behavior with an issue").andType("story")));
    }

    @Test
    public void a_test_should_have_features_defined_by_the_feature_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithFeatures.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a feature").andType("feature")));
    }

    @Test
    public void a_test_should_have_features_defined_at_the_story_levelby_the_feature_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithFeatures.story");

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
        ThucydidesJUnitStories story = newStory("aBehaviorWithMultipleFeatures.story");

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
        ThucydidesJUnitStories story = newStory("aBehaviorWithTags.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a domain").andType("domain")));
    }

    @Test
    public void a_test_should_accept_boolean_tags() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithTags.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("true").andType("security")));
    }

    @Test
    public void a_test_should_have_storywide_tags_defined_by_the_tag_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithTags.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a domain").andType("domain")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a capability").andType("capability")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("iteration 1").andType("iteration")));
    }

    @Test
    public void failing_stories_run_in_junit_should_fail() throws Throwable {

        // Given
        ThucydidesJUnitStories failingStory = newStory("aFailingBehavior.story");

        // When
        runStories(failingStory);

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void stories_with_errors_run_in_junit_should_fail() throws Throwable {

        // Given
        ThucydidesJUnitStories failingStory = newStory("aBehaviorThrowingAnException.story");

        // When
        runStories(failingStory);

        assert !raisedErrors.isEmpty();
    }

    @Test
    public void stories_with_errors_should_be_reported_as_failing() throws Throwable {

        // Given
        ThucydidesJUnitStories failingStory = newStory("aBehaviorThrowingAnException.story");

        // When
        run(failingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.ERROR));
    }

    @Test
    public void environment_specific_stories_should_be_executed_if_the_corresponding_environment_variable_is_set() throws Throwable {

        // Given
        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "+environment uat");
        ThucydidesJUnitStories uatStory = new ABehaviorForUatOnly(systemConfiguration);
        uatStory.setSystemConfiguration(systemConfiguration);
        uatStory.setEnvironmentVariables(environmentVariables);

        // When
        run(uatStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void environment_specific_stories_should_not_be_executed_if_a_filter_excludes_it() throws Throwable {

        systemConfiguration.getEnvironmentVariables().setProperty("metafilter", "-environment uat");

        // Given
        ThucydidesJUnitStories uatStory = new ABehaviorForUatOnly(systemConfiguration);

        // When
        run(uatStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(0));
    }

    @Test
    public void should_find_stories_in_a_jar_file() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("a_top_level_story.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
    }

    @Test
    public void should_find_stories_in_a_subdirectory_in_the_jar_file() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("a_story_in_the_stories_directory.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
    }

    @Test
    public void spring_autowiring_in_jbehave_steps_should_be_supported() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStory = newStory("aBehaviorWithSpringBeans.story");

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
        ThucydidesJUnitStories passingStory = newStory("aBehaviorWithSeleniumPageObjects.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_run_before_story_methods() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new AStorySample("aPassingBehavior.story");
        stories.setSystemConfiguration(systemConfiguration);

        FixtureMethods.beforeStoryCalledCount = 0;

        // When
        run(stories);

        // Then
        assertThat(FixtureMethods.beforeStoryCalledCount, is(1));
    }

    @Test
    public void should_run_before_scenario_methods() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new AStorySample("aPassingBehaviorWithSeveralScenarios.story");
        stories.setSystemConfiguration(systemConfiguration);

        FixtureMethods.beforeScenarioCalledCount = 0;

        // When
        run(stories);

        // Then
        assertThat(FixtureMethods.beforeScenarioCalledCount, is(2));
    }

    @Test
    public void should_run_before_scenario_methods_for_lots_of_stories() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new AStorySample("*PassingBehavior.story");
        stories.setSystemConfiguration(systemConfiguration);

        FixtureMethods.beforeScenarioCalledCount = 0;

        // When
        run(stories);

        // Then
        assertThat(FixtureMethods.beforeScenarioCalledCount, is(6));
    }


    final class AnotherStorySample extends ThucydidesJUnitStories {

        public AnotherStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/*Behavior.story");
        }
    }


    @Test
    public void should_mark_scenarios_with_failing_assumption_as_skipped() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new ABehaviorWithAFailingAssumption(environmentVariables);

        // When
        run(stories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(PENDING));
        assertThat(outcomes.get(1).getResult(), is(SUCCESS));
    }


    @Test
    public void test_should_run_stories_in_a_specified_place() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AnotherStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(3));
    }

    @Test
    public void test_should_not_run_given_stories_separately() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AnotherStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(3));

        TestOutcome scenarioWithGivens = outcomes.get(2);
        assertThat(scenarioWithGivens.getTestSteps().size(), is(5));

        TestStep firstStep = scenarioWithGivens.getTestSteps().get(0);
        TestStep secondStep = scenarioWithGivens.getTestSteps().get(1);
        TestStep scenarioStep = scenarioWithGivens.getTestSteps().get(2);
        assertThat(firstStep.getDescription(), is("precondition description"));
        assertThat(secondStep.getDescription(), is("another precondition description"));
        assertThat(scenarioStep.getDescription(), is("Given a system state"));

        assertThat(firstStep.getChildren().size(), is(3));
        assertThat(secondStep.getChildren().size(), is(4));


    }

    @Test
    public void test_should_count_preconditions_as_step() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AnotherSingleStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        TestOutcome scenarioWithGivens = outcomes.get(0);
        assertThat(scenarioWithGivens.getTestSteps().size(), is(5));

        TestStep firstStep = scenarioWithGivens.getTestSteps().get(0);
        TestStep secondStep = scenarioWithGivens.getTestSteps().get(1);
        TestStep scenarioStep = scenarioWithGivens.getTestSteps().get(2);
        assertThat(firstStep.getDescription(), is("precondition description"));
        assertThat(secondStep.getDescription(), is("another precondition description"));
        assertThat(scenarioStep.getDescription(), is("Given a system state"));

        assertThat(firstStep.getChildren().size(), is(3));
        assertThat(secondStep.getChildren().size(), is(4));


    }

    final class AnotherSingleStorySample extends ThucydidesJUnitStories {

        public AnotherSingleStorySample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/SomeMoreBehavior.story");
        }
    }

    @Test
    public void test_should_count_not_merge_main_step() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new ASingleStoryWithGivenSample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        TestOutcome scenarioWithGivens = outcomes.get(0);
        assertThat(scenarioWithGivens.getTestSteps().size(), is(5));

        TestStep firstStep = scenarioWithGivens.getTestSteps().get(0);
        TestStep firstChildrenFirstStep = firstStep.getChildren().get(0);
        TestStep secondChildrenFirstStep = firstStep.getChildren().get(1);
        TestStep thirdChildrenFirstStep = firstStep.getChildren().get(2);
        TestStep secondStep = scenarioWithGivens.getTestSteps().get(1);
        TestStep firstChildrenSecondStep = secondStep.getChildren().get(0);
        TestStep secondChildrenSecondStep = secondStep.getChildren().get(1);
        TestStep thirdChildrenSecondStep = secondStep.getChildren().get(2);
        TestStep fourthChildrenSecondStep = secondStep.getChildren().get(3);
        TestStep scenarioFirstStep = scenarioWithGivens.getTestSteps().get(2);
        TestStep scenarioSecondStep = scenarioWithGivens.getTestSteps().get(3);
        TestStep scenarioThirdStep = scenarioWithGivens.getTestSteps().get(4);
        assertThat(firstStep.getDescription(), is("precondition description"));
        assertThat(firstChildrenFirstStep.getDescription(), is("Given some pre-precondition"));
        assertThat(secondChildrenFirstStep.getDescription(), is("When I set up my precondition"));
        assertThat(thirdChildrenFirstStep.getDescription(), is("Then I should be ready for the real test"));
        assertThat(secondStep.getDescription(), is("another precondition description"));
        assertThat(firstChildrenSecondStep.getDescription(), is("Given some other pre-precondition"));
        assertThat(secondChildrenSecondStep.getDescription(), is("When I set up my other precondition"));
        assertThat(thirdChildrenSecondStep.getDescription(), is("And I do some other stuff"));
        assertThat(fourthChildrenSecondStep.getDescription(), is("Then I should be even more ready for the real test"));
        assertThat(scenarioFirstStep.getDescription(), is("Given a system state"));
        assertThat(scenarioSecondStep.getDescription(), is("When I do something"));
        assertThat(scenarioThirdStep.getDescription(), is("Then system is in a different state"));
    }

    final class ASingleStoryWithGivenSample extends ThucydidesJUnitStories {

        public ASingleStoryWithGivenSample() {
            super(environmentVariables);
            findStoriesCalled("stories/samples/SomeMoreBehavior.story");
        }
    }

    @Test
    public void test_stories_should_be_named_after_the_main_behavior_when_given_stories_are_present() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AnotherStorySample();

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(3));
        assertThat(outcomes.get(0).getUserStory().getName(), is("Some other behavior"));
        assertThat(outcomes.get(1).getUserStory().getName(), is("Some behavior"));
    }

    @Test
    public void steps_should_have_access_to_meta_tags_specified_in_the_story_files() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithCustomMetaTags.story");

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
        ThucydidesJUnitStories story = newStory("aBehaviorWithCustomMetaTagsAtSeveralLevels.story");

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
        ThucydidesJUnitStories story = newStory("aBehaviorWithCustomMetaTagsInSeveralScenarios.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void a_scenario_should_convert_dates_and_times() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStory = newStory("aBehaviorWithDatesAndTimes.story");

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void should_run_stories_with_composite_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories story = newStory("aBehaviorWithCompositeSteps.story");

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(1).getResult(), is(TestResult.SUCCESS));
    }

}
