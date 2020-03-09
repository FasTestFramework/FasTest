package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "testPlan",
description = "Request body for creating a jmeter testPlan")
public class AutomationPerformanceTestingTestPlanDto {
    @ApiModelProperty(value = "Name for the test plan.Any string can be given", required = true, example = "AutomationTestPlan")
    String name;
  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "AutomationPerformanceTestingTestPlanDto [name=" + name + "]";
	}






}
