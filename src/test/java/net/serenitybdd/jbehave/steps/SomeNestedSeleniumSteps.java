package net.serenitybdd.jbehave.steps;

import net.serenitybdd.jbehave.pages.StaticSitePage;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import org.openqa.selenium.WebDriver;

public class SomeNestedSeleniumSteps {

    @Managed
    public WebDriver webDriver;

    @ManagedPages
    public Pages pages;

    @Step
    public void enters_the_first_name(String firstname) {
        pages.get(StaticSitePage.class).setFirstName(firstname);
    }

    @Step
    public void enters_the_last_name(String lastname) {
        pages.get(StaticSitePage.class).setLastName(lastname);
    }

    @Step
    public void open_first_page() {
        pages.get(StaticSitePage.class).open();
    }

    @Step
    public void reads_first_name() {
        pages.get(StaticSitePage.class).firstName().getValue();
    }


}
