package net.thucydides.jbehave;

/**
 * Specifies system property values for the JBehave configuration.
 */
public enum ThucydidesJBehaveSystemProperties {

    /**
     * Set the JBehave ignoreFailuresInStories options.
     */
    IGNORE_FAILURES_IN_STORIES,

    /**
     * How long before a JBehave story times out (defaults to 300 seconds, or 5 minutes).
     */
    STORY_TIMEOUT_IN_SECS,

    /**
     * JBehave meta filters expressions, separated by commas.
     * These use the meta annotations in the JBehave stories to decide what stories to
     * execute. See http://jbehave.org/reference/stable/meta-filtering.html for details
     * on how the metafilter syntax works.
     */
    METAFILTER;

    public String getName() {return toString().toLowerCase().replaceAll("_",".");}

}

