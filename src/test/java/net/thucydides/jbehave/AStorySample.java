package net.thucydides.jbehave;

import net.thucydides.jbehave.ThucydidesJUnitStories;

public class AStorySample extends ThucydidesJUnitStories {
    public AStorySample(String storyName) {
        findStoriesCalled(storyName);
    }
}

