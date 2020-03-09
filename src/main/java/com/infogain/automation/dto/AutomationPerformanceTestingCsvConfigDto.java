package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "csvConfig",
description = "Request body for configuring input csv  config")
public class AutomationPerformanceTestingCsvConfigDto {
    @ApiModelProperty(value = "name of the input file. Relative file names are resolved based on the path of the active test plan. Absolute filenames are also supported", required = true, example = "D:\\workspaces\\peripheralWorkspace\\JmeterExampleProject\\src\\it\\resources\\claims3.csv")
    String inputCsvFilename;
    @ApiModelProperty(value = "delimiter that will be used to split the parsed values from the input file", required = true, example = ",")
    String delimiter;
    @ApiModelProperty(value = "list of separated variable names that will be used as a container for parsed values. If empty, the first line of the file will be interpreted as the list of variable names", required = true, example = "claimid,claimer,body")
    String variableNames;
    @ApiModelProperty(value = "true in case the file test plan should iterate over the file more than once. It will instruct JMeter to move the cursor at the beginning of the file", required = true, example = "false")
    String recycle;
    @ApiModelProperty(value = "Considers first line as a header line", required = true, example = "true")
    String ignoreFirstline;
    @ApiModelProperty(value = "false in case of loop iteration over the CSV file and true if you want to stop the thread after reading the whole file", required = true, example = "true")
    Boolean stopThread;
    @ApiModelProperty(value = "All threads - the file is shared between all virtual users (the default)\r\n" + 
                    "Current thread group - the file will be opened once for each thread group\r\n" + 
                    "Current thread - each file will be opened separately for each of threads\r\n" + 
                    "Identifier - all threads sharing the same identifier also share the same file", required = true, example = "shareMode.thread")
    String shareMode;

    public String getInputCsvFilename() {
        return inputCsvFilename;
    }

    public void setInputCsvFilename(String inputCsvFilename) {
        this.inputCsvFilename = inputCsvFilename;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getVariableNames() {
        return variableNames;
    }

    public void setVariableNames(String variableNames) {
        this.variableNames = variableNames;
    }

    public String getRecycle() {
        return recycle;
    }

    public void setRecycle(String recycle) {
        this.recycle = recycle;
    }

    public String getIgnoreFirstline() {
        return ignoreFirstline;
    }

    public void setIgnoreFirstline(String ignoreFirstline) {
        this.ignoreFirstline = ignoreFirstline;
    }

    public Boolean getStopThread() {
        return stopThread;
    }

    public void setStopThread(Boolean stopThread) {
        this.stopThread = stopThread;
    }

    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestingCsvConfigDto [");
        if (inputCsvFilename != null) {
            builder.append("inputCsvFilename=").append(inputCsvFilename).append(", ");
        }
        if (delimiter != null) {
            builder.append("delimiter=").append(delimiter).append(", ");
        }
        if (variableNames != null) {
            builder.append("variableNames=").append(variableNames).append(", ");
        }
        if (recycle != null) {
            builder.append("recycle=").append(recycle).append(", ");
        }
        if (ignoreFirstline != null) {
            builder.append("ignoreFirstline=").append(ignoreFirstline).append(", ");
        }
        if (stopThread != null) {
            builder.append("stopThread=").append(stopThread).append(", ");
        }
        if (shareMode != null) {
            builder.append("shareMode=").append(shareMode);
        }
        builder.append("]");
        return builder.toString();
    }


}
