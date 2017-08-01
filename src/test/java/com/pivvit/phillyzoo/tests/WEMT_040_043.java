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

public class WEMT_040_043 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-040", description = "Verify that non-members counter is  displayed when clicking on 'Add non-member tickets'")
    @Parameters({"email", "verificationCharacters"})
    public void checkNonMembersCounter(String customerEmail, String charactersSet) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "))
                .clickAddNonMemberTicketsLink();
        Assert.assertTrue(membersPopup.isNonMemberTicketsSelectVisible(), "Non member tickets select is not displayed.");
    }

    @Test(testName = "WEMT-042", dependsOnMethods = "checkNonMembersCounter",
            description = "Verify that Continue button is disabled when non-members counter is 0")
    public void checkButtonIsDisabled() {
        MembersPopup membersPopup = new MembersPopup();
        Assert.assertEquals(membersPopup.getCurrentAmountOfNonMemberTickets(), "0",
                "Amount of non member tickets is not set to 0 in the first place.");
        Assert.assertFalse(membersPopup.isPurchaseContinueButtonEnabled(), "Continue button is enabled.");
    }

    @Test(testName = "WEMT-041", dependsOnMethods = "checkButtonIsDisabled",
            description = "Verify that Continue button is enabled when members counter is between 1 to 14 interval")
    public void checkButtonIsEnabled() {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup();
        for (int amount = 1; amount <= 14; amount++) {
            membersPopup.selectNonMemberTicketsAmount(Integer.toString(amount));
            softAssert.assertTrue(membersPopup.isPurchaseContinueButtonEnabled(),
                    "Continue button is disabled for non member tickets amount " + amount);
        }
        softAssert.assertAll();
    }
}
