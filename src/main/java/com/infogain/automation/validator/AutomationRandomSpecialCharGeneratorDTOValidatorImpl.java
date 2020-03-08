package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRandomSpecialCharGeneratorDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.utilities.AutomationUtility;

public class AutomationRandomSpecialCharGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomSpecialCharGeneratorDTOValidator, AutomationRandomSpecialCharGeneratorDTO> {


    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomSpecialCharGeneratorDTOValidatorImpl.class);
    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomSpecialCharGeneratorDTOValidatorImpl(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    @Override
    public boolean isValid(AutomationRandomSpecialCharGeneratorDTO value, ConstraintValidatorContext context) {
        Integer length = value.getLength();
        String inclusions = value.getInclusions();
        String exclusions = value.getExclusions();
        List<ErrorCodesDTO> errorCodeList = new ArrayList<>();
        if (length != null && length <= 0) {
            errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION,
                            length));
        }
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        if (inclusionsFlag || exclusionsFlag) {
            String inclusionsOrExclusions = inclusionsFlag ? inclusions : exclusions;
            if (inclusionsOrExclusions.length() < 2 || inclusionsOrExclusions.contains(",")) {
                if (!inclusionsOrExclusions.matches(automationUtility
                                .fetchCommaSeperatedValueRegex(AutomationConstants.SPECIAL_CHARACTERS_REGEX))) {
                    errorCodeList.add(new ErrorCodesDTO(
                                    AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_DATA_EXCEPTION,
                                    inclusionsFlag ? "inclusions" : "exclusions", inclusionsOrExclusions));
                }
            } else {
                String message = "'" + inclusionsOrExclusions + "'"
                                + " inclusions and exclusions characters must be seperated by ',' ";
                errorCodeList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_DATA_EXCEPTION,
                                inclusions != null ? "inclusions" : "exclusions", message));

            }
        }
        if (inclusionsFlag && exclusionsFlag) {
            errorCodeList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION));
        }
        if (!errorCodeList.isEmpty()) {
            throw new FastTestBadRequestException(errorCodeList);
        }
        return true;
    }
}
