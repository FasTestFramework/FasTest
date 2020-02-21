package com.infogain.automation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.infogain.automation.properties.AutomationProperties;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * <br>
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is main class for FasTest application
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since 18-Nov-2019
 */
@SpringBootApplication
public class FasTestAutomationApplication {

    private static final Logger logger = LogManager.getLogger(FasTestAutomationApplication.class);

    /**
     * This method is the main method which takes arguments and invoke Listener
     * 
     * @param args Runtime Arguments
     * @since Nov 27, 2019
     */
    public static void main(String[] args) {
        logger.traceEntry("main method of FasTestAutomationApplication class");
        for (int i = 0; i < args.length; i++) {
            if (args[i].indexOf(".properties") != -1) {
                AutomationProperties.propertyFilePath = args[i];
                logger.info("Properties path is set to : {}", args[i]);
            }
        }
        SpringApplication.run(FasTestAutomationApplication.class, args);
        logger.traceExit();
    }

}
