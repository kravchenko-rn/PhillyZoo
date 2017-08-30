package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.PastMembershipPopup;
import com.pivvit.phillyzoo.pages.popup.PurchaseTicketsPopup;
import com.pivvit.phillyzoo.pages.popup.WinterExperienceTicketsPopup;
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
        new PurchaseTicketsPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        new PastMembershipPopup()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "));
        WinterExperienceTicketsPopup winterExperienceTicketsPopup = new WinterExperienceTicketsPopup();
        Assert.assertEquals(winterExperienceTicketsPopup.getCurrentAmountOfTickets(), "0", "Amount of tickets is not set to 0 in the first place.");
        Assert.assertFalse(winterExperienceTicketsPopup.isContinueButtonEnabled(), "Continue button is enabled.");
    }

    @Test(testName = "WEMT-037", dependsOnMethods = "checkButtonIsDisabled", alwaysRun = true,
            description = "Verify that Continue button should be enabled when members counter is between 1 to 13 interval")
    public void checkButtonIsEnabled() {
        SoftAssert softAssert = new SoftAssert();
        WinterExperienceTicketsPopup winterExperienceTicketsPopup = new WinterExperienceTicketsPopup();
        for (int amount = 1; amount <= 13; amount++) {
            winterExperienceTicketsPopup.selectMemberTicketsAmount(Integer.toString(amount));
            softAssert.assertTrue(winterExperienceTicketsPopup.isContinueButtonEnabled(),
                    "Continue button is disabled for tickets amount " + amount);
        }
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-039", dependsOnMethods = "checkButtonIsEnabled", alwaysRun = true,
            description = "Verify that max members counter value is 13")
    @Parameters("maximumAmount")
    public void checkMaximumAmountOfTicketsValue(String maximumAmount) {
        Assert.assertEquals(new WinterExperienceTicketsPopup().getMaximumAmountOfMemberTicketsValue(), maximumAmount,
                "The value of the maximum amount of tickets is incorrect.");
    }
}
