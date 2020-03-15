package com.infogain.automation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.infogain.automation.constants.ValidationMethods;
import com.infogain.automation.dto.AutomationCustomValidationMethodsResponseDTO;
import com.infogain.automation.dto.AutomationKeyPathsDTO;
import com.infogain.automation.dto.AutomationKeyPathsResponseDTO;
import com.infogain.automation.dto.AutomationTestCustomValidationsDTO;
import com.infogain.automation.dto.AutomationTestCustomValidationsResponseDTO;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is a service class which handles custom validations related endpoints
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Mar 3, 2020
 */
@Service
public class AutomationCustomValidationService {

    public AutomationTestCustomValidationsResponseDTO testCustomValidations(
                    AutomationTestCustomValidationsDTO automationTestCustomValidationsDTO) {
        return new AutomationTestCustomValidationsResponseDTO(new Random().nextBoolean());
    }

    public AutomationKeyPathsResponseDTO getAllKeyPaths(AutomationKeyPathsDTO automationKeyPathsDTO) {
        List<String> allKeyPaths = new ArrayList<>();
        allKeyPaths.add("output");
        allKeyPaths.add("output.receiptElements");
        allKeyPaths.add("output.receiptElements[0]");
        allKeyPaths.add("output.receiptElements[0].image");
        allKeyPaths.add("output.receiptElements[x]");
        allKeyPaths.add("output.receiptElements[x].barcode");
        return new AutomationKeyPathsResponseDTO(allKeyPaths);
    }

    public AutomationCustomValidationMethodsResponseDTO getValidationsMethods() {
        return new AutomationCustomValidationMethodsResponseDTO(Arrays.stream(ValidationMethods.values())
                        .map(ValidationMethods::getMethod).collect(Collectors.toList()));
    }

    // @Autowired
    // public AutomationCustomValidationService(final AutomationProperties automationProperties,
    // final AutomationRandomUtility automationRandomUtility,
    // final AutomationJsonUtility automationJsonUtility) {
    // this.automationProperties = automationProperties;
    // this.automationRandomUtility = automationRandomUtility;
    // this.automationJsonUtility = automationJsonUtility;
    // }


}
