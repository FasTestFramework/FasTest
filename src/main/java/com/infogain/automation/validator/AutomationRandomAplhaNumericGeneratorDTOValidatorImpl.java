package com.infogain.automation.validator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationRandomGenerateAlphaNumericDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationRandomAplhaNumericGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomAplhaNumericGeneratorDTOValidator, AutomationRandomGenerateAlphaNumericDTO> {


    private static final Logger logger =
                    LogManager.getLogger(AutomationRandomAplhaNumericGeneratorDTOValidatorImpl.class);


    @Override
    public boolean isValid(AutomationRandomGenerateAlphaNumericDTO value, ConstraintValidatorContext context) {
        Integer length = value.getLength();
        if (length != null && length <= 0) {
            throw new FastTestBadRequestException(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION, value.getLength()));
        }
        if (value.getExclusions() != null) {
            String str = new String(value.getExclusions());
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
            Matcher matcher = pattern.matcher(str);

            if (!matcher.matches()) {
                throw new FastTestBadRequestException(new ErrorCodesDTO(
                                AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_EXCLUSION_EXCEPTION, value.getExclusions()));
            }
        }
        return true;
    }
}
