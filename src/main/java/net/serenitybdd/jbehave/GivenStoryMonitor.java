package net.serenitybdd.jbehave;

public class GivenStoryMonitor {

    private boolean inGivenStory = false;

    public void enteringGivenStory() {
        inGivenStory = true;
    }

    public boolean isInGivenStory() {
        return inGivenStory;
    }

    public void exitingGivenStory() {
        inGivenStory = false;
    }

    public void clear() {
        inGivenStory = false;
    }
}
