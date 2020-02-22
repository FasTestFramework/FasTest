package com.infogain.automation.exception;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is user defined Exception class
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
public class RandomGenerationAutomationException extends RuntimeException {

    private static final long serialVersionUID = 1955733436336278411L;

    /**
     * @param message User defined Exception Message
     * @param cause Exception cause
     */
    public RandomGenerationAutomationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message User defined Exception Message
     */
    public RandomGenerationAutomationException(String message) {
        super(message);
    }
}
