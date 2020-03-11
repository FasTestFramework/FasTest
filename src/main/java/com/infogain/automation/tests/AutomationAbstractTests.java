package com.infogain.automation.tests;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpMethod;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.dto.Pair;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.mapper.AutomationInputDtoToAutomationModelMapper;
import com.infogain.automation.model.AutomationOutputModel;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.service.AutomationReportService;
import com.infogain.automation.utilities.AutomationClaimsUtility;
import com.infogain.automation.utilities.AutomationEmailUtility;
import com.infogain.automation.utilities.AutomationEndpointHitUtility;
import com.infogain.automation.utilities.AutomationExcelUtility;
import com.infogain.automation.utilities.AutomationHeadersUtility;
import com.infogain.automation.utilities.AutomationJsonUtility;
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
    private final AutomationHeadersUtility automationHeadersUtility;
    private final AutomationJsonUtility automationJsonUtility;
    private final AutomationValidationUtility automationValidationUtility;

    public AutomationAbstractTests() {
        automationProperties = BeanUtil.getBean(AutomationProperties.class);
        automationEmailUtility = BeanUtil.getBean(AutomationEmailUtility.class);
        automationClaimsUtility = BeanUtil.getBean(AutomationClaimsUtility.class);
        automationReportService = BeanUtil.getBean(AutomationReportService.class);
        automationExcelUtility = BeanUtil.getBean(AutomationExcelUtility.class);
        baseClaimUrl = automationProperties.getProperty(AutomationConstants.FASTEST_HOST_NAME) + ":"
                        + automationProperties.getProperty(AutomationConstants.FASTEST_PORT);
        automationEndpointHitUtility = BeanUtil.getBean(AutomationEndpointHitUtility.class);
        automationHeadersUtility = BeanUtil.getBean(AutomationHeadersUtility.class);
        automationJsonUtility = BeanUtil.getBean(AutomationJsonUtility.class);
        automationValidationUtility = BeanUtil.getBean(AutomationValidationUtility.class);
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
        String generateToken = automationProperties.getProperty(AutomationConstants.FASTEST_GENERATE_TOKEN);

        String generateTokenAt = automationProperties.getProperty(AutomationConstants.FASTEST_GENERATE_TOKEN_INSTANCE);
        if (generateTokenAt.equalsIgnoreCase("beforeSheet") && generateToken.equalsIgnoreCase("true")) {
            automationClaimsUtility.releaseAutomationServer();
            automationInputDTOList
                            .forEach(automationInputDTO -> automationClaimsUtility.updateToken(automationInputDTO));
        }
        automationInputDTOList.forEach(automationInputDTO -> {
            try {
                logger.info("Testing {} :", this.getClass().getSimpleName());
                if (generateTokenAt.equalsIgnoreCase("beforeTest") && generateToken.equalsIgnoreCase("true")) {
                    automationClaimsUtility.releaseAutomationServer();
                }
                if (!generateTokenAt.equalsIgnoreCase("beforeSheet") && generateToken.equalsIgnoreCase("true")) {
                    automationClaimsUtility.updateToken(automationInputDTO);
                }
                getActualResponse(baseClaimUrl, automationInputDTO);
            } catch (AutomationException e) {
                throw e;
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
        automationInputDTOList.forEach(
                        automationInputDTO -> automationValidationUtility.performValidations(automationInputDTO));
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

    /**
     * This method takes Endpoint URL and its Type and return response after
     * 
     * @param automationInputDTO of {@link AutomationInputDTO}
     * @return response
     * @since Dec 11, 2019
     */
    private void getActualResponse(String baseUrl, AutomationInputDTO automationInputDTO) {
        logger.traceEntry("hitEndpoint method of AutomationEndpointHitUtility class");
        logger.info("Expected Response Status code - {}, response body : {}",
                        automationInputDTO.getExpectedHttpStatus(), automationInputDTO.getExpectedOutput());
        String requestURL = automationInputDTO.getRequestURL();
        HttpMethod requestType = automationInputDTO.getRequestType();
        Pair<Response, Double> responseAndRuntime = automationEndpointHitUtility.hitEndpoint(baseUrl,
                        requestURL + automationInputDTO.getInputParam(), automationInputDTO.getHeaders(), requestType,
                        automationInputDTO.getTestCaseInputJson());
        Response resp = responseAndRuntime.getFirst();
        int actualStatusCode = resp.statusCode();
        //update token response if endpoint was of token
        automationClaimsUtility.checkIfTokenResponse(requestURL, requestType.name(), resp);
        String actualOutput = automationJsonUtility.beautifyJson(resp.asString());
        logger.info("Actual Response Status code - {}, response body : {}", actualStatusCode, actualOutput);
        Double testCaseRuntime = responseAndRuntime.getSecond();
        automationInputDTO.setActualHttpStatus(actualStatusCode);
        automationInputDTO.setActualOutput(actualOutput);
        automationInputDTO.setExecutionDateTime(LocalDateTime.now());
        automationInputDTO.setRuntime(testCaseRuntime);
        logger.traceExit();
    }

    private void setRequestBodyAndHeaders(String inputjsonFolderPath) {
        automationInputDTOList.forEach(automationInputDTO -> {
            String inputJson = automationInputDTO.getInputJson();
            String headerJson = automationInputDTO.getHeaderJson();
            if (StringUtils.isNotBlank(inputJson)) {
                inputJson = inputJson.toLowerCase().endsWith(".json") ? inputjsonFolderPath + "/" + inputJson
                                : inputJson;
                automationInputDTO.setTestCaseInputJson(automationJsonUtility.fetchJSONObject(inputJson));
            }
            // sets Header Object in AutomationInputDTO
            if (StringUtils.isNotBlank(headerJson)) {
                headerJson = headerJson.toLowerCase().endsWith(".json") ? inputjsonFolderPath + "/" + headerJson
                                : headerJson;
                Headers fetchHeaders = automationHeadersUtility.fetchHeaders(headerJson);
                automationInputDTO.setHeaders(fetchHeaders);
            }
        });
    }
}
