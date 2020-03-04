package com.infogain.automation.dto;

import java.util.Arrays;

import com.infogain.automation.validator.AutomationRandomStringGeneratorDTOValidator;

@AutomationRandomStringGeneratorDTOValidator
public class AutomationRandomStringGeneratorDTO {

    private Integer length;
    private char[] exclusions;
    private char[] inclusions;

   

    public AutomationRandomStringGeneratorDTO(Integer length, char[] exclusions, char[] inclusions) {
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
    public char[] getExclusions() {
        return exclusions;
    }

    /**
     * @param exclusions the exclusions to set
     */
    public void setExclusions(char[] exclusions) {
        this.exclusions = exclusions;
    }

    public AutomationRandomStringGeneratorDTO() {
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

    /**
     * This method 
     * 
     * @return
     * @since Mar 4, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomStringGeneratorDTO [length=" + length + ", exclusions=" + Arrays.toString(exclusions)
                        + ", inclusions=" + Arrays.toString(inclusions) + "]";
    }




}
