package com.infogain.automation.dto;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AutomationBoundaryValueAnalysisJsonDTO {

    JSONObject dataObject;
    JSONArray jsonMetaDataArray;
    String folderName;
    String jsonFileName;

    public AutomationBoundaryValueAnalysisJsonDTO() {

    }

    public AutomationBoundaryValueAnalysisJsonDTO(JSONObject dataObject, JSONArray jsonMetaDataArray, String folderName,
                    String jsonFileName) {
        super();
        this.dataObject = dataObject;
        this.jsonMetaDataArray = jsonMetaDataArray;
        this.folderName = folderName;
        this.jsonFileName = jsonFileName;
    }

    public JSONObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(JSONObject dataObject) {
        this.dataObject = dataObject;
    }

    public JSONArray getJsonMetaDataArray() {
        return jsonMetaDataArray;
    }

    public void setJsonMetaDataArray(JSONArray jsonMetaDataArray) {
        this.jsonMetaDataArray = jsonMetaDataArray;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getJsonFileName() {
        return jsonFileName;
    }

    public void setJsonFileName(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    @Override
    public String toString() {
        return "BoundaryValueAnalysisJsonDTO [dataObject=" + dataObject + ", jsonMetaDataArray=" + jsonMetaDataArray
                        + ", folderName=" + folderName + ", jsonFileName=" + jsonFileName + "]";
    }
}
