package com.infogain.automation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infogain.automation.dto.AutomationRequestDTO;
import com.infogain.automation.dto.AutomationUiRequestDTO;
import com.infogain.automation.service.AutomationInputExcelService;
import com.infogain.automation.service.AutomationStartupService;
import com.infogain.automation.service.AutomationUiRequestService;

@RestController
public class AutomationUIController {
    private static final Logger logger = LogManager.getLogger(AutomationUIController.class);

    private final AutomationStartupService automationStartupService;
    private final AutomationInputExcelService automationInputExcelService;
    private final AutomationUiRequestService automationUiRequestService;

    @Autowired
    public AutomationUIController(final AutomationStartupService automationStartupService,
                    final AutomationInputExcelService automationInputExcelService,
                    final AutomationUiRequestService automationUiRequestService) {
        this.automationStartupService = automationStartupService;
        this.automationInputExcelService = automationInputExcelService;
        this.automationUiRequestService = automationUiRequestService;
    }

    @GetMapping(value = "/runtest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void dostartuptasks(@RequestParam(defaultValue = "true") boolean sendMail,
                    @RequestParam(defaultValue = "true") boolean saveToDatabase) {
        automationStartupService.runTestCases(sendMail, saveToDatabase);
    }

    @PostMapping(value = "/createExcel", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void generateExcel(@RequestBody AutomationRequestDTO automationRequestDTO) {
        automationInputExcelService.insertDataToExcel(automationRequestDTO);
    }

    @PostMapping(value = "/getResponse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uiRequest(@RequestBody AutomationUiRequestDTO automationUiRequestDTO) {
        return logger.traceExit(
                        ResponseEntity.ok(automationUiRequestService.hitEndpointFromUI(automationUiRequestDTO)));
    }

}

