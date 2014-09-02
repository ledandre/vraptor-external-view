package vraptor.external.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Leandro Tomaz Andre
 *
 */
public class Config {

    private static Logger logger = LoggerFactory.getLogger(Config.class);

    private static final String PROPERTIES_FILE = "vraptor-external.properties";

    public static PropertiesConfiguration getConfiguration() {
        logger.info("Invoking config constructor");
        PropertiesConfiguration config = null;

        try {
            config = new PropertiesConfiguration(PROPERTIES_FILE);
            config.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            logger.error("Cannot load configuration file!", e);
        }

        return config;
    }

    private static String readStringProperty(String propertyName) {
        return getConfiguration().getString(propertyName);
    }

    public static String getDefaultViewPath() {
        return readStringProperty("default.view.path");
    }

    public static String getDefaultViewExtension() {
        return readStringProperty("default.view.extension");
    }
}