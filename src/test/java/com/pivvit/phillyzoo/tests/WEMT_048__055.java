package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

import java.util.Locale;

public class WEMT_048__055 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-048", description = "Verify that user can select a date within 3 months")
    @Parameters({"email", "verificationCharacters"})
    public void checkDatesAvailableFor3Month(String customerEmail, String charactersSet) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .clickLookupResultItem(0)
                .inputVerificationCharacters(charactersSet.split(" "))
                .selectTicketsAmount("1")
                .clickContinuePurchaseButton()
                .clickAcceptTermsButton();

        String startDate = membersPopup.getFirstDisplayedBookingDate();
        String lastDate;
        do {
            lastDate = membersPopup.getLastDisplayedBookingDate();
            membersPopup.clickNextTicketsDatesLink();
        } while (!startDate.equals(membersPopup.getFirstDisplayedBookingDate()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("EEE, dd MMM").withLocale(Locale.ENGLISH);
        DateTime dateStart = dateTimeFormatter.parseDateTime(startDate);
        DateTime dateLast = dateTimeFormatter.parseDateTime(lastDate);
        long numberOfDays = new Duration(dateStart, dateLast).getStandardDays();
        Assert.assertTrue(numberOfDays >= 89 && numberOfDays <= 92,
                "Dates are not available within 3 month. Number of available days is " + numberOfDays + ".");
    }

    @Test(testName = "WEMT-049", dependsOnMethods = "checkDatesAvailableFor3Month", alwaysRun = true,
            description = "Verify that month font color and format should changed to blue and bold after clicking")
    @Parameters("selectedMonthColor")
    public void checkFontFormatForSelectedMonth(String monthColor) {
        SoftAssert softAssert = new SoftAssert();
        new MembersPopup().validateMonthsFont(softAssert, monthColor, true);
        softAssert.assertAll();
    }
}
