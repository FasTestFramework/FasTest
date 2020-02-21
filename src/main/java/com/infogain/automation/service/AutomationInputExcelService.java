package com.infogain.automation.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.constants.FastTestExcelHeaders;
import com.infogain.automation.dto.AutomationExcelRequestDTO;
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
            XSSFWorkbook workbookInput = null;
            if (excelFile.exists() && !excelFile.isDirectory()) {
                try (FileInputStream fis = new FileInputStream(excelFile)) {
                    workbookInput = new XSSFWorkbook(fis);
                } catch (POIXMLException | IOException e) {
                    logger.debug("Exception Occured While Reading existing Excel {} ", ExceptionUtils.getStackTrace(e));
                    throw new AutomationException("Exception Occured While Reading Excel", e);
                }
            } else {
                try {
                    workbookInput = new XSSFWorkbook();
                } catch (POIXMLException e) {
                    logger.debug("Exception Occured While creating new existing Excel {} ",
                                    ExceptionUtils.getStackTrace(e));
                    throw new AutomationException("Exception Occured While creating Excel File.", e);
                }
            }
            insertRowsInAllExcels(inputExcelSheetsMap, inputExcelFilePath, workbookInput,
                            workbookInput.createCellStyle());
            saveDataToExcel(inputExcelFilePath, workbookInput);
        });
        logger.info("Data entered in Excel Successfully");
        logger.traceExit();

    }

    private void insertRowsInAllExcels(Map<String, List<AutomationExcelRowModel>> inputExcelSheetsMap,
                    String inputExcelFilePath, XSSFWorkbook workbookInput, CellStyle cellStyle) {
        for (Map.Entry<String, List<AutomationExcelRowModel>> entry : inputExcelSheetsMap.entrySet()) {
            String excelSheetName = entry.getKey();
            XSSFSheet excelSheet = workbookInput.getSheet(excelSheetName);
            if (excelSheet == null) {
                excelSheet = createNewSheet(excelSheetName, workbookInput);
            }
            insertDataInExcelSheet(entry.getValue(), cellStyle, excelSheet);
        }
    }

    private XSSFSheet createNewSheet(String inputExcelSheetName, XSSFWorkbook workbookInput) {
        XSSFSheet sheet = workbookInput.createSheet(inputExcelSheetName);
        Row row = sheet.createRow(0);
        CellStyle cellStyleForHeaderRow = automationExcelUtility.getCellStyleForBold(workbookInput);
        int i = 0;
        for (FastTestExcelHeaders header : FastTestExcelHeaders.values()) {
            createCell(row, i++, cellStyleForHeaderRow).setCellValue(header.getText());
        }
        return sheet;
    }



    private void insertDataInExcelSheet(List<AutomationExcelRowModel> automationExcelRowModelList, CellStyle cellStyle,
                    XSSFSheet sheet) {
        Iterator<Row> row = sheet.iterator();
        Map<String, Integer> headerIndexes = automationExcelUtility.getHeaderIndexes(row.next());
        int lastSNoCount = sheet.getLastRowNum();
        for (AutomationExcelRowModel automationExcelRowModel : automationExcelRowModelList) {
            Row currentRow = sheet.createRow(++lastSNoCount);
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.SERIAL_NO.getText()), cellStyle)
                            .setCellValue(lastSNoCount);
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.TEST_CASE_DESCRIPTION.getText()), cellStyle)
                            .setCellValue(automationExcelRowModel.getTestCaseDescription());
            Integer skipTestColumnIndex = headerIndexes.get(FastTestExcelHeaders.SKIP_TEST.getText());
            if (skipTestColumnIndex != null) {
                createCell(currentRow, skipTestColumnIndex, cellStyle).setCellValue("N");
            }
            Cell urlParameterCell = createCell(currentRow,
                            headerIndexes.get(FastTestExcelHeaders.URL_PARAMETER.getText()), cellStyle);
            String requestType = automationExcelRowModel.getRequestType();
            String requestUrl = automationExcelRowModel.getRequestUrl();
            if (requestUrl != null && requestType != null) {
                String urlwithRequestType = fetchrequestUrl(requestUrl) + "|" + requestType;
                String urlParameterKey = automationProperties.getKeyNameByValue(urlwithRequestType);
                String urlParameter = urlParameterKey != null ? StringUtils.substringAfterLast(urlParameterKey, ".")
                                : urlwithRequestType;
                urlParameterCell.setCellValue(urlParameter);
            }
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.PARAMS.getText()), cellStyle)
                            .setCellValue(automationExcelRowModel.getParams());
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.HEADERS.getText()), cellStyle)
                            .setCellValue(automationExcelRowModel.getHeaderJson());
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.INPUT_JSON.getText()), cellStyle)
                            .setCellValue(automationExcelRowModel.getInputJson());
            Integer keysValidationColumnIndex = headerIndexes.get(FastTestExcelHeaders.KEYS_VALIDATION.getText());
            if (keysValidationColumnIndex != null) {
                createCell(currentRow, keysValidationColumnIndex, cellStyle);
            }
            Cell expectedOutputCell = createCell(currentRow,
                            headerIndexes.get(FastTestExcelHeaders.EXPECTED_OUTPUT.getText()), cellStyle);
            String expectedOutput = automationExcelRowModel.getExpectedOutput();
            if (StringUtils.isNotBlank(expectedOutput)) {
                JsonParser jsonParser = new JsonParser();
                try {
                    JsonElement outputObject = jsonParser.parse(expectedOutput);
                    if (!outputObject.isJsonPrimitive()) {
                        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
                        expectedOutput = gson.toJson(outputObject);
                    }
                } catch (Exception e) {
                    // no futher handling needed
                }
            }
            expectedOutputCell.setCellValue(expectedOutput);
            expectedOutputCell.getCellStyle().setWrapText(true);

            Integer expectedHttpStatus = automationExcelRowModel.getExpectedHttpStatus();
            String expectedHttpStatusString = "";
            if (expectedHttpStatus != null) {
                expectedHttpStatusString = String.valueOf(expectedHttpStatus);
            }
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.EXPECTED_HTTP_STATUS.getText()), cellStyle)
                            .setCellValue(expectedHttpStatusString);
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.ACTUAL_OUTPUT.getText()), cellStyle);
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.ACTUAL_HTTP_STATUS.getText()), cellStyle);
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.TEST_CASE_RESULT.getText()), cellStyle);
            createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.EXECUTION_DATE_TIME.getText()), cellStyle);
        }
    }

    private String fetchrequestUrl(String requestUrl) {
        return requestUrl.toLowerCase().startsWith("http")
                        ? requestUrl.substring(StringUtils.ordinalIndexOf(requestUrl, "/", 3))
                        : requestUrl;
    }

    private void setBorders(CellStyle styleBorder) {
        logger.traceEntry("setBorders method of AutomationExcelUtility class");
        styleBorder.setBorderBottom(BorderStyle.THIN);
        styleBorder.setBorderLeft(BorderStyle.THIN);
        styleBorder.setBorderRight(BorderStyle.THIN);
        styleBorder.setBorderTop(BorderStyle.THIN);
        logger.traceExit();
    }

    private Cell createCell(Row row, int columnIndex, CellStyle style) {
        logger.traceEntry("createCell method of AutomationExcelUtility class");

        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            cell = row.createCell(columnIndex);
        }
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorders(style);
        cell.setCellStyle(style);
        cell.getCellStyle().setWrapText(true);

        return logger.traceExit(cell);
    }

    public void saveDataToExcel(String inputExcelFilePath, XSSFWorkbook workbookInput) {
        logger.traceEntry("saveDataToExcel method of AutomationExcelUtility class");
        try (FileOutputStream fos = new FileOutputStream(inputExcelFilePath)) {
            workbookInput.write(fos);
        } catch (POIXMLException | IOException e) {
            logger.debug("Exception Occured While Writing Excel  as the file is OPEN{} ",
                            ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Writing Excel", e);
        } finally {
            try {
                workbookInput.close();
            } catch (IOException e) {
                logger.error("Exception Occured While Closing Excel", e);
            }
        }
        logger.traceExit();
    }

}
