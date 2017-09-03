package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@FindBy(css = "#mainContainer>section")
public class TermsAndConditionsPopup extends BasePopup {
    @FindBy(css = "button[ng-click='previousPurchaseStep()']")
    WebElement backArrowButton;

    @FindBy(css = "button[ng-click='purchaseStep.acceptTerms()']")
    WebElement acceptTermsButton;

    @FindBy(css = "button.purchase-step__continue")
    WebElement continueButton;

    public TermsAndConditionsPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Clicks back arrow button
     * @return {@link WinterExperienceTicketsPopup} page
     */
    public WinterExperienceTicketsPopup clickBackArrowButton() {
        driver().switchTo().frame(contentIFrame);

        click(backArrowButton, "Clicking back arrow button.");

        driver().switchTo().defaultContent();
        return new WinterExperienceTicketsPopup();
    }

    /**
     * Clicks accept terms button
     * @return {@link WinterExperienceTickets2Popup} page
     */
    public WinterExperienceTickets2Popup clickAcceptTermsButton() {
        driver().switchTo().frame(contentIFrame);

        click(acceptTermsButton, "Clicking accept terms button.");

        driver().switchTo().defaultContent();
        return new WinterExperienceTickets2Popup();
    }

    /**
     * Clicks continue button
     * @return {@link WinterExperienceTickets2Popup} page
     */
    public WinterExperienceTickets2Popup clickContinueButton() {
        driver().switchTo().frame(contentIFrame);

        click(continueButton, "Clicking continue button.");

        driver().switchTo().defaultContent();
        return new WinterExperienceTickets2Popup();
    }
}
