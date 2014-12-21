package net.serenitybdd.jbehave.steps;

import net.serenitybdd.jbehave.pages.StaticSitePage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StoryStepsWithPageObjects {

    StaticSitePage indexPage;

    @Given("I start on the test page")
    public void givenIAmOnTheTestPage() {
        indexPage.open();
    }

    @When("I enter a first name $firstname")
    public void whenIEnterTheFirstName(String firstname) {
        indexPage.setFirstName(firstname);
    }

    @When("I enter a last name $lastname")
    public void whenIEnterTheLastName(String lastname) {
        indexPage.setLastName(lastname);
    }

    @Then("I should see the $firstname and $lastname")
    public void thenIShouldSeeInTheNamesFields(String firstname,
                                               String lastname) {
        assertThat(indexPage.firstName().getValue(), is(firstname));
        assertThat(indexPage.lastName().getValue(), is(lastname));
    }
}
