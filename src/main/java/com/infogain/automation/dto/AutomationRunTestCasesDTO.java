package com.infogain.automation.dto;

import java.util.List;
import java.util.Map;

public class AutomationRunTestCasesDTO {
    boolean sendMail;
    boolean saveToDatabase;
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
