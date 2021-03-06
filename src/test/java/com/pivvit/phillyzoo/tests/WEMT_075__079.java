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


public class WEMT_075__079 extends BaseTest {
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
                .waitTillLoadingIndicatorDisappears()
                .waitForLoad();
    }

    @Test(testName = "WEMT-075",
            description = "Verify that error text validation appear on the email and password text boxes when entering an invalid email")
    @Parameters({"invalidEmail", "password", "invalidLoginError"})
    public void checkInvalidEmail(String email, String password, String error) {
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .clickLoginLink()
                .inputLoginEmail(email)
                .inputLoginPassword(password)
                .clickContinueLoginButton();
        Assert.assertTrue(checkoutPopup.isInvalidLoginEmailErrorDisplayed(error),
                "Login email validation error is not displayed.");
    }

    @Test(testName = "WEMT-076", dependsOnMethods = "checkInvalidEmail", alwaysRun = true,
            description = "Verify that error text validation appear on the email and password text boxes when entering an invalid password")
    @Parameters({"email", "invalidPassword", "invalidPasswordError"})
    public void checkInvalidPassword(String email, String password, String passwordError) {
        SoftAssert softAssert = new SoftAssert();
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .inputLoginEmail(email)
                .inputLoginPassword(password)
                .clickContinueLoginButton();
        softAssert.assertTrue(checkoutPopup.isAnyInvalidLoginEmailErrorDisplayed(),
                "Login email validation error is displayed though the email is valid.");
        softAssert.assertTrue(checkoutPopup.isInvalidLoginPasswordErrorDisplayed(passwordError),
                "Login password validation error is not displayed.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-078", dependsOnMethods = "checkInvalidPassword", alwaysRun = true,
            description = "Verify that error text validation appear on the email text box when entering a non-registered valid email address on the forgot your password form")
    @Parameters({"nonRegisteredEmail", "nonRegisteredEmailError"})
    public void checkNonRegisteredEmailAtForgotFrom(String email, String error) {
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .clickForgotLink();
        Assert.assertTrue(checkoutPopup.isForgotEmailInputDisplayed(), "Forgot email input is not displayed.");

        checkoutPopup.inputForgotEmail(email)
                .clickSubmitForgotEmailButton();
        Assert.assertTrue(checkoutPopup.isForgotEmailErrorDisplayed(error),
                "Validation error is not displayed.");
    }

    @Test(testName = "WEMT-079", dependsOnMethods = "checkNonRegisteredEmailAtForgotFrom", alwaysRun = true,
            description = "Verify that error text validation appear on the email text box when entering an invalid email format on the forgot your password form")
    @Parameters({"invalidEmailFormat", "invalidEmailFormatError"})
    public void checkInvalidEmailFormatAtForgotForm(String email, String error) {
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .inputForgotEmail(email)
                .clickSubmitForgotEmailButton();
        Assert.assertTrue(checkoutPopup.isForgotEmailErrorDisplayed(error),
                "Validation error is not displayed.");
    }

    // TODO: add assertion when have a valid active account
    @Test(testName = "WEMT-077", dependsOnMethods = "checkInvalidEmailFormatAtForgotForm", alwaysRun = true,
            description = "Verify that email should sent to the user when entering a registered valid email address on the forgot your password form")
    @Parameters("email")
    public void checkRegisteredEmailAtForgotForm(String email) {
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .inputForgotEmail(email)
                .clickSubmitForgotEmailButton();

    }
}
