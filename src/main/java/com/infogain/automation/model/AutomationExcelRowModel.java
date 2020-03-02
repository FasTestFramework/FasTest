package com.infogain.automation.model;

public class AutomationExcelRowModel {

    String testCaseDescription;
    String requestUrl;
    String requestType;
    String headerJson;
    String inputJson;
    String expectedOutput;
    Integer expectedHttpStatus;
    String params;


    /**
     * @return the testCaseDescription
     */
    public String getTestCaseDescription() {
        return testCaseDescription;
    }

    /**
     * @param testCaseDescription the testCaseDescription to set
     */
    public void setTestCaseDescription(String testCaseDescription) {
        this.testCaseDescription = testCaseDescription;
    }

    /**
     * @return the requestUrl
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * @param requestUrl the requestUrl to set
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * @return the requestType
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * @return the headerJson
     */
    public String getHeaderJson() {
        return headerJson;
    }

    /**
     * @param headerJson the headerJson to set
     */
    public void setHeaderJson(String headerJson) {
        this.headerJson = headerJson;
    }

    /**
     * @return the inputJson
     */
    public String getInputJson() {
        return inputJson;
    }

    /**
     * @param inputJson the inputJson to set
     */
    public void setInputJson(String inputJson) {
        this.inputJson = inputJson;
    }

    /**
     * @return the expectedOutput
     */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    /**
     * @param expectedOutput the expectedOutput to set
     */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    /**
     * @return the expectedHttpStatus
     */
    public Integer getExpectedHttpStatus() {
        return expectedHttpStatus;
    }

    /**
     * @param expectedHttpStatus the expectedHttpStatus to set
     */
    public void setExpectedHttpStatus(Integer expectedHttpStatus) {
        this.expectedHttpStatus = expectedHttpStatus;
    }

    /**
     * @return the params
     */
    public String getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(String params) {
        this.params = params;
    }

    public AutomationExcelRowModel(String testCaseDescription, String requestUrl, String requestType, String headerJson,
                    String inputJson, String expectedOutput, Integer expectedHttpStatus, String params) {
        this.testCaseDescription = testCaseDescription;
        this.requestUrl = requestUrl;
        this.requestType = requestType;
        this.headerJson = headerJson;
        this.inputJson = inputJson;
        this.expectedOutput = expectedOutput;
        this.expectedHttpStatus = expectedHttpStatus;
        this.params = params;
    }

    /**
     * This method
     * 
     * @return
     * @since Mar 2, 2020
     */
    @Override
    public String toString() {
        return "AutomationExcelRowModel [testCaseDescription=" + testCaseDescription + ", requestUrl=" + requestUrl
                        + ", requestType=" + requestType + ", headerJson=" + headerJson + ", inputJson=" + inputJson
                        + ", expectedOutput=" + expectedOutput + ", expectedHttpStatus=" + expectedHttpStatus
                        + ", params=" + params + "]";
    }

}
