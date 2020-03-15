package com.infogain.automation.model;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONAware;

public class AutomationTestCustomValidationsModel {

    private JSONAware data;
    private Map<String, List<String>> customValidations;

    public JSONAware getData() {
        return data;
    }

    public void setData(JSONAware data) {
        this.data = data;
    }

    public Map<String, List<String>> getCustomValidations() {
        return customValidations;
    }

    public void setCustomValidations(Map<String, List<String>> customValidations) {
        this.customValidations = customValidations;
    }

    public AutomationTestCustomValidationsModel(JSONAware data, Map<String, List<String>> customValidations) {
        this.data = data;
        this.customValidations = customValidations;
    }

    @Override
    public String toString() {
        return "AutomationTestCustomValidationsModel [data=" + data + ", customValidations=" + customValidations + "]";
    }

}
