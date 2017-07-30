package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pivvit.utils.SoftAssert;

import java.util.List;

public class WEMT_030__032 {
    @BeforeTest
    public void init() {
        new HomePage().open();
        Actions.navigationActions().openMembersPopup();
    }

    @Test(testName = "WEMT-031",
            description = "Verify that the search result items changed into blue font color and bold text format when hovering on each result item")
    @Parameters({"email", "resultItemHoveredColor"})
    public void checkResultItemFontChange(String customerEmail, String resultItemColor) {
        SoftAssert softAssert = new SoftAssert();
        MembersPopup membersPopup = new MembersPopup()
                .inputCustomerEmail(customerEmail)
                .clickSearchButton()
                .waitTillLoadingIndicatorDisappears();
        Assert.assertTrue(membersPopup.isOptionsResultListDisplayed(), "Search result list is not displayed or is empty.");

        membersPopup.hoverSearchResultItem(0);
        validateResultItemFont(softAssert, membersPopup.getLookupResultItemParts(0), resultItemColor, true);
        softAssert.assertAll();
    }

    @Test(testName = "WEMT-030", dependsOnMethods = "checkResultItemFontChange", alwaysRun = true,
            description = "Verify that user is able get back on previous step by clicking on 'change your search' link")
    public void checkChangeSearchLink() {
        MembersPopup membersPopup = new MembersPopup()
                .clickChangeSearchLink();
        Assert.assertTrue(membersPopup.isEmailInputDisplayed(), "Did not get back to the previous step.");
    }

    public void validateResultItemFont(SoftAssert softAssert, List<WebElement> itemParts, String color, boolean validateBold) {
        itemParts.forEach(itemPart -> {
            String itemPartColor = itemPart.getCssValue("color");
            int weight = Integer.parseInt(itemPart.getCssValue("font-weight"));
            softAssert.assertEquals(itemPartColor, color, "Hovered result item part '" + itemPart.getText() + "' is not blue.");
            if (validateBold) {
                softAssert.assertTrue(weight >= 700, "Hovered result item part '" + itemPart.getText() + "' is not bold."
                        + "It's font-weight value is " + weight + ".");
            }
        });
    }
}
