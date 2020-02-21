package com.infogain.automation.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.infogain.automation.utilities.AutomationEmailUtility;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class adds the attachments to mail and send the mail. If we have given properties as skipUI then
 * it also closes the application
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 11, 2019
 */
@Service
public class MailService {

    private static final Logger logger = LogManager.getLogger(MailService.class);

    private final AutomationEmailUtility automationEmailUtility;

    @Autowired
    public MailService(final AutomationEmailUtility automationEmailUtility) {
        this.automationEmailUtility = automationEmailUtility;
    }

    /**
     * This method attach the Report of test Cases and send the mail
     * 
     * @param automationReportFileName - Name of Automation Report File
     * @since Dec 11, 2019
     */
    @Async
    public void attachReportAndProcessMail(String automationReportFileName) {
        logger.traceEntry("attachReportAndProcessMail method of MailService class");
        automationEmailUtility.addAttachment(automationReportFileName);
        automationEmailUtility.sendMail();
        logger.traceExit();
    }
}
