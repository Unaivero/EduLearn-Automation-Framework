package com.edulearn.api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API client for courses
 */
public class CourseAPI extends BaseAPI {
    
    private static final Logger logger = LogManager.getLogger(CourseAPI.class);
    private static final String COURSES_ENDPOINT = "/api/courses";
    
    /**
     * Constructor with auth token
     *
     * @param authToken authentication token
     */
    public CourseAPI(String authToken) {
        super(authToken);
    }
    
    /**
     * Gets all courses
     *
     * @return Response with all courses
     */
    public Response getAllCourses() {
        logger.info("Getting all courses");
        return get(COURSES_ENDPOINT);
    }
    
    /**
     * Gets a specific course by ID
     *
     * @param courseId course ID
     * @return Response with course details
     */
    public Response getCourseById(String courseId) {
        logger.info("Getting course by ID: {}", courseId);
        return get(COURSES_ENDPOINT + "/" + courseId);
    }
    
    /**
     * Searches for courses with keyword
     *
     * @param keyword search keyword
     * @return Response with search results
     */
    public Response searchCourses(String keyword) {
        logger.info("Searching courses with keyword: {}", keyword);
        
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("q", keyword);
        
        return get(COURSES_ENDPOINT + "/search", queryParams);
    }
    
    /**
     * Enrolls in a course
     *
     * @param courseId course ID
     * @return Response with enrollment result
     */
    public Response enrollInCourse(String courseId) {
        logger.info("Enrolling in course: {}", courseId);
        return post(COURSES_ENDPOINT + "/" + courseId + "/enroll", null);
    }
    
    /**
     * Gets enrolled courses for the current user
     *
     * @return Response with enrolled courses
     */
    public Response getEnrolledCourses() {
        logger.info("Getting enrolled courses");
        return get(COURSES_ENDPOINT + "/enrolled");
    }
    
    /**
     * Gets course progress
     *
     * @param courseId course ID
     * @return Response with course progress
     */
    public Response getCourseProgress(String courseId) {
        logger.info("Getting progress for course: {}", courseId);
        return get(COURSES_ENDPOINT + "/" + courseId + "/progress");
    }
    
    /**
     * Updates course progress
     *
     * @param courseId course ID
     * @param lessonId lesson ID
     * @param completed whether the lesson is completed
     * @return Response with update result
     */
    public Response updateLessonProgress(String courseId, String lessonId, boolean completed) {
        logger.info("Updating progress for course: {}, lesson: {}, completed: {}", 
                courseId, lessonId, completed);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("lessonId", lessonId);
        requestBody.put("completed", completed);
        
        return post(COURSES_ENDPOINT + "/" + courseId + "/progress", requestBody);
    }
    
    /**
     * Gets course lessons
     *
     * @param courseId course ID
     * @return Response with course lessons
     */
    public Response getCourseLessons(String courseId) {
        logger.info("Getting lessons for course: {}", courseId);
        return get(COURSES_ENDPOINT + "/" + courseId + "/lessons");
    }
    
    /**
     * Gets lesson details
     *
     * @param courseId course ID
     * @param lessonId lesson ID
     * @return Response with lesson details
     */
    public Response getLessonDetails(String courseId, String lessonId) {
        logger.info("Getting details for course: {}, lesson: {}", courseId, lessonId);
        return get(COURSES_ENDPOINT + "/" + courseId + "/lessons/" + lessonId);
    }
}
