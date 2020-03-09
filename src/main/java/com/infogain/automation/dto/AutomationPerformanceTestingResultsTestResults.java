package com.infogain.automation.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
   "version", "automationPerformanceTestingResultsHttpSample"
})
@XmlRootElement(name = "testResults")
public class AutomationPerformanceTestingResultsTestResults 
{
    @XmlElement(name ="httpSample", required = false)
    private AutomationPerformanceTestingResultsHttpSample[] automationPerformanceTestingResultsHttpSample;

    @XmlAttribute(name = "version")
    private Double version;

    public AutomationPerformanceTestingResultsHttpSample[] getHttpSample ()
    {
        return automationPerformanceTestingResultsHttpSample;
    }

    public void setHttpSample (AutomationPerformanceTestingResultsHttpSample[] httpSample)
    {
        this.automationPerformanceTestingResultsHttpSample = httpSample;
    }

   
    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [httpSample = "+automationPerformanceTestingResultsHttpSample.toString()+", version = "+version+"]";
    }
}