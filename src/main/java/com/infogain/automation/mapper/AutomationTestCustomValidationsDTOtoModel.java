package com.infogain.automation.mapper;


import org.json.simple.JSONAware;

import com.infogain.automation.dto.AutomationTestCustomValidationsDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.model.AutomationTestCustomValidationsModel;
import com.infogain.automation.utilities.AutomationJsonUtility;
import com.infogain.automation.utilities.BeanUtil;

public class AutomationTestCustomValidationsDTOtoModel {

    private AutomationTestCustomValidationsDTOtoModel() {}

    public static AutomationTestCustomValidationsModel convert(
                    AutomationTestCustomValidationsDTO automationTestCustomValidationsDTO) {
        AutomationJsonUtility automationJsonUtility = BeanUtil.getBean(AutomationJsonUtility.class);

        Object data = automationTestCustomValidationsDTO.getData();
        JSONAware jsonAware = automationJsonUtility.extractJsonObjectFromMapOrList(data);
        if (jsonAware == null) {
            throw new FastTestBadRequestException(new ErrorCodesDTO(
                            AutomationErrorCodes.AUTOMATION_TEST_CUSTOM_VALIDATIONS_INVALID_DATA_EXCEPTION, data));
        }
        return new AutomationTestCustomValidationsModel(jsonAware,
                        automationTestCustomValidationsDTO.getCustomValidations());

    }

}
