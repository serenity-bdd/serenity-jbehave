package net.thucydides.jbehave;

import net.thucydides.core.guice.Injectors;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.ParanamerConfiguration;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.util.List;

import static org.jbehave.core.reporters.Format.ANSI_CONSOLE;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

/**
 * A convenience class designed to make it easier to set up JBehave tests with ThucydidesWebdriverIntegration.
 */
public class ThucydidesJBehave {

    /**
     * Returns a default JBehave configuration object suitable for ThucydidesWebdriverIntegration tests.
     *
     * @return
     */
    public static Configuration defaultConfiguration(net.thucydides.core.webdriver.Configuration systemConfiguration,
                                                     List<Format> formats) {
        return new ParanamerConfiguration()
                .useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats()
                        .withFormats((Format[]) formats.toArray())
                        .withReporters(new ThucydidesReporter(systemConfiguration)));
    }

    public static Configuration defaultConfiguration(List<Format> formats) {
        return defaultConfiguration(Injectors.getInjector().getInstance(net.thucydides.core.webdriver.Configuration.class),
                                    formats);
    }
}
