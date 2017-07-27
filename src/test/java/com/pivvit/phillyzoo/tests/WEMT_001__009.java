package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.Assert;
import org.testng.annotations.*;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WEMT_001__009 extends BaseTest {
    @BeforeTest
    public void init() {
        new HomePage().open();
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
        Assert.assertTrue(membersPopup.isTooltipVisible(), "Tooltip is not displayed.");
    }

    @Test(testName = "WEMT-003, WEMT-004, WEMT-005, WEMT-009", dependsOnMethods = "checkTooltipOpens", alwaysRun = true,
            description = "Verify that error message appears when incorrect customer id is entered")
    @Parameters({"invalidCustomerIds", "expectedErrorText"})
    public void checkIncorrectCustomerId(String invalidCustomerIds, String errorText) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup();
        List<String> params = new LinkedList<>(Arrays.asList(invalidCustomerIds.split(" ")));
        params.add("");
        params.forEach(param -> {

            membersPopup.inputCustomerId(param)
                    .clickSearchButton();
            softAssert.assertTrue(membersPopup.isErrorMessageDisplayed(errorText), "Error message is not displayed.");
        });
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-007", dependsOnMethods = "checkIncorrectCustomerId", alwaysRun = true,
            description = "Verify that message appears when there should no results matching with Member Number")
    @Parameters({"nonExistingCustomerId", "expectedErrorText"})
    public void checkNonExistingCustomerId(String nonExistingCustomerId, String expectedErrorText) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerId(nonExistingCustomerId)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        softAssert.assertTrue(membersPopup.isErrorMessageDisplayed(expectedErrorText),
                "Error message is not displayed for non existing member.");
        softAssert.assertEquals(membersPopup.getErrorMessageText(), expectedErrorText,
                "Error text is incorrect for non existing member.");
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-008", dependsOnMethods = "checkNonExistingCustomerId", alwaysRun = true,
            description = "Verify that error textbox validation appear on email address textbox when clicking on search button with a wrong email address format")
    @Parameters("incorrectEmail")
    public void checkIncorrectEmail(String incorrectEmail) {
        MembersPopup membersPopup = new MembersPopup()
                .clearCustomerId()
                .inputCustomerEmail(incorrectEmail)
                .clickSearchButton();
        Assert.assertTrue(membersPopup.isEmailValidationErrorMessageDisplayed(),
                "Email validation error is not displayed.");
    }

    @Test(testName = "WEMT-002", dependsOnMethods = "checkIncorrectEmail", alwaysRun = true,
            description = "Verify that options for disguised information is returned when searching with valid member number")
    @Parameters("existingCustomerId")
    public void checkExistingCustomerId(String existingCustomerId) {
        checkMembersPopupOpens();
        MembersPopup membersPopup = new MembersPopup()
                .clearCustomerEmail()
                .inputCustomerId(existingCustomerId)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(membersPopup.isOptionsResultListDisplayed(), "Options for disguised information are not returned.");
    }
}
