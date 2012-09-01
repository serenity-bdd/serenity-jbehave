package net.thucydides.jbehave;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.util.Collections;
import java.util.List;

public class ThucydidesJBehaveTestRunner extends Suite {

    private final Class<?> testClass;

    public ThucydidesJBehaveTestRunner(Class<?> klass) throws InitializationError {
        super(klass, Collections.<Runner>emptyList());
        this.testClass = klass;
    }

    @Override
    protected List<Runner> getChildren() {
        return super.getChildren();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
