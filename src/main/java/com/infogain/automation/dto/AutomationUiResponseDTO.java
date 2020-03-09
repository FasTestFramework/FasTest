package com.infogain.automation.dto;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Component
@ApiModel(value = "AutomationUiResponseDTO",
description = "Response body received after requesting the control of Automation Ui.")
public class AutomationUiResponseDTO {

	@ApiModelProperty(value = "response body returned as output", example="\"{\\\"output\\\":{\\\"claim\\\":{\\\"claimId\\\":\\\"ef1f6420-3bce-41d6-acf3-4e5fe0e68b2a\\\",\\\"idleTimeOutSeconds\\\":\\\"120\\\"}}}\"")	  
	String responseBody;
	@ApiModelProperty(value = "HTTP Status received", example="202")
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
