package com.edulearn.tests;

import com.edulearn.core.ConfigManager;
import com.edulearn.core.DriverFactory;
import com.edulearn.pages.CoursePage;
import com.edulearn.pages.DashboardPage;
import com.edulearn.pages.LoginPage;
import com.edulearn.utils.ReportManager;
import com.edulearn.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * Test class for course functionality
 */
public class CourseTest {
    
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CoursePage coursePage;
    private final Logger logger = LogManager.getLogger(CourseTest.class);
    private final ConfigManager configManager = ConfigManager.getInstance();
    
    @BeforeClass
    public void setupClass() {
        ReportManager.initReports();
    }
    
    @BeforeMethod
    public void setup() {
        logger.info("Setting up test");
        String browser = configManager.getBrowser();
        boolean headless = configManager.isHeadless();
        driver = DriverFactory.initializeDriver(browser, headless);
        
        // Login before course tests
        loginPage = new LoginPage();
        String baseUrl = configManager.getBaseUrl();
        loginPage.openLoginPage(baseUrl);
        
        // Use student credentials
        String username = configManager.getProperty("student.username");
        String password = configManager.getProperty("student.password");
        
        dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded");
    }
    
    @Test(description = "Verify course details are displayed correctly")
    public void testCourseDetails() {
        ReportManager.createTest("Course Details Test");
        ReportManager.logInfo("Testing course details page");
        
        // Click on the first course
        coursePage = dashboardPage.clickCourse(0);
        
        // Verify course title is not empty
        String courseTitle = coursePage.getCourseTitle();
        Assert.assertFalse(courseTitle.isEmpty(), "Course title should not be empty");
        ReportManager.logInfo("Course title: " + courseTitle);
        
        // Verify course description is not empty
        String courseDescription = coursePage.getCourseDescription();
        Assert.assertFalse(courseDescription.isEmpty(), "Course description should not be empty");
        
        // Verify instructor name is not empty
        String instructorName = coursePage.getInstructorName();
        Assert.assertFalse(instructorName.isEmpty(), "Instructor name should not be empty");
        
        // Verify modules are present
        int numberOfModules = coursePage.getNumberOfModules();
        Assert.assertTrue(numberOfModules > 0, "Course should have at least one module");
        ReportManager.logInfo("Number of modules: " + numberOfModules);
        
        ReportManager.logPass("Successfully verified course details");
    }
    
    @Test(description = "Verify course enrollment functionality")
    public void testCourseEnrollment() {
        ReportManager.createTest("Course Enrollment Test");
        ReportManager.logInfo("Testing course enrollment functionality");
        
        // Navigate to a course that requires enrollment
        coursePage = dashboardPage.clickCourse(1);
        
        // Check if enrollment is required
        if (coursePage.isEnrollButtonDisplayed()) {
            // Enroll in the course
            coursePage.clickEnrollButton();
            
            // Verify enrollment successful - Start button is displayed
            Assert.assertTrue(coursePage.isStartCourseButtonDisplayed(), 
                    "Start Course button should be displayed after enrollment");
            
            ReportManager.logPass("Successfully enrolled in the course");
        } else {
            // Already enrolled - verify that Start or Resume button is displayed
            boolean canStart = coursePage.isStartCourseButtonDisplayed() || 
                              coursePage.isResumeCourseButtonDisplayed();
            
            Assert.assertTrue(canStart, "Start or Resume button should be displayed for enrolled course");
            ReportManager.logInfo("Course is already enrolled");
            ReportManager.logPass("Successfully verified enrollment status");
        }
    }
    
    @Test(description = "Verify navigation back to dashboard")
    public void testNavigationBackToDashboard() {
        ReportManager.createTest("Navigation Test");
        ReportManager.logInfo("Testing navigation from course page back to dashboard");
        
        // Navigate to a course
        coursePage = dashboardPage.clickCourse(0);
        
        // Navigate back to dashboard
        dashboardPage = coursePage.clickBackToDashboard();
        
        // Verify dashboard is loaded
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), 
                "Dashboard should be loaded after navigating back");
        
        ReportManager.logPass("Successfully navigated back to dashboard");
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
    
    @AfterClass
    public void tearDownClass() {
        ReportManager.flushReports();
    }
}
