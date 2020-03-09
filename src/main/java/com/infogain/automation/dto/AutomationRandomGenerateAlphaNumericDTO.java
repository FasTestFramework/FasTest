package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

import com.infogain.automation.validator.AutomationRandomAplhaNumericGeneratorDTOValidator;

@AutomationRandomAplhaNumericGeneratorDTOValidator
@ApiModel(value = "AutomationRandomGenerateAlphaNumericDTO",
                description = "Request body required to set properties and request the controller of Automation to generate random string of numbers and alphabets .")
public class AutomationRandomGenerateAlphaNumericDTO {


    @ApiModelProperty(value = "length of random generated String", required = false, example = "25")
    private Integer length;
    @ApiModelProperty(
                    value = "String which contains numbers and alphabets that must be excluded from the random generation string",
                    required = false, readOnly = true, example = "5,a,A")
    private String exclusions;
    @ApiModelProperty(
                    value = "String that contains numbers and alphabets, from which random string of numbers is generated",
                    required = false, example = "1,v,N")
    private String inclusions;

    public AutomationRandomGenerateAlphaNumericDTO() {
    }

    public AutomationRandomGenerateAlphaNumericDTO(Integer length, String exclusions, String inclusions) {
        this.length = length;
        this.exclusions = exclusions;
        this.inclusions = inclusions;
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

    public String getInclusions() {
        return inclusions;
    }

    public void setInclusions(String inclusions) {
        this.inclusions = inclusions;
    }

    /**
     * This method
     * 
     * @return
     * @since Mar 6, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomGenerateAlphaNumericDTO [length=" + length + ", exclusions=" + exclusions + "]";
    }


}
