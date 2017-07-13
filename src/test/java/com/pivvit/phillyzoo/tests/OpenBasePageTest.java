package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.pages.HomePage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pivvit.base.BaseTest;

@Test
public class OpenBasePageTest extends BaseTest {
    @BeforeTest
    public void prepare() {
        new HomePage().open();
    }

    @Test(description = "Verify that base page is loaded")
    public void checkPageLoad() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(new HomePage().getBtnJoinText(), "JOIN", "JOIN button text is incorrect");

        softAssert.assertAll();
    }

    @Test(description = "Verify that description pops up", dependsOnMethods = "checkPageLoad")
    public void checkDescriptionPopsUp() {
        HomePage homePage = new HomePage();
        SoftAssert softAssert = new SoftAssert();

        for (int i = 0; i < homePage.getNumberOfTiles(); i++) {
            homePage.hoverLogo().hoverTile(i);
            softAssert.assertTrue(homePage.isTileDescriptionDisplayed(i), "Title description#" + (i + 1) + " is not displayed");
        }

        softAssert.assertAll();
    }
}
