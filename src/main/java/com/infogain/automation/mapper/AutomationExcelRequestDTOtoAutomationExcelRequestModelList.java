package com.infogain.automation.mapper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infogain.automation.dto.AutomationExcelInputDTO;
import com.infogain.automation.dto.AutomationExcelRequestDTO;
import com.infogain.automation.model.AutomationExcelRequestModel;
import com.infogain.automation.model.AutomationExcelRowModel;
import com.infogain.automation.utilities.AutomationJsonUtility;
import com.infogain.automation.utilities.BeanUtil;

public class AutomationExcelRequestDTOtoAutomationExcelRequestModelList {

    public static List<AutomationExcelRequestModel> convert(AutomationExcelRequestDTO automationExcelRequestDTO) {
        List<AutomationExcelInputDTO> rowData = automationExcelRequestDTO.getRowData();
        Collections.sort(rowData, Comparator.comparing(AutomationExcelInputDTO::getInputExcelFolderName)
                        .thenComparing(AutomationExcelInputDTO::getInputExcelFileName));
        List<AutomationExcelRequestModel> automationExcelRequestModelList = new ArrayList<>();
        String previous = "";
        AutomationExcelRequestModel automationExcelRequestModel = null;
        for (AutomationExcelInputDTO automationExcelInputDTO : rowData) {
            String current = automationExcelInputDTO.getInputExcelFolderName()
                            + automationExcelInputDTO.getInputExcelFileName();
            if (!previous.equals(current)) {
                if (automationExcelRequestModel != null) {
                    automationExcelRequestModelList.add(automationExcelRequestModel);
                }
                automationExcelRequestModel =
                                automationExcelInputDTOtoAutomationExcelRequestModel(automationExcelInputDTO);
            }
            addSheetDataInModel(automationExcelInputDTO, automationExcelRequestModel.getSheets());
            previous = current;
        }
        automationExcelRequestModelList.add(automationExcelRequestModel);
        return automationExcelRequestModelList;
    }

    private static AutomationExcelRequestModel automationExcelInputDTOtoAutomationExcelRequestModel(
                    AutomationExcelInputDTO automationExcelInputDTO) {
        return new AutomationExcelRequestModel(automationExcelInputDTO.getInputExcelFolderName(),
                        automationExcelInputDTO.getInputExcelFileName(), new HashMap<>());
    }

    private static void addSheetDataInModel(AutomationExcelInputDTO automationExcelInputDTO,
                    Map<String, List<AutomationExcelRowModel>> sheets) {
        if (sheets.containsKey(automationExcelInputDTO.getInputExcelSheetName())) {
            sheets.get(automationExcelInputDTO.getInputExcelSheetName())
                            .add(automationExcelDTOtoAutomationExcelRowModel(automationExcelInputDTO));
        } else {
            List<AutomationExcelRowModel> rowList = new ArrayList<>();
            rowList.add(automationExcelDTOtoAutomationExcelRowModel(automationExcelInputDTO));
            sheets.put(automationExcelInputDTO.getInputExcelSheetName(), rowList);
        }

    }

    private static AutomationExcelRowModel automationExcelDTOtoAutomationExcelRowModel(
                    AutomationExcelInputDTO automationExcelInputDTO) {
        AutomationJsonUtility automationJsonUtility = BeanUtil.getBean(AutomationJsonUtility.class);

        String skipTest = automationExcelInputDTO.isSkipTest() ? "Y" : "N";
        Map<String, List<String>> customValidationsMap = automationExcelInputDTO.getCustomValidations();
        String customValidations =
                        customValidationsMap != null ? automationJsonUtility.beautifyJson(customValidationsMap) : "";
        return new AutomationExcelRowModel(automationExcelInputDTO.getTestCaseDescription(),
                        automationExcelInputDTO.getRequestUrl(), automationExcelInputDTO.getRequestType(),
                        automationExcelInputDTO.getHeaderJson(), automationExcelInputDTO.getInputJson(),
                        automationExcelInputDTO.getExpectedOutput(), automationExcelInputDTO.getExpectedHttpStatus(),
                        automationExcelInputDTO.getParams(), skipTest, customValidations);
    }
}
