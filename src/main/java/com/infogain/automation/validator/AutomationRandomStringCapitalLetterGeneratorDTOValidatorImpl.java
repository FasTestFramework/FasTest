package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationRandomStringCapitalLetterGeneratorDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.utilities.AutomationUtility;

public class AutomationRandomStringCapitalLetterGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomStringCapitalLetterGeneratorDTOValidator, AutomationRandomStringCapitalLetterGeneratorDTO> {


    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomStringCapitalLetterGeneratorDTOValidatorImpl.class);

    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomStringCapitalLetterGeneratorDTOValidatorImpl(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    @Override
    public boolean isValid(AutomationRandomStringCapitalLetterGeneratorDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodeList = new ArrayList<>();
        final char EMPTY = Character.MIN_VALUE;
        Integer length = value.getLength();
        char startCharacter = value.getStartCharacter();
        char endCharacter = value.getEndCharacter();
        char constCharacter = value.getConstantCharacter();
        String inclusions = value.getInclusions();
        String exclusions = value.getExclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        if (length != null && length <= 0) {
            errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION,
                            value.getLength()));
        }
        if (constCharacter != EMPTY && (startCharacter != EMPTY || endCharacter != EMPTY || inclusions != null
                        || exclusions != null)) {
            errorCodeList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_CONSTANT_CHARACTER_STRING_EXCEPTION));
            if (!Character.isAlphabetic(constCharacter) || Character.isLowerCase(constCharacter)) {
                errorCodeList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_STRING_GENERATION_INVALID_ALPHABETS_EXCEPTION,
                                "constCharacter", "UPPER CASE"));
            }
        }
        if (inclusionsFlag && exclusionsFlag) {
            errorCodeList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION));
        }
        if (Character.isLowerCase(startCharacter) || !Character.isAlphabetic(startCharacter)
                        || !Character.isAlphabetic(endCharacter) || Character.isLowerCase(endCharacter)) {
            errorCodeList.add(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_STRING_GENERATION_INVALID_ALPHABETS_EXCEPTION,
                            "startCharcter, endCharcater", "UPPER CASE"));
        } else if (startCharacter > endCharacter) {
            errorCodeList.add(
                            new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_RANGE_EXCEPTION,
                                            startCharacter, endCharacter));

        }
        if (inclusionsFlag || exclusionsFlag) {
            String inclusionsOrExclusions = inclusionsFlag ? inclusions : exclusions;
            String capsRegex = automationUtility.fetchCommaSeperatedValueRegex(AutomationConstants.CAPS_REGEX);
            if (!inclusionsOrExclusions.matches(capsRegex)) {
                errorCodeList.add(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_DATA_EXCEPTION,
                                inclusionsFlag ? "inclusions" : "exclusions", inclusionsOrExclusions));
            }
        }
        if (startCharacter != EMPTY && endCharacter == EMPTY || endCharacter != EMPTY && startCharacter == EMPTY) {
            errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_RANGE_EXCEPTION));
        }
        if (!errorCodeList.isEmpty()) {
            throw new FastTestBadRequestException(errorCodeList);
        }
        return true;
    }
}
