package com.infogain.automation.utilities;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.exception.CustomValidationFailure;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class AutomationValidationUtility {
    private static final String FAILURE_COMMENTS_SEPERATOR = "----------------------------------------\n";
    private final Logger logger = LogManager.getLogger(AutomationValidationUtility.class);
    private Map<String, List<String>> customKeysValidation;
    private StringBuilder comments;
    private List<String> keysIgnored = new ArrayList<>();

    private AutomationJsonUtility automationJsonUtility;

    @Autowired
    public AutomationValidationUtility(AutomationJsonUtility automationJsonUtility) {
        this.automationJsonUtility = automationJsonUtility;
    }

    public static void main(String[] args) {
        // //to be removed
        String expected = "{\r\n" + "  \"output\" : {\r\n"
                        + "    \"claimId\" : \"1d3d0dbe-3b24-4dd8-87b2-44114d83619b\",\r\n"
                        + "    \"receiptElements\" : [ " + "{\r\n" + "      \"cut\" : {\r\n"
                        + "        \"cutReceipt\" : true\r\n" + "      },\r\n" + "      \"barcode\" : {\r\n"
                        + "        \"textPosition\" : \"Below\",\r\n" + "        \"data\" : [ 1, 2, 3 ],\r\n"
                        + "        \"symbology\" : \"PDF41\",\r\n" + "        \"width\" : 100,\r\n"
                        + "        \"alignment\" : \"Center\",\r\n" + "        \"height\" : 100\r\n" + "      }\r\n"
                        + "    }, " + "{\r\n" + "      \"image\" : {\r\n" + "        \"alignment\" : \"Center\",\r\n"
                        + "        \"url\" : \"https://home.fedex.com/Corporate/SiteAssets/Lists/FedEx%20News%20Articles/NewForm/FedExCollective_120W_2017.jpg\"\r\n"
                        + "      }\r\n" + "    }, {\r\n" + "      \"receiptText\" : {\r\n"
                        + "        \"text\" : \"This is a text line to be printed on receipt\",\r\n"
                        + "        \"alignment\" : \"Center\"\r\n" + "      },\r\n" + "      \"barcode\" : {\r\n"
                        + "        \"textPosition\" : \"dfg\",\r\n" + "        \"data\" : [ 1, 2, 3, 4 ],\r\n"
                        + "        \"symbology\" : \"PDF41\",\r\n" + "        \"width\" : \"1000\",\r\n"
                        + "        \"alignment\" : \"Center\",\r\n" + "        \"height\" : 10000\r\n" + "      }\r\n"
                        + "    } ]\r\n" + "  }\r\n" + "}";

        String actual = "{\r\n" + "  \"output\" : {\r\n"
                        + "    \"claimId\" : \"1d3d0dbe-3b24-4dd8-87b2-44114d83619b\",\r\n"
                        + "    \"receiptElements\" : [ " + "{\r\n" + "      \"cut\" : {\r\n"
                        + "        \"cutReceipt\" : true\r\n" + "      },\r\n" + "      \"barcode\" : {\r\n"
                        + "        \"textPosition\" : \"Below\",\r\n" + "        \"data\" : [ 1, 2, 3 ],\r\n"
                        + "        \"symbology\" : \"PDF41\",\r\n" + "        \"width\" : 100,\r\n"
                        + "        \"alignment\" : \"Center\",\r\n" + "        \"height\" : 100\r\n" + "      }\r\n"
                        + "    }, " + "{\r\n" + "      \"image\" : {\r\n" + "        \"alignment\" : \"Center\",\r\n"
                        + "        \"url\" : \"https://home.fedex.com/Corporate/SiteAssets/Lists/FedEx%20News%20Articles/NewForm/FedExCollective_120W_2017.jpg\"\r\n"
                        + "      }\r\n" + "    }, {\r\n" + "      \"receiptText\" : {\r\n"
                        + "        \"text\" : \"This is a text line to be printed on receipt\",\r\n"
                        + "        \"alignment\" : \"Center\"\r\n" + "      },\r\n" + "      \"barcode\" : {\r\n"
                        + "        \"textPosition\" : \"dfg\",\r\n" + "        \"data\" : [ 1, 3,2, 4 ],\r\n"
                        + "        \"symbology\" : \"PDF41\",\r\n" + "        \"width\" : \"1000\",\r\n"
                        + "        \"alignment\" : \"Center\",\r\n" + "        \"height\" : 10000\r\n" + "      }\r\n"
                        + "    } ]\r\n" + "  }\r\n" + "}";


        // {"output":{
        // "claimId": "1d3d0dbe-3b24-4dd8-87b2-44114d83619b",
        // "receiptElements": [
        // {
        // "barcode": {
        // "alignment": "Center",
        // "data": [1,2,3],
        // "height": 100,
        // "symbology": "PDF41",
        // "textPosition": "Below",
        // "width": 100
        // },
        // "cut": {
        // "cutReceipt": true
        // }
        // },
        // {
        // "image": {
        // "alignment": "Center",
        // "url":
        // "https://home.fedex.com/Corporate/SiteAssets/Lists/FedEx%20News%20Articles/NewForm/FedExCollective_120W_2017.jpg"
        // }
        // },
        // {"barcode": {
        // "alignment": "Center",
        // "data": [1,2,3,4],
        // "height": 10000,
        // "symbology": "PDF41",
        // "textPosition": "dfg",
        // "width": "1000"
        // },
        // "receiptText": {
        // "alignment": "Center",
        // "text": "This is a text line to be printed on receipt"
        // }
        // }
        // ]}
        // }

        Map<String, List<String>> inputMap = new LinkedHashMap<>();
        inputMap.put("output.claimId", Arrays.asList("notNull()", "isNotNull()", "contains(\"-\")"));
        inputMap.put("output.receiptElements", Arrays.asList("containsArrayEntries()"));
        inputMap.put("output.receiptElements[x].barcode.data", Arrays.asList("contains(1,3)"));
        inputMap.put("output.receiptElements[x]", Arrays.asList("isEqual()"));
        inputMap.put("output.receiptElements[0].barcode.data", Arrays.asList("isEqual()"));
        inputMap.put("output.receiptElements[x]", Arrays.asList("ignore()"));

        inputMap.put("output.receiptElements[x].barcode",
                        Arrays.asList("containsKey(\"daa\")", "containsEntry(\"alignment\",\"Centr\")"));
        inputMap.put("output", Arrays.asList("containsKeys(\"receiptElements\",\"claimId\")"));
        inputMap.put("output.receiptElements", Arrays.asList("ignore()"));
        inputMap.put("output.receiptElements[2].barcode", Arrays.asList("sdfhghdfg()"));
System.out.println(new JSONObject().toJSONString(inputMap));
        AutomationValidationUtility automationValidationUtility =
                        new AutomationValidationUtility(new AutomationJsonUtility(new AutomationUtility()));
        automationValidationUtility.customKeysValidation = inputMap;
        try {
            JSONParser jsonParser = new JSONParser();
            Object jsonObjectExpectedOutput = jsonParser.parse(expected);
            Object jsonObjectActualOutput = jsonParser.parse(actual);
            automationValidationUtility.compareJSONs(jsonObjectExpectedOutput, jsonObjectActualOutput);
            System.out.println("Expected:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter()
                            .writeValueAsString(jsonObjectExpectedOutput));
            System.out.println("Actual:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter()
                            .writeValueAsString(jsonObjectActualOutput));
            System.out.println(automationValidationUtility.comments.toString());
        } catch (ParseException | JsonProcessingException e) {
        }
        System.out.println("result: " + (automationValidationUtility.comments.length() == 0));
    }

    public void performValidations(AutomationInputDTO automationInputDTO) {
        logger.traceEntry("performValidations method of AutomationValidationUtility class");
        comments = new StringBuilder();
        Integer actualHttpStatus = automationInputDTO.getActualHttpStatus();
        Integer expectedHttpStatus = automationInputDTO.getExpectedHttpStatus();
        boolean statusResult = actualHttpStatus.equals(expectedHttpStatus);
        String outputFailureComments = "";
        String expectedOutput = automationInputDTO.getExpectedOutput();
        String actualOutput = automationInputDTO.getActualOutput();
        if (StringUtils.isNotEmpty(expectedOutput) && StringUtils.isNotEmpty(actualOutput)) {
            customKeysValidation = automationInputDTO.getKeyValidation();
            try {
                Object jsonObjectExpectedOutput = automationJsonUtility.fetchObjectFromString(expectedOutput);
                Object jsonObjectActualOutput = automationJsonUtility.fetchObjectFromString(actualOutput);
                compareJSONs(jsonObjectExpectedOutput, jsonObjectActualOutput);
                outputFailureComments = comments.toString();
            } catch (IllegalArgumentException e) {
                if (!expectedOutput.equals(actualOutput)) {
                    String equalsFailureMessage = equalValidations(expectedOutput, actualOutput);
                    if (!equalsFailureMessage.isEmpty()) {
                        outputFailureComments = "Response body validation failure:\n" + equalsFailureMessage
                                        + "\n\nNote: Expected or Actual response body is not in json format.";
                    }
                }
            }
        } else if (!expectedOutput.equals(actualOutput)) {
            if (StringUtils.isEmpty(expectedOutput)) {
                outputFailureComments = "Expected response body to be blank but was not.";
            } else {
                outputFailureComments = "Expected response body to be:\n<" + expectedOutput + ">\nbut was blank.";
            }
        }
        StringBuilder failureComments = new StringBuilder();
        if (!statusResult) {
            failureComments.append("Expected Status Code:\n<" + actualHttpStatus + ">\nto be:\n<" + expectedHttpStatus
                            + ">\nbut was not.\n");
        }
        if (!outputFailureComments.isEmpty()) {
            if (failureComments.length() != 0) {
                failureComments.append(FAILURE_COMMENTS_SEPERATOR);
            }
            failureComments.append(outputFailureComments);
        }

        if (failureComments.length() == 0) {
            automationInputDTO.setTestCaseResult(AutomationConstants.TEST_CASE_RESULT_PASS);
        } else {
            automationInputDTO.setFailureComments(failureComments.toString());
            automationInputDTO.setTestCaseResult(AutomationConstants.TEST_CASE_RESULT_FAIL);
        }
        logger.traceExit();
    }

    public void compareJSONs(Object jsonObjectExpectedOutput, Object jsonObjectActualOutput) {
        objectValidate("", jsonObjectExpectedOutput, jsonObjectActualOutput, new StringBuilder());
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
     * @since Dec 11, 2019
     */
    private void objectValidate(Object key, Object objectExpected, Object objectActual, StringBuilder currentKeyPath) {
        logger.traceEntry("objectValidate method of AutomationValidationUtility class");
        String keyPath = currentKeyPath.toString();
        List<String> customValidations = getCustomValidationsByKey(keyPath);
        boolean customValidationsFound = customValidations != null;
        if (customValidationsFound) {
            doCustomValidations(objectExpected, objectActual, keyPath, customValidations);
        }
        if (objectExpected instanceof JSONObject) {
            jsonObjectValidate(objectExpected, objectActual, currentKeyPath, customValidationsFound);
        } else if (objectExpected instanceof JSONArray) {
            jsonArrayValidate(key, objectExpected, objectActual, currentKeyPath, customValidationsFound);
        } else if (customValidations == null) {
            String failureMessage = equalValidations(objectExpected, objectActual);
            if (!failureMessage.isEmpty()) {
                addComment(keyPath, failureMessage);
            }
        }
        logger.traceExit();
    }

    private void doCustomValidations(Object objectExpected, Object objectActual, String keyPath,
                    List<String> customValidations) {
        try {
            new AutomationCustomValidationUtility().validate(objectActual, objectExpected, customValidations);
        } catch (CustomValidationFailure e) {
            addComment(keyPath, e);
        }
        if (customValidations.contains("ignore()")) {
            keysIgnored.add(keyPath);
        }
    }

    private void jsonObjectValidate(Object objectExpected, Object objectActual, StringBuilder currentKeyPath,
                    boolean customValidationsFound) {
        if (objectActual instanceof JSONObject) {
            JSONObject jsonObjectExpected = (JSONObject) objectExpected;
            JSONObject jsonObjectActual = (JSONObject) objectActual;
            Set<Object> expectedKeySet = jsonObjectExpected.keySet();
            if (customValidationsFound || expectedKeySet.containsAll(jsonObjectActual.keySet())) {
                jsonObjectValidate(jsonObjectExpected, jsonObjectActual, currentKeyPath);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Expecting keys:\n<");
                sb.append(expectedKeySet.toString());
                sb.append(">\n but was:\n<");
                sb.append(jsonObjectActual.keySet().toString());
                sb.append(">");
                addComment(currentKeyPath.toString(), sb.toString());
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Expecting:\n<");
            addQuotesIfString(objectActual, sb);
            sb.append(">\nto be object:\n<");
            sb.append(objectExpected);
            sb.append(">\nbut was not.");
            addComment(currentKeyPath.toString(), sb.toString());
        }
    }

    private void addQuotesIfString(Object objectActual, StringBuilder sb) {
        if (objectActual instanceof String) {
            sb.append("\"");
            sb.append(objectActual);
            sb.append("\"");
        } else {
            sb.append(objectActual);
        }
    }

    private void jsonArrayValidate(Object key, Object objectExpected, Object objectActual, StringBuilder currentKeyPath,
                    boolean customValidationsFound) {
        if (objectActual instanceof JSONArray) {
            JSONArray jsonArrayExpected = (JSONArray) objectExpected;
            JSONArray jsonArrayActual = (JSONArray) objectActual;
            if (customValidationsFound || jsonArrayExpected.size() == jsonArrayActual.size()) {
                jsonArrayValidate(key, jsonArrayExpected, jsonArrayActual, currentKeyPath);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Expecting array size:\n<");
                sb.append(jsonArrayExpected.size());
                sb.append(">\nbut was :\n<");
                sb.append(jsonArrayActual.size());
                sb.append(">");
                addComment(currentKeyPath.toString(), sb.toString());
            }

        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Expecting:\n<");
            addQuotesIfString(objectActual, sb);
            sb.append(">\nto be an array:\n<");
            sb.append(objectExpected);
            sb.append(">\nbut was not.");
            addComment(currentKeyPath.toString(), sb.toString());
        }
    }

    private String equalValidations(Object objectExpected, Object objectActual) {
        String message = "";
        try {
            assertThat(objectActual).isEqualTo(objectExpected);
        } catch (AssertionError e) {
            message = e.getMessage();
        }
        return message;
    }

    private void addComment(String keyPath, CustomValidationFailure e) {
        addComment(keyPath, e.getMessage(), true);
    }

    private void addComment(String keyPath, String message) {
        addComment(keyPath, message, false);
    }

    private void addComment(String keyPath, String message, boolean addComment) {
        if (addComment || !ignoreTheKey(keyPath)) {
            if (comments.length() != 0) {
                comments.append(FAILURE_COMMENTS_SEPERATOR);
            }
            comments.append(keyPath).append(":\n").append(message).append("\n");
        }
    }

    private boolean ignoreTheKey(String currentKey) {
        return keysIgnored.stream().anyMatch(currentKey::startsWith);
    }

    /**
     * This method is for JSON objects which validate the expected keys and its value with actual keys and its value
     * 
     * @param jsonObjectExpected - Expected JSON Object
     * @param jsonObjectActual- Actual JSON Object
     * @return toReturn
     */
    @SuppressWarnings("unchecked")
    private void jsonObjectValidate(JSONObject jsonObjectExpected, JSONObject jsonObjectActual,
                    StringBuilder currentKeyPath) {
        logger.traceEntry("jsonObjectValidate method of AutomationValidationUtility class");
        for (Object entry : jsonObjectExpected.keySet()) {
            StringBuilder localPath = addKey(currentKeyPath, entry.toString());
            if (jsonObjectActual.containsKey(entry)) {
                objectValidate(entry, jsonObjectExpected.get(entry), jsonObjectActual.get(entry), localPath);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Expecting:\n<");
                sb.append(jsonObjectActual);
                sb.append(">\nto contain key:\n<");
                sb.append(entry);
                sb.append(">");
                addComment(localPath.toString(), sb.toString());
            }
        }

        logger.traceExit();
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
    private void jsonArrayValidate(Object key, JSONArray jsonArrayExpected, JSONArray jsonArrayActual,
                    StringBuilder currentKeyPath) {
        logger.traceEntry("jsonArrayValidate method of AutomationValidationUtility class");
        for (int i = 0; i < jsonArrayExpected.size(); i++) {
            StringBuilder localPath = new StringBuilder(currentKeyPath).append("[").append(i).append("]");
            objectValidate(key, jsonArrayExpected.get(i), jsonArrayActual.get(i), localPath);
        }
        logger.traceExit();
    }

    private List<String> getCustomValidationsByKey(String currentKeyPath) {
        Set<String> allKeyPaths = extractArrayGenericKeyPaths(currentKeyPath);
        for (String keyPath : allKeyPaths) {
            if (customKeysValidation.containsKey(keyPath)) {
                return customKeysValidation.get(keyPath);
            }
        }
        return null;
    }

    private Set<String> extractArrayGenericKeyPaths(String currentKeyPath) {
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

    private void addArrayGenericKeyPaths(String path, Set<String> arrayGenericKeyPaths) {
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
        indexes.forEach((k, v) -> arrayGenericKeyPaths.add(new StringBuilder(path).replace(k + 1, v, "x").toString()));
    }

    private StringBuilder addKey(StringBuilder currentKeyPath, String key) {
        return StringUtils.isNotEmpty(currentKeyPath.toString())
                        ? new StringBuilder(currentKeyPath).append(".").append(key)
                        : new StringBuilder(key);
    }

}
