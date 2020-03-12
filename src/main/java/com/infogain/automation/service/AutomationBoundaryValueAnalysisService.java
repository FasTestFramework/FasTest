package com.infogain.automation.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.constants.RandomGenerationMetadataEnum;
import com.infogain.automation.dto.AutomationBVAMetadataDTO;
import com.infogain.automation.dto.AutomationBoundaryValueAnalysisDTO;
import com.infogain.automation.dto.AutomationBoundaryValueAnalysisResponseDTO;
import com.infogain.automation.dto.AutomationRandomGenerationMetadataDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.dto.Pair;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.mapper.AutomationBoundaryValueAnalysisDTOtoModel;
import com.infogain.automation.model.AutomationBoundaryValueAnalysisModel;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.utilities.AutomationJsonUtility;
import com.infogain.automation.utilities.AutomationRandomUtility;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class creates boundary value analysis jsons
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Mar 3, 2020
 */
@Service
public class AutomationBoundaryValueAnalysisService {

    private static final int MAX_FILE_PATH_LENGTH = 200;
    private static final String JSON_EXTENSION = ".json";
    private final AutomationProperties automationProperties;
    private final AutomationRandomUtility automationRandomUtility;
    private final AutomationJsonUtility automationJsonUtility;
    private String directory;
    private String jsonFileName;

    @Autowired
    public AutomationBoundaryValueAnalysisService(final AutomationProperties automationProperties,
                    final AutomationRandomUtility automationRandomUtility,
                    final AutomationJsonUtility automationJsonUtility) {
        this.automationProperties = automationProperties;
        this.automationRandomUtility = automationRandomUtility;
        this.automationJsonUtility = automationJsonUtility;
    }

    public Map<String, AutomationRandomGenerationMetadataDTO> getBoundaryValueAnalysis() {
        Map<String, AutomationRandomGenerationMetadataDTO> metadata = new LinkedHashMap<>();
        for (RandomGenerationMetadataEnum metadataEnum : RandomGenerationMetadataEnum.values()) {
            Pair<String, AutomationRandomGenerationMetadataDTO> pairEnum = metadataEnum.getData();
            metadata.put(pairEnum.getFirst(), pairEnum.getSecond());
        }
        return metadata;
    }

    public AutomationBoundaryValueAnalysisResponseDTO getBoundaryValueAnalysis(
                    AutomationBoundaryValueAnalysisDTO automationBoundaryValueAnalysisDTO) {
        AutomationBoundaryValueAnalysisModel automationBoundaryValueAnalysisModel =
                        AutomationBoundaryValueAnalysisDTOtoModel.convert(automationBoundaryValueAnalysisDTO);
        directory = automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_JSON_FOLDER_PATH) + "/"
                        + automationBoundaryValueAnalysisModel.getFolderName() + "/";
        jsonFileName = automationBoundaryValueAnalysisModel.getFileName();
        if (jsonFileName.matches("^.*\\.(json|JSON)$")) {
            jsonFileName = jsonFileName.substring(0, jsonFileName.length() - JSON_EXTENSION.length());
        }
        int filesCreatedCount = generateJsons(automationBoundaryValueAnalysisModel.getData(),
                        automationBoundaryValueAnalysisModel.getMetaData());

        return new AutomationBoundaryValueAnalysisResponseDTO(filesCreatedCount + " Files Created at " + directory);
    }

    private int generateJsons(JSONAware jsonbody, List<AutomationBVAMetadataDTO> metaDataList) {
        List<ErrorCodesDTO> errorCodesList = new ArrayList<>();
        Map<String, String> fileNameWihData = new LinkedHashMap<>();
        for (int i = 0; i < metaDataList.size(); i++) {
            AutomationBVAMetadataDTO metaData = metaDataList.get(i);
            Map<Object, String> newValues = new LinkedHashMap<>();
            Double max = metaData.getMax();
            Double min = metaData.getMin();
            Integer length = metaData.getLength();
            String keyName = metaData.getKeyName();
            switch (metaData.getType()) {
                case INTEGER:
                    Long value;
                    if (max != null && min != null && max - min < 1) {
                        value = min.longValue();
                        newValues.put(value - 1, keyName + "(min-)_invalid");
                        newValues.put(value, keyName + "(min)");
                        newValues.put(max.longValue() + 1, keyName + "(max+)_invalid");
                    } else {
                        if (max != null) {
                            value = max.longValue();
                            newValues.put(value - 1, keyName + "(max-)");
                            newValues.put(value, keyName + "(max)");
                            newValues.put(value + 1, keyName + "(max+)_invalid");
                        }
                        if (min != null) {
                            value = min.longValue();
                            newValues.put(value - 1, keyName + "(min-)_invalid");
                            newValues.put(value, keyName + "(min)");
                            newValues.put(value + 1, keyName + "(min+)");
                        }
                    }
                    break;
                case DOUBLE:
                    if (max != null && min != null && max - min < 1) {
                        newValues.put(min - 1, keyName + "(min-)_invalid");
                        newValues.put(max, keyName + "(max)");
                        newValues.put(min, keyName + "(min)");
                        newValues.put(max + 1, keyName + "(max+)_invalid");
                    } else {
                        if (max != null) {
                            newValues.put(max - 1, keyName + "(max-)");
                            newValues.put(max, keyName + "(max)");
                            newValues.put(max + 1, keyName + "(max+)_invalid");
                        }
                        if (min != null) {
                            newValues.put(min - 1, keyName + "(min-)_invalid");
                            newValues.put(min, keyName + "(min)");
                            newValues.put(min + 1, keyName + "(min+)");
                        }
                    }
                    break;
                default:
                case STRING:
                    if (length != null) {
                        newValues.put(automationRandomUtility.generateRandomStringSmallLetters(length - 1),
                                        keyName + "(length-)_invalid");
                        newValues.put(automationRandomUtility.generateRandomStringSmallLetters(length),
                                        keyName + "(length)");
                        newValues.put(automationRandomUtility.generateRandomStringSmallLetters(length + 1),
                                        keyName + "(length+)_invalid");
                    }
            }
            try {
                automationJsonUtility.setValueandGetJSONStringForLeafNodes(jsonbody, keyName, newValues,
                                fileNameWihData);
            } catch (IllegalArgumentException e) {
                errorCodesList.add(new ErrorCodesDTO(AutomationErrorCodes.AUTOMATION_BVA_INVALID_DATA_EXCEPTION,
                                "metaData[" + i + "].keyName", keyName, e.getMessage()));
            }
        }
        if (!errorCodesList.isEmpty()) {
            throw new FastTestBadRequestException(errorCodesList);
        }
        return saveJson(fileNameWihData);
    }

    private int saveJson(Map<String, String> fileNameWihDataMap) {
        String directoryPathsCreated = createDirectoryStructureAndReturnDirectoryPaths(directory);
        List<String> fileNameArray = new ArrayList<>();
        for (Entry<String, String> fileNameWihData : fileNameWihDataMap.entrySet()) {
            String jsonFilePathsStr = getNewFileNameIfFileExists(
                            directory + jsonFileName + "_" + fileNameWihData.getKey(), JSON_EXTENSION);
            try (FileWriter fileWriter = new FileWriter(jsonFilePathsStr)) {
                fileWriter.write(fileNameWihData.getValue());
                fileWriter.flush();
                fileNameArray.add(jsonFilePathsStr);
            } catch (IOException e) {
                fileNameArray.forEach(name -> {
                    try {
                        Files.delete(Paths.get(name));
                    } catch (Exception e1) {
                        // No handling needed
                    }
                });
                try {
                    FileUtils.forceDelete(new File(directoryPathsCreated));
                } catch (IOException e1) {
                    // No handling needed
                }
            }
        }
        return fileNameArray.size();
    }

    private String createDirectoryStructureAndReturnDirectoryPaths(String directoryName) {
        File dir = new File(directoryName);
        String parentDirectory = directoryName;
        String dirNameCreated = parentDirectory;
        while (true) {
            parentDirectory = dir.getParent();
            dir = new File(parentDirectory);
            if (dir.exists()) {
                dir = new File(directoryName);
                break;
            }
            dirNameCreated = parentDirectory;
        }
        if (!dir.exists()) {
            new File(directoryName).mkdirs();
        }
        return dirNameCreated;
    }

    private String getNewFileNameIfFileExists(String filePath, String extension) {
        if (filePath.length() > MAX_FILE_PATH_LENGTH) {
            filePath = StringUtils.abbreviate(filePath, MAX_FILE_PATH_LENGTH);
        }
        while (Files.exists(Paths.get(filePath + extension))) {
            if (filePath.endsWith(" - Copy")) {
                filePath = filePath + " (1)";
            } else {
                Pattern pattern = Pattern.compile("^(.* - Copy )\\((\\d+)\\)$", Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(filePath);
                long copyCount = 0;
                String filePathWithoutCopy = null;
                while (matcher.find()) {
                    filePathWithoutCopy = matcher.group(1);
                    copyCount = Long.parseLong(matcher.group(2));
                }
                filePath = (filePathWithoutCopy != null && copyCount != 0)
                                ? filePathWithoutCopy + "(" + (copyCount + 1) + ")"
                                : filePath + " - Copy";
            }
        }
        return filePath + extension;
    }

}
