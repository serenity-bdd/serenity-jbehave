package net.thucydides.jbehave;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.thucydides.core.ThucydidesSystemProperties;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.codehaus.plexus.util.StringUtils;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;

import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

/**
 * A JUnit-runnable test case designed to run a set of ThucydidesWebdriverIntegration-enabled JBehave stories in a given package.
 * By default, it will look for *.story files on the classpath, and steps in or underneath the current package.
 * You can redefine these constraints as follows:
 */
public abstract class ThucydidesJUnitStories extends JUnitStories {

    public static final String DEFAULT_STORY_NAME =  "**/*.story";

    private net.thucydides.core.webdriver.Configuration systemConfiguration;

    private String storyFolder = "";
    private String storyNamePattern = DEFAULT_STORY_NAME;

    private Configuration configuration;

    @Override
    public Configuration configuration() {
        if (configuration == null) {
            configuration = ThucydidesJBehave.defaultConfiguration(getSystemConfiguration());
        }
        return configuration;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return ThucydidesStepFactory.withStepsFromPackage(getRootPackage());
    }

    @Override
    protected List<String> storyPaths() {
        List<String> storyPaths = Lists.newArrayList();

        Iterable<String> pathExpressions = getStoryPathExpressions();
        StoryFinder storyFinder = new StoryFinder();
        for(String pathExpression : pathExpressions) {
            storyPaths.addAll(storyFinder.findPaths(codeLocationFromClass(this.getClass()), pathExpression, ""));
        }
        return storyPaths;
    }

    /**
     * The root package on the classpath containing the JBehave stories to be run.
     */
    protected String getRootPackage() {
        return this.getClass().getPackage().getName();
    }

    protected Iterable<String> getStoryPathExpressions() {
        return Splitter.on(';').trimResults().split(getStoryPath());
    }

        /**
        * The root package on the classpath containing the JBehave stories to be run.
        */
    protected String getStoryPath() {
        return (StringUtils.isEmpty(storyFolder)) ? storyNamePattern : storyFolder + "/" + storyNamePattern;
    }

    /**
     * Define the folder on the class path where the stories should be found
     * @param storyFolder
     */
    public void findStoriesIn(String storyFolder) {
        this.storyFolder = storyFolder;
    }


    public void findStoriesCalled(String storyName) {
        if (storyName.startsWith("**/")) {
            storyNamePattern = storyName;
        } else {
            storyNamePattern = "**/" + storyName;
        }

    }

    /**
     * Use this to override the default ThucydidesWebdriverIntegration configuration - for testing purposes only.
     */
    public void setSystemConfiguration(net.thucydides.core.webdriver.Configuration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    public net.thucydides.core.webdriver.Configuration getSystemConfiguration() {
        if (systemConfiguration == null) {
            systemConfiguration = Injectors.getInjector().getInstance(net.thucydides.core.webdriver.Configuration.class);
        }
        return systemConfiguration;
    }

    protected void useDriver(String driver) {
        getSystemConfiguration().setIfUndefined(ThucydidesSystemProperty.DRIVER.getPropertyName(), driver);
        ThucydidesWebDriverSupport.initialize(driver);
    }

    public ThucydidesConfigurationBuilder runThucydides() {
        return new ThucydidesConfigurationBuilder(this);
    }

    public class ThucydidesConfigurationBuilder {
        private final ThucydidesJUnitStories thucydidesJUnitStories;

        public ThucydidesConfigurationBuilder(ThucydidesJUnitStories thucydidesJUnitStories) {
            this.thucydidesJUnitStories = thucydidesJUnitStories;
        }

        public ThucydidesConfigurationBuilder withDriver(String driver) {
            useDriver(driver);
            return this;
        }
    }
}
