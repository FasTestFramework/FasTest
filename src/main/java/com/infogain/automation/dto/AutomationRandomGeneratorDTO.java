package com.infogain.automation.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infogain.automation.model.AutomationRegexData;
import com.infogain.automation.validator.AutomationRandomGeneratorDTOValidator;

@JsonIgnoreProperties("automationRegexDatalist")
@AutomationRandomGeneratorDTOValidator
@ApiModel(value = "AutomationRandomGeneratorDTO",
                description = "Request body required to set properties and request the controller of Automation to generate random string from regex.")
public class AutomationRandomGeneratorDTO {

    @ApiModelProperty(value = "instructions to generate random data", required = true, example = "W(claimId: ){1}C(){5}")
    @JsonProperty
    private String instructionsToGenerateRandomData;
    
    @ApiModelProperty(hidden = true)
    private List<AutomationRegexData> automationRegexDatalist;

    public AutomationRandomGeneratorDTO() {
    }

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
