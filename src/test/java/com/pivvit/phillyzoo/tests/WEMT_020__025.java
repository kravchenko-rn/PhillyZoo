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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WEMT_020__025 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
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

    @Test(testName = "WEMT-024", dependsOnMethods = "checkUserFormInvalidPassword", alwaysRun = true,
            description = "Verify that user cannot enter letters on the phone number text box in 'Identity validation box'")
    @Parameters("literalCharacters")
    public void checkUserFormPhoneLiteralCharacters(String invalidPhone) {
        MembersPopup membersPopup = new MembersPopup()
                .inputUserFormPhone(invalidPhone);
        Assert.assertTrue(membersPopup.getPhoneUserForm().isEmpty(), "Phone input field accepts literal characters.");
    }

    @Test(testName = "WEMT-025", dependsOnMethods = "checkUserFormPhoneLiteralCharacters", alwaysRun = true,
            description = "Verify that user cannot enter special characters on the phone number text box  in 'Identity validation box'")
    @Parameters("specialCharacters")
    public void checkUserFormPhoneSpecialCharacters(String specialCharacters) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup();
        List<String> characters =  new LinkedList<>(Arrays.asList(specialCharacters.split(" ")));
        characters.forEach(character -> {
            membersPopup.inputUserFormPhone(character);
            softAssert.assertTrue(membersPopup.getPhoneUserForm().isEmpty(),
                    "Phone input field at the user form accepts '" + character + "' character.");
        });
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-020", dependsOnMethods = "checkUserFormPhoneSpecialCharacters", alwaysRun = true,
            description = "Verify that user is able to submit user information after clicking submit button with valid name, email address, password and phone number")
    @Parameters({"firstName", "lastName", "email", "password", "phoneNumber"})
    public void checkUserFormSubmit(String firstName, String lastName, String email, String password, String phone) {
        MembersPopup membersPopup = new MembersPopup()
                .inputUserFormFirstName(firstName)
                .inputUserFormLastName(lastName)
                .inputUserFormEmail(email)
                .inputUserFormPassword(password)
                .inputUserFormPhone(phone)
                .clickSubmitPurchaseButton()
                .waitTillLoadingIndicatorDisappears();

        Assert.assertTrue(membersPopup.isPurchaseSummaryDisplayed(), "User form is not submitted.");
    }
}
