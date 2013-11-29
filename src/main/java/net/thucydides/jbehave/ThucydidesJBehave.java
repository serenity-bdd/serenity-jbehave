package net.thucydides.jbehave;

import net.thucydides.jbehave.converters.DateListConverter;
import net.thucydides.jbehave.converters.DateTimeConverter;
import net.thucydides.jbehave.converters.DateTimeListConverter;
import net.thucydides.jbehave.converters.TimeConverter;
import net.thucydides.jbehave.converters.TimeListConverter;
import net.thucydides.jbehave.converters.YearMonthConverter;
import net.thucydides.jbehave.converters.YearMonthListConverter;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.ParanamerConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters;

import java.util.List;
import java.util.Properties;

/**
 * A convenience class designed to make it easier to set up JBehave tests with ThucydidesWebdriverIntegration.
 */
public class ThucydidesJBehave {

    private static final CrossReference xref = new CrossReference();

    /**
     * Returns a default JBehave configuration object suitable for ThucydidesWebdriverIntegration tests.
     */
    public static Configuration defaultConfiguration(net.thucydides.core.webdriver.Configuration systemConfiguration,
                                                     List<Format> formats,
                                                     Embeddable embeddable) {

        Class<? extends Embeddable> embeddableClass = embeddable.getClass();

        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");

        new ParameterConverters.DateConverter();
        return new ParanamerConfiguration()
                .useParameterConverters(
                        new ParameterConverters().addConverters(
                                new ParameterConverters.DateConverter(),
                                new DateListConverter(),
                                new DateTimeConverter(),
                                new DateTimeListConverter(),
                                new YearMonthConverter(),
                                new YearMonthListConverter(),
                                new TimeConverter(),
                                new TimeListConverter(),
                                new ParameterConverters.EnumConverter(),
                                new ParameterConverters.EnumListConverter()))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withDefaultFormats()
                                .withFormats((Format[]) formats.toArray())
                                .withCrossReference(xref)
                                .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                                .withViewResources(viewResources)
                                .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                                .withFailureTrace(true).withFailureTraceCompression(true)
                                .withReporters(new ThucydidesReporter(systemConfiguration)))
                .useStoryLoader(new UTF8StoryLoader());
    }
}
