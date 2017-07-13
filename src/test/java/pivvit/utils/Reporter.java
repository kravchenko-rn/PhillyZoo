package pivvit.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pivvit.base.BaseTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Reporter {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("H:mm:ss:SSS");

    public static void log(String s) {
        org.testng.Reporter.log("[" + FORMAT.format(System.currentTimeMillis()) + "]: " + Formatter.escapeCharacters(s) + "<br></br>");
    }

    public static void log(Throwable throwable) {
        org.testng.Reporter.log("[" + FORMAT.format(Calendar.getInstance().getTime()) + "]: " + Formatter.escapeCharacters(throwable.getMessage()) + "<br></br>");
        for (StackTraceElement stack : throwable.getStackTrace()) {
            org.testng.Reporter.log(stack.toString() + "<br></br>");
        }
    }

    public static void logAction(String msg) {
        org.testng.Reporter.log("[" + FORMAT.format(System.currentTimeMillis()) + "]: " + Formatter.escapeCharacters(String.format("<b>%s</b>", msg)) + "<br></br>");
    }

    public static void makeScreenshot(String message){
        makeScreenshot(message, "");
    }

    public static void makeScreenshot(String message, String screenshotName){
        log(message);
        String imageName = screenshotName + System.currentTimeMillis() + ".png";
        String path = System.getProperty("image.dir") + File.separatorChar + "html" + File.separatorChar;
        try {
            File screenshot = ((TakesScreenshot) BaseTest.getActiveDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path + imageName));
            log("<a href='" + imageName + "'><img src='" + imageName + "' width='600'/></a>");
        } catch (Exception e) {
            Reporter.log("Failed to save screenshot <br></br> " + e);
        }
    }

    public static class Formatter {
        /**
         * Escape special characters for HTML logs
         *
         * @param string string with unescaped characters
         * @return string with escaped characters
         */
        public static String escapeCharacters(String string) {
            return string.replace("&", "&amp;");
        }

        /**
         * Rounds to 2 digits after point.
         */
        public static float roundFractional(float number) {
            return roundFractional(number, 2);
        }

        public static float roundFractional(float number, int precision) {
            return (float) Math.round(number * (float) Math.pow(10, precision)) / (float) Math.pow(10, precision);
        }

        public static String unformatPhone(String formatterPhone) {
            return formatterPhone.replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
        }
    }
}
