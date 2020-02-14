package com.infogain.automation.dto;

import java.util.List;

public class AutomationRequestDTO {

    String inputExcelFileName;
    String inputExcelSheetName;
    List<AutomationExcelInputDTO> automationExcelInputDTO;

    public AutomationRequestDTO(String inputExcelFileName, String inputExcelSheetName,
                    List<AutomationExcelInputDTO> automationExcelInputDTO) {
        super();
        this.inputExcelFileName = inputExcelFileName;
        this.inputExcelSheetName = inputExcelSheetName;
        this.automationExcelInputDTO = automationExcelInputDTO;
    }

    /**
     * @return the inputExcelFileName
     */
    public String getInputExcelFileName() {
        return inputExcelFileName;
    }

    /**
     * @param inputExcelFileName the inputExcelFileName to set
     */
    public void setInputExcelFileName(String inputExcelFileName) {
        this.inputExcelFileName = inputExcelFileName;
    }

    /**
     * @return the inputExcelSheetName
     */
    public String getInputExcelSheetName() {
        return inputExcelSheetName;
    }

    /**
     * @param inputExcelSheetName the inputExcelSheetName to set
     */
    public void setInputExcelSheetName(String inputExcelSheetName) {
        this.inputExcelSheetName = inputExcelSheetName;
    }

    /**
     * @return the automationExcelInputDTO
     */
    public List<AutomationExcelInputDTO> getAutomationExcelInputDTO() {
        return automationExcelInputDTO;
    }

    /**
     * @param automationExcelInputDTO the automationExcelInputDTO to set
     */
    public void setAutomationExcelInputDTO(List<AutomationExcelInputDTO> automationExcelInputDTO) {
        this.automationExcelInputDTO = automationExcelInputDTO;
    }



}
