package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.popup.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;

public class WEMT_044__047 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openPurchaseTicketsPopup();
    }

    @Test(testName = "WEMT-044", description = "Verify that user is redirected to 'Winter' Terms and Conditions after clicking Continue button with member  ticket quantity greater than zero")
    @Parameters({"email", "verificationCharacters", "pageTitle"})
    public void checkRedirectForMemberTickets(String customerEmail, String charactersSet, String pageTitle) {
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
        Assert.assertEquals(new TermsAndConditionsPopup().getPageTitleText(), pageTitle,
                "Was not redirected to 'Winter' Terms and Conditions page.");
    }

    @Test(testName = "WEMT-046", dependsOnMethods = "checkRedirectForMemberTickets",
            description = "Verify that user is able get back on previous step by clicking on back arrow link")
    @Parameters("prevPageTitle")
    public void checkBackButton(String prevPageTitle) {
        new TermsAndConditionsPopup()
                .clickBackArrowButton();
        Assert.assertEquals(new BasePopup().getPageTitleText(), prevPageTitle,
                "Was not redirected back to Winter Experience Tickets page.");
    }

    @Test(testName = "WEMT-045", dependsOnMethods = "checkBackButton",
            description = "Verify that user is redirected to 'Winter' Terms and Conditions after clicking Continue button with non-member ticket quantity greater than zero")
    @Parameters("pageTitle")
    public void checkRedirectForNonMemberTickets(String pageTitle) {
        new WinterExperienceTicketsPopup()
                .selectMemberTicketsAmount("0")
                .clickAddNonMemberTicketsLink()
                .selectNonMemberTicketsAmount("1")
                .clickContinueButton();
        Assert.assertEquals(new BasePopup().getPageTitleText(), pageTitle,
                "Was not redirected to 'Winter' Terms and Conditions page.");
    }

    @Test(testName = "WEMT-047", dependsOnMethods = "checkRedirectForNonMemberTickets",
            description = "Verify that 'Winter' Experience Tickets popup appear after clicking 'Accept Terms' button")
    @Parameters("nextPageTitle")
    public void checkAcceptTermsButton(String pageTitle) {
        new TermsAndConditionsPopup()
                .clickAcceptTermsButton();
        Assert.assertEquals(new BasePopup().getPageTitleText(), pageTitle,
                "Was not redirected to 'Winter' Experience Tickets page.");
    }
}
