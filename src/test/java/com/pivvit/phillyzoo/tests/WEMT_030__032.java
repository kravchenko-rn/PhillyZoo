package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import com.pivvit.phillyzoo.pages.popup.PastMembershipPopup;
import com.pivvit.phillyzoo.pages.popup.PurchaseTicketsPopup;
import com.pivvit.phillyzoo.pages.popup.VerifyYourselfPopup;
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
        new PurchaseTicketsPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        PastMembershipPopup pastMembershipPopup = new PastMembershipPopup();
        Assert.assertTrue(pastMembershipPopup.isOptionsResultListDisplayed(), "Search result list is not displayed or is empty.");

        pastMembershipPopup.hoverSearchResultItem(0)
                .validateResultItemFont(softAssert, 0, resultItemColor, true);
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-030", dependsOnMethods = "checkResultItemFontChange", alwaysRun = true,
            description = "Verify that user is able get back on previous step by clicking on 'change your search' link")
    public void checkChangeSearchLink() {
        PurchaseTicketsPopup purchaseTicketsPopup = new PastMembershipPopup()
                .clickChangeSearchLink();
        Assert.assertTrue(purchaseTicketsPopup.isEmailInputDisplayed(), "Did not get back to the previous step.");
    }

    @Test(testName = "WEMT-032", dependsOnMethods = "checkChangeSearchLink",
            description = "Verify that user is redirected to 'Verify Yourself' form after clicking on a specific disguised information")
    @Parameters("pageTitle")
    public void checkRedirectToVerifyYourself(String pageTitle) {
        new PurchaseTicketsPopup()
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        VerifyYourselfPopup verifyYourselfPopup = new PastMembershipPopup()
                .clickLookupResultItem(0);
        Assert.assertEquals(verifyYourselfPopup.getPageTitleText(), pageTitle, "'Verify Yourself' is not opened.");
    }
}
