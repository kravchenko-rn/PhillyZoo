package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

@SuppressWarnings("all")
public class WEMT_062__067 extends BaseTest {
    @BeforeTest
    @Parameters({"email", "verificationCharacters"})
    public void init(String customerEmail, String charactersSet) {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "))
                .selectTicketsAmount("1")
                .clickContinuePurchaseButton()
                .clickAcceptTermsButton()
                .selectFirstAvailableDate();
        String time = membersPopup.getStartBookingTime();
        membersPopup.selectTimeForTheTicket(time)
                .clickContinuePurchaseButton()
                .waitTillLoadingIndicatorDisappears()
                .waitForCheckoutLoad();
    }

    @Test(testName = "WEMT-065",
            description = "Verify that experience ticket on held 5 minutes")
    public void checkTicketsHoldTime() {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup();
        Assert.assertTrue(membersPopup.isTicketsTimerDisplayed(), "Ticket hold message is not displayed.");
        membersPopup.validateTicketHoldTimeFiveMin(softAssert);
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-064", dependsOnMethods = "checkTicketsHoldTime", alwaysRun = true,
            description = "Verify that error textbox validation appear on the payment information textboxes when clicking on Purchase button with blank payment information")
    public void checkBlankFieldsValidationErrors() {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup()
                .clickSubmitPurchaseButton();
        softAssert.assertTrue(membersPopup.isCardNumberValidationErrorDisplayed(),
                "Card number validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isCardPaymentMethodValidationErrorDisplayed(),
                "Card payment method validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isCardExpirationMonthValidationErrorDisplayed(),
                "Card expiration month validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isCardExpirationYearValidationErrorDisplayed(),
                "Card expiration year validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isCardCvvValidationErrorDisplayed(),
                "Card cvv validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isCardPostalCodeValidationErrorDisplayed(),
                "Card postal code validation error is not displayed.");

        softAssert.assertAll();
    }

    @Test(testName = "WEMT-066", dependsOnMethods = "checkBlankFieldsValidationErrors", alwaysRun = true,
            description = "Verify that user returns to 'Winter' Experience Tickets pop-up when held time is finished")
    @Parameters("pageTitle")
    public void checkRedirectOnHoldTimeFinish(String pageTitle) {
        MembersPopup membersPopup = new MembersPopup();
        membersPopup.sleep(300);
        Assert.assertEquals(membersPopup.getPageTitleText(), pageTitle,
                "Was not redirected to 'Winter' Experience Tickets pop-up when hold time was finished.");
    }

    @Test(testName = "WEMT-062", dependsOnMethods = "checkBlankFieldsValidationErrors", alwaysRun = true,
            description = "Verify that error textbox validation appear on the payment information textboxes when entering invalid payment details")
    @Parameters({"paymentSystem", "invalidCardNumber", "invalidCardCvv", "invalidPostalCode", "errorText", "invalidEmail"})
    public void checkInvalidCardData(String paymentSystem, String cardNumber, String cardCvv,
                                     String postalCode, String errorText, String email) {
        MembersPopup membersPopup = new MembersPopup()
                .inputPaymentSystem(paymentSystem)
                .inputCardNumber(cardNumber)
                .selectCardExpirationMonthLastValue()
                .selectCardExpirationYearLastValue()
                .inputCardCvv(cardCvv)
                .inputCardPostalCode(postalCode)
                .inputAccountProfileEmail(email)
                .clickSubmitPurchaseButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(membersPopup.isPaymentMethodErrorDisplayed(errorText),
                "Validation error is not displayed.");
    }

    @Test(testName = "WEMT-063", dependsOnMethods = "checkInvalidCardData", alwaysRun = true,
            description = "Verify that error message appear when entering an invalid coupon code")
    @Parameters({"invalidDiscount", "discountError"})
    public void checkInvalidCouponError(String discount, String discountErrorText) {
        MembersPopup membersPopup = new MembersPopup()
                .clickApplyCodeLink()
                .inputDiscount(discount)
                .clickApplyDiscountButton();
        Assert.assertTrue(membersPopup.isDiscountErrorDisplayed(discountErrorText),
                "Discount error is not displayed.");
    }
}
