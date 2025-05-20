package com.edulearn.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object for the Course page
 */
public class CoursePage extends BasePage {
    
    @FindBy(id = "course-title")
    private WebElement courseTitle;
    
    @FindBy(id = "course-description")
    private WebElement courseDescription;
    
    @FindBy(id = "instructor-name")
    private WebElement instructorName;
    
    @FindBy(id = "module-list")
    private WebElement moduleList;
    
    @FindBy(className = "module-item")
    private List<WebElement> moduleItems;
    
    @FindBy(id = "enroll-button")
    private WebElement enrollButton;
    
    @FindBy(id = "start-course-button")
    private WebElement startCourseButton;
    
    @FindBy(id = "resume-course-button")
    private WebElement resumeCourseButton;
    
    @FindBy(id = "course-progress")
    private WebElement courseProgress;
    
    @FindBy(id = "rating")
    private WebElement rating;
    
    @FindBy(id = "back-to-dashboard")
    private WebElement backToDashboardButton;
    
    /**
     * Gets the course title
     *
     * @return course title
     */
    public String getCourseTitle() {
        return getText(courseTitle);
    }
    
    /**
     * Gets the course description
     *
     * @return course description
     */
    public String getCourseDescription() {
        return getText(courseDescription);
    }
    
    /**
     * Gets the instructor name
     *
     * @return instructor name
     */
    public String getInstructorName() {
        return getText(instructorName);
    }
    
    /**
     * Gets the number of modules
     *
     * @return number of modules
     */
    public int getNumberOfModules() {
        return moduleItems.size();
    }
    
    /**
     * Clicks the enroll button
     *
     * @return CoursePage instance
     */
    public CoursePage clickEnrollButton() {
        click(enrollButton);
        logger.info("Clicked enroll button");
        return this;
    }
    
    /**
     * Clicks the start course button
     *
     * @return LessonPage instance
     */
    public LessonPage clickStartCourseButton() {
        click(startCourseButton);
        logger.info("Clicked start course button");
        return new LessonPage();
    }
    
    /**
     * Clicks the resume course button
     *
     * @return LessonPage instance
     */
    public LessonPage clickResumeCourseButton() {
        click(resumeCourseButton);
        logger.info("Clicked resume course button");
        return new LessonPage();
    }
    
    /**
     * Gets the course progress percentage
     *
     * @return course progress percentage
     */
    public String getCourseProgressPercentage() {
        return getText(courseProgress);
    }
    
    /**
     * Gets the course rating
     *
     * @return course rating
     */
    public String getCourseRating() {
        return getText(rating);
    }
    
    /**
     * Clicks on a module by index
     *
     * @param index index of the module (0-based)
     * @return LessonPage instance
     */
    public LessonPage clickModule(int index) {
        if (index >= 0 && index < moduleItems.size()) {
            click(moduleItems.get(index));
            logger.info("Clicked on module at index: {}", index);
            return new LessonPage();
        } else {
            logger.error("Invalid module index: {}", index);
            throw new IndexOutOfBoundsException("Invalid module index: " + index);
        }
    }
    
    /**
     * Clicks the back to dashboard button
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickBackToDashboard() {
        click(backToDashboardButton);
        logger.info("Clicked back to dashboard button");
        return new DashboardPage();
    }
    
    /**
     * Checks if the enroll button is displayed
     *
     * @return true if the enroll button is displayed
     */
    public boolean isEnrollButtonDisplayed() {
        return isElementDisplayed(enrollButton);
    }
    
    /**
     * Checks if the start course button is displayed
     *
     * @return true if the start course button is displayed
     */
    public boolean isStartCourseButtonDisplayed() {
        return isElementDisplayed(startCourseButton);
    }
    
    /**
     * Checks if the resume course button is displayed
     *
     * @return true if the resume course button is displayed
     */
    public boolean isResumeCourseButtonDisplayed() {
        return isElementDisplayed(resumeCourseButton);
    }
}
