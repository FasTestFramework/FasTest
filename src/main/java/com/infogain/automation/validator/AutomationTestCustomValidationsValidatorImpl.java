package com.infogain.automation.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.infogain.automation.dto.AutomationBVAMetadataDTO;
import com.infogain.automation.dto.AutomationTestCustomValidationsDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationTestCustomValidationsValidatorImpl implements
                ConstraintValidator<AutomationTestCustomValidationsValidator, AutomationTestCustomValidationsDTO> {

    /**
     * This method calls to validate the request payload
     * 
     * @param value the {@link AutomationTestCustomValidationsDTO} to validate
     * @param context the {@link ConstraintValidatorContext}
     * @return {@code true} if requestDTO is valid<br>
     *         {@code false} if requestDTO is invalid
     * @since 15-Jul-2019
     */
    @Override
    public boolean isValid(AutomationTestCustomValidationsDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodes = new ArrayList<>();
        if (value == null) {
            errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION));
        } else {
            if (value.getData() == null) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_TEST_CUSTOM_VALIDATIONS_MISSING_DATA_EXCEPTION, "data"));
            }
            Map<String, List<String>> metaDataList = value.getCustomValidations();
            if (metaDataList == null || metaDataList.isEmpty()) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_TEST_CUSTOM_VALIDATIONS_MISSING_DATA_EXCEPTION,
                                "customValidations"));
            }
            if (!errorCodes.isEmpty()) {
                throw new FastTestBadRequestException(errorCodes);
            }
        }
        return true;
    }
}
