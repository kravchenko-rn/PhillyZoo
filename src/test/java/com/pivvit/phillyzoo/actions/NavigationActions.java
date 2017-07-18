package com.pivvit.phillyzoo.actions;

import com.pivvit.phillyzoo.pages.MembersPopup;
import com.pivvit.phillyzoo.pages.PurchaseTicketsTab;

public class NavigationActions {

    public MembersPopup openMembersPopup() {
        return new PurchaseTicketsTab().openMembersPopup();
    }
}
