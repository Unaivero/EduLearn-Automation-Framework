package com.edulearn.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration manager to handle properties and settings
 */
public class ConfigManager {
    
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();
    private static ConfigManager instance;
    
    private ConfigManager() {
        loadProperties();
    }
    
    /**
     * Gets the single instance of ConfigManager
     * 
     * @return ConfigManager instance
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    /**
     * Loads properties from config.properties file
     */
    private void loadProperties() {
        try {
            String configFilePath = System.getProperty("config.file");
            
            if (configFilePath == null) {
                configFilePath = "src/test/resources/config.properties";
            }
            
            FileInputStream inputStream = new FileInputStream(configFilePath);
            properties.load(inputStream);
            inputStream.close();
            logger.info("Loaded configuration from: {}", configFilePath);
        } catch (IOException e) {
            logger.error("Failed to load configuration properties", e);
        }
    }
    
    /**
     * Gets a property value as a String
     * 
     * @param key property key
     * @return property value
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found: {}", key);
        }
        return value;
    }
    
    /**
     * Gets a property value as a boolean
     * 
     * @param key property key
     * @return property value as boolean
     */
    public boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }
    
    /**
     * Gets a property value as an integer
     * 
     * @param key property key
     * @return property value as int
     */
    public int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse property {} as integer: {}", key, value);
            return 0;
        }
    }
    
    /**
     * Gets the base URL for the application
     * 
     * @return base URL
     */
    public String getBaseUrl() {
        return getProperty("base.url");
    }
    
    /**
     * Gets the browser to use for tests
     * 
     * @return browser name
     */
    public String getBrowser() {
        return getProperty("browser");
    }
    
    /**
     * Checks if tests should run in headless mode
     * 
     * @return true if headless mode is enabled
     */
    public boolean isHeadless() {
        return getBooleanProperty("headless");
    }
    
    /**
     * Gets the default timeout in seconds
     * 
     * @return timeout in seconds
     */
    public int getDefaultTimeout() {
        return getIntProperty("default.timeout");
    }
    
    /**
     * Gets the test data directory path
     * 
     * @return test data directory path
     */
    public String getTestDataDir() {
        return getProperty("testdata.dir");
    }
}
