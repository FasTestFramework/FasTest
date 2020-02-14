package com.infogain.automation.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationExcelInputDTO;
import com.infogain.automation.dto.AutomationRequestDTO;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;

@Service
public class AutomationInputExcelService {

    private static final Logger logger = LogManager.getLogger(AutomationInputExcelService.class);
    private Properties automationProperties;

    @Autowired
    public AutomationInputExcelService(final AutomationProperties automationProperties) {
        this.automationProperties = automationProperties.getProps();
    }

    public void insertDataToExcel(AutomationRequestDTO automationRequestDTO) {
        String inputExcelName = automationRequestDTO.getInputExcelFileName();
        String inputExcelSheetName = automationRequestDTO.getInputExcelSheetName();
        String inputExcelFilePath =
                        automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_FOLDER_PATH) + "/"
                                        + inputExcelName;

        File excelFile = new File(inputExcelFilePath);
        if (excelFile.exists() && !excelFile.isDirectory()) {
            try (FileInputStream fis = new FileInputStream(excelFile);
                            XSSFWorkbook workbookInput = new XSSFWorkbook(fis);) {
                CellStyle styleBorder = workbookInput.createCellStyle();
                setBorders(styleBorder);
                int sheets = workbookInput.getNumberOfSheets();
                logger.info("Total Number of sheets  {} in {}", sheets, inputExcelName);

                for (int i = 0; i < sheets; i++) {
                    if (workbookInput.getSheetName(i).equalsIgnoreCase(inputExcelSheetName)) {
                        XSSFSheet sheet = workbookInput.getSheetAt(i);
                        insertDataInExcelSheet(automationRequestDTO, styleBorder, sheet);
                        saveDataToExcel(inputExcelFilePath, workbookInput);

                    }
                }
                if (null == workbookInput.getSheet(inputExcelSheetName)) {
                    createNewSheet(automationRequestDTO, inputExcelSheetName, workbookInput);
                }
            } catch (IOException e) {
                logger.debug("Exception Occured While Writing in existing Excel {} ", ExceptionUtils.getStackTrace(e));
                throw new AutomationException("Exception Occured While Writing Excel", e);
            }
        } else {
            XSSFWorkbook workbookInput = new XSSFWorkbook();
            createNewSheet(automationRequestDTO, inputExcelSheetName, workbookInput);

        }
        logger.info("Data entered in Excel Successfully");
        logger.traceExit();
    }

    private void createNewSheet(AutomationRequestDTO automationRequestDTO, String inputExcelSheetName,
                    XSSFWorkbook workbookInput) {
        CellStyle styleBorder;
        String inputExcelName = automationRequestDTO.getInputExcelFileName();
        String inputExcelFilePath =
                        automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_FOLDER_PATH) + "/"
                                        + inputExcelName;
        XSSFSheet sheet = workbookInput.createSheet(inputExcelSheetName);
        styleBorder = workbookInput.createCellStyle();
        setBorders(styleBorder);
        int rowCount = 0;
        int columnCount = 0;
        Row row = sheet.createRow(rowCount);
        ArrayList<Object> headerRow = new ArrayList<>();
        headerRow.add("Sno");
        headerRow.add("Test case Description");
        headerRow.add("URL Parameter");
        headerRow.add("Header Json");
        headerRow.add("Param");
        headerRow.add("Input json");
        headerRow.add("Expected Output");
        headerRow.add("Expected http status");
        headerRow.add("Actual Output");
        headerRow.add("Actual http status");
        headerRow.add("Test case Result");
        headerRow.add("Execution date time");

        XSSFFont boldFont = workbookInput.createFont();
        boldFont.setBold(true);
        CellStyle cellStyleForHeaderRow = workbookInput.createCellStyle();
        // Creating cell Style i.e. Bold Font for Header ROW
        Cell cell = row.createCell(++columnCount);
        cellStyleForHeaderRow.setFont(boldFont);
        for (int i = 0; i < 12; i++) {

            cell = createCell(row, i, cellStyleForHeaderRow);
            setHeaderColumnNameInCell(headerRow.get(i), cell);
        }

        insertDataInExcelSheet(automationRequestDTO, styleBorder, sheet);
        saveDataToExcel(inputExcelFilePath, workbookInput);
    }

    private void insertDataInExcelSheet(AutomationRequestDTO automationRequestDTO, CellStyle styleBorder,
                    XSSFSheet sheet) {
        Iterator<Row> row = sheet.iterator();
        int totalRows = sheet.getLastRowNum() + 1;
        Row firstRow = row.next();
        Iterator<Cell> cell = firstRow.iterator();
        int serialNumberColumn = 0;
        int testCaseDescriptionColumn = 0;
        int urlParameterColumn = 0;
        int headerJsonColumn = 0;
        int inputJsonColumn = 0;
        int expectedOutputColumn = 0;
        int expectedHttpStatusColumn = 0;
        int actualOutputColumn = 0;
        int actualHttpStatusColumn = 0;
        int testCaseResultColumn = 0;
        int paramColumn = 0;
        int cellNumber = 0;

        while (cell.hasNext()) {
            Cell c = cell.next();
            CellStyle cellStyleBorder = c.getCellStyle();
            setBorders(cellStyleBorder);
            c.setCellStyle(cellStyleBorder);
            switch (c.getStringCellValue()) {
                case AutomationConstants.SERIAL_NO_COLUMN_NAME:
                    serialNumberColumn = cellNumber;
                    break;

                case AutomationConstants.TEST_CASE_DESCRIPTION:
                    testCaseDescriptionColumn = cellNumber;
                    break;

                case AutomationConstants.URL_PARAMETER:
                    urlParameterColumn = cellNumber;
                    break;

                case AutomationConstants.HEADER_JSON:
                    headerJsonColumn = cellNumber;
                    break;

                case AutomationConstants.INPUT_JSON:
                    inputJsonColumn = cellNumber;
                    break;

                case AutomationConstants.EXPECTED_OUTPUT:
                    expectedOutputColumn = cellNumber;
                    break;

                case AutomationConstants.EXPECTED_HTTP_STATUS:
                    expectedHttpStatusColumn = cellNumber;
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
                case AutomationConstants.PARAM:
                    paramColumn = cellNumber;
                    break;

                default:
            }
            cellNumber++;
        }
        int j = 0;
        int lastSNoCount = totalRows - 1;
        while (automationRequestDTO.getAutomationExcelInputDTO().size() > j) {
            AutomationExcelInputDTO automationExcelInputDTO = automationRequestDTO.getAutomationExcelInputDTO().get(j);
            Row currentRow = sheet.getRow(totalRows); // Last row of existing sheet

            if (currentRow == null) {
                currentRow = sheet.createRow(totalRows);
                Cell serialNumberCell = createCell(currentRow, serialNumberColumn, styleBorder);
                serialNumberCell.setCellValue(Integer.parseInt(automationExcelInputDTO.getSerialNo()) + lastSNoCount);
                serialNumberCell.getCellStyle().setWrapText(true);

                Cell testCaseDescriptionCell = createCell(currentRow, testCaseDescriptionColumn, styleBorder);
                testCaseDescriptionCell.setCellValue(automationExcelInputDTO.getTestCaseDescription());
                testCaseDescriptionCell.getCellStyle().setWrapText(true);

                Cell urlParameterCell = createCell(currentRow, urlParameterColumn, styleBorder);
                urlParameterCell.setCellValue(automationExcelInputDTO.getUrlParameter());
                urlParameterCell.getCellStyle().setWrapText(true);

                Cell headerJsonCell = createCell(currentRow, headerJsonColumn, styleBorder);
                headerJsonCell.setCellValue(automationExcelInputDTO.getHeaderJson());
                headerJsonCell.getCellStyle().setWrapText(true);

                Cell inputJsonCell = createCell(currentRow, inputJsonColumn, styleBorder);
                inputJsonCell.setCellValue(automationExcelInputDTO.getInputJson());
                inputJsonCell.getCellStyle().setWrapText(true);

                Cell expectedOutputCell = createCell(currentRow, expectedOutputColumn, styleBorder);
                expectedOutputCell.setCellValue(automationExcelInputDTO.getExpectedOutput());
                expectedOutputCell.getCellStyle().setWrapText(true);

                Cell expectedHttpStatusCell = createCell(currentRow, expectedHttpStatusColumn, styleBorder);
                expectedHttpStatusCell.setCellValue(automationExcelInputDTO.getExpectedHttpStatus());
                expectedHttpStatusCell.getCellStyle().setWrapText(true);

                Cell actualOutputCell = createCell(currentRow, actualOutputColumn, styleBorder);
                actualOutputCell.setCellValue(automationExcelInputDTO.getActualOutput());
                actualOutputCell.getCellStyle().setWrapText(true);

                Cell actualHttpStatusCell = createCell(currentRow, actualHttpStatusColumn, styleBorder);
                actualHttpStatusCell.setCellValue(automationExcelInputDTO.getActualHttpStatus());
                actualHttpStatusCell.getCellStyle().setWrapText(true);

                Cell testCaseResultCell = createCell(currentRow, testCaseResultColumn, styleBorder);
                testCaseResultCell.setCellValue(automationExcelInputDTO.getTestCaseResult());
                testCaseResultCell.getCellStyle().setWrapText(true);

                Cell paramCell = createCell(currentRow, paramColumn, styleBorder);
                paramCell.setCellValue(automationExcelInputDTO.getParam());
                paramCell.getCellStyle().setWrapText(true);

                j++;
                totalRows++;
            }
        }
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

    public void setHeaderColumnNameInCell(Object field, Cell cell) {
        if (field instanceof String) {
            cell.setCellValue((String) field);
        } else if (field instanceof Integer) {
            cell.setCellValue((Integer) field);
        }
    }

    public void saveDataToExcel(String inputExcelFilePath, XSSFWorkbook workbookInput) {
        logger.traceEntry("saveDataToExcel method of AutomationExcelUtility class");

        try (FileOutputStream fos = new FileOutputStream(inputExcelFilePath)) {
            workbookInput.write(fos);
            workbookInput.close();
            workbookInput = null;
        } catch (IOException e) {
            logger.debug("Exception Occured While Writing Excel  as the file is OPEN{} ",
                            ExceptionUtils.getStackTrace(e));
            throw new AutomationException("Exception Occured While Writing Excel", e);
        }
        logger.traceExit();
    }

}
