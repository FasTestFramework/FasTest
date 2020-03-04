package com.infogain.automation.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationGenerateRandomSentenceDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationRandomSentenceGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomSentenceGeneratorDTOValidator, AutomationGenerateRandomSentenceDTO> {


    private static final Logger logger = LogManager.getLogger(AutomationRandomSentenceGeneratorDTOValidatorImpl.class);


    @Override
    public boolean isValid(AutomationGenerateRandomSentenceDTO value, ConstraintValidatorContext context) {
        Integer length = value.getLength();
        if (length != null && length <= 0) {
            throw new FastTestBadRequestException(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION, value.getLength()));
        }
        return true;
    }
}
