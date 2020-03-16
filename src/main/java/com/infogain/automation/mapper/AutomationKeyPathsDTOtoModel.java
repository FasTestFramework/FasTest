package com.infogain.automation.mapper;


import org.json.simple.JSONAware;

import com.infogain.automation.dto.AutomationKeyPathsDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.model.AutomationKeyPathsModel;
import com.infogain.automation.utilities.AutomationJsonUtility;
import com.infogain.automation.utilities.BeanUtil;

public class AutomationKeyPathsDTOtoModel {

    private AutomationKeyPathsDTOtoModel() {}

    public static AutomationKeyPathsModel convert(AutomationKeyPathsDTO automationKeyPathsDTO) {
        AutomationJsonUtility automationJsonUtility = BeanUtil.getBean(AutomationJsonUtility.class);

        Object data = automationKeyPathsDTO.getData();
        JSONAware jsonAware = automationJsonUtility.extractJsonObjectFromMapOrList(data);
        if (jsonAware == null) {
            throw new FastTestBadRequestException(
                            new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_KEY_PATHS_INVALID_DATA_EXCEPTION, data));
        }
        return new AutomationKeyPathsModel(jsonAware);

    }

}
