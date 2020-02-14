package com.infogain.automation.dto;

import org.springframework.http.HttpMethod;

public class AutomationUiRequestDTO {

    String requestURL;
    HttpMethod requestType;
    String header;
    String body;

    /**
     * @return the requestURL
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     * @param requestURL the requestURL to set
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     * @return the requestType
     */
    public HttpMethod getRequestType() {
        return requestType;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(HttpMethod requestType) {
        this.requestType = requestType;
    }

    /**
     * @return the header
     */
    public String getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    public AutomationUiRequestDTO(String requestURL, HttpMethod requestType, String header, String body) {
        super();
        this.requestURL = requestURL;
        this.requestType = requestType;
        this.header = header;
        this.body = body;
    }



}
