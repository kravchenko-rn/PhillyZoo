package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.utils.SoftAssert;

public class WEMT_030__032 {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-031",
            description = "Verify that the search result items changed into blue font color and bold text format when hovering on each result item")
    @Parameters({"email", "resultItemHoveredColor"})
    public void checkResultItemFontChange(String customerEmail, String resultItemColor) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(membersPopup.isOptionsResultListDisplayed(), "Search result list is not displayed or is empty.");

        membersPopup.hoverSearchResultItem(0)
                .validateResultItemFont(softAssert, 0, resultItemColor, true);
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-030", dependsOnMethods = "checkResultItemFontChange", alwaysRun = true,
            description = "Verify that user is able get back on previous step by clicking on 'change your search' link")
    public void checkChangeSearchLink() {
        MembersPopup membersPopup = new MembersPopup()
                .clickChangeSearchLink();
        Assert.assertTrue(membersPopup.isEmailInputDisplayed(), "Did not get back to the previous step.");
    }

    @Test(testName = "WEMT-032", dependsOnMethods = "checkChangeSearchLink",
            description = "Verify that user is redirected to 'Verify Yourself' form after clicking on a specific disguised information")
    @Parameters("pageTitle")
    public void checkRedirectToVerifyYourself(String pageTitle) {
        MembersPopup membersPopup = new MembersPopup()
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .clickLookupResultItem(0);
        Assert.assertEquals(membersPopup.getPageTitleText(), pageTitle, "'Verify Yourself' is not opened.");
    }
}
