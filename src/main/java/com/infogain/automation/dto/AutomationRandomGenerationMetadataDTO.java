package com.infogain.automation.dto;

import java.util.List;

import com.infogain.automation.constants.InputType;
import com.infogain.automation.constants.JavaDataType;

public class AutomationRandomGenerationMetadataDTO {
    private InputType type;
    private JavaDataType dataType;
    private String url;
    private List<List<String>> requiredSet;
    private List<AutomationKeyMetadataDTO> keys;

    public InputType getType() {
        return type;
    }

    public void setType(InputType type) {
        this.type = type;
    }

    public JavaDataType getDataType() {
        return dataType;
    }

    public void setDataType(JavaDataType dataType) {
        this.dataType = dataType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<List<String>> getRequiredSet() {
        return requiredSet;
    }

    public void setRequiredSet(List<List<String>> requiredSet) {
        this.requiredSet = requiredSet;
    }

    public List<AutomationKeyMetadataDTO> getKeys() {
        return keys;
    }

    public void setKeys(List<AutomationKeyMetadataDTO> keys) {
        this.keys = keys;
    }

    public AutomationRandomGenerationMetadataDTO(InputType type, JavaDataType dataType, String url,
                    List<List<String>> requiredSet, List<AutomationKeyMetadataDTO> keys) {
        this.type = type;
        this.dataType = dataType;
        this.url = url;
        this.requiredSet = requiredSet;
        this.keys = keys;
    }

    @Override
    public String toString() {
        return "AutomationRandomGenerationMetadataDTO [type=" + type + ", dataType=" + dataType + ", url=" + url
                        + ", requiredSet=" + requiredSet + ", keys=" + keys + "]";
    }

}
