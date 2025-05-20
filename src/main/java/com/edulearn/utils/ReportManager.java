package com.edulearn.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for managing Extent Reports
 */
public class ReportManager {
    
    private static final Logger logger = LogManager.getLogger(ReportManager.class);
    private static ExtentReports extentReports;
    private static final Map<Long, ExtentTest> testMap = new HashMap<>();
    private static final String REPORT_DIR = "target/extent-reports/";
    private static final String REPORT_FILE = "edulearn-test-report.html";
    
    private ReportManager() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Initializes the Extent Reports instance
     */
    public static synchronized void initReports() {
        if (extentReports == null) {
            // Create report directory if it doesn't exist
            File reportDir = new File(REPORT_DIR);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            
            // Configure the report
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_DIR + REPORT_FILE);
            sparkReporter.config().setDocumentTitle("EduLearn Automation Test Report");
            sparkReporter.config().setReportName("EduLearn Automation Test Results");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setEncoding("UTF-8");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
            
            // Create ExtentReports instance
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            
            logger.info("Extent Reports initialized");
        }
    }
    
    /**
     * Creates a new test in the report
     *
     * @param testName name of the test
     * @return ExtentTest instance
     */
    public static synchronized ExtentTest createTest(String testName) {
        if (extentReports == null) {
            initReports();
        }
        
        ExtentTest test = extentReports.createTest(testName);
        testMap.put(Thread.currentThread().getId(), test);
        logger.debug("Created test: {}", testName);
        return test;
    }
    
    /**
     * Creates a new test with description in the report
     *
     * @param testName name of the test
     * @param description description of the test
     * @return ExtentTest instance
     */
    public static synchronized ExtentTest createTest(String testName, String description) {
        if (extentReports == null) {
            initReports();
        }
        
        ExtentTest test = extentReports.createTest(testName, description);
        testMap.put(Thread.currentThread().getId(), test);
        logger.debug("Created test: {} with description: {}", testName, description);
        return test;
    }
    
    /**
     * Gets the current test for the current thread
     *
     * @return ExtentTest instance
     */
    public static synchronized ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }
    
    /**
     * Logs a message with INFO status
     *
     * @param message message to log
     */
    public static void logInfo(String message) {
        getTest().log(Status.INFO, message);
        logger.info(message);
    }
    
    /**
     * Logs a message with PASS status
     *
     * @param message message to log
     */
    public static void logPass(String message) {
        getTest().log(Status.PASS, message);
        logger.info("PASS: " + message);
    }
    
    /**
     * Logs a message with FAIL status
     *
     * @param message message to log
     */
    public static void logFail(String message) {
        getTest().log(Status.FAIL, message);
        logger.error("FAIL: " + message);
    }
    
    /**
     * Logs a message with WARNING status
     *
     * @param message message to log
     */
    public static void logWarning(String message) {
        getTest().log(Status.WARNING, message);
        logger.warn(message);
    }
    
    /**
     * Logs a screenshot with INFO status
     *
     * @param base64Image Base64 encoded screenshot
     * @param title title for the screenshot
     */
    public static void logScreenshot(String base64Image, String title) {
        getTest().log(Status.INFO, title).addScreenCaptureFromBase64String(base64Image);
    }
    
    /**
     * Flushes the report to disk
     */
    public static synchronized void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
            logger.info("Extent Reports flushed to disk");
        }
    }
}
