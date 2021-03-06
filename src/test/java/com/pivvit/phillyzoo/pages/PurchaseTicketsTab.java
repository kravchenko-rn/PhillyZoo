package com.pivvit.phillyzoo.pages;

import com.pivvit.phillyzoo.pages.popup.PurchaseTicketsPopup;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.base.BaseFEPage;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class PurchaseTicketsTab extends BaseFEPage {
    @FindBy(css = "#tabPadder>div>a:nth-of-type(1)")
    WebElement membersButton;

    public PurchaseTicketsTab() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Opens members popup by clicking members button
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup openPurchaseTicketsPopup() {
        click(membersButton, "Opening Purchase Tickets popup");

        return new PurchaseTicketsPopup().waitForPageLoad();
    }
}
