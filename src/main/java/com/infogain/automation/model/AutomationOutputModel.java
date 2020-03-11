package com.infogain.automation.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.infogain.automation.constants.AutomationDatabaseConstants;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is Database Model Class
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Entity
@Table(name = AutomationDatabaseConstants.TABLE_NAME_TESTS, schema = "PeripheralAutomation")
@Component
public class AutomationOutputModel implements Serializable {

    private static final long serialVersionUID = -3889897888986387688L;

    @Column(name = AutomationDatabaseConstants.COLUMN_OUTPUT_EXCEL_NAME)
    private String outputExcelName;

    @Column(name = AutomationDatabaseConstants.COLUMN_OUTPUT_SHEET_NAME)
    private String outputSheetName;

    @Column(name = AutomationDatabaseConstants.COLUMN_TOTAL_EXECUTED_CASES)
    private int totalExecutedTestCases;

    @Column(name = AutomationDatabaseConstants.COLUMN_TOTAL_PASS_TEST_CASES)
    private int totalPassTestCases;

    @Column(name = AutomationDatabaseConstants.COLUMN_TOTAL_FAIL_TEST_CASES)
    private int totalFailedTestCases;

    @Column(name = AutomationDatabaseConstants.COLUMN_EXECUTION_DATE)
    private LocalDate executionDate;

    @Column(name = AutomationDatabaseConstants.COLUMN_TESTED_BY)
    private String testedBy;

    @EmbeddedId
    private AutomationId pai;

    public AutomationOutputModel() {

    }

    /**
     * @return the testedBy
     */
    public String getTestedBy() {
        return testedBy;
    }

    /**
     * @param testedBy the testedBy to set
     */
    public void setTestedBy(String testedBy) {
        this.testedBy = testedBy;
    }

    public AutomationOutputModel(String outputExcelName, String outputSheetName, int totalExecutedTestCases,
                    int totalPassTestCases, int totalFailedTestCases, LocalDate executionDate, String testedBy,
                    AutomationId pai) {
        super();
        this.outputExcelName = outputExcelName;
        this.outputSheetName = outputSheetName;
        this.totalExecutedTestCases = totalExecutedTestCases;
        this.totalPassTestCases = totalPassTestCases;
        this.totalFailedTestCases = totalFailedTestCases;
        this.executionDate = executionDate;
        this.testedBy = testedBy;
        this.pai = pai;
    }

    /**
     * @return the outputExcelName
     */
    public String getOutputExcelName() {
        return outputExcelName;
    }

    /**
     * @param outputExcelName the outputExcelName to set
     */
    public void setOutputExcelName(String outputExcelName) {
        this.outputExcelName = outputExcelName;
    }

    /**
     * @return the outputSheetName
     */
    public String getOutputSheetName() {
        return outputSheetName;
    }

    /**
     * @param outputSheetName the outputSheetName to set
     */
    public void setOutputSheetName(String outputSheetName) {
        this.outputSheetName = outputSheetName;
    }

    /**
     * @return the totalExecutedTestCases
     */
    public int getTotalExecutedTestCases() {
        return totalExecutedTestCases;
    }

    /**
     * @param totalExecutedTestCases the totalExecutedTestCases to set
     */
    public void setTotalExecutedTestCases(int totalExecutedTestCases) {
        this.totalExecutedTestCases = totalExecutedTestCases;
    }

    /**
     * @return the totalPassTestCases
     */
    public int getTotalPassTestCases() {
        return totalPassTestCases;
    }

    /**
     * @param totalPassTestCases the totalPassTestCases to set
     */
    public void setTotalPassTestCases(int totalPassTestCases) {
        this.totalPassTestCases = totalPassTestCases;
    }

    /**
     * @return the totalFailedTestCases
     */
    public int getTotalFailedTestCases() {
        return totalFailedTestCases;
    }

    /**
     * @param totalFailedTestCases the totalFailedTestCases to set
     */
    public void setTotalFailedTestCases(int totalFailedTestCases) {
        this.totalFailedTestCases = totalFailedTestCases;
    }

    /**
     * @return the executionDate
     */
    public LocalDate getExecutionDate() {
        return executionDate;
    }

    /**
     * @param executionDate the executionDate to set
     */
    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    /**
     * @return the pai
     */
    public AutomationId getPai() {
        return pai;
    }

    /**
     * @param pai the pai to set
     */
    public void setPai(AutomationId pai) {
        this.pai = pai;
    }

    @Override
    public String toString() {
        return "AutomationOutputModel [outputExcelName=" + outputExcelName + ", outputSheetName=" + outputSheetName
                        + ", totalExecutedTestCases=" + totalExecutedTestCases + ", totalPassTestCases="
                        + totalPassTestCases + ", totalFailedTestCases=" + totalFailedTestCases + ", executionDate="
                        + executionDate + ", testedBy=" + testedBy + ", pai=" + pai + "]";
    }

}
