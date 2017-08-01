package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

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
}
