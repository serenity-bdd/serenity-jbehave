package net.serenitybdd.jbehave;

import ch.lambdaj.Lambda;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.jbehave.runners.SerenityReportingRunner;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.jbehave.core.reporters.Format.*;

/**
 * A JUnit-runnable test case designed to run a set of SerenityWebdriverIntegration-enabled JBehave stories in a given package.
 * By default, it will look for *.story files on the classpath, and steps in or underneath the current package.
 * You can redefine these constraints as follows:
 */
@RunWith(SerenityReportingRunner.class)
public class SerenityStories extends JUnitStories {

    public static final String DEFAULT_STORY_NAME = "**/*.story";
    public static final List<String> DEFAULT_GIVEN_STORY_PREFIX = ImmutableList.of("Given", "Precondition");

    private net.thucydides.core.webdriver.Configuration systemConfiguration;
    private EnvironmentVariables environmentVariables;

    private String storyFolder = "";
    private String storyNamePattern = DEFAULT_STORY_NAME;

    private Configuration configuration;
    private List<Format> formats = Arrays.asList(CONSOLE, HTML, XML);

    public SerenityStories() {
        Serenity.throwExceptionsImmediately();
    }

    protected SerenityStories(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables.copy();
    }

    protected SerenityStories(net.thucydides.core.webdriver.Configuration configuration) {
        this.setSystemConfiguration(configuration);
    }

    public EnvironmentVariables getEnvironmentVariables() {
        if (environmentVariables == null) {
            environmentVariables = Injectors.getInjector().getProvider(EnvironmentVariables.class).get().copy();
        }
        return environmentVariables;
    }

    @Override
    public Configuration configuration() {
        if (configuration == null) {
            net.thucydides.core.webdriver.Configuration thucydidesConfiguration = getSystemConfiguration();
            if (environmentVariables != null) {
                thucydidesConfiguration = thucydidesConfiguration.withEnvironmentVariables(environmentVariables);
            }
            configuration = SerenityJBehave.defaultConfiguration(thucydidesConfiguration, formats, this);
        }
        return configuration;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return SerenityStepFactory.withStepsFromPackage(getRootPackage(), configuration()).andClassLoader(getClassLoader());
    }

    /**
     * The class loader used to obtain the JBehave and Step implementation classes.
     * You normally don't need to worry about this, but you may need to override it if your application
     * is doing funny business with the class loaders.
     */
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public List<String> storyPaths() {
        Set<String> storyPaths = Sets.newHashSet();

        List<String> pathExpressions = getStoryPathExpressions();
        StoryFinder storyFinder = new StoryFinder();
        for (String pathExpression : pathExpressions) {
            if (absolutePath(pathExpression)) {
                storyPaths.add(pathExpression);
            }
            for (URL classpathRootUrl : allClasspathRoots()) {
                storyPaths.addAll(storyFinder.findPaths(classpathRootUrl, pathExpression, ""));
            }
            storyPaths = removeDuplicatesFrom(storyPaths);
            storyPaths = pruneGivenStoriesFrom(storyPaths);
        }
        return Lists.newArrayList(storyPaths);
    }

    private Set<String> removeDuplicatesFrom(Set<String> storyPaths) {
        Set<String> trimmedPaths = Sets.newHashSet();
        for(String storyPath : storyPaths) {
            if (!thereExistsALongerVersionOf(storyPath, storyPaths)) {
                trimmedPaths.add(storyPath);
            }
        }
        return trimmedPaths;
    }

    private boolean thereExistsALongerVersionOf(String storyPath, Set<String> storyPaths) {
        for(String existingPath : storyPaths) {
            if ((existingPath.endsWith("/" + storyPath)) || (existingPath.endsWith("\\" + storyPath))) {
                return true;
            }
        }
        return false;
    }

    private Set<String> pruneGivenStoriesFrom(Set<String> storyPaths) {
        List<String> filteredPaths = Lists.newArrayList(storyPaths);
        for (String skippedPrecondition : skippedPreconditions()) {
            filteredPaths = removeFrom(filteredPaths)
                    .pathsNotStartingWith(skippedPrecondition)
                    .and().pathsNotStartingWith("/" + skippedPrecondition)
                    .filter();
        }
        return Sets.newHashSet(filteredPaths);
    }

    class FilterBuilder {

        private final List<String> paths;

        public FilterBuilder(List<String> paths) {
            this.paths = paths;
        }

        public FilterBuilder pathsNotStartingWith(String skippedPrecondition) {
            List<String> filteredPaths = Lists.newArrayList();
            for (String path : paths) {
                if (!startsWith(skippedPrecondition, path)) {
                    filteredPaths.add(path);
                }
            }
            return new FilterBuilder(filteredPaths);
        }

        public FilterBuilder and() {
            return this;
        }

        public List<String> filter() {
            return ImmutableList.copyOf(paths);
        }

        private boolean startsWith(String skippedPrecondition, String path) {
            return path.toLowerCase().startsWith(skippedPrecondition.toLowerCase());
        }
    }

    private FilterBuilder removeFrom(List<String> filteredPaths) {
        return new FilterBuilder(filteredPaths);
    }

    private List<String> skippedPreconditions() {
        return DEFAULT_GIVEN_STORY_PREFIX;
    }

    private boolean absolutePath(String pathExpression) {
        return (!pathExpression.contains("*"));
    }

    private Set<URL> allClasspathRoots() {
        try {
            Set<URL> baseRoots = Sets.newHashSet(Collections.list(getClassLoader().getResources(".")));
            return addGradleResourceRootsTo(baseRoots);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load the classpath roots when looking for story files", e);
        }
    }

    private Set<URL> addGradleResourceRootsTo(Set<URL> baseRoots) throws MalformedURLException {
        Set<URL> rootsWithGradleResources = Sets.newHashSet(baseRoots);
        for (URL baseUrl : baseRoots) {
            String gradleResourceUrl = baseUrl.toString().replace("/build/classes/", "/build/resources/");
            rootsWithGradleResources.add(new URL(gradleResourceUrl));
        }
        return rootsWithGradleResources;
    }

    /**
     * The root package on the classpath containing the JBehave stories to be run.
     */
    protected String getRootPackage() {
        return RootPackage.forPackage(getClass().getPackage());
    }


    protected List<String> getStoryPathExpressions() {
        return Lists.newArrayList(Splitter.on(';').trimResults().omitEmptyStrings().split(getStoryPath()));
    }

    /**
     * The root package on the classpath containing the JBehave stories to be run.
     */
    protected String getStoryPath() {
        return (StringUtils.isEmpty(storyFolder)) ? storyNamePattern : storyFolder + "/" + storyNamePattern;
    }

    /**
     * Define the folder on the class path where the stories should be found
     */
    public void findStoriesIn(String storyFolder) {
        this.storyFolder = storyFolder;
    }

    public void useFormats(Format... formats) {
        this.formats = Arrays.asList(formats);
    }

    public void findStoriesCalled(String storyNames) {
        Set<String> storyPathElements = new StoryPathFinder(getEnvironmentVariables(), storyNames).findAllElements();
        storyNamePattern = Lambda.join(storyPathElements, ";");
    }


    /**
     * Use this to override the default ThucydidesWebdriverIntegration configuration - for testing purposes only.
     */
    protected void setSystemConfiguration(net.thucydides.core.webdriver.Configuration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    protected void setEnvironmentVariables(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public net.thucydides.core.webdriver.Configuration getSystemConfiguration() {
        if (systemConfiguration == null) {
            systemConfiguration = Injectors.getInjector().getInstance(net.thucydides.core.webdriver.Configuration.class);
        }
        return systemConfiguration;
    }

    protected void useDriver(String driver) {
        getSystemConfiguration().setIfUndefined(ThucydidesSystemProperty.DRIVER.getPropertyName(), driver);
    }

    protected void useUniqueSession() {
        getSystemConfiguration().setIfUndefined(ThucydidesSystemProperty.THUCYDIDES_USE_UNIQUE_BROWSER.getPropertyName(), "true");
    }

    public ThucydidesConfigurationBuilder runSerenity() {
        return new ThucydidesConfigurationBuilder(this);
    }

    public class ThucydidesConfigurationBuilder {
        private final SerenityStories serenityStories;

        public ThucydidesConfigurationBuilder(SerenityStories serenityStories) {
            this.serenityStories = serenityStories;
        }

        public ThucydidesConfigurationBuilder withDriver(String driver) {
            useDriver(driver);
            return this;
        }

        public ThucydidesPropertySetter withProperty(ThucydidesSystemProperty property) {
            return new ThucydidesPropertySetter(serenityStories, property);
        }

        public ThucydidesPropertySetter withProperty(String property) {
            return new ThucydidesPropertySetter(serenityStories, property);
        }

        public ThucydidesConfigurationBuilder inASingleSession() {
            useUniqueSession();
            return this;
        }
    }

    public class ThucydidesPropertySetter {
        private final SerenityStories serenityStories;
        private final String propertyToSet;

        public ThucydidesPropertySetter(SerenityStories serenityStories,
                                        ThucydidesSystemProperty propertyToSet) {
            this.serenityStories = serenityStories;
            this.propertyToSet = propertyToSet.getPropertyName();
        }

        public ThucydidesPropertySetter(SerenityStories serenityStories,
                                        String propertyToSet) {
            this.serenityStories = serenityStories;
            this.propertyToSet = propertyToSet;
        }

        public void setTo(boolean value) {
            serenityStories.getSystemConfiguration().setIfUndefined(propertyToSet,
                    Boolean.toString(value));
        }

        public void setTo(String value) {
            serenityStories.getSystemConfiguration().setIfUndefined(propertyToSet, value);
        }

        public void setTo(Integer value) {
            serenityStories.getSystemConfiguration().setIfUndefined(propertyToSet,
                    Integer.toString(value));
        }
    }
}
