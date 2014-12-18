package net.serenity_bdd.jbehave.runners;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import de.codecentric.jbehave.junit.monitoring.JUnitDescriptionGenerator;
import de.codecentric.jbehave.junit.monitoring.JUnitScenarioReporter;
import net.serenity_bdd.jbehave.SerenityStories;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.jbehave.annotations.Metafilter;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.Embedder;
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
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static net.serenity_bdd.jbehave.SerenityJBehaveSystemProperties.IGNORE_FAILURES_IN_STORIES;
import static net.serenity_bdd.jbehave.SerenityJBehaveSystemProperties.IGNORE_FAILURES_IN_VIEW;
import static net.serenity_bdd.jbehave.SerenityJBehaveSystemProperties.METAFILTER;
import static net.serenity_bdd.jbehave.SerenityJBehaveSystemProperties.STORY_TIMEOUT_IN_SECS;
import static net.thucydides.core.ThucydidesSystemProperty.THUCYDIDES_USE_UNIQUE_BROWSER;

public class SerenityReportingRunner extends Runner {
	private List<Description> storyDescriptions;
	private Embedder configuredEmbedder;
	private List<String> storyPaths;
	private Configuration configuration;
	private Description description;
	List<CandidateSteps> candidateSteps;

    private final ConfigurableEmbedder configurableEmbedder;
    private final Class<? extends ConfigurableEmbedder> testClass;
    private final EnvironmentVariables environmentVariables;

//    private final String SKIP_FILTER = " -skip";
    private final String IGNORE_FILTER = " -ignore";
    private final String DEFAULT_METAFILTER = IGNORE_FILTER;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SerenityReportingRunner.class);


    @SuppressWarnings("unchecked")
    public SerenityReportingRunner(Class<? extends ConfigurableEmbedder> testClass) throws Throwable {
        this(testClass, testClass.newInstance());
    }

    public SerenityReportingRunner(Class<? extends ConfigurableEmbedder> testClass,
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
                LOGGER.error("Could not load story paths",e);
                return Collections.EMPTY_LIST;
            }
        }
        return storyPaths;
    }

    private EnvironmentVariables environmentVariablesFrom(ConfigurableEmbedder configurableEmbedder) {
        if (configurableEmbedder instanceof SerenityStories) {
            return ((SerenityStories) configurableEmbedder).getEnvironmentVariables();
        } else {
            return Injectors.getInjector().getProvider(EnvironmentVariables.class).get() ;
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
        getConfiguredEmbedder().embedderControls().doIgnoreFailureInView(getIgnoreFailuresInView());
        getConfiguredEmbedder().embedderControls().doIgnoreFailureInStories(getIgnoreFailuresInStories());
        getConfiguredEmbedder().embedderControls().useStoryTimeoutInSecs(getStoryTimeoutInSecs());
        if (metaFiltersAreDefined()) {
            getConfiguredEmbedder().useMetaFilters(getMetaFilters());
        }

        JUnitScenarioReporter junitReporter = new JUnitScenarioReporter(notifier, testCount(), getDescription(), new Keywords());
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
        shutdownTestSuite();
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
		List<Description> storyDescriptions = new ArrayList<>();

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
        Optional<String> environmentMetafilters = getEnvironmentMetafilters();
        Optional<String> annotatedMetafilters = getAnnotatedMetafilters(testClass);
        String metafilters = environmentMetafilters.or(annotatedMetafilters.or(DEFAULT_METAFILTER));
        if (isGroovy(metafilters)) {
            metafilters = addGroovyMetafilterValuesTo(metafilters);
        } else {
            metafilters = addDefaultMetafilterValuesTo(metafilters);
        }
        return metafilters;
    }

    private Optional<String> getEnvironmentMetafilters() {
        return Optional.fromNullable(environmentVariables.getProperty(METAFILTER.getName()));
    }

    private Optional<String> getAnnotatedMetafilters(Class<? extends ConfigurableEmbedder> testClass) {
        return (testClass.getAnnotation(Metafilter.class) != null) ?
                Optional.of(testClass.getAnnotation(Metafilter.class).value()) : Optional.<String>absent();
    }

    private boolean isGroovy(String metaFilters) {
        return (metaFilters != null) && (metaFilters.startsWith("groovy:"));
    }

    private String addDefaultMetafilterValuesTo(String metaFilters) {
        if (!metaFilters.contains(IGNORE_FILTER)) {
            metaFilters = metaFilters + ", " + IGNORE_FILTER;
        }
        return metaFilters;
    }

    private String addGroovyMetafilterValuesTo(String metaFilters) {
        String skipAndIgnore = "";
        if (!metaFilters.contains("skip")) {
            skipAndIgnore = skipAndIgnore + " && !skip";
        }
        if (!metaFilters.contains("ignore")) {
            skipAndIgnore = skipAndIgnore + " && !ignore";
        }
        if (!skipAndIgnore.isEmpty()) {
            return "groovy: (" + metaFilters.substring(7).trim() + ") " + skipAndIgnore;
        } else {
            return metaFilters;
        }
    }
    protected boolean getIgnoreFailuresInStories() {
        return environmentVariables.getPropertyAsBoolean(IGNORE_FAILURES_IN_STORIES.getName(),true);
    }

    protected int getStoryTimeoutInSecs() {
        return environmentVariables.getPropertyAsInteger(STORY_TIMEOUT_IN_SECS.getName(), 300);
    }

    protected List<String> getMetaFilters() {
        String metaFilters = getMetafilterSetting();
        return Lists.newArrayList(Splitter.on(Pattern.compile(",")).trimResults().omitEmptyStrings().split(metaFilters));
    }

    public boolean usingUniqueBrowser() {
        return environmentVariables.getPropertyAsBoolean(THUCYDIDES_USE_UNIQUE_BROWSER, false);
    }

    protected boolean getIgnoreFailuresInView() { return environmentVariables.getPropertyAsBoolean(IGNORE_FAILURES_IN_VIEW.getName(),true); }
}
