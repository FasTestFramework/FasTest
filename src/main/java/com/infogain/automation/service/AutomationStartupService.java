package com.infogain.automation.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRunTestCasesDTO;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.tests.AutomationAbstractTests;
import com.infogain.automation.tests.AutomationDefaultTest;
import com.infogain.automation.utilities.AutomationClaimsUtility;
import com.infogain.automation.utilities.AutomationEmailUtility;
import com.infogain.automation.utilities.AutomationExcelUtility;

@Service
public class AutomationStartupService {

    private static final Logger logger = LogManager.getLogger(AutomationStartupService.class);

    private final AutomationProperties automationProperties;
    private final AutomationReportService automationReportService;
    private final AutomationExcelUtility automationExcelUtility;
    private final AutomationEmailUtility automationEmailUtility;
    private final AutomationClaimsUtility automationClaimsUtility;

    @Autowired
    public AutomationStartupService(final AutomationProperties automationProperties,
                    final AutomationReportService automationReportService,
                    final AutomationExcelUtility automationExcelUtility,
                    final AutomationEmailUtility automationEmailUtility,
                    final AutomationClaimsUtility automationClaimsUtility) {
        this.automationProperties = automationProperties;
        this.automationReportService = automationReportService;
        this.automationExcelUtility = automationExcelUtility;
        this.automationEmailUtility = automationEmailUtility;
        this.automationClaimsUtility = automationClaimsUtility;
    }

    public void runTestCases(AutomationRunTestCasesDTO automationRunTestCasesDTO) {
        logger.traceEntry("runTestCases method of AutomationStartupService class");
        String generateTokenAt = automationProperties.getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN_INSTANCE);

        Map<String, List<String>> testInputFiles = automationRunTestCasesDTO.getTestInputFiles();
        if (testInputFiles == null || testInputFiles.isEmpty()) {
            testInputFiles = new HashMap<>();
            String[] excelFileNames = automationProperties.getPropertyAsString(AutomationConstants.FASTEST_EXCEL_SHEET_NAME)
                            .trim().split("\\|");
            String[] allExcelSheetNames = automationProperties
                            .getPropertyAsString(AutomationConstants.APPLICATION_PROPERTIES_SHEET_NAME).trim().split("\\|");
            for (int i = 0; i < excelFileNames.length; i++) {
                testInputFiles.put(excelFileNames[i], Arrays.asList(allExcelSheetNames[i].split(",")));
            }
        }
        List<String> attachments = new ArrayList<>();
        int lastExecutedTestCount = 0;
        String generateToken = automationProperties.getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN);
        if (generateTokenAt.equalsIgnoreCase("beforeStartUp") && generateToken.equalsIgnoreCase("true")) {
            automationClaimsUtility.generateClaimId();
        }
        for (Map.Entry<String, List<String>> entry : testInputFiles.entrySet()) {
            if (generateTokenAt.equalsIgnoreCase("beforeFile") && generateToken.equalsIgnoreCase("true")) {
                automationClaimsUtility.releaseAutomationServer();
                automationClaimsUtility.generateClaimId();
            }
            String excelFileName = entry.getKey();
            String inputExcelFilePath =
                            automationProperties.getPropertyAsString(AutomationConstants.FASTEST_INPUT_EXCEL_FOLDER_PATH) + "/"
                                            + excelFileName;
            XSSFWorkbook workbook = automationExcelUtility.readExcelFile(inputExcelFilePath);
            List<String> excelSheetNames = entry.getValue();
            for (String excelSheetName : excelSheetNames) {
                lastExecutedTestCount = lastExecutedTestCount
                                + automationReportService.getLastTestCaseExecuted(excelFileName, excelSheetName);
                Class<?> classFound;
                try {
                    classFound = Class.forName(AutomationConstants.TEST_CLASS_PACKAGE_NAME + excelSheetName);
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    classFound = AutomationDefaultTest.class;
                }
                try {
                    AutomationAbstractTests automationAbstractTests =
                                    (AutomationAbstractTests) classFound.newInstance();
                    automationAbstractTests.init(workbook, excelFileName, excelSheetName);
                    automationAbstractTests.test();
                    automationAbstractTests.performValidations();
                    automationAbstractTests.publishResults(workbook, automationRunTestCasesDTO.isSaveToDatabase());
                } catch (AutomationException e) {
                    automationExcelUtility.closeExcel(workbook);
                    throw e;
                } catch (Exception e) {
                    logger.debug("Exception Occured While testing sheet {} : {} ", excelSheetName,
                                    ExceptionUtils.getStackTrace(e));
                }
            }
            String outputExcelFileName = generateOutputExcelFileName(excelFileName);
            automationExcelUtility.generateExcel(workbook, outputExcelFileName, excelSheetNames);
            attachments.add(outputExcelFileName);
        }
        if (generateTokenAt.equalsIgnoreCase("beforeStartUp")) {
            automationClaimsUtility.releaseAutomationServer();
        }
        if (automationRunTestCasesDTO.isSendMail()) {
            automationEmailUtility.addAttachments(attachments);
            automationEmailUtility.setLastExecutedTestCount(lastExecutedTestCount);
            if (!automationRunTestCasesDTO.isSaveToDatabase()) {
                automationEmailUtility.sendMail();
            }
        }
        logger.traceExit();
    }

    private String generateOutputExcelFileName(String excelFileName) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss_a"));
        int extensionIndex = excelFileName.indexOf(".") - 1;
        // Setting Excel Sheet Name
        return automationProperties.getPropertyAsString(AutomationConstants.FASTEST_OUTPUT_FOLDER_PATH) + "/"
                        + excelFileName.substring(0, extensionIndex + 1) + "_" + dateTime
                        + excelFileName.substring(extensionIndex + 1);
    }

}
