package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.PastMembershipPopup;
import com.pivvit.phillyzoo.pages.popup.PurchaseSummaryPopup;
import com.pivvit.phillyzoo.pages.popup.PurchaseTicketsPopup;
import com.pivvit.phillyzoo.pages.popup.UserInformationPopup;
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
        new PurchaseTicketsPopup()
                .clickAlternateFieldsLink()
                .inputCustomerLastName(customerLastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        new PastMembershipPopup()
                .inputCustomerPhoneNumber(incompletePhoneNumber)
                .submitPhoneNumber();
        UserInformationPopup userInformationPopup = new UserInformationPopup()
                .clickSubmitButton();

        softAssert.assertTrue(userInformationPopup.isFirstNameValidationErrorDisplayed(expectedErrorText),
                "First name validation error is not displayed.");
        softAssert.assertTrue(userInformationPopup.isLastNameValidationErrorDisplayed(expectedErrorText),
                "Last name validation error is not displayed.");
        softAssert.assertTrue(userInformationPopup.isEmailValidationErrorDisplayed(expectedErrorText),
                "Email validation error is not displayed.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-021", dependsOnMethods = "checkUserInformationBlank", alwaysRun = true,
            description = "Verify that error text validation appear on email text box when entering invalid email format")
    @Parameters({"invalidEmail", "invalidFieldErrorText"})
    public void checkUserFormInvalidEmail(String invalidEmail, String expectedErrorText) {
        UserInformationPopup userInformationPopup = new UserInformationPopup()
                .inputEmail(invalidEmail)
                .clickSubmitButton();

        Assert.assertTrue(userInformationPopup.isEmailValidationErrorDisplayed(expectedErrorText),
                "Email validation error is not displayed.");
    }

    @Test(testName = "WEMT-022", dependsOnMethods = "checkUserFormInvalidEmail", alwaysRun = true,
            description = "Verify that error text validation appear on password text box when entering invalid password format")
    @Parameters({"invalidPassword", "invalidFieldErrorText"})
    public void checkUserFormInvalidPassword(String invalidPassword, String expectedErrorText) {
        UserInformationPopup userInformationPopup = new UserInformationPopup()
                .inputPassword(invalidPassword);

        Assert.assertTrue(userInformationPopup.isPasswordValidationErrorDisplayed(expectedErrorText),
                "Password validation error is not displayed.");
    }

    @Test(testName = "WEMT-024", dependsOnMethods = "checkUserFormInvalidPassword", alwaysRun = true,
            description = "Verify that user cannot enter letters on the phone number text box in 'Identity validation box'")
    @Parameters("literalCharacters")
    public void checkUserFormPhoneLiteralCharacters(String invalidPhone) {
        UserInformationPopup userInformationPopup = new UserInformationPopup()
                .inputPhone(invalidPhone);
        Assert.assertTrue(userInformationPopup.getPhoneInputText().isEmpty(), "Phone input field accepts literal characters.");
    }

    @Test(testName = "WEMT-025", dependsOnMethods = "checkUserFormPhoneLiteralCharacters", alwaysRun = true,
            description = "Verify that user cannot enter special characters on the phone number text box  in 'Identity validation box'")
    @Parameters("specialCharacters")
    public void checkUserFormPhoneSpecialCharacters(String specialCharacters) {
        SoftAssert softAssert = new SoftAssert();
        UserInformationPopup userInformationPopup = new UserInformationPopup();
        List<String> characters =  new LinkedList<>(Arrays.asList(specialCharacters.split(" ")));
        characters.forEach(character -> {
            userInformationPopup.inputPhone(character);
            softAssert.assertTrue(userInformationPopup.getPhoneInputText().isEmpty(),
                    "Phone input field accepts '" + character + "' character.");
        });
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-020", dependsOnMethods = "checkUserFormPhoneSpecialCharacters", alwaysRun = true,
            description = "Verify that user is able to submit user information after clicking submit button with valid name, email address, password and phone number")
    @Parameters({"firstName", "lastName", "email", "password", "phoneNumber"})
    public void checkUserFormSubmit(String firstName, String lastName, String email, String password, String phone) {
        new UserInformationPopup()
                .inputFirstName(firstName)
                .inputLastName(lastName)
                .inputEmail(email)
                .inputPassword(password)
                .inputPhone(phone)
                .clickSubmitButton()
                .waitTillLoadingIndicatorDisappears();

        Assert.assertTrue(new PurchaseSummaryPopup().isPurchaseSummaryDisplayed(),
                "User form is not submitted - purchase summary page is not displayed.");
    }
}
