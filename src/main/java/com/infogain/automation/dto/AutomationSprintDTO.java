package com.infogain.automation.dto;

import java.time.LocalDate;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is Peripheral Sprint DTO which contains all variables for getting and setting Data to
 * Database
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
public class AutomationSprintDTO {

    private String pi;
    private String sprint;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;

    public AutomationSprintDTO(String pi, String sprint, String projectName, LocalDate startDate, LocalDate endDate) {
        super();
        this.pi = pi;
        this.sprint = sprint;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the pi
     */
    public String getPi() {
        return pi;
    }

    /**
     * @param pi the pi to set
     */
    public void setPi(String pi) {
        this.pi = pi;
    }

    /**
     * @return the sprint
     */
    public String getSprint() {
        return sprint;
    }

    /**
     * @param sprint the sprint to set
     */
    public void setSprint(String sprint) {
        this.sprint = sprint;
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
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }



}
