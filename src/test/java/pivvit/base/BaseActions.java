package pivvit.base;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pivvit.properties.Properties;
import pivvit.properties.PropertiesNames;
import pivvit.properties.Viewports;
import pivvit.utils.Reporter;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

import java.util.Set;

public class BaseActions {
    WebDriver driver;

    private BaseActions(WebDriver driver) {
        this.driver = driver;
    }

    public static BaseActions frontendInstance() {
        return new BaseActions(BaseTest.frontendDriver());
    }

    public static BaseActions instance(BasePage instance) {
        if (instance instanceof BaseFEPage){
            return frontendInstance();
        }
        return null;
    }


    public void sleep(int seconds) {
        sleep(seconds, 0);
    }

    public void sleep(int seconds, int milliseconds) {
        try {
            Thread.sleep(seconds * 1000 + milliseconds);
        } catch (Exception e) {
        }
    }

    public void openPage(String url) {
        Reporter.log(String.format("Open url [%s]", url));
        driver.navigate().to(url);
        //workaround for IE bug with security certificate
        if (Properties.isDriverIE) {
            try {
                driver.findElement(By.id("overridelink")).click();
            } catch (Exception e) {
                //NOP
            }
        }
    }

    public void setWindowSize(Viewports viewport){
        driver.manage().window().setSize(new Dimension(viewport.getWidth(), viewport.getHeight()));
    }

    public void refresh() {
        Reporter.log("Refreshing page");
        driver.navigate().refresh();
    }

    /**
     * Switches to another window
     *
     * @return parent window handle
     */
    public String switchWindow() {
        Reporter.log("Switching to another window");
        String parentWindow = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        if (Properties.isSafari())
            sleep(5);
        driver.getWindowHandles().stream().filter(windowHandle -> !windowHandle.equals(parentWindow))
                .forEach(windowHandle -> driver.switchTo().window(windowHandle));
        return parentWindow;
    }

    public void closeOtherTabs() {
        Reporter.log("Closing other browser tabs");
        String currentHandle;
        if (driver.getWindowHandles().size()>1)
            currentHandle = driver.getWindowHandle();
        else currentHandle = (String)driver.getWindowHandles().toArray()[0];
        Set<String> handles = driver.getWindowHandles();
        for (String handle: handles) {
            if (! handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(currentHandle);
    }

    public void closeAndSwitchWindow() {
        switchWindow();
        closeOtherTabs();
    }

    /*Unfortunately, IE can't delete HttpOnly cookies*/
    public void clearSession() {
        driver.manage().deleteAllCookies();
        if (Properties.isDriverIE) {
            driver.manage().deleteCookieNamed("JSESSIONID");
        }
        sleep(BasePage.ELEMENT_EXTRASMALL_TIMEOUT_SECONDS);
    }

    public void acceptAlertAutomatically() {
        Reporter.log("Configure page to accept alert automatically");
        JavascriptExecutor js = (JavascriptExecutor)driver;

        js.executeScript("confirm = function(message){return true;};");
        js.executeScript("alert = function(message){return true;};");
        js.executeScript("prompt = function(message){return true;}");
    }

    /**
     * Move cursor to some {@link WebElement}
     * @param element The element that need to be hovered.
     */
    public void moveToElement(WebElement element) {
        new org.openqa.selenium.interactions.Actions(driver).moveToElement(element).perform();
    }

    /**
     * Move cursor to some {@link TypifiedElement}
     * @param element The element that need to be hovered.
     */
    public void moveToElement(TypifiedElement element) {
        moveToElement(element.getWrappedElement());
    }

    public void moveToElementAndClick(WebElement element, int xOffset, int yOffset){
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.moveToElement(element, xOffset, yOffset).click().perform();
    }

    public void navigateBack() {
        if (Properties.getBrowser(PropertiesNames.BROWSER).equals(BrowserType.SAFARI)) {
            ((JavascriptExecutor)driver).executeScript("history.go(-1)");
        } else {
            driver.navigate().back();
        }
    }
}
