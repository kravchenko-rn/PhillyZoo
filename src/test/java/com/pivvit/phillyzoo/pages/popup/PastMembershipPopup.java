package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;

@FindBy(css = "#mainContainer>section")
public class PastMembershipPopup extends BasePopup {
    @FindBy(css = ".zoo-membership__lookup-result")
    List<WebElement> lookupResults;

    @FindBy(css = "[ng-model$='zipFilter']")
    WebElement zipCodeFilterSelect;

    @FindBy(css = "[ng-model$='phoneFilter']")
    WebElement phoneFilterInput;

    public PastMembershipPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Retrieves phone filter input text.
     * @return string which contains phone filter input text
     */
    public String getPhoneFilterInputText() {
        driver().switchTo().frame(contentIFrame);

        String result = phoneFilterInput.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Selects zip code from dropdown filter.
     * @param zipCode string which contains zip code
     * @return {@link PastMembershipPopup} page
     */
    public PastMembershipPopup selectZipCodeFromFilter(String zipCode) {
        driver().switchTo().frame(contentIFrame);

        new Select(zipCodeFilterSelect).selectByVisibleText(zipCode);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Deselects filter by zip code value.
     * @return {@link PastMembershipPopup} page
     */
    public PastMembershipPopup deselectZipCodeFilter() {
        driver().switchTo().frame(contentIFrame);

        new Select(zipCodeFilterSelect).selectByValue("");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Sets text into phone filter input field.
     * @param phoneNumber string which contains phone number
     * @return {@link PastMembershipPopup} page
     */
    public PastMembershipPopup inputCustomerPhoneNumber(String phoneNumber) {
        inputText(phoneFilterInput, phoneNumber);
        return this;
    }

    /**
     * Submits phone number entered into the phone filter input field by pressing ENTER key
     * @return {@link PastMembershipPopup} page
     */
    public PastMembershipPopup submitPhoneNumber() {
        driver().switchTo().frame(contentIFrame);

        phoneFilterInput.sendKeys(Keys.ENTER);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clears customer phone filter input field
     * @return {@link PastMembershipPopup} page
     */
    public PastMembershipPopup clearPhoneFilterInput() {
        driver().switchTo().frame(contentIFrame);

        phoneFilterInput.clear();

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Checks whether the options for disguised information are returned
     * @return {@code true} in case when there's at least one option
     */
    public boolean isOptionsResultListDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = !lookupResults.isEmpty();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether zip code filter is displayed
     * @return {@code true} in case when zip code filter is displayed
     */
    public boolean isZipCodeFilterDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(zipCodeFilterSelect,
                "Checking whether the zip code filter is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether phone filter is displayed
     * @return {@code true} in case when phone filter is displayed
     */
    public boolean isPhoneFilterDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(phoneFilterInput,
                "Checking whether the phone filter is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }
}
