package com.edulearn.pages;

import com.edulearn.core.DriverFactory;
import com.edulearn.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all Page Object classes
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;
    protected final Logger logger = LogManager.getLogger(this.getClass());
    
    /**
     * Constructor
     */
    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        waitForPageLoad();
    }
    
    /**
     * Waits for page to be completely loaded
     */
    protected void waitForPageLoad() {
        WaitUtils.waitForPageLoad(driver);
    }
    
    /**
     * Clicks on an element with built-in wait
     *
     * @param element the WebElement to click
     */
    protected void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.debug("Clicked on element: {}", element);
        } catch (ElementClickInterceptedException e) {
            logger.warn("Element click intercepted, trying JavaScript click");
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            logger.error("Failed to click element", e);
            throw e;
        }
    }
    
    /**
     * Types text into an input field with built-in wait
     *
     * @param element the WebElement to type into
     * @param text the text to type
     */
    protected void type(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            logger.debug("Typed '{}' into element: {}", text, element);
        } catch (Exception e) {
            logger.error("Failed to type text", e);
            throw e;
        }
    }
    
    /**
     * Selects an option from a dropdown by visible text
     *
     * @param element the dropdown WebElement
     * @param visibleText the text of the option to select
     */
    protected void selectByVisibleText(WebElement element, String visibleText) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            Select select = new Select(element);
            select.selectByVisibleText(visibleText);
            logger.debug("Selected option '{}' from dropdown", visibleText);
        } catch (Exception e) {
            logger.error("Failed to select option by visible text", e);
            throw e;
        }
    }
    
    /**
     * Checks if an element is displayed
     *
     * @param element the WebElement to check
     * @return true if the element is displayed
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    
    /**
     * Gets text from an element with built-in wait
     *
     * @param element the WebElement to get text from
     * @return the text of the element
     */
    protected String getText(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.getText();
        } catch (Exception e) {
            logger.error("Failed to get text from element", e);
            throw e;
        }
    }
    
    /**
     * Hovers over an element
     *
     * @param element the WebElement to hover over
     */
    protected void hoverOver(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            actions.moveToElement(element).perform();
            logger.debug("Hovered over element: {}", element);
        } catch (Exception e) {
            logger.error("Failed to hover over element", e);
            throw e;
        }
    }
    
    /**
     * Scrolls to an element
     *
     * @param element the WebElement to scroll to
     */
    protected void scrollToElement(WebElement element) {
        try {
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            logger.debug("Scrolled to element: {}", element);
        } catch (Exception e) {
            logger.error("Failed to scroll to element", e);
            throw e;
        }
    }
    
    /**
     * Waits for an element to be visible
     *
     * @param element the WebElement to wait for
     * @return the visible WebElement
     */
    protected WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Waits for an element to be clickable
     *
     * @param element the WebElement to wait for
     * @return the clickable WebElement
     */
    protected WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Finds elements by locator with built-in wait
     *
     * @param locator the By locator
     * @return list of WebElements
     */
    protected List<WebElement> findElements(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }
    
    /**
     * Refreshes the current page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
        waitForPageLoad();
        logger.debug("Page refreshed");
    }
    
    /**
     * Gets the current page URL
     *
     * @return the current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Gets the current page title
     *
     * @return the page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
}
