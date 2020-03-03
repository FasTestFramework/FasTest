package com.infogain.automation.mapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.infogain.automation.dto.AutomationBoundaryValueAnalysisJsonDTO;
import com.infogain.automation.exception.StringToJsonParseException;

public class AutomationBoundaryValueAnalysisStringToJson {

    private static final String FILE_NAME = "FileName";
    private static final String DATA = "Data";
    private static final String META_DATA = "MetaData";
    private static final String FOLDER_NAME = "FolderName";

    public static AutomationBoundaryValueAnalysisJsonDTO stringToJson(String jsonString) {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            throw new StringToJsonParseException("Error While Parsing String to JSON");
        }
        JSONObject dataObject = (JSONObject) jsonObject.get(DATA);
        JSONArray jsonMetaDataArray = (JSONArray) jsonObject.get(META_DATA);
        String folderName = (String) jsonObject.get(FOLDER_NAME);
        String jsonFileName = (String) jsonObject.get(FILE_NAME);

        AutomationBoundaryValueAnalysisJsonDTO automationBoundaryValueAnalysisJsonDTO =
                        new AutomationBoundaryValueAnalysisJsonDTO(dataObject, jsonMetaDataArray, folderName,
                                        jsonFileName);

        return automationBoundaryValueAnalysisJsonDTO;
    }

}
