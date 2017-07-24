package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

public class WEMT_020__024 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-023", description = "Verify that error text validation appear on the required text boxes when clicking submit button with all text boxes left blank")
    @Parameters({"incompletePhoneNumber", "customerLastName"})
    public void checkUserInformationBlank(String incompletePhoneNumber, String customerLastName) {
        SoftAssert softAssert = new SoftAssert();

        MembersPopup membersPopup = new MembersPopup()
                .clickAlternateFieldsLink()
                .inputCustomerLastName(customerLastName)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears()
                .inputCustomerPhoneNumber(incompletePhoneNumber)
                .submitPhoneNumber()
                .clickSubmitPurchaseButton();

        softAssert.assertTrue(membersPopup.isUserFormFirstNameErrorDisplayed(),
                "User form first name validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isUserFormLastNameErrorDisplayed(),
                "User form last name validation error is not displayed.");
        softAssert.assertTrue(membersPopup.isUserFormEmailErrorDisplayed(),
                "User form email validation error is not displayed.");
        softAssert.assertAll();
    }
}
