package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.PastMembershipPopup;
import com.pivvit.phillyzoo.pages.popup.PurchaseTicketsPopup;
import com.pivvit.phillyzoo.pages.popup.VerifyYourselfPopup;
import com.pivvit.phillyzoo.pages.popup.WinterExperienceTicketsPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

public class WEMT_033__035 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-033", description = "Verify that user is able get back on previous step by clicking on 'return to results' link")
    @Parameters({"email", "pageTitle"})
    public void CheckReturnToResultsLink(String customerEmail, String pageTitle) {
        new PurchaseTicketsPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        PastMembershipPopup pastMembershipPopup = new PastMembershipPopup()
                .clickLookupResultItem(0)
                .clickReturnToResultsLink();
        Assert.assertEquals(pastMembershipPopup.getPageTitleText(), pageTitle, "Did not get back to the previous step.");
    }

    @Test(testName = "WEMT-035", dependsOnMethods = "CheckReturnToResultsLink",
            description = "Verify that error message appear after entering invalid missing characters")
    @Parameters({"incorrectVerificationCharacters", "verificationError"})
    public void checkInvalidMissingCharacters(String charactersSet, String verificationError) {
        SoftAssert softAssert = new SoftAssert();
        VerifyYourselfPopup verifyYourselfPopup = new PastMembershipPopup()
                .clickLookupResultItem(0)
                .clickSubmitButton();
        softAssert.assertTrue(verifyYourselfPopup.isVerificationErrorDisplayed(verificationError),
                "User verification error is not displayed or has incorrect text when input fields left blank.");

        verifyYourselfPopup
                .inputVerificationCharacters(charactersSet.split(" "))
                .clickSubmitButton();
        softAssert.assertTrue(verifyYourselfPopup.isVerificationErrorDisplayed(verificationError),
                "User verification error is not displayed or has incorrect text when input fields contain incorrect characters.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-034", dependsOnMethods = "checkInvalidMissingCharacters", alwaysRun = true,
            description = "Verify that user is redirected to 'Winter Experience Tickets' form after entering valid missing characters and clicking submit button")
    @Parameters({"verificationCharacters", "ticketsPageTitle"})
    public void checkValidVerificationCharacters(String charactersSet, String expectedPageTitle) {
        new VerifyYourselfPopup()
                .inputVerificationCharacters(charactersSet.split(" "));
        Assert.assertEquals(new WinterExperienceTicketsPopup().getPageTitleText(), expectedPageTitle,
                "User is not redirected to 'Winter Experience Tickets' form after entering valid missing characters.");
    }
}
