package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.PastMembershipPopup;
import com.pivvit.phillyzoo.pages.popup.PurchaseTicketsPopup;
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
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-010", description = "Verify that error textbox validation appear on the last name textbox when clicking the search button with blank last name and zip code textboxes")
    @Parameters("lastNameValidationError")
    public void checkEmptyNameZipCodeSearch(String lastNameValidationError) {
        PurchaseTicketsPopup purchaseTicketsPopup = new PurchaseTicketsPopup()
                .clickAlternateFieldsLink();
        Assert.assertTrue(purchaseTicketsPopup.isLastNameInputDisplayed(), "Customer last name inout field is not displayed.");
        Assert.assertTrue(purchaseTicketsPopup.isZipCodeInputDisplayed(), "Customer zip code input field is not displayed.");

        purchaseTicketsPopup.clickSearchButton();
        Assert.assertTrue(purchaseTicketsPopup.isLastNameValidationErrorMessageDisplayed(),
                "Last name validation error is not displayed.");
        Assert.assertEquals(purchaseTicketsPopup.getLastNameValidationErrorText(), lastNameValidationError,
                "Last name error validation text is incorrect.");
    }

    @Test(testName = "WEMT-011", dependsOnMethods = "checkEmptyNameZipCodeSearch",
            description = "Verify that text box validation error appears on the last name text box when clicking the search button with value on zip code text box only")
    @Parameters("customerZipCode")
    public void checkEmptyLastNameValidationError(String customerZipCode) {
        PurchaseTicketsPopup purchaseTicketsPopup = new PurchaseTicketsPopup()
                .inputCustomerZipCode(customerZipCode)
                .clickSearchButton();
        Assert.assertTrue(purchaseTicketsPopup.isLastNameValidationErrorMessageDisplayed(),
                "Last name validation error is not displayed.");
    }

    @Test(testName = "WEMT-013", dependsOnMethods = "checkEmptyLastNameValidationError",
    description = "Verify that filter by zip code or phone number appear when multiple memberships match the entered last name")
    @Parameters("customerLastName")
    public void checkFiltersAppear(String customerLastName) {
        SoftAssert softAssert = new SoftAssert();

        new PurchaseTicketsPopup()
                .clearCustomerZipCode()
                .inputCustomerLastName(customerLastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        PastMembershipPopup pastMembershipPopup = new PastMembershipPopup();
        softAssert.assertTrue(pastMembershipPopup.isZipCodeFilterDisplayed(), "Zip code filter is not displayed.");
        softAssert.assertTrue(pastMembershipPopup.isPhoneFilterDisplayed(), "Phone filter is not displayed.");
        softAssert.assertAll();
    }
}
