package com.edulearn.api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * API client for user authentication
 */
public class AuthAPI extends BaseAPI {
    
    private static final Logger logger = LogManager.getLogger(AuthAPI.class);
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String LOGOUT_ENDPOINT = "/api/auth/logout";
    
    /**
     * Performs login and returns auth token
     *
     * @param username username
     * @param password password
     * @return authentication token
     */
    public String login(String username, String password) {
        logger.info("Performing login for user: {}", username);
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);
        
        Response response = post(LOGIN_ENDPOINT, requestBody);
        
        if (response.getStatusCode() == 200) {
            String token = response.jsonPath().getString("token");
            setAuthToken(token);
            logger.info("Login successful");
            return token;
        } else {
            logger.error("Login failed with status code: {}", response.getStatusCode());
            return null;
        }
    }
    
    /**
     * Performs logout
     *
     * @return true if logout was successful
     */
    public boolean logout() {
        logger.info("Performing logout");
        
        Response response = post(LOGOUT_ENDPOINT, null);
        
        boolean successful = response.getStatusCode() == 200;
        if (successful) {
            setAuthToken(null);
            logger.info("Logout successful");
        } else {
            logger.error("Logout failed with status code: {}", response.getStatusCode());
        }
        
        return successful;
    }
}
