package com.edulearn.core;

/**
 * Constants used throughout the framework
 */
public class Constants {
    
    // Timeouts
    public static final int DEFAULT_TIMEOUT = 10;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    public static final int AJAX_TIMEOUT = 15;
    public static final int ANIMATION_TIMEOUT = 5;
    
    // Directories
    public static final String SCREENSHOTS_DIR = "target/screenshots/";
    public static final String REPORTS_DIR = "target/extent-reports/";
    public static final String DOWNLOADS_DIR = "target/downloads/";
    public static final String TESTDATA_DIR = "src/test/resources/testdata/";
    
    // Files
    public static final String CONFIG_FILE = "src/test/resources/config.properties";
    public static final String LOG_CONFIG_FILE = "src/test/resources/log4j2.xml";
    public static final String TESTNG_FILE = "src/test/resources/testng.xml";
    
    // Browser names
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE = "edge";
    public static final String SAFARI = "safari";
    
    // User types
    public static final String STUDENT = "student";
    public static final String INSTRUCTOR = "instructor";
    public static final String ADMIN = "admin";
    
    // API Endpoints
    public static final String BASE_API_URL = "https://api.edulearn-demo.example.com";
    public static final String AUTH_ENDPOINT = "/api/auth";
    public static final String COURSES_ENDPOINT = "/api/courses";
    public static final String USERS_ENDPOINT = "/api/users";
    
    // UI Pages
    public static final String LOGIN_PAGE = "/login";
    public static final String DASHBOARD_PAGE = "/dashboard";
    public static final String COURSE_PAGE = "/course";
    public static final String LESSON_PAGE = "/lesson";
    public static final String SEARCH_PAGE = "/search";
    
    // Page titles
    public static final String LOGIN_PAGE_TITLE = "EduLearn - Login";
    public static final String DASHBOARD_PAGE_TITLE = "EduLearn - Dashboard";
    public static final String COURSE_PAGE_TITLE = "EduLearn - Course Details";
    public static final String LESSON_PAGE_TITLE = "EduLearn - Lesson";
    public static final String SEARCH_PAGE_TITLE = "EduLearn - Search Results";
    
    private Constants() {
        // Private constructor to prevent instantiation
    }
}
