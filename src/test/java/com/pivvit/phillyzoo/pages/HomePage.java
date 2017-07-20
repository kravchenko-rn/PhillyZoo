package com.pivvit.phillyzoo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.base.BaseActions;
import pivvit.base.BaseFEPage;
import pivvit.properties.Properties;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class HomePage extends BaseFEPage {
    @FindBy(id = "logo")
    WebElement logo;

    @FindBy(css = "#tabPadder>div")
    WebElement tabPadder;

    public HomePage() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    /**
     * Opens home page
     * @return {@link HomePage} page
     */
    public HomePage open() {
        BaseActions.frontendInstance().openPage(Properties.getFrontendUrl());
        return new HomePage().waitForPageLoad();
    }

    /**
     * Waits till the page is loaded.
     * @return {@link HomePage} page
     */
    public HomePage waitForPageLoad() {
        waitForLoad(this);
        waitForVisibility(tabPadder, "Waiting for tabPadder to load");
        return this;
    }
}
