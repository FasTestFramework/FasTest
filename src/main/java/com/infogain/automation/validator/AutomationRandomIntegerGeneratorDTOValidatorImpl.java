package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRandomIntegerGeneratorDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.utilities.AutomationUtility;

public class AutomationRandomIntegerGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomIntegerGeneratorDTOValidator, AutomationRandomIntegerGeneratorDTO> {


    private static final Logger logger = LogManager.getLogger(AutomationRandomIntegerGeneratorDTOValidatorImpl.class);
    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomIntegerGeneratorDTOValidatorImpl(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    @Override
    public boolean isValid(AutomationRandomIntegerGeneratorDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodesList = new ArrayList<>();
        String exclusions = value.getExclusions();
        Integer max = value.getMaxValue();
        Integer min = value.getMinValue();
        if (exclusions != null && !exclusions.isEmpty()) {
            if ((max == null || min == null)) {
                errorCodesList.add(
                                new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_RANGE_EXCEPTION));
            } else if (max < min) {
                errorCodesList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_RANGE_EXCEPTION, min, max));
            }
            String integerRegex = automationUtility.fetchCommaSeperatedValueRegex(AutomationConstants.INTEGER_REGEX);
            if (!exclusions.matches(integerRegex)) {
                errorCodesList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_DATA_EXCEPTION, "exclusions",
                                exclusions));
            }
        }
        if (!errorCodesList.isEmpty()) {
            throw new FastTestBadRequestException(errorCodesList);
        }
        return true;
    }
}
