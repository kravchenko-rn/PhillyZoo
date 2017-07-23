package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

public class WEMT_010__013 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-010", description = "Verify that error textbox validation appear on the last name textbox when clicking the search button with blank last name and zip code textboxes")
    public void checkEmptyNameZipCodeSearch() {
        MembersPopup membersPopup = new MembersPopup()
                .clickAlternateFieldsLink();
        Assert.assertTrue(membersPopup.isLastNameInputDisplayed(), "Customer last name inout field is not displayed.");
        Assert.assertTrue(membersPopup.isZipCodeInputDisplayed(), "Customer zip code input field is not displayed.");

        membersPopup.clickSearchButton();
        Assert.assertTrue(membersPopup.isLastNameValidationErrorMessageDisplayed(),
                "Last name validation error is not displayed.");
    }
}
