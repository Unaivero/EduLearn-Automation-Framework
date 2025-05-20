package com.edulearn.tests;

import com.edulearn.pages.DashboardPage;
import com.edulearn.pages.LoginPage;
import com.edulearn.utils.ReportManager;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for login functionality
 */
public class LoginTest extends BaseTest {
    
    private LoginPage loginPage;
    
    @BeforeMethod
    @Override
    public void setup() {
        super.setup();
        loginPage = new LoginPage();
        
        String baseUrl = configManager.getBaseUrl();
        loginPage.openLoginPage(baseUrl);
    }
    
    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        ReportManager.createTest("Successful Login");
        ReportManager.logInfo("Testing login with valid credentials");
        
        // Get test data from config
        String username = configManager.getProperty("valid.username");
        String password = configManager.getProperty("valid.password");
        
        // Perform login
        DashboardPage dashboardPage = loginPage.login(username, password);
        
        // Verify dashboard is loaded
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard page should be loaded after successful login");
        
        // Verify welcome message contains username
        String welcomeMessage = dashboardPage.getWelcomeMessage();
        Assert.assertTrue(welcomeMessage.contains(username), 
                "Welcome message should contain username. Message: " + welcomeMessage);
        
        ReportManager.logPass("Successfully logged in and verified dashboard");
    }
    
    @Test(description = "Verify login fails with invalid credentials")
    public void testFailedLogin() {
        ReportManager.createTest("Failed Login");
        ReportManager.logInfo("Testing login with invalid credentials");
        
        // Get test data
        String invalidUsername = "invalid_user";
        String invalidPassword = "invalid_password";
        
        // Enter invalid credentials and click login
        loginPage.enterUsername(invalidUsername);
        loginPage.enterPassword(invalidPassword);
        loginPage.clickLoginButton();
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        
        // Verify error message text
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Invalid"), 
                "Error message should indicate invalid credentials. Message: " + errorMessage);
        
        ReportManager.logPass("Successfully verified error message for invalid login");
    }
    
    @AfterSuite
    public void tearDownSuite() {
        ReportManager.flushReports();
    }
}
