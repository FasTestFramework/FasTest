package com.infogain.automation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.infogain.automation.constants.JavaDataType;

@ApiModel(value = "AutomationBVAMetadataDTO", description = "meta data for keys of JSON")
public class AutomationBVAMetadataDTO {
    @ApiModelProperty(value = "key path", required = true, example = "receiptElements[0].barcode.width")
    private String keyName;
    @ApiModelProperty(value = "type of the key specified above", required = true,
                    allowableValues = "INTEGER,LONG,DOUBLE,STRING", example = "INTEGER")
    private JavaDataType type;
    @ApiModelProperty(dataType = "number", value = "length of string", required = false, example = "5")
    private Integer length;
    @ApiModelProperty(dataType = "number", value = "allowed minimum number", required = false, example = "0")
    private Double min;
    @ApiModelProperty(dataType = "number", value = "allowed maximum number", required = false, example = "80")
    private Double max;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public JavaDataType getType() {
        return type;
    }

    public void setType(JavaDataType type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "AutomationBVAMetadataDTO [keyName=" + keyName + ", type=" + type + ", length=" + length + ", min=" + min
                        + ", max=" + max + "]";
    }

}
