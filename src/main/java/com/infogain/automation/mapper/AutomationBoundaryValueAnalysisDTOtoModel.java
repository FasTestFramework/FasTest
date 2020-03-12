package com.infogain.automation.mapper;


import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.infogain.automation.dto.AutomationBoundaryValueAnalysisDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.model.AutomationBoundaryValueAnalysisModel;

public class AutomationBoundaryValueAnalysisDTOtoModel {

    private AutomationBoundaryValueAnalysisDTOtoModel() {}

    public static AutomationBoundaryValueAnalysisModel convert(
                    AutomationBoundaryValueAnalysisDTO automationBoundaryValueAnalysisDTO) {
        Object data = automationBoundaryValueAnalysisDTO.getData();
        JSONAware jsonAware = null;
        try {
            if (data instanceof Map) {
                jsonAware = (JSONAware) new JSONParser().parse(JSONObject.toJSONString(((Map) data)));
            } else if (data instanceof List) {
                jsonAware = (JSONAware) new JSONParser().parse(JSONArray.toJSONString(((List) data)));
            }
        } catch (ParseException e) {
            // No handling is needed
        }
        if (jsonAware == null) {
            throw new FastTestBadRequestException(
                            new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_INVALID_DATA_EXCEPTION, "data",
                                            "Object is not in Json format."));
        }
        return new AutomationBoundaryValueAnalysisModel(jsonAware, automationBoundaryValueAnalysisDTO.getMetaData(),
                        automationBoundaryValueAnalysisDTO.getFolderName(),
                        automationBoundaryValueAnalysisDTO.getFileName());

    }
}
