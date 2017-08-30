package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.properties.Properties;
import pivvit.properties.PropertiesNames;
import pivvit.utils.Reporter;
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

    @FindBy(css = "[ng-if$='showAlternateFields'] > a")
    WebElement alternateFieldsLink;

    @FindBy(css = "input[ng-model='purchaseStep.data.lastName']")
    WebElement customerLastNameInput;

    @FindBy(css = "input[ng-model$='zip']")
    WebElement customerZipCodeInput;

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
     * Sets text to customer zip code input field.
     * @param customerZipCode string with customer zip code
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup inputCustomerZipCode(String customerZipCode) {
        inputText(customerZipCodeInput, customerZipCode);
        return this;
    }

    /**
     * Sets text to customer last name input field.
     * @param customerLastName string with customer last name
     * @return  {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup inputCustomerLastName(String customerLastName) {
        inputText(customerLastNameInput, customerLastName);
        return this;
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
     * Clicks alternate fields link. Works for both:
     * switch to search by name and zip code; switch back to search by id and email.
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup clickAlternateFieldsLink() {
        driver().switchTo().frame(contentIFrame);

        click(alternateFieldsLink, "Clicking alternate fields link.");

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
     * Retrieves last name validation error text in the top left corner of the last name input field
     * @return string which contains error text
     */
    public String getLastNameValidationErrorText() {
        driver().switchTo().frame(contentIFrame);

        String result = customerLastNameInput.findElement(By.xpath("following-sibling::span")).getText();

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
     * Clears customer zip code input field
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup clearCustomerZipCode() {
        driver().switchTo().frame(contentIFrame);

        customerZipCodeInput.clear();

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Checks whether the email validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isEmailValidationErrorMessageDisplayed() {
        return isValidationErrorDisplayed(customerEmailInput, "invalid");
    }

    /**
     * Checks whether the last name validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isLastNameValidationErrorMessageDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = false;
        try {
            WebElement validationError = customerLastNameInput.findElement(By.xpath("following-sibling::span"));
            result = !validationError.getText().isEmpty();
        } catch (NoSuchElementException e) {
            Reporter.log(e);
        }

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @param errorText string which contains error message which should be displayed
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isErrorMessageDisplayed(String errorText) {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementTextVisible(errorMessage, errorText, "Checking whether error message is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the customer email input field is displayed
     * @return {@code true} in case when customer email input field is displayed
     */
    public boolean isEmailInputDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(customerEmailInput,
                "Checking whether the customer email input field is displayed.");

        driver().switchTo().defaultContent();
        return result;
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
     * Checks whether the customer last name input field is displayed
     * @return {@code true} in case when customer last name input field is displayed
     */
    public boolean isLastNameInputDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(customerLastNameInput,
                "Checking whether the customer last name input field is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the customer zip code input field is displayed
     * @return {@code true} in case when customer zip code input field is displayed
     */
    public boolean isZipCodeInputDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(customerZipCodeInput,
                "Checking whether the customer zip code input field is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }
}
