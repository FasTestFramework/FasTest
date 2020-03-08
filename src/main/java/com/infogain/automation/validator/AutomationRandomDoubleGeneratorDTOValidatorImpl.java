package com.infogain.automation.validator;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationRandomDoubleGenerateDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationRandomDoubleGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomDoubleGeneratorDTOValidator, AutomationRandomDoubleGenerateDTO> {

    private static final Logger logger = LogManager.getLogger(AutomationRandomDoubleGeneratorDTOValidatorImpl.class);

    @Override
    public boolean isValid(AutomationRandomDoubleGenerateDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodeList = new ArrayList<>();
        Double min = value.getMin();
        Double max = value.getMax();
        if (value.getPrecision() != null && value.getPrecision() < 0) {
            errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_PRECISION_EXCEPTION,
                            value.getPrecision()));
        }
        if (min == null || min == null) {
            errorCodeList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_RANGE_EXCEPTION));
        }
        if (min != null && max != null && max < min) {
            errorCodeList.add(
                            new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_RANDOM_GENERATION_INVALID_RANGE_EXCEPTION,
                                            value.getMin(), value.getMax()));
        }
        if (!errorCodeList.isEmpty()) {
            throw new FastTestBadRequestException(errorCodeList);
        }
        return true;
    }
}
