package com.infogain.automation.model;

import com.infogain.automation.utilities.AutomationRegex;

public class AutomationRegexData {

    private AutomationRegex regexSymbol;
    private String options;
    private int length;

    public AutomationRegexData(AutomationRegex regexSymbol, String options, int length) {
        this.regexSymbol = regexSymbol;
        this.options = options;
        this.length = length;
    }

    public boolean validateRegex() {
        return regexSymbol.validateRegex(options);
    }

    public AutomationRegex getRegexSymbol() {
        return regexSymbol;
    }

    public void setRegexSymbol(AutomationRegex regexSymbol) {
        this.regexSymbol = regexSymbol;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "AutomationRegexData [regexSymbol=" + regexSymbol + ", options=" + options + ", length=" + length + "]";
    }


}
