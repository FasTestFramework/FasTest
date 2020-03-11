package com.infogain.automation.dto;

import java.util.List;

import org.json.simple.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.validator.AutomationBVAValidator;
@AutomationBVAValidator
@ApiModel(value = "AutomationBoundaryValueAnalysisDTO",
                description = "Request body for Automation boundary value JSONs creation")
public class AutomationBoundaryValueAnalysisDTO {

    @ApiModelProperty(value = "The Claim id or a unique identifier of the client application", required = true,
                    example = "{\r\n" + "  \"claimId\": \"123e4567-e89b-12d3-a456-556642440000\",\r\n"
                                    + "  \"receiptElements\": [\r\n" + "    {\r\n" + "      \"barcode\": {\r\n"
                                    + "        \"alignment\": \"Center\",\r\n" + "        \"data\": \"012345678\",\r\n"
                                    + "        \"height\": 100,\r\n" + "        \"symbology\": \"ITF\",\r\n"
                                    + "        \"textPosition\": \"Below\",\r\n" + "        \"width\": 100\r\n"
                                    + "      },\r\n" + "      \"cut\": {\r\n" + "        \"cutReceipt\": true\r\n"
                                    + "      },\r\n" + "      \"image\": {\r\n" + "        \"alignment\": \"Left\",\r\n"
                                    + "        \"url\": \"www.fedex.peripheralserver.com/images/logo.gif\"\r\n"
                                    + "      },\r\n" + "      \"receiptText\": {\r\n"
                                    + "        \"alignment\": \"This is the alignment of text line to be printed on receipt\",\r\n"
                                    + "        \"text\": \"This is a text line to be printed on receipt\"\r\n"
                                    + "      }\r\n" + "    }\r\n" + "  ]\r\n" + "}")
    private Object data;

    private List<AutomationBVAMetadataDTO> metaData;
    @ApiModelProperty(value = "folder name", required = true, example = "folder")
    private String folderName;
    @ApiModelProperty(value = "file name", required = true, example = "file.json")
    private String fileName;

    public AutomationBoundaryValueAnalysisDTO(JSONObject data, List<AutomationBVAMetadataDTO> metaData,
                    String folderName, String fileName) {
        this.data = data;
        this.metaData = metaData;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public AutomationBoundaryValueAnalysisDTO() {}

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<AutomationBVAMetadataDTO> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<AutomationBVAMetadataDTO> metaData) {
        this.metaData = metaData;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "AutomationBoundaryValueAnalysisDTO [data=" + data + ", metaData=" + metaData + ", folderName="
                        + folderName + ", fileName=" + fileName + "]";
    }



}
