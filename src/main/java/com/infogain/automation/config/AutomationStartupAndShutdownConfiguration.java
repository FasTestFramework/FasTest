package com.infogain.automation.config;

import java.util.Properties;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.utilities.AutomationUtility;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * <br>
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Design and Architecture<br>
 * Description - This class consists of startup configuration tasks
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since 10-Jul-2019
 */
@Configuration
public class AutomationStartupAndShutdownConfiguration {

    private Properties automationProperties;
    private AutomationUtility automationUtility;

    public AutomationStartupAndShutdownConfiguration(final AutomationProperties automationProperties,
                    final AutomationUtility automationUtility) {
        this.automationProperties = automationProperties.getProps();
        this.automationUtility = automationUtility;
    }

    /**
     * This method open home page of UI at startup
     * 
     * @since 10-Jul-2019
     */
    @EventListener(ApplicationReadyEvent.class)
    public void doStartuptasks() {
        String url = "http://localhost:" + automationProperties.getProperty(AutomationConstants.FASTEST_SERVER_PORT);
        automationUtility.openUrlInBrowser(url);

    }
}
