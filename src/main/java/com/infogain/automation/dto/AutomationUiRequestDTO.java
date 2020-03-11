package com.infogain.automation.dto;

import org.springframework.http.HttpMethod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AutomationUiRequestDTO",
                description = "Request body required to request the controller of Automation")
public class AutomationUiRequestDTO {

    @ApiModelProperty(value = "URL TO hit", required = true, example = "/peripherals/fedexoffice/v1/claims")
    private String requestURL;
    @ApiModelProperty(value = "Type of request", required = true, example = "POST")
    private HttpMethod requestType;
    @ApiModelProperty(value = "header", required = false,
                    example = "\"[{\"Content-Type\" : \"application/json\"},{\"Accept\" : \"application/json\"}]\"")
    private String header;
    @ApiModelProperty(value = "body", required = false,
                    example = "\"{\"claimRequest\":{ \"claimType\":\"PERIPHERAL_SERVER\"\r\n}}\"")
    private String body;

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
