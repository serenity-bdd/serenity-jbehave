package net.serenitybdd.jbehave.runners;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import com.github.valfirst.jbehave.junit.monitoring.JUnitScenarioReporter;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.serenitybdd.core.exceptions.SerenityManagedException;
import net.serenitybdd.jbehave.SerenityJBehaveSystemProperties;
import net.serenitybdd.jbehave.SerenityStories;
import net.serenitybdd.jbehave.annotations.Metafilter;
import net.serenitybdd.jbehave.embedders.ExtendedEmbedder;
import net.serenitybdd.jbehave.embedders.monitors.ReportingEmbedderMonitor;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.util.EnvironmentVariables;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.PerformableTree;
import org.jbehave.core.embedder.PerformableTree.RunContext;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.NullStepMonitor;
import org.jbehave.core.steps.StepMonitor;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SerenityReportingRunner extends Runner {

    private List<Description> storyDescriptions;
    private ExtendedEmbedder configuredEmbedder;
    private List<String> storyPaths;
    private Configuration configuration;
    private Description description;
    List<CandidateSteps> candidateSteps;

    private final ConfigurableEmbedder configurableEmbedder;
    private final Class<? extends ConfigurableEmbedder> testClass;
    private final EnvironmentVariables environmentVariables;

    private static final Logger LOGGER = LoggerFactory.getLogger(SerenityReportingRunner.class);
    private boolean runningInMaven;

    @SuppressWarnings("unchecked")
    public SerenityReportingRunner(Class<? extends ConfigurableEmbedder> testClass) throws Throwable {
        this(testClass, testClass.newInstance());
    }

    public SerenityReportingRunner(Class<? extends ConfigurableEmbedder> testClass,
                                   ConfigurableEmbedder embedder) {
        this.configurableEmbedder = embedder;
        ExtendedEmbedder extendedEmbedder = new ExtendedEmbedder(this.configurableEmbedder.configuredEmbedder());
        extendedEmbedder.getEmbedderMonitor().subscribe(new ReportingEmbedderMonitor(
                ((SerenityStories) embedder).getSystemConfiguration(), extendedEmbedder));
        this.configurableEmbedder.useEmbedder(extendedEmbedder);
        this.testClass = testClass;
        this.environmentVariables = environmentVariablesFrom(configurableEmbedder);
    }

    protected List<Description> getDescriptions() {
        if (storyDescriptions == null) {
            storyDescriptions = buildDescriptionFromStories();
        }
        return storyDescriptions;
    }

    protected Configuration getConfiguration() {
        if (configuration == null) {
            configuration = getConfiguredEmbedder().configuration();
        }
        return configuration;
    }

    public ExtendedEmbedder getConfiguredEmbedder() {
        if (configuredEmbedder == null) {
            configuredEmbedder = (ExtendedEmbedder) configurableEmbedder.configuredEmbedder();
        }
        return configuredEmbedder;
    }

    List<String> getStoryPaths() {
        if ((storyPaths == null) || (storyPaths.isEmpty())) {
            storyPaths = storyPathsFromRunnerClass();
        }
        return storyPaths;
    }

    private List<String> storyPathsFromRunnerClass() {
        try {
            List<String> storyPaths = new ArrayList<>();

            if (configurableEmbedder instanceof JUnitStory) {
                storyPaths = getStoryPathsFromJUnitStory();
            } else if (configurableEmbedder instanceof JUnitStories) {
                storyPaths = getStoryPathsFromJUnitStories(testClass);
            }

            String storyFilter = getStoryFilterFrom(configurableEmbedder);

            return storyPaths.stream()
                             .filter(story -> story.matches(storyFilter))
                             .collect(Collectors.toList());

        } catch (Throwable e) {
            LOGGER.error("Could not load story paths", e);
            return Collections.EMPTY_LIST;
        }
    }

    private String getStoryFilterFrom(ConfigurableEmbedder embedder) {

        String defaultStoryFilter = environmentVariables.getProperty(SerenityJBehaveSystemProperties.STORY_FILTER.getName(), ".*");

        Optional<Method> getStoryFilter = Arrays.stream(embedder.getClass().getMethods())
                .filter(method -> method.getName().equals("getStoryFilter"))
                .findFirst();

        if (getStoryFilter.isPresent()) {
            try {
                Optional<Object> storyFilterValue = Optional.ofNullable(getStoryFilter.get().invoke(embedder));
                return storyFilterValue.orElse(defaultStoryFilter).toString();
            } catch (IllegalAccessException | InvocationTargetException e) {
                LOGGER.warn("Could not invoke getStoryFilter() method on " + embedder, e);
            }
        }
        return defaultStoryFilter;
    }

    private EnvironmentVariables environmentVariablesFrom(ConfigurableEmbedder configurableEmbedder) {
        if (configurableEmbedder instanceof SerenityStories) {
            return ((SerenityStories) configurableEmbedder).getEnvironmentVariables();
        } else {
            return Injectors.getInjector().getProvider(EnvironmentVariables.class).get();
        }
    }

    @Override
    public Description getDescription() {
        if (description == null) {
            description = Description.createSuiteDescription(configurableEmbedder.getClass());
            for (Description childDescription : getDescriptions()) {
                description.addChild(childDescription);
            }
        }
        return description;
    }

    private int testCount = 0;

    @Override
    public int testCount() {
        if (testCount == 0) {
            testCount = countStories();
        }
        return testCount;
    }

    @Override
    public void run(RunNotifier notifier) {

        beforeStoriesRun(getConfiguredEmbedder());

        getConfiguredEmbedder().embedderControls().doIgnoreFailureInView(getIgnoreFailuresInView());
        getConfiguredEmbedder().embedderControls().doIgnoreFailureInStories(getIgnoreFailuresInStories());
        getConfiguredEmbedder().embedderControls().useStoryTimeoutInSecs(getStoryTimeoutInSecs());
        getConfiguredEmbedder().embedderControls().useStoryTimeouts(getStoryTimeout());
        getConfiguredEmbedder().embedderControls().useThreads(getThreadCount());

        if (metaFiltersAreDefined()) {
            getConfiguredEmbedder().useMetaFilters(getMetaFilters());
        }

//      if (!isRunningInMaven() && !isRunningInGradle()) {

        JUnitScenarioReporter junitReporter = new JUnitScenarioReporter(notifier, testCount(), getDescription(),
                getConfiguredEmbedder().configuration().keywords());
        // tell the reporter how to handle pending steps
        junitReporter.usePendingStepStrategy(getConfiguration().pendingStepStrategy());

        JUnitReportingRunner.recommendedControls(getConfiguredEmbedder());

        addToStoryReporterFormats(junitReporter);
//      }

        try {
            getConfiguredEmbedder().runStoriesAsPaths(getStoryPaths());
        } catch (Throwable e) {
            throw new SerenityManagedException(e);
        } finally {
            getConfiguredEmbedder().generateCrossReference();
        }
        shutdownTestSuite();
    }

    private boolean isRunningInGradle() {
        return Stream.of(new Exception().getStackTrace()).anyMatch(elt -> elt.getClassName().startsWith("org.gradle"));
    }

    /**
     * Override this method to add custom configuration to the JBehave embedder object.
     *
     * @param configuredEmbedder
     */
    public void beforeStoriesRun(ExtendedEmbedder configuredEmbedder) {
    }

    private void shutdownTestSuite() {
        StepEventBus.getEventBus().testSuiteFinished();
    }

    List<CandidateSteps> getCandidateSteps() {
        if (candidateSteps == null) {
            StepMonitor originalStepMonitor = createCandidateStepsWithNoMonitor();
            createCandidateStepsWith(originalStepMonitor);
        }
        return candidateSteps;
    }

    private void createCandidateStepsWith(StepMonitor stepMonitor) {
        // reset step monitor and recreate candidate steps
        getConfiguration().useStepMonitor(stepMonitor);
        candidateSteps = buildCandidateSteps();
        candidateSteps.forEach(
                step -> step.configuration().useStepMonitor(stepMonitor)
        );
    }

    private StepMonitor createCandidateStepsWithNoMonitor() {
        StepMonitor usedStepMonitor = getConfiguration().stepMonitor();
        createCandidateStepsWith(new NullStepMonitor());
        return usedStepMonitor;
    }

    private List<String> getStoryPathsFromJUnitStory() {
        StoryPathResolver resolver = getConfiguredEmbedder().configuration().storyPathResolver();
        return Arrays.asList(resolver.resolve(configurableEmbedder.getClass()));
    }

    @SuppressWarnings("unchecked")
    private List<String> getStoryPathsFromJUnitStories(
            Class<? extends ConfigurableEmbedder> testClass)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        Method method = makeStoryPathsMethodPublic(testClass);
        return ((List<String>) method.invoke(configurableEmbedder, (Object[]) null));
    }

    private Method makeStoryPathsMethodPublic(
            Class<? extends ConfigurableEmbedder> testClass)
            throws NoSuchMethodException {
        Method method;
        try {
            method = testClass.getDeclaredMethod("storyPaths", (Class[]) null);
        } catch (NoSuchMethodException e) {
            method = testClass.getMethod("storyPaths", (Class[]) null);
        }
        method.setAccessible(true);
        return method;
    }

    private List<CandidateSteps> buildCandidateSteps() {
        List<CandidateSteps> candidateSteps;

        InjectableStepsFactory stepsFactory = configurableEmbedder
                .stepsFactory();
        if (stepsFactory != null) {
            candidateSteps = stepsFactory.createCandidateSteps();
        } else {
            Embedder embedder = getConfiguredEmbedder();
            candidateSteps = embedder.candidateSteps();
            if (candidateSteps == null || candidateSteps.isEmpty()) {
                candidateSteps = embedder.stepsFactory().createCandidateSteps();
            }
        }
        return candidateSteps;
    }

    private void addToStoryReporterFormats(JUnitScenarioReporter junitReporter) {
        StoryReporterBuilder storyReporterBuilder = getConfiguration().storyReporterBuilder();
        StoryReporterBuilder.ProvidedFormat junitReportFormat
                = new StoryReporterBuilder.ProvidedFormat(junitReporter);
        storyReporterBuilder.withFormats(junitReportFormat);
    }

    private List<Description> buildDescriptionFromStories() {
        JUnitDescriptionGenerator descriptionGenerator = new JUnitDescriptionGenerator(getCandidateSteps(), getConfiguration());
        List<Description> storyDescriptions = new ArrayList<>();

        addSuite(storyDescriptions, "BeforeStories");
        storyDescriptions.addAll(descriptionGenerator.createDescriptionFrom(createPerformableTree(getStoryPaths())));
        addSuite(storyDescriptions, "AfterStories");

        return storyDescriptions;
    }

    private int countStories() {
        JUnitDescriptionGenerator descriptionGenerator = new JUnitDescriptionGenerator(getCandidateSteps(), getConfiguration());
        return descriptionGenerator.getTestCases() + beforeAndAfterStorySteps();
    }

    private int beforeAndAfterStorySteps() {
        return 2;
    }

    private PerformableTree createPerformableTree(List<String> storyPaths) {
        ExtendedEmbedder configuredEmbedder = this.getConfiguredEmbedder();
        configuredEmbedder.useMetaFilters(getMetaFilters());
        BatchFailures failures = new BatchFailures(configuredEmbedder.embedderControls().verboseFailures());
        PerformableTree performableTree = new PerformableTree();
        RunContext context = performableTree.newRunContext(getConfiguration(), configuredEmbedder.stepsFactory(),
                configuredEmbedder.embedderMonitor(), configuredEmbedder.metaFilter(), failures);
        performableTree.addStories(context, storiesOf(performableTree, storyPaths));
        return performableTree;
    }

    private List<Story> storiesOf(PerformableTree performableTree, List<String> storyPaths) {

        final Configuration configuration = getConfiguration();

        return storyPaths.parallelStream().map(
                storyPath -> performableTree.storyOfPath(configuration, storyPath)
        ).collect(Collectors.toList());
    }

    private void addSuite(List<Description> storyDescriptions, String name) {
        storyDescriptions.add(Description.createTestDescription(Object.class,
                name));
    }

    private boolean metaFiltersAreDefined() {
        String metaFilters = getMetafilterSetting();
        return !StringUtils.isEmpty(metaFilters);
    }

    private String getMetafilterSetting() {
        Optional<String> environmentMetafilters = getEnvironmentMetafilters();
        Optional<String> annotatedMetafilters = getAnnotatedMetafilters(testClass);
        Optional<String> thucAnnotatedMetafilters = getThucAnnotatedMetafilters(testClass);
        return environmentMetafilters.orElse(annotatedMetafilters.orElse(thucAnnotatedMetafilters.orElse("")));
    }

    private Optional<String> getEnvironmentMetafilters() {
        return Optional.ofNullable(environmentVariables.getProperty(SerenityJBehaveSystemProperties.METAFILTER.getName()));
    }

    /**
     * When Metafilter in thucydides package is removed, this method and callers will be removed
     *
     * @param testClass
     * @return
     */
    @Deprecated
    private Optional<String> getThucAnnotatedMetafilters(Class<? extends ConfigurableEmbedder> testClass) {
        return (testClass.getAnnotation(net.thucydides.jbehave.annotations.Metafilter.class) != null) ?
                Optional.of(testClass.getAnnotation(net.thucydides.jbehave.annotations.Metafilter.class).value()) : Optional.empty();
    }

    private Optional<String> getAnnotatedMetafilters(Class<? extends ConfigurableEmbedder> testClass) {
        return (testClass.getAnnotation(Metafilter.class) != null) ?
                Optional.of(testClass.getAnnotation(Metafilter.class).value()) : Optional.empty();
    }

    protected boolean getIgnoreFailuresInStories() {
        return environmentVariables.getPropertyAsBoolean(SerenityJBehaveSystemProperties.IGNORE_FAILURES_IN_STORIES.getName(), false);
    }

    protected int getStoryTimeoutInSecs() {
        return environmentVariables.getPropertyAsInteger(SerenityJBehaveSystemProperties.STORY_TIMEOUT_IN_SECS.getName(),
                (int) getConfiguredEmbedder().embedderControls().storyTimeoutInSecs());
    }

    protected int getThreadCount() {
        return environmentVariables.getPropertyAsInteger(SerenityJBehaveSystemProperties.JBEHAVE_THREADS.getName(), 1);
    }

    protected String getStoryTimeout() {
        return environmentVariables.getProperty(
                SerenityJBehaveSystemProperties.STORY_TIMEOUT.getName(),
                getConfiguredEmbedder().embedderControls().storyTimeouts());
    }

    protected List<String> getMetaFilters() {
        String metaFilters = getMetafilterSetting();
        return Lists.newArrayList(Splitter.on(Pattern.compile(",")).trimResults().omitEmptyStrings().split(metaFilters));
    }

    protected boolean getIgnoreFailuresInView() {
        return environmentVariables.getPropertyAsBoolean(SerenityJBehaveSystemProperties.IGNORE_FAILURES_IN_VIEW.getName(), true);
    }

    public boolean isRunningInMaven() {
        return Stream.of(new Exception().getStackTrace()).anyMatch(elt -> elt.getClassName().contains("maven"));
    }
}
