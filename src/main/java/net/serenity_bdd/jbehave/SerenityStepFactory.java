package net.serenity_bdd.jbehave;

import ch.lambdaj.function.convert.Converter;
import com.google.common.collect.Lists;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.steps.DependencyInjector;
import net.thucydides.core.steps.PageObjectDependencyInjector;
import net.thucydides.core.steps.StepAnnotations;
import net.thucydides.core.steps.StepFactory;
import net.thucydides.core.steps.di.DependencyInjectorService;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.AbstractStepsFactory;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.convert;

public class SerenityStepFactory extends AbstractStepsFactory {

    private static final ThreadLocal<SerenityStepContext> context = new ThreadLocal<SerenityStepContext>();

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
        return ThucydidesWebDriverSupport.getStepFactory().thatThrowsExcpetionsImmediately();
    }

    public List<CandidateSteps> createCandidateSteps() {
        List<CandidateSteps> coreCandidateSteps = super.createCandidateSteps();
        return convert(coreCandidateSteps, toSerenityCandidateSteps());
    }

    @Override
    protected List<Class<?>> stepsTypes() {
        List<Class<?>> types = new ArrayList<Class<?>>();
        for (Class candidateClass : getCandidateClasses() ){
            if (hasAnnotatedMethods(candidateClass)) {
                types.add(candidateClass);
            }
        }
        return types;
    }

    private List<Class> getCandidateClasses() {

        List<Class<?>> allClassesUnderRootPackage = ClassFinder.loadClasses().withClassLoader(classLoader).fromPackage(rootPackage);
        List<Class> candidateClasses = Lists.newArrayList();
        for(Class<?> classUnderRootPackage : allClassesUnderRootPackage) {
            if (hasAnnotatedMethods(classUnderRootPackage)) {
                candidateClasses.add(classUnderRootPackage);
            }
        }

        return candidateClasses;
    }

    private Converter<CandidateSteps, CandidateSteps> toSerenityCandidateSteps() {
        return new Converter<CandidateSteps, CandidateSteps>() {
            public CandidateSteps convert(CandidateSteps candidateSteps) {
                return new SerenityCandidateSteps(candidateSteps);
            }
        };
    }

    public Object createInstanceOfType(Class<?> type) {
        Object stepsInstance = getContext().newInstanceOf(type);
        StepAnnotations.injectScenarioStepsInto(stepsInstance, getStepFactory());
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
