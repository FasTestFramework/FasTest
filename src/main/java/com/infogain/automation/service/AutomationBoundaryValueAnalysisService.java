package com.infogain.automation.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationBoundaryValueAnalysisJsonDTO;
import com.infogain.automation.mapper.AutomationBoundaryValueAnalysisStringToJson;
import com.infogain.automation.properties.AutomationProperties;
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

    private static final String JSON_EXTENSION = ".json";
    private static final String LENGTH = "Length";
    private static final String MAX = "Max";
    private static final String MIN = "Min";
    private static final String KEY_NAME = "KeyName";
    private static final String STRING = "String";
    private static final String TYPE = "Type";
    private StringBuilder fileName = new StringBuilder();
    private final AutomationProperties automationProperties;
    private final AutomationRandomUtility automationRandomUtility;
    private AutomationBoundaryValueAnalysisJsonDTO automationBoundaryValueAnalysisJsonDTO;
    int counter;
    String directory;
    List<StringBuilder> fileNameArray;

    @Autowired
    public AutomationBoundaryValueAnalysisService(final AutomationProperties automationProperties,
                    final AutomationRandomUtility automationRandomUtility) {
        this.automationProperties = automationProperties;
        this.automationRandomUtility = automationRandomUtility;
    }

    public String getBoundaryValueAnalysis(String jsonString) {
        int jsonMetaDataCounter = 0;
        counter = 0;
        fileNameArray = new ArrayList<StringBuilder>();
        automationBoundaryValueAnalysisJsonDTO = AutomationBoundaryValueAnalysisStringToJson.stringToJson(jsonString);
        traverser(jsonMetaDataCounter, automationBoundaryValueAnalysisJsonDTO.getDataObject(),
                        automationBoundaryValueAnalysisJsonDTO.getJsonMetaDataArray(), fileName);
        return counter + " Files Created at " + directory;
    }

    private void traverser(int jsonMetaDataCounter, JSONObject jsonObject, JSONArray jsonArray,
                    StringBuilder fileName) {
        if (jsonMetaDataCounter != jsonArray.size()) {
            JSONObject currentObject = (JSONObject) jsonArray.get(jsonMetaDataCounter);
            int conditionsCount = currentObject.get(TYPE).equals(STRING) ? 3 : 6;
            String keyName = (String) currentObject.get(KEY_NAME);
            for (int index = 0; index < conditionsCount; index++) {
                getJsonObject(currentObject, jsonObject, conditionsCount, index, keyName);
                if (jsonMetaDataCounter < jsonArray.size() - 1) {
                    jsonMetaDataCounter++;
                    traverser(jsonMetaDataCounter, jsonObject, jsonArray, fileName);
                    jsonMetaDataCounter--;
                } else {
                    saveJson(jsonObject, fileName);
                    counter++;
                }
                int lastIndex = fileName.lastIndexOf("_");
                fileName.delete(lastIndex - keyName.length(), fileName.length());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void getJsonObject(JSONObject currentObject, JSONObject jsonObject, int conditionsCount, int index,
                    String keyName) {
        Long value;
        fileName = fileName.append(keyName);
        if (conditionsCount == 6) {
            value = index <= 2 ? (Long) currentObject.get(MIN) : (Long) currentObject.get(MAX);
            value = operationToPerform(index, value, fileName);
            jsonObject.replace(keyName, value);
        } else if (conditionsCount == 3) {
            value = (Long) currentObject.get(LENGTH);
            String random = getRandomString(index, value, fileName);
            jsonObject.replace(keyName, random);
        }
    }

    private Long operationToPerform(int index, Long value, StringBuilder fileName) {
        if (value == null) {
            if (index <= 2) {
                fileName = fileName.append("_(min)");
                return (long) Integer.MIN_VALUE;
            } else {
                fileName = fileName.append("_(max)");
                return (long) Integer.MAX_VALUE;
            }
        } else {
            switch (index) {
                case 0:
                    fileName = fileName.append("_(min-)");
                    return value - 1;
                case 1:
                    fileName = fileName.append("_(min)");
                    return value;
                case 2:
                    fileName = fileName.append("_(min+)");
                    return value + 1;
                case 3:
                    fileName = fileName.append("_(max-)");
                    return value - 1;
                case 4:
                    fileName = fileName.append("_(max)");
                    return value;
                case 5:
                    fileName = fileName.append("_(max+)");
                    return value + 1;
                default:
                    return 0L;
            }
        }
    }

    private String getRandomString(int index, Long length, StringBuilder fileName) {
        if (length == null || length <= 0) {
            fileName = fileName.append("_(length)");
            return new AutomationRandomUtility().generateRandomStringSmallLetters();
        } else {
            int finalLength = length.intValue();
            switch (index) {
                case 0:
                    fileName = fileName.append("_(length-)");
                    return automationRandomUtility.generateRandomStringSmallLetters(finalLength - 1);
                case 1:
                    fileName = fileName.append("_(length)");
                    return automationRandomUtility.generateRandomStringSmallLetters(finalLength);
                case 2:
                    fileName = fileName.append("_(length+)");
                    return automationRandomUtility.generateRandomStringSmallLetters(finalLength + 1);
                default:
                    return null;
            }
        }
    }

    private void saveJson(JSONObject jsonObject, StringBuilder fileName) {

        directory = automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_JSON_FOLDER_PATH) + "/"
                        + automationBoundaryValueAnalysisJsonDTO.getFolderName() + "/";

        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileWriter fileWriter = new FileWriter(directory + automationBoundaryValueAnalysisJsonDTO.getJsonFileName()
                        + "_" + fileName + JSON_EXTENSION)) {
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            fileNameArray.add(fileName);
        } catch (IOException e) {
            fileNameArray.forEach(name -> {
                File file = new File(directory + automationBoundaryValueAnalysisJsonDTO.getJsonFileName() + "_" + name
                                + JSON_EXTENSION);
                file.delete();
            });
        }
    }

}
