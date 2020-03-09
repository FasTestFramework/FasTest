package com.infogain.automation.dto;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="AutomationRunTestCasesDTO", description = "Request body required to request the controller of Automation to run test cases.")
public class AutomationRunTestCasesDTO {
	@ApiModelProperty(value = "flag value whether to send mail", required = false,
            example = "false")
    boolean sendMail;
	@ApiModelProperty(value = "flag value whether to save to database", required = false,
            example = "false")
    boolean saveToDatabase;
	@ApiModelProperty(value = "map of input files to test", required = true,
            example = "{\n" + 
            		"\"Peripheral Server Test.xlsx\":[\"PeripheralDeviceHealthTest\",\"ReceiptPrinterJSONTest\"]\n" + 
            		"    }")
    Map<String, List<String>> testInputFiles;

    /**
     * @return the sendMail
     */
    public boolean isSendMail() {
        return sendMail;
    }

    /**
     * @param sendMail the sendMail to set
     */
    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    /**
     * @return the saveToDatabase
     */
    public boolean isSaveToDatabase() {
        return saveToDatabase;
    }

    /**
     * @param saveToDatabase the saveToDatabase to set
     */
    public void setSaveToDatabase(boolean saveToDatabase) {
        this.saveToDatabase = saveToDatabase;
    }

    /**
     * @return the testInputFiles
     */
    public Map<String, List<String>> getTestInputFiles() {
        return testInputFiles;
    }

    /**
     * @param testInputFiles the testInputFiles to set
     */
    public void setTestInputFiles(Map<String, List<String>> testInputFiles) {
        this.testInputFiles = testInputFiles;
    }



    public AutomationRunTestCasesDTO(boolean sendMail, boolean saveToDatabase,
                    Map<String, List<String>> testInputFiles) {
        this.sendMail = sendMail;
        this.saveToDatabase = saveToDatabase;
        this.testInputFiles = testInputFiles;
    }



}
