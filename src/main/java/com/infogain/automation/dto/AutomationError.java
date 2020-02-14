package com.infogain.automation.dto;

import java.util.Objects;

public class AutomationError {

    private String code;
    private String message;

    private AutomationError() {
        // default constructor intended for jackson (de)serialization
    }

    public AutomationError(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "AutomationError{" + "code='" + code + '\'' + ", message='" + message + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutomationError)) {
            return false;
        }
        AutomationError cxsError = (AutomationError) o;
        return Objects.equals(code, cxsError.code) && Objects.equals(message, cxsError.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }
}
