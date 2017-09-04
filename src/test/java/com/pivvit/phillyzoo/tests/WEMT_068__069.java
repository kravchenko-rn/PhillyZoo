package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

public class WEMT_068__069 extends BaseTest {
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
                .waitTillLoadingIndicatorDisappears();
    }

    @Test(testName = "WEMT-068",
            description = "Verify that 'x' (near date and time) return user to Winter Experience Tickets pop-up (when only one member should chosen)")
    @Parameters("pageTitle")
    public void checkDeleteButtonOneMember(String pageTitle) {
        CheckoutPopup checkoutPopup = new CheckoutPopup();
        Assert.assertEquals(checkoutPopup.getNumberOfTicketItems(), 1, "There are more then one ticket item.");

        checkoutPopup.removeFirstTicket();
        Assert.assertEquals(new BasePopup().getPageTitleText(), pageTitle,
                "Was not redirected to 'Winter' Experience Tickets pop-up");
    }

    @Test(testName = "WEMT-069", dependsOnMethods = "checkDeleteButtonOneMember",
            description = "Verify that 'x' (near date and time) have an able to remove members.")
    public void checkAbilityToRemoveMembers() {
        new WinterExperienceTicketsPopup()
                .selectMemberTicketsAmount("1")
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
        Assert.assertEquals(checkoutPopup.getNumberOfTicketItems(), 2, "Incorrect number of ticket items.");

        checkoutPopup.removeSecondTicket();
        Assert.assertEquals(checkoutPopup.getNumberOfTicketItems(), 1, "Non member ticket was not removed.");
    }
}
