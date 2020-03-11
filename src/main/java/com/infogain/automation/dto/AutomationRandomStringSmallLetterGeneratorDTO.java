package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.validator.AutomationRandomStringSmallLetterGeneratorDTOValidator;

@AutomationRandomStringSmallLetterGeneratorDTOValidator
@ApiModel(value = "AutomationRandomStringSmallLetterGeneratorDTO",
description = "Request body required to set properties and request the controller of Automation to generate random string of small letters.")
public class AutomationRandomStringSmallLetterGeneratorDTO {

    @ApiModelProperty(value = "length of random generated String", required = false, example = "25")
    Integer length;
    @ApiModelProperty(value = "Start range for random String generation", required = false, example = "b")
    char startCharacter;
    @ApiModelProperty(value = "End range for random String generation", required = false, example = "u")
    char endCharacter;
    @ApiModelProperty(value = "String which contains small letters that must be excluded from the random generation string",
                    required = false, readOnly = true, example = "e,i,h")
    String exclusions;
    @ApiModelProperty(value = "String that contains small letters, from which random string is generated",
                    required = false, example = "a,z,x")
    String inclusions;
    

    public AutomationRandomStringSmallLetterGeneratorDTO(Integer length, char startCharacter, char endCharacter,
                    String exclusions, String inclusions) {
        this.length = length;
        this.startCharacter = startCharacter;
        this.endCharacter = endCharacter;
        this.exclusions = exclusions;
        this.inclusions = inclusions;
    }

    public AutomationRandomStringSmallLetterGeneratorDTO() {
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
     * @return the startCharacter
     */
    public char getStartCharacter() {
        return startCharacter;
    }

    /**
     * @param startCharacter the startCharacter to set
     */
    public void setStartCharacter(char startCharacter) {
        this.startCharacter = startCharacter;
    }

    /**
     * @return the endCharacter
     */
    public char getEndCharacter() {
        return endCharacter;
    }

    /**
     * @param endCharacter the endCharacter to set
     */
    public void setEndCharacter(char endCharacter) {
        this.endCharacter = endCharacter;
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

    /**
     * This method 
     * 
     * @return
     * @since Mar 11, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomStringSmallLetterGeneratorDTO [length=" + length + ", startCharacter=" + startCharacter
                        + ", endCharacter=" + endCharacter + ", exclusions=" + exclusions + ", inclusions=" + inclusions
                        + "]";
    }

 


}
