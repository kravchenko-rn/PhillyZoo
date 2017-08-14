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
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FindBy(css = "#mainContainer>section")
public class MembersPopup extends BaseFEPage {
    @FindBy(id = "pivvitContent")
    WebElement contentIframe;

    @FindBy(css = "#facebook-btn iframe")
    WebElement fbButtonIFrame;

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

    @FindBy(css = ".price-breakdown .purchase__remove-item")
    List<WebElement> removeTicketButtons;

    @FindBy(css = ".price-breakdown td:nth-of-type(1) span")
    List<WebElement> ticketQuantities;

    @FindBy(css = ".total>td:nth-of-type(2)")
    WebElement totalPrice;

    @FindBy(css = ".purchase__item td:nth-of-type(1)")
    List<WebElement> ticketDescriptions;

    @FindBy(css = ".purchase-register [ng-model='paymentMethod.name']")
    WebElement paymentSystemInput;

    @FindBy(css = ".purchase-register [ng-model='paymentMethod.cardNumber']")
    WebElement cardNumberInput;

    @FindBy(css = ".purchase-register [ng-model='paymentMethod.expirationMonth']")
    WebElement cardExpirationMonth;

    @FindBy(css = ".purchase-register [ng-model='paymentMethod.expirationYear']")
    WebElement cardExpirationYear;

    @FindBy(css = ".purchase-register [ng-model='paymentMethod.postalCode']")
    WebElement cardPostalCode;

    @FindBy(css = ".purchase-register [ng-model='paymentMethod.securityCode']")
    WebElement cardCvvInput;

    @FindBy(css = ".purchase-register [ng-model='accountProfile.email']")
    WebElement accountEmail;

    @FindBy(css = "[ng-if='paymentMethod.errors.error']")
    WebElement paymentMethodError;

    @FindBy(css = "[ng-click='vm.enterCode()']")
    WebElement applyCodeLink;

    @FindBy(css = "[ng-model='vm.discount.code']")
    WebElement discountInput;

    @FindBy(css = ".enter-discount__apply > button")
    WebElement applyDiscountButton;

    @FindBy(css = ".error>small")
    WebElement discountError;

    @FindBy(css = "[ng-if='winterTicketsTimer']")
    WebElement ticketsTimerMessage;

    @FindBy(css = "a[ng-click^='goBack']")
    WebElement editPurchaseLink;

    @FindBy(css = ".purchase-register small > a")
    WebElement loginLink;

    @FindBy(css = "a[ng-click^='UI.hide']")
    WebElement createNewAccountLink;

    @FindBy(css = ".purchase-register")
    WebElement newAccountForm;

    @FindBy(css = "table._51mz tr:nth-of-type(1)")
    WebElement facebookLoginButton;

    private FacebookPage facebookPage;

    @FindBy(css = "#loginForm [ng-model='login.email']")
    WebElement loginEmailInput;

    @FindBy(css = "#loginForm [ng-model='login.password']")
    WebElement loginPasswordInput;

    @FindBy(css = "#loginForm button")
    WebElement continueLoginButton;

    @FindBy(css = "#loginForm a")
    WebElement forgotLink;

    @FindBy(css = "#forgotPasswordContainer input[ng-model='account.email']")
    WebElement forgotEmailInput;

    @FindBy(css = "#forgotPasswordContainer input[type='submit']")
    WebElement submitForgotEmailButton;

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
        return new MembersPopup();
    }

    /**
     * Waits till checkout page is loaded.
     * @return {@link MembersPopup} page
     */
    public MembersPopup waitForCheckoutLoad() {
        driver().switchTo().frame(contentIframe);

        waitForVisibility(checkoutTitle, "Waiting for checkout page to load.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
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
     * Sets text into forgot email input
     * @param email string which contains email
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputForgotEmail(String email) {
        inputText(forgotEmailInput, email);
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
     * @param phoneNumber string which contains phone number
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputCustomerPhoneNumber(String phoneNumber) {
        inputText(phoneFilterInput, phoneNumber);
        return this;
    }

    /**
     * Sets text into payment system input.
     * @param paymentSystem string which contains payment system
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputPaymentSystem(String paymentSystem) {
        inputText(paymentSystemInput, paymentSystem);
        return this;
    }

    /**
     * Sets text into card number input field.
     * @param cardNumber string which contains card number
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputCardNumber(String cardNumber) {
        inputText(cardNumberInput, cardNumber);
        return this;
    }

    /**
     * Sets text into discount input field.
     * @param discount string which contains discount
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputDiscount(String discount) {
        inputText(discountInput, discount);
        return this;
    }

    /**
     * Sets text into card cvv input.
     * @param cvv string which contains cvv
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputCardCvv(String cvv) {
        inputText(cardCvvInput, cvv);
        return this;
    }

    /**
     * Sets text into account profile email input.
     * @param email string which contains account profile email
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputAccountProfileEmail(String email) {
        inputText(accountEmail, email);
        return this;
    }

    /**
     * Sets text into login email input
     * @param email string which contains email
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputLoginEmail(String email) {
        inputText(loginEmailInput, email);
        return this;
    }

    /**
     * Sets text into login password input
     * @param password string which contains password
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputLoginPassword(String password) {
        inputText(loginPasswordInput, password);
        return this;
    }

    /**
     * Sets text into card postal code input.
     * @param postalCode string which contains postal code
     * @return {@link MembersPopup} page
     */
    public MembersPopup inputCardPostalCode(String postalCode) {
        inputText(cardPostalCode, postalCode);
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
     * Selects first value from card expiration month dropdown.
     * @return {@link MembersPopup} page
     */
    public MembersPopup selectCardExpirationMonthLastValue() {
        selectDropDownLastValue(cardExpirationMonth);
        return this;
    }

    /**
     * Selects first value from card expiration year dropdown.
     * @return {@link MembersPopup} page
     */
    public MembersPopup selectCardExpirationYearLastValue() {
        selectDropDownLastValue(cardExpirationYear);
        return this;
    }

    private void selectDropDownLastValue(WebElement dropdown) {
        driver().switchTo().frame(contentIframe);

        Select select = new Select(dropdown);
        String numberOfValues = Integer.toString(select.getOptions().size() - 2);
        select.selectByValue(numberOfValues);

        driver().switchTo().defaultContent();
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
     * Clicks facebook login button
     * @return {@link MembersPopup} page
     */
    public FacebookPage clickFacebookLoginButton() {
        driver().switchTo().frame(contentIframe);
        driver().switchTo().frame(fbButtonIFrame);

        click(facebookLoginButton, "Clicking facebook login button.");

        driver().switchTo().defaultContent();
        driver().switchTo().defaultContent();
        return new FacebookPage();
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
     * Clicks edit purchase link
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickEditPurchaseLink() {
        driver().switchTo().frame(contentIframe);

        click(editPurchaseLink, "Clicking edit purchase link.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
    }

    /**
     * Clicks login link
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickLoginLink() {
        driver().switchTo().frame(contentIframe);

        click(loginLink, "Clicking login link.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
    }

    /**
     * Clicks create new account link
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickCreateNewAccountLink() {
        driver().switchTo().frame(contentIframe);

        click(createNewAccountLink, "Clicking create new account link.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
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
        return new MembersPopup();
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
     * Clicks remove ticket button of the first ticket in the list.
     * @return {@link MembersPopup} page
     */
    public MembersPopup removeFirstTicket() {
        driver().switchTo().frame(contentIframe);

        if (removeTicketButtons.size() == 0) {
            throw new SkipException("There are no remove ticket buttons.");
        }

        click(removeTicketButtons.get(0), "Clicking remove ticket button of the first ticket.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
    }

    /**
     * Clicks remove ticket button of the second ticket in the list.
     * @return {@link MembersPopup} page
     */
    public MembersPopup removeSecondTicket() {
        driver().switchTo().frame(contentIframe);

        if (removeTicketButtons.size() == 0) {
            throw new SkipException("There are no remove ticket buttons.");
        }

        click(removeTicketButtons.get(1), "Clicking remove ticket button of the second ticket.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
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
     * Clicks apply code link
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickApplyCodeLink() {
        driver().switchTo().frame(contentIframe);

        click(applyCodeLink, "Clicking apply code link.");

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
        return new MembersPopup();
    }

    /**
     * Clicks continue login button
     * @return {@link MembersPopup} page
     */
    public MembersPopup clickContinueLoginButton() {
        driver().switchTo().frame(contentIframe);

        click(continueLoginButton, "Clicking continue login button.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
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
     * Clicks apply discount button
     * @return  {@link MembersPopup} page
     */
    public MembersPopup clickApplyDiscountButton() {
        driver().switchTo().frame(contentIframe);

        click(applyDiscountButton, "Clicking apply discount button.");

        driver().switchTo().defaultContent();
        return new MembersPopup();
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
     * Retrieves number of ticket items
     * @return number of ticket items
     */
    public int getNumberOfTicketItems() {
        driver().switchTo().frame(contentIframe);

        int result = driver().findElements(By.cssSelector(".purchase__item")).size();

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
     * Retrieves the quantity of the first ticket element.
     * @return string which contains ticket quantity
     */
    public String getFirstTicketQuantity() {
        driver().switchTo().frame(contentIframe);

        if (ticketQuantities.size() == 0) {
            throw new SkipException("There are no ticket quantities. Maybe there is only one ticket selected.");
        }
        String result = ticketQuantities.get(0).getText();

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
     * Checks whether the card payment method validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isCardPaymentMethodValidationErrorDisplayed() {
        driver().switchTo().frame(contentIframe);

        WebElement validationError = paymentSystemInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card payment system validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the facebook login button is displayed.
     * @return {@code true} in case when facebook login button is displayed
     */
    public boolean isFacebookLoginButtonDisplayed() {
        driver().switchTo().frame(contentIframe);
        driver().switchTo().frame(fbButtonIFrame);

        boolean result = isElementVisible(facebookLoginButton, "Checking whether the facebook login button displayed.");

        driver().switchTo().defaultContent();
        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the card expiration month validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isCardExpirationMonthValidationErrorDisplayed() {
        driver().switchTo().frame(contentIframe);

        WebElement validationError = cardExpirationMonth.findElement(By.xpath("parent::*/following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card expiration month validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the card expiration year validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isCardExpirationYearValidationErrorDisplayed() {
        driver().switchTo().frame(contentIframe);

        WebElement validationError = cardExpirationYear.findElement(By.xpath("parent::*/following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card expiration year validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the card cvv validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isCardCvvValidationErrorDisplayed() {
        driver().switchTo().frame(contentIframe);

        WebElement validationError = cardCvvInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card cvv validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the login email validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isInvalidLoginEmailErrorDisplayed(String errorText) {
        driver().switchTo().frame(contentIframe);

        WebElement validationError = loginEmailInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, errorText,
                "Checking whether the login email validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the login password validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isInvalidLoginPasswordErrorDisplayed(String errorText) {
        driver().switchTo().frame(contentIframe);

        WebElement validationError = loginPasswordInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, errorText,
                "Checking whether the login password validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the facebook login page is displayed.
     * @return {@code true} in case when the facebook login page is displayed
     */
    public boolean isFacebookLoginPageDisplayed() {
        String currentWindowHandle = driver().getWindowHandle();
        Set<String> windowHandles = driver().getWindowHandles();
        String facebookWindowHandle = null;
        for (String handle: windowHandles) {
            facebookWindowHandle = handle;
        }
        driver().switchTo().window(facebookWindowHandle);
        boolean result = facebookPage.isLoginFormDisplayed();
        driver().switchTo().window(currentWindowHandle);
        return result;
    }

    /**
     * Checks whether the card postal code validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isCardPostalCodeValidationErrorDisplayed() {
        driver().switchTo().frame(contentIframe);

        WebElement validationError = cardPostalCode.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card postal code validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the card number validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isCardNumberValidationErrorDisplayed() {
        driver().switchTo().frame(contentIframe);

        WebElement validationError = cardNumberInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
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
     * Retrieves total price text
     * @return string which contains total price text
     */
    public String getTotalPrice() {
        driver().switchTo().frame(contentIframe);

        String result = totalPrice.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves ticket hold message.
     * @return string which contains ticket hold message
     */
    public String getTicketHoldMessageText() {
        driver().switchTo().frame(contentIframe);

        String result = ticketsTimerMessage.getText();

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
     * Checks whether the login link displayed
     * @return {@code true} in case when login link is displayed
     */
    public boolean isLoginLinkDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(loginLink,
                "Checking whether the login link is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the create new account link displayed
     * @return {@code true} in case when create new account link is displayed
     */
    public boolean isCreateNewAccountLinkDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(createNewAccountLink,
                "Checking whether the 'create new account' link is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the new account form is displayed
     * @return {@code true} in case when the new account form is displayed
     */
    public boolean isNewAccountFormDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(newAccountForm,
                "Checking whether the 'create new account' form is displayed.");

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
     * Checks if the tickets timer message is displayed.
     * @return {@code true} in case when the tickets timer message is displayed
     */
    public boolean isTicketsTimerDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(ticketsTimerMessage, "Checking whether the tickets timer message is displayed.");

        driver().switchTo().defaultContent();
        return result;
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
     * Checks whether the checkout title is displayed.
     * @return {@code true} in case when the checkout title is displayed
     */
    public boolean isCheckoutTitleDisplayed() {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementVisible(checkoutTitle, "Checking whether the checkout title is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the ticket quantity of the first element is displayed.
     * @return {@code true} in case when the ticket quantity is displayed
     */
    public boolean isFirstTicketQuantityDisplayed() {
        driver().switchTo().frame(contentIframe);

        if (ticketQuantities.size() == 0) {
            throw new SkipException("There are no ticket quantities. Maybe there is only one ticket selected.");
        }
        boolean result = isElementVisible(ticketQuantities.get(0), "Checking whether the ticket quantity is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the payment method error is displayed  (by error text),
     * because the element itself is always visible but has no text.
     * @param errorText text of the error
     * @return {@code true} in case when payment method error is displayed
     */
    public boolean isPaymentMethodErrorDisplayed(String errorText) {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementTextVisible(paymentMethodError, errorText,
                "Checking whether the payment method error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the discount error is displayed  (by error text),
     * because the element itself is always visible but has no text.
     * @param errorText text of the error
     * @return {@code true} in case when discount error is displayed
     */
    public boolean isDiscountErrorDisplayed(String errorText) {
        driver().switchTo().frame(contentIframe);

        boolean result = isElementTextVisible(discountError, errorText,
                "Checking whether the discount error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the non member ticket is present.
     * @return {@code true} in case when non member ticket is present
     */
    public boolean isNonMemberTicketPresent() {
        driver().switchTo().frame(contentIframe);

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
     * Validates that ticket hold time is five minutes.
     * @param softAssert {@link SoftAssert} assertion object
     */
    public void validateTicketHoldTimeFiveMin(SoftAssert softAssert) {
        String holdMessage = getTicketHoldMessageText();
        Pattern timePattern = Pattern.compile("\\d{1,2}:\\d{2}");
        Matcher matcher = timePattern.matcher(holdMessage);
        if (matcher.find()) {
            String countdownValue = String.copyValueOf(holdMessage.toCharArray(), matcher.start(), matcher.end() - matcher.start());
            DateTimeFormatter dtf = DateTimeFormat.forPattern("mm:ss");
            DateTime time = dtf.parseDateTime(countdownValue);
            DateTime zeroMinutes = dtf.parseDateTime("0:00");
            Duration duration = new Duration(zeroMinutes, time);
            softAssert.assertEquals(duration.getStandardMinutes(), 5, "Ticket hold time is incorrect.");
        } else {
            softAssert.fail("There is no time displayed in the message.");
        }
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
