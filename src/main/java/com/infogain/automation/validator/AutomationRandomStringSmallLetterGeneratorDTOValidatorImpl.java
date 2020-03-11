package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRandomStringSmallLetterGeneratorDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.utilities.AutomationUtility;

public class AutomationRandomStringSmallLetterGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomStringSmallLetterGeneratorDTOValidator, AutomationRandomStringSmallLetterGeneratorDTO> {

    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomStringSmallLetterGeneratorDTOValidatorImpl.class);

    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomStringSmallLetterGeneratorDTOValidatorImpl(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    @Override
    public boolean isValid(AutomationRandomStringSmallLetterGeneratorDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodeList = new ArrayList<>();
        final char EMPTY = Character.MIN_VALUE;
        Integer length = value.getLength();
        char startCharacter = value.getStartCharacter();
        char endCharacter = value.getEndCharacter();
        String inclusions = value.getInclusions();
        String exclusions = value.getExclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        if (length != null && length <= 0) {
            errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION,
                            value.getLength()));
        }
        if (inclusionsFlag && exclusionsFlag) {
            errorCodeList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION));
        }
        int rangeCheckRequired = 0;
        if (startCharacter != EMPTY) {
            if (endCharacter == EMPTY) {
                errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_RANGE_EXCEPTION));
            }
            if (!Character.isLowerCase(startCharacter)) {
                errorCodeList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_STRING_GENERATION_INVALID_ALPHABETS_EXCEPTION,
                                "startCharcter", "LOWER CASE"));
            } else {
                rangeCheckRequired++;
            }
        }
        if (endCharacter != EMPTY) {
            if (startCharacter == EMPTY) {
                errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_RANGE_EXCEPTION));
            }
            if (!Character.isLowerCase(endCharacter)) {
                errorCodeList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_STRING_GENERATION_INVALID_ALPHABETS_EXCEPTION,
                                "endCharacter", "LOWER CASE"));
            } else {
                rangeCheckRequired++;
            }
        }
        if (rangeCheckRequired == 2 && startCharacter >= endCharacter) {
            errorCodeList.add(
                            new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_RANGE_EXCEPTION,
                                            startCharacter, endCharacter));
        }

        if (startCharacter != EMPTY && endCharacter == EMPTY || endCharacter != EMPTY && startCharacter == EMPTY) {
            errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_RANGE_EXCEPTION));
        }

        if (inclusionsFlag || exclusionsFlag) {
            String inclusionsOrExclusions = inclusionsFlag ? inclusions : exclusions;
            String smallRegex = automationUtility.fetchCommaSeperatedValueRegex(AutomationConstants.SMALL_REGEX);
            if (!inclusionsOrExclusions.matches(smallRegex)) {
                errorCodeList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_DATA_EXCEPTION,
                                inclusionsFlag ? "inclusions" : "exclusions", inclusionsOrExclusions));
            }
        }

        if (!errorCodeList.isEmpty()) {
            throw new FastTestBadRequestException(errorCodeList);
        }
        return true;
    }
}
