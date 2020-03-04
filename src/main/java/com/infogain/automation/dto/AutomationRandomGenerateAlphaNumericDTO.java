package com.infogain.automation.dto;

import java.util.Arrays;

import com.infogain.automation.validator.AutomationRandomAplhaNumericGeneratorDTOValidator;

@AutomationRandomAplhaNumericGeneratorDTOValidator
public class AutomationRandomGenerateAlphaNumericDTO {
 
    private Integer length;
    private char[] exclusions;

    public AutomationRandomGenerateAlphaNumericDTO() {
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

    public AutomationRandomGenerateAlphaNumericDTO(Integer length, char[] exclusions) {
        this.length = length;
        this.exclusions = exclusions;
    }

    @Override
    public String toString() {
        return "AutomationRandomGenerateAlphaNumericDTO [length=" + length + ", exclusions="
                        + Arrays.toString(exclusions) + "]";
    }

}
