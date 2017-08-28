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
public class WEMT_068__069 extends BaseTest {
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

    @Test(testName = "WEMT-068",
            description = "Verify that 'x' (near date and time) return user to Winter Experience Tickets pop-up (when only one member should chosen)")
    @Parameters("pageTitle")
    public void checkDeleteButtonOneMember(String pageTitle) {
        MembersPopup membersPopup = new MembersPopup();
        Assert.assertEquals(membersPopup.getNumberOfTicketItems(), 1, "There are more thn one ticket item.");

        membersPopup.removeFirstTicket();
        Assert.assertEquals(membersPopup.getPageTitleText(), pageTitle, "Was not redirected to 'Winter' Experience Tickets pop-up");
    }

    @Test(testName = "WEMT-069", dependsOnMethods = "checkDeleteButtonOneMember",
            description = "Verify that 'x' (near date and time) have an able to remove members.")
    public void checkAbilityToRemoveMembers() {
        MembersPopup membersPopup = new MembersPopup()
                .selectTicketsAmount("1")
                .clickAddNonMemberTicketsLink()
                .selectNonMemberTicketsAmount("1")
                .clickContinuePurchaseButton()
                .clickContinuePurchaseButton()
                .selectFirstAvailableDate();
        String time = membersPopup.getStartBookingTime();
        membersPopup.selectTimeForTheTicket(time)
                .clickContinuePurchaseButton()
                .waitTillLoadingIndicatorDisappears()
                .waitForCheckoutLoad();
        Assert.assertEquals(membersPopup.getNumberOfTicketItems(), 2, "Incorrect number of ticket items.");

        membersPopup.removeSecondTicket();
        Assert.assertEquals(membersPopup.getNumberOfTicketItems(), 1, "Non member ticket was not removed.");
    }
}
