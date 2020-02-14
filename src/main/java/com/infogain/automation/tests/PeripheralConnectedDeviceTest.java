package com.infogain.automation.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.infogain.automation.constants.AutomationConstants;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * <br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Implement application performance monitoring<br>
 * Description - This class is to test Connected Device Endpoint
 * 
 * @author Shagun Sharma [3696362]
 * @version 1.0.0
 * @since 06-Dec-2019
 */
public class PeripheralConnectedDeviceTest extends AutomationAbstractTests {

    protected ArrayList<String> keys =
                    new ArrayList<>(Arrays.asList("output", "connectedPeripheralResponse", "connectedPeripherals"));
    Set<String> deviceDetailsExpected = new HashSet<>();
    Set<String> deviceDetailsActual = new HashSet<>();
    int actualCount = 0;

    private static final String TYPE = "type";
    private static final String NAME = "name";

    private static final Logger logger = LogManager.getLogger(PeripheralConnectedDeviceTest.class);

    @Override
    public void performValidations() {
        logger.traceEntry("performValidations method of {} class", testSheetName);
        JSONParser jsonParser = new JSONParser();
        automationInputDTOList.forEach(peripheralInputDTO -> {
            boolean testCaseResult =
                            peripheralInputDTO.getActualHttpStatus().equals(peripheralInputDTO.getExpectedHttpStatus());
            if (testCaseResult) {
                if (StringUtils.isNotBlank(peripheralInputDTO.getExpectedOutput())
                                && StringUtils.isNotBlank(peripheralInputDTO.getActualOutput())) {
                    try {
                        Object jsonObjectExpectedOutput = jsonParser.parse(peripheralInputDTO.getExpectedOutput());
                        Object jsonObjectActualOutput = jsonParser.parse(peripheralInputDTO.getActualOutput());
                        for (int i = 0; i < keys.size(); i++) {
                            jsonObjectExpectedOutput = ((JSONObject) jsonObjectExpectedOutput).get(keys.get(i));
                            jsonObjectActualOutput = ((JSONObject) jsonObjectActualOutput).get(keys.get(i));
                        }
                        JSONArray jsonArrayExpectedOutput = (JSONArray) (jsonObjectExpectedOutput);
                        JSONArray jsonArrayActualOutput = (JSONArray) (jsonObjectActualOutput);
                        if (jsonArrayActualOutput.size() < jsonArrayExpectedOutput.size()) {
                            testCaseResult = false;
                        } else {
                            for (int j = 0; j < jsonArrayExpectedOutput.size(); j++) {
                                for (int i = 0; i < jsonArrayActualOutput.size(); i++) {

                                    JSONObject childObjectExpected = (JSONObject) jsonArrayExpectedOutput.get(j);
                                    JSONObject childObjectActual = (JSONObject) jsonArrayActualOutput.get(i);
                                    deviceDetailsExpected = childObjectExpected.keySet();
                                    deviceDetailsActual = childObjectActual.keySet();
                                    if (deviceDetailsExpected.contains(TYPE) && deviceDetailsActual.contains(TYPE)) {
                                        if ((childObjectExpected.get(TYPE)).equals(childObjectActual.get(TYPE))) {
                                            if ((childObjectExpected.get(TYPE)).equals("LABEL_PRINTER")) {
                                                if ((childObjectExpected.get(NAME))
                                                                .equals(childObjectActual.get(NAME))) {
                                                    testCaseResult = true;
                                                    break;
                                                } else {
                                                    testCaseResult = false;
                                                }
                                            } else {
                                                testCaseResult = true;
                                                break;
                                            }
                                        } else {
                                            testCaseResult = false;
                                        }
                                    } else {
                                        testCaseResult = false;
                                    }
                                }
                            }
                        }
                    } catch (ParseException e) {
                        testCaseResult = false;
                    }
                }
            }
            peripheralInputDTO.setTestCaseResult(testCaseResult ? AutomationConstants.TEST_CASE_RESULT_PASS
                            : AutomationConstants.TEST_CASE_RESULT_FAIL);
        });
        logger.traceExit();
    }
}
