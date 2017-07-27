package pivvit.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pivvit.utils.Reporter;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class BasePage extends CustomHtmlElement {
    public static int ELEMENT_MICRO_TIMEOUT_SECONDS = 1;
    public static int ELEMENT_EXTRASMALL_TIMEOUT_SECONDS = 2;
    public static int ELEMENT_SMALL_TIMEOUT_SECONDS = 5;
    public static int ELEMENT_TIMEOUT_SECONDS = ELEMENT_MICRO_TIMEOUT_SECONDS;
    public static int ELEMENT_LONG_TIMEOUT_SECONDS = 20;
    public static int ELEMENT_EXTRALONG_TIMEOUT_SECONDS = 40;
    public static int ELEMENT_MEGA_EXTRALONG_TIMEOUT_SECONDS = 300;

    protected abstract WebDriver driver();

    public static ExpectedCondition readyStateComplete(WebDriver driver) {
        return new ExpectedCondition() {
            public Object apply(Object o) {
                boolean ready = false;
                try {
                    ready = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                } catch (WebDriverException e) {
                    // swallow
                }
                return ready;
            }

            public String toString() {
                return "Waiting for the document to reach 'complete' status";
            }
        };
    }

    public static ExpectedCondition<Boolean> stalenessOf(
            final By locator) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                boolean isGone = false;
                try {
                    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    driver.findElement(locator);
                    isGone = false;
                } catch (NoSuchElementException expected) {
                    isGone = true;
                } finally {
                    driver.manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                }
                return isGone;
            }

            @Override
            public String toString() {
                return String.format("element (%s) to become stale", locator);
            }
        };
    }

    /**
     * Click element
     *
     * @param element
     * @param message
     */
    protected void click(WebElement element, String message) {
        Reporter.log(message);
        element.click();
    }

    protected void click(TypifiedElement element, String message) {
        click(element.getWrappedElement(), message);
    }

    protected void clickJS(WebElement element, String message) {
        Reporter.log(message);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].click()", element);
    }

    protected WebElement getElementUnderShadowRootByClassJS(String classNameAbove, String classNameUnder, String message) {
        Reporter.log(message);
        return (WebElement) ((JavascriptExecutor) driver()).
                executeScript(String.format("return document.querySelector('%s').shadowRoot.querySelector('%s')", classNameAbove, classNameUnder));
    }

    /**
     * Type value to element
     *
     * @param value
     * @param element
     * @param message
     */
    protected void type(String value, WebElement element, String message) {
        Reporter.log(message);
        element.clear();
        element.sendKeys(value);
    }

    protected void type(String value, TypifiedElement element, String message) {
        type(value, element.getWrappedElement(), message);
    }

    /**
     * Get text from element
     *
     * @param element
     * @param message
     * @return
     */
    protected String getText(WebElement element, String message) {
        Reporter.log(message);
        String type = element.getTagName().toLowerCase();

        if (type.equals("input") || type.equals("textarea")) {
            String placeholder = element.getAttribute("placeholder");
            return (placeholder != null && placeholder.length() > 0)
                    ? element.getAttribute("value").replace(placeholder, "")
                    : element.getAttribute("value");
        }
        if (type.equals("select")) {
            return new Select(element).getFirstSelectedOption().getText();
        }
        return element.getText();
    }

    protected Object executeJS(String script, WebElement element) {
        return ((JavascriptExecutor) driver()).executeScript(script, element);
    }

    protected Object appendChild(WebElement element, String value) {
        return ((JavascriptExecutor) driver()).executeScript(
                String.format("arguments[0].appendChild(document.createTextNode('%s'))", value), element);
    }

    protected Object removeElement(WebElement element) {
        return ((JavascriptExecutor) driver()).executeScript("arguments[0].remove()", element);
    }

    public void waitForLoad(BasePage instance) {
        new WebDriverWait(driver(), 30, 200).until(BasePage.readyStateComplete(driver()));
    }

    /**
     * Wait alert and accept.
     * <p><b>Does not work in Safari!</b></p>
     */
    protected void acceptAlert() {
        Reporter.log("Accept alert");
        WebDriverWait wait = new WebDriverWait(driver(), 10);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver().switchTo().alert();
        alert.accept();
    }

    /**
     * Drag and drop
     *
     * @param from
     * @param to
     * @param message
     */
    protected void dragAndDrop(WebElement from, WebElement to, String message) {
        Reporter.log(message);
        (new Actions(driver()).dragAndDrop(from, to)).perform();
    }

    /**
     * Waits for visibility of element
     *
     * @param element
     */
    protected WebElement waitForVisibility(WebElement element) {
        return new WebDriverWait(driver(), ELEMENT_LONG_TIMEOUT_SECONDS).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for visibility of element
     *
     * @param locator
     */
    protected WebElement waitForVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(driver(), ELEMENT_LONG_TIMEOUT_SECONDS);
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return new WebDriverWait(driver(), ELEMENT_LONG_TIMEOUT_SECONDS).until(ExpectedConditions.visibilityOf(element));
        } catch (StaleElementReferenceException e) {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return new WebDriverWait(driver(), ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.visibilityOf(element));
        }
    }

    protected WebElement waitForVisibility(TypifiedElement element) {
        return waitForVisibility(element.getWrappedElement());
    }

    /**
     * Waits for visibility of element
     *
     * @param element
     * @param message
     */
    protected WebElement waitForVisibility(WebElement element, String message) {
        Reporter.log(message);
        return waitForVisibility(element);
    }

    /**
     * Wait for visibility of element
     *
     * @param locator The element that expected to be visible
     * @param timeout Timeout in seconds for WebDriverWait object
     * @return Visible element or {@code null} if no such element found
     */
    protected WebElement waitForVisibilityNotStrict(By locator, int timeout) {
        WebElement element = null;
        try {
            driver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            element = new WebDriverWait(driver(), timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (WebDriverException e) {
            Reporter.log(String.format("Element is not shown in %d seconds.", timeout));
        } finally {
            driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }

        return element;
    }

    protected boolean waitForInvisibilityNotStrict(By locator, int timeout) {
        boolean elementVisible = false;
        try {
            driver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            elementVisible = new WebDriverWait(driver(), timeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (WebDriverException e) {
            Reporter.log(String.format("Element is displayed after %d seconds.", timeout));
        } finally {
            driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }
        return elementVisible;
    }

    /**
     * Wait for visibility of element
     *
     * @param element The element that expected to be visible
     * @param timeout Timeout in seconds for WebDriverWait object
     * @return Visible element
     */
    protected WebElement waitForVisibilityNotStrict(WebElement element, int timeout) {
        WebElement visibleElement = null;
        try {
            visibleElement = new WebDriverWait(driver(), timeout).until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverException e) {
            Reporter.log(String.format("Element is not shown in %d seconds.", timeout));
        }

        return visibleElement;
    }

    protected boolean waitForVisibilityNotStrict(WebElement element) {
        boolean elementVisible = false;
        try {
            new WebDriverWait(driver(), ELEMENT_SMALL_TIMEOUT_SECONDS).until(ExpectedConditions.visibilityOf(element));
            elementVisible = true;
        } catch (WebDriverException e) {
            Reporter.log(String.format("Element is not shown in %d seconds.", ELEMENT_SMALL_TIMEOUT_SECONDS));
        }
        return elementVisible;
    }

    /**
     * Waits for visibility of element
     *
     * @param element
     * @param message
     */
    public WebElement waitToBeClickable(WebElement element, String message) {
        Reporter.log(message);
        return new WebDriverWait(driver(), ELEMENT_SMALL_TIMEOUT_SECONDS).until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitToBeClickable(TypifiedElement element, String message) {
        return waitToBeClickable(element.getWrappedElement(), message);
    }

    protected void waitForInvisibility(WebElement element, String message) {
        waitForInvisibility(ELEMENT_TIMEOUT_SECONDS, ELEMENT_SMALL_TIMEOUT_SECONDS, element, message);
    }

    protected void waitForInvisibility(int waitElementTimeout, int timeout,
                                       WebElement element, String message) {
        Reporter.log(message);
        driver().manage().timeouts().implicitlyWait(waitElementTimeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver(), timeout);
        try {
            wait.until((ExpectedCondition) o -> !element.isDisplayed());
        } catch (WebDriverException ex) {
            // swallow
        } finally {
            driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }
    }

    /**
     * Checks whether image is loaded correctly
     *
     * @param element
     * @param message
     */
    protected boolean isImageLoaded(WebElement element, String message) {
        Reporter.log(message);
        return ((Boolean) ((JavascriptExecutor) driver())
                .executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element));
    }

    protected boolean isElementPresent(int timeout, By by, String message) {
        Reporter.log(message);
        boolean isPresent = false;

        try {
            driver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver(), timeout).until(ExpectedConditions.presenceOfElementLocated(by));
            isPresent = true;
        } catch (WebDriverException e) {
        } finally {
            driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }

        return isPresent;
    }

    protected boolean isElementPresent(By by, String message) {
        return isElementPresent(ELEMENT_SMALL_TIMEOUT_SECONDS, by, message);
    }

    protected boolean isElementPresent(TypifiedElement element) {
        return isElementPresent(element.getWrappedElement());
    }

    protected boolean isElementPresent(WebElement element) {
        return isElementPresent(element, 0);
    }

    protected boolean isElementPresent(WebElement element, int timeout) {
        boolean isPresent = false;
        try {
            driver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            element.getTagName();
            isPresent = true;
        } catch (Exception e) {

        } finally {
            driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }
        return isPresent;
    }

    protected boolean isElementVisible(WebElement element) {
        return isElementPresent(element, 0) && element.isDisplayed();
    }

    protected boolean isElementVisible(WebElement element, String message) {
        Reporter.log(message);
        return isElementPresent(element, 0) && element.isDisplayed();
    }

    protected boolean isElementVisible(By by, String message) {
        Reporter.log(message);
        if (isElementPresent(0, by, message)) {
            return driver().findElement(by).isDisplayed();
        } else {
            return false;
        }
    }

    /**
     * Select dropdown option by visible text, if not successful, tries select by value
     *
     * @param selectItemText visible text
     * @param element
     * @param message
     */
    protected void selectDropDownListOptionByText(String selectItemText, WebElement element, String message) {
        Reporter.log(message);
        Select dropDownList = new Select(element);
        // if element has wrong text we can try select item by value
        try {
            dropDownList.selectByVisibleText(selectItemText);
        } catch (NoSuchElementException e) {
            dropDownList.selectByValue(selectItemText);
        }
    }

    protected void selectDropDownListOptionByValue(String value, WebElement element, String message) {
        Reporter.log(message);
        Select dropDownList = new Select(element);
        dropDownList.selectByValue(value);
    }

    protected List<String> getDropDownListValues(WebElement select, String message) {
        Reporter.log(message);
        Select dropDownList = new Select(select);
        return dropDownList.getOptions().stream().map(e -> e.getAttribute("value")).collect(Collectors.toList());
    }

    /**
     * Hover element
     *
     * @param element
     * @param message
     */
    protected void hover(WebElement element, String message) {
        Reporter.log(message);
        Actions actions = new Actions(driver());
        actions.moveToElement(element).build().perform();
    }

    protected void hoverAndClick(WebElement element, String message) {
        Actions actions = new Actions(driver());
        actions.moveToElement(element).perform();
        clickJS(element, message);
    }

    private boolean isUrlChanged(String currentUrl) {
        WebDriverWait wait = new WebDriverWait(driver(), 10);
        try {
            wait.until((ExpectedCondition) o -> !currentUrl.equals(driver().getCurrentUrl()));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    protected void setElementAttributeWithJS(String attributeName, String attributeValue,
                                             WebElement webElement, Object... args) {
        JavascriptExecutor executor = (JavascriptExecutor) driver();
        executor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", webElement, attributeName,
                attributeValue);
    }

    protected void clickOnCoordinates(WebElement element, int xOffset, int yOffset) {
        Actions builder = new Actions(driver());
        builder.moveToElement(element, xOffset, yOffset).click().build().perform();
    }

    protected WebElement waitForPresence(By by, String message) {
        Reporter.log(message);
        return new WebDriverWait(driver(), ELEMENT_SMALL_TIMEOUT_SECONDS).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void switchToFrame(int nFrame) {
        driver().switchTo().frame(nFrame);
    }

    protected void switchToDefaultContent() {
        driver().switchTo().defaultContent();
    }

    protected boolean isAvailable(WebElement element) {
        return ExpectedConditions.elementToBeClickable(element) != null;
    }

    protected boolean isAvailable(TypifiedElement element) {
        return isAvailable(element.getWrappedElement());
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleep() {
        sleep(ELEMENT_MICRO_TIMEOUT_SECONDS);
    }
}
