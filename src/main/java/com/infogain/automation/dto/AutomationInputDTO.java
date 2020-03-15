package com.infogain.automation.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;

import io.restassured.http.Headers;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is Peripheral Input DTO which contains all variables for reading and writing column of Excel
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
public class AutomationInputDTO {

    private String serialNo;
    private String testCaseDescription;
    private String headerJson;
    private String inputJson;
    private String inputParam;
    private String expectedOutput;
    private Integer expectedHttpStatus;
    private JSONObject testCaseInputJson;
    private String actualOutput;
    private String failureComments;
    private Integer actualHttpStatus;
    private String testCaseResult;
    private LocalDateTime executionDateTime;
    private Headers headers;
    private String requestURL;
    private HttpMethod requestType;
    private Map<String, List<String>> keyValidation;
    private Double runtime;

    /**
     * @return the inputParam
     */
    public String getInputParam() {
        return inputParam;
    }

    /**
     * @param inputParam the inputParam to set
     */
    public void setInputParam(String inputParam) {
        this.inputParam = inputParam;
    }

    /**
     * @return the keyValidation
     */
    public Map<String, List<String>> getKeyValidation() {
        return keyValidation;
    }

    /**
     * @param keyValidation the keyValidation to set
     */
    public void setKeyValidation(Map<String, List<String>> keyValidation) {
        this.keyValidation = keyValidation;
    }

    /**
     * 
     * @return headers
     * @since Nov 27, 2019
     */
    public Headers getHeaders() {
        return headers;
    }

    /**
     * 
     * @param headers the headers to set
     * @since Nov 27, 2019
     */
    public void setHeaders(Headers headers) {
        this.headers = headers;
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
     * @return the testCaseInputJson
     */
    public JSONObject getTestCaseInputJson() {
        return testCaseInputJson;
    }

    /**
     * @param testCaseInputJson the testCaseInputJson to set
     */
    public void setTestCaseInputJson(JSONObject testCaseInputJson) {
        this.testCaseInputJson = testCaseInputJson;
    }

    /**
     * @return the requestURL
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     * @param requestURL the requestURL to set
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     * @return the requestType
     */
    public HttpMethod getRequestType() {
        return requestType;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(HttpMethod requestType) {
        this.requestType = requestType;
    }


    /**
     * @return the failureComments
     */
    public String getFailureComments() {
        return failureComments;
    }

    /**
     * @param failureComments the failureComments to set
     */
    public void setFailureComments(String failureComments) {
        this.failureComments = failureComments;
    }

    /**
     * @return the runtime
     */
    public Double getRuntime() {
        return runtime;
    }

    /**
     * @param runtime the runtime to set
     */
    public void setRuntime(Double runtime) {
        this.runtime = runtime;
    }

    public AutomationInputDTO() {

    }


    public AutomationInputDTO(String serialNo, String testCaseDescription, String headerJson, String inputJson,
                    String inputParam, String expectedOutput, Integer expectedHttpStatus, String requestURL,
                    HttpMethod requestType, Map<String, List<String>> keyValidation) {
        this.serialNo = serialNo;
        this.testCaseDescription = testCaseDescription;
        this.headerJson = headerJson;
        this.inputJson = inputJson;
        this.inputParam = inputParam;
        this.expectedOutput = expectedOutput;
        this.expectedHttpStatus = expectedHttpStatus;
        this.requestURL = requestURL;
        this.requestType = requestType;
        this.keyValidation = keyValidation;

    }

    /**
     * This method
     * 
     * @return
     * @since Mar 2, 2020
     */
    @Override
    public String toString() {
        return "AutomationInputDTO [serialNo=" + serialNo + ", testCaseDescription=" + testCaseDescription
                        + ", headerJson=" + headerJson + ", inputJson=" + inputJson + ", inputParam=" + inputParam
                        + ", expectedOutput=" + expectedOutput + ", expectedHttpStatus=" + expectedHttpStatus
                        + ", testCaseInputJson=" + testCaseInputJson + ", actualOutput=" + actualOutput
                        + ", failureComments=" + failureComments + ", actualHttpStatus=" + actualHttpStatus
                        + ", testCaseResult=" + testCaseResult + ", executionDateTime=" + executionDateTime
                        + ", headers=" + headers + ", requestURL=" + requestURL + ", requestType=" + requestType
                        + ", keyValidation=" + keyValidation + ", runtime=" + runtime + "]";
    }

    /**
     * This method
     * 
     * @return
     * @since Jan 22, 2020
     */
    @Override
    public int hashCode() {
        return Objects.hash(actualHttpStatus, actualOutput, executionDateTime, expectedHttpStatus, expectedOutput,
                        failureComments, headerJson, headers, inputJson, inputParam, keyValidation, requestType,
                        requestURL, runtime, serialNo, testCaseDescription, testCaseInputJson, testCaseResult);
    }

    /**
     * This method
     * 
     * @param obj
     * @return
     * @since Jan 22, 2020
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AutomationInputDTO)) {
            return false;
        }
        AutomationInputDTO other = (AutomationInputDTO) obj;
        return Objects.equals(actualHttpStatus, other.actualHttpStatus)
                        && Objects.equals(actualOutput, other.actualOutput)
                        && Objects.equals(executionDateTime, other.executionDateTime)
                        && Objects.equals(expectedHttpStatus, other.expectedHttpStatus)
                        && Objects.equals(expectedOutput, other.expectedOutput)
                        && Objects.equals(failureComments, other.failureComments)
                        && Objects.equals(headerJson, other.headerJson) && Objects.equals(headers, other.headers)
                        && Objects.equals(inputJson, other.inputJson) && Objects.equals(inputParam, other.inputParam)
                        && Objects.equals(keyValidation, other.keyValidation)
                        && Objects.equals(requestType, other.requestType)
                        && Objects.equals(requestURL, other.requestURL) && Objects.equals(runtime, other.runtime)
                        && Objects.equals(serialNo, other.serialNo)
                        && Objects.equals(testCaseDescription, other.testCaseDescription)
                        && Objects.equals(testCaseInputJson, other.testCaseInputJson)
                        && Objects.equals(testCaseResult, other.testCaseResult);
    }

    /**
     * @return the serial Number
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * @param serialNo the serial Number to set
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * @return test Case Description
     *         <ul>
     *         <li>Pass</li>
     *         <li>Fail</li>
     *         </ul>
     */
    public String getTestCaseDescription() {
        return testCaseDescription;
    }

    /**
     * @param testCaseDescription the test Case Description to set
     */
    public void setTestCaseDescription(String testCaseDescription) {
        this.testCaseDescription = testCaseDescription;
    }

    /**
     * @return Input JSON
     */
    public String getInputJson() {
        return inputJson;
    }

    /**
     * @param inputJson the Input JSON to set
     */
    public void setInputJson(String inputJson) {
        this.inputJson = inputJson;
    }

    /**
     * @return expected Output
     */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    /**
     * @param expectedOutput the Expected Output to set
     */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    /**
     * @return expected HTTP Status
     */
    public Integer getExpectedHttpStatus() {
        return expectedHttpStatus;
    }

    /**
     * @param expectedHttpStatus the Expected HTTP Status Code to set
     */
    public void setExpectedHttpStatus(Integer expectedHttpStatus) {
        this.expectedHttpStatus = expectedHttpStatus;
    }

    /**
     * @return Actual Output
     */
    public String getActualOutput() {
        return actualOutput;
    }

    /**
     * @param actualOutput the Actual output to set
     */
    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    /**
     * @return Actual HTTP Status
     */
    public Integer getActualHttpStatus() {
        return actualHttpStatus;
    }

    /**
     * @param actualHttpStatus the Actual HTTP Status to set
     */
    public void setActualHttpStatus(Integer actualHttpStatus) {
        this.actualHttpStatus = actualHttpStatus;
    }

    /**
     * @return Test Case Result
     */
    public String getTestCaseResult() {
        return testCaseResult;
    }

    /**
     * @param testCaseResult the Test Case Result to set
     */
    public void setTestCaseResult(String testCaseResult) {
        this.testCaseResult = testCaseResult;
    }

    /**
     * @return Execution Date Time
     */
    public LocalDateTime getExecutionDateTime() {
        return executionDateTime;
    }

    /**
     * @param executionDateTime the Execution Time to set
     */
    public void setExecutionDateTime(LocalDateTime executionDateTime) {
        this.executionDateTime = executionDateTime;
    }

}
