package com.infogain.automation.dto;

import java.util.Objects;

public class AutomationAlert {

    private String code;
    private String message;
    private AlertType alertType;

    private AutomationAlert() {
        // default constructor intended for jackson (de)serialization
    }

    public AutomationAlert(final String code, final String message, final AlertType alertType) {
        this.code = code;
        this.message = message;
        this.alertType = alertType;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    @Override
    public String toString() {
        return "AutomationAlert{" + "code='" + code + '\'' + ", message='" + message + '\'' + ", alertType=" + alertType
                        + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutomationAlert)) {
            return false;
        }
        AutomationAlert cxsAlert = (AutomationAlert) o;
        return Objects.equals(code, cxsAlert.code) && Objects.equals(message, cxsAlert.message)
                        && alertType == cxsAlert.alertType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, alertType);
    }

    public enum AlertType {
        WARNING,
        NOTE
    }
}
