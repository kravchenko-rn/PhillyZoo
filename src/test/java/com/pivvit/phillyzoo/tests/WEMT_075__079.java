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
    public void checkInvalidPassword(String email, String password, String passwordError) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup()
                .inputLoginEmail(email)
                .inputLoginPassword(password)
                .clickContinueLoginButton();
        softAssert.assertTrue(membersPopup.isAnyInvalidLoginEmailErrorDisplayed(),
                "Login email validation error is displayed though the email is valid.");
        softAssert.assertTrue(membersPopup.isInvalidLoginPasswordErrorDisplayed(passwordError),
                "Login password validation error is not displayed.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-078", dependsOnMethods = "checkInvalidPassword", alwaysRun = true,
            description = "Verify that error text validation appear on the email text box when entering a non-registered valid email address on the forgot your password form")
    @Parameters({"nonRegisteredEmail", "nonRegisteredEmailError"})
    public void checkNonRegisteredEmailAtForgotFrom(String email, String error) {
        MembersPopup membersPopup = new MembersPopup()
                .clickForgotLink();
        Assert.assertTrue(membersPopup.isForgotEmailInputDisplayed(), "Forgot email input is not displayed.");

        membersPopup.inputForgotEmail(email)
                .clickSubmitForgotEmailButton();
        Assert.assertTrue(membersPopup.isForgotEmailErrorDisplayed(error),
                "Validation error is not displayed.");
    }

    @Test(testName = "WEMT-079", dependsOnMethods = "checkNonRegisteredEmailAtForgotFrom", alwaysRun = true,
            description = "Verify that error text validation appear on the email text box when entering an invalid email format on the forgot your password form")
    @Parameters({"invalidEmailFormat", "invalidEmailFormatError"})
    public void checkInvalidEmailFormatAtForgotForm(String email, String error) {
        MembersPopup membersPopup = new MembersPopup()
                .inputForgotEmail(email)
                .clickSubmitForgotEmailButton();
        Assert.assertTrue(membersPopup.isForgotEmailErrorDisplayed(error),
                "Validation error is not displayed.");
    }

    // TODO: add assertion when have a valid active account
    @Test(testName = "WEMT-077", dependsOnMethods = "checkInvalidEmailFormatAtForgotForm", alwaysRun = true,
            description = "Verify that email should sent to the user when entering a registered valid email address on the forgot your password form")
    @Parameters("email")
    public void checkRegisteredEmailAtForgotForm(String email) {
        MembersPopup membersPopup = new MembersPopup()
                .inputForgotEmail(email)
                .clickSubmitForgotEmailButton();

    }
}
