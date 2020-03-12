package com.infogain.automation.dto;

import com.infogain.automation.validator.AutomationPerformanceTestingDTOValidator;

@AutomationPerformanceTestingDTOValidator
public class AutomationPerformanceTestingTest {
    AutomationPerformanceTestingCsvConfigDto csvConfig;
    AutomationPerformanceTestingHttpHandlerDto httpHandler;
    AutomationPerformanceTestingTestPlanDto testPlan;
    AutomationPerformanceTestingThreadGroupDto threadGroup;
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
        if (assertions != null) {
            builder.append("assertions=").append(assertions);
        }
        builder.append("]");
        return builder.toString();
    }

    
  
}
