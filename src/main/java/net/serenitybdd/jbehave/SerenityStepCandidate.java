package net.serenitybdd.jbehave;

import net.serenitybdd.jbehave.reflection.Extract;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.StepType;
import org.jbehave.core.steps.context.StepsContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class SerenityStepCandidate extends StepCandidate {

    private final StepCandidate stepCandidate;

    public SerenityStepCandidate(StepCandidate stepCandidate) {

        super(stepCandidate.getPatternAsString(),
                stepCandidate.getPriority(),
                stepCandidate.getStepType(),
                stepCandidate.getMethod(),
                (Class<?>) Extract.field("stepsType").from(stepCandidate),
                (InjectableStepsFactory) Extract.field("stepsFactory").from(stepCandidate),
                new StepsContext(),
                (Keywords) Extract.field("keywords").from(stepCandidate),
                new RegexPrefixCapturingPatternParser(),
                new ParameterConverters(),
                new ParameterControls());
        this.composedOf(stepCandidate.composedSteps());
        this.stepCandidate = stepCandidate;
    }

    @Override
    public Method getMethod() {
        return stepCandidate.getMethod();
    }

    @Override
    public Integer getPriority() {
        return stepCandidate.getPriority();
    }

    @Override
    public String getPatternAsString() {
        return stepCandidate.getPatternAsString();
    }

    @Override
    public StepType getStepType() {
        return stepCandidate.getStepType();
    }

    @Override
    public String getStartingWord() {
        return stepCandidate.getStartingWord();
    }

    @Override
    public boolean isComposite() {
        return stepCandidate.isComposite();
    }

    @Override
    public String[] composedSteps() {
        return stepCandidate.composedSteps();
    }

    @Override
    public boolean ignore(String stepAsString) {
        return stepCandidate.ignore(stepAsString);
    }

    @Override
    public boolean isPending() {
        return stepCandidate.isPending();
    }

    @Override
    public boolean matches(String stepAsString) {
        return stepCandidate.matches(stepAsString);
    }

    @Override
    public boolean matches(String step, String previousNonAndStep) {
        return stepCandidate.matches(step, previousNonAndStep);
    }

    @Override
    public Step createMatchedStep(String stepAsString, Map<String, String> namedParameters, List<Step> composedSteps) {
        return stepCandidate.createMatchedStep(stepAsString, namedParameters, composedSteps);
    }

    @Override
    public String toString() {
        return stepCandidate.toString();
    }
}
