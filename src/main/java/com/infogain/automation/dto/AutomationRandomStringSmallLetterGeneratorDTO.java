package com.infogain.automation.dto;

import java.util.Arrays;

import com.infogain.automation.validator.AutomationRandomStringSmallLetterGeneratorDTOValidator;
@AutomationRandomStringSmallLetterGeneratorDTOValidator
public class AutomationRandomStringSmallLetterGeneratorDTO {

    Integer length;
    char startCharacter;
    char endCharacter;
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
    public char[] getExclusions() {
        return exclusions;
    }

    /**
     * @param exclusions the exclusions to set
     */
    public void setExclusions(char[] exclusions) {
        this.exclusions = exclusions;
    }

    public AutomationRandomStringSmallLetterGeneratorDTO(Integer length, char startCharacter, char endCharacter,
                    char[] exclusions) {
        this.length = length;
        this.startCharacter = startCharacter;
        this.endCharacter = endCharacter;
        this.exclusions = exclusions;
    }

    public AutomationRandomStringSmallLetterGeneratorDTO() {
    }

    /**
     * This method 
     * 
     * @return
     * @since Mar 3, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomStringSmallLetterGeneratorDTO [length=" + length + ", startCharacter=" + startCharacter
                        + ", endCharacter=" + endCharacter + ", exclusions=" + Arrays.toString(exclusions) + "]";
    }

}
