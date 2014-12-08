package net.thucydides.jbehave.steps;

import net.serenity_bdd.core.Serenity;
import org.jbehave.core.annotations.BeforeStory;
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
        Map<String, String> metadata = Serenity.getCurrentSession().getMetaData();
        assertThat(metadata).isNotEmpty();
    }

    @Then("I should be able to read the issue tag")
    public void thenIShouldBeAbleToReadTheIssueTag() {
        Map<String, String> metadata = Serenity.getCurrentSession().getMetaData();
        assertThat(metadata.get("issue")).isEqualTo("ISSUE-1");
    }

    @Given("the scenario uses the custom tag $tagvalue")
    public void givenTheScenarioUsesTheCustomTag(String tagvalue) {
    }

    @Then("I should be able to read the custom tag $tagvalue and the global tag '$global'")
    public void thenIShouldBeAbleToReadTheCustomTag(String tagvalue, String global) {
        Map<String, String> metadata = Serenity.getCurrentSession().getMetaData();
        assertThat(metadata).isNotEmpty();
        assertThat(metadata.get("sql")).containsIgnoringCase(tagvalue);
        assertThat(metadata.get("global")).containsIgnoringCase(global);
    }

    @Then("I should be able to use the <name> and the <age> field in my stored procedure")
    public void thenIShouldBeAbleToUseThenameAndTheageFieldInMyStoredProcedure(String name, String age) {
    }

    @Then("the local variable should be defined")
    public void thenTheLocalVariableShouldBeDefined() {
        Map<String, String> metadata = Serenity.getCurrentSession().getMetaData();
        assertThat(metadata.get("local")).isEqualTo("defined");
    }


    @Then("the local variable should not be defined")
    public void thenTheLocalVariableShouldNotBeDefined() {
        Map<String, String> metadata = Serenity.getCurrentSession().getMetaData();
        assertThat(metadata.get("local")).isNull();
    }

    @BeforeStory
    public void beforeStory() {
        Map<String, String> metadata = Serenity.getCurrentSession().getMetaData();
        if (!metadata.isEmpty() && metadata.get("global") != null) {
            assertThat(metadata.get("global")).containsIgnoringCase("shared");
        }
    }

}

