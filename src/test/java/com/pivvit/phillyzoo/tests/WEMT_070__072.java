package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

public class WEMT_070__072 extends BaseTest {
    @BeforeTest
    @Parameters({"email", "verificationCharacters"})
    @SuppressWarnings("all")
    public void init(String customerEmail, String charactersSet) {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
        new PurchaseTicketsPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        new PastMembershipPopup()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "));
        new WinterExperienceTicketsPopup()
                .selectMemberTicketsAmount("1")
                .clickContinueButton();
        WinterExperienceTickets2Popup wet2Popup = new TermsAndConditionsPopup()
                .clickAcceptTermsButton()
                .selectFirstAvailableDate();
        String time = wet2Popup.getStartBookingTime();
        wet2Popup.selectTimeForTheTicket(time)
                .clickContinueButton()
                .waitTillLoadingIndicatorDisappears()
                .waitForLoad();
    }

    @Test(testName = "WEMT-072",
            description = "Verify that 'create new account'  link is displayed after clicking on 'login' link")
    public void checkCreateNewAccountLink() {
        CheckoutPopup checkoutPopup = new CheckoutPopup();
        Assert.assertTrue(checkoutPopup.isLoginLinkDisplayed(), "Login link is not displayed.");

        checkoutPopup.clickLoginLink();
        Assert.assertTrue(checkoutPopup.isCreateNewAccountLinkDisplayed(), "'create new account' link is not displayed.");
    }

    @Test(testName = "WEMT-071", dependsOnMethods = "checkCreateNewAccountLink",
            description = "Verify that user able to return back to Credit Card form after clicking on 'create new account' link")
    public void checkRedirectToCreditCardForm() {
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .clickCreateNewAccountLink();
        Assert.assertTrue(checkoutPopup.isNewAccountFormDisplayed(), "'Create new account' form is not displayed.");
    }

    @Test(testName = "WEMT-070", dependsOnMethods = "checkRedirectToCreditCardForm",
            description = "Verify that user able to login to social network (Facebook)")
    public void checkFacebookLogin() {
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .clickLoginLink();
        Assert.assertTrue(checkoutPopup.isFacebookLoginButtonDisplayed(), "Facebook login button is not displayed.");

        checkoutPopup.clickFacebookLoginButton();
        Assert.assertTrue(checkoutPopup.isFacebookLoginPageDisplayed(), "Facebook login page is not displayed.");
    }
}
