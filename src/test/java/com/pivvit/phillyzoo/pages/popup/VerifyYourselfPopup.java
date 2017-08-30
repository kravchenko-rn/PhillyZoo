package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;

@FindBy(css = "#mainContainer>section")
public class VerifyYourselfPopup extends BasePopup {
    @FindBy(css = "[ng-click$='returnToResults()']")
    WebElement returnToResultsLink;

    @FindBy(css = "button[ng-click$='verifyIdentity()']")
    WebElement submitButton;

    @FindBy(css = "[ng-if$='selectedMembership'] .error")
    WebElement verificationError;

    @FindBy(css = ".zoo-membership__obscured-part input")
    List<WebElement> verifyMemberInputs;

    public VerifyYourselfPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Clicks return to results link
     * @return {@link PastMembershipPopup} page
     */
    public PastMembershipPopup clickReturnToResultsLink() {
        driver().switchTo().frame(contentIFrame);

        click(returnToResultsLink, "Clicking return to results link.");

        driver().switchTo().defaultContent();
        return new PastMembershipPopup();
    }

    /**
     * Clicks submit button
     * @return {@link VerifyYourselfPopup} page
     */
    public VerifyYourselfPopup clickSubmitButton() {
        driver().switchTo().frame(contentIFrame);

        click(submitButton, "Clicking submit button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Sets text into verification input fields.
     * @param verificationCharacters array which contains verification characters
     * @return  {@link VerifyYourselfPopup} page
     */
    public VerifyYourselfPopup inputVerificationCharacters(String[] verificationCharacters) {
        driver().switchTo().frame(contentIFrame);

        if (verificationCharacters.length != verifyMemberInputs.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Number of verification characters is incorrect.");
        }

        for (int i = 0; i < verifyMemberInputs.size(); i++) {
            verifyMemberInputs.get(i).sendKeys(verificationCharacters[i]);
        }

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Checks whether the verification error message is displayed.
     * The element is always present and visible on the page but
     * if there's no error, it just has no text.
     * So the check is performed by verifying if the error text is empty or not.
     * @param text string which contains text to be visible
     * @return {@code true} in case when the error text is not empty
     */
    public boolean isVerificationErrorDisplayed(String text) {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementTextVisible(verificationError, text, "Checking if the verification error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }
}
