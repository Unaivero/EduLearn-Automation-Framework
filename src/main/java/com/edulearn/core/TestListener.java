package com.edulearn.core;

import com.edulearn.utils.ReportManager;
import com.edulearn.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener to handle test events
 */
public class TestListener implements ITestListener {
    
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    
    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting test suite: {}", context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        logger.info("Finished test suite: {}", context.getName());
        logger.info("Passed tests: {}", context.getPassedTests().size());
        logger.info("Failed tests: {}", context.getFailedTests().size());
        logger.info("Skipped tests: {}", context.getSkippedTests().size());
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: {}", result.getName());
        ReportManager.createTest(result.getMethod().getDescription() != null ? 
                result.getMethod().getDescription() : result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}", result.getName());
        ReportManager.logPass("Test passed successfully");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName());
        logger.error("Error: {}", result.getThrowable().getMessage());
        
        try {
            // Take screenshot on failure
            String screenshotPath = ScreenshotUtils.takeFailureScreenshot(
                    DriverFactory.getDriver(), result.getName());
            
            String base64Screenshot = ScreenshotUtils.takeBase64Screenshot(DriverFactory.getDriver());
            if (base64Screenshot != null) {
                ReportManager.logScreenshot(base64Screenshot, "Failure Screenshot");
            }
            
            ReportManager.logFail("Test failed: " + result.getThrowable().getMessage());
        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test skipped: {}", result.getName());
        ReportManager.logWarning("Test skipped: " + result.getThrowable().getMessage());
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test failed but within success percentage: {}", result.getName());
    }
    
    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logger.error("Test failed with timeout: {}", result.getName());
        ReportManager.logFail("Test failed with timeout: " + result.getThrowable().getMessage());
    }
}
