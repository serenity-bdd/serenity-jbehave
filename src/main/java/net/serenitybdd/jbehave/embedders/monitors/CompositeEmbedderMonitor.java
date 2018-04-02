package net.serenitybdd.jbehave.embedders.monitors;

import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.EmbedderMonitor;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.model.*;
import org.jbehave.core.reporters.ReportsCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * User: YamStranger
 * Date: 3/24/16
 * Time: 11:23 PM
 */
public class CompositeEmbedderMonitor implements EmbedderMonitor {

    private static final Logger logger = LoggerFactory.getLogger(CompositeEmbedderMonitor.class);

    private Set<EmbedderMonitor> monitors =
            Collections.newSetFromMap(new ConcurrentHashMap<EmbedderMonitor, Boolean>());

    public CompositeEmbedderMonitor(final EmbedderMonitor monitor, final EmbedderMonitor... monitors) {
        this.monitors.add(monitor);
        this.monitors.addAll(Arrays.asList(monitors));
    }

    public void subscribe(final EmbedderMonitor monitor, final EmbedderMonitor... monitors) {
        this.monitors.add(monitor);
        this.monitors.addAll(Arrays.asList(monitors));
    }

    public void unsubscribe(final EmbedderMonitor monitor, final EmbedderMonitor... monitors) {
        this.monitors.remove(monitor);
        this.monitors.removeAll(Arrays.asList(monitors));
    }

    @Override
    public void runningEmbeddable(final String name) {
        logger.debug("runningEmbeddable {}", name);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.runningEmbeddable(name);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#runningEmbeddable", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void embeddableFailed(final String name, final Throwable cause) {
        logger.debug("embeddableFailed {}", name);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.embeddableFailed(name, cause);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#embeddableFailed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void embeddableNotConfigurable(final String name) {
        logger.debug("embeddableNotConfigurable {}", name);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.embeddableNotConfigurable(name);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#embeddableNotConfigurable", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void embeddablesSkipped(final List<String> classNames) {
        logger.debug("embeddablesSkipped {}", classNames.size());
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.embeddablesSkipped(classNames);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#embeddablesSkipped", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void metaNotAllowed(final Meta meta, final MetaFilter filter) {
        logger.debug("metaNotAllowed {} {}", meta, filter);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.metaNotAllowed(meta, filter);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#metaNotAllowed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void runningStory(final String path) {
        logger.debug("runningStory {}", path);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.runningStory(path);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#runningStory", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void storyFailed(final String path, final Throwable cause) {
        logger.debug("storyFailed {}", path);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.storyFailed(path, cause);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#storyFailed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void storiesSkipped(final List<String> storyPaths) {
        logger.debug("storiesSkipped{}", storyPaths);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.storiesSkipped(storyPaths);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#storiesSkipped", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    @Deprecated
    public void storiesNotAllowed(final List<Story> notAllowed, final MetaFilter filter) {
        logger.debug("storiesNotAllowed {} {}", notAllowed, filter);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.storiesNotAllowed(notAllowed, filter);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#storiesNotAllowed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    @Deprecated
    public void storiesNotAllowed(final List<Story> notAllowed, final MetaFilter filter, final boolean verbose) {
        logger.debug("storiesNotAllowed {} {} {}", notAllowed, filter, verbose);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.storiesNotAllowed(notAllowed, filter, verbose);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#storiesNotAllowed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void scenarioNotAllowed(final Scenario scenario, final MetaFilter filter) {
        logger.debug("scenarioNotAllowed {} {}", scenario, filter);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.scenarioNotAllowed(scenario, filter);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#scenarioNotAllowed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void batchFailed(final BatchFailures failures) {
        logger.debug("batchFailed {}", failures);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.batchFailed(failures);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#batchFailed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void beforeOrAfterStoriesFailed() {
        logger.debug("beforeOrAfterStoriesFailed");
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.beforeOrAfterStoriesFailed();
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#beforeOrAfterStoriesFailed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void generatingReportsView(final File outputDirectory, final List<String> formats,
                                      final Properties viewProperties) {
        logger.debug("generatingReportsView {} {} {}", outputDirectory, formats, viewProperties);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.generatingReportsView(outputDirectory, formats, viewProperties);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#generatingReportsView", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void reportsViewGenerationFailed(final File outputDirectory, final List<String> formats,
                                            final Properties viewProperties, final Throwable cause) {
        logger.debug("reportsViewGenerationFailed {} {} {} {}", outputDirectory, formats, viewProperties, cause);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.reportsViewGenerationFailed(outputDirectory, formats, viewProperties, cause);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#reportsViewGenerationFailed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void reportsViewGenerated(final ReportsCount count) {
        logger.debug("reportsViewGenerated {}", count);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.reportsViewGenerated(count);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#reportsViewGenerated", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void reportsViewFailures(final ReportsCount count) {
        logger.debug("reportsViewFailures {}", count);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.reportsViewFailures(count);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#reportsViewFailures", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void reportsViewNotGenerated() {
        logger.debug("reportsViewNotGenerated");
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.reportsViewNotGenerated();
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#reportsViewNotGenerated", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void runningWithAnnotatedEmbedderRunner(final String className) {
        logger.debug("runningWithAnnotatedEmbedderRunner {}", className);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.runningWithAnnotatedEmbedderRunner(className);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#runningWithAnnotatedEmbedderRunner", monitor.getClass(),
                        suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void annotatedInstanceNotOfType(final Object annotatedInstance, final Class<?> type) {
        logger.debug("annotatedInstanceNotOfType {} {}", annotatedInstance, type);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.annotatedInstanceNotOfType(annotatedInstance, type);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#annotatedInstanceNotOfType", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void mappingStory(final String storyPath, final List<String> metaFilters) {
        logger.debug("mappingStory {} {}", storyPath, metaFilters);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.mappingStory(storyPath, metaFilters);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#mappingStory", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void generatingMapsView(final File outputDirectory, final StoryMaps storyMaps,
                                   final Properties viewProperties) {
        logger.debug("generatingMapsView {} {}", outputDirectory, storyMaps);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.generatingMapsView(outputDirectory, storyMaps, viewProperties);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#generatingMapsView", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void mapsViewGenerationFailed(final File outputDirectory, final StoryMaps storyMaps,
                                         final Properties viewProperties, final Throwable cause) {
        logger.debug("mapsViewGenerationFailed {} {} {} {}", outputDirectory, storyMaps, viewProperties, cause);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.mapsViewGenerationFailed(outputDirectory, storyMaps, viewProperties, cause);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#mapsViewGenerationFailed", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void generatingNavigatorView(final File outputDirectory, final Properties viewResources) {
        logger.debug("generatingNavigatorView {} {}", outputDirectory, viewResources);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.generatingNavigatorView(outputDirectory, viewResources);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#generatingNavigatorView", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void navigatorViewGenerationFailed(final File outputDirectory, final Properties viewResources,
                                              final Throwable cause) {
        logger.debug("navigatorViewGenerationFailed {} {} {}", outputDirectory, viewResources, cause);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.navigatorViewGenerationFailed(outputDirectory, viewResources, cause);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#navigatorViewGenerationFailed", monitor.getClass(),
                        suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void navigatorViewNotGenerated() {
        logger.debug("navigatorViewNotGenerated");
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.navigatorViewNotGenerated();
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#navigatorViewNotGenerated", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void processingSystemProperties(final Properties properties) {
        logger.debug("processingSystemProperties {}", properties);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.processingSystemProperties(properties);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#processingSystemProperties", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void systemPropertySet(final String name, final String value) {
        logger.debug("systemPropertySet {} {}", name, value);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.systemPropertySet(name, value);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#systemPropertySet", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void storyTimeout(final Story story, final StoryDuration storyDuration) {
        logger.debug("storyTimeout {} {}", story, storyDuration);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.storyTimeout(story, storyDuration);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#storyTimeout", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void usingThreads(final int threads) {
        logger.debug("usingThreads {}", threads);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.usingThreads(threads);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#usingThreads", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void usingExecutorService(final ExecutorService executorService) {
        logger.debug("usingExecutorService {}", executorService);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.usingExecutorService(executorService);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#usingExecutorService", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void usingControls(final EmbedderControls embedderControls) {
        logger.debug("usingControls{}", embedderControls);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.usingControls(embedderControls);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#usingControls", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void invalidTimeoutFormat(final String path) {
        logger.debug("invalidTimeoutFormat {}", path);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.invalidTimeoutFormat(path);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#invalidTimeoutFormat", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    @Override
    public void usingTimeout(final String path, final long timeout) {
        logger.debug("usingTimeout {} {}", path, timeout);
        final LinkedList<Exception> exceptions = new LinkedList<>();
        for (final EmbedderMonitor monitor : this.monitors) {
            try {
                monitor.usingTimeout(path, timeout);
            } catch (final Exception suppressed) {
                logger.error("exception during calling {}#usingTimeout", monitor.getClass(), suppressed);
                exceptions.add(suppressed);
            }
        }
        processSuppressed(exceptions);
    }

    private void processSuppressed(final List<Exception> exceptions) {
        if (exceptions.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (Exception suppressed : exceptions) {
                builder.append("\"").append(suppressed.getMessage()).append("\" ; ");
            }
            final RuntimeException chained = new RuntimeException("Exceptions thrown with messages:"
                    + builder.toString());
            for (Exception suppressed : exceptions) {
                chained.addSuppressed(suppressed);
            }
            throw chained;
        }
    }
}
