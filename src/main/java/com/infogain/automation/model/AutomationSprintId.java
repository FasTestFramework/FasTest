package com.infogain.automation.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.stereotype.Component;

import com.infogain.automation.constants.AutomationDatabaseConstants;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is the model class for Sprint and PI
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
@SuppressWarnings("serial")
@Embeddable
@Component
public class AutomationSprintId implements Serializable {

    @Column(name = AutomationDatabaseConstants.COLUMN_PI)
    private String pi;

    @Column(name = AutomationDatabaseConstants.COLUMN_SPRINT)
    private String sprint;

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

    public AutomationSprintId(String pi, String sprint) {
        super();
        this.pi = pi;
        this.sprint = sprint;
    }

    public AutomationSprintId() {

    }

}
