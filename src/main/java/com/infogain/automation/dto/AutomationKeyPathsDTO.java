package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.validator.AutomationKeyPathsValidator;
@AutomationKeyPathsValidator
@ApiModel(value = "AutomationKeyPathsDTO",
                description = "Request body to get all valid key paths for provided json Data.")
public class AutomationKeyPathsDTO {

    @ApiModelProperty(value = "The json data", required = true,
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AutomationKeyPathsDTO [data=" + data + "]";
    }

}
