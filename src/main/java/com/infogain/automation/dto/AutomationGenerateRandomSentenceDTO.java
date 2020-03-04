package com.infogain.automation.dto;

import com.infogain.automation.validator.AutomationRandomSentenceGeneratorDTOValidator;

@AutomationRandomSentenceGeneratorDTOValidator
public class AutomationGenerateRandomSentenceDTO {

	private Integer length;

	public AutomationGenerateRandomSentenceDTO() {
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

    public AutomationGenerateRandomSentenceDTO(Integer length) {
        this.length = length;
    }


    @Override
	public String toString() {
		return "AutomationGenerateRandomSentenceDTO [length=" + length + "]";
	}

}
