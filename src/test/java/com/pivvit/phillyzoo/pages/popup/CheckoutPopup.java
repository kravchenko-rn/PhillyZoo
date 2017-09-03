package com.pivvit.phillyzoo.pages.popup;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import pivvit.utils.SoftAssert;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @FindBy(css = "[ng-if='winterTicketsTimer']")
    WebElement ticketsTimerMessage;

    @FindBy(css = "a[ng-click^='goBack']")
    WebElement editPurchaseLink;

    @FindBy(css = ".submit-purchase-button")
    WebElement purchaseButton;

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

    @FindBy(css = "[ng-model='vm.discount.code']")
    WebElement discountInput;

    @FindBy(css = "[ng-if='paymentMethod.errors.error']")
    WebElement paymentMethodError;

    @FindBy(css = ".enter-discount__apply > button")
    WebElement applyDiscountButton;

    @FindBy(css = ".error>small")
    WebElement discountError;

    @FindBy(css = "[ng-click='vm.enterCode()']")
    WebElement applyCodeLink;

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

    public CheckoutPopup waitTillLoadingIndicatorDisappears() {
        super.waitTillLoadingIndicatorDisappears();
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
     * Retrieves ticket hold message.
     * @return string which contains ticket hold message
     */
    public String getTicketHoldMessageText() {
        driver().switchTo().frame(contentIFrame);

        String result = ticketsTimerMessage.getText();

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
     * Sets text into payment system input.
     * @param paymentSystem string which contains payment system
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup inputPaymentSystem(String paymentSystem) {
        inputText(paymentSystemInput, paymentSystem);
        return this;
    }

    /**
     * Sets text into card number input field.
     * @param cardNumber string which contains card number
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup inputCardNumber(String cardNumber) {
        inputText(cardNumberInput, cardNumber);
        return this;
    }

    /**
     * Sets text into card cvv input.
     * @param cvv string which contains cvv
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup inputCardCvv(String cvv) {
        inputText(cardCvvInput, cvv);
        return this;
    }

    /**
     * Sets text into card postal code input.
     * @param postalCode string which contains postal code
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup inputCardPostalCode(String postalCode) {
        inputText(cardPostalCode, postalCode);
        return this;
    }

    /**
     * Sets text into account profile email input.
     * @param email string which contains account profile email
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup inputAccountProfileEmail(String email) {
        inputText(accountEmail, email);
        return this;
    }

    /**
     * Sets text into discount input field.
     * @param discount string which contains discount
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup inputDiscount(String discount) {
        inputText(discountInput, discount);
        return this;
    }

    /**
     * Selects first value from card expiration month dropdown.
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup selectCardExpirationMonthLastValue() {
        selectDropDownLastValue(cardExpirationMonth);
        return this;
    }

    private void selectDropDownLastValue(WebElement dropdown) {
        driver().switchTo().frame(contentIFrame);

        Select select = new Select(dropdown);
        String numberOfValues = Integer.toString(select.getOptions().size() - 2);
        select.selectByValue(numberOfValues);

        driver().switchTo().defaultContent();
    }

    /**
     * Selects first value from card expiration year dropdown.
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup selectCardExpirationYearLastValue() {
        selectDropDownLastValue(cardExpirationYear);
        return this;
    }

    /**
     * Clicks edit purchase link
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup clickEditPurchaseLink() {
        driver().switchTo().frame(contentIFrame);

        click(editPurchaseLink, "Clicking edit purchase link.");

        driver().switchTo().defaultContent();
        return new CheckoutPopup();
    }

    /**
     * Clicks submit purchase button
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup clickPurchaseButton() {
        driver().switchTo().frame(contentIFrame);

        click(purchaseButton, "Clicking submit purchase button.");

        driver().switchTo().defaultContent();
        return new CheckoutPopup();
    }

    /**
     * Clicks apply code link
     * @return {@link CheckoutPopup} page
     */
    public CheckoutPopup clickApplyCodeLink() {
        driver().switchTo().frame(contentIFrame);

        click(applyCodeLink, "Clicking apply code link.");

        driver().switchTo().defaultContent();
        return new CheckoutPopup();
    }

    /**
     * Clicks apply discount button
     * @return  {@link CheckoutPopup} page
     */
    public CheckoutPopup clickApplyDiscountButton() {
        driver().switchTo().frame(contentIFrame);

        click(applyDiscountButton, "Clicking apply discount button.");

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

    /**
     * Checks if the tickets timer message is displayed.
     * @return {@code true} in case when the tickets timer message is displayed
     */
    public boolean isTicketsTimerDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(ticketsTimerMessage, "Checking whether the tickets timer message is displayed.");

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
        driver().switchTo().frame(contentIFrame);

        WebElement validationError = cardNumberInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the card payment method validation error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isCardPaymentMethodValidationErrorDisplayed() {
        driver().switchTo().frame(contentIFrame);

        WebElement validationError = paymentSystemInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card payment system validation error is displayed.");

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
        driver().switchTo().frame(contentIFrame);

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
        driver().switchTo().frame(contentIFrame);

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
        driver().switchTo().frame(contentIFrame);

        WebElement validationError = cardCvvInput.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card cvv validation error is displayed.");

        driver().switchTo().defaultContent();
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
        driver().switchTo().frame(contentIFrame);

        WebElement validationError = cardPostalCode.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, "required", "Checking whether the card postal code validation error is displayed.");

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
        driver().switchTo().frame(contentIFrame);

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
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementTextVisible(discountError, errorText,
                "Checking whether the discount error is displayed.");

        driver().switchTo().defaultContent();
        return result;
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
}
