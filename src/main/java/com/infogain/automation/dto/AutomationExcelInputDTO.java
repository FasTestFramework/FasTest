package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="AutomationExcelInputDTO", description = "Request body required to set properties and request the controller of Automation to generate Excels.")
public class AutomationExcelInputDTO {

	@ApiModelProperty(value = "This reflects the value of test case description", required = false,example="Print Receipt with Barcode Symbology ITF and height 1")
    String testCaseDescription;
	@ApiModelProperty(value = "whether to skip the test or not", required = false,example="true")
	boolean skipTest;
	@ApiModelProperty(value = "required parameters", required = false,example="123e4567-e89b-12d3-a456-556642440000")
    String params;
	@ApiModelProperty(value = "Header Json in String", required = false,example="headers/headers.json")
    String headerJson;
	@ApiModelProperty(value = "Input Json in String", required = false,example="receiptPrinter/PrintReceiptJSON_BarcodeSymbology_ITF_Height_1.json")
    String inputJson;
	@ApiModelProperty(value = "Expected Output", required = false,example="\"{\n" + 
			"  \"errors\": [\n" + 
			"    {\n" + 
			"      \"code\": \"PERIPHERALSERVER.CLAIMID.INVALIDDATAEXCEPTION\",\n" + 
			"      \"message\": \"'claimId' field has invalid data({0}). Please try again.\"\n" + 
			"    }\n" + 
			"  ]\n" + 
			"}\"")
    String expectedOutput;
	@ApiModelProperty(value = "Expected HTTP Status", required = false,example="405")
    Integer expectedHttpStatus;
	@ApiModelProperty(value = "URL to hit", required = false,example="http://localhost:8085/peripherals/fedexoffice/v1/claims/")
    String requestUrl;
	@ApiModelProperty(value = "Type of request", required = false,example="DELETE")
    String requestType;
	@ApiModelProperty(value = "Name of input excel file", required = true,example="file1.xlsx")
    String inputExcelFileName;
	@ApiModelProperty(value = "Sheet name of input excel file", required = true,example="sheet1")
    String inputExcelSheetName;
	@ApiModelProperty(value = "Folder name for input excel files", required = false,example="Barcode")
    String inputExcelFolderName;



    public AutomationExcelInputDTO(String testCaseDescription, boolean skipTest, String params, String headerJson,
                    String inputJson, String expectedOutput, Integer expectedHttpStatus, String requestUrl,
                    String requestType, String inputExcelFileName, String inputExcelSheetName,
                    String inputExcelFolderName) {
        this.testCaseDescription = testCaseDescription;
        this.skipTest = skipTest;
        this.params = params;
        this.headerJson = headerJson;
        this.inputJson = inputJson;
        this.expectedOutput = expectedOutput;
        this.expectedHttpStatus = expectedHttpStatus;
        this.requestUrl = requestUrl;
        this.requestType = requestType;
        this.inputExcelFileName = inputExcelFileName;
        this.inputExcelSheetName = inputExcelSheetName;
        this.inputExcelFolderName = inputExcelFolderName;
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
     * @return the skipTest
     */
    public boolean isSkipTest() {
        return skipTest;
    }

    /**
     * @param skipTest the skipTest to set
     */
    public void setSkipTest(boolean skipTest) {
        this.skipTest = skipTest;
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
     * @return the inputExcelFileName
     */
    public String getInputExcelFileName() {
        return inputExcelFileName;
    }

    /**
     * @param inputExcelFileName the inputExcelFileName to set
     */
    public void setInputExcelFileName(String inputExcelFileName) {
        this.inputExcelFileName = inputExcelFileName;
    }

    /**
     * @return the inputExcelSheetName
     */
    public String getInputExcelSheetName() {
        return inputExcelSheetName;
    }

    /**
     * @param inputExcelSheetName the inputExcelSheetName to set
     */
    public void setInputExcelSheetName(String inputExcelSheetName) {
        this.inputExcelSheetName = inputExcelSheetName;
    }

    /**
     * @return the inputExcelFolderName
     */
    public String getInputExcelFolderName() {
        return inputExcelFolderName;
    }

    /**
     * @param inputExcelFolderName the inputExcelFolderName to set
     */
    public void setInputExcelFolderName(String inputExcelFolderName) {
        this.inputExcelFolderName = inputExcelFolderName;
    }

    public AutomationExcelInputDTO() {
    }

    /**
     * This method
     * 
     * @return
     * @since Mar 5, 2020
     */
    @Override
    public String toString() {
        return "AutomationExcelInputDTO [testCaseDescription=" + testCaseDescription + ", skipTest=" + skipTest
                        + ", params=" + params + ", headerJson=" + headerJson + ", inputJson=" + inputJson
                        + ", expectedOutput=" + expectedOutput + ", expectedHttpStatus=" + expectedHttpStatus
                        + ", requestUrl=" + requestUrl + ", requestType=" + requestType + ", inputExcelFileName="
                        + inputExcelFileName + ", inputExcelSheetName=" + inputExcelSheetName
                        + ", inputExcelFolderName=" + inputExcelFolderName + "]";
    }



}

