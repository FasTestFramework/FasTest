package com.infogain.automation.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "requestHeader")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutomationPerformanceTestingResultsRequestHeader {
    
    @XmlAttribute(name ="class", required = true)
    private String requestClass;
    
    @XmlValue
    private String requestHeaders;

    public String getRequestClass() {
        return requestClass;
    }

    public void setRequestClass(String requestClass) {
        this.requestClass = requestClass;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RequestHeader [");
        if (requestClass != null) {
            builder.append("requestClass=").append(requestClass).append(", ");
        }
        if (requestHeaders != null) {
            builder.append("requestHeaders=").append(requestHeaders);
        }
        builder.append("]");
        return builder.toString();
    }

}
