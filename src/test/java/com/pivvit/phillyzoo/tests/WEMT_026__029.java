package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

public class WEMT_026__029 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
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
}
