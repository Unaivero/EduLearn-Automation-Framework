package com.edulearn.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for capturing screenshots
 */
public class ScreenshotUtils {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOTS_DIR = "target/screenshots/";
    
    private ScreenshotUtils() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Takes a screenshot and saves it to the screenshots directory
     *
     * @param driver WebDriver instance
     * @param testName name of the test
     * @return path to the screenshot file
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            logger.error("Driver is null, cannot take screenshot");
            return null;
        }
        
        if (!(driver instanceof TakesScreenshot)) {
            logger.error("Driver does not support taking screenshots");
            return null;
        }
        
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotsDir = new File(SCREENSHOTS_DIR);
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }
            
            // Take screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            // Generate filename with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = testName + "_" + timestamp + ".png";
            Path destination = Paths.get(SCREENSHOTS_DIR + filename);
            
            // Copy screenshot to destination
            Files.copy(srcFile.toPath(), destination);
            
            logger.info("Screenshot saved: {}", destination);
            return destination.toString();
            
        } catch (IOException e) {
            logger.error("Failed to save screenshot", e);
            return null;
        }
    }
    
    /**
     * Takes a screenshot for a test failure
     *
     * @param driver WebDriver instance
     * @param testName name of the test
     * @return path to the screenshot file
     */
    public static String takeFailureScreenshot(WebDriver driver, String testName) {
        return takeScreenshot(driver, "FAILURE_" + testName);
    }
    
    /**
     * Takes a screenshot and encodes it as Base64
     *
     * @param driver WebDriver instance
     * @return Base64 encoded screenshot
     */
    public static String takeBase64Screenshot(WebDriver driver) {
        if (driver == null || !(driver instanceof TakesScreenshot)) {
            logger.error("Driver is null or does not support taking screenshots");
            return null;
        }
        
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
