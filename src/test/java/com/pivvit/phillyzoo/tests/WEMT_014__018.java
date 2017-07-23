package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

public class WEMT_014__018 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-014", description = "Verify that disguised information is displayed after choosing a zip code on the dropdown list")
    @Parameters({"customerLastName", "zipCode"})
    public void checkDisguisedInformationIsDisplayed(String customerLastName, String zipCode) {
        MembersPopup membersPopup = new MembersPopup()
                .clickAlternateFieldsLink()
                .inputCustomerLastName(customerLastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .selectZipCodeFromFilter(zipCode);
        Assert.assertTrue(membersPopup.isOptionsResultListDisplayed(),
                "Disguised information is not displayed after choosing a zip code from dropdown filter.");
    }
}