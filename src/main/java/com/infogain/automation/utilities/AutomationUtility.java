package com.infogain.automation.utilities;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

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

    public void openUrlInBrowser(String url) {
        try {
            logger.info("Opening requestURL : {}", url);
            Runtime rt = Runtime.getRuntime();
            rt.exec("rundll32 requestURL.dll,FileProtocolHandler " + url);
        } catch (IOException e) {
            logger.debug("Exception occured while opening UI {} ", ExceptionUtils.getStackTrace(e));
        }
    }

}
