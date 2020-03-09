package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRandomGenerateAlphaNumericDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.utilities.AutomationUtility;

public class AutomationRandomAplhaNumericGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomAplhaNumericGeneratorDTOValidator, AutomationRandomGenerateAlphaNumericDTO> {


    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomAplhaNumericGeneratorDTOValidatorImpl.class);
    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomAplhaNumericGeneratorDTOValidatorImpl(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    @Override
    public boolean isValid(AutomationRandomGenerateAlphaNumericDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodeList = new ArrayList<>();
        Integer length = value.getLength();
        if (length != null && length <= 0) {
            errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION,
                            value.getLength()));
        }
        String exclusions = value.getExclusions();
        String inclusions = value.getInclusions();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        if (inclusionsFlag || exclusionsFlag) {
            String inclusionsOrExclusions = inclusionsFlag ? inclusions : exclusions;
            String aphaNumericRegex =
                            automationUtility.fetchCommaSeperatedValueRegex(AutomationConstants.ALPHANUMERIC_REGEX);
            if (!inclusionsOrExclusions.matches(aphaNumericRegex)) {
                errorCodeList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_DATA_EXCEPTION,
                                inclusionsFlag ? "inclusions" : "exclusions", inclusionsOrExclusions));
            }
        }
        if (exclusionsFlag && inclusionsFlag) {
            errorCodeList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION));
        }
        if (!errorCodeList.isEmpty()) {
            throw new FastTestBadRequestException(errorCodeList);
        }
        return true;
    }
}
