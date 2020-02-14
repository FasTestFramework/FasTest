package com.infogain.automation.dto;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This class is for storing PDF as base64 String
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
public class SendMailRequestDTO {

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
