package com.infogain.automation.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationRandomStringEverythingDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationRandomStringWithEverythingGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomStringWithEverythingGeneratorDTOValidator, AutomationRandomStringEverythingDTO> {


    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomStringWithEverythingGeneratorDTOValidatorImpl.class);


    @Override
    public boolean isValid(AutomationRandomStringEverythingDTO value, ConstraintValidatorContext context) {
        Integer length = value.getLength();
        if (length != null && length <= 0) {
            throw new FastTestBadRequestException(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION, value.getLength()));
        }
        if (value.getExclusions() != null && value.getInclusions() != null) {
            throw new FastTestBadRequestException(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION));
        }
        return true;
    }
}
