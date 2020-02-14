package com.infogain.automation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomAttributeDTO {

    @JsonProperty
    private int length;
    @JsonProperty
    private boolean useLetters;
    @JsonProperty
    private boolean useNumbers;

    public RandomAttributeDTO(int length, boolean useLetters, boolean useNumbers) {
        super();
        this.length = length;
        this.useLetters = useLetters;
        this.useNumbers = useNumbers;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isUseLetters() {
        return useLetters;
    }

    public void setUseLetters(boolean useLetters) {
        this.useLetters = useLetters;
    }

    public boolean isUseNumbers() {
        return useNumbers;
    }

    public void setUseNumbers(boolean useNumbers) {
        this.useNumbers = useNumbers;
    }


}

