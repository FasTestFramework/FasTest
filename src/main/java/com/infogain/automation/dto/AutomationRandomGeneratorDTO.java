package com.infogain.automation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AutomationRandomGeneratorDTO {

    @JsonProperty
    private String instructionsToGenerateRandomData;


    public AutomationRandomGeneratorDTO() {}

    public AutomationRandomGeneratorDTO(String instructionsToGenerateRandomData) {
        this.instructionsToGenerateRandomData = instructionsToGenerateRandomData;
    }

    public String getinstructionsToGenerateRandomData() {
        return instructionsToGenerateRandomData;
    }

    public void setinstructionsToGenerateRandomData(String instructionsToGenerateRandomData) {
        this.instructionsToGenerateRandomData = instructionsToGenerateRandomData;
    }

    @Override
    public String toString() {
        return "AutomationRandomGeneratorDTO [instructionsToGenerateRandomData=" + instructionsToGenerateRandomData
                        + "]";
    }

}

