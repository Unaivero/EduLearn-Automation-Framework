package com.edulearn.exceptions;

/**
 * Custom exception for test framework errors
 */
public class FrameworkException extends RuntimeException {
    
    /**
     * Constructor with error message
     *
     * @param message error message
     */
    public FrameworkException(String message) {
        super(message);
    }
    
    /**
     * Constructor with error message and cause
     *
     * @param message error message
     * @param cause cause of the exception
     */
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Factory method for element not found exception
     *
     * @param elementName name of the element
     * @return FrameworkException
     */
    public static FrameworkException elementNotFound(String elementName) {
        return new FrameworkException("Element not found: " + elementName);
    }
    
    /**
     * Factory method for element not clickable exception
     *
     * @param elementName name of the element
     * @return FrameworkException
     */
    public static FrameworkException elementNotClickable(String elementName) {
        return new FrameworkException("Element not clickable: " + elementName);
    }
    
    /**
     * Factory method for timeout exception
     *
     * @param operation operation that timed out
     * @return FrameworkException
     */
    public static FrameworkException timeout(String operation) {
        return new FrameworkException("Timeout occurred while: " + operation);
    }
    
    /**
     * Factory method for API error exception
     *
     * @param endpoint API endpoint
     * @param statusCode HTTP status code
     * @return FrameworkException
     */
    public static FrameworkException apiError(String endpoint, int statusCode) {
        return new FrameworkException("API error for endpoint " + endpoint + 
                " with status code: " + statusCode);
    }
    
    /**
     * Factory method for configuration error exception
     *
     * @param propertyName name of the missing property
     * @return FrameworkException
     */
    public static FrameworkException configurationError(String propertyName) {
        return new FrameworkException("Configuration error: Missing property: " + propertyName);
    }
    
    /**
     * Factory method for assertion error exception
     *
     * @param message assertion error message
     * @return FrameworkException
     */
    public static FrameworkException assertionError(String message) {
        return new FrameworkException("Assertion failed: " + message);
    }
}
