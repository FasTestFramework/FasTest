package com.infogain.automation.dto;

import java.util.HashMap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "httpHandler",
description = "Request body for configuring httpHandler to be used while request processing")
public class AutomationPerformanceTestingHttpHandlerDto {
    @ApiModelProperty(value = "The domain name of the endpoint to hit", required = true, example = "localhost")
    String  domain;
    @ApiModelProperty(value = "http/https", required = true, example = "http")
    String  protocol;
    @ApiModelProperty(value = "api path to test along with path variables", required = true, example = "/peripherals/fedexoffice/v1/claims/${claimid}/abcd/${claimer}")
    String  path;
    @ApiModelProperty(value = "GET/POST/PUT/DELETE", required = true, example = "POST")
    String  httpMethod;
    @ApiModelProperty(value = "HTTP Handler name.Can be any string", required = true, example = "AutomationPerformanceTest")
    String  name;
    @ApiModelProperty(value = "api port", required = true, example = "8086")
    int  port;
    @ApiModelProperty(value = "array of headers required to hit the api", required = true, example = "{\r\n" + 
                    "      \"Content-Type\": \"application/json\"\r\n" + 
                    "    }")
    HashMap<String,String> headers;
    
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public HashMap<String, String> getHeaders() {
        return headers;
    }
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestiingHttpHandlerDto [");
        if (domain != null) {
            builder.append("domain=").append(domain).append(", ");
        }
        if (protocol != null) {
            builder.append("protocol=").append(protocol).append(", ");
        }
        if (path != null) {
            builder.append("path=").append(path).append(", ");
        }
        if (httpMethod != null) {
            builder.append("httpMethod=").append(httpMethod).append(", ");
        }
        if (name != null) {
            builder.append("name=").append(name).append(", ");
        }
        builder.append("port=").append(port).append(", ");
        if (headers != null) {
            builder.append("headers=").append(headers);
        }
        builder.append("]");
        return builder.toString();
    }
    
}
