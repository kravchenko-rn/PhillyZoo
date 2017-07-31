package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

public class WEMT_037__039 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
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
}
