package com.infogain.automation.controller.advice;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.infogain.automation.dto.AutomationError;
import com.infogain.automation.dto.AutomationResponse;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.exception.RandomGenerationAutomationException;
import com.infogain.automation.exception.StringToJsonParseException;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * <br>
 * Theme - Automation<br>
 * Feature - Peripheral Services - Design and Architecture<br>
 * Description - This class handles the exceptions for Automation Framework Controllers
 * 
 * @author Puneet Gupta [3696361]
 * @version 1.0.0
 * @since 10-Jul-2019
 */
@ControllerAdvice
@SuppressWarnings("rawtypes")
public class AutomationControllerAdvice implements AutomationControllerAdviceType {

    private static Logger logger = LogManager.getLogger(AutomationControllerAdvice.class);
    private final AutomationErrorResponseBuilder automationErrorResponseBuilder;

    @Autowired
    public AutomationControllerAdvice(final AutomationErrorResponseBuilder automationErrorResponseBuilder) {
        this.automationErrorResponseBuilder = automationErrorResponseBuilder;
    }

    /**
     * This method handling the exception of type AutomationException and returning the appropriate AutomationResponse.
     * 
     * @param automationException {@link AutomationException} object
     * @param request Object of incoming {@link HttpServletRequest request}
     * @return {@code ResponseEntity<AutomationResponse>} Response wrapped with CXS Envelope error message
     * @since 09-Jul-2019
     */
    @ExceptionHandler(AutomationException.class)
    public ResponseEntity<AutomationResponse> handleAutomationException(final AutomationException automationException,
                    HttpServletRequest request) {
        logger.error("AutomationException has occured for request from: '{}' Exception: {}",
                        getRequestOriginAddress(request.getRemoteAddr()),
                        ExceptionUtils.getStackTrace(automationException));
        final AutomationError automationError =
                        new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_INERNAL_SERVER_ERROR_WITH_CUSTOM_MESSAGE,
                                        automationException.getMessage()).convertToAutomationError();
        final AutomationResponse automationResponse = AutomationResponse.error(automationError);
        // Returning Http status & filled AutomationResponse as per error code received from response builder
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(automationResponse);
    }

    /**
     * This method handling the exception of type FastTestBadRequestException and returning the appropriate
     * AutomationResponse.
     * 
     * @param automationException {@link FastTestBadRequestException} object
     * @param request Object of incoming {@link HttpServletRequest request}
     * @return {@code ResponseEntity<AutomationResponse>} Response wrapped with CXS Envelope error message
     * @since 09-Jul-2019
     */
    @ExceptionHandler(FastTestBadRequestException.class)
    public ResponseEntity<AutomationResponse> handleFastTestBadRequestException(
                    final FastTestBadRequestException fastTestBadRequestException, HttpServletRequest request) {
        logger.error("FastTestBadRequestException has occured for request from: '{}' Exception: {}",
                        getRequestOriginAddress(request.getRemoteAddr()),
                        ExceptionUtils.getStackTrace(fastTestBadRequestException));
        final AutomationResponse automationResponse = AutomationResponse
                        .error(convertErrorCodesDtoListToCXSErrorList(fastTestBadRequestException.getErrorCodes()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(automationResponse);
    }

    /**
     * This method handling the exception of type HttpMessageNotReadableException and returning the appropriate
     * AutomationResponse
     * 
     * @param httpMessageNotReadableException {@link HttpMessageNotReadableException} object
     * @param request Object of incoming {@link HttpServletRequest request}
     * @return {@code ResponseEntity<AutomationResponse>} Response wrapped with CXS Envelope error message
     * @since Aug 5, 2019
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AutomationResponse> handleHttpMessageNotReadableException(
                    final HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request) {
        logger.error("HttpMessageNotReadableException has occured for request from: '{}' Exception: {}",
                        getRequestOriginAddress(request.getRemoteAddr()),
                        ExceptionUtils.getStackTrace(httpMessageNotReadableException));
        Throwable throwable = httpMessageNotReadableException.getCause();
        AutomationError automationError = null;
        if (throwable != null) {
            if (throwable instanceof InvalidFormatException) {
                automationError = automationErrorResponseBuilder
                                .buildInvalidInputExceptionErrorResponse((InvalidFormatException) throwable);
            } else if (throwable instanceof UnrecognizedPropertyException) {
                automationError = automationErrorResponseBuilder
                                .buildUnrecognizedExceptionErrorResponse((UnrecognizedPropertyException) throwable);
            } else if (throwable instanceof MismatchedInputException) {
                automationError = automationErrorResponseBuilder
                                .buildMisMatchInputExceptionErrorResponse((MismatchedInputException) throwable);
            } else if (throwable instanceof JsonMappingException) {
                automationError = new AutomationError(
                                AutomationErrorCodes.AUTOMATION_REQUESTBODY_INVALID_EXCEPTION.getCode(),
                                AutomationErrorCodes.AUTOMATION_REQUESTBODY_INVALID_EXCEPTION.getMessage());
            } else {
                automationError = new AutomationError(
                                AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION.getCode(),
                                AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION.getMessage());
            }
        } else {
            automationError =
                            new AutomationError(AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION.getCode(),
                                            AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION.getMessage());
        }
        final AutomationResponse automationResponse = AutomationResponse.error(automationError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(automationResponse);
    }

    @ExceptionHandler(RandomGenerationAutomationException.class)
    public ResponseEntity<AutomationResponse> handleRandomGenerationAutomationException(
                    final RandomGenerationAutomationException randomGenerationAutomationException,
                    HttpServletRequest request) {
        logger.error("RandomGenerationAutomationException has occured for request from: '{}' Exception: {}",
                        getRequestOriginAddress(request.getRemoteAddr()),
                        ExceptionUtils.getStackTrace(randomGenerationAutomationException));
        final AutomationResponse automationResponse = AutomationResponse.error(
                        new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_INERNAL_SERVER_ERROR_WITH_CUSTOM_MESSAGE,
                                        randomGenerationAutomationException.getMessage()).convertToAutomationError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(automationResponse);
    }

    @ExceptionHandler(StringToJsonParseException.class)
    public ResponseEntity<AutomationResponse> stringToJsonParseException(
                    final StringToJsonParseException stringToJsonParseException) {
        final AutomationResponse automationResponse = AutomationResponse.error(
                        new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_INERNAL_SERVER_ERROR_WITH_CUSTOM_MESSAGE,
                                        stringToJsonParseException.getMessage()).convertToAutomationError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(automationResponse);
    }

}
