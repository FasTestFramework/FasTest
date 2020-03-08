package com.infogain.automation.dto;

import com.infogain.automation.validator.AutomationRandomStringCapitalLetterGeneratorDTOValidator;

@AutomationRandomStringCapitalLetterGeneratorDTOValidator
public class AutomationRandomStringCapitalLetterGeneratorDTO {

    Integer length;
    char startCharacter;
    char endCharacter;
    String exclusions;
    String inclusions;
    char constantCharacter;

    public AutomationRandomStringCapitalLetterGeneratorDTO(Integer length, char startCharacter, char endCharacter,
                    String exclusions, String inclusions, char constantCharacter) {
        this.length = length;
        this.startCharacter = startCharacter;
        this.endCharacter = endCharacter;
        this.exclusions = exclusions;
        this.inclusions = inclusions;
        this.constantCharacter = constantCharacter;
    }

    public AutomationRandomStringCapitalLetterGeneratorDTO() {
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
     * @return the constantCharacter
     */
    public char getConstantCharacter() {
        return constantCharacter;
    }

    /**
     * @param constantCharacter the constantCharacter to set
     */
    public void setConstantCharacter(char constantCharacter) {
        this.constantCharacter = constantCharacter;
    }

    /**
     * This method
     * 
     * @return
     * @since Mar 6, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomStringCapitalLetterGeneratorDTO [length=" + length + ", startCharacter="
                        + startCharacter + ", endCharacter=" + endCharacter + ", exclusions=" + exclusions
                        + ", inclusions=" + inclusions + ", constantCharacter=" + constantCharacter + "]";
    }
}
