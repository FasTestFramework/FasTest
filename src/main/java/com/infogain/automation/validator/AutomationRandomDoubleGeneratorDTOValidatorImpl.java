package com.infogain.automation.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationRandomDoubleGenerateDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationRandomDoubleGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomDoubleGeneratorDTOValidator, AutomationRandomDoubleGenerateDTO> {


    private static final Logger logger = LogManager.getLogger(AutomationRandomDoubleGeneratorDTOValidatorImpl.class);


    @Override
    public boolean isValid(AutomationRandomDoubleGenerateDTO value, ConstraintValidatorContext context) {
        if (!(value.getMin() instanceof Double) || !(value.getMax() instanceof Double)) {

            throw new FastTestBadRequestException(AutomationErrorCodes.AUTOMATION_INVALID_INPUT_EXCEPTION);
        }
        return true;
    }
}
