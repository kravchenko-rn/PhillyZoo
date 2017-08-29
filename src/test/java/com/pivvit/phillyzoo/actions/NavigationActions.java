package com.pivvit.phillyzoo.actions;

import com.pivvit.phillyzoo.pages.MembersPopup;
import com.pivvit.phillyzoo.pages.popup.PurchaseTicketsPopup;
import com.pivvit.phillyzoo.pages.PurchaseTicketsTab;

public class NavigationActions {

    /**
     * Navigates to members popup
     * @return {@link MembersPopup} page
     */
    public PurchaseTicketsPopup openPurchaseTicketsPopup() {
        return new PurchaseTicketsTab().openPurchaseTicketsPopup();
    }
}
