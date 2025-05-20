package com.edulearn.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the Lesson page
 */
public class LessonPage extends BasePage {
    
    @FindBy(id = "lesson-title")
    private WebElement lessonTitle;
    
    @FindBy(id = "lesson-content")
    private WebElement lessonContent;
    
    @FindBy(id = "video-player")
    private WebElement videoPlayer;
    
    @FindBy(id = "play-button")
    private WebElement playButton;
    
    @FindBy(id = "pause-button")
    private WebElement pauseButton;
    
    @FindBy(id = "next-lesson-button")
    private WebElement nextLessonButton;
    
    @FindBy(id = "previous-lesson-button")
    private WebElement previousLessonButton;
    
    @FindBy(id = "complete-lesson-button")
    private WebElement completeLessonButton;
    
    @FindBy(id = "back-to-course-button")
    private WebElement backToCourseButton;
    
    @FindBy(id = "lesson-notes")
    private WebElement lessonNotes;
    
    @FindBy(id = "save-notes-button")
    private WebElement saveNotesButton;
    
    /**
     * Gets the lesson title
     *
     * @return lesson title
     */
    public String getLessonTitle() {
        return getText(lessonTitle);
    }
    
    /**
     * Gets the lesson content
     *
     * @return lesson content
     */
    public String getLessonContent() {
        return getText(lessonContent);
    }
    
    /**
     * Clicks the play button
     *
     * @return LessonPage instance
     */
    public LessonPage clickPlayButton() {
        click(playButton);
        logger.info("Clicked play button");
        return this;
    }
    
    /**
     * Clicks the pause button
     *
     * @return LessonPage instance
     */
    public LessonPage clickPauseButton() {
        click(pauseButton);
        logger.info("Clicked pause button");
        return this;
    }
    
    /**
     * Clicks the next lesson button
     *
     * @return LessonPage instance
     */
    public LessonPage clickNextLessonButton() {
        click(nextLessonButton);
        logger.info("Clicked next lesson button");
        return this;
    }
    
    /**
     * Clicks the previous lesson button
     *
     * @return LessonPage instance
     */
    public LessonPage clickPreviousLessonButton() {
        click(previousLessonButton);
        logger.info("Clicked previous lesson button");
        return this;
    }
    
    /**
     * Clicks the complete lesson button
     *
     * @return LessonPage instance
     */
    public LessonPage clickCompleteLessonButton() {
        click(completeLessonButton);
        logger.info("Clicked complete lesson button");
        return this;
    }
    
    /**
     * Clicks the back to course button
     *
     * @return CoursePage instance
     */
    public CoursePage clickBackToCourseButton() {
        click(backToCourseButton);
        logger.info("Clicked back to course button");
        return new CoursePage();
    }
    
    /**
     * Enters lesson notes
     *
     * @param notes notes to enter
     * @return LessonPage instance
     */
    public LessonPage enterLessonNotes(String notes) {
        type(lessonNotes, notes);
        logger.info("Entered lesson notes");
        return this;
    }
    
    /**
     * Clicks the save notes button
     *
     * @return LessonPage instance
     */
    public LessonPage clickSaveNotesButton() {
        click(saveNotesButton);
        logger.info("Clicked save notes button");
        return this;
    }
    
    /**
     * Checks if the video player is displayed
     *
     * @return true if the video player is displayed
     */
    public boolean isVideoPlayerDisplayed() {
        return isElementDisplayed(videoPlayer);
    }
    
    /**
     * Checks if the next lesson button is enabled
     *
     * @return true if the next lesson button is enabled
     */
    public boolean isNextLessonButtonEnabled() {
        return nextLessonButton.isEnabled();
    }
    
    /**
     * Checks if the previous lesson button is enabled
     *
     * @return true if the previous lesson button is enabled
     */
    public boolean isPreviousLessonButtonEnabled() {
        return previousLessonButton.isEnabled();
    }
}
