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

public class WEMT_037__039 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-038", description = "Verify that Continue button is disabled when members counter is 0")
    @Parameters({"email", "verificationCharacters"})
    public void checkButtonIsDisabled(String customerEmail, String charactersSet) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "));
        Assert.assertEquals(membersPopup.getCurrentAmountOfTickets(), "0", "Amount of tickets is not set to 0 in the first place.");
        Assert.assertFalse(membersPopup.isPurchaseContinueButtonEnabled(), "Continue button is enabled.");
    }

    @Test(testName = "WEMT-037", dependsOnMethods = "checkButtonIsDisabled", alwaysRun = true,
            description = "Verify that Continue is should enabled when members counter is between 1 to 13 interval")
    public void checkButtonIsEnabled() {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup();
        for (int amount = 1; amount <= 13; amount++) {
            membersPopup.selectTicketsAmount(Integer.toString(amount));
            softAssert.assertTrue(membersPopup.isPurchaseContinueButtonEnabled(),
                    "Continue button is disabled for tickets amount " + amount);
        }
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-039", dependsOnMethods = "checkButtonIsEnabled", alwaysRun = true,
            description = "Verify that max members counter value is 13")
    @Parameters("maximumAmount")
    public void checkMaximumAmountOfTicketsValue(String maximumAmount) {
        MembersPopup membersPopup = new MembersPopup();
        Assert.assertEquals(membersPopup.getMaximumAmountOfTicketsValue(), maximumAmount,
                "The value of the maximum amount of tickets is incorrect.");
    }
}
