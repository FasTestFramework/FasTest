package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.validator.AutomationRandomStringWithEverythingGeneratorDTOValidator;

@AutomationRandomStringWithEverythingGeneratorDTOValidator
@ApiModel(value = "AutomationRandomStringEverythingDTO",
description = "Request body required to set properties and request the controller of Automation to generate random string")
public class AutomationRandomStringEverythingDTO {


    @ApiModelProperty(value = "length of random generated String", required = false, example = "25")
     private Integer length;
     @ApiModelProperty(value = "String which contains numbers that must be excluded from the random generation string",
                     required = false, readOnly = true, example = "5,!,A,f,7")
     private String exclusions;
     @ApiModelProperty(value = "String that contains numbers, from which random string of numbers is generated",
                     required = false, example = "@,n,M,8,s")
     private String inclusions;

    public AutomationRandomStringEverythingDTO() {
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

    /**
     * @return the inclusions
     */
    public String getInclusions() {
        return inclusions;
    }

    /**
     * @param inclusions the inclusions to set
     */
    public void setInclusions(String inclusions) {
        this.inclusions = inclusions;
    }

    public AutomationRandomStringEverythingDTO(Integer length, String exclusions, String inclusions) {
        this.length = length;
        this.exclusions = exclusions;
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
        return "AutomationRandomStringEverythingDTO [length=" + length + ", exclusions=" + exclusions + ", inclusions="
                        + inclusions + "]";
    }



}
