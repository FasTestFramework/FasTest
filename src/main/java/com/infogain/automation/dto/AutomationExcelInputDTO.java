package com.infogain.automation.dto;

import java.util.Objects;

public class AutomationExcelInputDTO {

    String serialNo;
    String testCaseDescription;
    String urlParameter;
    String headerJson;
    String inputJson;
    String expectedOutput;
    Integer expectedHttpStatus;
    String actualOutput;
    Integer actualHttpStatus;
    String testCaseResult;
    String param;

    public AutomationExcelInputDTO(String serialNo, String testCaseDescription, String urlParameter, String headerJson,
                    String inputJson, String expectedOutput, Integer expectedHttpStatus, String actualOutput,
                    Integer actualHttpStatus, String testCaseResult, String param) {
        super();
        this.serialNo = serialNo;
        this.testCaseDescription = testCaseDescription;
        this.urlParameter = urlParameter;
        this.headerJson = headerJson;
        this.inputJson = inputJson;
        this.expectedOutput = expectedOutput;
        this.expectedHttpStatus = expectedHttpStatus;
        this.actualOutput = actualOutput;
        this.actualHttpStatus = actualHttpStatus;
        this.testCaseResult = testCaseResult;
        this.param = param;
    }


    /**
     * This method
     * 
     * @return
     * @since Jan 8, 2020
     */
    @Override
    public String toString() {
        return "AutomationExcelInputDTO [serialNo=" + serialNo + ", testCaseDescription=" + testCaseDescription
                        + ", urlParameter=" + urlParameter + ", headerJson=" + headerJson + ", inputJson=" + inputJson
                        + ", expectedOutput=" + expectedOutput + ", expectedHttpStatus=" + expectedHttpStatus
                        + ", actualOutput=" + actualOutput + ", actualHttpStatus=" + actualHttpStatus
                        + ", testCaseResult=" + testCaseResult + ", param=" + param + "]";


    }



    /**
     * This method
     * 
     * @return
     * @since Jan 8, 2020
     */
    @Override
    public int hashCode() {
        return Objects.hash(actualHttpStatus, actualOutput, expectedHttpStatus, expectedOutput, headerJson, inputJson,
                        param, serialNo, testCaseDescription, testCaseResult, urlParameter);
    }


    /**
     * This method
     * 
     * @param obj
     * @return
     * @since Jan 8, 2020
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AutomationExcelInputDTO)) {
            return false;
        }
        AutomationExcelInputDTO other = (AutomationExcelInputDTO) obj;
        return Objects.equals(actualHttpStatus, other.actualHttpStatus)
                        && Objects.equals(actualOutput, other.actualOutput)
                        && Objects.equals(expectedHttpStatus, other.expectedHttpStatus)
                        && Objects.equals(expectedOutput, other.expectedOutput)
                        && Objects.equals(headerJson, other.headerJson) && Objects.equals(inputJson, other.inputJson)
                        && Objects.equals(param, other.param) && Objects.equals(serialNo, other.serialNo)
                        && Objects.equals(testCaseDescription, other.testCaseDescription)
                        && Objects.equals(testCaseResult, other.testCaseResult)
                        && Objects.equals(urlParameter, other.urlParameter);
    }


    /**
     * @return the serialNo
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * @param serialNo the serialNo to set
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

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
     * @return the urlParameter
     */
    public String getUrlParameter() {
        return urlParameter;
    }

    /**
     * @param urlParameter the urlParameter to set
     */
    public void setUrlParameter(String urlParameter) {
        this.urlParameter = urlParameter;
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
     * @return the actualOutput
     */
    public String getActualOutput() {
        return actualOutput;
    }

    /**
     * @param actualOutput the actualOutput to set
     */
    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    /**
     * @return the actualHttpStatus
     */
    public Integer getActualHttpStatus() {
        return actualHttpStatus;
    }

    /**
     * @param actualHttpStatus the actualHttpStatus to set
     */
    public void setActualHttpStatus(Integer actualHttpStatus) {
        this.actualHttpStatus = actualHttpStatus;
    }

    /**
     * @return the testCaseResult
     */
    public String getTestCaseResult() {
        return testCaseResult;
    }

    /**
     * @param testCaseResult the testCaseResult to set
     */
    public void setTestCaseResult(String testCaseResult) {
        this.testCaseResult = testCaseResult;
    }

    /**
     * @return the param
     */
    public String getParam() {
        return param;
    }

    /**
     * @param param the param to set
     */
    public void setParam(String param) {
        this.param = param;
    }

}

