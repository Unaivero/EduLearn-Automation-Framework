package com.edulearn.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the Login page
 */
public class LoginPage extends BasePage {
    
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "loginButton")
    private WebElement loginButton;
    
    @FindBy(id = "forgotPasswordLink")
    private WebElement forgotPasswordLink;
    
    @FindBy(id = "registerLink")
    private WebElement registerLink;
    
    @FindBy(className = "error-message")
    private WebElement errorMessage;
    
    /**
     * Opens the login page
     *
     * @param baseUrl base URL of the application
     * @return LoginPage instance
     */
    public LoginPage openLoginPage(String baseUrl) {
        driver.get(baseUrl + "/login");
        logger.info("Opened Login page");
        return this;
    }
    
    /**
     * Enters username
     *
     * @param username username to enter
     * @return LoginPage instance
     */
    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        logger.info("Entered username: {}", username);
        return this;
    }
    
    /**
     * Enters password
     *
     * @param password password to enter
     * @return LoginPage instance
     */
    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        logger.info("Entered password");
        return this;
    }
    
    /**
     * Clicks the login button
     *
     * @return DashboardPage instance if login successful
     */
    public DashboardPage clickLoginButton() {
        click(loginButton);
        logger.info("Clicked login button");
        return new DashboardPage();
    }
    
    /**
     * Performs login with given credentials
     *
     * @param username username to use
     * @param password password to use
     * @return DashboardPage instance if login successful
     */
    public DashboardPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }
    
    /**
     * Clicks the forgot password link
     *
     * @return ForgotPasswordPage instance
     */
    public void clickForgotPasswordLink() {
        click(forgotPasswordLink);
        logger.info("Clicked forgot password link");
    }
    
    /**
     * Clicks the register link
     *
     * @return RegistrationPage instance
     */
    public void clickRegisterLink() {
        click(registerLink);
        logger.info("Clicked register link");
    }
    
    /**
     * Gets the error message text
     *
     * @return error message text
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    /**
     * Checks if the error message is displayed
     *
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}
