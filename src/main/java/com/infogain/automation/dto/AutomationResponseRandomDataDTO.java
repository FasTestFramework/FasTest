package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AutomationResponseRandomDataDTO", description = "response body containing random generated data")
public class AutomationResponseRandomDataDTO {
    @ApiModelProperty(value = "Random Generated data", example = "Random data")
    private String output;

    public AutomationResponseRandomDataDTO() {
    }

    public AutomationResponseRandomDataDTO(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "AutomationResponseRandomDataDTO [output=" + output + "]";
    }
}
