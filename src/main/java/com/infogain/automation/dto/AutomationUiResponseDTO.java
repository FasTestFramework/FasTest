package com.infogain.automation.dto;

import org.springframework.stereotype.Component;

@Component
public class AutomationUiResponseDTO {

    String responseBody;
    int statusCode;

    /**
     * @return the responseBody
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * @param responseBody the responseBody to set
     */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    /**
     * @return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public AutomationUiResponseDTO(String responseBody, int statusCode) {
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }

    public AutomationUiResponseDTO() {

    }



}
