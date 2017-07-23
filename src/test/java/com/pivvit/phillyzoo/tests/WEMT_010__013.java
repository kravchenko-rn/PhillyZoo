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

public class WEMT_010__013 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-010", description = "Verify that error textbox validation appear on the last name textbox when clicking the search button with blank last name and zip code textboxes")
    @Parameters("lastNameValidationError")
    public void checkEmptyNameZipCodeSearch(String lastNameValidationError) {
        MembersPopup membersPopup = new MembersPopup()
                .clickAlternateFieldsLink();
        Assert.assertTrue(membersPopup.isLastNameInputDisplayed(), "Customer last name inout field is not displayed.");
        Assert.assertTrue(membersPopup.isZipCodeInputDisplayed(), "Customer zip code input field is not displayed.");

        membersPopup.clickSearchButton();
        Assert.assertTrue(membersPopup.isLastNameValidationErrorMessageDisplayed(),
                "Last name validation error is not displayed.");
        Assert.assertEquals(membersPopup.getLastNameValidationErrorText(), lastNameValidationError,
                "Last name error validation text is incorrect.");
    }

    @Test(testName = "WEMT-011", dependsOnMethods = "checkEmptyNameZipCodeSearch",
            description = "Verify that error textbox validation appear on the last name text box when clicking the search button with value on zip code textbox only")
    @Parameters("customerZipCode")
    public void checkEmptyLastNameValidationError(String customerZipCode) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerZipCode(customerZipCode)
                .clickSearchButton();
        Assert.assertTrue(membersPopup.isLastNameValidationErrorMessageDisplayed(),
                "Last name validation error is not displayed.");
    }

    @Test(testName = "WEMT-013", dependsOnMethods = "checkEmptyLastNameValidationError",
    description = "Verify that filter by zipcode or phone number appear when multiple memberships match the entered last name")
    @Parameters("customerLastName")
    public void checkFiltersAppear(String customerLastName) {
        SoftAssert softAssert = new SoftAssert();

        MembersPopup membersPopup = new MembersPopup()
                .clearCustomerZipCode()
                .inputCustomerLastName(customerLastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        softAssert.assertTrue(membersPopup.isZipCodeFilterDisplayed(), "Zip code filter is not displayed.");
        softAssert.assertTrue(membersPopup.isPhoneFilterDisplayed(), "Phone filter is not displayed.");
        softAssert.assertAll();
    }
}
