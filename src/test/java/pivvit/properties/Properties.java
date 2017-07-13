package pivvit.properties;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Properties {
    public static boolean isDriverIE = false;
    private static boolean isMobile = false;
    private static boolean isTablet = false;
    private static boolean isSafari = false;

    static {
        try {
            java.util.Properties properties = new java.util.Properties();
            java.util.Properties systemProperties = System.getProperties();
            InputStream inStream =
                    new FileInputStream("props.properties");
            if (inStream != null) {
                properties.load(inStream);
                Enumeration e = properties.propertyNames();

                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    if (systemProperties.getProperty(key) == null)
                        System.setProperty(key, properties.getProperty(key));
                }
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Platform getPlatform() {
        String platformName = System.getProperty(PropertiesNames.PLATFORM.toString());
        return (platformName != null && !platformName.equals("")) ? Platform.fromString(platformName) : Platform.ANY;
    }


    public static String getBrowser(PropertiesNames browserType) {
        String name = System.getProperty(browserType.toString());

        if (name == null)
            return BrowserType.FIREFOX;

        switch (name) {
            case "ie":
                return BrowserType.IE;
            case BrowserType.CHROME:
                return BrowserType.CHROME;
            case "debug":
                return name;
            default:
                return name;
        }
    }

    public static synchronized boolean isMobile() {
        return isMobile;
    }

    public static synchronized boolean isSafari() {
        return isSafari;
    }

    public static synchronized boolean isSViewport() {
        return System.getProperty(PropertiesNames.VIEWPORT.toString()).equalsIgnoreCase("S");
    }

    public static synchronized void setIsMobile(boolean isMobile) {
        Properties.isMobile = isMobile;
    }

    public static boolean isTablet() {
        return isTablet;
    }

    private static String getDriversDir() {
        return System.getProperty(PropertiesNames.DRIVERS_DIRECTORY.toString());
    }

    private static String getDriverPath(String fileName){
        String basePath = getDriversDir();
        if (basePath == null) {
            throw new IllegalStateException(PropertiesNames.DRIVERS_DIRECTORY.toString() + "should not be empty.");
        }
        return Paths.get(basePath, fileName).toString();
    }

    public static String getChromeDriverPath() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return getDriverPath("chromedriver.exe");
        }
        if (SystemUtils.IS_OS_LINUX) {
            return getDriverPath("chromedriver_linux");
        }
        if (SystemUtils.IS_OS_MAC) {
            return getDriverPath("chromedriver_mac");
        }
        throw new IllegalStateException("Chrome driver doesn't exist.");
    }

    public static String getIEDriverPath() {
        return Paths.get(getDriversDir(), "IEDriverServer.exe").toString();
    }

    public static String getFrontendUrl() {
        return System.getProperty(PropertiesNames.BASE_URL.toString());
    }

    /**
     * Returns environment host URL
     * @return URL value of host in form {@code http[s]://domain.name.com}.
     */
    public static String getHost(){
        Matcher matcher = Pattern.compile("^(http[s]?://.*?)/").matcher(getFrontendUrl());
        if (!matcher.find())
            Assert.fail("Unable to extract host URL from frontend URL string: " + getFrontendUrl());
        return matcher.group(1);
    }

    public static String getConfigDir() {
        return System.getProperty(PropertiesNames.CONFIG_DIRECTORY.toString());
    }

    public static String getTestDataDir() {
        return System.getProperty(PropertiesNames.TESTDATA_DIRECTORY.toString());
    }

    public static String formatSerializedProductFilename(String prefix){
        return "productData" + prefix.replaceAll("\\s+","") + ".obj";
    }

    public static int getThreadsCount(){
        return Integer.parseInt(System.getProperty(PropertiesNames.THREADS.toString()));
    }

    public static Viewports getViewport(PropertiesNames viewportType){
        return Viewports.fromString(System.getProperty(viewportType.toString()));
    }

    public static String getSerializedDir(){
        return System.getProperty(PropertiesNames.SERIALIZED_DIRECTORY.toString()) + File.separator;
    }
}
