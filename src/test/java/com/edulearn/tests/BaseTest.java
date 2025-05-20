package com.edulearn.tests;

import com.edulearn.core.ConfigManager;
import com.edulearn.core.DriverFactory;
import com.edulearn.utils.ReportManager;
import com.edulearn.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * Base class for all UI test classes
 */
public abstract class BaseTest {
    
    protected WebDriver driver;
    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected final ConfigManager configManager = ConfigManager.getInstance();
    
    @BeforeSuite
    public void setupSuite() {
        ReportManager.initReports();
        logger.info("Test suite started");
    }
    
    @BeforeMethod
    public void setup() {
        logger.info("Setting up test");
        String browser = configManager.getBrowser();
        boolean headless = configManager.isHeadless();
        driver = DriverFactory.initializeDriver(browser, headless);
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", result.getName());
            String screenshotPath = ScreenshotUtils.takeFailureScreenshot(driver, result.getName());
            ReportManager.logFail("Test failed. Screenshot: " + screenshotPath);
        }
        
        DriverFactory.quitDriver();
        logger.info("Driver quit successfully");
    }
}
