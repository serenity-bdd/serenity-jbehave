package net.serenitybdd.jbehave;

import ch.lambdaj.function.convert.Converter;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.BeforeOrAfterStep;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.StepCandidate;

import java.util.List;

import static ch.lambdaj.Lambda.convert;

public class SerenityCandidateSteps implements CandidateSteps {
    private final CandidateSteps candidateSteps;

    public SerenityCandidateSteps(CandidateSteps candidateSteps) {
        this.candidateSteps = candidateSteps;
    }

    public List<StepCandidate> listCandidates() {
        return convert(candidateSteps.listCandidates(), toSerenityStepCandidates());
    }

    private Converter<StepCandidate, StepCandidate> toSerenityStepCandidates() {
        return new Converter<StepCandidate, StepCandidate>() {
            public StepCandidate convert(StepCandidate stepCandidate) {
                return new SerenityStepCandidate(stepCandidate);
            }
        };
    }

    public List<BeforeOrAfterStep> listBeforeOrAfterStories() {
        return candidateSteps.listBeforeOrAfterStories();
    }

    public List<BeforeOrAfterStep> listBeforeOrAfterStory(boolean givenStory) {
        return candidateSteps.listBeforeOrAfterStory(givenStory);
    }

    public List<BeforeOrAfterStep> listBeforeOrAfterScenario(ScenarioType type) {
        return candidateSteps.listBeforeOrAfterScenario(type);
    }

    public Configuration configuration() {
        return candidateSteps.configuration();
    }
}
