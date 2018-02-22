package smoketests.stepdefinitions;

import net.serenitybdd.core.SkipNested;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class SkippingScenariosStepDefinitions {

    @DefaultUrl("https://duckduckgo.com")
    public static class DuckDuckGoSearchPage extends PageObject {

        @FindBy(id="search_form_input_homepage")
        WebElementFacade searchField;

        @FindBy(id="search_button_homepage")
        WebElementFacade searchButton;

        @FindBy(className = "result__title")
        List<WebElementFacade> results;

        @Step
        public void enterSearchTerm(String searchTerm) {
            searchField.type(searchTerm);
            searchButton.click();
        }

        public List<String> getResults() {
            return results.stream().map(element -> element.getText()).collect(Collectors.toList());
        }
    }

    public static class TestSteps {

        @Step(callNestedMethods = false)
        public void print(String value) {
            System.out.println("INSIDE STEP WITH VALUE " + value);
        }
    }

    public static class CuriousSurfer implements SkipNested {

        DuckDuckGoSearchPage searchPage;

        @Step
        public void opensTheSearchApp() {
            searchPage.open();
        }

        @Step
        public void searchesFor(String searchTerm) {
            System.out.println("searchesFor " + searchTerm);
            searchPage.enterSearchTerm(searchTerm);
        }

        @Step
        public void shouldSeeTitle(String title) {
            System.out.println("shouldSeeTitle " + title);
            assertThat(searchPage.getTitle()).contains(title);
        }

        @Step
        public void shouldSeeAListOfResults() {
            assertThat(searchPage.getResults().size()).isGreaterThan(0);
        }
    }

    @Steps
    CuriousSurfer connor;

    @Steps
    TestSteps steps;

    @When("print '$value'")
    public void print(String value) {
        System.out.println("WHEN STEP FOR " + value);
        steps.print(value);
    }

    @Given("I want to search for something")
    public void givenIWantToSearchForFruit() {
        System.out.println("Opens search page");
        connor.opensTheSearchApp();
    }

    @When("I lookup $searchTerm")
    public void whenILookup(String searchTerm) {
        connor.searchesFor(searchTerm);
    }

    @Then("I should see \"$title\" in the page title")
    public void thenIShouldSeeTitle(String title) {
        connor.shouldSeeTitle(title);
    }

    @Then("I should see search results")
    public void thenIShouldSeeSearchResults() {
        connor.shouldSeeAListOfResults();
    }

    @Then("subsequent steps should be ignored")
    public void ignoreSubsequentSteps() {}

    @Then("steps should be ignored")
    public void ignoreSteps() {}


    @Given("I want to indicate that a scenario should be performed manually")
    public void manual() {}

    @Given("I also want it appearing in the skipped scenarios")
    public void appear_as_skipped() {}

    @When("I tag it as ${tags}")
    public void tag_as(String tags) {}

    @Then("it should be reported as ${state}")
    public void reported_as(String states) {}
}
