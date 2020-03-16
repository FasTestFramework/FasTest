package com.infogain.automation.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.ValidationMethods;
import com.infogain.automation.dto.AutomationCustomValidationMethodsResponseDTO;
import com.infogain.automation.dto.AutomationKeyPathsDTO;
import com.infogain.automation.dto.AutomationKeyPathsResponseDTO;
import com.infogain.automation.dto.AutomationTestCustomValidationsDTO;
import com.infogain.automation.dto.AutomationTestCustomValidationsResponseDTO;
import com.infogain.automation.exception.CustomValidationFailure;
import com.infogain.automation.mapper.AutomationKeyPathsDTOtoModel;
import com.infogain.automation.mapper.AutomationTestCustomValidationsDTOtoModel;
import com.infogain.automation.model.AutomationTestCustomValidationsModel;
import com.infogain.automation.utilities.AutomationCustomValidationUtility;
import com.infogain.automation.utilities.AutomationJsonUtility;
import com.infogain.automation.utilities.AutomationValidationUtility;
import com.infogain.automation.utilities.BeanUtil;

import static com.infogain.automation.utilities.AutomationValidationUtility.FAILURE_COMMENTS_SEPERATOR;

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

    private final AutomationJsonUtility automationJsonUtility;

    @Autowired
    public AutomationCustomValidationService(final AutomationJsonUtility automationJsonUtility) {
        this.automationJsonUtility = automationJsonUtility;
    }

    public AutomationTestCustomValidationsResponseDTO testCustomValidations(
                    AutomationTestCustomValidationsDTO automationTestCustomValidationsDTO) {
        AutomationTestCustomValidationsModel automationTestCustomValidationsModel =
                        AutomationTestCustomValidationsDTOtoModel.convert(automationTestCustomValidationsDTO);
        StringBuilder comments = new StringBuilder();
        boolean isSuccess = false;
        JSONAware jsonData = automationTestCustomValidationsModel.getData();
        Map<String, List<String>> customValidations = automationTestCustomValidationsModel.getCustomValidations();
        Set<String> allKeyPaths = automationJsonUtility.fetchAllKeyPaths(jsonData, true);
        customValidations.keySet().removeIf(keyPath -> {
            boolean keyPathNotExist = !allKeyPaths.contains(keyPath);
            if (keyPathNotExist) {
                addComment(keyPath, "Invalid Key Path.", comments);
            }
            return keyPathNotExist;
        });
        try {
            new AutomationValidationUtility(BeanUtil.getBean(AutomationJsonUtility.class)).performValidations(jsonData,
                            customValidations);
        } catch (CustomValidationFailure e) {
            if (comments.length() != 0) {
                comments.append(FAILURE_COMMENTS_SEPERATOR);
            }
            comments.append(e.getMessage());
        }
        if (comments.length() == 0) {
            comments.append("Validations Pass!");
            isSuccess = true;
        }
        return new AutomationTestCustomValidationsResponseDTO(isSuccess, comments.toString().replace("\n", "<br>"));
    }

    private void addComment(String keyPath, String message, StringBuilder comments) {
        if (comments.length() != 0) {
            comments.append(FAILURE_COMMENTS_SEPERATOR);
        }
        comments.append(keyPath).append(":\n").append(message).append("\n");
    }

    public AutomationKeyPathsResponseDTO getAllKeyPaths(AutomationKeyPathsDTO automationKeyPathsDTO) {
        JSONAware jsonData = AutomationKeyPathsDTOtoModel.convert(automationKeyPathsDTO).getData();
        return new AutomationKeyPathsResponseDTO(automationJsonUtility.fetchAllKeyPaths(jsonData, true));
    }


    public AutomationCustomValidationMethodsResponseDTO getValidationsMethods() {
        return new AutomationCustomValidationMethodsResponseDTO(Arrays.stream(ValidationMethods.values())
                        .map(ValidationMethods::getMethod).collect(Collectors.toList()));
    }

}
