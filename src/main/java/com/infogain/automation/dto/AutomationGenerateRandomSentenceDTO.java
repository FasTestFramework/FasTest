package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.validator.AutomationRandomSentenceGeneratorDTOValidator;

@AutomationRandomSentenceGeneratorDTOValidator
@ApiModel(value = "AutomationGenerateRandomSentenceDTO",
                description = "Request body required to set properties and request the controller of Automation to generate random sentence")
public class AutomationGenerateRandomSentenceDTO {
    @ApiModelProperty(value = "length of random generated String", required = false, example = "25")
    private Integer length;

    public AutomationGenerateRandomSentenceDTO() {
    }

    /**
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    public AutomationGenerateRandomSentenceDTO(Integer length) {
        this.length = length;
    }


    @Override
    public String toString() {
        return "AutomationGenerateRandomSentenceDTO [length=" + length + "]";
    }

}
