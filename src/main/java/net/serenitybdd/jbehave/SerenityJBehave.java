package net.serenitybdd.jbehave;

import net.serenitybdd.jbehave.converters.*;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.ParanamerConfiguration;
import org.jbehave.core.failures.FailureStrategy;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.internal.AssumptionViolatedException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * A convenience class designed to make it easier to set up JBehave tests with ThucydidesWebdriverIntegration.
 */
public class SerenityJBehave {

//    private static final CrossReference xref = new CrossReference();

    /**
     * Returns a default JBehave configuration object suitable for ThucydidesWebdriverIntegration tests.
     */
    public static Configuration defaultConfiguration(net.thucydides.core.webdriver.DriverConfiguration systemConfiguration,
                                                     List<Format> formats,
                                                     Embeddable embeddable) {

        Class<? extends Embeddable> embeddableClass = embeddable.getClass();

        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");

        TableTransformers tableTransformers = new TableTransformers();
        LoadFromClasspath utf8StoryLoader = new LoadFromClasspath(StandardCharsets.UTF_8);
        return new ParanamerConfiguration()
                .useTableTransformers(tableTransformers)
                .useParameterConverters(
                        new ParameterConverters(utf8StoryLoader, tableTransformers).addConverters(
                                new ParameterConverters.DateConverter(),
                                new DateTimeConverter(),
                                new YearMonthConverter(),
                                new TimeConverter(),
                                new ParameterConverters.EnumConverter(),
                                new ParameterConverters.EnumListConverter()))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withDefaultFormats()
                                .withFormats(formats.toArray(new Format[0]))
//                                .withCrossReference(xref)
                                .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                                .withViewResources(viewResources)
                                .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                                .withFailureTrace(true).withFailureTraceCompression(true)
                                .withReporters(new SerenityReporter(systemConfiguration)))
                .useStoryLoader(utf8StoryLoader)
                .useFailureStrategy(new IgnoreAssumptionViolations());
    }

    private static class IgnoreAssumptionViolations implements FailureStrategy {
        @Override
        public void handleFailure(Throwable throwable) throws Throwable {
            if (throwable instanceof AssumptionViolatedException) {
                return;
            }
            if (throwable instanceof UUIDExceptionWrapper) {
                this.handleFailure(throwable.getCause());
            }
            throw throwable;
        }
    }
}
