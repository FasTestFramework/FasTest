package com.infogain.automation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.infogain.automation.constants.InputType;

@JsonInclude(Include.NON_NULL)
public class AutomationKeyMetadataDTO {
    private String name;
    private InputType type;
    private String pattern;
    private String description;
    private Boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputType getType() {
        return type;
    }

    public void setType(InputType type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "AutomationKeyMetadataDTO [name=" + name + ", type=" + type + ", pattern=" + pattern + ", description=" + description
                        + ", required=" + required + "]";
    }

    public AutomationKeyMetadataDTO(String name, InputType type) {
        this.name = name;
        this.type = type;
    }



}
