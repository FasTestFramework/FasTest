package com.infogain.automation.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infogain.automation.dto.AutomationExcelRequestDTO;
import com.infogain.automation.dto.AutomationFilePathAndNameDTO;
import com.infogain.automation.dto.AutomationRunTestCasesDTO;
import com.infogain.automation.dto.AutomationUiRequestDTO;
import com.infogain.automation.dto.AutomationUiResponseDTO;
import com.infogain.automation.service.AutomationFileDetailsService;
import com.infogain.automation.service.AutomationInputExcelService;
import com.infogain.automation.service.AutomationStartupService;
import com.infogain.automation.service.AutomationUiRequestService;

@RestController
public class AutomationUIController {
    private static final Logger logger = LogManager.getLogger(AutomationUIController.class);

    private final AutomationStartupService automationStartupService;
    private final AutomationInputExcelService automationInputExcelService;
    private final AutomationUiRequestService automationUiRequestService;
    private final AutomationFileDetailsService automationFileDetailsService;

    @Autowired
    public AutomationUIController(final AutomationStartupService automationStartupService,
                    final AutomationInputExcelService automationInputExcelService,
                    final AutomationUiRequestService automationUiRequestService,
                    final AutomationFileDetailsService automationFileDetailsService) {
        this.automationStartupService = automationStartupService;
        this.automationInputExcelService = automationInputExcelService;
        this.automationUiRequestService = automationUiRequestService;
        this.automationFileDetailsService = automationFileDetailsService;
    }

    @PostMapping(value = "/runtest", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void dostartuptasks(@RequestBody AutomationRunTestCasesDTO automationRunTestCasesDTO) {
        automationStartupService.runTestCases(automationRunTestCasesDTO);
    }

    @PostMapping(value = "/createExcel", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void generateExcel(@Valid  @RequestBody AutomationExcelRequestDTO automationExcelRequestDTO) {
        automationInputExcelService.insertDataToExcel(automationExcelRequestDTO);
    }

    @PostMapping(value = "/getResponse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationUiResponseDTO> uiRequest(
                    @RequestBody AutomationUiRequestDTO automationUiRequestDTO) {
        return logger.traceExit(
                        ResponseEntity.ok(automationUiRequestService.hitEndpointFromUI(automationUiRequestDTO)));
    }

    @GetMapping(value = "/fetchFilePathAndNames", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationFilePathAndNameDTO> getFilePathAndNames() {
        return logger.traceExit(ResponseEntity.ok(automationFileDetailsService.dataname()));
    }



}

