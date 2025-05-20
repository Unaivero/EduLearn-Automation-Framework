package com.edulearn.data;

import com.edulearn.core.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Test data loader to handle different data sources
 */
public class TestDataLoader {
    
    private static final Logger logger = LogManager.getLogger(TestDataLoader.class);
    private static final Map<String, Properties> propertiesCache = new HashMap<>();
    private static final ConfigManager configManager = ConfigManager.getInstance();
    
    private TestDataLoader() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Gets a test data property from specified file
     *
     * @param fileName name of the properties file without extension
     * @param key property key
     * @return property value or null if not found
     */
    public static String getProperty(String fileName, String key) {
        Properties props = loadPropertiesFile(fileName);
        String value = props.getProperty(key);
        
        if (value == null) {
            logger.warn("Property '{}' not found in file '{}'", key, fileName);
        }
        
        return value;
    }
    
    /**
     * Loads a properties file from test data directory
     *
     * @param fileName name of the properties file without extension
     * @return Properties object
     */
    private static Properties loadPropertiesFile(String fileName) {
        if (propertiesCache.containsKey(fileName)) {
            return propertiesCache.get(fileName);
        }
        
        Properties props = new Properties();
        String testDataDir = configManager.getTestDataDir();
        String filePath = testDataDir + File.separator + fileName + ".properties";
        
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
            propertiesCache.put(fileName, props);
            logger.debug("Loaded test data file: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to load test data file: {}", filePath, e);
        }
        
        return props;
    }
    
    /**
     * Gets an entire test data set as a map
     *
     * @param fileName name of the properties file without extension
     * @param prefix property prefix to filter by
     * @return Map of properties with given prefix
     */
    public static Map<String, String> getPropertiesWithPrefix(String fileName, String prefix) {
        Properties props = loadPropertiesFile(fileName);
        Map<String, String> result = new HashMap<>();
        
        for (String key : props.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                result.put(key.substring(prefix.length()), props.getProperty(key));
            }
        }
        
        return result;
    }
    
    /**
     * Creates a test data object from properties
     *
     * @param fileName name of the properties file
     * @param prefix property prefix
     * @param clazz class of the test data object
     * @param <T> type of the test data object
     * @return instance of test data object
     */
    public static <T> T createTestDataObject(String fileName, String prefix, Class<T> clazz) {
        Map<String, String> properties = getPropertiesWithPrefix(fileName, prefix);
        
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            
            // This is a simplified implementation - in a real project, you would
            // use reflection to set properties on the object based on the map values
            
            return instance;
        } catch (Exception e) {
            logger.error("Failed to create test data object", e);
            return null;
        }
    }
}
