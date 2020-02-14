package com.infogain.automation.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AutomationResponse<T extends AutomationOutput> {

    private List<AutomationError> errors = new ArrayList<>();

    private T output;

    private AutomationResponse() {}

    private AutomationResponse(final T output) {
        this.output = output;
    }

    private AutomationResponse(final List<AutomationError> errors) {
        this.errors = errors;
    }

    private AutomationResponse(final AutomationError... errors) {
        this(Arrays.asList(errors));
    }

    private AutomationResponse(final String errorCode, final String errorMessage) {
        this(new AutomationError(errorCode, errorMessage));
    }

    public static AutomationResponse success() {
        return new AutomationResponse();
    }

    public static <R extends AutomationOutput> AutomationResponse<R> success(final R output) {
        return new AutomationResponse<>(output);
    }

    public static AutomationResponse error(final String errorCode, final String errorMessage) {
        return new AutomationResponse<>(errorCode, errorMessage);
    }

    public static AutomationResponse error(final List<AutomationError> errors) {
        return new AutomationResponse<>(errors);
    }

    public static AutomationResponse error(final AutomationError... errors) {
        return new AutomationResponse<>(errors);
    }

    public List<AutomationError> getErrors() {
        return errors;
    }

    public T getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "AutomationResponse{" + "errors=" + errors + ", output=" + output + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutomationResponse)) {
            return false;
        }
        AutomationResponse<?> that = (AutomationResponse<?>) o;
        return Objects.equals(errors, that.errors) && Objects.equals(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors, output);
    }
}
