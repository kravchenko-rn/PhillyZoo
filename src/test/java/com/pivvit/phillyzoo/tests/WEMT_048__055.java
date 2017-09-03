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

public class WEMT_048__055 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-050", description = "Verify that user is able to paginate calendar by clicking on double arrows")
    @Parameters({"email", "verificationCharacters"})
    public void checkDatesPagination(String customerEmail, String charactersSet) {
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
        WinterExperienceTickets2Popup winterExperienceTickets2Popup = new TermsAndConditionsPopup()
                .clickAcceptTermsButton();
        String firstDate = winterExperienceTickets2Popup.getFirstDisplayedBookingDate();
        winterExperienceTickets2Popup.clickNextTicketsDatesLink();
        Assert.assertNotEquals(firstDate, winterExperienceTickets2Popup.getFirstDisplayedBookingDate(),
                "Right pagination arrows don't work.");

        winterExperienceTickets2Popup.clickPreviousTicketsDatesLink();
        Assert.assertEquals(firstDate, winterExperienceTickets2Popup.getFirstDisplayedBookingDate(),
                "Left pagination arrows don't work.");
    }

    @Test(testName = "WEMT-048", dependsOnMethods = "checkDatesPagination", alwaysRun = true,
            description = "Verify that user can select a date within 3 months")
    @Parameters("numberOfMonths")
    public void checkDatesAvailableFor3Month(int numberOfMonths) {
        Assert.assertEquals(new WinterExperienceTickets2Popup().getNumberOfAvailableMonths(), numberOfMonths,
                "Number of available months is incorrect.");
    }

    @Test(testName = "WEMT-049", dependsOnMethods = "checkDatesAvailableFor3Month", alwaysRun = true,
            description = "Verify that month font color and format should changed to blue and bold after clicking")
    @Parameters("selectedMonthColor")
    public void checkFontFormatForSelectedMonth(String monthColor) {
        SoftAssert softAssert = new SoftAssert();
        new WinterExperienceTickets2Popup().validateMonthsFont(softAssert, monthColor, true);
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-054", dependsOnMethods = "checkFontFormatForSelectedMonth", alwaysRun = true,
            description = "Verify that 'Sold out' text is displayed under a date when all tickets is sold.")
    public void checkSoldOutDate() {
        Assert.assertTrue(new WinterExperienceTickets2Popup().isSoldOutDatePresent(),
                "There is no sold out date at the current view.");
    }

    @Test(testName = "WEMT-051", dependsOnMethods = "checkSoldOutDate", alwaysRun = true,
            description = "Verify that Book your time dropdown and a purchasing reminder appear after selecting a date")
    public void checkTimeDropdownAndReminder() {
        SoftAssert softAssert = new SoftAssert();
        WinterExperienceTickets2Popup wet2Popup = new WinterExperienceTickets2Popup();
        softAssert.assertFalse(wet2Popup.isTimeSelectDisplayed(), "Ticket time is displayed before the date is selected.");
        softAssert.assertFalse(wet2Popup.isReminderDisplayed(), "Purchase reminder is displayed before the date is selected.");

        wet2Popup.selectFirstAvailableDate();
        softAssert.assertTrue(wet2Popup.isTimeSelectDisplayed(), "Ticket time select is not displayed.");
        softAssert.assertTrue(wet2Popup.isReminderDisplayed(), "Purchase reminder is not displayed.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-052", dependsOnMethods = "checkTimeDropdownAndReminder", alwaysRun = true,
            description = "Verify that 'Book your time' dropdown consist values from working hours (weekdays, weekends, holidays) interval with 20 min. increment")
    @Parameters({"startTime", "endTime", "interval"})
    public void checkBookingTimeBoundsAndInterval(String startTime, String endTime, int interval) {
        SoftAssert softAssert = new SoftAssert();
        WinterExperienceTickets2Popup wet2Popup = new WinterExperienceTickets2Popup();

        String bookingStartTime = wet2Popup.getStartBookingTime();
        String bookingEndTime = wet2Popup.getEndBookingTime();
        wet2Popup.clickTicketTimeSelect();

        softAssert.assertEquals(bookingStartTime, startTime, "Booking start time is incorrect.");
        softAssert.assertEquals(bookingEndTime, endTime, "Booking end time is incorrect");
        wet2Popup.validateBookingTimeInterval(softAssert, interval);
        wet2Popup.clickTicketTimeSelect();
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-053", dependsOnMethods = "checkBookingTimeBoundsAndInterval", alwaysRun = true,
            description = "Verify that 'Continue' button becomes enabled after selecting the time from Book hour Time dropdown")
    public void checkContinueButton() {
        SoftAssert softAssert = new SoftAssert();
        WinterExperienceTickets2Popup wet2Popup = new WinterExperienceTickets2Popup();

        softAssert.assertFalse(wet2Popup.isContinueButtonEnabled(),
                "Continue button is enabled with no booking time selected.");

        String time = wet2Popup.getStartBookingTime();
        wet2Popup.selectTimeForTheTicket(time);
        softAssert.assertTrue(wet2Popup.isContinueButtonEnabled(),
                "Continue button is not enabled after the booking time was selected.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-055", dependsOnMethods = "checkContinueButton",
            description = "Verify that 'Checkout' popup is displayed after clicking on red colored 'Continue' button")
    public void checkRedirectToCheckoutPage() {
        CheckoutPopup checkoutPopup = new WinterExperienceTickets2Popup()
                .clickContinueButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(checkoutPopup.isTitleDisplayed(), "Checkout popup is not displayed.");
    }
}
