package net.serenity_bdd.jbehave;

/**
 * Specifies system property values for the JBehave configuration.
 */
public enum ThucydidesJBehaveSystemProperties {

    /**
     * Set the JBehave ignoreFailuresInStories options.
     */
    IGNORE_FAILURES_IN_STORIES,

    /**
     * How long before the JBehave stories time out (defaults to 300 seconds, or 5 minutes).
     * Note that this applies to ALL of the JBehave stories as a whole (i.e. the total test
     * time for all of the tests), not the individual tests.
     */
    STORY_TIMEOUT_IN_SECS,

    /**
     * JBehave meta filters expressions, separated by commas.
     * These use the meta annotations in the JBehave stories to decide what stories to
     * execute. See http://jbehave.org/reference/stable/meta-filtering.html for details
     * on how the metafilter syntax works.
     */
    METAFILTER,

    /**
     * Force Thucydides to restart the browser before each scenario.
     */
    RESTART_BROWSER_EACH_SCENARIO,

    /**
     * Reset step libraries in JBehave step definitions for each scenario.
     * If this property is set to true (the default), any @Step-annotated member variables in JBehave step definitions
     * will be reinitialized before each scenario.
     */
    RESET_STEPS_EACH_SCENARIO,


    /**
     * Define a directory for the .story files, inside src/test/resources. By default, this is 'stories'
     */
    STORY_DIRECTORY,

    /**
     * If the JBehave stories are in a JAR file, we need to provide a semi-colon separated list of packages for
     * the test runner to look in, e.g. "/my/package;/my/other/package"
     * If the stories are in the root package or in the "stories" folder, this is not required.
     */
    JBEHAVE_STORY_PACKAGES,

    /**
     * Controls the ignoreFailuresInView flag in JBehave (see http://jbehave.org/reference/stable/running-stories.html).
     */
    IGNORE_FAILURES_IN_VIEW;

    public String getName() {return toString().toLowerCase().replaceAll("_",".");}

}

