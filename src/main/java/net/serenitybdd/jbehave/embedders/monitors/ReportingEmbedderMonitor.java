package net.serenitybdd.jbehave.embedders.monitors;

import net.serenitybdd.core.di.WebDriverInjectors;
import net.serenitybdd.jbehave.SerenityReporter;
import net.serenitybdd.jbehave.embedders.ExtendedEmbedder;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.DriverConfiguration;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.EmbedderMonitor;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.model.*;
import org.jbehave.core.reporters.ReportsCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * User: YamStranger
 * Date: 3/25/16
 * Time: 6:35 PM
 */
public class ReportingEmbedderMonitor implements EmbedderMonitor {
    private static final Logger logger = LoggerFactory.getLogger(ReportingEmbedderMonitor.class);
    private SerenityReporter reporter;
    private ExtendedEmbedder embedder;
    private final DriverConfiguration configuration;
    private final Set<String> processedStories=Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());


    public ReportingEmbedderMonitor(final ExtendedEmbedder embedder) {
        this(configuration(), embedder);
    }

    public ReportingEmbedderMonitor(final DriverConfiguration configuration,
                                    final ExtendedEmbedder embedder) {
        this.configuration = configuration;
        this.embedder = embedder;
    }


    @Override
    public void runningEmbeddable(String name) {
    }

    @Override
    public void embeddableFailed(String name, Throwable cause) {
    }

    @Override
    public void embeddableNotConfigurable(String name) {
    }

    @Override
    public void embeddablesSkipped(List<String> classNames) {
    }

    @Override
    public void metaNotAllowed(Meta meta, MetaFilter filter) {
    }

    @Override
    public void storyFailed(String path, Throwable cause) {
    }

    @Override
    public void storiesSkipped(List<String> storyPaths) {
    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, MetaFilter filter) {
    }

    @Override
    public void batchFailed(BatchFailures failures) {
    }

    @Override
    public void beforeOrAfterStoriesFailed() {
    }

    @Override
    public void generatingReportsView(File outputDirectory, List<String> formats, Properties viewProperties) {
    }

    @Override
    public void reportsViewGenerationFailed(File outputDirectory, List<String> formats, Properties viewProperties, Throwable cause) {
    }

    @Override
    public void reportsViewGenerated(ReportsCount count) {
    }

    @Override
    public void reportsViewFailures(ReportsCount count) {
    }

    @Override
    public void reportsViewNotGenerated() {
    }

    @Override
    public void runningWithAnnotatedEmbedderRunner(String className) {
    }

    @Override
    public void annotatedInstanceNotOfType(Object annotatedInstance, Class<?> type) {
    }

    @Override
    public void mappingStory(String storyPath, List<String> metaFilters) {
    }

    @Override
    public void generatingMapsView(File outputDirectory, StoryMaps storyMaps, Properties viewProperties) {
    }

    @Override
    public void mapsViewGenerationFailed(File outputDirectory, StoryMaps storyMaps, Properties viewProperties, Throwable cause) {
    }

    @Override
    public void generatingNavigatorView(File outputDirectory, Properties viewResources) {
    }

    @Override
    public void navigatorViewGenerationFailed(File outputDirectory, Properties viewResources, Throwable cause) {
    }

    @Override
    public void navigatorViewNotGenerated() {
    }

    @Override
    public void processingSystemProperties(Properties properties) {
    }

    @Override
    public void systemPropertySet(String name, String value) {
    }

    @Override
    public void storyTimeout(Story story, StoryDuration storyDuration) {
    }

    @Override
    public void usingThreads(int threads) {
    }

    @Override
    public void usingExecutorService(ExecutorService executorService) {
    }

    @Override
    public void usingControls(EmbedderControls embedderControls) {
    }

    @Override
    public void invalidTimeoutFormat(String path) {
    }

    @Override
    public void usingTimeout(String path, long timeout) {
    }

    @Override
    public void runningStory(String path) {
        logger.info("{}story running with path {}", this.hashCode(), path);
        final Story story = embedder.findStory(path);
        if (story == null) {
            logger.error("can not find any story by path {}", path);
        } else {
            includeInReportSkippedAndIgnoredAndWip(story);
        }
    }


    @Override
    public void storiesNotAllowed(List<Story> notAllowed, MetaFilter filter) {
        logger.debug("processing stories Not Allowed {}", notAllowed);
        for (final Story story : notAllowed) {
            includeInReportSkippedAndIgnoredAndWip(story);
        }
    }

    @Override
    public void storiesNotAllowed(List<Story> notAllowed, MetaFilter filter, boolean verbose) {
        logger.debug("processing stories Not Allowed {}", notAllowed);
        for (final Story story : notAllowed) {
            includeInReportSkippedAndIgnoredAndWip(story);
        }
    }

    private void includeInReportSkippedAndIgnoredAndWip(final Story story) {
        final SerenityReporter reporter = reporter();
        this.processedStories.addAll(
                reporter.processExcludedByFilter(story, this.processedStories)
        );
    }

    public synchronized SerenityReporter reporter() {
        if (this.reporter == null) {
            this.reporter = new SerenityReporter(this.configuration);
        }
        return reporter;
    }


    private static DriverConfiguration configuration() {
        DriverConfiguration<DriverConfiguration> configuration =
                WebDriverInjectors.getInjector().getInstance(DriverConfiguration.class);
        EnvironmentVariables variables =
                Injectors.getInjector().getProvider(EnvironmentVariables.class).get().copy();
        if (variables != null) {
            configuration = configuration.withEnvironmentVariables(variables);
        }
        return configuration;
    }
}
