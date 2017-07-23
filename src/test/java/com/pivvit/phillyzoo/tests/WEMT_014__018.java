package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import com.sun.xml.internal.bind.v2.TODO;
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
    public void checkDisguisedInformationOnZipCodeSelect(String customerLastName, String zipCode) {
        MembersPopup membersPopup = new MembersPopup()
                .clickAlternateFieldsLink()
                .inputCustomerLastName(customerLastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .selectZipCodeFromFilter(zipCode);
        Assert.assertTrue(membersPopup.isOptionsResultListDisplayed(),
                "Disguised information is not displayed after choosing a zip code from dropdown filter.");
    }

    // TODO: dependsOnMethod parameter should be updated and the test should be enabled when the valid phone number is set for this test
    @Test(testName = "WEMT-015", dependsOnMethods = "checkDisguisedInformationOnZipCodeSelect", enabled = false,
            description = "Verify that disguised information is displayed after entering a valid phone number")
    @Parameters("customerPhoneNumber")
    public void checkDisguisedInformationOnPhoneEnter(String customerPhoneNumber) {
        MembersPopup membersPopup = new MembersPopup()
                .deselectZipCodeFilter()
                .inputCustomerPhoneNumber(customerPhoneNumber);
        Assert.assertTrue(membersPopup.isOptionsResultListDisplayed(),
                "Disguised information is not displayed after inputting phone number into phone filter.");
    }
}
