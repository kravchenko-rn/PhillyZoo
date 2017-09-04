package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import pivvit.properties.Properties;
import pivvit.properties.PropertiesNames;
import pivvit.utils.SoftAssert;
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

    @FindBy(css = "[ng-click$='resetSearch()']")
    WebElement changeSearchLink;

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
     * Clicks change search link
     * @return {@link PurchaseTicketsPopup} page
     */
    public PurchaseTicketsPopup clickChangeSearchLink() {
        driver().switchTo().frame(contentIFrame);

        click(changeSearchLink, "Clicking change search link.");

        driver().switchTo().defaultContent();
        return new PurchaseTicketsPopup();
    }

    /**
     * Clicks the specified lookup result item.
     * @param itemIndex index of the item to click
     * @return  {@link VerifyYourselfPopup} page
     */
    public VerifyYourselfPopup clickLookupResultItem(int itemIndex) {
        driver().switchTo().frame(contentIFrame);

        if (itemIndex >= lookupResults.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Element index exceeds the number of found elements.");
        }
        click(lookupResults.get(itemIndex), "Clicking lookup result item #" + itemIndex);

        driver().switchTo().defaultContent();
        return new VerifyYourselfPopup();
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
     * Hovers a search result item by index.
     * @param index index of a search result item
     * @return {@link PastMembershipPopup} page
     */
    public PastMembershipPopup hoverSearchResultItem(int index) {
        driver().switchTo().frame(contentIFrame);

        if (index >= lookupResults.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Element index exceeds the number of found elements.");
        }

        WebElement searchResultItem = lookupResults.get(index);
        hover(searchResultItem, "Hovering search result item.");

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

    /**
     * Validates the look of the lookup result item.
     * @param softAssert {@link SoftAssert} assertion object
     * @param index index of the element
     * @param color expected color
     * @param validateBold {@code true} in case when we need to check if the text is bold
     */
    public void validateResultItemFont(SoftAssert softAssert, int index, String color, boolean validateBold) {
        List<WebElement> itemParts = getLookupResultItemParts(index);

        driver().switchTo().frame(contentIFrame);
        itemParts.forEach(itemPart -> {
            String itemPartColor = itemPart.getCssValue("color");
            String[] itemPartColorValues = getColorValues(itemPartColor);
            String[] expectedColorValues = getColorValues(color);

            if (Properties.getBrowser(PropertiesNames.BROWSER).equals(BrowserType.FIREFOX)) {
                int weight = Integer.parseInt(itemPart.getCssValue("font-weight"));
                if (validateBold) {
                    softAssert.assertTrue(weight >= 700, "Hovered result item part '" + itemPart.getText() + "' is not bold."
                            + "It's font-weight value is " + weight + ".");
                }
            }

            if (Properties.getBrowser(PropertiesNames.BROWSER).equals(BrowserType.CHROME)) {
                String weight = itemPart.getCssValue("font-weight");
                if (validateBold) {
                    softAssert.assertEquals(weight, "bold",
                            "Hovered result item part '" + itemPart.getText() + "' is not bold.");
                }
            }
            softAssert.assertTrue(itemPartColorValues[0].equals(expectedColorValues[0])
                    && itemPartColorValues[1].equals(expectedColorValues[1])
                    && itemPartColorValues[2].equals(expectedColorValues[2]),
                    "Hovered result item part '" + itemPart.getText() + "' is not blue."
            + "Expected " + color + ", but found " + itemPartColor + ".");

        });

        driver().switchTo().defaultContent();
    }

    private String[] getColorValues(String colorDescription) {
        int startIndex = colorDescription.indexOf("(") + 1;
        String colorValuesString = String.copyValueOf(colorDescription.toCharArray(), startIndex,
                colorDescription.length() - startIndex - 1);
        return colorValuesString.split(",");
    }

    /**
     * Retrieves parts (span elements) of a lookup result item
     * @param index index of a lookup result item
     * @return list of web elements
     */
    private List<WebElement> getLookupResultItemParts(int index) {
        driver().switchTo().frame(contentIFrame);

        if (index >= lookupResults.size()) {
            driver().switchTo().defaultContent();
            throw new SkipException("Element index exceeds the number of found elements.");
        }

        List<WebElement> result = lookupResults.get(index).findElements(By.cssSelector("span"));

        driver().switchTo().defaultContent();
        return result;
    }
}
