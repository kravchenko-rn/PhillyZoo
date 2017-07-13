package pivvit.base;

import org.testng.IClassListener;
import org.testng.IMethodInstance;
import org.testng.ITestClass;

import static pivvit.base.BaseTest.*;

public class ClassListener implements IClassListener {
    @Override
    public void onBeforeClass(ITestClass iTestClass, IMethodInstance iMethodInstance) {

    }

    @Override
    public void onAfterClass(ITestClass iTestClass, IMethodInstance iMethodInstance) {
        //tearDown();
    }
}
