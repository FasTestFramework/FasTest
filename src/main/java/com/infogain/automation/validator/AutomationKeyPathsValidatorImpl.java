package com.infogain.automation.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.infogain.automation.dto.AutomationBVAMetadataDTO;
import com.infogain.automation.dto.AutomationBoundaryValueAnalysisDTO;
import com.infogain.automation.dto.AutomationKeyPathsDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationKeyPathsValidatorImpl
                implements ConstraintValidator<AutomationKeyPathsValidator, AutomationKeyPathsDTO> {

    /**
     * This method calls to validate the request payload
     * 
     * @param value the {@link AutomationKeyPathsDTO} to validate
     * @param context the {@link ConstraintValidatorContext}
     * @return {@code true} if requestDTO is valid<br>
     *         {@code false} if requestDTO is invalid
     * @since 15-Jul-2019
     */
    @Override
    public boolean isValid(AutomationKeyPathsDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            throw new FastTestBadRequestException(AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION);
        } else if (value.getData() == null) {
            throw new FastTestBadRequestException(AutomationErrorCodes.AUTOMATION_KEY_PATHS_MISSING_DATA_EXCEPTION);
        }
        return true;
    }
}
