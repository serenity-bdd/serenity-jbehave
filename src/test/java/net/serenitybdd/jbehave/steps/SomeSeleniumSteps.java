package net.serenitybdd.jbehave.steps;

import net.serenitybdd.jbehave.pages.StaticSitePage;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SomeSeleniumSteps {

    @Managed(driver="chrome")
    public WebDriver webDriver;

    @ManagedPages
    public Pages pages;

    @Steps
    SomeNestedSeleniumSteps the_user;

    StaticSitePage page;
    public SomeSeleniumSteps(Pages pages) {
        this.pages = pages;
        page = pages.get(StaticSitePage.class);
    }

    @Given("I have an implemented JBehave scenario that uses selenium")
    public void givenIHaveAnImplementedJBehaveScenarioThatUsesSelenium() {
    }

    @Given("the scenario uses selenium")
    public void givenTheScenarioUsesSelenium() {
    }

    @When("I run the web scenario")
    public void whenIRunTheWebScenario() {
        page.open();
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
        page.open();
    }

    @When("I enter the first name $firstname")
    public void whenIEnterTheFirstName(String firstname) {
        page.setFirstName(firstname);
    }

    @When("I enter the last name $lastname")
    public void whenIEnterTheLastName(String lastname) {
        page.setLastName(lastname);
    }

    @When("I type in the first name <firstname>")
    public void whenITypeInTheFirstName(String firstname) {
        the_user.enters_the_first_name(firstname);
    }

    @When("I type in the last name <lastname>")
    public void whenITypeInTheLastName(String lastname) {
        the_user.enters_the_last_name(lastname);
    }

    @Then("I should see entered values of <expectedFirstname> and <expectedLastname>")
    @Alias("I should see <firstname> and <lastname> in the names fields")
    public void thenIShouldSeeInTheNamesFields(String expectedFirstname,
                                               String expectedLastname) {
        StaticSitePage indexPage = page;
        assertThat(page.firstName().getValue(), is(expectedFirstname));
        assertThat(page.lastName().getValue(), is(expectedLastname));
    }

    @Then("I should see first name $expectedFirstname on the screen")
    public void thenIShouldSeeFirstNameOnTheScreen(String $expectedFirstname) {
        assertThat(page.firstName().getValue(), is($expectedFirstname));
    }

    @Then("I should see last name $expectedLastname on the screen")
    public void thenIShouldSeeLastNameOnTheScreen(String $expectedLastname) {
        assertThat(page.lastName().getValue(), is($expectedLastname));
    }

    @Then("I should be using $browser")
    public void andIShouldBeUsingHtmlUnit(String browser) {
        assertThat(((WebDriverFacade)webDriver).getDriverClass().getName(), containsString(browser));
    }

    @Given("the scenario throws an exception")
    public void throwAnException() {
        throw new ElementNotVisibleException("Oops");
    }

}
