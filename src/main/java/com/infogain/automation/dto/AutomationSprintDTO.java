package com.infogain.automation.dto;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value = "AutomationSprintDTO", description = "This is a response body for Sprint DTO which contains all variables for getting and setting Data to Database")
public class AutomationSprintDTO {

	@ApiModelProperty(value="Name of PI", example = "20.3")
    private String pi;
	@ApiModelProperty(value="Name of sprint", example = "3")
    private String sprint;
	@ApiModelProperty(value="Name of project", example = "peripheralserver")
    private String projectName;
	@ApiModelProperty(value="Start Date", example = "2019-11-20")
    private LocalDate startDate;
	@ApiModelProperty(value="End Date", example = "2019-09-24")
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
