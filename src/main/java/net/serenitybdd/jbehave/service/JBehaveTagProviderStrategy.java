package net.serenitybdd.jbehave.service;


import com.beust.jcommander.internal.Lists;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.requirements.FileSystemRequirementsTagProvider;
import net.thucydides.core.statistics.service.TagProvider;
import net.thucydides.core.statistics.service.TagProviderStrategy;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.util.EnvironmentVariables;

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
        return StepEventBus.TEST_SOURCE_JBEHAVE.equalsIgnoreCase(testType);
    }

    @Override
    public Iterable<TagProvider> getTagProviders() {
        String rootDirectory = ThucydidesSystemProperty.THUCYDIDES_REQUIREMENTS_DIR.from(environmentVariables,"stories");
        return Lists.newArrayList((TagProvider) new FileSystemRequirementsTagProvider(environmentVariables,rootDirectory));
    }

    @Override
    public boolean hasHighPriority() {
        return true;
    }

}
