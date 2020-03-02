package com.infogain.automation.utilities;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.dto.Pair;
import com.infogain.automation.properties.AutomationProperties;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class generated Claim Id <br>
 * <ul>
 * <li>If Claim Id is already generated then it will release it</li>
 * </ul>
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationClaimsUtility {
    private static final Logger logger = LogManager.getLogger(AutomationClaimsUtility.class);
    private final AutomationRequestBodyAndHeadersUtility automationRequestBodyAndHeadersUtility;

    private Response tokenResponse;
    private String inputJSONfolderPath;
    private String baseClaimUrl;
    private final AutomationEndpointHitUtility automationEndpointHitUtility;
    private final AutomationProperties automationProperties;
    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationClaimsUtility(final AutomationProperties automationProperties,
                    final AutomationEndpointHitUtility automationEndpointHitUtility,
                    final AutomationRequestBodyAndHeadersUtility automationRequestBodyAndHeadersUtility,
                    final AutomationUtility automationUtility) {
        this.automationProperties = automationProperties;
        this.automationEndpointHitUtility = automationEndpointHitUtility;
        this.automationUtility = automationUtility;
        this.automationRequestBodyAndHeadersUtility = automationRequestBodyAndHeadersUtility;
        baseClaimUrl = automationProperties.getProperty(AutomationConstants.FASTEST_HOST_NAME) + ":"
                        + automationProperties.getProperty(AutomationConstants.FASTEST_PORT);
        inputJSONfolderPath = automationProperties.getProperty(AutomationConstants.FASTEST_INPUT_JSON_FOLDER_PATH);
    }

    /**
     * This method generated the claim ID if it is null and updates the claimID value from JSON file by new generated
     * claim ID
     * 
     * @param automationInputDTOList of {@link AutomationInputDTO}
     * @since Dec 11, 2019
     */
    @SuppressWarnings("unchecked")
    public void updateToken(AutomationInputDTO automationInputDTO) {
        logger.traceEntry("updateClaimId method of AutomationClaimsUtility class");
        String tokenPropertyNames =
                        automationProperties.getProperty(AutomationConstants.FASTEST_TOKEN_REPLACE_PROPERTY_NAMES);
        automationInputDTO.setHeaders(replaceKeysInRequest(tokenPropertyNames, automationInputDTO.getHeaders(),
                        automationInputDTO.getTestCaseInputJson(), null).getFirst());
    }

    /**
     * This method fetches claim ID by generating Claim Id if not generated already <br>
     * else it will release the previously generated ClaimId and generate new Claim Id from Server
     * 
     * @since Nov 27, 2019
     */
    public void generateClaimId() {
        logger.traceEntry("generateClaimId method of AutomationClaimsUtility class");
        if (tokenResponse == null) {
            tokenResponse = getTokenFromServer();
        }
        logger.traceExit();
    }

    /**
     * This method deletes the generated Claim Id
     * 
     * @since Dec 11, 2019
     */
    public void releaseAutomationServer() {
        logger.traceEntry("releaseAutomationServer method of AutomationClaimsUtility class");
        if (automationProperties.getProperty(AutomationConstants.FASTEST_RELEASE_TOKEN).equalsIgnoreCase("true")
                        && tokenResponse != null) {
            hitReleaseAutomationServer();
        }
        tokenResponse = null;
        logger.traceExit();
    }

    private Pair<String, HttpMethod> fetchUrlAndType(String url) {
        if (!url.contains("/")) {
            url = automationProperties.getProperty(url);
        }
        String[] urlWithType = url.split("\\|");
        HttpMethod urlType = automationUtility.getRequestMethodType(urlWithType[1]);
        return Pair.of(urlWithType[0], urlType);
    }

    private String pathCheck(String jsonFilePath) {
        String filePath;
        if (StringUtils.isNotEmpty(jsonFilePath)) {
            filePath = inputJSONfolderPath + "/" + jsonFilePath;
        } else {
            filePath = null;
        }
        return filePath;
    }

    private Map<String, Map<String, String>> tokenKeyValues(String tokenPropertyNames) {
        Map<String, Map<String, String>> addTokenIn = new LinkedHashMap<>();
        Map<String, String> tokenReplace = new LinkedHashMap<>();
        char[] temp = tokenPropertyNames.toCharArray();
        int start = 0, end = 0;
        int i = 0;
        String outerKey = "", innerKey = "", innerValue = "";
        while (i < temp.length) {
            if (temp[i] == ':') {
                outerKey = tokenPropertyNames.substring(start == 0 ? start : start + 1, i);
            } else if (temp[i] == '{') {
                start = i + 1;
            } else if (temp[i] == '=') {
                end = i;
                innerKey = tokenPropertyNames.substring(start, end);
                start = i + 1;
            } else if (temp[i] == ',') {
                end = i;
                innerValue = tokenPropertyNames.substring(start, end);
                tokenReplace.put(innerKey, innerValue);
                start = i + 1;
            } else if (temp[i] == '}') {
                end = i;
                innerValue = tokenPropertyNames.substring(start, end);
                tokenReplace.put(innerKey, innerValue);
                addTokenIn.put(outerKey, tokenReplace);
                tokenReplace = new HashMap<>();
                start = i + 1;
            }
            i++;
        }
        return addTokenIn;
    }

    private Pair<Headers, String> replaceKeysInRequest(String tokenPropertyNames, Headers headers, JSONObject body,
                    String url) {
        Map<String, Map<String, String>> addOrReplaceTokenKeyValues = tokenKeyValues(tokenPropertyNames);
        for (Entry<String, Map<String, String>> addOrReplaceTokenIn : addOrReplaceTokenKeyValues.entrySet()) {
            String replaceTokenIn = addOrReplaceTokenIn.getKey().toLowerCase();
            Map<String, String> tokenKeyMapping = addOrReplaceTokenIn.getValue();
            switch (replaceTokenIn) {
                case "header":
                    headers = addTokenInHeader(tokenKeyMapping, headers);
                    break;
                case "body":
                    addTokenInBody(tokenKeyMapping, body);
                    break;
                case "url":
                    url = addTokenInUrl(tokenKeyMapping, url);
                    break;
            }
        }
        return Pair.of(headers, url);
    }

    private String addTokenInUrl(Map<String, String> tokenKeyMapping, String url) {
        if (url != null) {
            for (Entry<String, String> tokenKeyEntry : tokenKeyMapping.entrySet()) {
                url = url.replace("{" + tokenKeyEntry.getKey() + "}", fetchValueFromResponse(tokenKeyEntry.getValue()));
            }
        }
        return url;
    }

    @SuppressWarnings("unchecked")
    private void addTokenInBody(Map<String, String> tokenKeyMapping, JSONObject testCaseInputJson) {
        if (testCaseInputJson != null) {
            for (Entry<String, String> tokenKeyEntry : tokenKeyMapping.entrySet()) {
                String tokenPropertyName = tokenKeyEntry.getKey();
                String tokenFetchResponseKey = tokenKeyEntry.getValue();

                String[] tokenPropertyNameSplitted = tokenPropertyName.split("\\.");
                int lastPropertyIndex = tokenPropertyNameSplitted.length - 1;

                JSONObject ob = testCaseInputJson;
                for (int j = 0; j < lastPropertyIndex + 1; j++) {
                    Object obj = ob.get(tokenPropertyNameSplitted[j]);
                    if (obj != null) {
                        if (j == lastPropertyIndex) {
                            break;
                        } else {
                            ob = (JSONObject) obj;
                        }
                    }
                }
                if (ob.get(tokenPropertyNameSplitted[lastPropertyIndex]) != null
                                && ob.get(tokenPropertyNameSplitted[lastPropertyIndex]).toString().contains("{}")) {
                    generateClaimId();

                    logger.info("Updating claim ID from JSON to generated claimID");
                    String tempValue = ob.get(tokenPropertyNameSplitted[lastPropertyIndex]).toString().replace("{}",
                                    fetchValueFromResponse(tokenFetchResponseKey));
                    ob.remove(tokenPropertyNameSplitted[lastPropertyIndex]);
                    ob.put(tokenPropertyNameSplitted[lastPropertyIndex], tempValue);
                }
            }
        }
    }

    private Headers addTokenInHeader(Map<String, String> tokenKeyMapping, Headers headers) {
        if (headers != null) {
            List<Header> headersList = headers.asList().stream().map(header -> {
                if (tokenKeyMapping.containsKey(header.getName()) && header.getValue().contains("{}")) {
                    generateClaimId();
                    String tempValue = header.getValue().replace("{}",
                                    fetchValueFromResponse(tokenKeyMapping.get(header.getName())));
                    header = new Header(header.getName(), tempValue);
                }
                return header;
            }).collect(Collectors.toList());
            headers = new Headers(headersList);
        }
        return logger.traceExit(headers);

    }

    /**
     * This method deletes the ClaimID
     * 
     * @param claimId which needs to be released
     * @since Dec 11, 2019
     */
    private void hitReleaseAutomationServer() {
        logger.traceEntry("hitReleaseAutomationServer method of AutomationClaimsUtility class");
        String tokenReleaseUrl = automationProperties.getProperty(AutomationConstants.FASTEST_TOKEN_RELEASE_URL);
        Pair<String, HttpMethod> urlAndType = fetchUrlAndType(tokenReleaseUrl);
        tokenReleaseUrl = urlAndType.getFirst();
        HttpMethod tokenReleaseUrlType = urlAndType.getSecond();
        String headerPath = automationProperties.getProperty(AutomationConstants.FASTEST_GENERATE_TOKEN_HEADER_TO_USE);
        String tokenReleaseHeaderPath = pathCheck(headerPath);
        Headers headers = automationRequestBodyAndHeadersUtility.fetchHeaders(tokenReleaseHeaderPath);
        String tokenPropertyNames =
                        automationProperties.getProperty(AutomationConstants.FASTEST_TOKEN_RELEASE_PROPERTY_NAMES);
        String releaseBodyPath =
                        automationProperties.getProperty(AutomationConstants.FASTEST_RELEASE_TOKEN_BODY_TO_USE);
        String tokenreleaseBodyPath = pathCheck(releaseBodyPath);
        JSONObject requestBody = automationRequestBodyAndHeadersUtility.fetchJSONObject(tokenreleaseBodyPath);
        Pair<Headers, String> replaceKeysInRequest =
                        replaceKeysInRequest(tokenPropertyNames, headers, requestBody, tokenReleaseUrl);
        headers = replaceKeysInRequest.getFirst();
        tokenReleaseUrl = replaceKeysInRequest.getSecond();

        automationEndpointHitUtility.hitEndpoint(baseClaimUrl, tokenReleaseUrl, headers, tokenReleaseUrlType,
                        requestBody);
        logger.info(" generated Claim Id released");
        logger.traceExit();
    }

    /**
     * This method fetch Claim Id from Response
     * 
     * @param response contains generated Claim Id
     * @return Claim Id as String
     * @since Nov 27, 2019
     */
    private String fetchValueFromResponse(String key) {
        logger.traceEntry("fetchValueFromResponse method of AutomationClaimsUtility class");
        String value = new JsonPath(tokenResponse.asString()).get(key);
        return logger.traceExit(value);
    }

    /**
     * This method generates Claim Id from Server
     * 
     * @param requestBodyFile contains JSON input for generating Claim ID from Server
     * @return Response which contains Claim Id
     * @since Nov 27, 2019
     */
    private Response getTokenFromServer() {
        logger.traceEntry("getClaimIdFromServer method of AutomationClaimsUtility class");
        String claimUrl = automationProperties.getProperty(AutomationConstants.FASTEST_GENERATE_TOKEN_URL);
        String headerPath = automationProperties.getProperty(AutomationConstants.FASTEST_GENERATE_TOKEN_HEADER_TO_USE);
        String tokenGenerateHeaderPath = pathCheck(headerPath);
        Pair<String, HttpMethod> urlAndType = fetchUrlAndType(claimUrl);
        claimUrl = urlAndType.getFirst();
        HttpMethod claimUrlType = urlAndType.getSecond();
        Headers headers = automationRequestBodyAndHeadersUtility.fetchHeaders(tokenGenerateHeaderPath);
        String claimJsonPath = inputJSONfolderPath + "/"
                        + automationProperties.getProperty(AutomationConstants.FASTEST_GENERATE_TOKEN_BODY_TO_USE);
        return logger.traceExit(automationEndpointHitUtility.hitEndpoint(baseClaimUrl, claimUrl, headers, claimUrlType,
                        automationRequestBodyAndHeadersUtility.fetchJSONObject(claimJsonPath)).getFirst());
    }

}
