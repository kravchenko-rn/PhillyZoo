package com.pivvit.phillyzoo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.properties.Properties;
import pivvit.properties.PropertiesNames;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@FindBy(css = "#mainContainer>section")
public class PurchaseTicketsPopup extends BasePopup {
    @FindBy(css = "form .fa-question-circle")
    WebElement questionMark;

    @FindBy(css = "form .tooltip")
    WebElement tooltip;

    @FindBy(css = "input[ng-model$='customerID']")
    WebElement customerIdInput;

    @FindBy(css = "input[ng-model$='purchaseStep.data.email']")
    WebElement customerEmailInput;

    @FindBy(css = ".form-field .error")
    WebElement errorMessage;

    @FindBy(css = "button[ng-click='purchaseStep.search()']")
    WebElement searchButton;

    public PurchaseTicketsPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Waits till the page is loaded.
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup waitForPageLoad() {
        waitForLoad(this);
        driver().switchTo().frame(contentIFrame);

        waitForVisibility(container, "Waiting for Purchase Tickets popup to load");

        driver().switchTo().defaultContent();
        return this;
    }

    public PurchaseTicketsPopup waitTillLoadingIndicatorDisappears() {
        super.waitTillLoadingIndicatorDisappears();
        return new PurchaseTicketsPopup();
    }

    /**
     * Hovers mouse over the question mark in the customer id input field.
     * WORKAROUND: In Firefox browser it performs click instead of hovering because geckodriver
     * is not capable of hovering yet.
     * In this particular case it's not really important whether it's hover or click..
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup hoverQuestionMark() {
        driver().switchTo().frame(contentIFrame);

        if (Properties.getBrowser(PropertiesNames.BROWSER).equals(BrowserType.FIREFOX)) {
            click(questionMark, "Clicking question mark instead of hovering (workaround for firefox)");
            waitForVisibilityNotStrict(tooltip, 5);
        } else {
            hover(questionMark, "Hovering question mark.");
        }

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Checks whether the tooltip is displayed.
     * @return {@code true} in case when the tooltip is displayed
     */
    public boolean isTooltipVisible() {
        driver().switchTo().frame(contentIFrame);

        boolean result =  isElementVisible(tooltip, "Checking whether the tooltip is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Sets text to customer id input field.
     * @param customerId string with customer id
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup inputCustomerId(String customerId) {
        inputText(customerIdInput, customerId);
        return this;
    }

    /**
     * Sets text to customer email input field.
     * @param customerEmail string with customer email
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup inputCustomerEmail(String customerEmail) {
        inputText(customerEmailInput, customerEmail);
        return this;
    }

    /**
     * Clears customer email input field
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup clearCustomerEmail() {
        driver().switchTo().frame(contentIFrame);

        customerEmailInput.clear();

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Checks whether the error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isErrorMessageDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = !errorMessage.getText().isEmpty();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the email validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isEmailValidationErrorMessageDisplayed() {
        driver().switchTo().frame(contentIFrame);

        WebElement validationError = customerEmailInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "invalid", "Checking whether the email validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Clicks search button
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup clickSearchButton() {
        driver().switchTo().frame(contentIFrame);

        click(searchButton, "Clicking search button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Retrieves error message text
     * @return string which contains error text
     */
    public String getErrorMessageText() {
        driver().switchTo().frame(contentIFrame);

        String result = errorMessage.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Clears customer id input field
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup clearCustomerId() {
        driver().switchTo().frame(contentIFrame);

        customerIdInput.clear();

        driver().switchTo().defaultContent();
        return this;
    }
}
