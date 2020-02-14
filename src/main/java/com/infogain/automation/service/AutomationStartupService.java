package com.infogain.automation.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.tests.AutomationAbstractTests;
import com.infogain.automation.tests.AutomationDefaultTest;
import com.infogain.automation.utilities.AutomationEmailUtility;
import com.infogain.automation.utilities.AutomationExcelUtility;

@Service
public class AutomationStartupService {

    private static final Logger logger = LogManager.getLogger(AutomationStartupService.class);

    private final Properties automationProperties;
    private final AutomationReportService automationReportService;
    private final AutomationExcelUtility automationExcelUtility;
    private final AutomationEmailUtility automationEmailUtility;

    @Autowired
    public AutomationStartupService(final AutomationProperties automationProperties,
                    final AutomationReportService automationReportService,
                    final AutomationExcelUtility automationExcelUtility,
                    final AutomationEmailUtility automationEmailUtility) {
        this.automationProperties = automationProperties.getProps();
        this.automationReportService = automationReportService;
        this.automationExcelUtility = automationExcelUtility;
        this.automationEmailUtility = automationEmailUtility;
    }

    public void runTestCases(boolean sendMail, boolean saveDataToDatabase) {
        logger.traceEntry("runTestCases method of AutomationStartupService class");
        String[] excelFileNames = automationProperties
                        .getProperty(AutomationConstants.FASTEST_EXCEL_SHEET_NAME).trim().split("\\|");
        String[] allExcelSheetNames = automationProperties
                        .getProperty(AutomationConstants.APPLICATION_PROPERTIES_SHEET_NAME).trim().split("\\|");
        // For Loop to find the Test Cases Executed For Given excels and sheets
        int lastExecutedTestCount = 0;
        List<String> attachments = new ArrayList<>();
        for (int i = 0; i < excelFileNames.length; i++) {
            String[] excelSheetNames = allExcelSheetNames[i].split(",");
            for (int j = 0; j < excelSheetNames.length; j++) {
                lastExecutedTestCount = lastExecutedTestCount + automationReportService
                                .getLastTestCaseExecuted(excelFileNames[i], excelSheetNames[j]);
                Class<?> classFound;
                try {
                    classFound = Class.forName(AutomationConstants.TEST_CLASS_PACKAGE_NAME + excelSheetNames[j]);
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    classFound = AutomationDefaultTest.class;
                }
                try {
                    AutomationAbstractTests automationAbstractTests =
                                    (AutomationAbstractTests) classFound.newInstance();
                    automationAbstractTests.init(excelFileNames[i], excelSheetNames[j]);
                    automationAbstractTests.test();
                    automationAbstractTests.performValidations();
                    automationAbstractTests.publishResults(saveDataToDatabase);
                } catch (AutomationException e) {
                    throw e;
                } catch (Exception e) {
                    if (e.getCause() instanceof AutomationException) {
                        throw (AutomationException) e.getCause();
                    } else {
                        logger.debug("Exception Occured While testing sheet {} : {} ", excelSheetNames[j],
                                        ExceptionUtils.getStackTrace(e));
                    }
                }
            }
            String outputExcelFileName = generateOutputExcelFileName(excelFileNames[i]);
            automationExcelUtility.saveDataToExcel(outputExcelFileName, excelSheetNames);
            attachments.add(outputExcelFileName);
        }
        new AutomationDefaultTest().cleanup();
        if (sendMail) {
            automationEmailUtility.addAttachments(attachments);
            automationEmailUtility.setLastExecutedTestCount(lastExecutedTestCount);
            if (!saveDataToDatabase) {
                automationEmailUtility.sendMail();
            }
        }
        logger.traceExit();
    }

    private String generateOutputExcelFileName(String excelFileName) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss a"));
        int extensionIndex = excelFileName.indexOf(".") - 1;
        // Setting Excel Sheet Name
        return automationProperties.getProperty(AutomationConstants.FASTEST_OUTPUT_FOLDER_PATH) + "/"
                        + excelFileName.substring(0, extensionIndex + 1) + "_" + dateTime
                        + excelFileName.substring(extensionIndex + 1);
    }

}
