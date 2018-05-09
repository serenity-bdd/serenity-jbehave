package net.serenitybdd.jbehave.service;


import com.google.common.collect.ImmutableSet;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.requirements.FileSystemRequirementsTagProvider;
import net.thucydides.core.statistics.service.ContextTagProvider;
import net.thucydides.core.statistics.service.InjectedTagProvider;
import net.thucydides.core.statistics.service.TagProvider;
import net.thucydides.core.statistics.service.TagProviderStrategy;
import net.thucydides.core.util.EnvironmentVariables;

import static net.thucydides.core.steps.TestSourceType.TEST_SOURCE_JBEHAVE;

public class JBehaveTagProviderStrategy implements TagProviderStrategy {

    private final EnvironmentVariables environmentVariables;

    public JBehaveTagProviderStrategy(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public JBehaveTagProviderStrategy() {
        this(Injectors.getInjector().getInstance(EnvironmentVariables.class));
    }

    @Override
    public boolean canHandleTestSource(String testType) {
        return TEST_SOURCE_JBEHAVE.getValue().equalsIgnoreCase(testType);
    }

    @Override
    public Iterable<? extends TagProvider> getTagProviders() {
        String rootDirectory = ThucydidesSystemProperty.THUCYDIDES_REQUIREMENTS_DIR.from(environmentVariables,"stories");
        return ImmutableSet.of(
                new FileSystemRequirementsTagProvider(environmentVariables,rootDirectory),
                new InjectedTagProvider(environmentVariables),
                new ContextTagProvider(environmentVariables)
        );
    }

    @Override
    public boolean hasHighPriority() {
        return false;
    }

}
