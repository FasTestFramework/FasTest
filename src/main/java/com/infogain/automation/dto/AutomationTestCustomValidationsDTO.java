package com.infogain.automation.dto;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.validator.AutomationTestCustomValidationsValidator;

@AutomationTestCustomValidationsValidator
@ApiModel(value = "AutomationTestCustomValidationsDTO",
                description = "Request body for testing custom validations against provided json Data.")
public class AutomationTestCustomValidationsDTO {

    @ApiModelProperty(value = "The JSON data", required = true,
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

    @ApiModelProperty(
                    value = "map of custom validations where key is the key path, and value is the validations where validations are need to be applied",
                    required = true,
                    example = "{\r\n" + "  \"output.claimId\": [\r\n" + "    \"isNotNull()\",\r\n"
                                    + "    \"contains(\\\"-\\\")\"\r\n" + "  ],\r\n"
                                    + "  \"output.receiptElements\": [\r\n" + "    \"isArray()\"\r\n" + "  ],\r\n"
                                    + "  \"receiptElements[0].image.alignment\": [\r\n" + "    \"notNull()\",\r\n"
                                    + "    \"isEqualTo(\\\"Left\\\")\"\r\n" + "  ]\r\n" + "}")
    private Map<String, List<String>> customValidations;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, List<String>> getCustomValidations() {
        return customValidations;
    }

    public void setCustomValidations(Map<String, List<String>> customValidations) {
        this.customValidations = customValidations;
    }

    @Override
    public String toString() {
        return "AutomationTestCustomValidationsDTO [data=" + data + ", customValidations=" + customValidations + "]";
    }

}
