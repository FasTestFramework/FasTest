package com.infogain.automation.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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
import com.infogain.automation.constants.FastTestExcelHeaders;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is for Reading and Writing Data to Excel File
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationExcelUtility {

    private static final Logger logger = LogManager.getLogger(AutomationExcelUtility.class);
    protected AutomationProperties automationProperties;
    private XSSFWorkbook workbook;

    @Autowired
    public AutomationExcelUtility(final AutomationProperties automationProperties) {
        this.automationProperties = automationProperties;
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
                        XSSFWorkbook workbookInput = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbookInput.getSheet(sheetName);
            if (sheet != null) {
                Iterator<Row> row = sheet.iterator();
                Map<String, Integer> headerIndexes = getHeaderIndexes(row.next());
                while (row.hasNext()) {
                    Row currentRow = row.next();
                    if (!checkIfRowIsEmpty(currentRow)) {
                        Cell runTestCell = null;
                        Integer skipTestColumn = headerIndexes.get(FastTestExcelHeaders.SKIP_TEST.getText());
                        if (skipTestColumn != null) {
                            runTestCell = currentRow.getCell(skipTestColumn);
                        }

                        if (runTestCell == null || !runTestCell.toString().equals("Y")) {
                            Cell serialNumberCell = currentRow
                                            .getCell(headerIndexes.get(FastTestExcelHeaders.SERIAL_NO.getText()));
                            Cell testCaseDescriptionCell = currentRow.getCell(
                                            headerIndexes.get(FastTestExcelHeaders.TEST_CASE_DESCRIPTION.getText()));
                            Cell urlParameterCell = currentRow
                                            .getCell(headerIndexes.get(FastTestExcelHeaders.URL_PARAMETER.getText()));

                            Cell expectedHttpStatusCell = currentRow.getCell(
                                            headerIndexes.get(FastTestExcelHeaders.EXPECTED_HTTP_STATUS.getText()));
                            if (serialNumberCell != null && testCaseDescriptionCell != null
                                            && expectedHttpStatusCell != null && urlParameterCell != null) {
                                String serialNumber = extractValueFromCell(serialNumberCell);
                                String testCaseDescription = testCaseDescriptionCell.toString();
                                String expectedHttpStatusVal = extractValueFromCell(expectedHttpStatusCell);
                                Integer expectedHttpStatus = expectedHttpStatusVal.equals("") ? 0
                                                : Integer.parseInt(expectedHttpStatusVal);

                                Map<String, String> keyValidationMap = extractingCustomKeyValidations(currentRow,
                                                headerIndexes.get(FastTestExcelHeaders.KEYS_VALIDATION.getText()));
                                Cell inputJsonCell = currentRow
                                                .getCell(headerIndexes.get(FastTestExcelHeaders.INPUT_JSON.getText()));
                                Cell paramCell = currentRow
                                                .getCell(headerIndexes.get(FastTestExcelHeaders.PARAMS.getText()));
                                Cell headerJsonCell = currentRow
                                                .getCell(headerIndexes.get(FastTestExcelHeaders.HEADERS.getText()));
                                Cell expectedOutputCell = currentRow.getCell(
                                                headerIndexes.get(FastTestExcelHeaders.EXPECTED_OUTPUT.getText()));
                                String headerJson = headerJsonCell != null ? headerJsonCell.toString() : "";
                                String inputJson = inputJsonCell != null ? inputJsonCell.toString() : "";
                                String expectedOutput = expectedOutputCell != null ? expectedOutputCell.toString() : "";
                                String inputParam = paramCell != null ? paramCell.toString() : "";

                                String endpoint = urlParameterCell.toString();
                                if (!endpoint.contains("|")) {
                                    endpoint = automationProperties
                                                    .getProperty(AutomationConstants.FASTEST_URL_PREFIX + endpoint);
                                }
                                String[] printJsonReceiptUrlArray = endpoint.split("\\|");
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
                                if (StringUtils.isNotBlank(testCaseDescription) && StringUtils.isNotBlank(serialNumber)
                                                && expectedHttpStatus != 0) {
                                    AutomationInputDTO automationInput = new AutomationInputDTO(serialNumber,
                                                    testCaseDescription, headerJson, inputJson, inputParam,
                                                    expectedOutput, expectedHttpStatus, requestUrl, requestMethodType,
                                                    keyValidationMap);
                                    jsonFileList.add(automationInput);
                                } else {
                                    throw new AutomationException("Wrong Input Specified in Excel Sheet "
                                                    + excelSheetName + " in file " + sheetName);
                                }
                            } else {
                                throw new AutomationException("Excel Sheet " + sheetName + " in file " + excelSheetName
                                                + " has some missing inputs");
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

    private Map<String, String> extractingCustomKeyValidations(Row currentRow, int keyValidationColumn) {
        Map<String, String> keyValidationMap = new HashMap<>();
        if (keyValidationColumn != 0) {
            String[] allKeyAndValues = currentRow.getCell(keyValidationColumn).toString().split("\\|");
            for (String keyWithValue : allKeyAndValues) {
                String[] keyValue = keyWithValue.split("=");
                if (keyValue.length == 2) {
                    keyValidationMap.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return keyValidationMap;
    }

    /**
     * This method removes extra testInputFiles from Input Excel File which are not present in Automation Properties
     * 
     * @since Nov 27, 2019
     */
    private void removeOtherSheets(List<String> excelSheetNames) {
        logger.traceEntry("removeOtherSheets method of AutomationExcelUtility class");
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = sheetCount - 1; i >= 0; i--) {
            boolean isPresent = excelSheetNames.stream().anyMatch(workbook.getSheetName(i)::equals);
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

        try (FileInputStream fis = new FileInputStream(inputExcelSheetName)) {
            if (workbook == null) {
                workbook = new XSSFWorkbook(fis);
            }
            CellStyle styleBorder = workbook.createCellStyle();
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet != null) {
                ArrayList<Integer> rowToBeRemovedIndex = new ArrayList<>();
                Iterator<Row> row = sheet.iterator();
                Map<String, Integer> headerIndexes = getHeaderIndexes(row.next());
                int j = 0;
                while (row.hasNext() && automationInputDTOList.size() > j) {
                    AutomationInputDTO automationInputDTO = automationInputDTOList.get(j);
                    // Iterating Row
                    Row currentRow = row.next();
                    Cell serialNumberCell =
                                    currentRow.getCell(headerIndexes.get(FastTestExcelHeaders.SERIAL_NO.getText()));
                    String serialNumber = extractValueFromCell(serialNumberCell);
                    if (serialNumber.equals(automationInputDTO.getSerialNo())) {
                        String actualOutput = automationInputDTO.getActualOutput();
                        Cell actualOutputColumnCell = createCell(currentRow,
                                        headerIndexes.get(FastTestExcelHeaders.ACTUAL_OUTPUT.getText()), styleBorder);
                        if (StringUtils.isNotBlank(actualOutput)) {
                            JsonParser jsonParser = new JsonParser();
                            try {
                                JsonElement outputObject = jsonParser.parse(actualOutput);
                                if (!outputObject.isJsonPrimitive()) {
                                    Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
                                    actualOutput = gson.toJson(outputObject);
                                }
                            } catch (Exception e) {
                                // no futher handling needed
                            }
                            actualOutputColumnCell.setCellValue(actualOutput);
                            actualOutputColumnCell.getCellStyle().setWrapText(true);
                        }
                        createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.ACTUAL_HTTP_STATUS.getText()),
                                        styleBorder).setCellValue(automationInputDTO.getActualHttpStatus());

                        // Setting BackGround color of TestCaseResult to Green for Pass and Red for Fail
                        IndexedColors bgColor = automationInputDTO.getTestCaseResult().equalsIgnoreCase(
                                        AutomationConstants.TEST_CASE_RESULT_PASS) ? IndexedColors.BRIGHT_GREEN
                                                        : IndexedColors.RED;
                        createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.TEST_CASE_RESULT.getText()),
                                        getCellStyleForBGColor(workbook, bgColor)).setCellValue(automationInputDTO.getTestCaseResult());
                        createCell(currentRow, headerIndexes.get(FastTestExcelHeaders.EXECUTION_DATE_TIME.getText()),
                                        getCellStyleForDate(workbook)).setCellValue(LocalDateTime.now());
                        j++;
                    } else {
                        rowToBeRemovedIndex.add(currentRow.getRowNum());
                    }
                }
                sheet.autoSizeColumn(headerIndexes.get(FastTestExcelHeaders.EXECUTION_DATE_TIME.getText()));
                for (ListIterator<Integer> iterator =
                                rowToBeRemovedIndex.listIterator(rowToBeRemovedIndex.size()); iterator.hasPrevious();) {
                    removeRow(sheet, iterator.previous());
                }
            }
        } catch (IOException e) {
            logger.debug("Exception Occured While Writing Excel {} ", ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Writing Excel", e);
        }
        logger.traceExit();
    }

    private CellStyle getCellStyleForDate(XSSFWorkbook workbook) {
        CellStyle cellStyleForDate = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyleForDate.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yyyy h:mm:ss.0"));
        return cellStyleForDate;
    }

    private CellStyle getCellStyleForBGColor(XSSFWorkbook workbook, IndexedColors bgColor) {
        CellStyle cellStyleForBGColor = workbook.createCellStyle();
        cellStyleForBGColor.setFillForegroundColor(bgColor.getIndex());
        cellStyleForBGColor.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);
        cellStyleForBGColor.setFont(boldFont);
        return cellStyleForBGColor;
    }
    
    public CellStyle getCellStyleForBold(XSSFWorkbook workbook) {
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);
        CellStyle cellStyleForHeaderRow = workbook.createCellStyle();
        // Creating cell Style i.e. Bold Font for Header ROW
        cellStyleForHeaderRow.setFont(boldFont);
        return cellStyleForHeaderRow;
    }

    public Map<String, Integer> getHeaderIndexes(Row headerRow) {
        Map<String, Integer> headerIndexes = new HashMap<>();
        Iterator<Cell> cell = headerRow.iterator();
        int i = 0;
        while (cell.hasNext()) {
            headerIndexes.put(cell.next().toString(), i++);
        }
        return headerIndexes;
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
    public void saveDataToExcel(String outputExcelSheetName, List<String> excelSheetNames) {
        logger.traceEntry("saveDataToExcel method of AutomationExcelUtility class");
        removeOtherSheets(excelSheetNames);
        FileOutputStream fos = null;
        try {
            String folderPathOfExcel = StringUtils.substringBeforeLast(outputExcelSheetName, "\\");
            if (!folderPathOfExcel.equals(outputExcelSheetName)) {
                Files.createDirectories(Paths.get(folderPathOfExcel));
            }
            fos = new FileOutputStream(outputExcelSheetName);
            workbook.write(fos);
        } catch (IOException e) {
            logger.debug("Exception Occured While Writing Excel {} ", ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Writing Excel", e);
        } finally {
            try {
                workbook.close();
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                logger.error("Exception Occured While Closing Excel", e);
            }
            workbook = null;
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
