package com.infogain.automation.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "responseHeader")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutomationPerformanceTestingResultsResponseHeader {
    
    @XmlAttribute(name ="class", required = true)
    private String responseClass;
    
    @XmlValue
    private String responseHeaders;

    public String getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(String responseClass) {
        this.responseClass = responseClass;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponseHeader [");
        if (responseClass != null) {
            builder.append("responseClass=").append(responseClass).append(", ");
        }
        if (responseHeaders != null) {
            builder.append("responseHeaders=").append(responseHeaders);
        }
        builder.append("]");
        return builder.toString();
    }
    
    

}
