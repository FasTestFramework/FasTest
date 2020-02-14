package com.infogain.automation.exception;

public class CustomValidationFailure extends Exception {
    private static final long serialVersionUID = 1955733436336278411L;

    /**
     * @param message User defined Exception Message
     * @param cause Exception cause
     */
    public CustomValidationFailure(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message User defined Exception Message
     */
    public CustomValidationFailure(String message) {
        super(message);
    }
}
