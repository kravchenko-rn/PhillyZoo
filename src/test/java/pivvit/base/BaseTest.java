package pivvit.base;

import com.pivvit.phillyzoo.actions.Actions;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.xml.XmlSuite;
import pivvit.properties.Properties;
import pivvit.properties.PropertiesNames;
import pivvit.properties.Viewports;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static ThreadLocal<String> currentTestName = new ThreadLocal<>();

    public static String getCurrentTestName() {
        return currentTestName.get();
    }

    private static ThreadLocal<WebDriver> frontendDriver = new ThreadLocal<>();

    private static ThreadLocal<WebDriver> activeDriver = new ThreadLocal<>();

    public static WebDriver getActiveDriver() {
        return ThreadGuard.protect(activeDriver.get());
    }

    public static WebDriver frontendDriver() {
        if (frontendDriver.get() == null) {
            String browser = Properties.getBrowser(PropertiesNames.BROWSER);

            if (((browser == null) || browser.isEmpty())) {
                throw new AssertionError("BROWSER in setUp method shouldn't be null or empty.");
            }

            frontendDriver.set(setupFEDriver(browser));
            setupViewport(frontendDriver.get(), Properties.getViewport(PropertiesNames.VIEWPORT));
            frontendDriver.get().manage().timeouts().implicitlyWait(BasePage.ELEMENT_SMALL_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }

        Assert.assertNotNull(frontendDriver.get(), "Frontend WebDriver is not set up!");
        activeDriver.set(frontendDriver.get());
        return ThreadGuard.protect(frontendDriver.get());
    }

    public static void setFrontendDriver(WebDriver driver) {
        frontendDriver.set(driver);
    }

    public static void setupViewport(WebDriver webDriver, Viewports viewport) {
        if (Properties.isTablet() || Properties.isMobile())
            return;
        webDriver.manage().window().setSize(new Dimension(viewport.getWidth(), viewport.getHeight()));
    }

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        XmlSuite xmlSuite = context
                .getSuite()
                .getXmlSuite();
                xmlSuite.setThreadCount(Properties.getThreadsCount());
    }

    @BeforeClass
    public void getTestName() {
        currentTestName.set(getClass().getName());
    }

    private static WebDriver setupFEDriver(String browser) {
        Platform platform = getPlatform();

        switch (browser) {
            case BrowserType.FIREFOX:
                return setUpFirefox(platform);
            case BrowserType.CHROME:
                return setUpChrome();
            case BrowserType.IE:
            case "ie":
                Properties.isDriverIE = true;
                return setUpIE(platform);
            default:
                return setUpFirefox(platform);
        }
    }

    private static WebDriver setUpFirefox(Platform platform) {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setBrowserName("firefox");
        capabilities.setPlatform(platform);
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setPreference("network.http.phishy-userpass-length", 255);
        WebDriver driver = new FirefoxDriver(capabilities);
        return driver;
    }

    private static WebDriver setUpChrome() {
        String chromeDriverPath = Properties.getChromeDriverPath();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");

        WebDriver driver = new ChromeDriver(options) {
            @Override
            public WebElement findElement(By by) {
                try {
                    return new CustomWebElement(by.findElement(this));
                } catch (NoSuchElementException noSuchElement) {
                    Field field;
                    try {
                        field = Throwable.class.getDeclaredField("detailMessage");
                    } catch (NoSuchFieldException e) {
                        throw noSuchElement;
                    }
                    field.setAccessible(true);
                    try {
                        String error = "\n" + by + "\n" + field.get(noSuchElement);
                        field.set(noSuchElement, error);
                    } catch (IllegalAccessException ignored) {
                    }
                    throw noSuchElement;
                }
            }
        };
        return driver;
    }

    private static WebDriver setUpIE(Platform platform) {
        String IEDriverPath = Properties.getIEDriverPath();
        System.setProperty("webdriver.ie.driver", IEDriverPath);
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setPlatform(platform);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        capabilities.setCapability(InternetExplorerDriver.SILENT, true);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
        capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
        capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
        WebDriver driver = new InternetExplorerDriver(capabilities);
        return driver;
    }

    private static Platform getPlatform() {
        Platform platform;
        if (SystemUtils.IS_OS_WINDOWS) {
            platform = Platform.WINDOWS;
        } else if (SystemUtils.IS_OS_LINUX) {
            platform = Platform.LINUX;
        } else if (SystemUtils.IS_OS_MAC) {
            platform = Platform.MAC;
        } else {
            platform = Platform.ANY;
        }
        return platform;
    }

    @AfterClass
    public static void tearDown() {
        Actions.clear();
        stopDriver();
    }

    protected static void stopDriver() {
        if (frontendDriver.get() != null) {
            frontendDriver.get().quit();
        }
        if (activeDriver.get() != null) {
            activeDriver.get().quit();
            activeDriver.set(null);
        }
    }

    protected static void skipTest(String message) {
        throw new SkipException(message);
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleep() {
        sleep(3);
    }
}
