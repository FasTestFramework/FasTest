package com.infogain.automation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infogain.automation.dto.AutomationPerformanceTestingResultsTestResultsDto;
import com.infogain.automation.dto.AutomationPerformanceTestingTest;
import com.infogain.automation.dto.AutomationResponse;
import com.infogain.automation.service.AutomationPerformanceTestingService;

import io.swagger.annotations.ApiOperation;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class contains Rest Endpoints for Performance Testing
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Mar 03, 2020
 */
@CrossOrigin(origins = "*")
@RestController
public class AutomationPerformanceTestingController {

	private static final Logger logger = LogManager.getLogger(AutomationPerformanceTestingController.class);

	private final AutomationPerformanceTestingService automationPerformanceTestingService;

	@Autowired
	public AutomationPerformanceTestingController(
			final AutomationPerformanceTestingService automationPerformanceTestingService) {
		this.automationPerformanceTestingService = automationPerformanceTestingService;
	}

	/**
	 * This method creates saves performance test results to db
	 * 
	 * @param jsonString
	 */
	@PostMapping(value = "/performancetest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "/performancetest", notes = "This API is used to create and run a performance test", responseReference = "AutomationResponse<AutomationPerformanceTestingResultsTestResultsDto>", protocols = "http,https")
	public AutomationResponse<AutomationPerformanceTestingResultsTestResultsDto> createPerformanceTest(
			@RequestBody AutomationPerformanceTestingTest automationPerformanceTestingTest) {
		return logger.traceExit(
				automationPerformanceTestingService.createAndRunPerformanceTest(automationPerformanceTestingTest));
	}

}
