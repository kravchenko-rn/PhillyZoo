package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseActions;
import pivvit.base.BaseTest;

public class WEMT_026__029 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-026", description = "Verify that error message appear when no results were returned that match the entered last name")
    @Parameters({"nonExistingLastName", "expectedErrorText"})
    public void checkNonExistingLastName(String lastName, String expectedErrorText) {
        MembersPopup membersPopup = new MembersPopup()
                .clickAlternateFieldsLink()
                .inputCustomerLastName(lastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(membersPopup.isErrorMessageDisplayed(expectedErrorText), "Error message is not displayed.");
        Assert.assertEquals(membersPopup.getErrorMessageText(), expectedErrorText, "Error message is incorrect.");
    }

    @Test(testName = "WEMT-028", dependsOnMethods = "checkNonExistingLastName", alwaysRun = true,
            description = "Verify that error message appear when no results were returned that match the entered last name and zip code")
    @Parameters({"nonExistingLastName", "expectedErrorText", "zipCode"})
    public void checkNonExistingLastNameExistingZipCode(String lastName, String expectedErrorText, String zipCode) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerLastName(lastName)
                .inputCustomerZipCode(zipCode)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(membersPopup.isErrorMessageDisplayed(expectedErrorText), "Error message is not displayed.");
        Assert.assertEquals(membersPopup.getErrorMessageText(), expectedErrorText, "Error message is incorrect.");
    }

    @Test(testName = "WEMT-027", dependsOnMethods = "checkNonExistingLastNameExistingZipCode", alwaysRun = true,
            description = "Verify that search result returns when searching with valid member last name and zip code")
    @Parameters({"customerLastName", "zipCode"})
    public void checkExistingLastNameZipCode(String lastName, String zipCode) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerLastName(lastName)
                .inputCustomerZipCode(zipCode)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(membersPopup.isOptionsResultListDisplayed(), "Search results are not displayed or empty.");
    }

    @Test(testName = "WEMT-029", dependsOnMethods = "checkExistingLastNameZipCode", alwaysRun = true,
    description = "Verify that search result list named 'Past Membership' appear after searching with valid member last name/email address")
    @Parameters("email")
    public void checkExistingEmail(String email) {
        BaseActions.frontendInstance().refresh();
        MembersPopup membersPopup = new MembersPopup()
                .waitForPageLoad()
                .inputCustomerEmail(email)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(membersPopup.isOptionsResultListDisplayed(), "Search results are not displayed or empty.");
    }
}
