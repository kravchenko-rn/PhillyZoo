package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import jnr.x86asm.Mem;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WEMT_001__009 extends BaseTest {
    SoftAssert softAssert;

    @BeforeTest
    public void init() {
        new HomePage().open();
        softAssert = new SoftAssert();
    }

    @Test(testName = "WEMT-001", description = "Verify that Member Lookup popup appear when clicking on Member button")
    public void checkMembersPopupOpens() {
        MembersPopup membersPopup = Actions.navigationActions()
                .openMembersPopup();
        Assert.assertTrue(membersPopup.isLoaded(), "Members popup is not opened.");
    }

    @Test(testName = "WEMT-006", dependsOnMethods = "checkMembersPopupOpens",
            description = "Verify that the help popup appear when hovering over question (?) icon on the right side of Member Number textbox\t")
    public void checkTooltipOpens() {
        MembersPopup membersPopup = new MembersPopup()
                .hoverQuestionMark();
        softAssert.assertTrue(membersPopup.isTooltipVisible(), "Tooltip is not displayed.");
    }

    @Test(testName = "WEMT-003, WEMT-004, WEMT-005, WEMT-009", dependsOnMethods = "checkTooltipOpens",
            description = "Verify that error message appears when incorrect customer id is entered")
    @Parameters("invalidCustomerIds")
    public void checkIncorrectCustomerId(String invalidCustomerIds) {
        MembersPopup membersPopup = new MembersPopup();
        List<String> params = new LinkedList<>(Arrays.asList(invalidCustomerIds.split(" ")));
        params.add("");
        params.forEach(param -> {

            membersPopup.inputCustomerId(param)
                    .clickSearchButton();
            softAssert.assertTrue(membersPopup.isErrorMessageDisplayed(), "Error message is not displayed.");
        });
    }

    @Test(testName = "WEMT-007", dependsOnMethods = "checkIncorrectCustomerId", alwaysRun = true,
            description = "Verify that message appears when there should no results matching with Member Number")
    @Parameters({"nonExistingCustomerId", "expectedErrorText"})
    public void checkNonExistingCustomerId(String nonExistingCustomerId, String expectedErrorText) {
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerId(nonExistingCustomerId)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        softAssert.assertTrue(membersPopup.isErrorMessageDisplayed(),
                "Error message is not displayed for non existing member.");
        softAssert.assertEquals(membersPopup.getErrorMessageText(), expectedErrorText,
                "Error text is incorrect for non existing member.");
    }

    @Test(testName = "WEMT-008", dependsOnMethods = "checkNonExistingCustomerId",
            description = "Verify that error textbox validation appear on email address textbox when clicking on search button with a wrong email address format")
    @Parameters("incorrectEmail")
    public void checkIncorrectEmail(String incorrectEmail) {
        MembersPopup membersPopup = new MembersPopup()
                .clearCustomerId()
                .inputCustomerEmail(incorrectEmail)
                .clickSearchButton();
        softAssert.assertTrue(membersPopup.isEmailValidationErrorMessageDisplayed(),
                "Email validation error is not displayed.");
    }

    @AfterTest
    public void summarize() {
        softAssert.assertAll();
    }
}
