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
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This class is Sprint Information Model Class
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
@Entity
@Table(name = AutomationDatabaseConstants.TABLE_NAME_SPRINT, schema = "PeripheralAutomation")
@Component
public class AutomationSprintInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AutomationSprintId psi;

    @Column(name = AutomationDatabaseConstants.COLUMN_PROJECT_NAME)
    private String projectName;

    @Column(name = AutomationDatabaseConstants.COLUMN_SPRINT_START_DATE)
    private LocalDate sprintStartDate;

    @Column(name = AutomationDatabaseConstants.COLUMN_SPRINT_END_DATE)
    private LocalDate sprintEndDate;

    /**
     * @return the psi
     */
    public AutomationSprintId getPsi() {
        return psi;
    }

    /**
     * @param psi the psi to set
     */
    public void setPsi(AutomationSprintId psi) {
        this.psi = psi;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the sprintStartDate
     */
    public LocalDate getSprintStartDate() {
        return sprintStartDate;
    }

    /**
     * @param sprintStartDate the sprintStartDate to set
     */
    public void setSprintStartDate(LocalDate sprintStartDate) {
        this.sprintStartDate = sprintStartDate;
    }

    /**
     * @return the sprintEndDate
     */
    public LocalDate getSprintEndDate() {
        return sprintEndDate;
    }

    /**
     * @param sprintEndDate the sprintEndDate to set
     */
    public void setSprintEndDate(LocalDate sprintEndDate) {
        this.sprintEndDate = sprintEndDate;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public AutomationSprintInfoModel(AutomationSprintId psi, String projectName, LocalDate sprintStartDate,
                    LocalDate sprintEndDate) {
        super();
        this.psi = psi;
        this.projectName = projectName;
        this.sprintStartDate = sprintStartDate;
        this.sprintEndDate = sprintEndDate;
    }

    public AutomationSprintInfoModel() {

    }

}
