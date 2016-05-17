package net.serenitybdd.jbehave.runners;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import de.codecentric.jbehave.junit.monitoring.JUnitDescriptionGenerator;
import de.codecentric.jbehave.junit.monitoring.JUnitScenarioReporter;
import net.serenitybdd.jbehave.SerenityJBehaveSystemProperties;
import net.serenitybdd.jbehave.SerenityStories;
import net.serenitybdd.jbehave.annotations.Metafilter;
import net.serenitybdd.jbehave.embedders.ExtendedEmbedder;
import net.serenitybdd.jbehave.embedders.monitors.ReportingEmbedderMonitor;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static net.thucydides.core.ThucydidesSystemProperty.THUCYDIDES_USE_UNIQUE_BROWSER;

public class SerenityReportingRunner extends Runner {
    private static final Logger logger = LoggerFactory.getLogger(SerenityReportingRunner.class);

	private List<Description> storyDescriptions;
	private ExtendedEmbedder configuredEmbedder;
	private List<String> storyPaths;
	private Configuration configuration;
	private Description description;
	List<CandidateSteps> candidateSteps;
    private final ExtendedEmbedder extendedEmbedder;

    private final ConfigurableEmbedder configurableEmbedder;
    private final Class<? extends ConfigurableEmbedder> testClass;
    private final EnvironmentVariables environmentVariables;

    private final String SKIP_FILTER = " -skip";
    private final String IGNORE_FILTER = " -ignore";
    private final String WIP_FILTER = " -wip";

    private final String DEFAULT_METAFILTER = IGNORE_FILTER+" "+SKIP_FILTER+" "+WIP_FILTER;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SerenityReportingRunner.class);


    @SuppressWarnings("unchecked")
    public SerenityReportingRunner(Class<? extends ConfigurableEmbedder> testClass) throws Throwable {
        this(testClass, testClass.newInstance());
    }

    public SerenityReportingRunner(Class<? extends ConfigurableEmbedder> testClass,
                                   ConfigurableEmbedder embedder) throws Throwable {
        this.configurableEmbedder = embedder;
        this.extendedEmbedder = new ExtendedEmbedder(this.configurableEmbedder.configuredEmbedder());
        this.extendedEmbedder.getEmbedderMonitor().subscribe(new ReportingEmbedderMonitor(
                ((SerenityStories)embedder).getSystemConfiguration(),
                this.extendedEmbedder));
        this.configurableEmbedder.useEmbedder(this.extendedEmbedder);
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

    ExtendedEmbedder getConfiguredEmbedder() {
        if (configuredEmbedder == null) {
            configuredEmbedder = (ExtendedEmbedder)configurableEmbedder.configuredEmbedder();
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
            for (Description childDescription: getDescriptions()) {
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
        getConfiguredEmbedder().embedderControls().doIgnoreFailureInView(getIgnoreFailuresInView());
        getConfiguredEmbedder().embedderControls().doIgnoreFailureInStories(getIgnoreFailuresInStories());
        getConfiguredEmbedder().embedderControls().useStoryTimeoutInSecs(getStoryTimeoutInSecs());
        if (metaFiltersAreDefined()) {
            getConfiguredEmbedder().useMetaFilters(getMetaFilters());
        }

        JUnitScenarioReporter junitReporter = new JUnitScenarioReporter(notifier, testCount(), getDescription(),
                getConfiguredEmbedder().configuration().keywords());
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
            this.extendedEmbedder.registerStory(storyPath,parseStory);
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
        Optional<String> thucAnnotatedMetafilters = getThucAnnotatedMetafilters(testClass);
        String metafilters = environmentMetafilters.or(annotatedMetafilters.or(
                thucAnnotatedMetafilters.or("")));
        if (isGroovy(metafilters)) {
            metafilters = addGroovyMetafilterValuesTo(metafilters);
        }else{
            metafilters = addMetafilterValuesTo(metafilters);
        }
        return metafilters;
    }

    private Optional<String> getEnvironmentMetafilters() {
        return Optional.fromNullable(environmentVariables.getProperty(SerenityJBehaveSystemProperties.METAFILTER.getName()));
    }

    /**
     * When Metafilter in thucydides package is removed, this method and callers will be removed
     * @param testClass
     * @return
     */
    @Deprecated
    private Optional<String> getThucAnnotatedMetafilters(Class<? extends ConfigurableEmbedder> testClass) {
        return (testClass.getAnnotation(net.thucydides.jbehave.annotations.Metafilter.class) != null) ?
                Optional.of(testClass.getAnnotation(net.thucydides.jbehave.annotations.Metafilter.class).value()) : Optional.<String>absent();
    }

    private Optional<String> getAnnotatedMetafilters(Class<? extends ConfigurableEmbedder> testClass) {
        return (testClass.getAnnotation(Metafilter.class) != null) ?
                Optional.of(testClass.getAnnotation(Metafilter.class).value()) : Optional.<String>absent();
    }

    private boolean isGroovy(String metaFilters) {
        return (metaFilters != null) && (metaFilters.startsWith("groovy:"));
    }


    private String addGroovyMetafilterValuesTo(String metaFilters) {
        String filters = "";
        if (!metaFilters.contains("skip")) {
            filters = filters + " && !skip";
        }
        if (!metaFilters.contains("ignore")) {
            filters = filters + " && !ignore";
        }
        if (!metaFilters.contains("wip")) {
            filters = filters + " && !wip";
        }
        if (!filters.isEmpty()) {
            return "groovy: ((" + metaFilters.substring(7).trim() + ")" + filters+")";
        } else {
            return metaFilters;
        }
    }

    private String addMetafilterValuesTo(String metaFilters) {
        String filters = "";
        if (!metaFilters.contains("skip")) {
            filters = filters + SKIP_FILTER;
        }
        if (!metaFilters.contains("ignore")) {
            if (StringUtils.isNotEmpty(filters)) {
                filters = filters + ",";
            }
            filters = filters + IGNORE_FILTER;
        }
        if (!metaFilters.contains("wip")) {
            if (StringUtils.isNotEmpty(filters)) {
                filters = filters + ",";
            }
            filters = filters + WIP_FILTER;
        }
        if (!filters.isEmpty()) {
            return metaFilters + (StringUtils.isNotEmpty(metaFilters) ? "," : "") + filters;
        } else {
            return metaFilters;
        }
    }

    protected boolean getIgnoreFailuresInStories() {
        return environmentVariables.getPropertyAsBoolean(SerenityJBehaveSystemProperties.IGNORE_FAILURES_IN_STORIES.getName(),false);
    }

    protected int getStoryTimeoutInSecs() {
        return environmentVariables.getPropertyAsInteger(SerenityJBehaveSystemProperties.STORY_TIMEOUT_IN_SECS.getName(),
                                                         (int) getConfiguredEmbedder().embedderControls().storyTimeoutInSecs());
    }

    protected List<String> getMetaFilters() {
        String metaFilters = getMetafilterSetting();
        return Lists.newArrayList(Splitter.on(Pattern.compile(",")).trimResults().omitEmptyStrings().split(metaFilters));
    }

    public boolean usingUniqueBrowser() {
        return environmentVariables.getPropertyAsBoolean(THUCYDIDES_USE_UNIQUE_BROWSER, false);
    }

    protected boolean getIgnoreFailuresInView() { return environmentVariables.getPropertyAsBoolean(SerenityJBehaveSystemProperties.IGNORE_FAILURES_IN_VIEW.getName(),true); }
}
