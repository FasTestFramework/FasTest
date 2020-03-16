package com.infogain.automation.model;

import org.json.simple.JSONAware;

public class AutomationKeyPathsModel {

    private JSONAware data;

    public JSONAware getData() {
        return data;
    }

    public void setData(JSONAware data) {
        this.data = data;
    }

    public AutomationKeyPathsModel(JSONAware data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AutomationKeyPathsModel [data=" + data + "]";
    }


}
