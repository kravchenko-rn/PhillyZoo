package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class CheckoutPopup extends BasePopup {
    @FindBy(css = ".offering-purchase h3")
    WebElement title;

    public CheckoutPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Checks whether the title is displayed.
     * @return {@code true} in case when the checkout title is displayed
     */
    public boolean isTitleDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(title, "Checking whether the title is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }
}
