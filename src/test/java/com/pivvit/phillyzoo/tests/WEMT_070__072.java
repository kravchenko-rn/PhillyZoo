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
        Actions.navigationActions().openPurchaseTicketsPopup();
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

    @Test(testName = "WEMT-071", dependsOnMethods = "checkCreateNewAccountLink",
            description = "Verify that user able to return back to Credit Card form after clicking on 'create new account' link")
    public void checkRedirectToCreditCardForm() {
        MembersPopup membersPopup = new MembersPopup()
                .clickCreateNewAccountLink();
        Assert.assertTrue(membersPopup.isNewAccountFormDisplayed(), "'Create new account' form is not displayed.");
    }

    @Test(testName = "WEMT-070", dependsOnMethods = "checkRedirectToCreditCardForm",
            description = "Verify that user able to login to social network (Facebook)")
    public void checkFacebookLogin() {
        MembersPopup membersPopup = new MembersPopup()
                .clickLoginLink();
        Assert.assertTrue(membersPopup.isFacebookLoginButtonDisplayed(), "Fcebook login button is not displayed.");

        membersPopup.clickFacebookLoginButton();
        Assert.assertTrue(membersPopup.isFacebookLoginPageDisplayed(), "Facebook login page is not displayed.");
    }
}
