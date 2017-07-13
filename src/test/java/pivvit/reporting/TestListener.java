package pivvit.reporting;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import pivvit.utils.Reporter;

public class TestListener extends TestListenerAdapter {
    /**
     * Take Screenshot if test failure
     *
     * @param result
     */
    @Override
    public void onTestFailure(ITestResult result) {
        org.testng.Reporter.setCurrentTestResult(result);
        Reporter.makeScreenshot("Test failed.", result.getName());
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        org.testng.Reporter.setCurrentTestResult(result);
        Reporter.makeScreenshot("Before/After method failed.", result.getName());
    }
}
