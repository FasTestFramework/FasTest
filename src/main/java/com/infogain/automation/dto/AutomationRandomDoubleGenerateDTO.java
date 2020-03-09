package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.validator.AutomationRandomDoubleGeneratorDTOValidator;

@AutomationRandomDoubleGeneratorDTOValidator
@ApiModel(value = "AutomationRandomDoubleGenerateDTO",
                description = "Request body required to set properties and request the controller of Automation to generate random double as string")
public class AutomationRandomDoubleGenerateDTO {
    @ApiModelProperty(value = "Start range for random double generation", required = true, example = "3.6")
    private Double min;
    @ApiModelProperty(value = "End range for random double generation", required = true, example = "12.62")
    private Double max;
    @ApiModelProperty(value = "Precision range for random double generation", required = false, example = "4")
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
