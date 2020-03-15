package com.infogain.automation.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AutomationKeyPathsResponseDTO",
                description = "Response body containing list of all key paths for provided json data.")
public class AutomationKeyPathsResponseDTO {

    @ApiModelProperty(value = "List of all key paths for provided json data.",
                    example = "[\"output.claimId\", \"output.receiptElements\", \"output.receiptElements[x].barcode.data\", \"output.receiptElements[0]\", \"output.receiptElements[0].image\", \"output.receiptElements\"]")
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

    public AutomationKeyPathsResponseDTO(List<String> output) {
        this.output = output;
    }

}
