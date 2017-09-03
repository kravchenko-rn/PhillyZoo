package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;

public class CheckoutPopup extends BasePopup {
    @FindBy(css = ".offering-purchase h3")
    WebElement title;

    @FindBy(css = ".price-breakdown .purchase__remove-item")
    List<WebElement> removeTicketButtons;

    @FindBy(css = ".total>td:nth-of-type(2)")
    WebElement totalPrice;

    @FindBy(css = ".price-breakdown td:nth-of-type(1) span")
    List<WebElement> ticketQuantities;

    @FindBy(css = ".purchase__item td:nth-of-type(1)")
    List<WebElement> ticketDescriptions;

    public CheckoutPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Waits till checkout page is loaded.
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup waitForLoad() {
        driver().switchTo().frame(contentIFrame);

        waitForVisibility(title, "Waiting for popup to load.");

        driver().switchTo().defaultContent();
        return new CheckoutPopup();
    }

    /**
     * Retrieves total price text
     * @return string which contains total price text
     */
    public String getTotalPrice() {
        driver().switchTo().frame(contentIFrame);

        String result = totalPrice.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the quantity of the first ticket element.
     * @return string which contains ticket quantity
     */
    public String getFirstTicketQuantity() {
        driver().switchTo().frame(contentIFrame);

        if (ticketQuantities.size() == 0) {
            throw new SkipException("There are no ticket quantities. Maybe there is only one ticket selected.");
        }
        String result = ticketQuantities.get(0).getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Clicks remove ticket button of the first ticket in the list.
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup removeFirstTicket() {
        driver().switchTo().frame(contentIFrame);

        if (removeTicketButtons.size() == 0) {
            throw new SkipException("There are no remove ticket buttons.");
        }

        click(removeTicketButtons.get(0), "Clicking remove ticket button of the first ticket.");

        driver().switchTo().defaultContent();
        return new CheckoutPopup();
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

    /**
     * Checks whether the ticket quantity of the first element is displayed.
     * @return {@code true} in case when the ticket quantity is displayed
     */
    public boolean isFirstTicketQuantityDisplayed() {
        driver().switchTo().frame(contentIFrame);

        if (ticketQuantities.size() == 0) {
            throw new SkipException("There are no ticket quantities. Maybe there is only one ticket selected.");
        }
        boolean result = isElementVisible(ticketQuantities.get(0), "Checking whether the ticket quantity is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the non member ticket is present.
     * @return {@code true} in case when non member ticket is present
     */
    public boolean isNonMemberTicketPresent() {
        driver().switchTo().frame(contentIFrame);

        boolean result = false;
        for (WebElement ticketDescription: ticketDescriptions) {
            if (ticketDescription.getText().contains("Non-member")) {
                result = true;
                break;
            }
        }

        driver().switchTo().defaultContent();
        return result;
    }
}
