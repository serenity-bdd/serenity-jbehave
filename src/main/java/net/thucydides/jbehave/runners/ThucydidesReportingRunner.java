package net.thucydides.jbehave.runners;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.codecentric.jbehave.junit.monitoring.JUnitDescriptionGenerator;
import de.codecentric.jbehave.junit.monitoring.JUnitScenarioReporter;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.jbehave.ThucydidesJBehaveSystemProperties;
import net.thucydides.jbehave.ThucydidesJUnitStories;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.io.StoryResourceNotFound;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.thucydides.core.ThucydidesSystemProperty.UNIQUE_BROWSER;
import static net.thucydides.jbehave.ThucydidesJBehaveSystemProperties.IGNORE_FAILURES_IN_STORIES;
import static net.thucydides.jbehave.ThucydidesJBehaveSystemProperties.METAFILTER;
import static net.thucydides.jbehave.ThucydidesJBehaveSystemProperties.STORY_TIMEOUT_IN_SECS;

public class ThucydidesReportingRunner extends Runner {
	private List<Description> storyDescriptions;
	private Embedder configuredEmbedder;
	private List<String> storyPaths;
	private Configuration configuration;
	private Description description;
	List<CandidateSteps> candidateSteps;

    private final ConfigurableEmbedder configurableEmbedder;
    private final Class<? extends ConfigurableEmbedder> testClass;
    private final EnvironmentVariables environmentVariables;

    private final String SKIP_FILTER = "-skip";
    private final String IGNORE_FILTER = "-ignore";
    private final String DEFAULT_METAFILTER = SKIP_FILTER + " " + IGNORE_FILTER;

    @SuppressWarnings("unchecked")
    public ThucydidesReportingRunner(Class<? extends ConfigurableEmbedder> testClass) throws Throwable {
        this(testClass, testClass.newInstance());
    }

    public ThucydidesReportingRunner(Class<? extends ConfigurableEmbedder> testClass,
                                     ConfigurableEmbedder embedder) throws Throwable {
        this.configurableEmbedder = embedder;
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

    Embedder getConfiguredEmbedder() {
        if (configuredEmbedder == null) {
            configuredEmbedder = configurableEmbedder.configuredEmbedder();
        }
        return configuredEmbedder;
    }

    List<String> getStoryPaths() {
        if ((storyPaths == null) || (storyPaths.isEmpty())) {
            try {
                if (configurableEmbedder instanceof JUnitStory) {
                    getStoryPathsFromJUnitStory();
                } else  if (configurableEmbedder instanceof JUnitStories) {
                    getStoryPathsFromJUnitStories(testClass);
                }
            } catch(Throwable e) {
                return Collections.EMPTY_LIST;
            }
        }
        return storyPaths;
    }

    private EnvironmentVariables environmentVariablesFrom(ConfigurableEmbedder configurableEmbedder) {
        if (configurableEmbedder instanceof ThucydidesJUnitStories) {
            return ((ThucydidesJUnitStories) configurableEmbedder).getSystemConfiguration().getEnvironmentVariables();
        } else {
            return Injectors.getInjector().getInstance(EnvironmentVariables.class);
        }
    }

    @Override
	public Description getDescription() {
        if (description == null) {
            description = Description.createSuiteDescription(configurableEmbedder.getClass());
            description.getChildren().addAll(getDescriptions());
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

        int threadCount = environmentVariables.getPropertyAsInteger(ThucydidesJBehaveSystemProperties.JBEHAVE_THREADS, 1);

        getConfiguredEmbedder().embedderControls().doIgnoreFailureInView(true);
        getConfiguredEmbedder().embedderControls().doIgnoreFailureInStories(getIgnoreFailuresInStories());
        getConfiguredEmbedder().embedderControls().useStoryTimeoutInSecs(getStoryTimeoutInSecs());
        getConfiguredEmbedder().embedderControls().useThreads(threadCount);
        if (metaFiltersAreDefined()) {
            getConfiguredEmbedder().useMetaFilters(getMetaFilters());
        }

        JUnitScenarioReporter junitReporter = new JUnitScenarioReporter(notifier, testCount(), getDescription());
		// tell the reporter how to handle pending steps
		junitReporter.usePendingStepStrategy(getConfiguration().pendingStepStrategy());
	
		addToStoryReporterFormats(junitReporter);

		try {
            getConfiguredEmbedder().runStoriesAsPaths(getStoryPaths());
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
            if (usingUniqueBrowser()) {
                ThucydidesWebDriverSupport.closeAllDrivers();
            }
            getConfiguredEmbedder().generateCrossReference();
		}
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
		for (CandidateSteps step : candidateSteps) {
			step.configuration().useStepMonitor(stepMonitor);
		}
	}

	private StepMonitor createCandidateStepsWithNoMonitor() {
		StepMonitor usedStepMonitor = getConfiguration().stepMonitor();
		createCandidateStepsWith(new NullStepMonitor());
		return usedStepMonitor;
	}

	private void getStoryPathsFromJUnitStory() {
		StoryPathResolver resolver = getConfiguredEmbedder().configuration().storyPathResolver();
		storyPaths = Arrays.asList(resolver.resolve(configurableEmbedder.getClass()));
	}

	@SuppressWarnings("unchecked")
	private void getStoryPathsFromJUnitStories(
			Class<? extends ConfigurableEmbedder> testClass)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Method method = makeStoryPathsMethodPublic(testClass);
		storyPaths = ((List<String>) method.invoke(configurableEmbedder, (Object[]) null));
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
			Embedder embedder = configurableEmbedder.configuredEmbedder();
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
		StoryRunner storyRunner = new StoryRunner();
		List<Description> storyDescriptions = new ArrayList<Description>();

		addSuite(storyDescriptions, "BeforeStories");
		addStories(storyDescriptions, storyRunner, descriptionGenerator);
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

    private void addStories(List<Description> storyDescriptions,
			StoryRunner storyRunner, JUnitDescriptionGenerator gen) {

        for (String storyPath : getStoryPaths()) {
            Story parseStory = storyRunner.storyOfPath(getConfiguration(), storyPath);
            Description descr = gen.createDescriptionFrom(parseStory);
            storyDescriptions.add(descr);
		}
	}

	private void addSuite(List<Description> storyDescriptions, String name) {
		storyDescriptions.add(Description.createTestDescription(Object.class,
				name));
	}

    //////////////////

    private boolean metaFiltersAreDefined() {
        String metaFilters = getMetafilterSetting();
        return !StringUtils.isEmpty(metaFilters);
    }

    private String getMetafilterSetting() {
        String metaFilters = environmentVariables.getProperty(METAFILTER.getName(),DEFAULT_METAFILTER);
        if (!metaFilters.contains(SKIP_FILTER)) {
            metaFilters = metaFilters + SKIP_FILTER;
        }
        if (!metaFilters.contains(IGNORE_FILTER)) {
            metaFilters = metaFilters + IGNORE_FILTER;
        }
        return metaFilters;
    }

    protected boolean getIgnoreFailuresInStories() {
        return environmentVariables.getPropertyAsBoolean(IGNORE_FAILURES_IN_STORIES.getName(),true);
    }

    protected int getStoryTimeoutInSecs() {
        return environmentVariables.getPropertyAsInteger(STORY_TIMEOUT_IN_SECS.getName(), 300);
    }

    protected List<String> getMetaFilters() {
        String metaFilters = getMetafilterSetting();
        return Lists.newArrayList(Splitter.on(",").trimResults().split(metaFilters));
    }

    public boolean usingUniqueBrowser() {
        return environmentVariables.getPropertyAsBoolean(UNIQUE_BROWSER, false);
    }
}