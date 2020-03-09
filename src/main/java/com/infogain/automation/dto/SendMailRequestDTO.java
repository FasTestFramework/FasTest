package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is for storing PDF as base64 String
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
@ApiModel(value = "SendMailRequestDTO", description = "Request body for storing PDF as base64 String")
public class SendMailRequestDTO {

	@ApiModelProperty(value = "Name of the report file", required = true, example = "outputPDF.pdf")
    private String reportFileName;

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    @Override
    public String toString() {
        return "SendMailRequestDTO [reportFileName=" + reportFileName + "]";
    }

}
