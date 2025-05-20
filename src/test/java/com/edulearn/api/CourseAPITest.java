package com.edulearn.api;

import com.edulearn.core.ConfigManager;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class for Course API
 */
public class CourseAPITest {
    
    private static final Logger logger = LogManager.getLogger(CourseAPITest.class);
    private static final ConfigManager configManager = ConfigManager.getInstance();
    
    private AuthAPI authAPI;
    private CourseAPI courseAPI;
    private String authToken;
    
    @BeforeClass
    public void setup() {
        logger.info("Setting up CourseAPITest");
        
        authAPI = new AuthAPI();
        
        // Login and get token
        String username = configManager.getProperty("student.username");
        String password = configManager.getProperty("student.password");
        
        authToken = authAPI.login(username, password);
        Assert.assertNotNull(authToken, "Authentication token should not be null");
        
        courseAPI = new CourseAPI(authToken);
    }
    
    @Test(description = "Verify get all courses API")
    public void testGetAllCourses() {
        logger.info("Testing get all courses API");
        
        Response response = courseAPI.getAllCourses();
        
        // Verify status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        
        // Verify response body
        JsonPath jsonPath = response.jsonPath();
        List<Object> courses = jsonPath.getList("courses");
        
        Assert.assertNotNull(courses, "Courses list should not be null");
        Assert.assertFalse(courses.isEmpty(), "Courses list should not be empty");
        
        logger.info("Found {} courses", courses.size());
    }
    
    @Test(description = "Verify search courses API")
    public void testSearchCourses() {
        logger.info("Testing search courses API");
        
        String keyword = "Java";
        Response response = courseAPI.searchCourses(keyword);
        
        // Verify status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        
        // Verify response body
        JsonPath jsonPath = response.jsonPath();
        List<Object> courses = jsonPath.getList("courses");
        
        Assert.assertNotNull(courses, "Courses list should not be null");
        
        // If courses were found, verify they contain the keyword
        if (!courses.isEmpty()) {
            for (Object course : courses) {
                String title = jsonPath.getString("courses[" + courses.indexOf(course) + "].title");
                String description = jsonPath.getString("courses[" + courses.indexOf(course) + "].description");
                
                boolean containsKeyword = (title != null && title.contains(keyword)) || 
                                         (description != null && description.contains(keyword));
                
                Assert.assertTrue(containsKeyword, 
                        "Course should contain keyword in title or description");
            }
        }
        
        logger.info("Found {} courses matching keyword '{}'", courses.size(), keyword);
    }
    
    @Test(description = "Verify get course by ID API")
    public void testGetCourseById() {
        logger.info("Testing get course by ID API");
        
        // First, get all courses to find a valid ID
        Response allCoursesResponse = courseAPI.getAllCourses();
        Assert.assertEquals(allCoursesResponse.getStatusCode(), 200, "Status code should be 200");
        
        JsonPath jsonPath = allCoursesResponse.jsonPath();
        List<Object> courses = jsonPath.getList("courses");
        
        if (!courses.isEmpty()) {
            String courseId = jsonPath.getString("courses[0].id");
            Assert.assertNotNull(courseId, "Course ID should not be null");
            
            // Get course by ID
            Response courseResponse = courseAPI.getCourseById(courseId);
            
            // Verify status code
            Assert.assertEquals(courseResponse.getStatusCode(), 200, "Status code should be 200");
            
            // Verify response body
            JsonPath courseJsonPath = courseResponse.jsonPath();
            String title = courseJsonPath.getString("title");
            String description = courseJsonPath.getString("description");
            
            Assert.assertNotNull(title, "Course title should not be null");
            Assert.assertNotNull(description, "Course description should not be null");
            
            logger.info("Successfully retrieved course: {}", title);
        } else {
            logger.warn("No courses found to test getCourseById");
        }
    }
    
    @Test(description = "Verify get enrolled courses API")
    public void testGetEnrolledCourses() {
        logger.info("Testing get enrolled courses API");
        
        Response response = courseAPI.getEnrolledCourses();
        
        // Verify status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        
        // Verify response body
        JsonPath jsonPath = response.jsonPath();
        List<Object> enrolledCourses = jsonPath.getList("courses");
        
        Assert.assertNotNull(enrolledCourses, "Enrolled courses list should not be null");
        
        logger.info("Found {} enrolled courses", enrolledCourses.size());
    }
    
    @AfterClass
    public void tearDown() {
        logger.info("Tearing down CourseAPITest");
        
        // Logout
        if (authToken != null) {
            boolean logoutSuccessful = authAPI.logout();
            Assert.assertTrue(logoutSuccessful, "Logout should be successful");
        }
    }
}
