package net.thucydides.jbehave;

import ch.lambdaj.function.convert.Converter;
import com.google.common.collect.Lists;
import net.sf.extcos.ComponentQuery;
import net.sf.extcos.ComponentScanner;
import net.thucydides.core.steps.StepAnnotations;
import net.thucydides.core.steps.StepFactory;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.steps.AbstractStepsFactory;
import org.jbehave.core.steps.CandidateSteps;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ch.lambdaj.Lambda.convert;

public class ThucydidesStepFactory extends AbstractStepsFactory {

    private static final ThreadLocal<ThucydidesStepContext> context = new ThreadLocal<ThucydidesStepContext>();

    private final String rootPackage;

    public ThucydidesStepFactory(Configuration configuration, String rootPackage) {
        super(configuration);
        this.rootPackage = rootPackage;
    }

    private StepFactory getStepFactory() {
        return ThucydidesWebDriverSupport.getStepFactory();
    }

    public List<CandidateSteps> createCandidateSteps() {
        List<CandidateSteps> coreCandidateSteps = super.createCandidateSteps();
        return convert(coreCandidateSteps, toThucydidesCandidateSteps());
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
        ComponentScanner scanner = new ComponentScanner();

        Set<Class<?>> allClassesUnderRootPackage = scanner.getClasses(new ComponentQuery() {
            protected void query() {
                select().from(rootPackage).returning(all());
            }
        });

        List<Class> candidateClasses = Lists.newArrayList();
        for(Class<?> classUnderRootPackage : allClassesUnderRootPackage) {
            if (hasAnnotatedMethods(classUnderRootPackage)) {
                candidateClasses.add(classUnderRootPackage);
            }
        }

        return candidateClasses;
    }

    private Converter<CandidateSteps, CandidateSteps> toThucydidesCandidateSteps() {
        return new Converter<CandidateSteps, CandidateSteps>() {
            public CandidateSteps convert(CandidateSteps candidateSteps) {
                return new ThucydidesCandidateSteps(candidateSteps, getStepFactory());
            }
        };
    }

    public Object createInstanceOfType(Class<?> type) {
        Object stepsInstance = getContext().newInstanceOf(type);
        StepAnnotations.injectScenarioStepsInto(stepsInstance, getStepFactory());
        ThucydidesWebDriverSupport.initializeFieldsIn(stepsInstance);
        return stepsInstance;
    }

    public ThucydidesStepContext getContext() {
        if (context.get() == null) {
            context.set(new ThucydidesStepContext());
        }
        return context.get();
    }

    public static void resetContext() {
        context.remove();
    }

    public static ThucydidesStepFactory withStepsFromPackage(String rootPackage, List<Format> formats) {
        return new ThucydidesStepFactory(ThucydidesJBehave.defaultConfiguration(formats), rootPackage);
    }

    public ThucydidesStepFactory andConfiguration(Configuration configuration) {
        return new ThucydidesStepFactory(configuration, this.rootPackage);
    }

}
