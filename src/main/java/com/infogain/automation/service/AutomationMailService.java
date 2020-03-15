package com.infogain.automation.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.dto.SendMailRequestDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.properties.AutomationProperties;
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
public class AutomationMailService {

    private static final Logger logger = LogManager.getLogger(AutomationMailService.class);

    private final AutomationEmailUtility automationEmailUtility;
    private final AutomationProperties automationProperties;

    @Autowired
    public AutomationMailService(final AutomationEmailUtility automationEmailUtility,
                    final AutomationProperties automationProperties) {
        this.automationEmailUtility = automationEmailUtility;
        this.automationProperties = automationProperties;
    }

    /**
     * This method attach the Report of test Cases and send the mail
     * 
     * @param automationReportFileName - Name of Automation Report File
     * @since Dec 11, 2019
     */
    public void attachReportAndProcessMail(SendMailRequestDTO sendMailRequestDTO) {
        logger.traceEntry("attachReportAndProcessMail method of AutomationMailService class");
        String fileBase64Data = sendMailRequestDTO.getFileBase64Data();
        byte[] decoder = Base64.getDecoder().decode(fileBase64Data);
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss_a"));
        String automationReportFileName =
                        automationProperties.getPropertyAsString(AutomationConstants.FASTEST_OUTPUT_FOLDER_PATH) + "/"
                                        + "AutomationTestingReport_" + dateTime + ".pdf";
        // Creating PDF document object
        try (PDDocument document = PDDocument.load(decoder)) {
            // Saving the document
            document.save(automationReportFileName);
            logger.info("PDF Report File Saved");
        } catch (Exception e) {
            logger.debug("Exception Occured While Saving PDF Report {} ", ExceptionUtils.getStackTrace(e));
            throw new FastTestBadRequestException(
                            new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_SEND_MAIL_INVALID_BASE64_DATA_EXCEPTION,
                                            fileBase64Data, "Could not generate PDF as base64 string is invalid."));
        }
        automationEmailUtility.addAttachment(automationReportFileName);
        automationEmailUtility.sendMail();
        logger.traceExit();
    }
}
