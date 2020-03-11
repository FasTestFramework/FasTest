package com.infogain.automation.utilities;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.infogain.automation.exception.AutomationException;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is automation utility class
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationUtility {
    private static final Logger logger = LogManager.getLogger(AutomationUtility.class);
    public static final String COMMA_SEPERATED_REGEX = "(.,)*.";

    public void openUrlInBrowser(String url) {
        try {
            logger.info("Opening requestURL : {}", url);
            Runtime rt = Runtime.getRuntime();
            rt.exec("rundll32 requestURL.dll,FileProtocolHandler " + url);
        } catch (IOException e) {
            logger.debug("Exception occured while opening UI {} ", ExceptionUtils.getStackTrace(e));
        }
    }

    public Integer extractIntegerOfString(String text) {
        if (text == null) {
            return null;
        }
        Integer integer = null;
        try {
            integer = Double.valueOf(text).intValue();
        } catch (NumberFormatException e) {
            // do nothing
        }
        return integer;
    }

    public HttpMethod getRequestMethodType(String requestType) {
        try {
            return HttpMethod.valueOf(requestType.toUpperCase());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new AutomationException("Wrong Http Method is defined in Properties file i.e. " + requestType);
        }
    }

    public String fetchCommaSeperatedValueRegex(String value) {
        return "(" + value + ",)*" + value;
    }

}
