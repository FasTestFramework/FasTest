package com.infogain.automation.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ooxml.POIXMLException;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

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
    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationExcelUtility(final AutomationProperties automationProperties,
                    final AutomationUtility automationUtility) {
        this.automationProperties = automationProperties;
        this.automationUtility = automationUtility;
    }


    public XSSFWorkbook readExcelFile(String filePath) {
        XSSFWorkbook workbookInput;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbookInput = new XSSFWorkbook(fis);
        } catch (POIXMLException | IOException e) {
            logger.debug("Exception Occured While Reading Excel {} ", ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Reading Excel", e);
        }
        return workbookInput;
    }


    public XSSFWorkbook createExcelFile() {
        try {
            return new XSSFWorkbook();
        } catch (POIXMLException e) {
            logger.debug("Exception Occured While creating new existing Excel {} ", ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While creating Excel File.", e);
        }
    }

    /**
     * This method read Data from Input Excel File and set it to Automation Input DTO List of {@link AutomationInputDTO}
     * 
     * @param excelSheetName Input Excel File Name
     * @param sheetName Excel Sheet Name
     * @throws AutomationException in Writing Data to Excel File
     * @return object of list {@link AutomationInputDTO}
     */
    public List<AutomationInputDTO> readInputExcelFile(XSSFWorkbook workbookInput, String fileName, String sheetName) {
        logger.traceEntry("readInputExcelFile method of AutomationExcelUtility class");
        String requestUrl;
        String requestType;
        ArrayList<AutomationInputDTO> jsonFileList = new ArrayList<>();
        XSSFSheet sheet = workbookInput.getSheet(sheetName);
        if (sheet != null) {
            Iterator<Row> row = sheet.iterator();
            Row headerRow = row.next();
            Map<FastTestExcelHeaders, Integer> headerIndexes = getHeaderIndexes(headerRow);
            while (row.hasNext()) {
                Row currentRow = headerRow;
                if (!checkIfRowIsEmpty(currentRow)
                                && !fetchCellValue(headerIndexes, currentRow, FastTestExcelHeaders.SKIP_TEST)
                                                .equals("Y")) {
                    String serialNumber = fetchCellValue(headerIndexes, currentRow, FastTestExcelHeaders.SERIAL_NO);
                    String testCaseDescription = fetchCellValue(headerIndexes, currentRow,
                                    FastTestExcelHeaders.TEST_CASE_DESCRIPTION);
                    String endpoint = fetchCellValue(headerIndexes, currentRow, FastTestExcelHeaders.URL_PARAMETER);
                    Integer expectedHttpStatus = extractIntegerOfString(fetchCellValue(headerIndexes, currentRow,
                                    FastTestExcelHeaders.EXPECTED_HTTP_STATUS));
                    if (StringUtils.isNotBlank(serialNumber) && StringUtils.isNotBlank(testCaseDescription)
                                    && StringUtils.isNotBlank(endpoint) && expectedHttpStatus != null) {
                        Map<String, String> keyValidationMap =
                                        extractingCustomKeyValidations(fetchCellValue(headerIndexes, currentRow,
                                                        FastTestExcelHeaders.KEYS_VALIDATION));
                        String headerJson = fetchCellValue(headerIndexes, currentRow, FastTestExcelHeaders.HEADERS);
                        String inputJson = fetchCellValue(headerIndexes, currentRow, FastTestExcelHeaders.INPUT_JSON);
                        String expectedOutput =
                                        fetchCellValue(headerIndexes, currentRow, FastTestExcelHeaders.EXPECTED_OUTPUT);
                        String inputParam = fetchCellValue(headerIndexes, currentRow, FastTestExcelHeaders.PARAMS);
                        if (!endpoint.contains("|")) {
                            endpoint = automationProperties
                                            .getProperty(AutomationConstants.FASTEST_URL_PREFIX + endpoint);
                        }
                        String[] printJsonReceiptUrlArray = endpoint.split("|");
                        // Splitting the Request Url and Request Type which are defined in properties file
                        requestUrl = printJsonReceiptUrlArray[0];
                        requestType = printJsonReceiptUrlArray[1];
                        AutomationInputDTO automationInput = new AutomationInputDTO(serialNumber, testCaseDescription,
                                        headerJson, inputJson, inputParam, expectedOutput, expectedHttpStatus,
                                        requestUrl, getRequestMethodType(requestType), keyValidationMap);
                        jsonFileList.add(automationInput);

                    } else {
                        throw new AutomationException("Excel Sheet " + sheetName + " in file " + fileName
                                        + " has some missing or invalid inputs");
                    }
                }
            }
        }
        return jsonFileList;
    }

    public Map<FastTestExcelHeaders, Integer> generateHeaderRow(XSSFWorkbook workbookInput, XSSFSheet sheet,
                    Row headerRow) {
        Map<FastTestExcelHeaders, Integer> headerIndexes = getHeaderIndexes(headerRow);
        int lastCellNum = headerRow.getLastCellNum();
        int i = lastCellNum >= 0 ? lastCellNum - 1 : lastCellNum;
        FastTestExcelHeaders[] headersValues = FastTestExcelHeaders.values();
        if (headerIndexes.size() != headersValues.length) {
            for (FastTestExcelHeaders header : headersValues) {
                if (!headerIndexes.containsKey(header)) {
                    headerIndexes.put(header, ++i);
                    Cell cell = createCell(headerRow, i);
                    setStyle(cell, getCellStyleForBGColorWithBold(workbookInput, IndexedColors.LIGHT_CORNFLOWER_BLUE),
                                    BorderStyle.THICK);
                    cell.setCellValue(header.getName());
                    sheet.setColumnWidth(headerIndexes.get(header), header.getWidth());
                }
            }
        }
        sheet.createFreezePane(0, 1);
        return headerIndexes;
    }

    private String fetchCellValue(Map<FastTestExcelHeaders, Integer> headerIndexes, Row currentRow,
                    FastTestExcelHeaders headers) {
        String cellValue = "";
        Integer cellIndex = headerIndexes.get(headers);
        if (cellIndex != null) {
            cellValue = extractValueFromCell(currentRow.getCell(cellIndex));
        }
        return cellValue;
    }

    private Integer extractIntegerOfString(String text) {
        Integer integer = null;
        try {
            integer = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // do nothing
        }
        return integer;
    }

    private HttpMethod getRequestMethodType(String requestType) {
        try {
            return HttpMethod.valueOf(requestType.toUpperCase());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new AutomationException("Wrong Http Method is defined in Properties file i.e. " + requestType);
        }
    }

    private String extractValueFromCell(Cell serialNumberCell) {
        String serialNumber;
        if (serialNumberCell == null) {
            serialNumber = "";
        } else if (serialNumberCell.getCellType().equals(CellType.NUMERIC)) {
            double numericCellValue = serialNumberCell.getNumericCellValue();
            serialNumber = String.valueOf(numericCellValue % 1 == 0 ? (int) numericCellValue : numericCellValue);
        } else {
            serialNumber = serialNumberCell.toString();
        }
        return serialNumber;
    }

    private Map<String, String> extractingCustomKeyValidations(String keyValdiationsString) {
        Map<String, String> keyValidationMap = new HashMap<>();
        if (StringUtils.isNotBlank(keyValdiationsString)) {
            String[] allKeyAndValues = keyValdiationsString.split("\\|");
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
    private void removeOtherSheets(XSSFWorkbook workbook, List<String> excelSheetNames) {
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
    public void writeOutputExcelFile(XSSFWorkbook workbook, String sheetName,
                    List<AutomationInputDTO> automationInputDTOList) {
        logger.traceEntry("writeOutputExcelFile method of AutomationExcelUtility class");
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if (sheet != null) {
            ArrayList<Integer> rowToBeRemovedIndex = new ArrayList<>();
            Iterator<Row> row = sheet.iterator();
            Map<FastTestExcelHeaders, Integer> headerIndexes = generateHeaderRow(workbook, sheet, row.next());
            int j = 0;
            Map<FastTestExcelHeaders, Object> cellData = new EnumMap<>(FastTestExcelHeaders.class);
            while (row.hasNext() && automationInputDTOList.size() > j) {
                AutomationInputDTO automationInputDTO = automationInputDTOList.get(j);
                // Iterating Row
                Row currentRow = row.next();
                String serialNumber = fetchCellValue(headerIndexes, currentRow, FastTestExcelHeaders.SERIAL_NO);
                if (serialNumber.equals(automationInputDTO.getSerialNo())) {
                    cellData.clear();
                    cellData.put(FastTestExcelHeaders.ACTUAL_OUTPUT, automationInputDTO.getActualOutput());
                    cellData.put(FastTestExcelHeaders.ACTUAL_HTTP_STATUS, automationInputDTO.getActualHttpStatus());
                    cellData.put(FastTestExcelHeaders.TEST_CASE_RESULT, automationInputDTO.getTestCaseResult());
                    cellData.put(FastTestExcelHeaders.EXECUTION_DATE_TIME, LocalDateTime.now());

                    insertRowData(workbook, currentRow, headerIndexes, cellData);
                    j++;
                } else {
                    rowToBeRemovedIndex.add(currentRow.getRowNum());
                }
            }
            for (ListIterator<Integer> iterator = rowToBeRemovedIndex.listIterator(rowToBeRemovedIndex.size()); iterator
                            .hasPrevious();) {
                removeRow(sheet, iterator.previous());
            }
        }
        logger.traceExit();
    }

    private CellStyle getCellStyleForDate(XSSFWorkbook workbook) {
        CellStyle cellStyleForDate = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyleForDate.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yyyy h:mm:ss.0"));
        return cellStyleForDate;
    }

    private CellStyle getCellStyleForBGColorWithBold(XSSFWorkbook workbook, IndexedColors bgColor) {
        CellStyle boldWithBgColor = workbook.createCellStyle();
        boldWithBgColor.setFillForegroundColor(bgColor.getIndex());
        boldWithBgColor.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return getCellStyleForBold(boldWithBgColor, workbook);
    }

    public CellStyle getCellStyleForBold(CellStyle cellStyleForHeaderRow, XSSFWorkbook workbook) {
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);
        cellStyleForHeaderRow.setFont(boldFont);
        return cellStyleForHeaderRow;
    }

    public Map<FastTestExcelHeaders, Integer> getHeaderIndexes(Row headerRow) {
        EnumMap<FastTestExcelHeaders, Integer> headerIndexes = new EnumMap<>(FastTestExcelHeaders.class);
        Iterator<Cell> cell = headerRow.iterator();
        int i = 0;
        while (cell.hasNext()) {
            FastTestExcelHeaders header = FastTestExcelHeaders.getEnumValueByText(extractValueFromCell(cell.next()));
            if (header != null) {
                headerIndexes.put(header, i);
            }
            i++;
        }
        return headerIndexes;
    }

    /**
     * This method is to set Border Style in Output Excel File
     * 
     * @param styleBorder - Style which needs to be apply on Border in Excel File
     * @since Nov 27, 2019
     */
    private void setBorders(CellStyle styleBorder, BorderStyle borderStyle) {
        logger.traceEntry("setBorders method of AutomationExcelUtility class");
        styleBorder.setBorderBottom(borderStyle);
        styleBorder.setBorderLeft(borderStyle);
        styleBorder.setBorderRight(borderStyle);
        styleBorder.setBorderTop(borderStyle);
        logger.traceExit();
    }

    /**
     * This method writes Data to Excel Output File
     * 
     * @param outputExcelSheetName Output Excel Sheet Name
     * @since Nov 27, 2019
     */
    public void generateExcel(XSSFWorkbook workbook, String outputExcelSheetName, List<String> excelSheetNames) {
        logger.traceEntry("saveDataToExcel method of AutomationExcelUtility class");
        removeOtherSheets(workbook, excelSheetNames);
        String folderPathOfExcel = StringUtils.substringBeforeLast(outputExcelSheetName, "\\");
        if (!folderPathOfExcel.equals(outputExcelSheetName)) {
            try {
                Files.createDirectories(Paths.get(folderPathOfExcel));
            } catch (IOException e) {
                throw new AutomationException("Unable to create directory " + folderPathOfExcel, e);
            }
        }
        saveDataToExcel(outputExcelSheetName, workbook);
        logger.traceExit();
    }

    public void saveDataToExcel(String outputExcelFilePath, XSSFWorkbook workbookInput) {
        logger.traceEntry("saveDataToExcel method of AutomationExcelUtility class");
        try (FileOutputStream fos = new FileOutputStream(outputExcelFilePath)) {
            workbookInput.write(fos);
        } catch (POIXMLException | IOException e) {
            logger.debug("Exception Occured While Writing Excel  as the file is OPEN{} ",
                            ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Writing Excel", e);
        } finally {
            closeExcel(workbookInput);
        }
        logger.traceExit();
    }


    public void closeExcel(XSSFWorkbook workbookInput) {
        try {
            workbookInput.close();
        } catch (IOException e) {
            logger.error("Exception Occured While Closing Excel", e);
        }
    }

    public void insertRowData(XSSFWorkbook workbook, Row row, Map<FastTestExcelHeaders, Integer> headerIndexes,
                    Map<FastTestExcelHeaders, Object> cellData) {
        cellData.forEach((columnName, data) -> {
            Cell cell = createCell(row, headerIndexes.get(columnName));
            XSSFCellStyle defaultCellStyle = workbook.createCellStyle();
            switch (columnName) {
                case TEST_CASE_DESCRIPTION:
                case SKIP_TEST:
                case URL_PARAMETER:
                case PARAMS:
                case KEYS_VALIDATION:
                case FAILURES:
                case COMMENTS:
                    setStyle(cell, defaultCellStyle);
                    if (data != null) {
                        cell.setCellValue((String) data);
                    }
                    break;
                case HEADERS:
                case INPUT_JSON:
                case EXPECTED_OUTPUT:
                case ACTUAL_OUTPUT:
                    setStyle(cell, defaultCellStyle);
                    if (data != null) {
                        cell.setCellValue(automationUtility.beautifyJson((String) data));
                    }
                    break;
                case SERIAL_NO:
                case EXPECTED_HTTP_STATUS:
                case ACTUAL_HTTP_STATUS:
                    setStyle(cell, defaultCellStyle);
                    if (data != null) {
                        cell.setCellValue((Integer) data);
                    }
                    break;
                case TEST_CASE_RESULT:
                    String value = (String) data;
                    if (data != null) {
                        IndexedColors bgColor = value.equalsIgnoreCase(AutomationConstants.TEST_CASE_RESULT_PASS)
                                        ? IndexedColors.BRIGHT_GREEN
                                        : IndexedColors.RED;
                        setStyle(cell, getCellStyleForBGColorWithBold(workbook, bgColor));
                        cell.setCellValue(value);
                    } else {
                        setStyle(cell, defaultCellStyle);
                    }
                    break;
                case EXECUTION_DATE_TIME:
                    LocalDateTime dateTime = (LocalDateTime) data;
                    setStyle(cell, getCellStyleForDate(workbook));
                    if (data != null) {
                        cell.setCellValue(dateTime);
                    }
                default:
            }
        });
    }

    private Cell setStyle(Cell cell, CellStyle style) {
        return setStyle(cell, style, BorderStyle.THIN);
    }

    private Cell setStyle(Cell cell, CellStyle style, BorderStyle borderStyle) {
        logger.traceEntry("createCell method of AutomationExcelUtility class");
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorders(style, borderStyle);
        style.setWrapText(true);
        cell.setCellStyle(style);
        return logger.traceExit(cell);
    }

    private Cell createCell(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            cell = row.createCell(columnIndex);
        }
        return cell;
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
