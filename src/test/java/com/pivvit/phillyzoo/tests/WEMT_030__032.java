package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class WEMT_030__032 {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-030", description = "Verify that user is able get back on previous step by clicking on 'change your search' link")
    @Parameters("email")
    public void checkChangeSearchLink(String customerEmail) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .clickChangeSearchLink();
        Assert.assertTrue(membersPopup.isEmailInputDisplayed(), "Did not get back to the previous step.");
    }
}
