package com.infogain.automation.dto;

import java.util.Arrays;

import com.infogain.automation.validator.AutomationRandomSpecialCharGeneratorDTOValidator;

@AutomationRandomSpecialCharGeneratorDTOValidator
public class AutomationRandomSpecialCharGeneratorDTO {

    Integer length;
    char[] exclusions;

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

    public AutomationRandomSpecialCharGeneratorDTO(Integer length, char[] exclusions) {
        this.length = length;
        this.exclusions = exclusions;
    }

    public AutomationRandomSpecialCharGeneratorDTO() {
    }

    /**
     * This method
     * 
     * @return
     * @since Mar 3, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomSpecialCharGeneratorDTO [length=" + length + ", exclusions="
                        + Arrays.toString(exclusions) + "]";
    }


}
