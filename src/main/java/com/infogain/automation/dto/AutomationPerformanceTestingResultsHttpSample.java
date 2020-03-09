package com.infogain.automation.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "httpSample")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutomationPerformanceTestingResultsHttpSample
{
   @XmlAttribute(name ="lt", required = true)
    private String latency;
   @XmlElement(name ="responseData", required = true)
    private AutomationPerformanceTestingResultsResponseData automationPerformanceTestingResultsResponseData;
   @XmlAttribute(name ="it", required = true)
    private String idleTime;
   @XmlAttribute(name ="sby", required = true)
    private String sentBytes;
   @XmlAttribute(name ="dt", required = true)
    private String dataType;
   @XmlAttribute(name ="ct", required = true)
    private String connect;
   @XmlAttribute(name ="rc", required = true)
    private String responseCode;
   @XmlAttribute(name ="s", required = true)
    private String success;
   @XmlAttribute(name ="na", required = true)
    private String allThreads;
   @XmlAttribute(name ="t", required = true)
    private String elapsed;
   @XmlAttribute(name ="lb", required = true)
    private String label;
   @XmlAttribute(name ="by", required = true)
    private String bytes;
   @XmlAttribute(name ="ng", required = true)
    private String grpThreads;
   @XmlAttribute(name ="tn", required = true)
    private String threadName;
   @XmlAttribute(name ="rm", required = true)
    private String responseMessage;
   @XmlAttribute(name ="ts", required = true)
    private String timeStamp;
   @XmlElement(name ="responseHeader", required = true)
   private AutomationPerformanceTestingResultsResponseHeader automationPerformanceTestingResultsResponseHeader;
   @XmlElement(name ="requestHeader", required = true)
   private AutomationPerformanceTestingResultsRequestHeader automationPerformanceTestingResultsRequestHeader;
   @XmlElement(name ="java.net.URL", required = true)
   private String requestUrl;
public String getLatency() {
    return latency;
}
public void setLatency(String latency) {
    this.latency = latency;
}
public AutomationPerformanceTestingResultsResponseData getResponseData() {
    return automationPerformanceTestingResultsResponseData;
}
public void setResponseData(AutomationPerformanceTestingResultsResponseData automationPerformanceTestingResultsResponseData) {
    this.automationPerformanceTestingResultsResponseData = automationPerformanceTestingResultsResponseData;
}
public String getIdleTime() {
    return idleTime;
}
public void setIdleTime(String idleTime) {
    this.idleTime = idleTime;
}
public String getSentBytes() {
    return sentBytes;
}
public void setSentBytes(String sentBytes) {
    this.sentBytes = sentBytes;
}
public String getDataType() {
    return dataType;
}
public void setDataType(String dataType) {
    this.dataType = dataType;
}
public String getConnect() {
    return connect;
}
public void setConnect(String connect) {
    this.connect = connect;
}
public String getResponseCode() {
    return responseCode;
}
public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
}
public String getSuccess() {
    return success;
}
public void setSuccess(String success) {
    this.success = success;
}
public String getAllThreads() {
    return allThreads;
}
public void setAllThreads(String allThreads) {
    this.allThreads = allThreads;
}
public String getElapsed() {
    return elapsed;
}
public void setElapsed(String elapsed) {
    this.elapsed = elapsed;
}
public String getLabel() {
    return label;
}
public void setLabel(String label) {
    this.label = label;
}
public String getBytes() {
    return bytes;
}
public void setBytes(String bytes) {
    this.bytes = bytes;
}
public String getGrpThreads() {
    return grpThreads;
}
public void setGrpThreads(String grpThreads) {
    this.grpThreads = grpThreads;
}
public String getThreadName() {
    return threadName;
}
public void setThreadName(String threadName) {
    this.threadName = threadName;
}
public String getResponseMessage() {
    return responseMessage;
}
public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
}
public String getTimeStamp() {
    return timeStamp;
}
public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
}
public AutomationPerformanceTestingResultsResponseHeader getResponseHeader() {
    return automationPerformanceTestingResultsResponseHeader;
}
public void setResponseHeader(AutomationPerformanceTestingResultsResponseHeader automationPerformanceTestingResultsResponseHeader) {
    this.automationPerformanceTestingResultsResponseHeader = automationPerformanceTestingResultsResponseHeader;
}
public AutomationPerformanceTestingResultsRequestHeader getRequestHeader() {
    return automationPerformanceTestingResultsRequestHeader;
}
public void setRequestHeader(AutomationPerformanceTestingResultsRequestHeader automationPerformanceTestingResultsRequestHeader) {
    this.automationPerformanceTestingResultsRequestHeader = automationPerformanceTestingResultsRequestHeader;
}
public String getRequestUrl() {
    return requestUrl;
}
public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
}
@Override
public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("HttpSample [");
    if (latency != null) {
        builder.append("latency=").append(latency).append(", ");
    }
    if (automationPerformanceTestingResultsResponseData != null) {
        builder.append("responseData=").append(automationPerformanceTestingResultsResponseData).append(", ");
    }
    if (idleTime != null) {
        builder.append("idleTime=").append(idleTime).append(", ");
    }
    if (sentBytes != null) {
        builder.append("sentBytes=").append(sentBytes).append(", ");
    }
    if (dataType != null) {
        builder.append("dataType=").append(dataType).append(", ");
    }
    if (connect != null) {
        builder.append("connect=").append(connect).append(", ");
    }
    if (responseCode != null) {
        builder.append("responseCode=").append(responseCode).append(", ");
    }
    if (success != null) {
        builder.append("success=").append(success).append(", ");
    }
    if (allThreads != null) {
        builder.append("allThreads=").append(allThreads).append(", ");
    }
    if (elapsed != null) {
        builder.append("elapsed=").append(elapsed).append(", ");
    }
    if (label != null) {
        builder.append("label=").append(label).append(", ");
    }
    if (bytes != null) {
        builder.append("bytes=").append(bytes).append(", ");
    }
    if (grpThreads != null) {
        builder.append("grpThreads=").append(grpThreads).append(", ");
    }
    if (threadName != null) {
        builder.append("threadName=").append(threadName).append(", ");
    }
    if (responseMessage != null) {
        builder.append("responseMessage=").append(responseMessage).append(", ");
    }
    if (timeStamp != null) {
        builder.append("timeStamp=").append(timeStamp).append(", ");
    }
    if (automationPerformanceTestingResultsResponseHeader != null) {
        builder.append("responseHeader=").append(automationPerformanceTestingResultsResponseHeader).append(", ");
    }
    if (automationPerformanceTestingResultsRequestHeader != null) {
        builder.append("requestHeader=").append(automationPerformanceTestingResultsRequestHeader).append(", ");
    }
    if (requestUrl != null) {
        builder.append("requestUrl=").append(requestUrl);
    }
    builder.append("]");
    return builder.toString();
}


   

    
}