package com.infogain.automation.controller;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.infogain.automation.dto.AutomationCustomValidationMethodsResponseDTO;
import com.infogain.automation.dto.AutomationKeyPathsDTO;
import com.infogain.automation.dto.AutomationKeyPathsResponseDTO;
import com.infogain.automation.dto.AutomationTestCustomValidationsDTO;
import com.infogain.automation.dto.AutomationTestCustomValidationsResponseDTO;
import com.infogain.automation.service.AutomationCustomValidationService;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class contains all Rest Endpoints
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Mar 03, 2020
 */
@CrossOrigin(origins = "*")
@RestController
public class AutomationCustomValidationController {

    private static final Logger logger = LogManager.getLogger(AutomationCustomValidationController.class);

    private final AutomationCustomValidationService automationCustomValidationService;

    @Autowired
    public AutomationCustomValidationController(
                    final AutomationCustomValidationService automationCustomValidationService) {
        this.automationCustomValidationService = automationCustomValidationService;
    }

    /**
     * This method creates json of boundary value analysis
     * 
     * @param Json in the form of {@link String}
     * @return String message containing no. of json created and file path
     * @since Mar 03, 2020
     */
    @PostMapping(value = "/testCustomValidations", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/testCustomValidations",
                    notes = "This API is used to test custom validations against json data provided",
                    protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 200, message = "custom validations are tested successfully",
                    response = AutomationTestCustomValidationsDTO.class, responseContainer = "",
                    reference = "AutomationTestCustomValidationsDTO"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationTestCustomValidationsResponseDTO> testCustomValidations(
                    @RequestBody @Valid AutomationTestCustomValidationsDTO automationTestCustomValidationsDTO) {
        return logger.traceExit(ResponseEntity.status(HttpStatus.OK).body(
                        automationCustomValidationService.testCustomValidations(automationTestCustomValidationsDTO)));
    }

    @PostMapping(value = "/getKeyPaths", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/getKeyPaths",
                    notes = "This API is used to get all key paths possible for provided json data",
                    protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 200, message = "All key paths possible for provided json data",
                    response = AutomationKeyPathsResponseDTO.class, responseContainer = "",
                    reference = "AutomationKeyPathsResponseDTO"), @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AutomationKeyPathsResponseDTO> getAllKeyPaths(
                    @RequestBody @Valid AutomationKeyPathsDTO automationKeyPathsDTO) {
        return logger.traceExit(ResponseEntity.status(HttpStatus.OK)
                        .body(automationCustomValidationService.getAllKeyPaths(automationKeyPathsDTO)));
    }

    @GetMapping(value = "/getValidationsMethods", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/getValidationsMethods",
                    notes = "This API is used to get List of valid Custom Validation Methods", protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 200, message = "List of valid Custom Validation Methods",
                    response = AutomationCustomValidationMethodsResponseDTO.class, responseContainer = "",
                    reference = "AutomationCustomValidationMethodsResponseDTO"),
                    @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AutomationCustomValidationMethodsResponseDTO> getValidationsMethods() {
        return logger.traceExit(ResponseEntity.status(HttpStatus.OK)
                        .body(automationCustomValidationService.getValidationsMethods()));
    }

}
