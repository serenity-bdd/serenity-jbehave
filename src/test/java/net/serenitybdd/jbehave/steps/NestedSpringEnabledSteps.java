package net.serenitybdd.jbehave.steps;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(locations = "/spring/config.xml")
public class NestedSpringEnabledSteps {

    @Autowired
    public WidgetService widgetService;

    private String widgetName;

    @Steps
    private NestedSteps nestedSteps;

    @Given("I hava a nested autowired Spring bean")
    public void givenIHavaAnAutowiredSpringBean() {
        assertThat(nestedSteps.widgetService, notNullValue());
    }

    @When("I use the nested bean")
    public void whenIUseTheBean() {
        widgetName = nestedSteps.widgetService.getName();
    }

    @Then("the nested bean should be instanciated")
    public void thenItShouldBeInstanciated() {
        assertThat(widgetName, is("Widgets"));
    }

}
