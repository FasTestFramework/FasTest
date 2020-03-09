package com.infogain.automation.dto;

import io.swagger.annotations.ApiModelProperty;

public class AutomationPerformanceTestingTest {
    AutomationPerformanceTestingCsvConfigDto csvConfig;
    AutomationPerformanceTestingHttpHandlerDto httpHandler;
    AutomationPerformanceTestingTestPlanDto testPlan;
    AutomationPerformanceTestingThreadGroupDto threadGroup;
    @ApiModelProperty(value = "whether detailed response is needed", required = true, example = "false",position = 5)
    private Boolean deatiledResponse;
    private AutomationPerformanceTestingAssertions assertions;

    public AutomationPerformanceTestingCsvConfigDto getCsvConfig() {
        return csvConfig;
    }

    public void setCsvConfig(AutomationPerformanceTestingCsvConfigDto csvConfig) {
        this.csvConfig = csvConfig;
    }

    public AutomationPerformanceTestingHttpHandlerDto getHttpHandler() {
        return httpHandler;
    }

    public void setHttpHandler(AutomationPerformanceTestingHttpHandlerDto httpHandler) {
        this.httpHandler = httpHandler;
    }

    public AutomationPerformanceTestingTestPlanDto getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(AutomationPerformanceTestingTestPlanDto testPlan) {
        this.testPlan = testPlan;
    }

    public AutomationPerformanceTestingThreadGroupDto getThreadGroup() {
        return threadGroup;
    }

    public void setThreadGroup(AutomationPerformanceTestingThreadGroupDto threadGroup) {
        this.threadGroup = threadGroup;
    }

    public Boolean getDeatiledResponse() {
        return deatiledResponse;
    }

    public void setDeatiledResponse(Boolean deatiledResponse) {
        this.deatiledResponse = deatiledResponse;
    }

    public AutomationPerformanceTestingAssertions getAssertions() {
        return assertions;
    }

    public void setAssertions(AutomationPerformanceTestingAssertions assertions) {
        this.assertions = assertions;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestingTest [");
        if (csvConfig != null) {
            builder.append("csvConfig=").append(csvConfig).append(", ");
        }
        if (httpHandler != null) {
            builder.append("httpHandler=").append(httpHandler).append(", ");
        }
        if (testPlan != null) {
            builder.append("testPlan=").append(testPlan).append(", ");
        }
        if (threadGroup != null) {
            builder.append("threadGroup=").append(threadGroup).append(", ");
        }
        if (deatiledResponse != null) {
            builder.append("deatiledResponse=").append(deatiledResponse).append(", ");
        }
        if (assertions != null) {
            builder.append("assertions=").append(assertions);
        }
        builder.append("]");
        return builder.toString();
    }

    
  
}
