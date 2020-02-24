package com.infogain.automation.tests;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.mapper.AutomationInputDtoToAutomationModelMapper;
import com.infogain.automation.model.AutomationOutputModel;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.service.AutomationReportService;
import com.infogain.automation.utilities.AutomationClaimsUtility;
import com.infogain.automation.utilities.AutomationEmailUtility;
import com.infogain.automation.utilities.AutomationEndpointHitUtility;
import com.infogain.automation.utilities.AutomationExcelUtility;
import com.infogain.automation.utilities.AutomationRequestBodyAndHeadersUtility;
import com.infogain.automation.utilities.AutomationValidationUtility;
import com.infogain.automation.utilities.BeanUtil;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is the parent class of all test classes which does the following tasks :
 * <ul>
 * <li>Generate response for every Request Type</li>
 * <li>sets the Total Pass , failed and Executed Test cases to Model class</li>
 * <li>clean up tasks which writes the data to output Excel File and release the ClaimID</li>
 * </ul>
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
public abstract class AutomationAbstractTests {

    private static final Logger logger = LogManager.getLogger(AutomationAbstractTests.class);
    protected List<AutomationInputDTO> automationInputDTOList;
    protected String testSheetName;
    protected String baseClaimUrl;

    private AutomationProperties automationProperties;
    private String inputExcelFileName;
    private String outputExcelSheet;

    private final AutomationEmailUtility automationEmailUtility;
    private final AutomationClaimsUtility automationClaimsUtility;
    private final AutomationReportService automationReportService;
    private final AutomationExcelUtility automationExcelUtility;
    private final AutomationEndpointHitUtility automationEndpointHitUtility;
    private final AutomationRequestBodyAndHeadersUtility automationRequestBodyAndHeadersUtility;

    public AutomationAbstractTests() {
        automationProperties = BeanUtil.getBean(AutomationProperties.class);
        automationEmailUtility = BeanUtil.getBean(AutomationEmailUtility.class);
        automationClaimsUtility = BeanUtil.getBean(AutomationClaimsUtility.class);
        automationReportService = BeanUtil.getBean(AutomationReportService.class);
        automationExcelUtility = BeanUtil.getBean(AutomationExcelUtility.class);
        baseClaimUrl = automationProperties.getProperty(AutomationConstants.FASTEST_HOST_NAME) + ":"
                        + automationProperties.getProperty(AutomationConstants.FASTEST_PORT);
        automationEndpointHitUtility = BeanUtil.getBean(AutomationEndpointHitUtility.class);
        automationRequestBodyAndHeadersUtility = BeanUtil.getBean(AutomationRequestBodyAndHeadersUtility.class);
    }

    public void init(XSSFWorkbook workbook, String inputExcelFileName, String testSheetName) {
        this.testSheetName = testSheetName;
        this.inputExcelFileName = inputExcelFileName;
        // Reading Data from Excel Utility Method and setting it to Automation Input DTO
        automationInputDTOList = automationExcelUtility.readInputExcelFile(workbook, inputExcelFileName, testSheetName);
        String inputjsonFolderPath =
                        automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_JSON_FOLDER_PATH);
        setRequestBodyAndHeaders(inputjsonFolderPath);
    }

    /**
     * This method is to release claimID and put values from response to {@link AutomationInputDTO}
     * 
     * @since Dec 11, 2019
     */
    public void test() {
        logger.traceEntry("test method of {} class", testSheetName);
        if (testSheetName.equals(AutomationConstants.CLAIM_TEST)) {
            automationClaimsUtility.releaseAutomationServer();
            logger.info("generated claim ID released");
        } else {
            // Updating ClaimId by new Generated Claim ID in JSON File
            automationInputDTOList
                            .forEach(automationInputDTO -> automationClaimsUtility.updateToken(automationInputDTO));
        }
        automationInputDTOList.forEach(automationInputDTO -> {
            try {
                logger.info("Testing {} :", this.getClass().getSimpleName());
                Response resp = getActualResponse(baseClaimUrl, automationInputDTO);
                if (testSheetName.equals(AutomationConstants.CLAIM_TEST)) {
                    automationClaimsUtility.releaseAutomationServer(resp);
                }
            } catch (Exception e) {
                logger.debug("Exception Occured While hitting endpoint {} of type {} : {}",
                                automationInputDTO.getRequestURL(), automationInputDTO.getRequestType(),
                                ExceptionUtils.getStackTrace(e));
                throw new AutomationException(
                                "Exception Occured While hitting endpoint" + automationInputDTO.getRequestURL()
                                                + " of type " + automationInputDTO.getRequestType(),
                                e);
            }
        });
        logger.traceExit();
    }

    /**
     * This method validates equality of
     * <ul>
     * <li>Expected Output and Actual Output (JSON Compare)</li>
     * <li>Expected HTTP status Code and Actual HTTP status Code</li>
     * </ul>
     * and set test case result to the list of {@link AutomationInputDTO}
     * 
     * @since Nov 27, 2019
     */
    public void performValidations() {
        logger.traceEntry("performValidations method of {} class", testSheetName);
        automationInputDTOList.forEach(AutomationValidationUtility::performValidations);
        logger.traceExit();
    }

    /**
     * This method writes data to Excel Output File and saves the data to Database
     * 
     * @since Nov 27, 2019
     */
    public void publishResults(XSSFWorkbook workbook, boolean saveToDatabase) {
        logger.traceEntry("publishResults method of {} class", testSheetName);

        automationExcelUtility.writeOutputExcelFile(workbook, testSheetName, automationInputDTOList);
        AutomationOutputModel automationOutputModel =
                        AutomationInputDtoToAutomationModelMapper.convertAutomationDtoToAutomationOutputModel(
                                        automationInputDTOList, outputExcelSheet, testSheetName, inputExcelFileName);
        logger.debug("Test Data created to be saved in DB - {} ", automationOutputModel);
        automationEmailUtility.setTotalExecutedTestCases(automationEmailUtility.getTotalExecutedTestCases()
                        + automationOutputModel.getTotalExecutedTestCases());
        automationEmailUtility.setTotalPassTestCases(
                        automationEmailUtility.getTotalPassTestCases() + automationOutputModel.getTotalPassTestCases());
        automationEmailUtility.setTotalFailedTestCases(automationEmailUtility.getTotalFailedTestCases()
                        + automationOutputModel.getTotalFailedTestCases());

        if (saveToDatabase) {
            // save the Model object
            automationOutputModel = automationReportService.insertData(automationOutputModel);
            logger.info("Saved test Data - {} ", automationOutputModel);
        }
        logger.traceExit();
    }

    public void cleanup() {
        automationClaimsUtility.releaseAutomationServer();
    }

    /**
     * This method takes Endpoint URL and its Type and return response after
     * 
     * @param automationInputDTO of {@link AutomationInputDTO}
     * @return response
     * @since Dec 11, 2019
     */
    private Response getActualResponse(String baseUrl, AutomationInputDTO automationInputDTO) {
        logger.traceEntry("hitEndpoint method of AutomationEndpointHitUtility class");
        logger.info("Expected Response Status code - {}, response body : {}",
                        automationInputDTO.getExpectedHttpStatus(), automationInputDTO.getExpectedOutput());
        Response resp = automationEndpointHitUtility.hitEndpoint(baseUrl,
                        automationInputDTO.getRequestURL() + automationInputDTO.getInputParam(),
                        automationInputDTO.getHeaders(), automationInputDTO.getRequestType(),
                        automationInputDTO.getTestCaseInputJson());
        automationInputDTO.setActualHttpStatus(resp.statusCode());
        automationInputDTO.setActualOutput(resp.asString());
        automationInputDTO.setExecutionDateTime(LocalDateTime.now());
        return logger.traceExit(resp);
    }

    private void setRequestBodyAndHeaders(String inputjsonFolderPath) {
        automationInputDTOList.forEach(automationInputDTO -> {
            String inputJson = automationInputDTO.getInputJson();
            String headerJson = automationInputDTO.getHeaderJson();
            if (StringUtils.isNotBlank(inputJson)) {
                automationInputDTO.setTestCaseInputJson(automationRequestBodyAndHeadersUtility
                                .fetchJSONObject(inputjsonFolderPath + "/" + inputJson));
            }
            // sets Header Object in AutomationInputDTO
            if (StringUtils.isNotBlank(headerJson)) {
                Headers fetchHeaders = automationRequestBodyAndHeadersUtility
                                .fetchHeaders(inputjsonFolderPath + "/" + headerJson);
                automationInputDTO.setHeaders(fetchHeaders);
            }
        });
    }
}
