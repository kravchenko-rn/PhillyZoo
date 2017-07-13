package pivvit.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CustomWebElement implements WebElement, WrapsElement, Locatable {
    private WebElement wrappedElement;

    public CustomWebElement(WebElement elementToWrap) {
        wrappedElement = elementToWrap;
    }

    @Override
    public void click() {
        try {
            wrappedElement.click();
        } catch (WebDriverException e) {
            if (e.getMessage().contains("is not clickable at point")) {
                try { Thread.sleep(100); } catch (InterruptedException ignored) { }
                wrappedElement.click();
            } else {
                throw e;
            }
        }

    }

    @Override
    public void submit() {
        wrappedElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        wrappedElement.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        wrappedElement.clear();
    }

    @Override
    public String getTagName() {
        return wrappedElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return wrappedElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return wrappedElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return wrappedElement.isEnabled();
    }

    @Override
    public String getText() {
        return wrappedElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return wrappedElement.findElements(by).stream().map(CustomWebElement::new).collect(toList());
    }

    @Override
    public WebElement findElement(By by) {
        return new CustomWebElement(wrappedElement.findElement(by));
    }

    @Override
    public boolean isDisplayed() {
        return wrappedElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return wrappedElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return wrappedElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return wrappedElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return wrappedElement.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return wrappedElement.getScreenshotAs(target);
    }

    @Override
    public WebElement getWrappedElement() {
        return wrappedElement;
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable)wrappedElement).getCoordinates();
    }
}
