package com.pivvit.phillyzoo.tests;

import com.pivvit.phillyzoo.actions.Actions;
import com.pivvit.phillyzoo.pages.HomePage;
import com.pivvit.phillyzoo.pages.MembersPopup;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pivvit.base.BaseTest;
import pivvit.utils.SoftAssert;

public class WEMT_001 extends BaseTest {
    SoftAssert softAssert;

    @BeforeTest
    public void init() {
        new HomePage().open();
        softAssert = new SoftAssert();
    }

    @Test
    public void checkMembersPopup() {
        MembersPopup membersPopup = Actions.navigationActions()
                .openMembersPopup();
        softAssert.assertTrue(membersPopup.isLoaded(), "Members popup was not loaded");

        softAssert.assertAll();
    }
}
