package com.infogain.automation.controller.advice;

import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.infogain.automation.dto.AutomationError;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * <br>
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Design and Architecture<br>
 * Description - This class is responsible for building the error response.
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since 09-Jul-2019
 */
@Component
public class AutomationErrorResponseBuilder {

    public AutomationError buildInvalidInputExceptionErrorResponse(InvalidFormatException argInvalidInputException) {
        return buildInvalidValueErrorResponse(argInvalidInputException.getPath());
    }

    /**
     * This method sets the appropriate errorCode into {@link AutomationError} along with the message when
     * UnrecognizedException occurs
     * 
     * @param argUnrecognizedPropertyException {@link UnrecognizedPropertyException} object
     * @return {@link AutomationError} CXS Envelope error message
     * @since Sep 23, 2019
     */
    public AutomationError buildUnrecognizedExceptionErrorResponse(
                    UnrecognizedPropertyException argUnrecognizedPropertyException) {
        String unrecognizedPropertyName = argUnrecognizedPropertyException.getPropertyName();
        if (unrecognizedPropertyName != null && !StringUtils.isWhitespace(unrecognizedPropertyName)) {
            return new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_UNRECOGNIZED_PROPERTY_EXCEPTION,
                            unrecognizedPropertyName).convertToAutomationError();
        } else {
            return new AutomationError(AutomationErrorCodes.AUTOMATION_BLANK_PROPERTY_EXCEPTION.getCode(),
                            AutomationErrorCodes.AUTOMATION_BLANK_PROPERTY_EXCEPTION.getMessage());
        }
    }

    /**
     * This method sets the appropriate errorCode into {@link AutomationError} along with the message when
     * MisMatchInputException occurs
     * 
     * @param argMismatchedInputException {@link MismatchedInputException} object
     * @return {@link AutomationError} CXS Envelope error message
     * @since Sep 23, 2019
     */
    public AutomationError buildMisMatchInputExceptionErrorResponse(
                    MismatchedInputException argMismatchedInputException) {
        return buildInvalidValueErrorResponse(argMismatchedInputException.getPath());
    }

    /**
     * This method sets the appropriate errorCode into {@link AutomationError} along with the message when there is some
     * invalid value in payload. It finds key name from the list provided to it.
     * 
     * @param list the list of {@link Reference} which contains key name which has invalid value
     * @return {@link AutomationError} CXS Envelope error message
     * @since 20-Dec-2019
     */
    private AutomationError buildInvalidValueErrorResponse(List<Reference> list) {
        ListIterator<Reference> itr = list.listIterator(list.size());
        StringBuilder errorMessageBuilder = new StringBuilder();
        String fieldName = null;
        while (itr.hasPrevious()) {
            fieldName = itr.previous().getFieldName();
            if (fieldName != null) {
                break;
            }
        }
        if (fieldName != null) {
            errorMessageBuilder.append("'");
            errorMessageBuilder.append(fieldName);
            errorMessageBuilder.append("'");
        } else {
            errorMessageBuilder.append("Some");
        }
        return new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_INVALID_INPUT_EXCEPTION,
                        errorMessageBuilder.toString()).convertToAutomationError();
    }

}
