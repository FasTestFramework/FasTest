package com.infogain.automation.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationFilePathAndNameDTO;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.utilities.AutomationExcelUtility;

@Service
public class AutomationFileDetailsService {

    private final AutomationProperties automationProperties;
    private final AutomationExcelUtility automationExcelUtility;

    private static final Logger logger = LogManager.getLogger(AutomationFileDetailsService.class);

    @Autowired
    public AutomationFileDetailsService(final AutomationProperties automationProperties,
                    final AutomationExcelUtility automationExcelUtility) {
        this.automationProperties = automationProperties;
        this.automationExcelUtility = automationExcelUtility;

    }

    public AutomationFilePathAndNameDTO dataname() {
        String inputJSonFolderPath =
                        automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_JSON_FOLDER_PATH);
        String inputExcelFolderPath =
                        automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_EXCEL_FOLDER_PATH);
        List<String> result = new ArrayList<>();

        try (Stream<Path> walk = Files.walk(Paths.get(inputExcelFolderPath))) {
            result = walk.filter(path -> Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".xlsx"))
                            .map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            throw new AutomationException("Exception Occured While Fetching excelfile names", e);
        }
        Map<String, List<String>> allExcelSheetsNames = new HashMap<>();
        for (String excelFilePath : result) {
            ArrayList<String> excelSheetNames = new ArrayList<>();
            XSSFWorkbook workbookInput = automationExcelUtility.readExcelFile(excelFilePath);
            for (int i = 0; i < workbookInput.getNumberOfSheets(); i++) {
                excelSheetNames.add(workbookInput.getSheetName(i));
            }
            automationExcelUtility.closeExcel(workbookInput);
            if (!excelSheetNames.isEmpty()) {
                allExcelSheetsNames.put(excelFilePath.substring(inputExcelFolderPath.length() + 1), excelSheetNames);
            }
        }
        return new AutomationFilePathAndNameDTO(inputJSonFolderPath, inputExcelFolderPath, allExcelSheetsNames);
    }

}
