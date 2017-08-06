package com.pivvit.phillyzoo.pages;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import pivvit.base.BaseFEPage;
import pivvit.properties.Properties;
import pivvit.properties.PropertiesNames;
import pivvit.utils.Reporter;
import pivvit.utils.SoftAssert;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@FindBy(css = "#mainContainer>section")
public class MembersPopup extends BaseFEPage {
    @FindBy(id = "pivvitContent")
    WebElement contentIframe;

    @FindBy(css = ".pv-loading")
    WebElement loadingIndicator;

    @FindBy(css = ".campaign-offering-details-container")
    WebElement container;

    @FindBy(css = "[ng-if='purchaseStep.title']")
    WebElement pageTitle;

    @FindBy(css = ".offering-purchase h3")
    WebElement checkoutTitle;

    @FindBy(css = "input[ng-model$='customerID']")
    WebElement customerIdInput;

    @FindBy(css = "input[ng-model$='purchaseStep.data.email']")
    WebElement customerEmailInput;

    @FindBy(css = "input[ng-model='purchaseStep.data.lastName']")
    WebElement customerLastNameInput;

    @FindBy(css = "input[ng-model$='zip']")
    WebElement customerZipCodeInput;

    @FindBy(css = "form .fa-question-circle")
    WebElement questionMark;

    @FindBy(css = "form .tooltip")
    WebElement tooltip;

    @FindBy(css = "button[ng-click='purchaseStep.search()']")
    WebElement searchButton;

    @FindBy(css = ".form-field .error")
    WebElement errorMessage;

    @FindBy(css = "div[ng-class$='purchaseStep.errors.email }'] > span")
    WebElement emailValidationError;

    @FindBy(css = "div[ng-class$='purchaseStep.errors.lastName }'] > span")
    WebElement lastNameValidationError;

    @FindBy(css = ".account-profile-form .mf__row:nth-of-type(1) .mf__field:nth-of-type(1) .error-text")
    WebElement firstNameUserFormValidationError;

    @FindBy(css = ".account-profile-form .mf__row:nth-of-type(1) .mf__field:nth-of-type(2) .error-text")
    WebElement lastNameUserFormValidationError;

    @FindBy(css = ".account-profile-form .mf__row:nth-of-type(2) .mf__field:nth-of-type(1) .error-text")
    WebElement emailUserFormValidationError;

    @FindBy(css = ".account-profile-form .mf__row:nth-of-type(3) .mf__field:nth-of-type(1) .error-text")
    WebElement passwordUserFormValidationError;

    @FindBy(css = "[ng-model$='zipFilter']")
    WebElement zipCodeFilterSelect;

    @FindBy(css = "[ng-model$='phoneFilter']")
    WebElement phoneFilterInput;

    @FindBy(css = "[ng-show^='showAccountProfile']")
    WebElement userInformationForm;

    @FindBy(css = "[ng-model='accountProfile.firstName']")
    WebElement firstNameUserFormInput;

    @FindBy(css = "[ng-model='accountProfile.lastName']")
    WebElement lastNameUserFormInput;

    @FindBy(css = "[ng-model='accountProfile.email']")
    WebElement emailUserFormInput;

    @FindBy(css = "[ng-model='accountProfile.password']")
    WebElement passwordUserFormInput;

    @FindBy(css = "[ng-model='accountProfile.phone']")
    WebElement phoneUserFormInput;

    @FindBy(css = "[ng-if$='showAlternateFields'] > a")
    WebElement alternateFieldsLink;

    @FindBy(css = ".submit-purchase-button")
    WebElement submitPurchaseButton;

    @FindBy(css = ".zoo-membership__lookup-result")
    List<WebElement> lookupResults;

    @FindBy(css = "[ng-click$='resetSearch()']")
    WebElement changeSearchLink;

    @FindBy(css = "[ng-click$='returnToResults()']")
    WebElement returnToResultsLink;

    @FindBy(css = "[ng-show='vm.showPurchaseSummary()']")
    WebElement purchaseSummary;

    @FindBy(css = ".zoo-membership__obscured-part input")
    List<WebElement> verifyMemberInputs;

    @FindBy(css = "button[ng-click$='verifyIdentity()']")
    WebElement submitVerificationButton;

    @FindBy(css = "[ng-if$='selectedMembership'] .error")
    WebElement userVerificationError;

    @FindBy(css = "button.purchase-step__continue")
    WebElement continuePurchaseButton;

    @FindBy(css = ".items-list tr:nth-of-type(1) .minus-select-plus select")
    WebElement ticketsAmountSelect;

    @FindBy(css = "a[ng-click='addNonMemberTickets()']")
    WebElement addNonMemberTicketsLink;

    @FindBy(css = ".items-list tr:nth-of-type(3) .minus-select-plus select")
    WebElement nonMembersTicketsAmountSelect;

    @FindBy(css = "button[ng-click='previousPurchaseStep()']")
    WebElement backArrowButton;

    @FindBy(css = "button[ng-click='purchaseStep.acceptTerms()']")
    WebElement acceptTermsButton;

    @FindBy(css = ".ticket-selection__dates__date.ng-scope")
    List<WebElement> ticketSelectionDates;

    @FindBy(css = ".ticket-selection__dates__right-arrow")
    WebElement ticketDatesPaginationRightLink;

    @FindBy(css = ".ticket-selection__dates__left-arrow")
    WebElement ticketDatesPaginationLeftLink;

    @FindBy(css = ".ticket-selection__months span")
    List<WebElement> availableMonths;

    @FindBy(css = ".ticket-selection__times-container select")
    WebElement ticketTimeSelect;

    @FindBy(css = ".ticket-selection__times-container .text-red")
    WebElement purchaseReminder;

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
        waitForVisibilityNotStrict(tooltip, 5);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Hovers a search result item by index.
     * @param index index of a search result item
     * @return @link MembersPopup} page
     */
    public MembersPopup hoverSearchResultItem(int index) {
        driver().switchTo().frame(contentIframe);

        if (index >= lookupResults.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Element index exceeds the number of found elements.");
        }

        WebElement searchResultItem = lookupResults.get(index);
        hover(searchResultItem, "Hovering search result item.");

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
     * Clears customer email input field
     * @return {@link MembersPopup} page
     */
    public MembersPopup clearCustomerEmail() {
        driver().switchTo().frame(contentIframe);

        customerEmailInput.clear();

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Sets text to customer last name input field.
     * @param customerLastName string with customer last name
     * @return  {@link MembersPopup} page
     */
    public MembersPopup inputCustomerLastName(String customerLastName) {
        inputText(customerLastNameInput, customerLastName);
        return this;
    }

    /**
     * Sets text to customer zip code input field.
     * @param customerZipCode string with customer zip code
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputCustomerZipCode(String customerZipCode) {
        inputText(customerZipCodeInput, customerZipCode);
        return this;
    }

    /**
     * Clears customer zip code input field
     * @return {@link MembersPopup} page
     */
    public MembersPopup clearCustomerZipCode() {
        driver().switchTo().frame(contentIframe);

        customerZipCodeInput.clear();

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Sets text into phone filter input field.
     * @param phoneNumer string which contains phone number
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputCustomerPhoneNumber(String phoneNumer) {
        inputText(phoneFilterInput, phoneNumer);
        return this;
    }

    /**
     * Submits phone number entered into the phone filter input field by pressing ENTER key
     * @return {@link MembersPopup} page
     */
    public MembersPopup submitPhoneNumber() {
        driver().switchTo().frame(contentIframe);

        phoneFilterInput.sendKeys(Keys.ENTER);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clears customer phone filter input field
     * @return {@link MembersPopup} page
     */
    public MembersPopup clearPhoneFilterInput() {
        driver().switchTo().frame(contentIframe);

        phoneFilterInput.clear();

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Sets text into the first name input field at the user information form
     * @param firstName string which contains first name
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputUserFormFirstName(String firstName) {
        inputText(firstNameUserFormInput, firstName);
        return this;
    }

    /**
     * Sets text into the last name input field at the user information form
     * @param lastName string which contains first name
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputUserFormLastName(String lastName) {
        inputText(lastNameUserFormInput, lastName);
        return this;
    }

    /**
     * Sets text into the email input field at the user information form
     * @param email string which contains email
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputUserFormEmail(String email) {
        inputText(emailUserFormInput, email);
        return this;
    }

    /**
     * Sets text into the password input field at the user information form
     * @param password string which contains password
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputUserFormPassword(String password) {
        inputText(passwordUserFormInput, password);
        return this;
    }

    /**
     * Sets text into the phone input field at the user information form
     * @param phone string which contains phone
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputUserFormPhone(String phone) {
        inputText(phoneUserFormInput, phone);
        return this;
    }

    /**
     * Sets text into verification input fields.
     * @param verificationCharacters array which contains verification characters
     * @return  {@link MembersPopup} page
     */
    public MembersPopup inputVerificationCharacters(String[] verificationCharacters) {
        driver().switchTo().frame(contentIframe);

        if (verificationCharacters.length != verifyMemberInputs.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Number of verification characters is incorrect.");
        }

        for (int i = 0; i < verifyMemberInputs.size(); i++) {
            verifyMemberInputs.get(i).sendKeys(verificationCharacters[i]);
        }

        driver().switchTo().defaultContent();
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
     * Selects zip code from dropdown filter.
     * @param zipCode string which contains zip code
     * @return {@link MembersPopup} page
     */
    public MembersPopup selectZipCodeFromFilter(String zipCode) {
        driver().switchTo().frame(contentIframe);

        new Select(zipCodeFilterSelect).selectByVisibleText(zipCode);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Deselects filter by zip code value.
     * @return {@link MembersPopup} page
     */
    public MembersPopup deselectZipCodeFilter() {
        driver().switchTo().frame(contentIframe);

        new Select(zipCodeFilterSelect).selectByValue("");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Selects amount of tickets from dropdown list.
     * @param amount string which contains desired amount of tickets
     * @return {@link MembersPopup} page
     */
    public MembersPopup selectTicketsAmount(String amount) {
        driver().switchTo().frame(contentIframe);

        new Select(ticketsAmountSelect).selectByVisibleText(amount);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Selects amount of non member tickets from dropdown list.
     * @param amount string which contains desired amount of tickets
     * @return {@link MembersPopup} page
     */
    public MembersPopup selectNonMemberTicketsAmount(String amount) {
        driver().switchTo().frame(contentIframe);

        new Select(nonMembersTicketsAmountSelect).selectByVisibleText(amount);

        driver().switchTo().defaultContent();
        return this;
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
     * Clicks change search link
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickChangeSearchLink() {
        driver().switchTo().frame(contentIframe);

        click(changeSearchLink, "Clicking change search link.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks return to results link
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickReturnToResultsLink() {
        driver().switchTo().frame(contentIframe);

        click(returnToResultsLink, "Clicking return to results link.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks ticket time select
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickTicketTimeSelect() {
        driver().switchTo().frame(contentIframe);

        click(ticketTimeSelect, "Clicking tickettime select.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks submit purchase button
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickSubmitPurchaseButton() {
        driver().switchTo().frame(contentIframe);

        click(submitPurchaseButton, "Clicking submit purchase button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks back arrow button
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickBackArrowButton() {
        driver().switchTo().frame(contentIframe);

        click(backArrowButton, "Clicking back arrow button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks accept terms button
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickAcceptTermsButton() {
        driver().switchTo().frame(contentIframe);

        click(acceptTermsButton, "Clicking accept terms button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks next tickets dates link
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickNextTicketsDatesLink() {
        driver().switchTo().frame(contentIframe);

        click(ticketDatesPaginationRightLink, "Clicking next tickets dates link.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
    }

    /**
     * Clicks previous(left) tickets dates link
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickPreviousTicketsDatesLink() {
        driver().switchTo().frame(contentIframe);

        click(ticketDatesPaginationLeftLink, "Clicking previous tickets dates link.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
    }

    /**
     * Clicks submit verification button
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickSubmitVerificationButton() {
        driver().switchTo().frame(contentIframe);

        click(submitVerificationButton, "Clicking submit verification button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks the month specified by it's index.
     * @param index index of a month
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickMonth(int index) {
        driver().switchTo().frame(contentIframe);

        if (index >= availableMonths.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Unable to click the month, months index is greater than the number of months.");
        }

        click(availableMonths.get(index), "Clicking a month by index.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks continue purchase button
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickContinuePurchaseButton() {
        driver().switchTo().frame(contentIframe);

        click(continuePurchaseButton, "Clicking continue purchase button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks 'Add non member tickets' link.
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickAddNonMemberTicketsLink() {
        driver().switchTo().frame(contentIframe);

        click(addNonMemberTicketsLink, "Clicking 'Add non member tickets' link.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks the specified lookup result item.
     * @param itemIndex index of the item to click
     * @return  {@link MembersPopup} page
     */
    public MembersPopup clickLookupResultItem(int itemIndex) {
        driver().switchTo().frame(contentIframe);

        if (itemIndex >= lookupResults.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Element index exceeds the number of found elements.");
        }
        click(lookupResults.get(itemIndex), "Clicking lookup result item #" + itemIndex);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks alternate fields link. Works for both:
     * switch to search by name and zip code; switch back to search by id and email.
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickAlternateFieldsLink() {
        driver().switchTo().frame(contentIframe);

        click(alternateFieldsLink, "Clicking alternate fields link.");

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
     * Retrieves last name validation error text in the top left corner of the last name input field
     * @return string which contains error text
     */
    public String getLastNameValidationErrorText() {
        driver().switchTo().frame(contentIframe);

        String result = lastNameValidationError.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves page title text.
     * @return string which contains page title text
     */
    public String getPageTitleText() {
        driver().switchTo().frame(contentIframe);

        String result = pageTitle.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves email validation error text in the top left corner of the email input field
     * at the user information form.
     * @return string which contains error text
     */
    public String getEmailUserFormValidationErrorText() {
        driver().switchTo().frame(contentIframe);

        String result = emailUserFormValidationError.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves password validation error text in the top left corner of the password input field
     * at the user information form.
     * @return string which contains error text
     */
    public String getPasswordUserFormValidationErrorText() {
        driver().switchTo().frame(contentIframe);

        String result = passwordUserFormValidationError.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves phone filter input text.
     * @return string which contains phone filter input text
     */
    public String getPhoneFilterInputText() {
        driver().switchTo().frame(contentIframe);

        String result = phoneFilterInput.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves phone input text from the user form.
     * @return string which contains phone input text
     */
    public String getPhoneUserForm() {
        driver().switchTo().frame(contentIframe);

        String result = phoneUserFormInput.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves currently selected amount of tickets
     * @return string which contains amount of tickets
     */
    public String getCurrentAmountOfTickets() {
        driver().switchTo().frame(contentIframe);

        String result = new Select(ticketsAmountSelect).getFirstSelectedOption().getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves currently selected amount of non member tickets
     * @return string which contains amount of tickets
     */
    public String getCurrentAmountOfNonMemberTickets() {
        driver().switchTo().frame(contentIframe);

        String result = new Select(nonMembersTicketsAmountSelect).getFirstSelectedOption().getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the text of the first option in dropdown
     * @return string which contains the text of the first option
     */
    public String getStartBookingTime() {
        driver().switchTo().frame(contentIframe);

        String result = new Select(ticketTimeSelect).getOptions().get(1).getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the text of the last option in dropdown
     * @return string which contains the text of the last option
     */
    public String getEndBookingTime() {
        driver().switchTo().frame(contentIframe);

        List<WebElement> options = new Select(ticketTimeSelect).getOptions();
        String result = options.get(options.size() - 1).getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the number of available months for purchasing tickets
     * @return number of available months
     */
    public int getNumberOfAvailableMonths() {
        driver().switchTo().frame(contentIframe);

        int result = availableMonths.size();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the text value of the specified month by index.
     * @param index index of the month
     * @return string which contains month text value
     */
    public String getMonthText(int index) {
        driver().switchTo().frame(contentIframe);

        if (index >= availableMonths.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Unable to get the month text value, months index is greater than the number of months.");
        }

        String result = availableMonths.get(index).getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the value of the maximum amount of tickets,
     * assuming that the maximum value is the last one in the dropdown list.
     * @return string which contains the maximum value from the dropdown
     */
    public String getMaximumAmountOfTicketsValue() {
        return getMaxAmountOfTickets(new Select(ticketsAmountSelect));
    }

    /**
     * Retrieves the value of the maximum amount of tickets,
     * assuming that the maximum value is the last one in the dropdown list.
     * @return string which contains the maximum value from the dropdown
     */
    public String getMaximumAmountOfNonMemberTicketsValue() {
        return getMaxAmountOfTickets(new Select(nonMembersTicketsAmountSelect));
    }

    /**
     * Retrieves the maximum value of the dropdown
     * @param dropdown {@link Select} dropdown object
     * @return string which contains the maximum value from the dropdown
     */
    private String getMaxAmountOfTickets(Select dropdown) {
        driver().switchTo().frame(contentIframe);

        int numberOfOptions = dropdown.getOptions().size();

        String result = dropdown.getOptions().get(numberOfOptions - 1).getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves date from the first displayed element
     * @return string which contains date
     */
    public String getFirstDisplayedBookingDate() {
        return getDisplayedBookingDate(0);
    }

    /**
     * Retrieves date from the last displayed element
     * @return string which contains date
     */
    public String getLastDisplayedBookingDate() {
        driver().switchTo().frame(contentIframe);

        int lastElementIndex = ticketSelectionDates.size() - 1;

        driver().switchTo().defaultContent();

        return getDisplayedBookingDate(lastElementIndex);
    }

    /**
     * Retrieves date from the specified displayed element
     * @param dateElemIndex index of the element to retrieve date from
     * @return string which contains date
     */
    private String getDisplayedBookingDate(int dateElemIndex) {
        driver().switchTo().frame(contentIframe);

        WebElement dateElem = ticketSelectionDates.get(dateElemIndex);
        String dayOfWeek = dateElem.findElement(By.cssSelector("strong:nth-of-type(1)")).getText();
        String month = dateElem.findElement(By.tagName("div")).getText();
        String dayOfMonth = dateElem.findElement(By.cssSelector("strong:nth-of-type(2)")).getText();

        String result = dayOfWeek + ", " + dayOfMonth + " " + month;

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves parts (span elements) of a lookup result item
     * @param index index of a lookup result item
     * @return list of web elements
     */
    public List<WebElement> getLookupResultItemParts(int index) {
        driver().switchTo().frame(contentIframe);

        if (index >= lookupResults.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Element index exceeds the number of found elements.");
        }

        List<WebElement> result = lookupResults.get(index).findElements(By.cssSelector("span"));

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the last name validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isLastNameValidationErrorMessageDisplayed() {
        return !getLastNameValidationErrorText().isEmpty();
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
     * @param text string which contains text to be visible
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isErrorMessageDisplayed(String text) {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementTextVisible(errorMessage,text, "Checking if the error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the user verification error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @param text string which contains text to be visible
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isUserVerificationErrorDisplayed(String text) {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementTextVisible(userVerificationError, text, "Checking if the user verification error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the tooltip is displayed.
     * @return {@code true} in case when the tooltip is displayed
     */
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

    /**
     * Checks whether the options for disguised information are returned
     * @return {@code true} in case when there's at least one option
     */
    public boolean isOptionsResultListDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = !lookupResults.isEmpty();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the customer last name input field is displayed
     * @return {@code true} in case when customer last name input field is displayed
     */
    public boolean isLastNameInputDisplayed() {
        driver().switchTo().frame(contentIframe);

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
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(customerZipCodeInput,
                "Checking whether the customer zip code input field is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the non member tickets select is displayed
     * @return {@code true} in case when customer zip code input field is displayed
     */
    public boolean isNonMemberTicketsSelectVisible() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(nonMembersTicketsAmountSelect,
                "Checking whether the non member tickets select is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the customer email input field is displayed
     * @return {@code true} in case when customer email input field is displayed
     */
    public boolean isEmailInputDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(customerEmailInput,
                "Checking whether the customer email input field is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether zip code filter is displayed
     * @return {@code true} in case when zip code filter is displayed
     */
    public boolean isZipCodeFilterDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(zipCodeFilterSelect,
                "Checking whether the zip code filter is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether phone filter is displayed
     * @return {@code true} in case when phone filter is displayed
     */
    public boolean isPhoneFilterDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(phoneFilterInput,
                "Checking whether the phone filter is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether user information form is displayed
     * @return {@code true} in case when user information form is displayed
     */
    public boolean isUserInformationFormDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(userInformationForm,
                "Checking whether the user information form is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the user form first name validation error is displayed (by error text),
     * because the element itself is always visible but has no text.
     * @param errorText string which contains error text
     * @return {@code true} in case when the user form first name validation error is displayed
     */
    public boolean isUserFormFirstNameErrorDisplayed(String errorText) {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementTextVisible(firstNameUserFormValidationError, errorText,
                "Checking whether the user form first name validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the user form last name validation error is displayed (by error text),
     * because the element itself is always visible but has no text.
     * @param errorText string which contains error text
     * @return {@code true} in case when the user form last name validation error is displayed
     */
    public boolean isUserFormLastNameErrorDisplayed(String errorText) {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementTextVisible(lastNameUserFormValidationError, errorText,
                "Checking whether the user form last name validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the user form email validation error is displayed (by error text),
     * because the element itself is always visible but has no text.
     * @param errorText string which contains error text
     * @return {@code true} in case when the user form email validation error is displayed
     */
    public boolean isUserFormEmailErrorDisplayed(String errorText) {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementTextVisible(emailUserFormValidationError, errorText,
                "Checking whether the user form email validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the user form password validation error is displayed (by error text),
     * because the element itself is always visible but has no text.
     * @param errorText string which contains error text
     * @return {@code true} in case when the user form password validation error is displayed
     */
    public boolean isUserFormPasswordErrorDisplayed(String errorText) {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementTextVisible(passwordUserFormValidationError, errorText,
                "Checking whether the user form password validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Validates the presence of the text in the element.
     * @param element an element which should contain the text
     * @param elementText string which contains the text which should be present
     * @param message string which contains a message for the log
     * @return {@code true} in case when the text is present in the element
     */
    private boolean isElementTextVisible(WebElement element, String elementText, String message) {
        Reporter.log(message);
        boolean elementVisible = false;
        try {
            driver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            elementVisible = new WebDriverWait(driver(), 5)
                    .until(ExpectedConditions.textToBePresentInElement(element, elementText));
        } catch (WebDriverException e) {
            Reporter.log(String.format("Element is not displayed after %d seconds.", 5));
        } finally {
            driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }
        return elementVisible;
    }

    /**
     * Checks whether the purchase summary is displayed.
     * @return {@code true} in case when the purchase summary is displayed
     */
    public boolean isPurchaseSummaryDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(purchaseSummary, "Checking whether the purchase summary page is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the purchase continue button enabled.
     * @return {@code true} in case when continue button is enabled
     */
    public boolean isPurchaseContinueButtonEnabled() {
        driver().switchTo().frame(contentIframe);

        Reporter.log("Checking whether the purchase continue button is enabled.");
        boolean result = continuePurchaseButton.isEnabled() && continuePurchaseButton.getAttribute("class").contains("button-orange");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the ticket time select is displayed.
     * @return {@code true} in case when the ticket time select is displayed
     */
    public boolean isTicketTimeSelectDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(ticketTimeSelect, "Checking whether the ticket time select is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the purchase reminder is displayed
     * @return {@code true} in case when the purchase reminder is displayed
     */
    public boolean isPurchaseReminderDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(purchaseReminder, "Checking whether the purchase reminder is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether there is at least one sold out date present at the current view.
     * @return {@code true} in case when there is at least one sold out date at the current view
     */
    public boolean isSoldOutDatePresent() {
        driver().switchTo().frame(contentIframe);

        boolean result = false;
        for (WebElement ticketSelectionDate: ticketSelectionDates) {
            try {
                ticketSelectionDate.findElement(By.cssSelector(".sold-out-label"));
                result = true;
                break;
            } catch (NoSuchElementException e) {
                // swallow
            }
        }

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Validates the look of the lookup result item.
     * @param softAssert {@link SoftAssert} assertion object
     * @param index index of the element
     * @param color expected color
     * @param validateBold {@code true} in case when we need to check if the text is bold
     */
    public void validateResultItemFont(SoftAssert softAssert, int index, String color, boolean validateBold) {
        List<WebElement> itemParts = getLookupResultItemParts(index);

        driver().switchTo().frame(contentIframe);
        itemParts.forEach(itemPart -> {
            String itemPartColor = itemPart.getCssValue("color");
            int weight = Integer.parseInt(itemPart.getCssValue("font-weight"));
            softAssert.assertEquals(itemPartColor, color, "Hovered result item part '" + itemPart.getText() + "' is not blue.");
            if (validateBold) {
                softAssert.assertTrue(weight >= 700, "Hovered result item part '" + itemPart.getText() + "' is not bold."
                        + "It's font-weight value is " + weight + ".");
            }
        });

        driver().switchTo().defaultContent();
    }

    /**
     * Validates the look of the selected month.
     * @param softAssert {@link SoftAssert} assertion object
     * @param color expected color
     * @param validateBold {@code true} in case when we need to check if the text is bold
     */
    public void validateMonthsFont(SoftAssert softAssert, String color, boolean validateBold) {
        driver().switchTo().frame(contentIframe);

        availableMonths.forEach(availableMonth -> {
            click(availableMonth, "Clicking month " + availableMonth.getText() + ".");
            String monthColor = availableMonth.getCssValue("color");
            int weight = Integer.parseInt(availableMonth.getCssValue("font-weight"));
            softAssert.assertEquals(monthColor, color, "Month " + availableMonth.getText() + " has incorrect color.");
            if (validateBold) {
                softAssert.assertTrue(weight >= 700, "Month '" + availableMonth.getText() + "' is not bold."
                        + "It's font-weight value is " + weight + ".");
            }
        });

        driver().switchTo().defaultContent();
    }

    /**
     * Selects first available date from the list.
     * Throws skip exception in case when there are no available dates.
     * @return {@link MembersPopup} page
     */
    public MembersPopup selectFirstAvailableDate() {
        String startDate = getFirstDisplayedBookingDate();
        String newFirstDate = getFirstDisplayedBookingDate();
        driver().switchTo().frame(contentIframe);

        boolean found = false;
        do {
            for (WebElement ticketSelectionDate : ticketSelectionDates) {
                try {
                    ticketSelectionDate.findElement(By.cssSelector(".sold-out-label"));
                } catch (NoSuchElementException e) {
                    click(ticketSelectionDate, "Clicking ticket selection date.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                driver().switchTo().defaultContent();
                clickNextTicketsDatesLink();
                newFirstDate = getFirstDisplayedBookingDate();
                driver().switchTo().frame(contentIframe);
            }
        } while (!found && !startDate.equals(newFirstDate));

        if (!found) {
            driver().switchTo().defaultContent();
            throw new SkipException("There are no available dates.");
        }

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Selects time from the dropdown by text
     * @param time string which contains time to select
     * @return {@link MembersPopup} page
     */
    public MembersPopup selectTimeForTheTicket(String time) {
        driver().switchTo().frame(contentIframe);

        new Select(ticketTimeSelect).selectByVisibleText(time);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Validates time interval among each time value in the 'Book your time' dropdown
     * @param softAssert {@link SoftAssert} assertion object
     * @param interval interval in minutes
     */
    public void validateBookingTimeInterval(SoftAssert softAssert, int interval) {
        driver().switchTo().frame(contentIframe);

        List<WebElement> options = new Select(ticketTimeSelect).getOptions();
        options.remove(0);
        for (int index = 0; index < options.size() - 1; index++) {
            String option1 = options.get(index).getText();
            String option2 = options.get(index + 1).getText();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mmaa").withLocale(Locale.ENGLISH);
            DateTime option1time = formatter.parseDateTime(option1);
            DateTime option2time = formatter.parseDateTime(option2);
            softAssert.assertEquals(new Duration(option1time, option2time).getStandardMinutes(), interval,
                    "Incorrect interval between " + option1 + " and " + option2 + ".");
        }

        driver().switchTo().defaultContent();
    }
}
