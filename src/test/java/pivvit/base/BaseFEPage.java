package pivvit.base;

import org.openqa.selenium.WebDriver;

public abstract class BaseFEPage extends BasePage {
    @Override
    protected WebDriver driver() {
        return BaseTest.frontendDriver();
    }
}
