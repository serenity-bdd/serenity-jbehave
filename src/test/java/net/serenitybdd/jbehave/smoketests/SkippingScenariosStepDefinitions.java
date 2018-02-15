package net.serenitybdd.jbehave.smoketests;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SkippingScenariosStepDefinitions {

    @DefaultUrl("https://duckduckgo.com")
    public static class DuckDuckGoSearchPage extends PageObject {

        @FindBy(id="search_form_input_homepage")
        WebElementFacade searchField;

        @FindBy(id="search_button_homepage")
        WebElementFacade searchButton;

        @FindBy(className = "result__title")
        List<WebElementFacade> results;

        public void enterSearchTerm(String searchTerm) {
            searchField.type(searchTerm);
        }

        public void requestSearch() {
            searchButton.click();
        }

        public List<String> getResults() {
            return results.stream().map(element -> element.getText()).collect(Collectors.toList());
        }
    }

    public static class CuriousSurfer {

        DuckDuckGoSearchPage searchPage;

        @Step
        public void opensTheSearchApp() {
            searchPage.open();
        }

        @Step
        public void searchesFor(String searchTerm) {
            searchPage.enterSearchTerm(searchTerm);
            searchPage.requestSearch();
        }

        @Step
        public void shouldSeeTitle(String title) {
            assertThat(searchPage.getTitle()).contains(title);
        }

        @Step
        public void shouldSeeAListOfResults() {
            assertThat(searchPage.getResults().size()).isGreaterThan(0);
        }
    }

    @Steps
    CuriousSurfer connor;

    @Given("I want to search for something")
    public void givenIWantToSearchForFruit() {
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

    @Given("I want to indicate that a scenario should be performed manually")
    public void manual() {}

    @Given("I also want it appearing in the skipped scenarios")
    public void appear_as_skipped() {}

    @When("I tag it as ${tags}")
    public void tag_as(String tags) {}

    @Then("it should be reported as ${state}")
    public void reported_as(String states) {}

}
