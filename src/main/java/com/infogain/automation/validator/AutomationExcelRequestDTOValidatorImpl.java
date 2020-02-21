package com.infogain.automation.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationExcelRequestDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;

public class AutomationExcelRequestDTOValidatorImpl
                implements ConstraintValidator<AutomationExcelRequestDTOValidator, AutomationExcelRequestDTO> {

    private List<ErrorCodesDTO> errorCodes;

    private static final Logger logger = LogManager.getLogger(AutomationExcelRequestDTOValidatorImpl.class);

    /**
     * This method calls to validate the request payload
     * 
     * @param requestDTO the {@link AutomationExcelRequestDTO} to validate
     * @param context the {@link ConstraintValidatorContext}
     * @return {@code true} if requestDTO is valid<br>
     *         {@code false} if requestDTO is invalid
     * @since 15-Jul-2019
     */
    @Override
    public boolean isValid(AutomationExcelRequestDTO value, ConstraintValidatorContext context) {
        if (value == null || value.getRowData() == null) {
            throw new FastTestBadRequestException(
                            AutomationErrorCodes.AUTOMATION_REQUESTBODY_MISSING_EXCEPTION);
        } else {
            errorCodes = new ArrayList<>();
            if (value.getRowData().isEmpty()) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.EXCEL_ROW_DATA_EMPTY_EXCEPTION));
            }
            value.getRowData().forEach(automationExcelInputDTO -> {
                if (StringUtils.isBlank(automationExcelInputDTO.getInputExcelFolderName())) {
                    automationExcelInputDTO.setInputExcelFolderName("");
                }
                if (StringUtils.isEmpty(automationExcelInputDTO.getInputExcelFileName())) {
                    errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.INPUT_EXCEL_FILE_NAME_EXCEPTION));
                }
                if (StringUtils.isEmpty(automationExcelInputDTO.getInputExcelSheetName())) {
                    errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.INPUT_EXCEL_SHEET_NAME_EXCEPTION));
                }
            });
            if (!errorCodes.isEmpty()) {
                throw new FastTestBadRequestException(errorCodes);
            }
        }
        return true;
    }
}
