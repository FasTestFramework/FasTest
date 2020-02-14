package com.infogain.automation.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AutomationOutput {

    protected List<AutomationAlert> alerts = new ArrayList<>();

    protected AutomationOutput() {
        // default constructor for use by jackson (de)serialization
    }

    public List<AutomationAlert> getAlerts() {
        return alerts;
    }

    public AutomationOutput withAlerts(final AutomationAlert... alerts) {
        if (null != alerts) {
            this.alerts.addAll(Arrays.asList(alerts));
        }
        return this;
    }

}
