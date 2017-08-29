package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@FindBy(css = "#mainContainer>section")
public class PurchaseSummaryPopup extends BasePopup {
    @FindBy(css = "[ng-show='vm.showPurchaseSummary()']")
    WebElement purchaseSummary;

    public PurchaseSummaryPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Checks whether the purchase summary is displayed.
     * @return {@code true} in case when the purchase summary is displayed
     */
    public boolean isPurchaseSummaryDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(purchaseSummary, "Checking whether the purchase summary page is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }
}
