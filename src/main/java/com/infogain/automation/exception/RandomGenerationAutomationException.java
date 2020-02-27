package com.infogain.automation.exception;

import java.util.Collections;
import java.util.List;

import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is user defined Exception class
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
public class RandomGenerationAutomationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final List<ErrorCodesDTO> errorCodesList;

    public RandomGenerationAutomationException(String message) {
        super(message);
        this.errorCodesList = null;
    }

    public RandomGenerationAutomationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCodesList = null;
    }

    public RandomGenerationAutomationException(List<ErrorCodesDTO> errorCodesList) {
        super(errorCodesList.toString());
        this.errorCodesList = errorCodesList;
    }

    public RandomGenerationAutomationException(ErrorCodesDTO errorCodesList) {
        super(errorCodesList.toString());
        this.errorCodesList = Collections.singletonList(errorCodesList);
    }

    public RandomGenerationAutomationException(AutomationErrorCodes automationErrorCodes) {
        super(automationErrorCodes.getCode() + automationErrorCodes.getMessage());
        this.errorCodesList = Collections.singletonList(new ErrorCodesDTO(automationErrorCodes));
    }

    public List<ErrorCodesDTO> getErrorCodes() {
        return errorCodesList;
    }
}
