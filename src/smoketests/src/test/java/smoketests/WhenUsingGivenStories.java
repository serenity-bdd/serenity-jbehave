package smoketests;

import net.serenitybdd.jbehave.SerenityStory;

public class WhenUsingGivenStories extends SerenityStory {
    public WhenUsingGivenStories() {
        this.findStoriesCalled("**/when_using*_given_stories.story");
    }
}
