package com.infogain.automation.utilities;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.restassured.http.Header;
import io.restassured.http.Headers;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is for reading Data from Header JSON File and setting in {@link AutomationInputDTO}
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationHeadersUtility {

    private static final Logger logger = LogManager.getLogger(AutomationHeadersUtility.class);
    private final AutomationJsonUtility automationJsonUtility;

    @Autowired
    public AutomationHeadersUtility(final AutomationJsonUtility automationJsonUtility) {
        this.automationJsonUtility = automationJsonUtility;
    }

    @SuppressWarnings("unchecked")
    public Headers fetchHeaders(JSONArray headersJsonArray) {
        ArrayList<Header> headersList = new ArrayList<>();
        headersJsonArray.forEach(header -> {
            Header keyAndValue = gettingHeaderValue((JSONObject) header);
            headersList.add(keyAndValue);
        });
        return new Headers(headersList);
    }

    public Headers fetchHeaders(String fileNameorHeadersJson) {
        if (StringUtils.isEmpty(fileNameorHeadersJson)) {
            return new Headers();
        } else {
            return fetchHeaders(automationJsonUtility.fetchJSONArray(fileNameorHeadersJson));
        }
    }

    /**
     * This method takes {@link JSONObject} of header and returns a {@link Header} object.
     * 
     * @param header JSON Object contains Key and its value
     * @return Header Object
     * @since Nov 27, 2019
     */
    private Header gettingHeaderValue(JSONObject header) {
        logger.traceEntry("gettingHeaderValue method of AutomationHeadersUtility class");
        for (Object entry : header.keySet()) {
            return logger.traceExit(new Header(entry.toString(), header.get(entry).toString()));
        }
        return null;
    }
}
