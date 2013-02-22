package net.thucydides.jbehave;

import ch.lambdaj.function.convert.Converter;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.thucydides.core.ThucydidesListeners;
import net.thucydides.core.ThucydidesReports;
import net.thucydides.core.model.DataTable;
import net.thucydides.core.model.TestTag;
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
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.OutcomesTable;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.model.StoryDuration;
import org.jbehave.core.reporters.StoryReporter;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import static ch.lambdaj.Lambda.convert;

public class ThucydidesReporter implements StoryReporter {

    private ThreadLocal<ThucydidesListeners> thucydidesListenersThreadLocal;
    private ThreadLocal<ReportService> reportServiceThreadLocal;
    private final List<BaseStepListener> baseStepListeners;

    private final Configuration systemConfiguration;
    private static final String OPEN_PARAM_CHAR =  "\uff5f";
    private static final String CLOSE_PARAM_CHAR = "\uff60";

    public ThucydidesReporter(Configuration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
        thucydidesListenersThreadLocal = new ThreadLocal<ThucydidesListeners>();
        reportServiceThreadLocal = new ThreadLocal<ReportService>();
        baseStepListeners = Lists.newArrayList();

    }

    protected void clearListeners() {
        thucydidesListenersThreadLocal.remove();
        reportServiceThreadLocal.remove();
    }

    protected ThucydidesListeners getThucydidesListeners() {
        if (thucydidesListenersThreadLocal.get() == null) {
            ThucydidesListeners listeners = ThucydidesReports.setupListeners(systemConfiguration);
            thucydidesListenersThreadLocal.set(listeners);
            synchronized(baseStepListeners) {
                baseStepListeners.add(listeners.getBaseStepListener());
            }
        }
        return thucydidesListenersThreadLocal.get();
    }

    protected ReportService getReportService() {
        if (reportServiceThreadLocal.get() == null) {
            reportServiceThreadLocal.set(ThucydidesReports.getReportService(systemConfiguration));
        }
        return reportServiceThreadLocal.get();
    }

    public void storyNotAllowed(Story story, String filter) {
    }

    public void storyCancelled(Story story, StoryDuration storyDuration) {
    }

    private Story currentStory;

    private Stack<String> activeScenarios = new Stack<String>();

    public void beforeStory(Story story, boolean givenStory) {
        currentStory = story;
        if (!isFixture(story) && !givenStory) {

            activeScenarios.clear();

            configureDriver(story);

            ThucydidesStepFactory.resetContext();

            getThucydidesListeners().withDriver(ThucydidesWebDriverSupport.getDriver());

            String storyName = removeSuffixFrom(story.getName());
            String storyTitle = NameConverter.humanize(storyName);

            net.thucydides.core.model.Story userStory
                        = net.thucydides.core.model.Story.withIdAndPath(storyName, storyTitle, story.getPath())
                                                         .withNarrative(story.getNarrative().asA());
            StepEventBus.getEventBus().testSuiteStarted(userStory);

            registerTags(story);
        }
    }

    private void configureDriver(Story story) {
        StepEventBus.getEventBus().setUniqueSession(systemConfiguration.getUseUniqueBrowser());
        String requestedDriver = getRequestedDriver(story.getMeta());
        if (StringUtils.isNotEmpty(requestedDriver)) {
            ThucydidesWebDriverSupport.initialize(requestedDriver);
        } else {
            ThucydidesWebDriverSupport.initialize();
        }
    }

    private void registerTags(Story story) {
        registerStoryIssues(story.getMeta());
        registerStoryFeaturesAndEpics(story.getMeta());
        registerStoryTags(story.getMeta());
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
                return TestTag.withName(tagParts.get(1)).andType(tagParts.get(0));
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

    private void registerStoryTags(Meta metaData) {
        List<TestTag> tags = getTagOrTagsPropertyValues(metaData);

        if (!tags.isEmpty()) {
            StepEventBus.getEventBus().addTagsToCurrentStory(tags);
        }
    }
    private String removeSuffixFrom(String name) {
        return (name.contains(".")) ? name.substring(0, name.indexOf(".")) :  name;
    }

    public void afterStory(boolean given) {
        if (isAfterStory(currentStory)) {
            finishAnyActiveScenarios();
            closeBrowsersForThisStory();
            generateReports();
        } else if (!isFixture(currentStory) && !given) {
            StepEventBus.getEventBus().testSuiteFinished();
            clearListeners();
        }
    }

    private void finishAnyActiveScenarios() {
        while (!activeScenarios.isEmpty()) {
            afterScenario();
        }
    }

    private void closeBrowsersForThisStory() {
        if (!systemConfiguration.getUseUniqueBrowser()) {
            ThucydidesWebDriverSupport.closeAllDrivers();
        }
    }

    private boolean isAfterStory(Story currentStory) {
        return (currentStory.getName().equals("AfterStories"));
    }

    private synchronized void generateReports() {
        for(BaseStepListener listener : baseStepListeners) {
            getReportService().generateReportsFor(listener.getTestOutcomes());
        }
    }

    public void narrative(Narrative narrative) {}

    public void scenarioNotAllowed(Scenario scenario, String s) {
    }

    public void beforeScenario(String scenarioTitle) {
        if (shouldRestartDriverBeforeEachScenario()) {
            WebdriverProxyFactory.resetDriver(ThucydidesWebDriverSupport.getDriver());
        }
        StepEventBus.getEventBus().testStarted(scenarioTitle);
        activeScenarios.add(scenarioTitle);
    }

    private boolean shouldRestartDriverBeforeEachScenario() {
        return systemConfiguration.getEnvironmentVariables().getPropertyAsBoolean(
               ThucydidesJBehaveSystemProperties.RESTART_BROWSER_EACH_SCENARIO.getName(), false);
    }


    public void scenarioMeta(Meta meta) {
        registerIssues(meta);
        registerFeaturesAndEpics(meta);
        registerTags(meta);
    }

    public void afterScenario() {
        StepEventBus.getEventBus().testFinished();
        activeScenarios.pop();
    }

    public void givenStories(GivenStories givenStories) {
    }

    public void givenStories(List<String> strings) {
    }

    List<Map<String,String>> exampleData;
    int exampleCount = 0;
    public void beforeExamples(List<String> steps, ExamplesTable table) {
        exampleCount = 0;
        exampleData = ImmutableList.copyOf(table.getRows());
        StepEventBus.getEventBus().useExamplesFrom(thucydidesTableFrom(table));
    }

    private DataTable thucydidesTableFrom(ExamplesTable table) {
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
        startExample();
    }

    private void startExample() {
        Map<String,String> data = exampleData.get(exampleCount - 1);
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
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(stepTitle));
    }

    public void successful(String title) {
        StepEventBus.getEventBus().updateCurrentStepTitle(normalized(title));
        StepEventBus.getEventBus().stepFinished();
    }

    public void ignorable(String title) {
        StepEventBus.getEventBus().updateCurrentStepTitle(normalized(title));
        StepEventBus.getEventBus().stepIgnored();
    }

    public void pending(String stepTitle) {
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(normalized(stepTitle)));
        StepEventBus.getEventBus().stepPending();
    }

    public void notPerformed(String stepTitle) {
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(normalized(stepTitle)));
        StepEventBus.getEventBus().stepIgnored();
    }

    public void failed(String stepTitle, Throwable cause) {
        Throwable rootCause = cause.getCause() != null ? cause.getCause() : cause;
        StepEventBus.getEventBus().updateCurrentStepTitle(stepTitle);
        StepEventBus.getEventBus().stepFailed(new StepFailure(ExecutedStepDescription.withTitle(normalized(stepTitle)), rootCause));
    }

    public void failedOutcomes(String s, OutcomesTable outcomesTable) {
    }

    public void restarted(String s, Throwable throwable) {
    }

    public void dryRun() {
    }

    public void pendingMethods(List<String> strings) {
    }

    private String normalized(String value) {
        return value.replaceAll(OPEN_PARAM_CHAR, "{").replaceAll(CLOSE_PARAM_CHAR,"}");

    }
}
