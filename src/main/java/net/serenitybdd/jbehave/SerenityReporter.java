package net.serenitybdd.jbehave;

import ch.lambdaj.function.convert.Converter;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.SerenityListeners;
import net.serenitybdd.core.SerenityReports;
import net.thucydides.core.model.*;
import net.thucydides.core.reports.ReportService;
import net.thucydides.core.steps.BaseStepListener;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.util.Inflector;
import net.thucydides.core.util.NameConverter;
import net.thucydides.core.webdriver.Configuration;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.core.webdriver.WebdriverProxyFactory;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;
import org.junit.internal.AssumptionViolatedException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static ch.lambdaj.Lambda.*;

public class SerenityReporter implements StoryReporter {

    private static final Logger logger = LoggerFactory.getLogger(SerenityReporter.class);

    private ThreadLocal<SerenityListeners> serenityListenersThreadLocal;
    private ThreadLocal<ReportService> reportServiceThreadLocal;
    private final List<BaseStepListener> baseStepListeners;

    private final Configuration systemConfiguration;
    private static final String OPEN_PARAM_CHAR = "\uff5f";
    private static final String CLOSE_PARAM_CHAR = "\uff60";

    private static final String PENDING = "pending";
    private static final String MANUAL = "manual";
    private static final String SKIP = "skip";
    private static final String WIP = "wip";
    private static final String IGNORE = "ignore";

    private static Optional<TestResult> forcedScenarioResult;

    private GivenStoryMonitor givenStoryMonitor;

    public SerenityReporter(Configuration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
        serenityListenersThreadLocal = new ThreadLocal<>();
        reportServiceThreadLocal = new ThreadLocal<>();
        baseStepListeners = Lists.newArrayList();
        givenStoryMonitor = new GivenStoryMonitor();
        clearScenarioResult();

    }

    private void clearScenarioResult() {
        forcedScenarioResult = Optional.absent();
    }

    protected void clearListeners() {
        serenityListenersThreadLocal.remove();
        reportServiceThreadLocal.remove();
        givenStoryMonitor.clear();
    }

    protected SerenityListeners getSerenityListeners() {
        if (serenityListenersThreadLocal.get() == null) {
            SerenityListeners listeners = SerenityReports.setupListeners(systemConfiguration);
            serenityListenersThreadLocal.set(listeners);
            synchronized (baseStepListeners) {
                baseStepListeners.add(listeners.getBaseStepListener());
            }
        }
        return serenityListenersThreadLocal.get();
    }

    protected ReportService getReportService() {
        return SerenityReports.getReportService(systemConfiguration);
    }

    public void storyNotAllowed(Story story, String filter) {
        logger.debug("not allowed story ".concat(story.getName()));
    }

    public void storyCancelled(Story story, StoryDuration storyDuration) {
        logger.debug("cancelled story ".concat(story.getName()));
    }

    private Stack<Story> storyStack = new Stack<>();

    private Stack<String> activeScenarios = new Stack<>();
    private List<String> givenStories = Lists.newArrayList();
    private Map<String, Meta> scenarioMeta= new ConcurrentHashMap<>();
    private Set<String> scenarioMetaProcessed= Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    private Story currentStory() {
        return storyStack.peek();
    }

    private void currentStoryIs(Story story) {
        storyStack.push(story);
    }

    private Map<String, String> storyMetadata;

    private void clearActiveScenariosData() {
        activeScenarios.clear();
        scenarioMeta.clear();
        scenarioMetaProcessed.clear();
    }

    private void registerScenariosMeta(Story story) {
        final List<Scenario> scenarios = story.getScenarios();
        for (Scenario scenario : scenarios) {
            scenarioMeta.put(scenario.getTitle(), scenario.getMeta());
        }
    }

    public void beforeStory(Story story, boolean givenStory) {
        logger.debug("before story ".concat(story.getName()));
        currentStoryIs(story);
        noteAnyGivenStoriesFor(story);
        storyMetadata = getMetadataFrom(story.getMeta());
        if (!isFixture(story) && !givenStory) {

            clearActiveScenariosData();
            registerScenariosMeta(story);

            configureDriver(story);

            SerenityStepFactory.resetContext();

            getSerenityListeners().withDriver(ThucydidesWebDriverSupport.getDriver());

            if (!isAStoryLevelGiven(story)) {
                startTestSuiteForStory(story);
                if (givenStoriesPresentFor(story)) {
                    startTestForFirstScenarioIn(story);
                }
            }
        } else if(givenStory) {
            shouldNestScenarios(true);
        }
        registerStoryMeta(story.getMeta());
    }

    private boolean nestScenarios = false;

    private boolean shouldNestScenarios() {
        return nestScenarios;
    }

    private void shouldNestScenarios(boolean nestScenarios) {
        this.nestScenarios = nestScenarios;
    }

    private void startTestForFirstScenarioIn(Story story) {
        Scenario firstScenario = story.getScenarios().get(0);
        startScenarioCalled(firstScenario.getTitle());
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle("Preconditions"));
        shouldNestScenarios(true);
    }

    public void beforeScenario(String scenarioTitle) {
        logger.debug("scenario started ".concat(scenarioTitle));
        clearScenarioResult();

        if (shouldRestartDriverBeforeEachScenario() && !shouldNestScenarios()) {
            WebdriverProxyFactory.resetDriver(ThucydidesWebDriverSupport.getDriver());
        }

        if (shouldResetStepsBeforeEachScenario()) {
            SerenityStepFactory.resetContext();
        }

        if(isCurrentScenario(scenarioTitle)) {
            //This is our scenario
        } else if (shouldNestScenarios()) {
            startNewStep(scenarioTitle);
        } else {
            startScenarioCalled(scenarioTitle);
            if(!isMetaProcessed(scenarioTitle)){
                scenarioMeta(scenarioMeta.get(scenarioTitle));
                scenarioMetaProcessed.add(scenarioTitle);
            }
        }
    }

    private boolean isCurrentScenario(String scenarioTitle) {
        return !activeScenarios.empty() && scenarioTitle.equals(activeScenarios.peek());
    }

    private void startNewStep(String scenarioTitle) {
        if (givenStoryMonitor.isInGivenStory() && StepEventBus.getEventBus().areStepsRunning()) {
            StepEventBus.getEventBus().updateCurrentStepTitle(scenarioTitle);
        } else {
            StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(scenarioTitle));
        }
    }

    private boolean givenStoriesPresentFor(Story story) {
        return !story.getGivenStories().getStories().isEmpty();
    }

    private void startTestSuiteForStory(Story story) {
        String storyName = removeSuffixFrom(story.getName());
        String storyTitle = NameConverter.humanize(storyName);

        net.thucydides.core.model.Story userStory
                = net.thucydides.core.model.Story.withIdAndPath(storyName, storyTitle, story.getPath())
                .withNarrative(getNarrativeFrom(story));
        StepEventBus.getEventBus().testSuiteStarted(userStory);
        registerTags(story);
    }

    private String getNarrativeFrom(Story story) {
        return (!story.getNarrative().isEmpty()) ?
             story.getNarrative().asString(new Keywords()).trim() : "";
    }

    private void noteAnyGivenStoriesFor(Story story) {
        for (GivenStory given : story.getGivenStories().getStories()) {
            String givenStoryName = new File(given.getPath()).getName();
            givenStories.add(givenStoryName);
        }
    }

    private boolean isAStoryLevelGiven(Story story) {
        for (String givenStoryName : givenStories) {
            if (hasSameName(story, givenStoryName)) {
                return true;
            }
        }
        return false;
    }

    private void givenStoryDone(Story story) {
        givenStories.remove(story.getName());
    }

    private boolean hasSameName(Story story, String givenStoryName) {
        return story.getName().equalsIgnoreCase(givenStoryName);
    }

    Map<Story, WebDriver> drivers = Maps.newConcurrentMap();

    private void configureDriver(Story story) {
        StepEventBus.getEventBus().setUniqueSession(systemConfiguration.shouldUseAUniqueBrowser());
        String requestedDriver = getRequestedDriver(story.getMeta());
        if (StringUtils.isNotEmpty(requestedDriver)) {
            ThucydidesWebDriverSupport.initialize(requestedDriver);
            drivers.put(story, ThucydidesWebDriverSupport.getDriver());
        } else {
            ThucydidesWebDriverSupport.initialize();
        }
    }

    private void registerTags(Story story) {
        registerStoryIssues(story.getMeta());
        registerStoryFeaturesAndEpics(story.getMeta());
        registerStoryTags(story.getMeta());
        registerStoryMeta(story.getMeta());
    }

    private boolean isFixture(Story story) {
        return (story.getName().equals("BeforeStories") || story.getName().equals("AfterStories"));
    }

    private String getRequestedDriver(Meta metaData) {
        if (StringUtils.isNotEmpty(metaData.getProperty("driver"))) {
            return metaData.getProperty("driver");
        }
        if (systemConfiguration.getDriverType() != null) {
            return systemConfiguration.getDriverType().toString();
        }
        return null;
    }

    private List<String> getIssueOrIssuesPropertyValues(Meta metaData) {
        return getTagPropertyValues(metaData, "issue");
    }

    private List<TestTag> getFeatureOrFeaturesPropertyValues(Meta metaData) {
        List<String> features = getTagPropertyValues(metaData, "feature");
        return convert(features, toFeatureTags());
    }

    private List<TestTag> getEpicOrEpicsPropertyValues(Meta metaData) {
        List<String> epics = getTagPropertyValues(metaData, "epic");
        return convert(epics, toEpicTags());
    }

    private List<TestTag> getTagOrTagsPropertyValues(Meta metaData) {
        List<String> tags = getTagPropertyValues(metaData, "tag");
        return convert(tags, toTags());
    }

    private Converter<String, TestTag> toTags() {
        return new Converter<String, TestTag>() {
            public TestTag convert(String tag) {
                List<String> tagParts = Lists.newArrayList(Splitter.on(":").trimResults().split(tag));
                if (tagParts.size() == 2) {
                    return TestTag.withName(tagParts.get(1)).andType(tagParts.get(0));
                } else {
                    return TestTag.withName("true").andType(tagParts.get(0));
                }
            }
        };
    }

    private Converter<String, TestTag> toFeatureTags() {
        return new Converter<String, TestTag>() {
            public TestTag convert(String featureName) {
                return TestTag.withName(featureName).andType("feature");
            }
        };
    }

    private Converter<String, TestTag> toEpicTags() {
        return new Converter<String, TestTag>() {
            public TestTag convert(String featureName) {
                return TestTag.withName(featureName).andType("epic");
            }
        };
    }

    private List<String> getTagPropertyValues(Meta metaData, String tagType) {
        String singularTag = metaData.getProperty(tagType);
        String pluralTagType = Inflector.getInstance().pluralize(tagType);

        String multipleTags = metaData.getProperty(pluralTagType);
        String allTags = Joiner.on(',').skipNulls().join(singularTag, multipleTags);

        return Lists.newArrayList(Splitter.on(',').omitEmptyStrings().trimResults().split(allTags));
    }

    private void registerIssues(Meta metaData) {
        List<String> issues = getIssueOrIssuesPropertyValues(metaData);

        if (!issues.isEmpty()) {
            StepEventBus.getEventBus().addIssuesToCurrentTest(issues);
        }
    }

    private void registerStoryIssues(Meta metaData) {
        List<String> issues = getIssueOrIssuesPropertyValues(metaData);

        if (!issues.isEmpty()) {
            StepEventBus.getEventBus().addIssuesToCurrentStory(issues);
        }
    }

    private void registerFeaturesAndEpics(Meta metaData) {
        List<TestTag> featuresAndEpics = featureAndEpicTags(metaData);

        if (!featuresAndEpics.isEmpty()) {
            StepEventBus.getEventBus().addTagsToCurrentTest(featuresAndEpics);
        }
    }

    private List<TestTag> featureAndEpicTags(Meta metaData) {
        List<TestTag> featuresAndEpics = Lists.newArrayList();
        featuresAndEpics.addAll(getFeatureOrFeaturesPropertyValues(metaData));
        featuresAndEpics.addAll(getEpicOrEpicsPropertyValues(metaData));
        return featuresAndEpics;
    }

    private void registerStoryFeaturesAndEpics(Meta metaData) {
        List<TestTag> featuresAndEpics = featureAndEpicTags(metaData);

        if (!featuresAndEpics.isEmpty()) {
            StepEventBus.getEventBus().addTagsToCurrentStory(featuresAndEpics);
        }
    }

    private void registerTags(Meta metaData) {
        List<TestTag> tags = getTagOrTagsPropertyValues(metaData);

        if (!tags.isEmpty()) {
            StepEventBus.getEventBus().addTagsToCurrentTest(tags);
        }
    }

    private Map<String, String> getMetadataFrom(Meta metaData) {
        Map<String, String> metadata = Maps.newHashMap();
        for (String propertyName : metaData.getPropertyNames()) {
            metadata.put(propertyName, metaData.getProperty(propertyName));
        }
        return metadata;
    }

    private void registerMetadata(Meta metaData) {
        Serenity.getCurrentSession().clearMetaData();

        Map<String, String> scenarioMetadata = getMetadataFrom(metaData);
        scenarioMetadata.putAll(storyMetadata);
        for (String key : scenarioMetadata.keySet()) {
            Serenity.getCurrentSession().addMetaData(key, scenarioMetadata.get(key));
        }
    }

    private void registerStoryTags(Meta metaData) {
        List<TestTag> tags = getTagOrTagsPropertyValues(metaData);

        if (!tags.isEmpty()) {
            StepEventBus.getEventBus().addTagsToCurrentStory(tags);
        }
    }

    private void registerStoryMeta(Meta metaData) {
        if (isPending(metaData)) {
            StepEventBus.getEventBus().suspendTest();
        } else if (isSkipped(metaData)) {
            StepEventBus.getEventBus().suspendTest();
        }
    }

    private Optional<TestResult> getStoryMetadataResult() {
        if (isPending(currentStory().getMeta())) {
            return Optional.of(TestResult.PENDING);
        } else if (isSkipped(currentStory().getMeta())) {
            return Optional.of(TestResult.SKIPPED);
        } else {
            return Optional.absent();
        }
    }

    private boolean isStoryManual(){
        return isManual(currentStory().getMeta());
    }

    private Optional<TestResult> getScenarioMetadataResult() {
       return forcedScenarioResult;
    }

    private void registerScenarioMeta(Meta metaData) {
        if (isPending(metaData)) {
            forcedScenarioResult = Optional.of(TestResult.PENDING);
        } else if (isSkipped(metaData)) {
            forcedScenarioResult = Optional.of(TestResult.SKIPPED);
        } else if (isManual(metaData) || isStoryManual()) {
            StepEventBus.getEventBus().testIsManual();
            StepEventBus.getEventBus().suspendTest();
        } else if (isIgnored(metaData)) {
            forcedScenarioResult = Optional.of(TestResult.IGNORED);
        }
    }

    private String removeSuffixFrom(String name) {
        return (name.contains(".")) ? name.substring(0, name.indexOf(".")) : name;
    }

    public void afterStory(boolean given) {
        shouldNestScenarios(false);
        if (given) {
            givenStoryMonitor.exitingGivenStory();
            givenStoryDone(currentStory());
        } else {
            closeBrowsersForThisStory();
            if (isAfterStory(currentStory())) {
                generateReports();
            } else if (!isFixture(currentStory()) && (!isAStoryLevelGiven(currentStory()))) {
                StepEventBus.getEventBus().testSuiteFinished();
                clearListeners();
            }
        }

        storyStack.pop();
    }

    private void closeBrowsersForThisStory() {
        if (drivers.containsKey(currentStory())) {
            drivers.get(currentStory()).close();
            drivers.get(currentStory()).quit();
            drivers.remove(currentStory());
        }
    }

    private boolean isAfterStory(Story currentStory) {
        return (currentStory.getName().equals("AfterStories"));
    }

    private synchronized void generateReports() {
        getReportService().generateReportsFor(getAllTestOutcomes());
    }

    public List<TestOutcome> getAllTestOutcomes() {
        return flatten(extract(baseStepListeners, on(BaseStepListener.class).getTestOutcomes()));
    }

    public void narrative(Narrative narrative) {
        logger.debug("narrative ".concat(narrative.toString()));
    }

    public void lifecyle(Lifecycle lifecycle) {
        logger.debug("lifecyle ".concat(lifecycle.toString()));
    }

    public void scenarioNotAllowed(Scenario scenario, String s) {
        logger.debug("scenarioNotAllowed ".concat(scenario.getTitle()));
        StepEventBus.getEventBus().testIgnored();
    }

    private void startScenarioCalled(String scenarioTitle) {
        StepEventBus.getEventBus().testStarted(scenarioTitle);
        activeScenarios.add(scenarioTitle);
    }

    private boolean shouldRestartDriverBeforeEachScenario() {
        return systemConfiguration.getEnvironmentVariables().getPropertyAsBoolean(
                SerenityJBehaveSystemProperties.RESTART_BROWSER_EACH_SCENARIO.getName(), false);
    }

    private boolean shouldResetStepsBeforeEachScenario() {
        return systemConfiguration.getEnvironmentVariables().getPropertyAsBoolean(
                SerenityJBehaveSystemProperties.RESET_STEPS_EACH_SCENARIO.getName(), true);
    }

    private boolean isMetaProcessed(final String scenarioTitle) {
        return scenarioMetaProcessed.contains(scenarioTitle);
    }

    public void scenarioMeta(Meta meta) {
        final String title = activeScenarios.peek();
        logger.debug("scenario:\"" + (StringUtils.isEmpty(title) ? " don't know name ": title) + "\" registering metadata for" + meta);
        if (!isMetaProcessed(title)) {
            registerIssues(meta);
            registerFeaturesAndEpics(meta);
            registerTags(meta);
            registerMetadata(meta);
            registerScenarioMeta(meta);
            if (isPendingScenario()) {
                StepEventBus.getEventBus().testPending();
            } else if (isSkippedScenario()) {
                StepEventBus.getEventBus().testSkipped();
            }
        }
    }

    private boolean isPending(Meta metaData) {
        return (metaData.hasProperty(PENDING));
    }

    private boolean isManual(Meta metaData) {
        return (metaData.hasProperty(MANUAL));
    }

    private boolean isSkipped(Meta metaData) {
        return (metaData.hasProperty(WIP)|| metaData.hasProperty(SKIP));
    }

    private boolean isIgnored(Meta metaData) {
        return (metaData.hasProperty(IGNORE));
    }

    public void afterScenario() {
        final String scenarioTitle = activeScenarios.peek();
        logger.debug("afterScenario : "+activeScenarios.peek());
        if(!isMetaProcessed(scenarioTitle)){
            scenarioMeta(scenarioMeta.get(scenarioTitle));
            scenarioMetaProcessed.add(scenarioTitle);
        }


        if (givenStoryMonitor.isInGivenStory() || shouldNestScenarios()) {
            StepEventBus.getEventBus().stepFinished();
        } else {
            if(isIgnoredScenario()) {
                StepEventBus.getEventBus().testIgnored();
            }else{
                StepEventBus.getEventBus().testFinished();
            }
            if (isPendingScenario() || isPendingStory()) {
                StepEventBus.getEventBus().setAllStepsTo(TestResult.PENDING);
            }
            if (isSkippedScenario() || isSkippedStory()) {
                StepEventBus.getEventBus().setAllStepsTo(TestResult.SKIPPED);
            }
            if(isIgnoredScenario()){
                StepEventBus.getEventBus().setAllStepsTo(TestResult.IGNORED);
            }
            activeScenarios.pop();
        }
    }

    private boolean isPendingScenario() {
        return (getStoryMetadataResult().or(TestResult.UNDEFINED) == TestResult.PENDING)
                || (getScenarioMetadataResult().or(TestResult.UNDEFINED) == TestResult.PENDING);
    }

    private boolean isSkippedScenario() {
        return (getStoryMetadataResult().or(TestResult.UNDEFINED) == TestResult.SKIPPED)
                || (getScenarioMetadataResult().or(TestResult.UNDEFINED) == TestResult.SKIPPED);
    }

    private boolean isIgnoredScenario() {
        return (getStoryMetadataResult().or(TestResult.UNDEFINED) == TestResult.IGNORED)
                || (getScenarioMetadataResult().or(TestResult.UNDEFINED) == TestResult.IGNORED);
    }

    private boolean isPendingStory() {
        return getStoryMetadataResult().or(TestResult.UNDEFINED) == TestResult.PENDING;
    }

    private boolean isSkippedStory() {
        return getStoryMetadataResult().or(TestResult.UNDEFINED) == TestResult.SKIPPED;
    }


    public void givenStories(GivenStories givenStories) {
        givenStoryMonitor.enteringGivenStory();
    }

    public void givenStories(List<String> strings) {
    }

    int exampleCount = 0;

    public void beforeExamples(List<String> steps, ExamplesTable table) {
        exampleCount = 0;
        StepEventBus.getEventBus().useExamplesFrom(serenityTableFrom(table));
    }

    private DataTable serenityTableFrom(ExamplesTable table) {
        return DataTable.withHeaders(table.getHeaders()).andMappedRows(table.getRows()).build();

    }

    public void example(Map<String, String> tableRow) {
        if (shouldRestartDriverBeforeEachScenario()) {
            WebdriverProxyFactory.resetDriver(ThucydidesWebDriverSupport.getDriver());
        }

        StepEventBus.getEventBus().clearStepFailures();
        if (executingExamples()) {
            finishExample();
        }
        restartPeriodically();
        startExample(tableRow);
    }

    private void startExample(Map<String, String> data) {
        StepEventBus.getEventBus().exampleStarted(data);
    }

    private void finishExample() {
        StepEventBus.getEventBus().exampleFinished();
    }

    private boolean executingExamples() {
        return (exampleCount > 0);
    }

    private void restartPeriodically() {
        exampleCount++;
        if (systemConfiguration.getRestartFrequency() > 0) {
            if (exampleCount % systemConfiguration.getRestartFrequency() == 0) {
                WebdriverProxyFactory.resetDriver(ThucydidesWebDriverSupport.getDriver());
            }
        }
    }

    public void afterExamples() {
        finishExample();
    }

    public void beforeStep(String stepTitle) {
        logger.debug("before step: ".concat(stepTitle));
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(stepTitle));
    }

    public void successful(String title) {
        logger.debug("successfull : ".concat(title));
        if (annotatedResultTakesPriority()) {
            processAnnotatedResult();
        } else{
            StepEventBus.getEventBus().updateCurrentStepTitle(normalized(title));
            StepEventBus.getEventBus().stepFinished();
        }
    }

    private void processAnnotatedResult() {
        TestResult forcedResult = StepEventBus.getEventBus().getForcedResult().get();
        switch (forcedResult) {
            case PENDING:
                StepEventBus.getEventBus().stepPending();
                break;
            case IGNORED:
                StepEventBus.getEventBus().stepIgnored();
                break;
            case SKIPPED:
                StepEventBus.getEventBus().stepIgnored();
                break;
            default:
                StepEventBus.getEventBus().stepIgnored();
        }

    }

    private boolean annotatedResultTakesPriority() {
        return StepEventBus.getEventBus().getForcedResult().isPresent();
    }

    public void ignorable(String title) {
        logger.debug("ignorable: ".concat(title));
        StepEventBus.getEventBus().updateCurrentStepTitle(normalized(title));
        StepEventBus.getEventBus().stepIgnored();
    }

    public void pending(String stepTitle) {
        logger.debug("pending: ".concat(stepTitle));
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(normalized(stepTitle)));
        StepEventBus.getEventBus().stepPending();

    }

    public void notPerformed(String stepTitle) {
        logger.debug("stepTitle: ".concat(stepTitle));
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(normalized(stepTitle)));
        StepEventBus.getEventBus().stepIgnored();
    }

    public void failed(String stepTitle, Throwable cause) {
        logger.debug("failed : ".concat(stepTitle));
        Throwable rootCause = cause.getCause() != null ? cause.getCause() : cause;
        StepEventBus.getEventBus().updateCurrentStepTitle(stepTitle);
        if (isAssumptionFailure(rootCause)) {
            StepEventBus.getEventBus().assumptionViolated(rootCause.getMessage());
        } else {
            StepEventBus.getEventBus().stepFailed(new StepFailure(ExecutedStepDescription.withTitle(normalized(stepTitle)), rootCause));
        }
    }

    private boolean isAssumptionFailure(Throwable rootCause) {
        return (AssumptionViolatedException.class.isAssignableFrom(rootCause.getClass()));
    }

    public void failedOutcomes(String s, OutcomesTable outcomesTable) {
    }

    public void restarted(String s, Throwable throwable) {
    }

    @Override
    public void restartedStory(Story story, Throwable cause) {

    }

    public void dryRun() {
        logger.debug("dryRun");
    }

    public void pendingMethods(List<String> strings) {
    }

    private String normalized(String value) {
        return value.replaceAll(OPEN_PARAM_CHAR, "{").replaceAll(CLOSE_PARAM_CHAR, "}");

    }
}
