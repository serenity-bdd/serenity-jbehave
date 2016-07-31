package net.serenitybdd.jbehave;

import net.thucydides.core.model.TestOutcome;

import java.util.List;

public class TestOutcomeFinder {

    private final String name;

    public TestOutcomeFinder(String name) {
        this.name = name;
    }

    public static TestOutcomeFinder theScenarioCalled(String name) {
        return new TestOutcomeFinder(name);
    }

    public TestOutcome in(List<TestOutcome> testOutcomes) {
        for(TestOutcome testOutcome : testOutcomes) {
            if (testOutcome.getName().equalsIgnoreCase(name)) {
                return testOutcome;
            }
        }
        return null;
    }
}
