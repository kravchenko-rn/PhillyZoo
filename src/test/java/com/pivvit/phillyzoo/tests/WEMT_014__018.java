package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import com.pivvit.phillyzoo.pages.popup.PastMembershipPopup;
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

public class WEMT_014__018 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-014", description = "Verify that disguised information is displayed after choosing a zip code on the dropdown list")
    @Parameters({"customerLastName", "zipCode"})
    public void checkDisguisedInformationOnZipCodeSelect(String customerLastName, String zipCode) {
        new PurchaseTicketsPopup()
                .clickAlternateFieldsLink()
                .inputCustomerLastName(customerLastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        PastMembershipPopup pastMembershipPopup = new PastMembershipPopup()
                .selectZipCodeFromFilter(zipCode);
        Assert.assertTrue(pastMembershipPopup.isOptionsResultListDisplayed(),
                "Disguised information is not displayed after choosing a zip code from dropdown filter.");
    }

    // TODO: this test should be enabled when the valid phone number is set for this test
    @Test(testName = "WEMT-015", dependsOnMethods = "checkDisguisedInformationOnZipCodeSelect", enabled = false,
            description = "Verify that disguised information is displayed after entering a valid phone number")
    @Parameters("customerPhoneNumber")
    public void checkDisguisedInformationOnPhoneEnter(String customerPhoneNumber) {
        PastMembershipPopup pastMembershipPopup = new PastMembershipPopup()
                .deselectZipCodeFilter()
                .inputCustomerPhoneNumber(customerPhoneNumber)
                .submitPhoneNumber();
        Assert.assertTrue(pastMembershipPopup.isOptionsResultListDisplayed(),
                "Disguised information is not displayed after inputting phone number into phone filter.");
    }

    // TODO: dependsOnMethod parameter should be updated when the valid phone number is set for WEMT-015
    @Test(testName = "WEMT-016", dependsOnMethods = "checkDisguisedInformationOnZipCodeSelect",
            description = "Verify that user cannot enter letters on the phone number text box")
    @Parameters("literalCharacters")
    public void checkPhoneFilterLiteralCharacters(String literalCharacters) {
        PastMembershipPopup pastMembershipPopup = new PastMembershipPopup()
                .clearPhoneFilterInput()
                .inputCustomerPhoneNumber(literalCharacters);
        Assert.assertTrue(pastMembershipPopup.getPhoneFilterInputText().isEmpty(), "Phone filter accepts literal symbols.");
    }

    @Test(testName = "WEMT-017", dependsOnMethods = "checkPhoneFilterLiteralCharacters",
            description = "Verify that user cannot enter special characters on the phone number text box")
    @Parameters("specialCharacters")
    public void checkPhoneFilterSpecialCharacters(String specialCharacters) {
        SoftAssert softAssert = new SoftAssert();
        PastMembershipPopup pastMembershipPopup = new PastMembershipPopup();
        List<String> characters = new LinkedList<>(Arrays.asList(specialCharacters.split(" ")));
        characters.forEach(character -> {
            pastMembershipPopup.clearPhoneFilterInput()
                    .inputCustomerPhoneNumber(character);
            softAssert.assertTrue(pastMembershipPopup.getPhoneFilterInputText().isEmpty(),
                    "Phone filter accepts '" + character + "' character.");
        });
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-018", dependsOnMethods = "checkPhoneFilterSpecialCharacters",
    description = "Verify that user is redirected to 'user information' form when entering an incomplete phone number")
    @Parameters("incompletePhoneNumber")
    public void checkIncompletePhoneNumber(String incompletePhoneNumber) {
        new PastMembershipPopup()
                .inputCustomerPhoneNumber(incompletePhoneNumber)
                .submitPhoneNumber();
        Assert.assertTrue(new UserInformationPopup().isUserInformationFormDisplayed(),
                "User information form is not displayed after inputting partial phone number into phone filter.");
    }
}
