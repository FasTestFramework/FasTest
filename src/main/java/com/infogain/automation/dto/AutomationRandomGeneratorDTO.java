package com.infogain.automation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infogain.automation.model.AutomationRegexData;
import com.infogain.automation.validator.AutomationRandomGeneratorDTOValidator;

@JsonIgnoreProperties("automationRegexDatalist")
@AutomationRandomGeneratorDTOValidator
public class AutomationRandomGeneratorDTO {

    @JsonProperty
    private String instructionsToGenerateRandomData;
    private List<AutomationRegexData> automationRegexDatalist;

    public AutomationRandomGeneratorDTO() {}

    public AutomationRandomGeneratorDTO(String instructionsToGenerateRandomData) {
        this.instructionsToGenerateRandomData = instructionsToGenerateRandomData;
    }

    public String getInstructionsToGenerateRandomData() {
        return instructionsToGenerateRandomData;
    }

    public void setInstructionsToGenerateRandomData(String instructionsToGenerateRandomData) {
        this.instructionsToGenerateRandomData = instructionsToGenerateRandomData;
    }

    public List<AutomationRegexData> getAutomationRegexDatalist() {
        return automationRegexDatalist;
    }

    public void setAutomationRegexDatalist(List<AutomationRegexData> automationRegexDatalist) {
        this.automationRegexDatalist = automationRegexDatalist;
    }

    @Override
    public String toString() {
        return "AutomationRandomGeneratorDTO [instructionsToGenerateRandomData=" + instructionsToGenerateRandomData
                        + ", automationRegexDatalist=" + automationRegexDatalist + "]";
    }

}
