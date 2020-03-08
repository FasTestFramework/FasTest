package com.infogain.automation.dto;

import com.infogain.automation.validator.AutomationRandomSpecialCharGeneratorDTOValidator;

@AutomationRandomSpecialCharGeneratorDTOValidator
public class AutomationRandomSpecialCharGeneratorDTO {

    Integer length;
    String exclusions;
    String inclusions;

    /**
     * This method
     * 
     * @return
     * @since Mar 6, 2020
     */
    @Override
    public String toString() {
        return "AutomationRandomSpecialCharGeneratorDTO [length=" + length + ", exclusions=" + exclusions
                        + ", inclusions=" + inclusions + "]";
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



    public AutomationRandomSpecialCharGeneratorDTO(Integer length, String exclusions, String inclusions) {
        this.length = length;
        this.exclusions = exclusions;
        this.inclusions = inclusions;
    }



    public AutomationRandomSpecialCharGeneratorDTO() {
    }


}
