package com.infogain.automation.dto;

import java.util.List;

import com.infogain.automation.validator.AutomationExcelRequestDTOValidator;

import io.swagger.annotations.ApiModel;

@AutomationExcelRequestDTOValidator
@ApiModel(value="AutomationExcelRequestDTO", description = "Request body encapsulating input properties to request the controller of Automation to generate Excels.")
public class AutomationExcelRequestDTO {

    List<AutomationExcelInputDTO> rowData;

    public AutomationExcelRequestDTO(List<AutomationExcelInputDTO> rowData) {
        this.rowData = rowData;
    }

    public AutomationExcelRequestDTO() {
    }

    /**
     * @return the rowData
     */
    public List<AutomationExcelInputDTO> getRowData() {
        return rowData;
    }

    /**
     * @param rowData the rowData to set
     */
    public void setRowData(List<AutomationExcelInputDTO> rowData) {
        this.rowData = rowData;
    }


}
