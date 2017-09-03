package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

public class WEMT_056__061 extends BaseTest {
    @BeforeTest
    @Parameters({"email", "verificationCharacters"})
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
                .waitTillLoadingIndicatorDisappears();
    }

    // TODO: 07.08.2017 automate WEMT-056 when there will be all the valid data
    @Test(testName = "WEMT-056", enabled = false,
            description = "Verify that 'Checkout' is success after inputing all required fields by valid data")
    public void checkSuccessWithValidData() {

    }

    @Test(testName = "WEMT-057, WEMT-059, WEMT-061",
            description = "Verify that Total is recalculated after changing quantity tickets (add non-member)"
                    + "Verify that the user can re-select ticket quantity after removing all items on the checkout"
                    + "Verify that Quantity is displayed on 'Checkout' pop-up near datetime.")
    @Parameters("pageTitle")
    public void checkTotalSumAndTicketQuantity(String pageTitle) {
        SoftAssert softAssert = new SoftAssert();
        CheckoutPopup checkoutPopup = new CheckoutPopup()
                .waitForLoad();
        String firstTotalPrice = checkoutPopup.getTotalPrice();
        checkoutPopup.removeFirstTicket();
        Assert.assertEquals(new BasePopup().getPageTitleText(), pageTitle,
                "Was not redirected to 'Winter Experience Tickets' page.");

        new WinterExperienceTicketsPopup()
                .selectMemberTicketsAmount("2")
                .clickContinueButton();
        WinterExperienceTickets2Popup wet2Popup = new TermsAndConditionsPopup()
                .clickContinueButton()
                .selectFirstAvailableDate();
        String time = wet2Popup.getStartBookingTime();
        checkoutPopup = wet2Popup.selectTimeForTheTicket(time)
                .clickContinueButton()
                .waitTillLoadingIndicatorDisappears();
        softAssert.assertNotEquals(checkoutPopup.getTotalPrice(), firstTotalPrice, "Total price was not recalculated.");
        softAssert.assertTrue(checkoutPopup.isFirstTicketQuantityDisplayed(), "Ticket quantity is no displayed.");
        softAssert.assertTrue(checkoutPopup.getFirstTicketQuantity().equals("(2)"), "Ticket quantity was not increased.");

        softAssert.assertAll();
    }

    @Test(testName = "WEMT-058, WEMT-060", dependsOnMethods = "checkTotalSumAndTicketQuantity",
            description = "Verify that the total amount is recalculated after removing a member or a non-member"
                    + "Verify that Non-member is displayed on 'Checkout' pop-up")
    public void checkNonMemberTicketAndTotalPrice() {
        SoftAssert softAssert = new SoftAssert();
        new CheckoutPopup()
                .waitForLoad()
                .removeFirstTicket();
        new WinterExperienceTicketsPopup()
                .selectMemberTicketsAmount("1")
                .clickAddNonMemberTicketsLink()
                .selectNonMemberTicketsAmount("1")
                .clickContinueButton();
        WinterExperienceTickets2Popup wet2Popup = new TermsAndConditionsPopup()
                .clickContinueButton()
                .selectFirstAvailableDate();
        String time = wet2Popup.getStartBookingTime();
        CheckoutPopup checkoutPopup = wet2Popup.selectTimeForTheTicket(time)
                .clickContinueButton()
                .waitTillLoadingIndicatorDisappears()
                .waitForLoad();
        softAssert.assertTrue(checkoutPopup.isNonMemberTicketPresent(), "Non member ticket is not displayed.");
        String firstTotalPrice = checkoutPopup.getTotalPrice();
        checkoutPopup.removeFirstTicket();
        softAssert.assertNotEquals(checkoutPopup.getTotalPrice(), firstTotalPrice, "Total price was not recalculated.");

        softAssert.assertAll();
    }
}
