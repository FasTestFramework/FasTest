package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.utilities.AutomationUtility;

public class AutomationRandomStringSmallCapitalMixGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomStringSmallCapitalMixGeneratorDTOValidator, AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO> {


    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomStringSmallCapitalMixGeneratorDTOValidatorImpl.class);

    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomStringSmallCapitalMixGeneratorDTOValidatorImpl(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    @Override
    public boolean isValid(AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO value,
                    ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errocodesList = new ArrayList<>();
        Integer length = value.getLength();
        String inclusions = value.getInclusions();
        String exclusions = value.getExclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        if (length != null && length <= 0) {
            errocodesList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION, value.getLength()));
        }
        if (inclusionsFlag && exclusionsFlag) {
            errocodesList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION));
        }
        if (inclusionsFlag || exclusionsFlag) {
            String inclusionsOrExclusions = inclusionsFlag ? inclusions : exclusions;
            String aphaRegex = automationUtility.fetchCommaSeperatedValueRegex(AutomationConstants.ALPHA_REGEX);
            if (!inclusionsOrExclusions.matches(aphaRegex)) {
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
