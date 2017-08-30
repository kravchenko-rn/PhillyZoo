package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.utils.Reporter;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@FindBy(css = "#mainContainer>section")
public class WinterExperienceTicketsPopup extends BasePopup {
    @FindBy(css = ".items-list tr:nth-of-type(1) .minus-select-plus select")
    WebElement memberTicketsAmountSelect;

    @FindBy(css = "button.purchase-step__continue")
    WebElement continueButton;

    public WinterExperienceTicketsPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Retrieves currently selected amount of tickets
     * @return string which contains amount of tickets
     */
    public String getCurrentAmountOfTickets() {
        driver().switchTo().frame(contentIFrame);

        String result = new Select(memberTicketsAmountSelect).getFirstSelectedOption().getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the value of the maximum amount of member tickets,
     * assuming that the maximum value is the last one in the dropdown list.
     * @return string which contains the maximum value from the dropdown
     */
    public String getMaximumAmountOfMemberTicketsValue() {
        return getMaxAmountOfTickets(new Select(memberTicketsAmountSelect));
    }

    /**
     * Retrieves the maximum value of the dropdown
     * @param dropdown {@link Select} dropdown object
     * @return string which contains the maximum value from the dropdown
     */
    private String getMaxAmountOfTickets(Select dropdown) {
        driver().switchTo().frame(contentIFrame);

        int numberOfOptions = dropdown.getOptions().size();

        String result = dropdown.getOptions().get(numberOfOptions - 1).getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Selects amount of tickets from dropdown list.
     * @param amount string which contains desired amount of tickets
     * @return {@link WinterExperienceTicketsPopup} page
     */
    public WinterExperienceTicketsPopup selectTicketsAmount(String amount) {
        driver().switchTo().frame(contentIFrame);

        new Select(memberTicketsAmountSelect).selectByVisibleText(amount);

        driver().switchTo().defaultContent();
        return new WinterExperienceTicketsPopup();
    }

    /**
     * Checks whether the continue button enabled.
     * @return {@code true} in case when continue button is enabled
     */
    public boolean isContinueButtonEnabled() {
        driver().switchTo().frame(contentIFrame);

        Reporter.log("Checking whether the purchase continue button is enabled.");
        boolean result = continueButton.isEnabled() && continueButton.getAttribute("class").contains("button-orange");

        driver().switchTo().defaultContent();
        return result;
    }
}
