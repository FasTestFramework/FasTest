package com.infogain.automation.dto;

import com.infogain.automation.validator.AutomationRandomIntegerGeneratorDTOValidator;

@AutomationRandomIntegerGeneratorDTOValidator
public class AutomationRandomIntegerGeneratorDTO {

    Integer minValue;
    Integer maxValue;
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
