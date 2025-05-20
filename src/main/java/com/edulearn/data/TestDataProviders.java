package com.edulearn.data;

import org.testng.annotations.DataProvider;

/**
 * TestNG data providers for parameterized tests
 */
public class TestDataProviders {
    
    /**
     * Data provider for login scenarios
     *
     * @return Object[][] with username and password combinations
     */
    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return new Object[][] {
            // Format: { username, password, expectedResult }
            { "student", "Learn@123", true },
            { "invalid_user", "invalid_pass", false },
            { "student", "wrong_password", false },
            { "admin", "Admin@123", true }
        };
    }
    
    /**
     * Data provider for course search keywords
     *
     * @return Object[][] with search keywords and expected minimum results
     */
    @DataProvider(name = "searchKeywords")
    public static Object[][] getSearchKeywords() {
        return new Object[][] {
            // Format: { keyword, expectedMinimumResults }
            { "Java", 1 },
            { "Python", 1 },
            { "Programming", 2 },
            { "Database", 1 },
            { "xyzxyzxyz123456", 0 }
        };
    }
    
    /**
     * Data provider for course enrollment
     *
     * @return Object[][] with course names
     */
    @DataProvider(name = "courseData")
    public static Object[][] getCourseData() {
        return new Object[][] {
            // Format: { courseName }
            { "Introduction to Java Programming" },
            { "Python for Beginners" },
            { "Advanced Web Development" },
            { "Database Design Fundamentals" }
        };
    }
}
