package net.serenitybdd.jbehave.steps;

import net.thucydides.core.steps.ScenarioSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = "/spring/config.xml")
 public class NestedSteps extends ScenarioSteps {
    @Autowired
    public WidgetService widgetService;

    public NestedSteps() {
        super();
    }
}
