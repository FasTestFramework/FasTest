package com.infogain.automation.model;

import java.util.List;
import java.util.Map;

public class AutomationExcelRequestModel {

    String inputExcelFolderName;
    String inputExcelFileName;
    Map<String, List<AutomationExcelRowModel>> sheets;

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
     * @return the testInputFiles
     */
    public Map<String, List<AutomationExcelRowModel>> getSheets() {
        return sheets;
    }

    /**
     * @param testInputFiles the testInputFiles to set
     */
    public void setSheets(Map<String, List<AutomationExcelRowModel>> sheets) {
        this.sheets = sheets;
    }

    public AutomationExcelRequestModel(String inputExcelFolderName, String inputExcelFileName,
                    Map<String, List<AutomationExcelRowModel>> sheets) {
        this.inputExcelFolderName = inputExcelFolderName;
        this.inputExcelFileName = inputExcelFileName;
        this.sheets = sheets;
    }

    public AutomationExcelRequestModel() {}

    /**
     * This method
     * 
     * @return
     * @since Feb 19, 2020
     */
    @Override
    public String toString() {
        return "AutomationExcelRequestModel [inputExcelFolderName=" + inputExcelFolderName + ", inputExcelFileName="
                        + inputExcelFileName + ", testInputFiles=" + sheets + "]";
    }

}
