package com.infogain.automation.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.stereotype.Component;

import com.infogain.automation.constants.AutomationDatabaseConstants;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This class is Database Model Class for composite primary Key which consists
 * <ul>
 * <li>Input Excel File Name</li>
 * <li>Input Excel Sheet Name</li>
 * <li>Execution Date Time</li>
 * </ul>
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Embeddable
@Component
public class AutomationId implements Serializable {

    private static final long serialVersionUID = 8559310374497179966L;

    @Column(name = AutomationDatabaseConstants.COLUMN_INPUT_EXCEL_NAME)
    private String inputExcelName;

    @Column(name = AutomationDatabaseConstants.COLUMN_INPUT_SHEET_NAME)
    private String inputSheetName;

    @Column(name = AutomationDatabaseConstants.COLUMN_EXECUTION_DATE_TIME)
    private LocalDateTime executionDataTime;

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
     * @return the inputSheetName
     */
    public String getInputSheetName() {
        return inputSheetName;
    }

    /**
     * @param inputSheetName the inputSheetName to set
     */
    public void setInputSheetName(String inputSheetName) {
        this.inputSheetName = inputSheetName;
    }

    /**
     * @return the executionDataTime
     */
    public LocalDateTime getExecutionDataTime() {
        return executionDataTime;
    }

    /**
     * @param executionDataTime the executionDataTime to set
     */
    public void setExecutionDataTime(LocalDateTime executionDataTime) {
        this.executionDataTime = executionDataTime;
    }

    public AutomationId(String inputExcelName, String inputSheetName, LocalDateTime executionDataTime) {
        this.inputExcelName = inputExcelName;
        this.inputSheetName = inputSheetName;
        this.executionDataTime = executionDataTime;
    }

    public AutomationId() {}

    /**
     * This method generates Hash Code for Object of {@link AutomationId}
     * 
     * @return Hash Code for Object of {@link AutomationId}
     * @since Nov 27, 2019
     */
    @Override
    public int hashCode() {
        return Objects.hash(executionDataTime, inputExcelName, inputSheetName);
    }

    /**
     * This method checks the equality of two Objects of {@link AutomationId}
     * 
     * @param obj Object which needs to be validated
     * @return {@code true} both objects are equal<br>
     *         {@code false} both objects are not equal
     * @since Nov 27, 2019
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AutomationId)) {
            return false;
        }
        AutomationId other = (AutomationId) obj;
        return Objects.equals(executionDataTime, other.executionDataTime)
                        && Objects.equals(inputExcelName, other.inputExcelName)
                        && Objects.equals(inputSheetName, other.inputSheetName);
    }

}
