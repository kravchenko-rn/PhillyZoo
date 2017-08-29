package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pivvit.base.BaseFEPage;
import pivvit.utils.Reporter;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.concurrent.TimeUnit;

@FindBy(css = "#mainContainer>section")
public class BasePopup extends BaseFEPage {
    @FindBy(id = "pivvitContent")
    WebElement contentIFrame;

    @FindBy(css = ".campaign-offering-details-container")
    WebElement container;

    @FindBy(css = ".pv-loading")
    WebElement loadingIndicator;

    public BasePopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Waits till loading indicator goes away after the search button was clicked
     */
    protected BasePopup waitTillLoadingIndicatorDisappears() {
        driver().switchTo().frame(contentIFrame);

        waitForInvisibility(loadingIndicator, "Waiting for loading indicator to go away.");
        sleep(1);

        driver().switchTo().defaultContent();
        return new BasePopup();
    }

    /**
     * Checks whether the page is loaded by presence of the main element
     * @return {@code true} in case when the main element is present
     */
    public boolean isLoaded() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementPresent(container);

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Sets text into an input field
     * @param inputElement an element to set text to
     * @param text string with text to set
     */
    protected void inputText(WebElement inputElement, String text) {
        driver().switchTo().frame(contentIFrame);

        Reporter.log("Clearing customer input field.");
        inputElement.clear();
        Reporter.log("Typing text into input field: " + text);
        inputElement.sendKeys(text);

        driver().switchTo().defaultContent();
    }

    /**
     * Checks whether the validation error is displayed by text presence,
     * because the element itself is always visible but has no text.
     * @param element for which the validation error should be displayed
     * @return {@code true} in case when the validation error is displayed
     */
    protected boolean isValidationErrorDisplayed(WebElement element, String errorText) {
        driver().switchTo().frame(contentIFrame);

        WebElement validationError = element.findElement(By.xpath("following-sibling::span"));
        boolean result = isElementTextVisible(validationError, errorText, "Checking whether the first name validation error is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Validates the presence of the text in the element.
     * @param element an element which should contain the text
     * @param elementText string which contains the text which should be present
     * @param message string which contains a message for the log
     * @return {@code true} in case when the text is present in the element
     */
    protected boolean isElementTextVisible(WebElement element, String elementText, String message) {
        Reporter.log(message);
        boolean elementVisible = false;
        try {
            driver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            elementVisible = new WebDriverWait(driver(), 5)
                    .until(ExpectedConditions.textToBePresentInElement(element, elementText));
        } catch (WebDriverException e) {
            Reporter.log(String.format("Element is not displayed after %d seconds.", 5));
        } finally {
            driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }
        return elementVisible;
    }
}
