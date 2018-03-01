package alf.integration.service.base;

import java.util.NoSuchElementException;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.log4j.Logger;
/**
 * {@code ResourceManager} loads application properties.
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 * @see org.apache.commons.configuration
 * @since commons-configuration 1.9
 */
public class ResourceManager {
    
    /** The log. */
    private static Logger LOG = Logger.getLogger(ResourceManager.class);
    
    /** The Constant RESOURCE_BUNDLE. */
    public static final String RESOURCE_BUNDLE = "service.properties";
    
    /** The config. */
    public static CompositeConfiguration config;

    /** Loads the configuration files. */
    static {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Loading configuration properties");
        }
        config = new CompositeConfiguration();
        config.addConfiguration(new SystemConfiguration());
        try {
            config.addConfiguration(new PropertiesConfiguration(RESOURCE_BUNDLE));
        } catch (ConfigurationException e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            }             
            throw new PropertiesLoadException("Could not load properties files", e);
        }
        config.setThrowExceptionOnMissing(true);
        if (LOG.isDebugEnabled()) {
            //log.debug("Properties loaded from " + RESOURCE_BUNDLE);
        }
    }

    /**
     * Gets the config.
     *
     * @return the config
     */
    public static Configuration getConfig() {
        return config;
    }

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(final String key) {
        try {
            String value = config.getString(key);
            if (LOG.isTraceEnabled()) {
                LOG.trace("Found property [" + key + ":" + value + "] from [" + RESOURCE_BUNDLE + "].");
            }
            return value;
        } catch (NoSuchElementException e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            }             
            String message = "Could not load property [" + key + "] from [" + RESOURCE_BUNDLE + "].";
            throw new PropertiesLoadException(message, e);
        }
    }

    /**
     * Save property.
     *
     * @param key the key
     * @param value the value
     */
    public static void saveProperty(String key, Object value) {
        config.setProperty(key, value);
    }
}
