package com.pivvit.phillyzoo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.base.BaseFEPage;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@FindBy(id = "facebook")
public class FacebookPage extends BaseFEPage {
    @FindBy(id = "login_form")
    WebElement loginForm;

    public FacebookPage() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Checks whether the login form is displayed.
     * @return {@code true} in case when login form is displayed
     */
    public boolean isLoginFormDisplayed() {
        return isElementVisible(loginForm);
    }
}
