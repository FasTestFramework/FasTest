package com.infogain.automation.dto;

import com.infogain.automation.validator.AutomationRandomDoubleGeneratorDTOValidator;

@AutomationRandomDoubleGeneratorDTOValidator
public class AutomationRandomDoubleGenerateDTO {

    private Double min;
    private Double max;
    private Integer precision;

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



    /**
     * @return the precision
     */
    public Integer getPrecision() {
        return precision;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public AutomationRandomDoubleGenerateDTO(Double min, Double max, Integer precision) {
        this.min = min;
        this.max = max;
        this.precision = precision;
    }

    /**
     * This method
     * 
     * @return
     * @since Mar 6, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomDoubleGenerateDTO [min=" + min + ", max=" + max + ", precision=" + precision + "]";
    }


}
