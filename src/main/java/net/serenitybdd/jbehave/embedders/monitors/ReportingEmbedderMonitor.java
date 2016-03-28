package net.serenitybdd.jbehave.embedders.monitors;

import net.serenitybdd.jbehave.SerenityJBehave;
import net.serenitybdd.jbehave.SerenityReporter;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.EmbedderMonitor;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.model.*;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.core.reporters.ReportsCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * User: YamStranger
 * Date: 3/25/16
 * Time: 6:35 PM
 */
public class ReportingEmbedderMonitor implements EmbedderMonitor {
    private static final Logger logger = LoggerFactory.getLogger(ReportingEmbedderMonitor.class);
    private SerenityReporter reporter;

    public ReportingEmbedderMonitor() {
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
        logger.debug("story running with path " + path);
        includeInReportSkippedAndIgnored(parseStory(path));
    }


    @Override
    public void storiesNotAllowed(List<Story> notAllowed, MetaFilter filter) {
        logger.debug("processing stories Not Allowed " + notAllowed);
        for (final Story story : notAllowed) {
            includeInReportSkippedAndIgnored(story);
        }
    }

    @Override
    public void storiesNotAllowed(List<Story> notAllowed, MetaFilter filter, boolean verbose) {
        logger.debug("processing stories Not Allowed " + notAllowed);
        for (final Story story : notAllowed) {
            includeInReportSkippedAndIgnored(story);
        }
    }

    private Story parseStory(final String path) {
        final Keywords keywords = new LocalizedKeywords();
        final StoryParser parser = new RegexStoryParser(keywords);
        try {
            final Path story = Paths.get(path);
            final StringBuilder builder = new StringBuilder();
            try (final BufferedReader reader = Files.newBufferedReader(story, StandardCharsets.UTF_8)) {
                for (; ; ) {
                    String line = reader.readLine();
                    if (line == null)
                        break;
                    builder.append(line).append("\\n");
                }
            }
            return parser.parseStory(builder.toString(), path);
        } catch (IOException e) {
            logger.error("can not read story", e);
        }
        return new Story();
    }

    private void includeInReportSkippedAndIgnored(final Story story) {
        final SerenityReporter reporter = reporter();
        reporter.processExcludedByFilter(story);
    }

    public synchronized SerenityReporter reporter() {
        if (this.reporter == null) {
            net.thucydides.core.webdriver.Configuration configuration =
                    Injectors.getInjector().getInstance(net.thucydides.core.webdriver.Configuration.class);
            EnvironmentVariables variables =
                    Injectors.getInjector().getProvider(EnvironmentVariables.class).get().copy();
            if (variables != null) {
                configuration = configuration.withEnvironmentVariables(variables);
            }
            this.reporter = new SerenityReporter(configuration);
        }
        return reporter;
    }
}
