package com.pivvit.phillyzoo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.base.BaseFEPage;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class PurchaseTicketsTab extends BaseFEPage {
    @FindBy(css = "#tabPadder>div>a:nth-of-type(1)")
    WebElement membersButton;

    public PurchaseTicketsTab() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    public MembersPopup openMembersPopup() {
        click(membersButton, "Opening members popup");

        return new MembersPopup().waitForPageLoad();
    }
}
