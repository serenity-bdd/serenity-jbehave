package net.thucydides.jbehave.steps;

import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.webdriver.WebDriverFacade;
import net.thucydides.jbehave.pages.StaticSitePage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
}
