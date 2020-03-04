package com.infogain.automation.dto;

import java.util.Arrays;

import com.infogain.automation.validator.AutomationRandomStringOutOfGivenCharGeneratorDTOValidator;
@AutomationRandomStringOutOfGivenCharGeneratorDTOValidator
public class AutomationRandomStringOutOfGivenCharGeneratorDTO {

    Integer length;
    char[] characters;
    String stringOfChars;

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
     * @return the characters
     */
    public char[] getCharacters() {
        return characters;
    }

    /**
     * @param characters the characters to set
     */
    public void setCharacters(char[] characters) {
        this.characters = characters;
    }

    /**
     * @return the stringOfChars
     */
    public String getStringOfChars() {
        return stringOfChars;
    }

    /**
     * @param stringOfChars the stringOfChars to set
     */
    public void setStringOfChars(String stringOfChars) {
        this.stringOfChars = stringOfChars;
    }

    public AutomationRandomStringOutOfGivenCharGeneratorDTO(Integer length, char[] characters, String stringOfChars) {
        this.length = length;
        this.characters = characters;
        this.stringOfChars = stringOfChars;
    }

    public AutomationRandomStringOutOfGivenCharGeneratorDTO() {
    }

    /**
     * This method 
     * 
     * @return
     * @since Mar 3, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomStringOutOfGivenCharGeneratorDTO [length=" + length + ", characters="
                        + Arrays.toString(characters) + ", stringOfChars=" + stringOfChars + "]";
    }


}
