package net.thucydides.jbehave.runners;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import de.codecentric.jbehave.junit.monitoring.JUnitDescriptionGenerator;
import de.codecentric.jbehave.junit.monitoring.JUnitScenarioReporter;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.StoryRunner;
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

import static net.thucydides.jbehave.ThucydidesJBehaveSystemProperties.IGNORE_FAILURES_IN_STORIES;
import static net.thucydides.jbehave.ThucydidesJBehaveSystemProperties.METAFILTER;
import static net.thucydides.jbehave.ThucydidesJBehaveSystemProperties.STORY_TIMEOUT_IN_SECS;

public class ThucydidesReportingRunner extends Runner {
	private List<Description> storyDescriptions;
	private Embedder configuredEmbedder;
	private List<String> storyPaths;
	private Configuration configuration;
	private int numberOfTestCases;
	private Description rootDescription;
	List<CandidateSteps> candidateSteps;
	private ConfigurableEmbedder configurableEmbedder;
    private EnvironmentVariables environmentVariables;

	@SuppressWarnings("unchecked")
	public ThucydidesReportingRunner(Class<? extends ConfigurableEmbedder> testClass)
			throws Throwable {
        this(testClass, Injectors.getInjector().getInstance(EnvironmentVariables.class),
             (ConfigurableEmbedder) testClass.newInstance());
	}

    public ThucydidesReportingRunner(Class<? extends ConfigurableEmbedder> testClass,
                                     EnvironmentVariables environmentVariables,
                                     ConfigurableEmbedder embedder) throws Throwable {

        this.configurableEmbedder = embedder;
        this.environmentVariables = environmentVariables;

        if (configurableEmbedder instanceof JUnitStories) {
            getStoryPathsFromJUnitStories(testClass);
        } else if (configurableEmbedder instanceof JUnitStory) {
            getStoryPathsFromJUnitStory();
        }

        configuration = configuredEmbedder.configuration();

        StepMonitor originalStepMonitor = createCandidateStepsWithNoMonitor();
        storyDescriptions = buildDescriptionFromStories();
        createCandidateStepsWith(originalStepMonitor);

        initRootDescription();
    }

	@Override
	public Description getDescription() {
		return rootDescription;
	}

	@Override
	public int testCount() {
		return numberOfTestCases;
	}

	@Override
	public void run(RunNotifier notifier) {

        configuredEmbedder.embedderControls().doIgnoreFailureInView(true);
        configuredEmbedder.embedderControls().doIgnoreFailureInStories(getIgnoreFailuresInStories());
        configuredEmbedder.embedderControls().useStoryTimeoutInSecs(getStoryTimeoutInSecs());
        if (metaFiltersAreDefined()) {
            configuredEmbedder.useMetaFilters(getMetaFilters());
        }

        JUnitScenarioReporter junitReporter = new JUnitScenarioReporter(notifier, numberOfTestCases, rootDescription);
		// tell the reporter how to handle pending steps
		junitReporter.usePendingStepStrategy(configuration.pendingStepStrategy());
	
		addToStoryReporterFormats(junitReporter);
	
		try {
			configuredEmbedder.runStoriesAsPaths(storyPaths);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			configuredEmbedder.generateCrossReference();
		}
	}

	public static EmbedderControls recommandedControls(Embedder embedder) {
		return embedder.embedderControls()
		// don't throw an exception on generating reports for failing stories
				.doIgnoreFailureInView(true)
				// don't throw an exception when a story failed
				.doIgnoreFailureInStories(true)
				// .doVerboseFailures(true)
				.useThreads(1);
	}

	private void createCandidateStepsWith(StepMonitor stepMonitor) {
		// reset step monitor and recreate candidate steps
		configuration.useStepMonitor(stepMonitor);
		getCandidateSteps();
		for (CandidateSteps step : candidateSteps) {
			step.configuration().useStepMonitor(stepMonitor);
		}
	}

	private StepMonitor createCandidateStepsWithNoMonitor() {
		StepMonitor usedStepMonitor = configuration.stepMonitor();
		createCandidateStepsWith(new NullStepMonitor());
		return usedStepMonitor;
	}

	private void getStoryPathsFromJUnitStory() {
		configuredEmbedder = configurableEmbedder.configuredEmbedder();
		StoryPathResolver resolver = configuredEmbedder.configuration()
				.storyPathResolver();
		storyPaths = Arrays.asList(resolver.resolve(configurableEmbedder
				.getClass()));
	}

	@SuppressWarnings("unchecked")
	private void getStoryPathsFromJUnitStories(
			Class<? extends ConfigurableEmbedder> testClass)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		configuredEmbedder = configurableEmbedder.configuredEmbedder();
		Method method = makeStoryPathsMethodPublic(testClass);
		storyPaths = ((List<String>) method.invoke(
				(JUnitStories) configurableEmbedder, (Object[]) null));
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

	private void getCandidateSteps() {
		// candidateSteps = configurableEmbedder.configuredEmbedder()
		// .stepsFactory().createCandidateSteps();
		InjectableStepsFactory stepsFactory = configurableEmbedder
				.stepsFactory();
		if (stepsFactory != null) {
			candidateSteps = stepsFactory.createCandidateSteps();
		} else {
			Embedder embedder = configurableEmbedder.configuredEmbedder();
			candidateSteps = embedder.candidateSteps();
			if (candidateSteps == null || candidateSteps.isEmpty()) {
				candidateSteps = embedder.stepsFactory().createCandidateSteps();
			}
		}
	}

	private void initRootDescription() {
		rootDescription = Description
				.createSuiteDescription(configurableEmbedder.getClass());
		rootDescription.getChildren().addAll(storyDescriptions);
	}

	private void addToStoryReporterFormats(JUnitScenarioReporter junitReporter) {
		StoryReporterBuilder storyReporterBuilder = configuration
				.storyReporterBuilder();
		StoryReporterBuilder.ProvidedFormat junitReportFormat = new StoryReporterBuilder.ProvidedFormat(
				junitReporter);
		storyReporterBuilder.withFormats(junitReportFormat);
	}

	private List<Description> buildDescriptionFromStories() {
		JUnitDescriptionGenerator descriptionGenerator = new JUnitDescriptionGenerator(
				candidateSteps, configuration);
		StoryRunner storyRunner = new StoryRunner();
		List<Description> storyDescriptions = new ArrayList<Description>();

		addSuite(storyDescriptions, "BeforeStories");
		addStories(storyDescriptions, storyRunner, descriptionGenerator);
		addSuite(storyDescriptions, "AfterStories");

		numberOfTestCases += descriptionGenerator.getTestCases();

		return storyDescriptions;
	}

	private void addStories(List<Description> storyDescriptions,
			StoryRunner storyRunner, JUnitDescriptionGenerator gen) {
		for (String storyPath : storyPaths) {
			Story parseStory = storyRunner
					.storyOfPath(configuration, storyPath);
			Description descr = gen.createDescriptionFrom(parseStory);
			storyDescriptions.add(descr);
		}
	}

	private void addSuite(List<Description> storyDescriptions, String name) {
		storyDescriptions.add(Description.createTestDescription(Object.class,
				name));
		numberOfTestCases++;
	}

    //////////////////

    private boolean metaFiltersAreDefined() {
        String metaFilters = environmentVariables.getProperty(METAFILTER.getName());
        return !StringUtils.isEmpty(metaFilters);
    }

    protected boolean getIgnoreFailuresInStories() {
        return environmentVariables.getPropertyAsBoolean(IGNORE_FAILURES_IN_STORIES.getName(),true);
    }

    protected int getStoryTimeoutInSecs() {
        return environmentVariables.getPropertyAsInteger(STORY_TIMEOUT_IN_SECS.getName(), 300);
    }

    protected List<String> getMetaFilters() {
        String metaFilters = environmentVariables.getProperty(METAFILTER.getName());
        return Lists.newArrayList(Splitter.on(",").trimResults().split(metaFilters));
    }
}