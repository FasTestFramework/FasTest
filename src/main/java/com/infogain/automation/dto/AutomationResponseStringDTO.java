package com.infogain.automation.dto;

public class AutomationResponseStringDTO {
    private String output;

    public AutomationResponseStringDTO() {}

    public AutomationResponseStringDTO(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "AutomationResponseStringDTO [output=" + output + "]";
    }
}
