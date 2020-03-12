package com.infogain.automation.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.json.simple.JSONObject;

import com.infogain.automation.utilities.AutomationJsonUtility;
import com.infogain.automation.utilities.AutomationUtility;

@XmlRootElement(name = "responseClass")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutomationPerformanceTestingResultsResponseData {
    @XmlAttribute(name = "class", required = true)
    private String responseClass;
    @XmlValue
    private String content;
    @XmlTransient
    private JSONObject jsonContent;

    public JSONObject getJsonContent() {
        return new AutomationJsonUtility(new AutomationUtility()).fetchJSONObject(content);
    }

    public void setJsonContent(JSONObject jsonContent) {
        this.jsonContent = jsonContent;
    }

    public String getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(String responseClass) {
        this.responseClass = responseClass;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponseData [");
        if (responseClass != null) {
            builder.append("responseClass=").append(responseClass).append(", ");
        }
        if (content != null) {
            builder.append("content=").append(content);
        }
        builder.append("]");
        return builder.toString();
    }


}
