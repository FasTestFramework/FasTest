package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.validator.AutomationRandomIntegerGeneratorDTOValidator;

@AutomationRandomIntegerGeneratorDTOValidator
@ApiModel(value = "AutomationRandomIntegerGeneratorDTO",
description = "Request body required to set properties and request the controller of Automation to generate random integer as string")
public class AutomationRandomIntegerGeneratorDTO {
    @ApiModelProperty(value = "Start range for random integer generation", required = true, example = "25")
    Integer minValue;
    @ApiModelProperty(value = "End range for random integer generation", required = true, example = "50")
    Integer maxValue;
    @ApiModelProperty(value = "String which contains numbers that must be excluded from the random integer generation as string",
                    required = false, example = "47,43,34")
    String exclusions;

    public AutomationRandomIntegerGeneratorDTO(Integer minValue, Integer maxValue, String exclusions) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.exclusions = exclusions;
    }

    /**
     * This method
     * 
     * @return
     * @since Mar 7, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomIntegerGeneratorDTO [minValue=" + minValue + ", maxValue=" + maxValue + ", exclusions="
                        + exclusions + "]";
    }

    /**
     * @return the minValue
     */
    public Integer getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the maxValue
     */
    public Integer getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the exclusions
     */
    public String getExclusions() {
        return exclusions;
    }

    /**
     * @param exclusions the exclusions to set
     */
    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    public AutomationRandomIntegerGeneratorDTO() {
    }



}
