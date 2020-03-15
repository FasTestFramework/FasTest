package com.infogain.automation.model;

public class AutomationExcelRowModel {
    private String testCaseDescription;
    private String requestUrl;
    private String requestType;
    private String headerJson;
    private String inputJson;
    private String expectedOutput;
    private Integer expectedHttpStatus;
    private String params;
    private String skipTest;
    private String customValidations;

    public AutomationExcelRowModel(String testCaseDescription, String requestUrl, String requestType, String headerJson,
                    String inputJson, String expectedOutput, Integer expectedHttpStatus, String params, String skipTest,
                    String customValidations) {
        this.testCaseDescription = testCaseDescription;
        this.requestUrl = requestUrl;
        this.requestType = requestType;
        this.headerJson = headerJson;
        this.inputJson = inputJson;
        this.expectedOutput = expectedOutput;
        this.expectedHttpStatus = expectedHttpStatus;
        this.params = params;
        this.skipTest = skipTest;
        this.customValidations = customValidations;
    }

    public String getTestCaseDescription() {
        return testCaseDescription;
    }

    public void setTestCaseDescription(String testCaseDescription) {
        this.testCaseDescription = testCaseDescription;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getHeaderJson() {
        return headerJson;
    }

    public void setHeaderJson(String headerJson) {
        this.headerJson = headerJson;
    }

    public String getInputJson() {
        return inputJson;
    }

    public void setInputJson(String inputJson) {
        this.inputJson = inputJson;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public Integer getExpectedHttpStatus() {
        return expectedHttpStatus;
    }

    public void setExpectedHttpStatus(Integer expectedHttpStatus) {
        this.expectedHttpStatus = expectedHttpStatus;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSkipTest() {
        return skipTest;
    }

    public void setSkipTest(String skipTest) {
        this.skipTest = skipTest;
    }

    public String getCustomValidations() {
        return customValidations;
    }

    public void setCustomValidations(String customValidations) {
        this.customValidations = customValidations;
    }

    @Override
    public String toString() {
        return "AutomationExcelRowModel [testCaseDescription=" + testCaseDescription + ", requestUrl=" + requestUrl
                        + ", requestType=" + requestType + ", headerJson=" + headerJson + ", inputJson=" + inputJson
                        + ", expectedOutput=" + expectedOutput + ", expectedHttpStatus=" + expectedHttpStatus
                        + ", params=" + params + ", skipTest=" + skipTest + ", customValidations=" + customValidations
                        + "]";
    }

}
