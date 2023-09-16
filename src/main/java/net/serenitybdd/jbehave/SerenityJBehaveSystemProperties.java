package net.serenitybdd.jbehave;

/**
 * Specifies system property values for the JBehave configuration.
 */
public enum SerenityJBehaveSystemProperties {

    /**
     * Set the JBehave ignoreFailuresInStories options.
     */
    IGNORE_FAILURES_IN_STORIES,

    /**
     * @deprecated use story.timeout instead
     * How long before the JBehave stories time out (defaults to 300 seconds, or 5 minutes).
     * Note that this applies to ALL of the JBehave stories as a whole (i.e. the total test
     * time for all of the tests), not the individual tests.
     */
    STORY_TIMEOUT_IN_SECS,

    /**
     * This appears to have replaced the story.timeout.in.secs property in the JBehave API.
     */
    STORY_TIMEOUT,

    /**
     * JBehave meta filters expressions, separated by commas.
     * These use the meta annotations in the JBehave stories to decide what stories to
     * execute. See http://jbehave.org/reference/stable/meta-filtering.html for details
     * on how the metafilter syntax works.
     */
    METAFILTER,

    /**
     * A regular expression that indicates which stories are included in the test run, based on the story name.
     * Can be used to speed up tests, but must be used in conjunction with the metafilter tag.
     */
    STORY_FILTER,

    /**
     * If you don't restart a browser between scenarios, do you clear the session cookies? (defaults to true)
     */
    RESET_COOKIES_EACH_SCENARIO,

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
    IGNORE_FAILURES_IN_VIEW,

    /**
     * The number of threads to run stories in.
     */
    JBEHAVE_THREADS,

    /**
     * Set story control property.
     * https://jbehave.org/reference/latest/javadoc/core/org/jbehave/core/embedder/StoryControls.html
     */
    STORYCONTROL_IGNORE_METAFILTERS_IF_GIVEN_STORY;

    public String getName() {return toString().toLowerCase().replaceAll("_",".");}

}

