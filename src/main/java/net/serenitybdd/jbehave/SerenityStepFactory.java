package net.serenitybdd.jbehave;

import net.serenitybdd.core.di.DependencyInjector;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.steps.PageObjectDependencyInjector;
import net.thucydides.core.steps.StepAnnotations;
import net.thucydides.core.steps.StepFactory;
import net.thucydides.core.steps.di.DependencyInjectorService;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.AbstractStepsFactory;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SerenityStepFactory extends AbstractStepsFactory {

    private static final ThreadLocal<SerenityStepContext> context = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(SerenityStepFactory.class);

    private final String rootPackage;
    private ClassLoader classLoader;
    private DependencyInjectorService dependencyInjectorService;

    public SerenityStepFactory(Configuration configuration, String rootPackage, ClassLoader classLoader) {
        super(configuration);
        this.rootPackage = rootPackage;
        this.classLoader = classLoader;
        this.dependencyInjectorService = Injectors.getInjector().getInstance(DependencyInjectorService.class);
    }

    private StepFactory getStepFactory() {
        return ThucydidesWebDriverSupport.getStepFactory();
    }

    @Override
    public List<CandidateSteps> createCandidateSteps() {
        return super.createCandidateSteps().stream().map(SerenityCandidateSteps::new).collect(Collectors.toList());
    }

    @Override
    protected List<Class<?>> stepsTypes() {
        List<Class<?>> types = new ArrayList<>();
        for (Class candidateClass : getCandidateClasses() ){
            if (hasAnnotatedMethods(candidateClass)) {
                types.add(candidateClass);
            }
        }
        return types;
    }

	@Override
    protected List<Class> getCandidateClasses() {

        List<Class<?>> allClassesUnderRootPackage = ClassFinder.loadClasses().withClassLoader(classLoader).fromPackage(rootPackage);
        List<Class> candidateClasses = new ArrayList<>();
        for(Class<?> classUnderRootPackage : allClassesUnderRootPackage) {
            if (hasAnnotatedMethods(classUnderRootPackage)) {
                candidateClasses.add(classUnderRootPackage);
            }
        }

        return candidateClasses;
    }

    @Override
    public Object createInstanceOfType(Class<?> type) {
        Object stepsInstance = getContext().newInstanceOf(type);
        StepAnnotations.injector().injectScenarioStepsInto(stepsInstance, getStepFactory());
        ThucydidesWebDriverSupport.initializeFieldsIn(stepsInstance);
        injectDependencies(stepsInstance);

        return stepsInstance;
    }

    private void injectDependencies(Object stepInstance) {
        List<DependencyInjector> dependencyInjectors = dependencyInjectorService.findDependencyInjectors();
        dependencyInjectors.add(new PageObjectDependencyInjector(ThucydidesWebDriverSupport.getPages()));

        for(DependencyInjector injector : dependencyInjectors) {
            injector.injectDependenciesInto(stepInstance);
        }
    }


    public SerenityStepContext getContext() {
        if (context.get() == null) {
            context.set(new SerenityStepContext());
        }
        return context.get();
    }

    public static void resetContext() {
        context.remove();
    }

    public static SerenityStepFactory withStepsFromPackage(String rootPackage, Configuration configuration) {
        return new SerenityStepFactory(configuration, rootPackage, defaultClassLoader());
    }

    private static ClassLoader defaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public SerenityStepFactory andConfiguration(Configuration configuration) {
        return new SerenityStepFactory(configuration, this.rootPackage, this.classLoader);
    }

    public InjectableStepsFactory andClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }
}
