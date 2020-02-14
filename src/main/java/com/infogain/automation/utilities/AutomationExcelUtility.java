package com.infogain.automation.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This class is for Reading and Writing Data to Excel File
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationExcelUtility {

    private static final Logger logger = LogManager.getLogger(AutomationExcelUtility.class);
    protected Properties automationProperties;
    private XSSFWorkbook workbook;

    @Autowired
    public AutomationExcelUtility(final AutomationProperties automationProperties) {
        this.automationProperties = automationProperties.getProps();
    }

    /**
     * This method read Data from Input Excel File and set it to Automation Input DTO List of {@link AutomationInputDTO}
     * 
     * @param excelSheetName Input Excel File Name
     * @param sheetName Excel Sheet Name
     * @throws AutomationException in Writing Data to Excel File
     * @return object of list {@link AutomationInputDTO}
     */
    public List<AutomationInputDTO> readInputExcelFile(String excelSheetName, String sheetName) {
        logger.traceEntry("readInputExcelFile method of AutomationExcelUtility class");
        String requestUrl;
        String requestType;
        ArrayList<AutomationInputDTO> jsonFileList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(excelSheetName);
                        XSSFWorkbook workbookInput = new XSSFWorkbook(fis);) {
            int sheets = workbookInput.getNumberOfSheets();
            logger.info("Total Number of sheets  {} in {}", sheets, excelSheetName);
            for (int i = 0; i < sheets; i++) {
                if (workbookInput.getSheetName(i).equalsIgnoreCase(sheetName)) {
                    XSSFSheet sheet = workbookInput.getSheetAt(i);
                    Iterator<Row> row = sheet.iterator();
                    Row firstRow = row.next();

                    Iterator<Cell> cell = firstRow.iterator();
                    int headerJsonColoumn = 0;
                    int paramColumn = 0;
                    int inputJsonColumn = 0;
                    int expectedOutputColumn = 0;
                    int expectedHttpStatusColumn = 0;
                    int serialNumberColumn = 0;
                    int testCaseDescriptionColumn = 0;
                    int urlParameterColumn = 0;
                    int runTestColumn = 0;
                    int keyValidationColumn = 0;
                    int cellNumber = 0;
                    while (cell.hasNext()) {
                        Cell c = cell.next();
                        switch (c.getStringCellValue()) {
                            case AutomationConstants.SERIAL_NO_COLUMN_NAME:
                                serialNumberColumn = cellNumber;
                                break;
                            case AutomationConstants.TEST_CASE_DESCRIPTION_COLUMN_NAME:
                                testCaseDescriptionColumn = cellNumber;
                                break;
                            case AutomationConstants.URL_PARAMETER:
                                urlParameterColumn = cellNumber;
                                break;
                            case AutomationConstants.HEADER_JSON_COLUMN_NAME:
                                headerJsonColoumn = cellNumber;
                                break;
                            case AutomationConstants.PARAM:
                                paramColumn = cellNumber;
                                break;
                            case AutomationConstants.INPUT_JSON_COLUMN_NAME:
                                inputJsonColumn = cellNumber;
                                break;
                            case AutomationConstants.EXPECTED_OUTPUT_COLUMN_NAME:
                                expectedOutputColumn = cellNumber;
                                break;
                            case AutomationConstants.EXPECTED_HTTP_STATUS_COLUMN_NAME:
                                expectedHttpStatusColumn = cellNumber;
                                break;
                            case AutomationConstants.KEYS_VALIDATION:
                                keyValidationColumn = cellNumber;
                                break;
                            case AutomationConstants.SKIP_TEST:
                                runTestColumn = cellNumber;
                            default:
                        }
                        cellNumber++;
                    }

                    while (row.hasNext()) {
                        Row currentRow = row.next();
                        if (!checkIfRowIsEmpty(currentRow)) {
                            Cell runTestCell = null;
                            if (runTestColumn != 0) {
                                runTestCell = currentRow.getCell(runTestColumn);
                            }

                            if (runTestCell == null || !runTestCell.toString().equals("Y")) {
                                Cell serialNumberCell = currentRow.getCell(serialNumberColumn);
                                Cell testCaseDescriptionCell = currentRow.getCell(testCaseDescriptionColumn);
                                Cell urlParameterCell = currentRow.getCell(urlParameterColumn);

                                Cell expectedHttpStatusCell = currentRow.getCell(expectedHttpStatusColumn);
                                if (serialNumberCell != null && testCaseDescriptionCell != null
                                                && expectedHttpStatusCell != null && urlParameterCell != null) {
                                    String serialNumber = extractValueFromCell(serialNumberCell);
                                    String testCaseDescription = testCaseDescriptionCell.toString();
                                    String expectedHttpStatusVal = extractValueFromCell(expectedHttpStatusCell);
                                    Integer expectedHttpStatus = expectedHttpStatusVal.equals("") ? 0
                                                    : Integer.parseInt(expectedHttpStatusVal);

                                    Map<String, String> keyValidationMap = new HashMap<>();
                                    extractingCustomKeyValidations(keyValidationColumn, currentRow, keyValidationMap);
                                    Cell inputJsonCell = currentRow.getCell(inputJsonColumn);
                                    Cell paramCell = currentRow.getCell(paramColumn);
                                    Cell headerJsonCell = currentRow.getCell(headerJsonColoumn);
                                    Cell expectedOutputCell = currentRow.getCell(expectedOutputColumn);
                                    String headerJson = headerJsonCell != null ? headerJsonCell.toString() : "";
                                    String inputJson = inputJsonCell != null ? inputJsonCell.toString() : "";
                                    String expectedOutput =
                                                    expectedOutputCell != null ? expectedOutputCell.toString() : "";
                                    String inputParam = paramCell != null ? paramCell.toString() : "";

                                    String endpoint =
                                                    AutomationConstants.FASTEST_URL_PREFIX + urlParameterCell;

                                    String[] printJsonReceiptUrlArray =
                                                    automationProperties.getProperty(endpoint).split("\\|");
                                    // Splitting the Request Url and Request Type which are defined in properties file
                                    requestUrl = printJsonReceiptUrlArray[0];
                                    requestType = printJsonReceiptUrlArray[1];
                                    HttpMethod requestMethodType;
                                    try {
                                        requestMethodType = HttpMethod.valueOf(requestType.toUpperCase());
                                    } catch (IllegalArgumentException illegalArgumentException) {
                                        throw new AutomationException(
                                                        "Wrong Http Method is defined in Properties file i.e. "
                                                                        + requestType);
                                    }
                                    if (StringUtils.isNotBlank(testCaseDescription)
                                                    && StringUtils.isNotBlank(serialNumber)
                                                    && expectedHttpStatus != 0) {
                                        AutomationInputDTO automationInput = new AutomationInputDTO(serialNumber,
                                                        testCaseDescription, headerJson, inputJson, inputParam,
                                                        expectedOutput, expectedHttpStatus, requestUrl,
                                                        requestMethodType, keyValidationMap);
                                        jsonFileList.add(automationInput);
                                    } else {
                                        throw new AutomationException("Wrong Input Specified in Excel Sheet "
                                                        + excelSheetName + " in file " + sheetName);
                                    }
                                } else {
                                    throw new AutomationException("Excel Sheet " + sheetName + " in file "
                                                    + excelSheetName + " has some missing inputs");
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.debug("Exception Occured While Reading Excel {} ", ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Reading Excel", e);
        }
        return logger.traceExit(jsonFileList);
    }

    private String extractValueFromCell(Cell serialNumberCell) {
        String serialNumber = "";
        if (serialNumberCell.getCellType().equals(CellType.NUMERIC)) {
            serialNumber = String.valueOf((int) serialNumberCell.getNumericCellValue());
        } else if (serialNumberCell.getCellType().equals(CellType.STRING)) {
            serialNumber = serialNumberCell.getStringCellValue();
        }
        return serialNumber;
    }

    private void extractingCustomKeyValidations(int keyValidationColumn, Row currentRow,
                    Map<String, String> keyValidationMap) {
        if (keyValidationColumn != 0) {
            String[] allKeyAndValues = currentRow.getCell(keyValidationColumn).toString().split("\\|");
            for (String keyWithValue : allKeyAndValues) {
                String[] keyValue = keyWithValue.split("=");
                if (keyValue.length == 2) {
                    keyValidationMap.put(keyValue[0], keyValue[1]);
                }
            }
        }
    }

    /**
     * This method removes extra sheets from Input Excel File which are not present in Automation Properties
     * 
     * @since Nov 27, 2019
     */
    private void removeOtherSheets(String[] excelSheetNames) {
        logger.traceEntry("removeOtherSheets method of AutomationExcelUtility class");
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = sheetCount - 1; i >= 0; i--) {
            boolean isPresent = Arrays.stream(excelSheetNames).anyMatch(workbook.getSheetName(i)::equals);
            if (!isPresent) {
                workbook.removeSheetAt(i);
            }
        }
        logger.traceExit();
    }

    /**
     * This method writes Data from {@link AutomationInputDTO} to new Excel File
     * 
     * @param inputExcelSheetName Input Excel Sheet Name
     * @param sheetName Excel Sheets Name
     * @param automationInputDTOList list of {@link AutomationInputDTO}
     * @since Nov 27, 2019
     */
    public void writeOutputExcelFile(String inputExcelSheetName, String sheetName,
                    List<AutomationInputDTO> automationInputDTOList) {
        logger.traceEntry("writeOutputExcelFile method of AutomationExcelUtility class");

        try (FileInputStream fis = new FileInputStream(inputExcelSheetName);) {
            if (workbook == null) {
                workbook = new XSSFWorkbook(fis);
            }
            int sheets = workbook.getNumberOfSheets();
            logger.info("Total Number of sheets to write : {}", sheets);
            CellStyle styleBorder = workbook.createCellStyle();
            setBorders(styleBorder);
            int executionDateTimeColumn = 0;
            int actualOutputColumn = 0;
            int actualHttpStatusColumn = 0;
            int testCaseResultColumn = 0;
            int serialNumberColumn = 0;
            int cellNumber = 0;
            for (int i = 0; i < sheets; i++) {
                if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    ArrayList<Integer> rowToBeRemovedIndex = new ArrayList<>();
                    Iterator<Row> row = sheet.iterator();
                    Row firstRow = row.next();

                    Iterator<Cell> cell = firstRow.iterator();

                    while (cell.hasNext()) {
                        Cell c = cell.next();
                        CellStyle cellStyleBorder = c.getCellStyle();
                        setBorders(cellStyleBorder);
                        c.setCellStyle(cellStyleBorder);
                        switch (c.getStringCellValue()) {
                            case AutomationConstants.SERIAL_NO_COLUMN_NAME:
                                serialNumberColumn = cellNumber;
                                break;
                            case AutomationConstants.ACTUAL_OUTPUT_COLUMN_NAME:
                                actualOutputColumn = cellNumber;
                                break;
                            case AutomationConstants.ACTUAL_HTTP_STATUS_COLUMN_NAME:
                                actualHttpStatusColumn = cellNumber;
                                break;
                            case AutomationConstants.TEST_CASE_RESULT_COLUMN_NAME:
                                testCaseResultColumn = cellNumber;
                                break;
                            case AutomationConstants.EXECUTION_DATE_TIME_COLUMN_NAME:
                                executionDateTimeColumn = cellNumber;
                                break;

                            default:
                        }
                        cellNumber++;
                    }
                    int j = 0;
                    while (row.hasNext() && automationInputDTOList.size() > j) {
                        AutomationInputDTO automationInputDTO = automationInputDTOList.get(j);
                        // Iterating Row
                        Row currentRow = row.next();
                        Cell serialNumberCell = currentRow.getCell(serialNumberColumn);
                        String serialNumber = extractValueFromCell(serialNumberCell);
                        if (serialNumber.equals(automationInputDTO.getSerialNo())) {
                            String actualOutput = automationInputDTO.getActualOutput();
                            if (StringUtils.isNotBlank(actualOutput)) {
                                Cell actualOutputColumnCell;
                                JsonParser jsonParser = new JsonParser();
                                String actualOutputString = actualOutput;
                                try {
                                    JsonElement outputObject = jsonParser.parse(actualOutput);
                                    if (!outputObject.isJsonPrimitive()) {
                                        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
                                                        .create();
                                        actualOutputString = gson.toJson(outputObject);
                                    }
                                } finally {
                                    actualOutputColumnCell = createCell(currentRow, actualOutputColumn, styleBorder);
                                    actualOutputColumnCell.setCellValue(actualOutputString);
                                    actualOutputColumnCell.getCellStyle().setWrapText(true);
                                }
                            }
                            createCell(currentRow, actualHttpStatusColumn, styleBorder)
                                            .setCellValue(automationInputDTO.getActualHttpStatus());
                            XSSFFont boldFont = workbook.createFont();
                            boldFont.setBold(true);
                            CellStyle cellStyleForBGColor = workbook.createCellStyle();
                            // Setting BackGround color of TestCaseResult to Green for Pass and Red for Fail
                            cellStyleForBGColor.setFillForegroundColor(automationInputDTO.getTestCaseResult()
                                            .equalsIgnoreCase(AutomationConstants.TEST_CASE_RESULT_PASS)
                                                            ? IndexedColors.BRIGHT_GREEN.getIndex()
                                                            : IndexedColors.RED.getIndex());
                            cellStyleForBGColor.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            cellStyleForBGColor.setFont(boldFont);
                            createCell(currentRow, testCaseResultColumn, cellStyleForBGColor)
                                            .setCellValue(automationInputDTO.getTestCaseResult());
                            CellStyle cellStyleForDate = workbook.createCellStyle();
                            CreationHelper createHelper = workbook.getCreationHelper();
                            cellStyleForDate.setDataFormat(
                                            createHelper.createDataFormat().getFormat("m/d/yyyy h:mm:ss.0"));
                            createCell(currentRow, executionDateTimeColumn, cellStyleForDate)
                                            .setCellValue(LocalDateTime.now());
                            j++;
                        } else {
                            rowToBeRemovedIndex.add(currentRow.getRowNum());
                        }
                    }
                    sheet.autoSizeColumn(executionDateTimeColumn);
                    for (ListIterator<Integer> iterator =
                                    rowToBeRemovedIndex.listIterator(rowToBeRemovedIndex.size()); iterator
                                                    .hasPrevious();) {
                        removeRow(sheet, iterator.previous());
                    }
                }
            }
        } catch (IOException e) {
            logger.debug("Exception Occured While Writing Excel {} ", ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Writing Excel", e);
        }
        logger.traceExit();
    }

    /**
     * This method is to set Border Style in Output Excel File
     * 
     * @param styleBorder - Style which needs to be apply on Border in Excel File
     * @since Nov 27, 2019
     */
    private void setBorders(CellStyle styleBorder) {
        logger.traceEntry("setBorders method of AutomationExcelUtility class");

        styleBorder.setBorderBottom(BorderStyle.THIN);
        styleBorder.setBorderLeft(BorderStyle.THIN);
        styleBorder.setBorderRight(BorderStyle.THIN);
        styleBorder.setBorderTop(BorderStyle.THIN);
        logger.traceExit();
    }

    /**
     * This method writes Data to Excel Output File
     * 
     * @param outputExcelSheetName Output Excel Sheet Name
     * @since Nov 27, 2019
     */
    public void saveDataToExcel(String outputExcelSheetName, String[] excelSheetNames) {
        logger.traceEntry("saveDataToExcel method of AutomationExcelUtility class");
        removeOtherSheets(excelSheetNames);
        try (FileOutputStream fos = new FileOutputStream(outputExcelSheetName)) {
            workbook.write(fos);
            workbook.close();
            workbook = null;
        } catch (IOException e) {
            logger.debug("Exception Occured While Writing Excel {} ", ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Writing Excel", e);
        }
        logger.traceExit();
    }

    /**
     * This method creates a cell if cell is not present in particular row and column
     * 
     * @param row of Excel Sheet
     * @param columnIndex of Column
     * @param style instance
     * @return Cell Object which we use to set values
     * @since Nov 27, 2019
     */
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
        return logger.traceExit(cell);
    }

    private boolean checkIfRowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }

    private void removeRow(XSSFSheet sheet, int rowIndex) {
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {
            Row removingRow = sheet.getRow(rowIndex);
            if (removingRow != null) {
                sheet.removeRow(removingRow);
            }
        }
    }

}
