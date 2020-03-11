package com.infogain.automation.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.infogain.automation.exception.AutomationException;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is for reading properties from Peripheral Properties file externally with single
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationProperties {

    private static final Logger logger = LogManager.getLogger(AutomationProperties.class);

    public static String propertyFilePath;

    private Properties props;

    /**
     * This method reads the properties from Automation Properties file and throws Exception if any error occurred
     */
    public AutomationProperties() {
        try (InputStream input = StringUtils.isNotBlank(propertyFilePath) ? new FileInputStream(propertyFilePath)
                        : this.getClass().getClassLoader().getResourceAsStream("application.properties");) {
            props = new Properties();
            props.load(input);
        } catch (IOException ex) {
            logger.debug("Exception Occured While Reading Properties File With Path {} : {} ", propertyFilePath,
                            ExceptionUtils.getStackTrace(ex));
            throw new AutomationException(
                            "Exception Occured While Reading Properties File With Path " + propertyFilePath, ex);
        }
    }

    public String getProperty(Object key) {
        return (String) props.get(key);
    };

    public String getKeyNameByValue(String value) {
        String urlParameterKey = null;
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            if (entry.getValue().equals(value)) {
                urlParameterKey = (String) entry.getKey();
                break;
            }
        }
        return urlParameterKey;
    }

    public void reloadProperties() {
        Properties newProperties;
        try (InputStream input = new FileInputStream(propertyFilePath)) {
            newProperties = new Properties();
            newProperties.load(input);
            props = newProperties;
        } catch (IOException ex) {
            logger.debug("Exception Occured While Reading Properties File With Path {} : {} ", propertyFilePath,
                            ExceptionUtils.getStackTrace(ex));
        }
    }
}
