package com.infogain.automation.validator;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.dto.SendMailRequestDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationSendMailValidatorImpl
                implements ConstraintValidator<AutomationSendMailValidator, SendMailRequestDTO> {

    /**
     * This method calls to validate the request payload
     * 
     * @param value the {@link SendMailRequestDTO} to validate
     * @param context the {@link ConstraintValidatorContext}
     * @return {@code true} if requestDTO is valid<br>
     *         {@code false} if requestDTO is invalid
     * @since 15-Jul-2019
     */
    @Override
    public boolean isValid(SendMailRequestDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodes = new ArrayList<>();
        if (value == null) {
            errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION));
        } else {
            errorCodes = new ArrayList<>();
            String fileBase64Data = value.getFileBase64Data();
            if (StringUtils.isBlank(fileBase64Data)) {
                errorCodes.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_SEND_MAIL_MISSING_BASE64_DATA_EXCEPTION));
            } else {
                try {
                    Base64.getDecoder().decode(fileBase64Data);
                } catch (IllegalArgumentException e) {
                    errorCodes.add(new ErrorCodesDTO(
                                    AutomationErrorCodes.AUTOMATION_SEND_MAIL_INVALID_BASE64_DATA_EXCEPTION,
                                    fileBase64Data, "It does not have valid base64 string."));
                }
            }
        }
        if (!errorCodes.isEmpty()) {
            throw new FastTestBadRequestException(errorCodes);
        }
        return true;
    }
}
