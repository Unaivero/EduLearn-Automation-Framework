package com.edulearn.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for waiting operations
 */
public class WaitUtils {
    
    private static final Logger logger = LogManager.getLogger(WaitUtils.class);
    
    private WaitUtils() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Waits for page to be completely loaded
     *
     * @param driver WebDriver instance
     */
    public static void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        try {
            // Wait for DOM to be ready
            wait.until((ExpectedCondition<Boolean>) wd -> 
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
            
            // Wait for jQuery if it's present
            ExpectedCondition<Boolean> jQueryLoad = driver1 -> {
                try {
                    return ((Long) ((JavascriptExecutor) driver1)
                            .executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    // jQuery is not available
                    return true;
                }
            };
            wait.until(jQueryLoad);
            
            logger.debug("Page loaded completely");
        } catch (Exception e) {
            logger.warn("Timeout waiting for page load", e);
        }
    }
    
    /**
     * Waits for a specified amount of time
     *
     * @param milliseconds time to wait in milliseconds
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep interrupted", e);
        }
    }
    
    /**
     * Waits for an element to be in viewport
     *
     * @param driver WebDriver instance
     * @param element WebElement to check
     * @return true if element is in viewport
     */
    public static boolean waitForElementInViewport(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        return wait.until(driver1 -> {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            return (Boolean) js.executeScript(
                    "var rect = arguments[0].getBoundingClientRect(); " +
                    "return (rect.top >= 0 && rect.left >= 0 && " +
                    "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                    "rect.right <= (window.innerWidth || document.documentElement.clientWidth));", 
                    element);
        });
    }
    
    /**
     * Waits for an AJAX call to complete
     *
     * @param driver WebDriver instance
     */
    public static void waitForAjax(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        try {
            wait.until(driver1 -> {
                JavascriptExecutor js = (JavascriptExecutor) driver1;
                return (Boolean) js.executeScript(
                        "return (typeof jQuery !== 'undefined') ? jQuery.active === 0 : true");
            });
            logger.debug("AJAX calls completed");
        } catch (Exception e) {
            logger.warn("Timeout waiting for AJAX calls to complete", e);
        }
    }
    
    /**
     * Waits for animations to complete
     *
     * @param driver WebDriver instance
     */
    public static void waitForAnimations(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            wait.until(driver1 -> {
                JavascriptExecutor js = (JavascriptExecutor) driver1;
                return (Boolean) js.executeScript(
                        "return (typeof jQuery !== 'undefined') ? jQuery(':animated').length === 0 : true");
            });
            logger.debug("Animations completed");
        } catch (Exception e) {
            logger.warn("Timeout waiting for animations to complete", e);
        }
    }
}
