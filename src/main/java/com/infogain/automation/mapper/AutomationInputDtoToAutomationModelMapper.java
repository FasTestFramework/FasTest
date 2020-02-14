package com.infogain.automation.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.model.AutomationId;
import com.infogain.automation.model.AutomationOutputModel;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This class is for mapping data from Peripheral Input DTO to Database Model class
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
public class AutomationInputDtoToAutomationModelMapper {
    private static final Logger logger = LogManager.getLogger(AutomationInputDtoToAutomationModelMapper.class);

    /**
     * this method converts list of {@link AutomationInputDTO} to a new instance of {@link AutomationOutputModel}
     * 
     * @param automationInputDTOList list of {@link AutomationInputDTO}
     * @param outputExcelSheetName Output Excel Sheet Name
     * @param testSheetName Excel Sheet Name
     * @param inputExcelSheetName Input Excel Sheet Name
     * @return an instance of {@link AutomationOutputModel}
     */
    public static AutomationOutputModel convertAutomationDtoToAutomationOutputModel(
                    List<AutomationInputDTO> automationInputDTOList, String outputExcelSheetName, String testSheetName,
                    String inputExcelSheetName) {
        logger.traceEntry(
                        "convertAutomationDtoToAutomationOutputModel method of AutomationInputDtoToAutomationModelMapper class");
        int totalExecutedTestCases = automationInputDTOList.size();
        // Getting Local Date Time
        LocalDateTime executionDateTime = automationInputDTOList.get(0).getExecutionDateTime();
        // Getting Local Date
        LocalDate executionDate = executionDateTime.toLocalDate();
        // Getting total Pass Test Cases Count
        int totalPassTestCases =
                        (int) automationInputDTOList.stream()
                                        .filter(automationInputDTO -> automationInputDTO.getTestCaseResult()
                                                        .equalsIgnoreCase(AutomationConstants.TEST_CASE_RESULT_PASS))
                                        .count();
        logger.info("Total Pass Test Cases are {}", totalPassTestCases);
        int totalFailedTestCases = totalExecutedTestCases - totalPassTestCases;
        logger.info("Total Fail Test Cases are {}", totalFailedTestCases);

        String testedBy = System.getProperty("user.name").replaceAll("\\.", " ");
        return logger.traceExit(new AutomationOutputModel(outputExcelSheetName, testSheetName, totalExecutedTestCases,
                        totalPassTestCases, totalFailedTestCases, executionDate, testedBy,
                        new AutomationId(inputExcelSheetName, testSheetName, executionDateTime)));
    }
}
