package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

public class WEMT_048__055 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-050", description = "Verify that user is able to paginate calendar by clicking on double arrows")
    @Parameters({"email", "verificationCharacters"})
    public void checkDatesPagination(String customerEmail, String charactersSet) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "))
                .selectTicketsAmount("1")
                .clickContinuePurchaseButton()
                .clickAcceptTermsButton();
        String firstDate = membersPopup.getFirstDisplayedBookingDate();
        membersPopup.clickNextTicketsDatesLink();
        Assert.assertNotEquals(firstDate, membersPopup.getFirstDisplayedBookingDate(),
                "Right pagination arrows don't work.");

        membersPopup.clickPreviousTicketsDatesLink();
        Assert.assertEquals(firstDate, membersPopup.getFirstDisplayedBookingDate(),
                "Left pagination arrows don't work.");
    }

    @Test(testName = "WEMT-048", dependsOnMethods = "checkDatesPagination", alwaysRun = true,
            description = "Verify that user can select a date within 3 months")
    @Parameters("numberOfMonths")
    public void checkDatesAvailableFor3Month(int numberOfMonths) {
        Assert.assertEquals(new MembersPopup().getNumberOfAvailableMonths(), numberOfMonths,
                "Number of available months is incorrect.");
    }

    @Test(testName = "WEMT-049", dependsOnMethods = "checkDatesAvailableFor3Month", alwaysRun = true,
            description = "Verify that month font color and format should changed to blue and bold after clicking")
    @Parameters("selectedMonthColor")
    public void checkFontFormatForSelectedMonth(String monthColor) {
        SoftAssert softAssert = new SoftAssert();
        new MembersPopup().validateMonthsFont(softAssert, monthColor, true);
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-051", dependsOnMethods = "checkFontFormatForSelectedMonth", alwaysRun = true,
            description = "Verify that Book your time dropdown and a purchasing reminder appear after selecting a date")
    public void checkTimeDropdownAndReminder() {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup();
        softAssert.assertFalse(membersPopup.isTicketTimeSelectDisplayed(), "Ticket time is displayed before the date is selected.");
        softAssert.assertFalse(membersPopup.isPurchaseReminderDisplayed(), "Purchase reminder is displayed before the date is selected.");

        membersPopup.selectFirstAvailableDate();
        softAssert.assertTrue(membersPopup.isTicketTimeSelectDisplayed(), "Ticket time select is not displayed.");
        softAssert.assertTrue(membersPopup.isPurchaseReminderDisplayed(), "Purchase reminder is not displayed.");
        softAssert.assertAll();
    }
}
