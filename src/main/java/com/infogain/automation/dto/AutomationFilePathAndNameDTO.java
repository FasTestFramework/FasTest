package com.infogain.automation.dto;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AutomationFilePathAndNameDTO",
                description = "Response body received after requesting the control of Automation to get File Path and Name.")
public class AutomationFilePathAndNameDTO {

    @ApiModelProperty(value = "input Json folder path", example = "D:/fasTest/input/json")
    private String inputJsonFolderPath;
    @ApiModelProperty(value = "input excel folder path", example = "D:/fasTest/input/Excels")
    private String inputExcelFolderPath;
    @ApiModelProperty(value = "output folder path", example = "D:/fasTest/output")
    private String outputFolderPath;
    @ApiModelProperty(value = "",
                    example = "{\n" + "    \"Barcode/file1.xlsx\": [\n" + "      \"sheet1\"\n" + "    ]\n" + "  }")
    private Map<String, List<String>> testInputFiles;

    public AutomationFilePathAndNameDTO() {}

    public AutomationFilePathAndNameDTO(String inputJsonFolderPath, String inputExcelFolderPath,
                    String outputFolderPath, Map<String, List<String>> testInputFiles) {
        this.inputJsonFolderPath = inputJsonFolderPath;
        this.inputExcelFolderPath = inputExcelFolderPath;
        this.outputFolderPath = outputFolderPath;
        this.testInputFiles = testInputFiles;
    }

    /**
     * @return the inputJsonFolderPath
     */
    public String getInputJsonFolderPath() {
        return inputJsonFolderPath;
    }

    /**
     * @param inputJsonFolderPath the inputJsonFolderPath to set
     */
    public void setInputJsonFolderPath(String inputJsonFolderPath) {
        this.inputJsonFolderPath = inputJsonFolderPath;
    }

    /**
     * @return the inputExcelFolderPath
     */
    public String getInputExcelFolderPath() {
        return inputExcelFolderPath;
    }

    /**
     * @param inputExcelFolderPath the inputExcelFolderPath to set
     */
    public void setInputExcelFolderPath(String inputExcelFolderPath) {
        this.inputExcelFolderPath = inputExcelFolderPath;
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

    public String getOutputFolderPath() {
        return outputFolderPath;
    }

    public void setOutputFolderPath(String outputFolderPath) {
        this.outputFolderPath = outputFolderPath;
    }

}
