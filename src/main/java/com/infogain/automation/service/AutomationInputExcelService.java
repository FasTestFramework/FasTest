package com.infogain.automation.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.constants.FastTestExcelHeaders;
import com.infogain.automation.dto.AutomationExcelRequestDTO;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.mapper.AutomationExcelRequestDTOtoAutomationExcelRequestModelList;
import com.infogain.automation.model.AutomationExcelRequestModel;
import com.infogain.automation.model.AutomationExcelRowModel;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.utilities.AutomationExcelUtility;

@Service
public class AutomationInputExcelService {

    private static final Logger logger = LogManager.getLogger(AutomationInputExcelService.class);
    private final AutomationProperties automationProperties;
    private final AutomationExcelUtility automationExcelUtility;

    @Autowired
    public AutomationInputExcelService(final AutomationProperties automationProperties,
                    final AutomationExcelUtility automationExcelUtility) {
        this.automationProperties = automationProperties;
        this.automationExcelUtility = automationExcelUtility;
    }

    public void insertDataToExcel(AutomationExcelRequestDTO automationExcelRequestDTO) {
        List<AutomationExcelRequestModel> automationExcelRequestModelList =
                        AutomationExcelRequestDTOtoAutomationExcelRequestModelList
                                        .mapExcelRequestDtoToExcelRequestModelList(automationExcelRequestDTO);
        automationExcelRequestModelList.forEach(automationExcelRequestModel -> {
            String inputExcelFolderName = automationExcelRequestModel.getInputExcelFolderName();
            String inputExcelName = automationExcelRequestModel.getInputExcelFileName();
            Map<String, List<AutomationExcelRowModel>> inputExcelSheetsMap = automationExcelRequestModel.getSheets();
            String inputExcelFolderPath =
                            automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_EXCEL_FOLDER_PATH);
            if (StringUtils.isNotEmpty(inputExcelFolderName)) {
                inputExcelFolderPath = inputExcelFolderPath + "/" + inputExcelFolderName;
            }
            String inputExcelFilePath = inputExcelFolderPath + "/" + inputExcelName;
            try {
                Files.createDirectories(Paths.get(inputExcelFolderPath));
            } catch (IOException e1) {
                throw new AutomationException("Unable to create directory " + inputExcelFolderName, e1);
            }
            File excelFile = new File(inputExcelFilePath);
            XSSFWorkbook workbookInput = excelFile.exists() && !excelFile.isDirectory()
                            ? automationExcelUtility.readExcelFile(inputExcelFilePath)
                            : automationExcelUtility.createExcelFile();
            insertRowsInAllExcels(inputExcelSheetsMap, workbookInput);
            automationExcelUtility.saveDataToExcel(inputExcelFilePath, workbookInput);
        });
        logger.info("Data entered in Excel Successfully");
        logger.traceExit();
    }

    private void insertRowsInAllExcels(Map<String, List<AutomationExcelRowModel>> inputExcelSheetsMap,
                    XSSFWorkbook workbookInput) {
        for (Map.Entry<String, List<AutomationExcelRowModel>> entry : inputExcelSheetsMap.entrySet()) {
            String excelSheetName = entry.getKey();
            XSSFSheet excelSheet = workbookInput.getSheet(excelSheetName);
            if (excelSheet == null) {
                excelSheet = createNewSheet(excelSheetName, workbookInput);
            }
            insertDataInExcelSheet(entry.getValue(), excelSheet);
        }
    }

    private XSSFSheet createNewSheet(String inputExcelSheetName, XSSFWorkbook workbookInput) {
        XSSFSheet sheet = workbookInput.createSheet(inputExcelSheetName);
        automationExcelUtility.reArrangeHeaders(sheet.createRow(0));
        return sheet;
    }

    private void insertDataInExcelSheet(List<AutomationExcelRowModel> automationExcelRowModelList, XSSFSheet sheet) {
        Iterator<Row> row = sheet.iterator();
        Row firstRow = row.next();
        Map<FastTestExcelHeaders, Integer> headerIndexes =
                        automationExcelUtility.reArrangeHeaders(firstRow);
        int lastSNoCount = sheet.getLastRowNum();
        while(automationExcelUtility.removeRowIfEmpty(sheet, lastSNoCount)) {
            lastSNoCount--;
        }
        Map<FastTestExcelHeaders, Object> cellData = new EnumMap<>(FastTestExcelHeaders.class);
        for (AutomationExcelRowModel automationExcelRowModel : automationExcelRowModelList) {
            Row currentRow = sheet.createRow(++lastSNoCount);
            cellData.clear();
            cellData.put(FastTestExcelHeaders.SERIAL_NO, lastSNoCount);
            cellData.put(FastTestExcelHeaders.TEST_CASE_DESCRIPTION, automationExcelRowModel.getTestCaseDescription());
            cellData.put(FastTestExcelHeaders.SKIP_TEST, "N");
            cellData.put(FastTestExcelHeaders.URL_PARAMETER, getUrlParamter(automationExcelRowModel));
            cellData.put(FastTestExcelHeaders.PARAMS, automationExcelRowModel.getParams());
            cellData.put(FastTestExcelHeaders.HEADERS, automationExcelRowModel.getHeaderJson());
            cellData.put(FastTestExcelHeaders.INPUT_JSON, automationExcelRowModel.getInputJson());
            cellData.put(FastTestExcelHeaders.EXPECTED_OUTPUT, automationExcelRowModel.getExpectedOutput());
            cellData.put(FastTestExcelHeaders.EXPECTED_HTTP_STATUS, automationExcelRowModel.getExpectedHttpStatus());
            automationExcelUtility.insertRowData(currentRow, headerIndexes, cellData);
        }
        List<FastTestExcelHeaders> columnsToDelete = new ArrayList<>();
        columnsToDelete.add(FastTestExcelHeaders.ACTUAL_OUTPUT);
        columnsToDelete.add(FastTestExcelHeaders.ACTUAL_HTTP_STATUS);
        columnsToDelete.add(FastTestExcelHeaders.TEST_CASE_RESULT);
        columnsToDelete.add(FastTestExcelHeaders.FAILURES);
        columnsToDelete.add(FastTestExcelHeaders.RUNTIME);
        columnsToDelete.add(FastTestExcelHeaders.EXECUTION_DATE_TIME);
        columnsToDelete.add(FastTestExcelHeaders.COMMENTS);
        automationExcelUtility.deleteColumnsInSheet(firstRow, columnsToDelete);
    }

    private String getUrlParamter(AutomationExcelRowModel automationExcelRowModel) {
        String requestType = automationExcelRowModel.getRequestType();
        String requestUrl = automationExcelRowModel.getRequestUrl();
        String urlParameter = "";
        if (requestUrl != null && requestType != null) {
            String urlwithRequestType = fetchrequestUrl(requestUrl) + "|" + requestType;
            String urlParameterKey = automationProperties.getKeyNameByValue(urlwithRequestType);
            urlParameter = urlParameterKey != null ? StringUtils.substringAfterLast(urlParameterKey, ".")
                            : urlwithRequestType;
        }
        return urlParameter;
    }

    private String fetchrequestUrl(String requestUrl) {
        return requestUrl.toLowerCase().startsWith("http")
                        ? requestUrl.substring(StringUtils.ordinalIndexOf(requestUrl, "/", 3))
                        : requestUrl;
    }

}
