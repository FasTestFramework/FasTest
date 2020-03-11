package com.infogain.automation.model;

import java.util.List;

import org.json.simple.JSONAware;

import com.infogain.automation.dto.AutomationBVAMetadataDTO;

public class AutomationBoundaryValueAnalysisModel {

    private JSONAware data;
    private List<AutomationBVAMetadataDTO> metaData;
    private String folderName;
    private String fileName;

    public AutomationBoundaryValueAnalysisModel(JSONAware data, List<AutomationBVAMetadataDTO> metaData,
                    String folderName, String fileName) {
        this.data = data;
        this.metaData = metaData;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public AutomationBoundaryValueAnalysisModel() {}

    public JSONAware getData() {
        return data;
    }

    public void setData(JSONAware data) {
        this.data = data;
    }

    public List<AutomationBVAMetadataDTO> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<AutomationBVAMetadataDTO> metaData) {
        this.metaData = metaData;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "AutomationBoundaryValueAnalysisModel [data=" + data + ", metaData=" + metaData + ", folderName="
                        + folderName + ", fileName=" + fileName + "]";
    }



}
