package com.edulearn.api;

import com.edulearn.core.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Base API class with common methods for API testing
 */
public class BaseAPI {
    
    private static final Logger logger = LogManager.getLogger(BaseAPI.class);
    private static final ConfigManager configManager = ConfigManager.getInstance();
    
    static {
        RestAssured.baseURI = configManager.getProperty("api.base.url");
        RestAssured.useRelaxedHTTPSValidation();
    }
    
    private String authToken;
    
    /**
     * Constructor with auth token
     *
     * @param authToken authentication token
     */
    public BaseAPI(String authToken) {
        this.authToken = authToken;
    }
    
    /**
     * Default constructor
     */
    public BaseAPI() {
        this.authToken = null;
    }
    
    /**
     * Sets authentication token
     *
     * @param authToken authentication token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    
    /**
     * Creates a request specification with authentication
     *
     * @return RequestSpecification with auth token if available
     */
    protected RequestSpecification createRequest() {
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON);
        
        if (authToken != null && !authToken.isEmpty()) {
            request.header("Authorization", "Bearer " + authToken);
        }
        
        return request;
    }
    
    /**
     * Performs a GET request
     *
     * @param endpoint API endpoint
     * @return Response object
     */
    public Response get(String endpoint) {
        logger.info("Performing GET request to: {}", endpoint);
        
        Response response = createRequest()
                .get(endpoint);
        
        logResponse(response);
        return response;
    }
    
    /**
     * Performs a GET request with query parameters
     *
     * @param endpoint API endpoint
     * @param queryParams query parameters
     * @return Response object
     */
    public Response get(String endpoint, Map<String, String> queryParams) {
        logger.info("Performing GET request to: {} with query params: {}", endpoint, queryParams);
        
        Response response = createRequest()
                .queryParams(queryParams)
                .get(endpoint);
        
        logResponse(response);
        return response;
    }
    
    /**
     * Performs a POST request with request body
     *
     * @param endpoint API endpoint
     * @param requestBody request body object
     * @return Response object
     */
    public Response post(String endpoint, Object requestBody) {
        logger.info("Performing POST request to: {}", endpoint);
        
        Response response = createRequest()
                .body(requestBody)
                .post(endpoint);
        
        logResponse(response);
        return response;
    }
    
    /**
     * Performs a PUT request with request body
     *
     * @param endpoint API endpoint
     * @param requestBody request body object
     * @return Response object
     */
    public Response put(String endpoint, Object requestBody) {
        logger.info("Performing PUT request to: {}", endpoint);
        
        Response response = createRequest()
                .body(requestBody)
                .put(endpoint);
        
        logResponse(response);
        return response;
    }
    
    /**
     * Performs a DELETE request
     *
     * @param endpoint API endpoint
     * @return Response object
     */
    public Response delete(String endpoint) {
        logger.info("Performing DELETE request to: {}", endpoint);
        
        Response response = createRequest()
                .delete(endpoint);
        
        logResponse(response);
        return response;
    }
    
    /**
     * Logs response details
     *
     * @param response Response object
     */
    private void logResponse(Response response) {
        logger.info("Response Status Code: {}", response.getStatusCode());
        logger.debug("Response Body: {}", response.getBody().asString());
    }
}
