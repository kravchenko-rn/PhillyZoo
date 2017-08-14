package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;


public class WEMT_075__079 extends BaseTest {
    @BeforeTest
    @Parameters({"email", "verificationCharacters"})
    @SuppressWarnings("all")
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

    @Test(testName = "WEMT-075",
            description = "Verify that error text validation appear on the email and password text boxes when entering an invalid email")
    @Parameters({"invalidEmail", "password", "invalidLoginError"})
    public void checkInvalidEmail(String email, String password, String error) {
        MembersPopup membersPopup = new MembersPopup()
                .clickLoginLink()
                .inputLoginEmail(email)
                .inputLoginPassword(password)
                .clickContinueLoginButton();
        Assert.assertTrue(membersPopup.isInvalidLoginEmailErrorDisplayed(error),
                "Login email validation error is not displayed.");
    }

    @Test(testName = "WEMT-076", dependsOnMethods = "checkInvalidEmail", alwaysRun = true,
            description = "Verify that error text validation appear on the email and password text boxes when entering an invalid password")
    @Parameters({"email", "invalidPassword", "invalidPasswordError"})
    public void checkInvalidPassword(String email, String password, String error) {
        MembersPopup membersPopup = new MembersPopup()
                .clickLoginLink()
                .inputLoginEmail(email)
                .inputLoginPassword(password)
                .clickContinueLoginButton();
        Assert.assertTrue(membersPopup.isInvalidLoginPasswordErrorDisplayed(error),
                "Login email validation error is not displayed.");
    }
}
