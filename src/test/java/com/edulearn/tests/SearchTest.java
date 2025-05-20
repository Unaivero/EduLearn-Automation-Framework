package com.edulearn.tests;

import com.edulearn.core.ConfigManager;
import com.edulearn.core.DriverFactory;
import com.edulearn.pages.CoursePage;
import com.edulearn.pages.DashboardPage;
import com.edulearn.pages.LoginPage;
import com.edulearn.pages.SearchResultsPage;
import com.edulearn.utils.ReportManager;
import com.edulearn.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * Test class for search functionality
 */
public class SearchTest {
    
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private SearchResultsPage searchResultsPage;
    private final Logger logger = LogManager.getLogger(SearchTest.class);
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
        
        // Login before search tests
        loginPage = new LoginPage();
        String baseUrl = configManager.getBaseUrl();
        loginPage.openLoginPage(baseUrl);
        
        // Use student credentials
        String username = configManager.getProperty("student.username");
        String password = configManager.getProperty("student.password");
        
        dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded");
    }
    
    @Test(description = "Verify search with valid keyword returns results")
    public void testSearchWithValidKeyword() {
        ReportManager.createTest("Search with Valid Keyword");
        ReportManager.logInfo("Testing search functionality with valid keyword");
        
        // Search for a course with keyword "Java"
        String keyword = "Java";
        searchResultsPage = dashboardPage.searchCourse(keyword);
        
        // Verify search results are displayed
        int resultsCount = searchResultsPage.getNumberOfSearchResults();
        Assert.assertTrue(resultsCount > 0, "Search should return results for keyword: " + keyword);
        
        // Verify search keyword is displayed correctly
        String searchKeywordText = searchResultsPage.getSearchKeywordText();
        Assert.assertTrue(searchKeywordText.contains(keyword), 
                "Search keyword text should contain the search term: " + keyword);
        
        // Verify search results count matches the number of results
        String resultsCountText = searchResultsPage.getSearchResultsCountText();
        Assert.assertTrue(resultsCountText.contains(String.valueOf(resultsCount)), 
                "Search results count text should match the number of results");
        
        ReportManager.logPass("Successfully verified search results for valid keyword");
    }
    
    @Test(description = "Verify search with invalid keyword returns no results")
    public void testSearchWithInvalidKeyword() {
        ReportManager.createTest("Search with Invalid Keyword");
        ReportManager.logInfo("Testing search functionality with invalid keyword");
        
        // Search for a course with an invalid keyword
        String invalidKeyword = "xyzabcdef123456";
        searchResultsPage = dashboardPage.searchCourse(invalidKeyword);
        
        // Verify no results message is displayed
        Assert.assertTrue(searchResultsPage.isNoResultsMessageDisplayed(), 
                "No results message should be displayed for invalid keyword");
        
        // Verify no results message contains the keyword
        String noResultsMessage = searchResultsPage.getNoResultsMessageText();
        Assert.assertTrue(noResultsMessage.contains(invalidKeyword), 
                "No results message should contain the search term");
        
        ReportManager.logPass("Successfully verified no results for invalid keyword");
    }
    
    @Test(description = "Verify clicking on a search result opens the course page")
    public void testClickingOnSearchResult() {
        ReportManager.createTest("Clicking on Search Result");
        ReportManager.logInfo("Testing clicking on a search result");
        
        // Search for a course with keyword that will return results
        String keyword = "Programming";
        searchResultsPage = dashboardPage.searchCourse(keyword);
        
        // Verify search returns results
        int resultsCount = searchResultsPage.getNumberOfSearchResults();
        Assert.assertTrue(resultsCount > 0, "Search should return results for keyword: " + keyword);
        
        // Click on the first search result
        CoursePage coursePage = searchResultsPage.clickSearchResult(0);
        
        // Verify course page is loaded
        String courseTitle = coursePage.getCourseTitle();
        Assert.assertFalse(courseTitle.isEmpty(), "Course title should not be empty");
        
        ReportManager.logPass("Successfully verified clicking on a search result");
    }
    
    @Test(description = "Verify navigation back to dashboard from search results")
    public void testNavigationBackToDashboard() {
        ReportManager.createTest("Navigation from Search Results");
        ReportManager.logInfo("Testing navigation from search results back to dashboard");
        
        // Perform a search
        searchResultsPage = dashboardPage.searchCourse("Programming");
        
        // Navigate back to dashboard
        dashboardPage = searchResultsPage.clickBackToDashboard();
        
        // Verify dashboard is loaded
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), 
                "Dashboard should be loaded after navigating back");
        
        ReportManager.logPass("Successfully navigated back to dashboard from search results");
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
