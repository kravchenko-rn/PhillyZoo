package com.pivvit.phillyzoo.pages.popup;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.SkipException;
import pivvit.utils.Reporter;
import pivvit.utils.SoftAssert;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;
import java.util.Locale;

public class WinterExperienceTickets2Popup extends BasePopup {
    @FindBy(css = ".ticket-selection__dates__date.ng-scope")
    List<WebElement> ticketSelectionDates;

    @FindBy(css = ".ticket-selection__dates__right-arrow")
    WebElement ticketDatesPaginationRightLink;

    @FindBy(css = ".ticket-selection__dates__left-arrow")
    WebElement ticketDatesPaginationLeftLink;

    @FindBy(css = ".ticket-selection__months span")
    List<WebElement> availableMonths;

    @FindBy(css = ".ticket-selection__times-container select")
    WebElement timeSelect;

    @FindBy(css = ".ticket-selection__times-container .text-red")
    WebElement reminder;

    @FindBy(css = "button.purchase-step__continue")
    WebElement continueButton;

    public WinterExperienceTickets2Popup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    public CheckoutPopup waitTillLoadingIndicatorDisappears() {
        super.waitTillLoadingIndicatorDisappears();
        return new CheckoutPopup();
    }

    /**
     * Retrieves date from the first displayed element
     * @return string which contains date
     */
    public String getFirstDisplayedBookingDate() {
        return getDisplayedBookingDate(0);
    }

    /**
     * Retrieves date from the specified displayed element
     * @param dateElemIndex index of the element to retrieve date from
     * @return string which contains date
     */
    private String getDisplayedBookingDate(int dateElemIndex) {
        driver().switchTo().frame(contentIFrame);

        WebElement dateElem = ticketSelectionDates.get(dateElemIndex);
        String dayOfWeek = dateElem.findElement(By.cssSelector("strong:nth-of-type(1)")).getText();
        String month = dateElem.findElement(By.tagName("div")).getText();
        String dayOfMonth = dateElem.findElement(By.cssSelector("strong:nth-of-type(2)")).getText();

        String result = dayOfWeek + ", " + dayOfMonth + " " + month;

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the number of available months for purchasing tickets
     * @return number of available months
     */
    public int getNumberOfAvailableMonths() {
        driver().switchTo().frame(contentIFrame);

        int result = availableMonths.size();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the text of the first option in dropdown
     * @return string which contains the text of the first option
     */
    public String getStartBookingTime() {
        driver().switchTo().frame(contentIFrame);

        String result = new Select(timeSelect).getOptions().get(1).getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Retrieves the text of the last option in dropdown
     * @return string which contains the text of the last option
     */
    public String getEndBookingTime() {
        driver().switchTo().frame(contentIFrame);

        List<WebElement> options = new Select(timeSelect).getOptions();
        String result = options.get(options.size() - 1).getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Selects first available date from the list.
     * Throws skip exception in case when there are no available dates.
     * @return {@link WinterExperienceTickets2Popup} page
     */
    public WinterExperienceTickets2Popup selectFirstAvailableDate() {
        String startDate = getFirstDisplayedBookingDate();
        String newFirstDate = getFirstDisplayedBookingDate();
        driver().switchTo().frame(contentIFrame);

        boolean found = false;
        do {
            for (WebElement ticketSelectionDate : ticketSelectionDates) {
                try {
                    ticketSelectionDate.findElement(By.cssSelector(".sold-out-label"));
                } catch (NoSuchElementException e) {
                    click(ticketSelectionDate, "Clicking ticket selection date.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                driver().switchTo().defaultContent();
                clickNextTicketsDatesLink();
                newFirstDate = getFirstDisplayedBookingDate();
                driver().switchTo().frame(contentIFrame);
            }
        } while (!found && !startDate.equals(newFirstDate));

        if (!found) {
            driver().switchTo().defaultContent();
            throw new SkipException("There are no available dates.");
        }

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Selects time from the dropdown by text
     * @param time string which contains time to select
     * @return {@link WinterExperienceTickets2Popup} page
     */
    public WinterExperienceTickets2Popup selectTimeForTheTicket(String time) {
        driver().switchTo().frame(contentIFrame);

        new Select(timeSelect).selectByVisibleText(time);

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks ticket time select
     * @return {@link WinterExperienceTickets2Popup} page
     */
    public WinterExperienceTickets2Popup clickTicketTimeSelect() {
        driver().switchTo().frame(contentIFrame);

        click(timeSelect, "Clicking ticket time select.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks next tickets dates link
     * @return {@link WinterExperienceTickets2Popup} page
     */
    public WinterExperienceTickets2Popup clickNextTicketsDatesLink() {
        driver().switchTo().frame(contentIFrame);

        try {
            click(ticketDatesPaginationRightLink, "Clicking next tickets dates link.");
        } catch (Exception e) {
            Assert.fail("Could not click next dates link", e);
        } finally {
            driver().switchTo().defaultContent();
        }

        return new WinterExperienceTickets2Popup();
    }

    /**
     * Clicks previous(left) tickets dates link
     * @return {@link WinterExperienceTickets2Popup} page
     */
    public WinterExperienceTickets2Popup clickPreviousTicketsDatesLink() {
        driver().switchTo().frame(contentIFrame);

        try {
            click(ticketDatesPaginationLeftLink, "Clicking previous tickets dates link.");
        } catch (Exception e) {
            Assert.fail("Could not click next dates link", e);
        } finally {
            driver().switchTo().defaultContent();
        }

        return new WinterExperienceTickets2Popup();
    }

    /**
     * Clicks continue purchase button
     * @return {@link WinterExperienceTickets2Popup} page
     */
    public WinterExperienceTickets2Popup clickContinueButton() {
        driver().switchTo().frame(contentIFrame);

        click(continueButton, "Clicking continue purchase button.");

        driver().switchTo().defaultContent();
        return new WinterExperienceTickets2Popup();
    }

    /**
     * Checks whether there is at least one sold out date present at the current view.
     * @return {@code true} in case when there is at least one sold out date at the current view
     */
    public boolean isSoldOutDatePresent() {
        driver().switchTo().frame(contentIFrame);

        boolean result = false;
        for (WebElement ticketSelectionDate: ticketSelectionDates) {
            try {
                ticketSelectionDate.findElement(By.cssSelector(".sold-out-label"));
                result = true;
                break;
            } catch (NoSuchElementException e) {
                // swallow
            }
        }

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the time select is displayed.
     * @return {@code true} in case when the time select is displayed
     */
    public boolean isTimeSelectDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(timeSelect, "Checking whether the ticket time select is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the reminder is displayed
     * @return {@code true} in case when the reminder is displayed
     */
    public boolean isReminderDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(reminder, "Checking whether the reminder is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the continue button enabled.
     * @return {@code true} in case when continue button is enabled
     */
    public boolean isContinueButtonEnabled() {
        driver().switchTo().frame(contentIFrame);

        Reporter.log("Checking whether the continue button is enabled.");
        boolean result = continueButton.isEnabled() && continueButton.getAttribute("class").contains("button-orange");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Validates the look of the selected month.
     * @param softAssert {@link SoftAssert} assertion object
     * @param color expected color
     * @param validateBold {@code true} in case when we need to check if the text is bold
     */
    public void validateMonthsFont(SoftAssert softAssert, String color, boolean validateBold) {
        driver().switchTo().frame(contentIFrame);

        availableMonths.forEach(availableMonth -> {
            click(availableMonth, "Clicking month " + availableMonth.getText() + ".");
            String monthColor = availableMonth.getCssValue("color");
            int weight = Integer.parseInt(availableMonth.getCssValue("font-weight"));
            softAssert.assertEquals(monthColor, color, "Month " + availableMonth.getText() + " has incorrect color.");
            if (validateBold) {
                softAssert.assertTrue(weight >= 700, "Month '" + availableMonth.getText() + "' is not bold."
                        + "It's font-weight value is " + weight + ".");
            }
        });

        driver().switchTo().defaultContent();
    }

    /**
     * Validates time interval among each time value in the 'Book your time' dropdown
     * @param softAssert {@link SoftAssert} assertion object
     * @param interval interval in minutes
     */
    public void validateBookingTimeInterval(SoftAssert softAssert, int interval) {
        driver().switchTo().frame(contentIFrame);

        List<WebElement> options = new Select(timeSelect).getOptions();
        options.remove(0);
        for (int index = 0; index < options.size() - 1; index++) {
            String option1 = options.get(index).getText();
            String option2 = options.get(index + 1).getText();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mmaa").withLocale(Locale.ENGLISH);
            DateTime option1time = formatter.parseDateTime(option1);
            DateTime option2time = formatter.parseDateTime(option2);
            softAssert.assertEquals(new Duration(option1time, option2time).getStandardMinutes(), interval,
                    "Incorrect interval between " + option1 + " and " + option2 + ".");
        }

        driver().switchTo().defaultContent();
    }
}
