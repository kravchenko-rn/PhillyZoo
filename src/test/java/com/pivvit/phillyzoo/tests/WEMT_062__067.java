package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

public class WEMT_062__067 extends BaseTest {
    @BeforeTest
    @Parameters({"email", "verificationCharacters"})
    @SuppressWarnings("all")
    public void init(String customerEmail, String charactersSet) {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
        new PurchaseTicketsPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        new PastMembershipPopup()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "));
        new WinterExperienceTicketsPopup()
                .selectMemberTicketsAmount("1")
                .clickContinueButton();
        WinterExperienceTickets2Popup wet2Popup = new TermsAndConditionsPopup()
                .clickAcceptTermsButton()
                .selectFirstAvailableDate();
        String time = wet2Popup.getStartBookingTime();
        wet2Popup.selectTimeForTheTicket(time)
                .clickContinueButton()
                .waitTillLoadingIndicatorDisappears();
    }

    @Test(testName = "WEMT-065",
            description = "Verify that experience ticket on held 5 minutes")
    public void checkTicketsHoldTime() {
        SoftAssert softAssert = new SoftAssert();
        CheckoutPopup checkoutPopup = new CheckoutPopup();
        Assert.assertTrue(checkoutPopup.isTicketsTimerDisplayed(), "Ticket hold message is not displayed.");
        checkoutPopup.validateTicketHoldTimeFiveMin(softAssert);
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-067", dependsOnMethods = "checkTicketsHoldTime", alwaysRun = true,
            description = "Verify that 'Edit purchase' link return user to 'Winter' Experience Tickets pop-up")
    @Parameters("pageTitle")
    public void checkEditPurchaseLink(String pageTitle) {
        new CheckoutPopup()
                .clickEditPurchaseLink();
        Assert.assertEquals(new BasePopup().getPageTitleText(), pageTitle,
                "Was not redirected to 'Winter' Experience Tickets pop-up.");
    }

    @Test(testName = "WEMT-064", dependsOnMethods = "checkEditPurchaseLink", alwaysRun = true,
            description = "Verify that error textBox validation appear on the payment information textBoxes when clicking on Purchase button with blank payment information")
    public void checkBlankFieldsValidationErrors() {
        SoftAssert softAssert = new SoftAssert();

        CheckoutPopup checkoutPopup = new WinterExperienceTickets2Popup()
                .clickContinueButton()
                .waitTillLoadingIndicatorDisappears()
                .waitForLoad()
                .clickPurchaseButton();
        softAssert.assertTrue(checkoutPopup.isCardNumberValidationErrorDisplayed(),
                "Card number validation error is not displayed.");
        softAssert.assertTrue(checkoutPopup.isCardPaymentMethodValidationErrorDisplayed(),
                "Card payment method validation error is not displayed.");
        softAssert.assertTrue(checkoutPopup.isCardExpirationMonthValidationErrorDisplayed(),
                "Card expiration month validation error is not displayed.");
        softAssert.assertTrue(checkoutPopup.isCardExpirationYearValidationErrorDisplayed(),
                "Card expiration year validation error is not displayed.");
        softAssert.assertTrue(checkoutPopup.isCardCvvValidationErrorDisplayed(),
                "Card cvv validation error is not displayed.");
        softAssert.assertTrue(checkoutPopup.isCardPostalCodeValidationErrorDisplayed(),
                "Card postal code validation error is not displayed.");

        softAssert.assertAll();
    }

    @Test(testName = "WEMT-066", dependsOnMethods = "checkBlankFieldsValidationErrors", alwaysRun = true,
            description = "Verify that user returns to 'Winter' Experience Tickets pop-up when held time is finished")
    @Parameters("pageTitle")
    public void checkRedirectOnHoldTimeFinish(String pageTitle) {
        CheckoutPopup checkoutPopup = new CheckoutPopup();
        checkoutPopup.clickEditPurchaseLink();//checkoutPopup.sleep(300);
        Assert.assertEquals(new BasePopup().getPageTitleText(), pageTitle,
                "Was not redirected to 'Winter' Experience Tickets pop-up when hold time was finished.");
    }

    @Test(testName = "WEMT-062", dependsOnMethods = "checkRedirectOnHoldTimeFinish", alwaysRun = true,
            description = "Verify that error textBox validation appear on the payment information textBoxes when entering invalid payment details")
    @Parameters({"paymentSystem", "invalidCardNumber", "invalidCardCvv", "invalidPostalCode", "errorText", "invalidEmail"})
    public void checkInvalidCardData(String paymentSystem, String cardNumber, String cardCvv,
                                     String postalCode, String errorText, String email) {
        WinterExperienceTickets2Popup wet2Popup = new WinterExperienceTickets2Popup();
        String time = wet2Popup.getStartBookingTime();
        CheckoutPopup checkoutPopup = wet2Popup.selectTimeForTheTicket(time)
                .clickContinueButton()
                .waitTillLoadingIndicatorDisappears()
                .waitForLoad()
                .inputPaymentSystem(paymentSystem)
                .inputCardNumber(cardNumber)
                .selectCardExpirationMonthLastValue()
                .selectCardExpirationYearLastValue()
                .inputCardCvv(cardCvv)
                .inputCardPostalCode(postalCode)
                .inputAccountProfileEmail(email)
                .clickPurchaseButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(checkoutPopup.isPaymentMethodErrorDisplayed(errorText),
                "Validation error is not displayed.");
    }

    @Test(testName = "WEMT-063", dependsOnMethods = "checkInvalidCardData", alwaysRun = true,
            description = "Verify that error message appears when entering an invalid coupon code")
    @Parameters({"invalidDiscount", "discountError"})
    public void checkInvalidCouponError(String discount, String discountErrorText) {
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .clickApplyCodeLink()
                .inputDiscount(discount)
                .clickApplyDiscountButton();
        Assert.assertTrue(checkoutPopup.isDiscountErrorDisplayed(discountErrorText),
                "Discount error is not displayed.");
    }
}
