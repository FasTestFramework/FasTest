package com.infogain.automation.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationGenerateRandomSentenceDTO;
import com.infogain.automation.dto.AutomationRandomStringCapitalLetterGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringEverythingDTO;
import com.infogain.automation.dto.AutomationRandomStringSmallLetterGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationRandomStringCapitalLetterGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomStringCapitalLetterGeneratorDTOValidator, AutomationRandomStringCapitalLetterGeneratorDTO> {


    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomStringCapitalLetterGeneratorDTOValidatorImpl.class);


    @Override
    public boolean isValid(AutomationRandomStringCapitalLetterGeneratorDTO value, ConstraintValidatorContext context) {
        Integer length = value.getLength();
        if (length != null && length <= 0) {
            throw new FastTestBadRequestException(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION, value.getLength()));
        }
        return true;
    }
}
