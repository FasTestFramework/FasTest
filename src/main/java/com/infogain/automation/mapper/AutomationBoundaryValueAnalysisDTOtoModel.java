package com.infogain.automation.mapper;


import org.json.simple.JSONAware;

import com.infogain.automation.dto.AutomationBoundaryValueAnalysisDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.model.AutomationBoundaryValueAnalysisModel;
import com.infogain.automation.utilities.AutomationJsonUtility;
import com.infogain.automation.utilities.BeanUtil;

public class AutomationBoundaryValueAnalysisDTOtoModel {

    private AutomationBoundaryValueAnalysisDTOtoModel() {}

    public static AutomationBoundaryValueAnalysisModel convert(
                    AutomationBoundaryValueAnalysisDTO automationBoundaryValueAnalysisDTO) {
        AutomationJsonUtility automationJsonUtility = BeanUtil.getBean(AutomationJsonUtility.class);

        Object data = automationBoundaryValueAnalysisDTO.getData();
        JSONAware jsonAware = automationJsonUtility.extractJsonObjectFromMapOrList(data);
        if (jsonAware == null) {
            throw new FastTestBadRequestException(
                            new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_INVALID_DATA_EXCEPTION, "data", data,
                                            "Object is not in Json format."));
        }
        return new AutomationBoundaryValueAnalysisModel(jsonAware, automationBoundaryValueAnalysisDTO.getMetaData(),
                        automationBoundaryValueAnalysisDTO.getFolderName(),
                        automationBoundaryValueAnalysisDTO.getFileName());

    }

}
