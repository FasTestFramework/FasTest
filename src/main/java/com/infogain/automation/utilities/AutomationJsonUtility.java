package com.infogain.automation.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Component
public class AutomationJsonUtility {
    private static final Logger logger = LogManager.getLogger(AutomationJsonUtility.class);
    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationJsonUtility(final AutomationUtility automationUtility) {
        this.automationUtility = automationUtility;
    }

    public String beautifyJson(String text) {
        String actualOutput = text;
        JsonParser jsonParser = new JsonParser();
        try {
            JsonElement outputObject = jsonParser.parse(actualOutput);
            if (!outputObject.isJsonNull() && !outputObject.isJsonPrimitive()) {
                actualOutput = beautifyJson(outputObject);
            }
        } catch (Exception e) {
            // no further handling needed
        }
        return actualOutput;
    }

    public String beautifyJson(Object object) {
        String jsonString;
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            jsonString = gson.toJson(object);
        } catch (Exception e) {
            jsonString = object.toString();
        }
        return jsonString;
    }

    public JSONObject fetchJSONObject(String jsonString) {
        return fetchJSONObject(jsonString, false);
    }

    public JSONArray fetchJSONArray(String jsonString) {
        return fetchJSONArray(jsonString, false);
    }

    public JSONObject fetchJSONObject(String fileNameorJson, boolean checkIfFile) {
        if (StringUtils.isEmpty(fileNameorJson)) {
            return null;
        } else {
            Object parseObj;
            if (checkIfFile) {
                parseObj = extractObjectFromFileOrString(fileNameorJson);
            } else {
                parseObj = fetchObjectFromString(fileNameorJson);
            }
            if (parseObj instanceof JSONObject) {
                return (JSONObject) parseObj;
            } else {
                throw new IllegalArgumentException("Object was not parsable to JSONObject");
            }
        }
    }

    public JSONArray fetchJSONArray(String fileNameorJson, boolean checkIfFile) {
        Object parseObj;
        if (checkIfFile) {
            parseObj = extractObjectFromFileOrString(fileNameorJson);
        } else {
            parseObj = fetchObjectFromString(fileNameorJson);
        }
        if (parseObj instanceof JSONArray) {
            return (JSONArray) parseObj;
        } else {
            throw new IllegalArgumentException("Object was not parsable to JSONArray");
        }
    }

    private Object extractObjectFromFileOrString(String fileNameorHeadersJson) {
        return fileNameorHeadersJson.toLowerCase().endsWith(".json") ? fetchObjectFromFile(fileNameorHeadersJson)
                        : fetchObjectFromString(fileNameorHeadersJson);
    }

    public Object fetchObjectFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            return jsonParseObject(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while reading file", e);
        }
    }

    public Object fetchObjectFromString(String jsonData) {
        JSONParser jsonParser = new JSONParser();
        try {
            return jsonParser.parse(jsonData);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unable to parse Data", e);
        }
    }

    private Object jsonParseObject(Reader reader) {
        JSONParser jsonParser = new JSONParser();
        try {
            return jsonParser.parse(reader);
        } catch (ParseException | IOException e) {
            throw new IllegalArgumentException("Unable to parse Data", e);
        }
    }


    private String pathArrayToDelimitedString(List<String> arr, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (arr.get(i).charAt(0) != '[' && i > 0) {
                sb.append(".");
            }
            sb.append(arr.get(i));
        }
        if (sb.length() == 0) {
            sb.append("root of json");
        }
        return sb.toString();
    }

    // public static void main(String[] args) {
    // String json1 = "{\r\n" + " \"fileName\": \"receiptPrinter.json\",\r\n"
    // + " \"folderName\": \"receiptPrinter/bva\",\r\n" + " \"data\": {\r\n" + " \"0\": \"231\",\r\n"
    // + " \"key2\": \"ddd\",\r\n" + " \"key3\": \"12\",\r\n" + " \"key4\": \"asd\"\r\n" + " },\r\n"
    // + " \"metaData\": [\r\n" + " {\r\n" + " \"keyName\": [1,2],\r\n" + " \"type\": \"Int\",\r\n"
    // + " \"max\": 1,\r\n" + " \"min\": 10\r\n" + " },\r\n" + " {\r\n" + " \"keyName\": \"key2\",\r\n"
    // + " \"type\": \"Int\",\r\n" + " \"max\": 1,\r\n" + " \"min\": 10\r\n" + " }" + ",\r\n"
    // + " {\r\n" + " \"keyName\": \"key2\",\r\n" + " \"type\": \"Int\",\r\n" + " \"max\": 1,\r\n"
    // + " \"min\": 10\r\n" + " }]\r\n" + "}";
    // String json2 = "[\r\n" + " {\r\n" + " \"keyName[0]\": \"key1\",\r\n" + " \"type\": \"Int\",\r\n"
    // + " \"max\": 1,\r\n" + " \"min\": [\r\n" + " [\r\n" + " [\r\n"
    // + " [\r\n" + " 1,\r\n" + " 2\r\n" + " ],\r\n"
    // + " 3\r\n" + " ],\r\n" + " 4\r\n" + " ],\r\n" + " 5\r\n"
    // + " ]\r\n" + " },\r\n" + " 6,\r\n" + " [\r\n" + " 7,\r\n" + " 8\r\n" + " ]\r\n"
    // + "]";
    // JSONAware jsonObj1 = null;
    // try {
    // jsonObj1 = (JSONAware) new JSONParser().parse(json1);
    // } catch (ParseException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // JSONAware jsonObj2 = null;
    // try {
    // jsonObj2 = (JSONAware) new JSONParser().parse(json2);
    // } catch (ParseException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // //
    // String[] strArray = {"[0].min[0][0][0]", "abcd", "metaData[0].min.rdf", "[4]", "[0].keyName", "[2]", "[2][1]",
    // "[10000]", "[0].folderName", "[]", "[ass]", "metaesfData[10]", "metaseg", "[0].min[0][0][0][0]",
    // "[0].min[0][0][0][1]", "[0].min[0][0][0][0][0]", "[0].min[0][0][1]", "metaData[0].min.rdf",
    // "metaData[0].min.abc.hj", "metaData[1].keyName.abc", "folderName", "data.0",
    // "metaData[0].keyName", "metaData[0].keyName[0]", "metaData[1].keyName", "metaData[10]",
    // "metaData[aa]", "metaData[0].keyName[4]", "metaData[2][0][1][1][1].keyName[4]",
    // "metaData[0].ke yName[4]", "metaData[0].ke.yName[4]", "metaData[0].ke.yName[4 ]"};
    // // String[] strArray = {"metaData[0]", "metaData[0].min", "metaData[1].keyName","folderName", "data.0",
    // // "metaData[0].keyName", "metaData[0].keyName[0]",
    // // "metaData[1].keyName", "metaData[10]", "metaData[aa]", "metaData[0].keyName[4]",
    // // "metaData[2][0][1][1][1].keyName[4]", "metaData[0].ke yName[4]", "metaData[0].ke.yName[4]",
    // // "metaData[0].ke.yName[4 ]"};
    // AutomationJsonUtility automationJsonUtility = new AutomationJsonUtility(new AutomationUtility());
    //
    // for (String keyPath : strArray) {
    // System.out.println("\n For json1 :\n object: " + jsonObj1 + "\n keyPath: " + keyPath);
    // meraPath(jsonObj1, keyPath, automationJsonUtility);
    //
    // try {
    // System.out.println(automationJsonUtility.setValueandGetJSONStringForLeafNodes(jsonObj1, keyPath,
    // Arrays.asList("Namit")));
    // } catch (Exception e) {
    // System.out.println(e);
    // }
    //
    // System.out.println("\n For json2 :\n object: " + jsonObj2 + "\n keyPath: " + keyPath);
    // meraPath(jsonObj2, keyPath, automationJsonUtility);
    //
    // try {
    // System.out.println(automationJsonUtility.setValueandGetJSONStringForLeafNodes(jsonObj2, keyPath,
    // Arrays.asList("Namit")));
    // } catch (Exception e) {
    // System.out.println(e);
    // }
    //
    // }
    //
    // }

    public void setValueandGetJSONStringForLeafNodes(JSONAware obj, String keyPath, Map<Object, String> newValueList,
                    Map<String, String> fileNameWithData) {
        checkValidJsonPath(keyPath);
        List<String> keyPaths = splitKeyPath(keyPath);
        String lastKey;
        Object parentObj;
        Object oldValue;
        String parentKeyPath;
        if (keyPaths.size() > 1) {
            int lastIndex = keyPaths.size() - 1;
            lastKey = keyPaths.get(lastIndex);
            keyPaths.remove(lastIndex);
            parentKeyPath = pathArrayToDelimitedString(keyPaths, keyPaths.size());
            parentObj = getObjectUsingJsonPath(obj, parentKeyPath);
        } else {
            parentKeyPath = pathArrayToDelimitedString(keyPaths, 0);
            lastKey = keyPath;
            parentObj = obj;
        }
        oldValue = getObjectFromSingleKey(parentObj, lastKey, parentKeyPath);
        nullCheckObj(oldValue, keyPath);
        if (oldValue instanceof JSONAware) {
            throw new IllegalArgumentException("'" + keyPath + "' is not a leaf node.");
        }
        for (Entry<Object, String> newValue : newValueList.entrySet()) {
            setValueFromSingleKey(parentObj, lastKey, newValue.getKey());
            fileNameWithData.put(newValue.getValue(), beautifyJson(obj.toJSONString()));
        }
        setValueFromSingleKey(parentObj, lastKey, oldValue);
    }

    public String setValueandGetJSONString(JSONAware obj, String keyPath, Object newValue) {
        checkValidJsonPath(keyPath);
        List<String> keyPaths = splitKeyPath(keyPath);
        String lastKey;
        Object parentObj;
        Object oldValue;
        String parentKeyPath;
        if (keyPaths.size() > 1) {
            int lastIndex = keyPaths.size() - 1;
            lastKey = keyPaths.get(lastIndex);
            keyPaths.remove(lastIndex);
            parentKeyPath = pathArrayToDelimitedString(keyPaths, keyPaths.size());
            parentObj = getObjectUsingJsonPath(obj, parentKeyPath);
        } else {
            parentKeyPath = pathArrayToDelimitedString(keyPaths, 0);
            lastKey = keyPath;
            parentObj = obj;
        }
        oldValue = getObjectFromSingleKey(parentObj, lastKey, parentKeyPath);
        nullCheckObj(oldValue, keyPath);
        setValueFromSingleKey(parentObj, lastKey, newValue);
        String toReturn = beautifyJson(obj.toJSONString());
        setValueFromSingleKey(parentObj, lastKey, oldValue);
        return toReturn;
    }

    public Object getObjectUsingJsonPath(Object obj, String keyPath) {
        List<String> keyPaths = splitKeyPath(keyPath);
        for (int i = 0; i < keyPaths.size(); i++) {
            String arrayToDelimitedString = pathArrayToDelimitedString(keyPaths, i);
            obj = getObjectFromSingleKey(obj, keyPaths.get(i), arrayToDelimitedString);
        }
        nullCheckObj(obj, keyPath);
        return obj;
    }

    private void nullCheckObj(Object obj, String keyPath) {
        if (obj == null) {
            throw new IllegalArgumentException("'" + keyPath + "' doesn't exists in json.");
        }
    }

    public void checkValidJsonPath(String keyPath) {
        if (StringUtils.isEmpty(keyPath)) {
            throw new IllegalArgumentException("Empty Json Path Received.");
        }
        if (!keyPath.matches(
                        "^(\\[\\d+\\])*\\.?(((?!\\[)([^\\s\\[\\]\\.]+?)(\\[\\d+\\])*\\.)*?(?!\\[)([^\\s\\[\\]\\.]+?)(\\[\\d+\\])*)?$")) {
            throw new IllegalArgumentException("Syntactically Invalid Json Path Received.");
        }
    }

    public Object getObjectFromSingleKey(Object obj, String keyName, String keyPath) {
        nullCheckObj(obj, keyPath);
        if (obj instanceof JSONObject) {
            obj = getJSONObjectFromKey(obj, keyName, keyPath);
        } else if (obj instanceof JSONArray) {
            obj = getJSONArrayFromKey(obj, keyName, keyPath);
        } else {
            throw new IllegalArgumentException("'" + keyPath + "' is not object.");
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public void setValueFromSingleKey(Object obj, String keyName, Object value) {
        if (obj instanceof JSONObject) {
            ((JSONObject) obj).put(keyName, value);
        } else if (obj instanceof JSONArray) {
            Integer index = automationUtility.extractIntegerOfString(keyName.substring(1, keyName.length() - 1));
            ((JSONArray) obj).set(index, value);
        }
    }

    public Object getJSONArrayFromKey(Object obj, String keyName, String keyPath) {
        if (keyName.charAt(0) != '[') {
            throw new IllegalArgumentException("'" + keyPath + "' is array not object.");
        } else if (keyName.charAt(0) == '[') {
            JSONArray childArray = (JSONArray) obj;
            Integer index = automationUtility.extractIntegerOfString(keyName.substring(1, keyName.length() - 1));
            if (index > childArray.size() - 1) {
                throw new IllegalArgumentException("'" + keyPath + "' has length " + childArray.size() + ".");
            } else {
                obj = childArray.get(index);
            }
        }
        return obj;
    }

    public Object getJSONObjectFromKey(Object obj, String keyName, String keyPath) {
        if (keyName.charAt(0) == '[') {
            throw new IllegalArgumentException("'" + keyPath + "' is object not array.");
        } else if (keyName.charAt(0) != '[') {
            obj = ((JSONObject) obj).get(keyName);
        }
        return obj;
    }

    public List<String> splitKeyPath(String keyPath) {
        keyPath = keyPath.replace("[", " [");
        if (keyPath.charAt(0) == ' ') {
            keyPath = keyPath.substring(1);
        }
        String[] split = keyPath.split("\\.|\\s");
        List<String> keyNames = new ArrayList<>(split.length);
        keyNames.addAll(Arrays.asList(split));
        return keyNames;
    }

    public JSONAware extractJsonObjectFromMapOrList(Object data) {
        JSONAware jsonAware = null;
        try {
            if (data instanceof Map) {
                jsonAware = (JSONAware) new JSONParser().parse(JSONObject.toJSONString(((Map) data)));
            } else if (data instanceof List) {
                jsonAware = (JSONAware) new JSONParser().parse(JSONArray.toJSONString(((List) data)));
            }
        } catch (ParseException e) {
            // No handling is needed
        }
        return jsonAware;
    }

    public <T> T fetchObject(String string, TypeReference<T> responseType) {
        T readValue = null;
        try {
            readValue = new ObjectMapper().readValue(string, responseType);
        } catch (IOException e) {
            logger.error("Can't convert String to required Type", e);
        }
        return readValue;
    }

    public Set<String> fetchAllKeyPaths(Object jsonData, boolean addGenericArrayPaths) {
        Set<String> allKeyPaths = new LinkedHashSet<>();
        addKeysFromObject(allKeyPaths, jsonData, "", addGenericArrayPaths);
        return allKeyPaths;
    }

    private void addKeysFromObject(Set<String> allKeyPaths, Object jsonData, String currentKeyPath,
                    boolean addGenericArrayPaths) {
        if (jsonData instanceof JSONObject) {
            addKeysFromJSONObject(allKeyPaths, (JSONObject) jsonData, currentKeyPath, addGenericArrayPaths);
        } else if (jsonData instanceof JSONArray) {
            addKeysFromJSONArray(allKeyPaths, (JSONArray) jsonData, currentKeyPath, addGenericArrayPaths);
        }
    }

    private void addKeysFromJSONObject(Set<String> allKeyPaths, JSONObject jsonData, String currentKeyPath,
                    boolean addGenericArrayPaths) {
        String currentKeyPathObject;
        for (Object entry : jsonData.keySet()) {
            currentKeyPathObject =
                            currentKeyPath.isEmpty() ? entry.toString() : currentKeyPath + "." + entry.toString();
            allKeyPaths.add(currentKeyPathObject);
            addKeysFromObject(allKeyPaths, jsonData.get(entry), currentKeyPathObject, addGenericArrayPaths);
        }
    }

    private void addKeysFromJSONArray(Set<String> allKeyPaths, JSONArray jsonData, String currentKeyPath,
                    boolean addGenericArrayPaths) {
        String currentKeyPathArray;
        if (addGenericArrayPaths) {
            currentKeyPathArray = currentKeyPath + "[x]";
            allKeyPaths.add(currentKeyPathArray);
            if (!jsonData.isEmpty()) {
                addKeysFromObject(allKeyPaths, jsonData.get(0), currentKeyPathArray, addGenericArrayPaths);
            }
        }
        for (int i = 0; i < jsonData.size(); i++) {
            currentKeyPathArray = currentKeyPath + "[" + i + "]";
            allKeyPaths.add(currentKeyPathArray);
            addKeysFromObject(allKeyPaths, jsonData.get(i), currentKeyPathArray, addGenericArrayPaths);
        }
    }

}
