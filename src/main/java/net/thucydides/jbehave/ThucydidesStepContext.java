package net.thucydides.jbehave;

import net.thucydides.core.pages.Pages;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * Keeps track of instantiated JBehave step libraries used in ThucydidesWebdriverIntegration tests.
 */
public class ThucydidesStepContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThucydidesStepContext.class);

    public ThucydidesStepContext() {
    }

    public Object newInstanceOf(final Class<?> type) {
        try {
            ThucydidesWebDriverSupport.getPages();
            if (hasConstructorWithPagesParameter(type)) {
                return createNewPageEnabledStepCandidate(type);
            } else {
                return type.newInstance();
            }
        } catch (Exception e) {
            throw new ThucydidesStepInitializationError(e);
        }
    }

    private boolean hasConstructorWithPagesParameter(Class<?> type) {
        Class[] constructorArgs = new Class[1];
        constructorArgs[0] = Pages.class;
        try {
            type.getConstructor(constructorArgs);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    private <T> T createNewPageEnabledStepCandidate(final Class<T> type) {
        T newInstance = null;
        try {
            Pages pageFactory = ThucydidesWebDriverSupport.getPages();
            Class[] constructorArgs = new Class[1];
            constructorArgs[0] = Pages.class;
            Constructor<T> constructor = type.getConstructor(constructorArgs);
            newInstance = constructor.newInstance(pageFactory);
        } catch (Exception e) {
            LOGGER.info("Failed to instantiate page of type {} ({})", type, e.getMessage());
        }
        return newInstance;
    }

}

