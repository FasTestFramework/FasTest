package com.infogain.automation.dto;

import java.util.List;
import java.util.Map;

public class AutomationFilePathAndNameDTO {

    String inputJsonFolderPath;
    String inputExcelFolderPath;
    Map<String, List<String>> testInputFiles;


    public AutomationFilePathAndNameDTO() {}


    public AutomationFilePathAndNameDTO(String inputJSonFolderPath, String inputExcelFolderPath,
                    Map<String, List<String>> testInputFiles) {
        this.inputJsonFolderPath = inputJSonFolderPath;
        this.inputExcelFolderPath = inputExcelFolderPath;
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



}
