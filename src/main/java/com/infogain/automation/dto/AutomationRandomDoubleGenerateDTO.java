package com.infogain.automation.dto;

import com.infogain.automation.validator.AutomationRandomDoubleGeneratorDTOValidator;

@AutomationRandomDoubleGeneratorDTOValidator
public class AutomationRandomDoubleGenerateDTO {

    private Double min;
    private Double max;

    public AutomationRandomDoubleGenerateDTO() {
    }

    /**
     * @return the min
     */
    public Double getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public Double getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(Double max) {
        this.max = max;
    }

    public AutomationRandomDoubleGenerateDTO(Double min, Double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "AutomationRandomDoubleGenerateDTO [min=" + min + ", max=" + max + "]";
    }

}
