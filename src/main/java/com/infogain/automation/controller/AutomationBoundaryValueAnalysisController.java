package com.infogain.automation.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, String>> generateBoundaryValueAnalysis(@RequestBody String jsonString) {
        Map<String, String> responseMap = new HashMap<String, String>();
        responseMap.put("Result", automationBoundaryValueAnalysisService.getBoundaryValueAnalysis(jsonString));
        return logger.traceExit(ResponseEntity.status(HttpStatus.CREATED).body(responseMap));
    }

}
