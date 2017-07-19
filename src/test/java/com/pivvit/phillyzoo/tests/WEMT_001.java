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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WEMT_001 extends BaseTest {
    SoftAssert softAssert;

    @BeforeTest
    public void init() {
        new HomePage().open();
        softAssert = new SoftAssert();
    }

    @Test
    @Parameters({"invalidCustomerIds", "nonExistingCustomerId", "expectedErrorText"})
    public void checkMembersPopup(String invalidCustomerIds, String nonExistingCustomerId, String expectedErrorText) {
        // WEMT-001
        MembersPopup membersPopup = Actions.navigationActions()
                .openMembersPopup();
        Assert.assertTrue(membersPopup.isLoaded(), "Members popup is not opened.");

        // WEMT-006
        membersPopup.hoverQuestionMark();
        softAssert.assertTrue(membersPopup.isTooltipVisible(), "Tooltip is not displayed.");

        // WEMT-003, WEMT-004, WEMT-005
        List<String> params = new LinkedList<>(Arrays.asList(invalidCustomerIds.split(" ")));
        params.forEach(param -> verifyIncorrectId(membersPopup, param));

        // WEMT-007
        membersPopup.inputCustomerId(nonExistingCustomerId)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        softAssert.assertTrue(membersPopup.isErrorMessageDisplayed(),
                "Error message is not displayed for non existing member.");
        softAssert.assertEquals(membersPopup.getErrorMessageText(), expectedErrorText,
                "Error text is incorrect for non existing member.");

        softAssert.assertAll();
    }

    private void verifyIncorrectId(MembersPopup membersPopup, String customerId) {
        membersPopup.inputCustomerId(customerId)
                .clickSearchButton();
        softAssert.assertTrue(membersPopup.isErrorMessageDisplayed(), "Error message is not displayed.");
    }
}
