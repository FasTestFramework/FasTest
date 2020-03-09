package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "threadGroup",
description = "Request body for creating a jmeter threadGroup")
public class AutomationPerformanceTestingThreadGroupDto {
    @ApiModelProperty(value = "Name for the jmeter threadGroup.Any string can be given", required = true, example = "AutomationThreadGroup")
    String threadGroupName;
    @ApiModelProperty(value = "No of threads to run", required = true, example = "2")
    int numberOfThreads;
    @ApiModelProperty(value = "Ramp up value", required = true, example = "1")
    int rampUp;

    public String getThreadGroupName() {
        return threadGroupName;
    }

    public void setThreadGroupName(String threadGroupName) {
        this.threadGroupName = threadGroupName;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public int getRampUp() {
        return rampUp;
    }

    public void setRampUp(int rampUp) {
        this.rampUp = rampUp;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestiingThreadGroupDto [");
        if (threadGroupName != null) {
            builder.append("threadGroupName=").append(threadGroupName).append(", ");
        }
        builder.append("numberOfThreads=").append(numberOfThreads).append(", rampUp=").append(rampUp).append("]");
        return builder.toString();
    }

}
