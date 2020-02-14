package com.infogain.automation.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import io.restassured.http.Header;
import io.restassured.http.Headers;

import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.exception.AutomationException;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This class is for reading Data from Header JSON File and setting in {@link AutomationInputDTO}
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationRequestBodyAndHeadersUtility {

    private static final Logger logger = LogManager.getLogger(AutomationRequestBodyAndHeadersUtility.class);

    @SuppressWarnings("unchecked")
    public Headers fetchHeaders(JSONArray headersJsonArray) {
        ArrayList<Header> headersList = new ArrayList<>();
        headersJsonArray.forEach(header -> {
            Header keyAndValue = gettingHeaderValue((JSONObject) header);
            headersList.add(keyAndValue);
        });
        Headers headers = new Headers(headersList);
        return headers;
    }

    public Headers fetchHeaders(String fileNameorHeadersJson) {
        return fetchHeaders(fetchJSONArray(fileNameorHeadersJson));
    }

    public JSONObject fetchJSONObject(String fileNameorHeadersJson) {
        Object parseObj = extractObjectFromFileOrString(fileNameorHeadersJson);
        if (parseObj instanceof JSONObject) {
            return (JSONObject) parseObj;
        } else {
            throw new AutomationException("Object was not parsable to JSONObject");
        }
        
    }

    public JSONArray fetchJSONArray(String fileNameorHeadersJson) {
        Object parseObj = extractObjectFromFileOrString(fileNameorHeadersJson);
        if (parseObj instanceof JSONArray) {
            return (JSONArray) parseObj;
        } else {
            throw new AutomationException("Object was not parsable to JSONArray");
        }
    }

    private Object extractObjectFromFileOrString(String fileNameorHeadersJson) {
        Object parseObj = fileNameorHeadersJson.toLowerCase().endsWith(".json") ? fetchObjectFromFile(fileNameorHeadersJson)
                        : fetchObjectFromString(fileNameorHeadersJson);
        return parseObj;
    }

    public Object fetchObjectFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            return jsonParseObject(reader);
        } catch (FileNotFoundException e) {
            throw new AutomationException("File not found !", e);
        } catch (IOException e) {
            throw new AutomationException("File Path not valid !", e);
        }
    }

    public Object fetchObjectFromString(String jsonData) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object parseObj = jsonParser.parse(jsonData);
            return parseObj;
        } catch (ParseException e) {
            throw new AutomationException("Unable to parse Data", e);
        }
    }

    private Object jsonParseObject(Reader reader) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object parseObj = jsonParser.parse(reader);
            return parseObj;
        } catch (IOException e) {
            throw new AutomationException("File Path not valid !", e);
        } catch (ParseException e) {
            throw new AutomationException("Unable to parse Data", e);
        }
    }



    /**
     * This method takes {@link JSONObject} of header and returns a {@link Header} object.
     * 
     * @param header JSON Object contains Key and its value
     * @return Header Object
     * @since Nov 27, 2019
     */
    public Header gettingHeaderValue(JSONObject header) {
        logger.traceEntry("gettingHeaderValue method of AutomationRequestBodyAndHeadersUtility class");
        for (Object entry : header.keySet()) {
            return logger.traceExit(new Header(entry.toString(), header.get(entry).toString()));
        }
        return null;
    }
}
