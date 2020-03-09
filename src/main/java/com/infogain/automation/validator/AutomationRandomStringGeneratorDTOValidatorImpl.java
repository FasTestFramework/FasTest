package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRandomStringGeneratorDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.utilities.AutomationUtility;

public class AutomationRandomStringGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomStringGeneratorDTOValidator, AutomationRandomStringGeneratorDTO> {


    private static final Logger logger = LogManager.getLogger(AutomationRandomStringGeneratorDTOValidatorImpl.class);
    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomStringGeneratorDTOValidatorImpl(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    @Override
    public boolean isValid(AutomationRandomStringGeneratorDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errocodesList = new ArrayList<>();
        Integer length = value.getLength();
        String exclusions = value.getExclusions();
        String inclusions = value.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        if (length != null && length <= 0) {
            errocodesList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION,
                            value.getLength()));
        }
        if (inclusionsFlag && exclusionsFlag) {
            errocodesList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION));

        } else if (inclusionsFlag || exclusionsFlag) {
            String inclusionsOrExclusions = inclusionsFlag ? inclusions : exclusions;
            String singleDigitRegex =
                            automationUtility.fetchCommaSeperatedValueRegex(AutomationConstants.SINGLE_DIGIT_REGEX);
            if (!inclusionsOrExclusions.matches(singleDigitRegex)) {
                errocodesList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_DATA_EXCEPTION,
                                inclusionsFlag ? "inclusions" : "exclusions", inclusionsOrExclusions));
            }
        }
        if (!errocodesList.isEmpty()) {
            throw new FastTestBadRequestException(errocodesList);
        }
        return true;

    }
}
