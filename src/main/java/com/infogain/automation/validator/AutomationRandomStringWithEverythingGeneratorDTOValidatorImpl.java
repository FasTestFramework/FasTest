package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRandomStringEverythingDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.utilities.AutomationUtility;

public class AutomationRandomStringWithEverythingGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomStringWithEverythingGeneratorDTOValidator, AutomationRandomStringEverythingDTO> {


    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomStringWithEverythingGeneratorDTOValidatorImpl.class);

    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomStringWithEverythingGeneratorDTOValidatorImpl(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    @Override
    public boolean isValid(AutomationRandomStringEverythingDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errocodesList = new ArrayList<>();
        Integer length = value.getLength();
        if (length != null && length <= 0) {
            throw new FastTestBadRequestException(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION, value.getLength()));
        }
        String exclusions = value.getExclusions();
        String inclusions = value.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        if (inclusionsFlag && exclusionsFlag) {
            errocodesList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION));
        } else if (inclusionsFlag || exclusionsFlag) {
            String inclusionsOrExclusions = inclusionsFlag ? inclusions : exclusions;
            String anyCharRegex = automationUtility.fetchCommaSeperatedValueRegex(AutomationConstants.ANY_CHAR_REGEX);
            if (!inclusionsOrExclusions.matches(anyCharRegex)) {
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
