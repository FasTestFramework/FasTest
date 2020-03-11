package com.infogain.automation.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.infogain.automation.dto.AutomationBVAMetadataDTO;
import com.infogain.automation.dto.AutomationBoundaryValueAnalysisDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationBVAValidatorImpl
                implements ConstraintValidator<AutomationBVAValidator, AutomationBoundaryValueAnalysisDTO> {

    /**
     * This method calls to validate the request payload
     * 
     * @param value the {@link AutomationBoundaryValueAnalysisDTO} to validate
     * @param context the {@link ConstraintValidatorContext}
     * @return {@code true} if requestDTO is valid<br>
     *         {@code false} if requestDTO is invalid
     * @since 15-Jul-2019
     */
    @Override
    public boolean isValid(AutomationBoundaryValueAnalysisDTO value, ConstraintValidatorContext context) {
        List<ErrorCodesDTO> errorCodes;
        if (value == null) {
            throw new FastTestBadRequestException(AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION);
        } else {
            errorCodes = new ArrayList<>();
            if (value.getData() == null) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_MISSING_DATA_EXCEPTION, "data"));
            }
            List<AutomationBVAMetadataDTO> metaDataList = value.getMetaData();
            if (metaDataList == null || metaDataList.isEmpty()) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_MISSING_DATA_EXCEPTION,
                                "metaData"));
            } else {
                for (int i = 0; i < metaDataList.size(); i++) {
                    AutomationBVAMetadataDTO metaData = metaDataList.get(i);
                    if (StringUtils.isBlank(metaData.getKeyName())) {
                        errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_MISSING_DATA_EXCEPTION,
                                        "metaData[" + i + "].keyName"));
                    }
                    if (metaData.getType() == null) {
                        errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_MISSING_DATA_EXCEPTION,
                                        "metaData[" + i + "].type"));
                    }
                    Double min = metaData.getMin();
                    Double max = metaData.getMax();
                    if (min != null && max != null && max < min) {
                        errorCodes.add(new ErrorCodesDTO(
                                        AutomationErrorCodes.AUTOMATION_BVA_INVAID_MIN_MAX_DATA_EXCEPTION, min, max));
                    }
                    Integer length = metaData.getLength();
                    if (length != null && length < 1) {
                        errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_INVALID_DATA_EXCEPTION,
                                        "metaData[" + i + "].type", length, "Invalid length."));
                    }
                }
            }
            if (StringUtils.isBlank(value.getFileName())) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_MISSING_DATA_EXCEPTION,
                                "fileName"));
            }
            if (StringUtils.isBlank(value.getFolderName())) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_MISSING_DATA_EXCEPTION,
                                "folderName"));
            }

            if (!errorCodes.isEmpty()) {
                throw new FastTestBadRequestException(errorCodes);
            }
        }
        return true;
    }
}
