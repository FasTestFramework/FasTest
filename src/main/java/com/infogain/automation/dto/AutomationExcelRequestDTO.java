package com.infogain.automation.dto;

import java.util.List;

import com.infogain.automation.validator.AutomationExcelRequestDTOValidator;

@AutomationExcelRequestDTOValidator
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
