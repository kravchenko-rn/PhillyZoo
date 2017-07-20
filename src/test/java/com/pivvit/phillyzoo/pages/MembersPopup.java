package com.pivvit.phillyzoo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.base.BaseFEPage;
import pivvit.properties.Properties;
import pivvit.properties.PropertiesNames;
import pivvit.utils.Reporter;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;

@FindBy(css = "#mainContainer>section")
public class MembersPopup extends BaseFEPage {
    @FindBy(id = "pivvitContent")
    WebElement contentIframe;

    @FindBy(css = ".pv-loading")
    WebElement loadingIndicator;

    @FindBy(css = ".campaign-offering-details-container")
    WebElement container;

    @FindBy(css = "input[ng-model$='customerID']")
    WebElement customerIdInput;

    @FindBy(css = "input[ng-model$='purchaseStep.data.email']")
    WebElement customerEmailInput;

    @FindBy(css = "form .fa-question-circle")
    WebElement questionMark;

    @FindBy(css = "form .tooltip")
    WebElement tooltip;

    @FindBy(css = "button[ng-click='purchaseStep.search()']")
    WebElement searchButton;

    @FindBy(css = ".form-field .error")
    WebElement errorMessage;

    @FindBy(css = "div[ng-class$='purchaseStep.errors.email }'] span")
    WebElement emailValidationError;

    @FindBy(css = "div[ng-repeat$='lookupResults']")
    List<WebElement> lookupResults;

    public MembersPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Waits till the page is loaded.
     * @return {@link MembersPopup} page
     */
    public MembersPopup waitForPageLoad() {
        waitForLoad(this);
        driver().switchTo().frame(contentIframe);

        waitForVisibility(container, "Waiting for popup to load");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Waits till loading indicator goes away after the search button was clicked
     * @return {@link MembersPopup} page
     */
    public MembersPopup waitTillLoadingIndicatorDisappears() {
        driver().switchTo().frame(contentIframe);

        waitForInvisibility(loadingIndicator, "Waiting for loading indicator to go away.");
        sleep(1);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Hovers mouse over the question mark in the customer id input field.
     * WORKAROUND: In Firefox browser it performs click instead of hovering because geckodriver
     * is not capable of hovering yet.
     * In this particular case it's not really important whether it's hover or click..
     * @return {@link MembersPopup} page
     */
    public MembersPopup hoverQuestionMark() {
        driver().switchTo().frame(contentIframe);

        if (Properties.getBrowser(PropertiesNames.BROWSER).equals(BrowserType.FIREFOX)) {
            click(questionMark, "Clicking question mark instead of hovering (workaround for firefox)");
        } else {
            hover(questionMark, "Hovering question mark.");
        }
        sleep(1);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Sets text to customer id input field.
     * @param customerId string with customer id
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputCustomerId(String customerId) {
        inputText(customerIdInput, customerId);
        return this;
    }

    /**
     * Clears customer id input field
     * @return {@link MembersPopup} page
     */
    public MembersPopup clearCustomerId() {
        driver().switchTo().frame(contentIframe);

        customerIdInput.clear();

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Sets text to customer email input field.
     * @param customerEmail string with customer email
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputCustomerEmail(String customerEmail) {
        inputText(customerEmailInput, customerEmail);
        return this;
    }

    /**
     * Sets text into an input field
     * @param inputElement an element to set text to
     * @param text string with text to set
     */
    private void inputText(WebElement inputElement, String text) {
        driver().switchTo().frame(contentIframe);

        Reporter.log("Clearing customer input field.");
        inputElement.clear();
        Reporter.log("Typing text into input field: " + text);
        inputElement.sendKeys(text);

        driver().switchTo().defaultContent();
    }

    /**
     * Clicks search button
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickSearchButton() {
        driver().switchTo().frame(contentIframe);

        click(searchButton, "Clicking search button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Retrieves email validation error text in the top left corner of the email input field
     * @return string which contains error text
     */
    public String getEmailValidationErrorText() {
        driver().switchTo().frame(contentIframe);

        String result = emailValidationError.getText();

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
        return !getEmailValidationErrorText().isEmpty();
    }

    /**
     * Retrieves error message text
     * @return string which contains error text
     */
    public String getErrorMessageText() {
        driver().switchTo().frame(contentIframe);

        String result = errorMessage.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isErrorMessageDisplayed() {
        return !getErrorMessageText().isEmpty();
    }

    public boolean isTooltipVisible() {
        driver().switchTo().frame(contentIframe);

        boolean result =  isElementVisible(tooltip, "Checking whether the tooltip is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the page is loaded by presence of the main element
     * @return {@code true} in case when the main element is present
     */
    public boolean isLoaded() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementPresent(container);

        driver().switchTo().defaultContent();
        return result;
    }
}
