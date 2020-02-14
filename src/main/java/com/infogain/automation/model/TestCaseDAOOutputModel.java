package com.infogain.automation.model;

import java.time.LocalDate;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This class is the model class for getting Test Case data for Report generation
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
public class TestCaseDAOOutputModel {

    private String inputExcelName;
    private LocalDate date;
    private long testCaseCount;

    public TestCaseDAOOutputModel(String inputExcelName, LocalDate date, long testCaseCount) {
        this.inputExcelName = inputExcelName;
        this.date = date;
        this.testCaseCount = testCaseCount;
    }

    /**
     * @return the inputExcelName
     */
    public String getInputExcelName() {
        return inputExcelName;
    }

    /**
     * @param inputExcelName the inputExcelName to set
     */
    public void setInputExcelName(String inputExcelName) {
        this.inputExcelName = inputExcelName;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the testCaseCount
     */
    public long getTestCaseCount() {
        return testCaseCount;
    }

    /**
     * @param testCaseCount the testCaseCount to set
     */
    public void setTestCaseCount(long testCaseCount) {
        this.testCaseCount = testCaseCount;
    }

    @Override
    public String toString() {
        return "TestCaseDAOOutputModel [inputExcelName=" + inputExcelName + ", date=" + date + ", testCaseCount="
                        + testCaseCount + "]";
    }

}
