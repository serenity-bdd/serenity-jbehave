package net.serenitybdd.jbehave;

import net.serenitybdd.jbehave.runners.SerenityReportingRunner;
import net.thucydides.core.configuration.WebDriverConfiguration;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.reports.TestOutcomeLoader;
import net.thucydides.core.util.MockEnvironmentVariables;
import net.thucydides.core.webdriver.DriverConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AbstractJBehaveStory {

    protected MockEnvironmentVariables environmentVariables;
    protected DriverConfiguration systemConfiguration;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    protected File outputDirectory;

    protected List<Throwable> raisedErrors = new ArrayList<>();

    @Before
    public void prepareReporter() throws IOException {

        environmentVariables = new MockEnvironmentVariables();

        outputDirectory = temporaryFolder.newFolder("output");
        environmentVariables.setProperty("thucydides.outputDirectory", outputDirectory.getAbsolutePath());
        environmentVariables.setProperty("webdriver.driver", "phantomjs");
        systemConfiguration = new WebDriverConfiguration(environmentVariables);
        raisedErrors.clear();
    }

    final class AlertingNotifier extends RunNotifier {

        private Throwable exceptionThrown;

        @Override
        public void fireTestFailure(Failure failure) {
            exceptionThrown = failure.getException();
            super.fireTestFailure(failure);
        }

        public Throwable getExceptionThrown() {
            return exceptionThrown;
        }
    }

    protected void run(SerenityStories stories) {
        SerenityReportingRunner runner;

        AlertingNotifier notifier = new AlertingNotifier();
        try {
            runner = new SerenityReportingRunner(stories.getClass(), stories);
            runner.getDescription();
            runner.run(notifier);
        } catch(Throwable e) {
            e.printStackTrace();
         //   throw e;
        } finally {
            if (notifier.getExceptionThrown() != null) {
                raisedErrors.add(notifier.getExceptionThrown());
            }
        }
    }

    protected List<TestOutcome> loadTestOutcomes() {
        TestOutcomeLoader loader = new TestOutcomeLoader();
        return loader.loadFrom(outputDirectory);
    }

    protected SerenityStories newStory(String storyPattern) {
        return new AStorySample(storyPattern, systemConfiguration, environmentVariables);
    }
}
