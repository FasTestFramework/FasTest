package com.infogain.automation.utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.exception.CustomValidationFailure;

import static org.assertj.core.api.Assertions.assertThat;

public class AutomationValidationUtility {
    private static final Logger logger = LogManager.getLogger(AutomationValidationUtility.class);
    private static Map<String, String> customKeysValidation;
    private static StringBuilder comments = new StringBuilder();

    private AutomationValidationUtility() {}

    /*
     * public static void main(String[] args) { //to be removed String expected = "{\"output\":{\r\n" +
     * "  \"claimId\": \"1d3d0dbe-3b24-4dd8-87b2-44114d83619b\",\r\n" + "  \"receiptElements\": [\r\n" + "    {\r\n" +
     * "      \"barcode\": {\r\n" + "        \"alignment\": \"Center\",\r\n" + "        \"data\": [1.23],\r\n" +
     * "        \"height\": 100,\r\n" + "        \"symbology\": \"PDF41\",\r\n" +
     * "        \"textPosition\": \"Below\",\r\n" + "        \"width\": 100\r\n" + "      },\r\n" +
     * "      \"cut\": {\r\n" + "        \"cutReceipt\": true\r\n" + "      }\r\n" + "    },\r\n" + "    {\r\n" +
     * "      \"image\": {\r\n" + "        \"alignment\": \"Center\",\r\n" +
     * "        \"requestURL\": \"https://home.Infogain.com/Corporate/SiteAssets/Lists/Infogain%20News%20Articles/NewForm/InfogainCollective_120W_2017.jpg\"\r\n"
     * + "      }\r\n" + "    },\r\n" + "    {\"barcode\": {\r\n" + "        \"alignment\": \"Center\",\r\n" +
     * "        \"data\": [13.122,2.0,3,4],\r\n" + "        \"height\": 10000,\r\n" +
     * "        \"symbology\": \"PDF41\",\r\n" + "        \"textPosition\": \"dfg\",\r\n" +
     * "        \"width\": \"1000\"\r\n" + "      },\r\n" + "      \"receiptText\": {\r\n" +
     * "        \"alignment\": \"Center\",\r\n" +
     * "        \"text\": \"This is a text line to be printed on receipt\"\r\n" + "      }\r\n" + "    }\r\n" +
     * "  ]}\r\n" + "}";
     * 
     * String actual = "{\"output\":{\r\n" + "  \"claimId\": \"1d3d0dbe-3b24-4dd8-87b2-44114d83619b\",\r\n" +
     * "  \"receiptElements\": [\r\n" + "    {\r\n" + "      \"barcode\": {\r\n" +
     * "        \"alignment\": \"Center\",\r\n" + "        \"data\": [1.2],\r\n" + "        \"height\": 100,\r\n" +
     * "        \"symbology\": \"PDF41\",\r\n" + "        \"textPosition\": \"Below\",\r\n" +
     * "        \"width\": 100\r\n" + "      },\r\n" + "      \"cut\": {\r\n" + "        \"cutReceipt\": true\r\n" +
     * "      }\r\n" + "    },\r\n" + "    {\r\n" + "      \"image\": {\r\n" + "        \"alignment\": \"Center\",\r\n"
     * +
     * "        \"requestURL\": \"https://home.Infogain.com/Corporate/SiteAssets/Lists/Infogain%20News%20Articles/NewForm/InfogainCollective_120W_2017.jpg\"\r\n"
     * + "      }\r\n" + "    },\r\n" + "    {\"barcode\": {\r\n" + "        \"alignment\": \"Center\",\r\n" +
     * "        \"data\": [13.122,2.0,1.23,4],\r\n" + "        \"height\": 10000,\r\n" +
     * "        \"symbology\": \"PDF41\",\r\n" + "        \"textPosition\": \"dfg\",\r\n" +
     * "        \"width\": \"1000\"\r\n" + "      },\r\n" + "      \"receiptText\": {\r\n" +
     * "        \"alignment\": \"Center\",\r\n" +
     * "        \"text\": \"This is a text line to be printed on receipt\"\r\n" + "      }\r\n" + "    }\r\n" +
     * "  ]}\r\n" + "}";
     * 
     * // {"output":{ // "claimId": "1d3d0dbe-3b24-4dd8-87b2-44114d83619b", // "receiptElements": [ // { // "barcode": {
     * // "alignment": "Center", // "data": [1,2,3], // "height": 100, // "symbology": "PDF41", // "textPosition":
     * "Below", // "width": 100 // }, // "cut": { // "cutReceipt": true // } // }, // { // "image": { // "alignment":
     * "Center", // "requestURL": //
     * "https://home.Infogain.com/Corporate/SiteAssets/Lists/Infogain%20News%20Articles/NewForm/InfogainCollective_120W_2017.jpg"
     * // } // }, // {"barcode": { // "alignment": "Center", // "data": [1,2,3,4], // "height": 10000, // "symbology":
     * "PDF41", // "textPosition": "dfg", // "width": "1000" // }, // "receiptText": { // "alignment": "Center", //
     * "text": "This is a text line to be printed on receipt" // } // } // ]} // }
     * 
     * Map<String, String> inputMap = new LinkedHashMap<>(); // inputMap.put("output.claimId",
     * "notNull();isNotNull();contains(\"-\")"); inputMap.put("output.receiptElements[x].barcode.data",
     * "contains(1.23)"); // inputMap.put("output.receiptElements[x]", "isEqual()"); //
     * inputMap.put("output.receiptElements[0].barcode.data", "isEqual()"); // inputMap.put("output.receiptElements[x]",
     * "ignore()"); // // inputMap.put("output.receiptElements[x].barcode", //
     * "containsKey(\"data\");containsEntry(\"alignment\",\"Centr\")"); // inputMap.put("output",
     * "containsKeys(\"receiptElements\",\"claimId\")");
     * 
     * customKeysValidation = inputMap;
     * 
     * boolean testCaseResult; try { JSONParser jsonParser = new JSONParser(); Object jsonObjectExpectedOutput =
     * jsonParser.parse(expected); Object jsonObjectActualOutput = jsonParser.parse(actual); testCaseResult =
     * compareJSONs(jsonObjectExpectedOutput, jsonObjectActualOutput); System.out.println(comments.toString()); } catch
     * (ParseException e) { testCaseResult = false; } System.out.println("result: " + testCaseResult); }
     */
    public static void performValidations(AutomationInputDTO automationInputDTO) {
        logger.traceEntry("performValidations method of AutomationValidationUtility class");
        boolean testCaseResult =
                        automationInputDTO.getActualHttpStatus().equals(automationInputDTO.getExpectedHttpStatus());
        // If actual and Expected HTTP status are equal then only Output is validated
        if (testCaseResult) {
            if (StringUtils.isNotBlank(automationInputDTO.getExpectedOutput())
                            && StringUtils.isNotBlank(automationInputDTO.getActualOutput())) {
                try {
                    customKeysValidation = automationInputDTO.getKeyValidation();
                    JSONParser jsonParser = new JSONParser();
                    Object jsonObjectExpectedOutput = jsonParser.parse(automationInputDTO.getExpectedOutput());
                    Object jsonObjectActualOutput = jsonParser.parse(automationInputDTO.getActualOutput());
                    testCaseResult = compareJSONs(jsonObjectExpectedOutput, jsonObjectActualOutput);
                    System.out.println(comments.toString());
                } catch (ParseException e) {
                    testCaseResult = false;
                }
            } else if (!automationInputDTO.getExpectedOutput().equals(automationInputDTO.getActualOutput())) {
                testCaseResult = false;
            }
        }
        automationInputDTO.setTestCaseResult(testCaseResult ? AutomationConstants.TEST_CASE_RESULT_PASS
                        : AutomationConstants.TEST_CASE_RESULT_FAIL);
        logger.traceExit();

    }

    private static boolean compareJSONs(Object jsonObjectExpectedOutput, Object jsonObjectActualOutput) {

        return objectValidate("", jsonObjectExpectedOutput, jsonObjectActualOutput, new StringBuilder());
    }

    /**
     * This method checks the object is either
     * <ul>
     * <li>JSON Object</li>
     * <li>JSON Array</li>
     * </ul>
     * 
     * @param key in JSON
     * @param objectExpected - Expected Object
     * @param objectActual - Actual Object
     * @return toReturn
     * @since Dec 11, 2019
     */
    private static boolean objectValidate(Object key, Object objectExpected, Object objectActual,
                    StringBuilder currentKeyPath) {
        logger.traceEntry("objectValidate method of AutomationValidationUtility class");
        boolean toReturn = true;
        String keyPath = currentKeyPath.toString();
        String customValidations = getCustomValidationsByKey(keyPath);

        if (customValidations != null) {
            try {
                new AutomationCustomValidationUtility().validate(objectActual, objectExpected, customValidations);
            } catch (CustomValidationFailure e) {
                if (comments.length() != 0) {
                    comments.append("----------------------------------------\n");
                }
                comments.append(keyPath).append(":\n").append(e.getMessage());
                toReturn = false;
            }
        }
        if (objectExpected instanceof JSONObject) {
            toReturn = jsonObjectValidate((JSONObject) objectExpected, (JSONObject) objectActual, currentKeyPath);
        } else if (objectExpected instanceof JSONArray) {
            toReturn = jsonArrayValidate(key, (JSONArray) objectExpected, (JSONArray) objectActual, currentKeyPath);
        } else if (customValidations != null) {
            try {
                assertThat(objectActual).isEqualTo(objectExpected);
            } catch (AssertionError e) {
                if (comments.length() != 0) {
                    comments.append("----------------------------------------\n");
                }
                comments.append(keyPath).append(":\n").append(e.getMessage());
                toReturn = false;
            }
        }

        // else if (customValidations != null) {
        // try {
        // new AutomationCustomValidationUtility().validate(objectActual, customValidations);
        // } catch (CustomValidationFailure e) {
        // logger.info(e);
        // toReturn = false;
        // }
        // } else if (!(objectActual.getClass().equals(objectExpected.getClass())
        // && objectActual.toString().equals(objectExpected.toString()))) {
        // toReturn = false;
        // }
        return logger.traceExit(toReturn);
    }

    /**
     * This method is for JSON objects which validate the expected keys and its value with actual keys and its value
     * 
     * @param jsonObjectExpected - Expected JSON Object
     * @param jsonObjectActual- Actual JSON Object
     * @return toReturn
     */
    @SuppressWarnings("unchecked")
    private static boolean jsonObjectValidate(JSONObject jsonObjectExpected, JSONObject jsonObjectActual,
                    StringBuilder currentKeyPath) {
        logger.traceEntry("jsonObjectValidate method of AutomationValidationUtility class");

        boolean toReturn = true;
        for (Object entry : jsonObjectExpected.keySet()) {
            StringBuilder localPath = addKey(currentKeyPath, entry.toString());
            if (jsonObjectActual.containsKey(entry)) {
                toReturn = objectValidate(entry, jsonObjectExpected.get(entry), jsonObjectActual.get(entry), localPath);
            } else {
                toReturn = false;
            }
            if (!toReturn) {
                break;
            }
        }
        return logger.traceExit(toReturn);
    }

    /**
     * This method is for JSON Array objects which validate the expected keys and its value with actual keys and its
     * value
     * 
     * @param key - JSON key
     * @param jsonArrayExpected - Expected JSON Array
     * @param jsonArrayActual- Actual JSON Array
     * @return toReturn
     */
    private static boolean jsonArrayValidate(Object key, JSONArray jsonArrayExpected, JSONArray jsonArrayActual,
                    StringBuilder currentKeyPath) {
        logger.traceEntry("jsonArrayValidate method of AutomationValidationUtility class");

        boolean toReturn = true;
        if (jsonArrayExpected.size() == jsonArrayActual.size()) {
            for (int i = 0; i < jsonArrayExpected.size(); i++) {
                StringBuilder localPath = new StringBuilder(currentKeyPath).append("[").append(i).append("]");
                if (!objectValidate(key, jsonArrayExpected.get(i), jsonArrayActual.get(i), localPath)) {
                    toReturn = false;
                    break;
                }
            }
        } else {
            toReturn = false;
        }
        return logger.traceExit(toReturn);
    }

    private static String getCustomValidationsByKey(String currentKeyPath) {
        Set<String> allKeyPaths = extractArrayGenericKeyPaths(currentKeyPath);
        for (String keyPath : allKeyPaths) {
            if (customKeysValidation.containsKey(keyPath)) {
                return customKeysValidation.get(keyPath);
            }
        }
        return null;
    }

    private static Set<String> extractArrayGenericKeyPaths(String currentKeyPath) {
        Set<String> arrayGenericKeyPaths = new CopyOnWriteArraySet<>();
        arrayGenericKeyPaths.add(currentKeyPath);
        List<String> checked = new ArrayList<>();
        for (int i = 0; i < currentKeyPath.split("\\[\\d*\\]").length; i++) {
            for (String arrayGenericKeyPath : arrayGenericKeyPaths) {
                if (!checked.contains(arrayGenericKeyPath)) {
                    addArrayGenericKeyPaths(arrayGenericKeyPath, arrayGenericKeyPaths);
                    checked.add(arrayGenericKeyPath);
                }
            }
        }
        return arrayGenericKeyPaths;
    }

    private static void addArrayGenericKeyPaths(String path, Set<String> arrayGenericKeyPaths) {
        Map<Integer, Integer> indexes = new LinkedHashMap<>();
        int index = 0;
        int diff = 0;
        while (index != -1) {
            index = path.indexOf("[", index + diff);
            if (index != -1) {
                if (path.indexOf("x", index) - index == 1) {
                    index = index + 2;
                } else {
                    diff = path.indexOf("]", index);
                    indexes.put(index, diff);
                    diff = diff - index;
                    index++;
                }
            }
        }
        indexes.forEach((k, v) -> {
            arrayGenericKeyPaths.add(new StringBuilder(path).replace(k + 1, v, "x").toString());
        });
    }

    private static StringBuilder addKey(StringBuilder currentKeyPath, String key) {
        return StringUtils.isNotEmpty(currentKeyPath.toString())
                        ? new StringBuilder(currentKeyPath).append(".").append(key)
                        : new StringBuilder(key);
    }


}
