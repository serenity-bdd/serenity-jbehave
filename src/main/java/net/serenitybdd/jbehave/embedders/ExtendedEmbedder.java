package net.serenitybdd.jbehave.embedders;

import net.serenitybdd.jbehave.embedders.monitors.CompositeEmbedderMonitor;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.*;
import org.jbehave.core.model.Story;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * User: YamStranger
 * Date: 3/25/16
 * Time: 12:02 AM
 */
public class ExtendedEmbedder extends Embedder {
    final Embedder embedder;
    final Map<String, Story> stories = new ConcurrentHashMap<>();

    public ExtendedEmbedder(Embedder embedder) {
        this.embedder = embedder;
        this.embedder.useEmbedderMonitor(new CompositeEmbedderMonitor(embedderMonitor));
    }

    public CompositeEmbedderMonitor getEmbedderMonitor() {
        return (CompositeEmbedderMonitor) this.embedder.embedderMonitor();
    }

    public void registerStory(final String path, final Story story) {
        this.stories.put(path, story);
    }

    public Story findStory(final String path){
        return this.stories.get(path);
    }

    @Override
    public void mapStoriesAsPaths(List<String> storyPaths) {
        embedder.mapStoriesAsPaths(storyPaths);
    }

    @Override
    public void runAsEmbeddables(List<String> classNames) {
        embedder.runAsEmbeddables(classNames);
    }

    @Override
    public void runStoriesWithAnnotatedEmbedderRunner(List<String> classNames) {
        embedder.runStoriesWithAnnotatedEmbedderRunner(classNames);
    }

    @Override
    public void runStoriesAsPaths(List<String> storyPaths) {
        embedder.runStoriesAsPaths(storyPaths);
    }

    @Override
    public void generateReportsView() {
        embedder.generateReportsView();
    }

    @Override
    public void generateReportsView(File outputDirectory, List<String> formats, Properties viewResources) {
        embedder.generateReportsView(outputDirectory, formats, viewResources);
    }

    @Override
    public void generateCrossReference() {
        embedder.generateCrossReference();
    }

    @Override
    public void reportStepdocs() {
        embedder.reportStepdocs();
    }

    @Override
    public void reportStepdocsAsEmbeddables(List<String> classNames) {
        embedder.reportStepdocsAsEmbeddables(classNames);
    }

    @Override
    public void reportStepdocs(Configuration configuration, List<CandidateSteps> candidateSteps) {
        embedder.reportStepdocs(configuration, candidateSteps);
    }

    @Override
    public void reportMatchingStepdocs(String stepAsString) {
        embedder.reportMatchingStepdocs(stepAsString);
    }

    @Override
    public void processSystemProperties() {
        embedder.processSystemProperties();
    }

    @Override
    public EmbedderClassLoader classLoader() {
        return embedder.classLoader();
    }

    @Override
    public Configuration configuration() {
        return embedder.configuration();
    }

    @Override
    public List<CandidateSteps> candidateSteps() {
        return embedder.candidateSteps();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return embedder.stepsFactory();
    }

    @Override
    public EmbedderControls embedderControls() {
        return embedder.embedderControls();
    }

    @Override
    public EmbedderMonitor embedderMonitor() {
        return embedder.embedderMonitor();
    }

    @Override
    public EmbedderFailureStrategy embedderFailureStrategy() {
        return embedder.embedderFailureStrategy();
    }

    @Override
    public boolean hasExecutorService() {
        return embedder.hasExecutorService();
    }

    @Override
    public ExecutorService executorService() {
        return embedder.executorService();
    }

    @Override
    public StoryManager storyManager() {
        return embedder.storyManager();
    }

    @Override
    public List<String> metaFilters() {
        return embedder.metaFilters();
    }

    @Override
    public Map<String, MetaFilter.MetaMatcher> metaMatchers() {
        return embedder.metaMatchers();
    }

    @Override
    public MetaFilter metaFilter() {
        return embedder.metaFilter();
    }

    @Override
    public PerformableTree performableTree() {
        return embedder.performableTree();
    }

    @Override
    public Properties systemProperties() {
        return embedder.systemProperties();
    }

    @Override
    public StoryTimeouts.TimeoutParser[] timeoutParsers() {
        return embedder.timeoutParsers();
    }

    @Override
    public void useClassLoader(EmbedderClassLoader classLoader) {
        embedder.useClassLoader(classLoader);
    }

    @Override
    public void useConfiguration(Configuration configuration) {
        embedder.useConfiguration(configuration);
    }

    @Override
    public void useCandidateSteps(List<CandidateSteps> candidateSteps) {
        embedder.useCandidateSteps(candidateSteps);
    }

    @Override
    public void useStepsFactory(InjectableStepsFactory stepsFactory) {
        embedder.useStepsFactory(stepsFactory);
    }

    @Override
    public void useEmbedderControls(EmbedderControls embedderControls) {
        embedder.useEmbedderControls(embedderControls);
    }

    @Override
    public void useEmbedderFailureStrategy(EmbedderFailureStrategy failureStategy) {
        embedder.useEmbedderFailureStrategy(failureStategy);
    }

    @Override
    public void useEmbedderMonitor(EmbedderMonitor embedderMonitor) {
        embedder.useEmbedderMonitor(embedderMonitor);
    }

    @Override
    public void useExecutorService(ExecutorService executorService) {
        embedder.useExecutorService(executorService);
    }

    @Override
    public void useMetaFilters(List<String> metaFilters) {
        embedder.useMetaFilters(metaFilters);
    }

    @Override
    public void useMetaMatchers(Map<String, MetaFilter.MetaMatcher> metaMatchers) {
        embedder.useMetaMatchers(metaMatchers);
    }

    @Override
    public void usePerformableTree(PerformableTree performableTree) {
        embedder.usePerformableTree(performableTree);
    }

    @Override
    public void useSystemProperties(Properties systemProperties) {
        embedder.useSystemProperties(systemProperties);
    }

    @Override
    public void useTimeoutParsers(StoryTimeouts.TimeoutParser... timeoutParsers) {
        embedder.useTimeoutParsers(timeoutParsers);
    }

    @Override
    public String toString() {
        return embedder.toString();
    }
}
