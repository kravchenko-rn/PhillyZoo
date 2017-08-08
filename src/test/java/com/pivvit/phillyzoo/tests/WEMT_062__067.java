package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

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

    @Test(testName = "WEMT-062",
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
}
