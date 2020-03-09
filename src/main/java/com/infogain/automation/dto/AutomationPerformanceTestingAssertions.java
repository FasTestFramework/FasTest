package com.infogain.automation.dto;

public class AutomationPerformanceTestingAssertions {
    AutomationPerformanceTestingDurationAssertion durationAssertion;
    public AutomationPerformanceTestingDurationAssertion getDurationAssertion() {
        return durationAssertion;
    }
    public void setDurationAssertion(AutomationPerformanceTestingDurationAssertion durationAssertion) {
        this.durationAssertion = durationAssertion;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestingAssertions [");
        if (durationAssertion != null) {
            builder.append("durationAssertion=").append(durationAssertion);
        }
        builder.append("]");
        return builder.toString();
    }
   
   
    
}
