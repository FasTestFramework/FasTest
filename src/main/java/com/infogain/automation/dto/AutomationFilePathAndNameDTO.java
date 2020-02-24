package com.infogain.automation.dto;

import java.util.List;
import java.util.Map;

public class AutomationFilePathAndNameDTO {

    String inputJSonFolderPath;
    String inputExcelFolderPath;
    Map<String, List<String>> testInputFiles;


    public AutomationFilePathAndNameDTO() {}


    public AutomationFilePathAndNameDTO(String inputJSonFolderPath, String inputExcelFolderPath,
                    Map<String, List<String>> testInputFiles) {
        this.inputJSonFolderPath = inputJSonFolderPath;
        this.inputExcelFolderPath = inputExcelFolderPath;
        this.testInputFiles = testInputFiles;
    }


    /**
     * @return the inputJSonFolderPath
     */
    public String getInputJSonFolderPath() {
        return inputJSonFolderPath;
    }


    /**
     * @param inputJSonFolderPath the inputJSonFolderPath to set
     */
    public void setInputJSonFolderPath(String inputJSonFolderPath) {
        this.inputJSonFolderPath = inputJSonFolderPath;
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
