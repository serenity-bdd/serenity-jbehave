package net.serenitybdd.jbehave.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = "/spring/config.xml")
 public class NestedSteps {
    @Autowired
    public WidgetService widgetService;
}
