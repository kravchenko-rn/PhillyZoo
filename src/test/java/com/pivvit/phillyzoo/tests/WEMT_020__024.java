package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseActions;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

public class WEMT_020__024 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-023", description = "Verify that error text validation appear on the required text boxes when clicking submit button with all text boxes left blank")
    @Parameters({"incompletePhoneNumber", "customerLastName", "blankFieldErrorText"})
    public void checkUserInformationBlank(String incompletePhoneNumber, String customerLastName, String expectedErrorText) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup()
                .clickAlternateFieldsLink()
                .inputCustomerLastName(customerLastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .inputCustomerPhoneNumber(incompletePhoneNumber)
                .submitPhoneNumber()
                .clickSubmitPurchaseButton();

        softAssert.assertTrue(membersPopup.isUserFormFirstNameErrorDisplayed(expectedErrorText),
                "User form first name validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isUserFormLastNameErrorDisplayed(expectedErrorText),
                "User form last name validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isUserFormEmailErrorDisplayed(expectedErrorText),
                "User form email validation error is not displayed.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-021", dependsOnMethods = "checkUserInformationBlank", alwaysRun = true,
            description = "Verify that error text validation appear on email text box when entering invalid email format")
    @Parameters({"invalidEmail", "invalidFieldErrorText"})
    public void checkUserFormInvalidEmail(String invalidEmail, String expectedErrorText) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup()
                .inputUserFormEmail(invalidEmail)
                .clickSubmitPurchaseButton();

        softAssert.assertTrue(membersPopup.isUserFormEmailErrorDisplayed(expectedErrorText),
                "User form email validation error is not displayed.");
        softAssert.assertEquals(membersPopup.getEmailUserFormValidationErrorText(), expectedErrorText,
                "Email validation error text is invalid.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-022", dependsOnMethods = "checkUserFormInvalidEmail", alwaysRun = true,
            description = "Verify that error text validation appear on password text box when entering invalid password format")
    @Parameters({"invalidPassword", "invalidFieldErrorText"})
    public void checkUserFormInvalidPassword(String invalidPassword, String expectedErrorText) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup()
                .inputUserFormPassword(invalidPassword);

        softAssert.assertTrue(membersPopup.isUserFormPasswordErrorDisplayed(expectedErrorText),
                "User form password validation error is not displayed.");
        softAssert.assertEquals(membersPopup.getPasswordUserFormValidationErrorText(), expectedErrorText,
                "Password validation error text is invalid.");
        softAssert.assertAll();
    }
}
