package net.serenitybdd.jbehave.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(locations = "/spring/config.xml")
public class SpringEnabledSteps {

    @Autowired
    public WidgetService widgetService;

    private String widgetName;

    @Given("I hava an autowired Spring bean")
    public void givenIHavaAnAutowiredSpringBean() {
        assertThat(widgetService, notNullValue());
    }

    @When("I use the bean")
    public void whenIUseTheBean() {
        widgetName = widgetService.getName();
    }

    @Then("it should be instanciated")
    public void thenItShouldBeInstanciated() {
        assertThat(widgetName, is("Widgets"));
    }

}
