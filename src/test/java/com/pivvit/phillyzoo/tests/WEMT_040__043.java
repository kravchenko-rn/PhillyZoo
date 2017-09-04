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

public class WEMT_040__043 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-040", description = "Verify that non-members counter is  displayed when clicking on 'Add non-member tickets'")
    @Parameters({"email", "verificationCharacters"})
    public void checkNonMembersCounter(String customerEmail, String charactersSet) {
        new PurchaseTicketsPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        new PastMembershipPopup()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "));
        WinterExperienceTicketsPopup winterExperienceTicketsPopup = new WinterExperienceTicketsPopup()
                .clickAddNonMemberTicketsLink();
        Assert.assertTrue(winterExperienceTicketsPopup.isNonMemberTicketsSelectVisible(),
                "Non member tickets select is not displayed.");
    }

    @Test(testName = "WEMT-042", dependsOnMethods = "checkNonMembersCounter",
            description = "Verify that Continue button is disabled when non-members counter is 0")
    public void checkButtonIsDisabled() {
        WinterExperienceTicketsPopup winterExperienceTicketsPopup = new WinterExperienceTicketsPopup();
        Assert.assertEquals(winterExperienceTicketsPopup.getCurrentAmountOfNonMemberTickets(), "0",
                "Amount of non member tickets is not set to 0 in the first place.");
        Assert.assertFalse(winterExperienceTicketsPopup.isContinueButtonEnabled(), "Continue button is enabled.");
    }

    @Test(testName = "WEMT-041", dependsOnMethods = "checkButtonIsDisabled",
            description = "Verify that Continue button is enabled when members counter is between 1 to 14 interval")
    public void checkButtonIsEnabled() {
        SoftAssert softAssert = new SoftAssert();
        WinterExperienceTicketsPopup winterExperienceTicketsPopup = new WinterExperienceTicketsPopup();
        for (int amount = 1; amount <= 14; amount++) {
            winterExperienceTicketsPopup.selectNonMemberTicketsAmount(Integer.toString(amount));
            softAssert.assertTrue(winterExperienceTicketsPopup.isContinueButtonEnabled(),
                    "Continue button is disabled for non member tickets amount " + amount);
        }
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-043", dependsOnMethods = "checkButtonIsEnabled",
            description = "Verify that max members counter value is 14")
    @Parameters("maximumAmount")
    public void checkMaximumAmoutOfNonmemberTicketsValue(String maximumAmount) {
        Assert.assertEquals(new WinterExperienceTicketsPopup().getMaximumAmountOfNonMemberTicketsValue(), maximumAmount,
                "The value of the maximum amount of non member tickets is incorrect.");
    }
}
