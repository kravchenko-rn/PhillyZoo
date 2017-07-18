package com.pivvit.phillyzoo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.base.BaseFEPage;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@FindBy(css = "#mainContainer>section")
public class MembersPopup extends BaseFEPage {
    @FindBy(id = "pivvitContent")
    WebElement contentIframe;

    @FindBy(css = ".campaign-offering-details-container")
    WebElement container;

    @FindBy(css = "button[ng-click='purchaseStep.search()']")
    WebElement searchButton;

    public MembersPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    public MembersPopup waitForPageLoad() {
        waitForLoad(this);
        driver().switchTo().frame(contentIframe);
        //WebElement elem = driver().findElement(By.cssSelector(".campaign-offering-details-container"));
        waitForVisibility(container, "Waiting for popup to load");
        driver().switchTo().defaultContent();
        return this;
    }

    public boolean isLoaded() {
        driver().switchTo().frame(contentIframe);
        boolean result = isElementPresent(container);
        driver().switchTo().defaultContent();
        return result;
    }
}
