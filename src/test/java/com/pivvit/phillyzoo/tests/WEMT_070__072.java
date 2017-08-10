package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

@SuppressWarnings("all")
public class WEMT_070__072 extends BaseTest {
    @BeforeTest
    @Parameters({"email", "verificationCharacters"})
    public void init(String customerEmail, String charactersSet) {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "))
                .selectTicketsAmount("1")
                .clickContinuePurchaseButton()
                .clickAcceptTermsButton()
                .selectFirstAvailableDate();
        String time = membersPopup.getStartBookingTime();
        membersPopup.selectTimeForTheTicket(time)
                .clickContinuePurchaseButton()
                .waitTillLoadingIndicatorDisappears()
                .waitForCheckoutLoad();
    }

    @Test(testName = "WEMT-072",
            description = "Verify that 'create new account'  link is displayed after clicking on 'login' link")
    public void checkCreateNewAccountLink() {
        MembersPopup membersPopup = new MembersPopup();
        Assert.assertTrue(membersPopup.isLoginLinkDisplayed(), "Login link is not displayed.");

        membersPopup.clickLoginLink();
        Assert.assertTrue(membersPopup.isCreateNewAccountLinkDisplayed(), "'create new account' link is not displayed.");
    }
}
