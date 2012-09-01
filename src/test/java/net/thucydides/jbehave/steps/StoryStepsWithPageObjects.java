package net.thucydides.jbehave.steps;

import net.thucydides.core.pages.Pages;
import net.thucydides.jbehave.pages.StaticSitePage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StoryStepsWithPageObjects {

    private final Pages pages;

    StaticSitePage indexPage;

    public StoryStepsWithPageObjects(Pages pages) {
        this.pages = pages;
        indexPage = pages.get(StaticSitePage.class);
    }

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
