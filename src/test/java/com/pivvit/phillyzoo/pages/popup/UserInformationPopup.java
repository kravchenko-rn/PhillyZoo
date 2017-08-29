package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@FindBy(css = "#mainContainer>section")
public class UserInformationPopup extends BasePopup {
    @FindBy(css = "[ng-show^='showAccountProfile']")
    WebElement userInformationForm;

    public UserInformationPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Checks whether user information form is displayed
     * @return {@code true} in case when user information form is displayed
     */
    public boolean isUserInformationFormDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(userInformationForm,
                "Checking whether the user information form is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }
}
