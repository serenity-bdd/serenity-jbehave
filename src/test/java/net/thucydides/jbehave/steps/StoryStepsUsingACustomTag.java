package net.thucydides.jbehave.steps;

import net.thucydides.core.Thucydides;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class StoryStepsUsingACustomTag {

    @Given("the scenario uses a custom tag")
    public void givenTheScenarioUsesACustomTag() {
   }

    @Then("I should be able to read the custom tag")
    public void thenIShouldBeAbleToReadTheCustomTag() {
        Map<String, String> metadata = Thucydides.getCurrentSession().getMetaData();
        assertThat(metadata).isNotEmpty();
    }

    @Given("the scenario uses the custom tag $tagvalue")
    public void givenTheScenarioUsesTheCustomTag(String tagvalue) {
    }

    @Then("I should be able to read the custom tag $tagvalue and the global tag '$global'")
    public void thenIShouldBeAbleToReadTheCustomTag(String tagvalue, String global) {
        Map<String, String> metadata = Thucydides.getCurrentSession().getMetaData();
        assertThat(metadata).isNotEmpty();
        assertThat(metadata.get("sql")).containsIgnoringCase(tagvalue);
        assertThat(metadata.get("global")).containsIgnoringCase(global);
    }
}

