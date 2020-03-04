package com.infogain.automation.dto;

import java.util.Arrays;

import com.infogain.automation.validator.AutomationRandomStringWithEverythingGeneratorDTOValidator;

@AutomationRandomStringWithEverythingGeneratorDTOValidator
public class AutomationRandomStringEverythingDTO {
    private Integer length;
    private char[] exclusions;
    private char[] inclusions;

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
    public char[] getExclusions() {
        return exclusions;
    }


    /**
     * @param exclusions the exclusions to set
     */
    public void setExclusions(char[] exclusions) {
        this.exclusions = exclusions;
    }


    /**
     * @return the inclusions
     */
    public char[] getInclusions() {
        return inclusions;
    }


    /**
     * @param inclusions the inclusions to set
     */
    public void setInclusions(char[] inclusions) {
        this.inclusions = inclusions;
    }


    public AutomationRandomStringEverythingDTO(Integer length, char[] exclusions, char[] inclusions) {
        this.length = length;
        this.exclusions = exclusions;
        this.inclusions = inclusions;
    }


    /**
     * This method
     * 
     * @return
     * @since Mar 4, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomStringEverythingDTO [length=" + length + ", exclusions=" + Arrays.toString(exclusions)
                        + ", inclusions=" + Arrays.toString(inclusions) + "]";
    }



}
