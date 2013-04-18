package net.thucydides.jbehave.steps;

import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.StepFactory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;

import static net.thucydides.core.steps.StepData.withTestDataFrom;

public class SomeDataDrivenSteps {

    @ManagedPages
    Pages pages;

    @Steps
    SomeSeleniumSteps steps;

    @Steps
    SomeNormalSteps normalSteps;

    @Steps
    EnterNamesStep enterNameSteps;

    String dataSource;

    @Given("the names in $dataSource")
    public void givenTheNamesIn(String dataSource) {
        this.dataSource = dataSource;
    }

    @When("we enter these values")
    public void whenWeEnterTheseValues() {
        steps.givenIAmOnTheTestPage();
    }

    @Then("we should see them on the screen")
    public void thenWeShouldSeeThemOnTheScreen() throws IOException {
        StepFactory factory = new StepFactory(pages);
        withTestDataFrom(dataSource).usingFactory(factory).run(enterNameSteps).enterAndCheckName();
    }
}
