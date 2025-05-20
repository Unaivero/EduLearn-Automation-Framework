package com.edulearn.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object for the Dashboard page
 */
public class DashboardPage extends BasePage {
    
    @FindBy(id = "welcome-message")
    private WebElement welcomeMessage;
    
    @FindBy(id = "courses-container")
    private WebElement coursesContainer;
    
    @FindBy(className = "course-card")
    private List<WebElement> courseCards;
    
    @FindBy(id = "notifications-icon")
    private WebElement notificationsIcon;
    
    @FindBy(id = "profile-dropdown")
    private WebElement profileDropdown;
    
    @FindBy(id = "logout-button")
    private WebElement logoutButton;
    
    @FindBy(id = "search-course")
    private WebElement searchCourseField;
    
    @FindBy(id = "search-button")
    private WebElement searchButton;
    
    /**
     * Checks if the Dashboard page is loaded
     *
     * @return true if the Dashboard page is loaded
     */
    public boolean isDashboardLoaded() {
        return isElementDisplayed(welcomeMessage) && isElementDisplayed(coursesContainer);
    }
    
    /**
     * Gets the welcome message text
     *
     * @return welcome message text
     */
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }
    
    /**
     * Gets the number of courses displayed
     *
     * @return number of courses
     */
    public int getNumberOfCourses() {
        return courseCards.size();
    }
    
    /**
     * Clicks on a course by index
     *
     * @param index index of the course (0-based)
     * @return CoursePage instance
     */
    public CoursePage clickCourse(int index) {
        if (index >= 0 && index < courseCards.size()) {
            click(courseCards.get(index));
            logger.info("Clicked on course at index: {}", index);
            return new CoursePage();
        } else {
            logger.error("Invalid course index: {}", index);
            throw new IndexOutOfBoundsException("Invalid course index: " + index);
        }
    }
    
    /**
     * Clicks on a course by name
     *
     * @param courseName name of the course
     * @return CoursePage instance
     */
    public CoursePage clickCourseByName(String courseName) {
        for (WebElement courseCard : courseCards) {
            WebElement courseTitle = courseCard.findElement(By.className("course-title"));
            if (getText(courseTitle).equals(courseName)) {
                click(courseCard);
                logger.info("Clicked on course: {}", courseName);
                return new CoursePage();
            }
        }
        logger.error("Course not found: {}", courseName);
        throw new RuntimeException("Course not found: " + courseName);
    }
    
    /**
     * Clicks the notifications icon
     */
    public void clickNotificationsIcon() {
        click(notificationsIcon);
        logger.info("Clicked notifications icon");
    }
    
    /**
     * Opens the profile dropdown
     */
    public void openProfileDropdown() {
        click(profileDropdown);
        logger.info("Opened profile dropdown");
    }
    
    /**
     * Logs out from the application
     *
     * @return LoginPage instance
     */
    public LoginPage logout() {
        openProfileDropdown();
        click(logoutButton);
        logger.info("Clicked logout button");
        return new LoginPage();
    }
    
    /**
     * Searches for a course
     *
     * @param keyword keyword to search for
     * @return SearchResultsPage instance
     */
    public SearchResultsPage searchCourse(String keyword) {
        type(searchCourseField, keyword);
        click(searchButton);
        logger.info("Searched for course with keyword: {}", keyword);
        return new SearchResultsPage();
    }
}
