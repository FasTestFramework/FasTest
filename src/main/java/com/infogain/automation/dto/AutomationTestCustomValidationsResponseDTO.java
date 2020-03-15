package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AutomationTestCustomValidationsResponseDTO",
                description = "Response body containing boolean value received after testing custom validations.")
public class AutomationTestCustomValidationsResponseDTO {

    @ApiModelProperty(value = "Custom validations test result status", example = "true")
    private boolean validationStatus;
    @ApiModelProperty(value = "Custom validations test result comments", example = "")
    private String validationComments;

    public AutomationTestCustomValidationsResponseDTO(boolean validationStatus, String validationComments) {
        this.validationStatus = validationStatus;
        this.validationComments = validationComments;
    }

    public boolean isValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(boolean validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getValidationComments() {
        return validationComments;
    }

    public void setValidationComments(String validationComments) {
        this.validationComments = validationComments;
    }

    @Override
    public String toString() {
        return "AutomationTestCustomValidationsResponseDTO [validationStatus=" + validationStatus
                        + ", validationComments=" + validationComments + "]";
    }

}
