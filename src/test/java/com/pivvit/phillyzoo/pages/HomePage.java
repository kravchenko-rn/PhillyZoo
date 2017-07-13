package com.pivvit.phillyzoo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pivvit.base.BaseActions;
import pivvit.base.BaseFEPage;
import pivvit.properties.Properties;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;

public class HomePage extends BaseFEPage {
    @FindBy(id = "logo")
    WebElement logo;

    @FindBy(css = ".tile > img")
    List<WebElement> tiles;

    @FindBy(css = ".tile > .description")
    List<WebElement> tileDescriptions;

    @FindBy(css = ".new-members")
    WebElement btnJoin;

    public HomePage() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    public HomePage open() {
        BaseActions.frontendInstance().openPage(Properties.getFrontendUrl());
        return new HomePage().waitForPageLoad();
    }

    public HomePage waitForPageLoad() {
        waitForLoad(this);
        for (WebElement title : tiles) {
            if (!title.getSize().equals(new Dimension(0, 0))) {
                waitToBeClickable(title, "Waiting for a title pic");
            }
        }
        return this;
    }

    public HomePage hoverLogo() {
        hover(logo, "Hovering logo");
        sleep(1);
        return this;
    }

    public HomePage hoverTile(int index) {
        hover(tiles.get(index), "Hovering title# " + index);
        return this;
    }

    public boolean isTileDescriptionDisplayed(int index) {
        return tileDescriptions.get(index).isDisplayed();
    }

    public String getBtnJoinText() {
        return btnJoin.getText();
    }

    public int getNumberOfTiles() {
        return tiles.size();
    }
}
