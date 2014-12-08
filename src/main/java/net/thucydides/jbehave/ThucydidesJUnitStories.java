package net.thucydides.jbehave;

import net.serenity_bdd.jbehave.SerenityStories;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.jbehave.runners.ThucydidesReportingRunner;
import org.junit.runner.RunWith;

/**
 * @deprecated Use SerenityStories instead
 *
 * A JUnit-runnable test case designed to run a set of ThucydidesWebdriverIntegration-enabled JBehave stories in a given package.
 * By default, it will look for *.story files on the classpath, and steps in or underneath the current package.
 * You can redefine these constraints as follows:
 */
@Deprecated
@RunWith(ThucydidesReportingRunner.class)
public class ThucydidesJUnitStories extends SerenityStories {

    public ThucydidesJUnitStories() {
        super();
    }

    protected ThucydidesJUnitStories(EnvironmentVariables environmentVariables) {
        super(environmentVariables);
    }

    protected ThucydidesJUnitStories(net.thucydides.core.webdriver.Configuration configuration) {
        super(configuration);
    }
}
