package com.infogain.automation.dto;

import java.util.Arrays;

import com.infogain.automation.validator.AutomationRandomIntegerGeneratorDTOValidator;

@AutomationRandomIntegerGeneratorDTOValidator
public class AutomationRandomIntegerGeneratorDTO {

    Integer minValue;
    Integer maxValue;
    int[] exclude;

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
     * @return the exclude
     */
    public int[] getExclude() {
        return exclude;
    }

    /**
     * @param exclude the exclude to set
     */
    public void setExclude(int[] exclude) {
        this.exclude = exclude;
    }

    public AutomationRandomIntegerGeneratorDTO(Integer minValue, Integer maxValue, int[] exclude) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.exclude = exclude;
    }

    public AutomationRandomIntegerGeneratorDTO() {
    }

    /**
     * This method
     * 
     * @return
     * @since Mar 3, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomIntegerGeneratorDTO [minValue=" + minValue + ", maxValue=" + maxValue + ", exclude="
                        + Arrays.toString(exclude) + "]";
    }

}
