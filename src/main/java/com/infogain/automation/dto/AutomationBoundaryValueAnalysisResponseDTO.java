package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AutomationBoundaryValueAnalysisResponseDTO",
                description = "Response body received after boundary value JSONs creation.")
public class AutomationBoundaryValueAnalysisResponseDTO {

    @ApiModelProperty(value = "No. of JSONs created", example = "6 Files Created at D:/fasTest/input/json")
    private String output;

    @Override
    public String toString() {
        return "AutomationBoundaryValueAnalysisResponseDTO [output=" + output + "]";
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public AutomationBoundaryValueAnalysisResponseDTO(String output) {
        this.output = output;
    }

}
