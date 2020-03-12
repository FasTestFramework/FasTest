package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "durationAssertion",
description = "Request body for creating a jmeter duration assertion")
public class AutomationPerformanceTestingDurationAssertion {
    @ApiModelProperty(value = "Name for the durationAssertion.Any string can be given", required = true, example = "AutomationDurationAssertion")
    String name;
    @ApiModelProperty(value = "allowed duration for a http request", required = true, example = "3000")
    Long allowedDuration;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getAllowedDuration() {
        return allowedDuration;
    }
    public void setAllowedDuration(Long allowedDuration) {
        this.allowedDuration = allowedDuration;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestingDurationAssertion [");
        if (name != null) {
            builder.append("name=").append(name).append(", ");
        }
        if (allowedDuration != null) {
            builder.append("allowedDuration=").append(allowedDuration);
        }
        builder.append("]");
        return builder.toString();
    }
    
}
