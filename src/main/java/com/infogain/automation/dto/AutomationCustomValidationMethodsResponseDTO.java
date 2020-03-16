package com.infogain.automation.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AutomationCustomValidationMethodsResponseDTO",
                description = "Response body containing list of all valid methods for custom validations.")
public class AutomationCustomValidationMethodsResponseDTO {

    @ApiModelProperty(value = "list of all valid methods for custom validations",
                    example = "[\"contains(String)\", \"isEqualTo(Object)\", \"isNotEqualTo(Object)\", \"doesNotContain(String)isBlank()\", \"isEmpty()\", \"isNull()\", \"isNullOrEmpty()\", \"isSubstringOf(String)\", \"matches(String)\", \"isIn(String[])\", \"isNotIn(String[])\", \"isNotNull()\", \"isNotBlank()\", \"isNotEmpty()\"]")
    private List<String> output;

    @Override
    public String toString() {
        return "AutomationTestCustomValidationsResponseDTO [output=" + output + "]";
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public AutomationCustomValidationMethodsResponseDTO(List<String> output) {
        this.output = output;
    }

}
