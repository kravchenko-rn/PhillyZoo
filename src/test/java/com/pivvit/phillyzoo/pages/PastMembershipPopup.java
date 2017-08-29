package com.pivvit.phillyzoo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
