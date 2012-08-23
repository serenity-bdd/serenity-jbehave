package net.thucydides.jbehave;

import ch.lambdaj.function.convert.Converter;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.thucydides.core.ThucydidesListeners;
import net.thucydides.core.ThucydidesReports;
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

import static ch.lambdaj.Lambda.convert;

public class ThucydidesReporter implements StoryReporter {

    private ThreadLocal<ThucydidesListeners> thucydidesListenersThreadLocal;
    private ThreadLocal<ReportService> reportServiceThreadLocal;
    private List<BaseStepListener> baseStepListeners;

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
            System.out.println("Initialize listeners");
            ThucydidesListeners listeners = ThucydidesReports.setupListeners(systemConfiguration);
            thucydidesListenersThreadLocal.set(listeners);
            synchronized(baseStepListeners) {
                baseStepListeners.add(listeners.getBaseStepListener());
            }
            System.out.println("Initialize listeners done");
        }
        return thucydidesListenersThreadLocal.get();
    }

    protected ReportService getReportService() {
        if (reportServiceThreadLocal.get() == null) {
            reportServiceThreadLocal.set(ThucydidesReports.getReportService(systemConfiguration));
        }
        return reportServiceThreadLocal.get();
    }

    public void storyNotAllowed(Story story, String s) {
    }

    public void storyCancelled(Story story, StoryDuration storyDuration) {
    }

    private Story currentStory;

    public void beforeStory(Story story, boolean b) {
        currentStory = story;
        if (!isFixture(story)) {

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
        return metaData.getProperty("driver");
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
            @Override
            public TestTag convert(String tag) {
                List<String> tagParts = Lists.newArrayList(Splitter.on(":").trimResults().split(tag));
                return TestTag.withName(tagParts.get(1)).andType(tagParts.get(0));
            }
        };
    }

    private Converter<String, TestTag> toFeatureTags() {
        return new Converter<String, TestTag>() {
            @Override
            public TestTag convert(String featureName) {
                return TestTag.withName(featureName).andType("feature");
            }
        };
    }

    private Converter<String, TestTag> toEpicTags() {
        return new Converter<String, TestTag>() {
            @Override
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
        System.out.println("afterStory");
        if (isAfterStory(currentStory)) {
            ThucydidesWebDriverSupport.closeAllDrivers();
            generateReports();
        } else if (!isFixture(currentStory)) {
            StepEventBus.getEventBus().testSuiteFinished();
            clearListeners();
        }
    }

    private boolean isAfterStory(Story currentStory) {
        return (currentStory.getName().equals("AfterStories"));
    }

    private synchronized void generateReports() {
        System.out.println("generateReports");
        for(BaseStepListener listener : baseStepListeners) {
            getReportService().generateReportsFor(listener.getTestOutcomes());
        }
        System.out.println("generateReports done");
    }

    public void narrative(Narrative narrative) {}

    public void scenarioNotAllowed(Scenario scenario, String s) {
    }

    public void beforeScenario(String scenarioTitle) {
        StepEventBus.getEventBus().testStarted(scenarioTitle);
    }

    public void scenarioMeta(Meta meta) {
        registerIssues(meta);
        registerFeaturesAndEpics(meta);
        registerTags(meta);
    }

    public void afterScenario() {
        StepEventBus.getEventBus().testFinished();
    }

    public void givenStories(GivenStories givenStories) {
    }

    public void givenStories(List<String> strings) {
    }

    public void beforeExamples(List<String> strings, ExamplesTable examplesTable) {
    }

    public void example(Map<String, String> stringStringMap) {
    }

    public void afterExamples() {
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

        StepEventBus.getEventBus().updateCurrentStepTitle(stepTitle);
        StepEventBus.getEventBus().stepFailed(new StepFailure(ExecutedStepDescription.withTitle(normalized(stepTitle)), cause));
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
