package net.thucydides.jbehave;

import net.thucydides.core.guice.Injectors;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.ParanamerConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.util.List;
import java.util.Properties;

import static org.jbehave.core.reporters.Format.ANSI_CONSOLE;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

/**
 * A convenience class designed to make it easier to set up JBehave tests with ThucydidesWebdriverIntegration.
 */
public class ThucydidesJBehave {

    private static final CrossReference xref = new CrossReference();

    /**
     * Returns a default JBehave configuration object suitable for ThucydidesWebdriverIntegration tests.
     *
     * @return
     */
    public static Configuration defaultConfiguration(net.thucydides.core.webdriver.Configuration systemConfiguration,
                                                     List<Format> formats,
                                                     Embeddable embeddable) {

        Class<? extends Embeddable> embeddableClass = embeddable.getClass();

        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");

        return new ParanamerConfiguration()
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withDefaultFormats()
                                .withFormats((Format[]) formats.toArray())
                                .withCrossReference(xref)
                                .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                                .withViewResources(viewResources)
                                .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                                .withFailureTrace(true).withFailureTraceCompression(true)
                                .withReporters(new ThucydidesReporter(systemConfiguration)));
    }
}
