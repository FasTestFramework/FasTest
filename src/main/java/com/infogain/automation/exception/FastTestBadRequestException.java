package com.infogain.automation.exception;

import java.util.Collections;
import java.util.List;

import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;

public class FastTestBadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final List<ErrorCodesDTO> errorCodesList;

    public FastTestBadRequestException(String message) {
        super(message);
        this.errorCodesList = null;
    }

    public FastTestBadRequestException(String message, Throwable cause) {
        super(message, cause);
        this.errorCodesList = null;
    }

    public FastTestBadRequestException(List<ErrorCodesDTO> errorCodesList) {
        super(errorCodesList.toString());
        this.errorCodesList = errorCodesList;
    }

    public FastTestBadRequestException(ErrorCodesDTO errorCodesList) {
        super(errorCodesList.toString());
        this.errorCodesList = Collections.singletonList(errorCodesList);
    }

    public FastTestBadRequestException(AutomationErrorCodes automationErrorCodes) {
        super(automationErrorCodes.getCode() + automationErrorCodes.getMessage());
        this.errorCodesList = Collections.singletonList(new ErrorCodesDTO(automationErrorCodes));
    }

    public List<ErrorCodesDTO> getErrorCodes() {
        return errorCodesList;
    }
}
