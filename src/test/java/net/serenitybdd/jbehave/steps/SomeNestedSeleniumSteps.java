package net.serenitybdd.jbehave.steps;

import net.serenitybdd.jbehave.pages.StaticSitePage;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebDriver;

public class SomeNestedSeleniumSteps {

    @Managed
    public WebDriver webDriver;

    StaticSitePage staticSitePage;

    @Step
    public void enters_the_first_name(String firstname) {
        staticSitePage.setFirstName(firstname);
    }

    @Step
    public void enters_the_last_name(String lastname) {
        staticSitePage.setLastName(lastname);
    }

    @Step
    public void open_first_page() {
        staticSitePage.open();
    }

    @Step
    public void reads_first_name() {
        staticSitePage.firstName().getValue();
    }


}
