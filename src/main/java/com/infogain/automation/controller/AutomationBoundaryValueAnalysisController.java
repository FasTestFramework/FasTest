package com.infogain.automation.controller;

import java.util.Map;

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

import com.infogain.automation.dto.AutomationBoundaryValueAnalysisDTO;
import com.infogain.automation.dto.AutomationBoundaryValueAnalysisResponseDTO;
import com.infogain.automation.dto.AutomationRandomGenerationMetadataDTO;
import com.infogain.automation.service.AutomationBoundaryValueAnalysisService;

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
public class AutomationBoundaryValueAnalysisController {

    private static final Logger logger = LogManager.getLogger(AutomationBoundaryValueAnalysisController.class);

    private final AutomationBoundaryValueAnalysisService automationBoundaryValueAnalysisService;

    @Autowired
    public AutomationBoundaryValueAnalysisController(
                    final AutomationBoundaryValueAnalysisService automationBoundaryValueAnalysisService) {
        this.automationBoundaryValueAnalysisService = automationBoundaryValueAnalysisService;
    }

    /**
     * This method creates json of boundary value analysis
     * 
     * @param Json in the form of {@link String}
     * @return String message containing no. of json created and file path
     * @since Mar 03, 2020
     */
    @PostMapping(value = "/boundaryValueAnalysis", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/boundaryValueAnalysis", notes = "This API is used to genereate boundary value jsons",
                    protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "Boundary value JSONs are created successfully.",
                    response = AutomationBoundaryValueAnalysisResponseDTO.class, responseContainer = "",
                    reference = "AutomationBoundaryValueAnalysisResponseDTO"),
                    @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationBoundaryValueAnalysisResponseDTO> generateBoundaryValueAnalysis(
                    @RequestBody @Valid AutomationBoundaryValueAnalysisDTO automationBoundaryValueAnalysisDTO) {
        return logger.traceExit(ResponseEntity.status(HttpStatus.CREATED).body(automationBoundaryValueAnalysisService
                        .getBoundaryValueAnalysis(automationBoundaryValueAnalysisDTO)));
    }

    /**
     * This method is used to get metadata for random data generation
     * 
     * @since 09-Mar-2020
     */
    @GetMapping(value = "/getMetadata", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/getMetadata", notes = "This API is used to get metadata for random data generation",
                    protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "Boundary value JSONs are created successfully.",
                    response = Map.class, responseContainer = "",
                    reference = "Map<String, AutomationRandomGenerationMetadataDTO>"),
                    @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, AutomationRandomGenerationMetadataDTO>> generateBoundaryValueAnalysis() {
        return logger.traceExit(ResponseEntity.status(HttpStatus.OK)
                        .body(automationBoundaryValueAnalysisService.getBoundaryValueAnalysis()));
    }

}
