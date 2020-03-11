package com.infogain.automation.controller;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationGraphRequestDTO;
import com.infogain.automation.dto.AutomationSprintDTO;
import com.infogain.automation.dto.SendMailRequestDTO;
import com.infogain.automation.model.TestCaseDAOOutputModel;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.service.AutomationReportService;
import com.infogain.automation.service.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class contains all Rest Endpoints
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
@CrossOrigin(origins = "*")
@RestController
@Api(value="")
public class AutomationReportingRestController {

    private static final Logger logger = LogManager.getLogger(AutomationReportingRestController.class);

    private final AutomationProperties automationProperties;
    private final AutomationReportService automationReportService;
    private final MailService mailService;

    @Autowired
    public AutomationReportingRestController(final AutomationProperties automationProperties,
                    final AutomationReportService automationReportService, final MailService mailService) {
        this.automationReportService = automationReportService;
        this.mailService = mailService;
        this.automationProperties = automationProperties;
    }

    /**
     * This method gives the list of Distinct Excel File Names
     * 
     * @return List of Distinct Excel File names
     * @since Dec 12, 2019
     */
    @GetMapping(value = "/distinctExcelNames", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "/distinctExcelNames", notes = "This API is used to show distinct Excel name", response = String.class, responseContainer = "List", protocols = "http,https")
	@ApiResponses({ @ApiResponse(code = 200, message = "Distinct excel list displayed successfully"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<String>> distinctExcelNames() {
        logger.traceEntry("distinctExcelNames method of AutomationReportingRestController class");
        List<String> listexcel = automationReportService.distinctExcelName();
        return logger.traceExit(ResponseEntity.ok(listexcel));
    }

    /**
     * This method receives the testInputFiles name, start and end Date and gives total count of Pass Test Cases
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO} Request body to give testInputFiles name,
     *        start and end Date
     * @return list of total count of Pass Test Cases
     * @since Dec 12, 2019
     */
    @PostMapping(value = "/graphDataPass", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/graphDataPass",
    notes = "This API is used to count pass test case",
    response = TestCaseDAOOutputModel.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 200, message = "Passed test case successfully counted"),
    @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<TestCaseDAOOutputModel>> graphDataSuccess(
                    @Valid @RequestBody AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry("graphDataSuccess method of AutomationReportingRestController class");
        List<TestCaseDAOOutputModel> testcaseList =
                        automationReportService.getPassTestCaseList(automationGraphRequestDTO);
        return logger.traceExit(ResponseEntity.ok(testcaseList));
    }

    /**
     * This method receives the testInputFiles name, start and end Date and gives total count of Fail Test Cases
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO} Request body to give testInputFiles name,
     *        start and end Date
     * @return list of total count of Fail Test Cases
     * @since Dec 12, 2019
     */
    @PostMapping(value = "/graphDataFail", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/graphDataFail",
    notes = "This API is used to count fail test case",
    response = TestCaseDAOOutputModel.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 200, message = "Failed test case successfully counted"),
    @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<TestCaseDAOOutputModel>> getFailGraphData(
                    @Valid @RequestBody AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry("getFailGraphData method of AutomationReportingRestController class");

        List<TestCaseDAOOutputModel> testcaseList =
                        automationReportService.getFailTestCaseList(automationGraphRequestDTO);
        return logger.traceExit(ResponseEntity.ok(testcaseList));
    }

    /**
     * This method receives the testInputFiles name, start and end Date and gives total count of Test Cases
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO} Request body to give testInputFiles name,
     *        start and end Date
     * @return list of total count of Test Cases
     * @since Dec 12, 2019
     */
    @PostMapping(value = "/graphDataTotal", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/graphDataTotal",
    notes = "This API is used to count total no. test case",
    response = TestCaseDAOOutputModel.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 200, message = "Total test case successfully counted"),
    @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<TestCaseDAOOutputModel>> getTotalGraphData(
                    @Valid @RequestBody AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry("getTotalGraphData method of AutomationReportingRestController class");
        List<TestCaseDAOOutputModel> testcaseList =
                        automationReportService.getExecutedTestCaseList(automationGraphRequestDTO);
        return logger.traceExit(ResponseEntity.ok(testcaseList));
    }

    /**
     * This method receives the Excel File name, start and end Date and gives Last Executed Test Cases
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO} Request body to give testInputFiles name,
     *        start and end Date
     * @return list of Last Executed Test Cases
     * @since Dec 12, 2019
     */
    @PostMapping(value = "/graphDataExecutedCount", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/graphDataExecutedCount",
    notes = "This API is used to count total last executed test case",
    response = TestCaseDAOOutputModel.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 200, message = "Total last executed test case successfully counted"),
    @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<TestCaseDAOOutputModel>> graphDataExecutedCount(
                    @Valid @RequestBody AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry("graphDataExecutedCount method of AutomationReportingRestController class");
        List<TestCaseDAOOutputModel> testcaseList =
                        automationReportService.getExecutedLastTestCaseList(automationGraphRequestDTO);
        return logger.traceExit(ResponseEntity.ok(testcaseList));
    }

    /**
     * This method writes the Report data into a PDF
     * 
     * @param automationPdfDTO of {@link SendMailRequestDTO}
     * @return PDF
     * @since Dec 12, 2019
     */
    @PostMapping(value = "/sendmail", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/sendmail",
    notes = "This API is used to write report data into a PDF",
    response = String.class,
    protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 204, message = "Report written in a PDF successfully"),
    @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> sendMail(@RequestBody SendMailRequestDTO sendMailRequestDTO) {
        logger.traceEntry("sendMail method of AutomationReportingRestController class");
        String automationReportFileName =
                        automationProperties.getProperty(AutomationConstants.FASTEST_OUTPUT_FOLDER_PATH) + "/"
                                        + sendMailRequestDTO.getReportFileName();
        File file = new File(automationReportFileName);
        HttpStatus status;
        String responseBody = null;
        if (file.exists() && !file.isDirectory()) {
            mailService.attachReportAndProcessMail(automationReportFileName);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.BAD_REQUEST;
            responseBody = "File does not exist at path specified.";
        }
        return logger.traceExit(ResponseEntity.status(status).body(responseBody));
    }

    /**
     * This method gives the Sprint Information like:
     * <ul>
     * <li>PI</li>
     * <li>Sprint</li>
     * <li>Project Name</li>
     * <li>Start Date</li>
     * <li>End Date</li>
     * </ul>
     * 
     * @return List of Last Executed Test Cases
     * @since Dec 12, 2019
     */
	@GetMapping(value = "/allSprintInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "/allSprintInfo", notes = "This API is used to give sprint information", 
	response = AutomationSprintDTO.class, protocols = "http,https")
	@ApiResponses({ @ApiResponse(code = 202, message = "Sprint information displayed successfully"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseEntity<List<AutomationSprintDTO>> getSprintInfo() {
        logger.traceEntry("getSprintInfo method of AutomationReportingRestController class");
        List<AutomationSprintDTO> list = automationReportService.getAllSprintInfo();
        return logger.traceExit(ResponseEntity.status(HttpStatus.ACCEPTED).body(list));
    }

}
