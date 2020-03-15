package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AutomationTestCustomValidationsResponseDTO",
                description = "Response body containing boolean value received after testing custom validations.")
public class AutomationTestCustomValidationsResponseDTO {

    @ApiModelProperty(value = "Custom validations test result", example = "true")
    private boolean output;

    @Override
    public String toString() {
        return "AutomationTestCustomValidationsResponseDTO [output=" + output + "]";
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public AutomationTestCustomValidationsResponseDTO(boolean output) {
        this.output = output;
    }


}
