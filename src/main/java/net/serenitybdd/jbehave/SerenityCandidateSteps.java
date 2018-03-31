package net.serenitybdd.jbehave;

import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.BeforeOrAfterStep;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.StepCandidate;

import java.util.List;
import java.util.stream.Collectors;

public class SerenityCandidateSteps implements CandidateSteps {
    private final CandidateSteps candidateSteps;

    SerenityCandidateSteps(CandidateSteps candidateSteps) {
        this.candidateSteps = candidateSteps;
    }

    @Override
    public List<StepCandidate> listCandidates() {

        return candidateSteps.listCandidates().parallelStream()
                .map(SerenityStepCandidate::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<BeforeOrAfterStep> listBeforeOrAfterStories() {
        return candidateSteps.listBeforeOrAfterStories();
    }

    @Override
    public List<BeforeOrAfterStep> listBeforeOrAfterStory(boolean givenStory) {
        return candidateSteps.listBeforeOrAfterStory(givenStory);
    }

    @Override
    public List<BeforeOrAfterStep> listBeforeOrAfterScenario(ScenarioType type) {
        return candidateSteps.listBeforeOrAfterScenario(type);
    }

    @Override
    public Configuration configuration() {
        return candidateSteps.configuration();
    }
}
