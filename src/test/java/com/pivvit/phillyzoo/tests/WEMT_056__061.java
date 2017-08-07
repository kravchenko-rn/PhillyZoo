package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

public class WEMT_056__061 extends BaseTest {
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
                .waitTillLoadingIndicatorDisappears();
    }

    // TODO: 07.08.2017 automate WEMT-056 when there will be all the valid data
    @Test(testName = "WEMT-056", enabled = false,
            description = "Verify that 'Checkout' is success after inputing all required fields by valid data")
    public void checkSuccessWithValidData() {

    }

    @Test(testName = "WEMT-057", description = "Verify that Total is recalculated after changing quantity tickets (add non-member)")
    @Parameters("pageTitle")
    public void checkTotalRecalculatedOnAddingNonMember(String pageTitle) {
        MembersPopup membersPopup = new MembersPopup()
                .waitForCheckoutLoad();
        String firstTotalPrice = membersPopup.getTotalPrice();
        membersPopup.removeFirstTicket();
        Assert.assertEquals(membersPopup.getPageTitleText(), pageTitle, "Was not redirected to 'Winter Experience Tickets' page.");

        membersPopup.selectTicketsAmount("2")
                .clickContinuePurchaseButton()
                .clickContinuePurchaseButton()
                .selectFirstAvailableDate();
        String time = membersPopup.getStartBookingTime();
        membersPopup.selectTimeForTheTicket(time)
                .clickContinuePurchaseButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertNotEquals(membersPopup.getTotalPrice(), firstTotalPrice, "Total price was not recalculated.");
    }

    @Test(testName = "WEMT-058", dependsOnMethods = "checkTotalRecalculatedOnAddingNonMember",
            description = "Verify that the total amount is recalculated after removing a member or a non-member")
    public void checkTotalRecalculatedOnRemovingATicket() {
        MembersPopup membersPopup = new MembersPopup()
                .waitForCheckoutLoad()
                .removeFirstTicket()
                .selectTicketsAmount("1")
                .clickAddNonMemberTicketsLink()
                .selectNonMemberTicketsAmount("1")
                .clickContinuePurchaseButton()
                .clickContinuePurchaseButton()
                .selectFirstAvailableDate();
        String time = membersPopup.getStartBookingTime();
        membersPopup = membersPopup.selectTimeForTheTicket(time)
                .clickContinuePurchaseButton()
                .waitTillLoadingIndicatorDisappears()
                .waitForCheckoutLoad();
        String firstTotalPrice = membersPopup.getTotalPrice();
        membersPopup.removeFirstTicket();
        Assert.assertNotEquals(membersPopup.getTotalPrice(), firstTotalPrice, "Total price was not recalculated.");
    }
}
