package net.thucydides.jbehave.steps;

import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.pages.Pages;
import net.thucydides.jbehave.pages.StaticSitePage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SomeSeleniumSteps {

    @Managed
    public WebDriver webDriver;

    @ManagedPages
    public Pages pages;

    @Given("I have an implemented JBehave scenario that uses selenium")
    public void givenIHaveAnImplementedJBehaveScenarioThatUsesSelenium() {
    }

    @Given("the scenario uses selenium")
    public void givenTheScenarioUsesSelenium() {
    }

    @When("I run the web scenario")
    public void whenIRunTheWebScenario() {
        StaticSitePage indexPage = pages.get(StaticSitePage.class);
        System.out.println("indexPage = " + indexPage);
//        indexPage.open();
//        indexPage.setFirstName("Joe");
//        assertThat(indexPage.firstName().getValue(), is("Joe"));
    }

    @Then("the webdriver variable should be correctly instantiated")
    public void thenTheWebdriverVariableShouldBeCorrectlyInstantiated() {
        assertThat(webDriver, is(notNullValue()));
    }

    @Then("the pages variable should be correctly instantiated")
    public void thenThePagesVariableShouldBeCorrectlyInstantiated() {
        assertThat(pages, is(notNullValue()));
    }

    @Given("I am on the test page")
    public void givenIAmOnTheTestPage() {
        StaticSitePage indexPage = pages.get(StaticSitePage.class);
    }

    @When("I enter the first name <firstname>")
    public void whenIEnterTheFirstName(@Named("firstname") String firstname) {
        pages.get(StaticSitePage.class).setFirstName(firstname);
    }

    @When("I enter the last name <lastname>")
    public void whenIEnterTheLastName(@Named("lastname") String lastname) {
        pages.get(StaticSitePage.class).setLastName(lastname);
    }

    @Then("I should see <firstname> and <lastname> in the names fields")
    public void thenIShouldSeeInTheNamesFields(@Named("firstname") String firstname,
                                               @Named("lastname") String lastname) {
        assertThat(pages.get(StaticSitePage.class).firstName().getValue(), is(firstname));
        assertThat(pages.get(StaticSitePage.class).lastName().getValue(), is(lastname));
    }


}
