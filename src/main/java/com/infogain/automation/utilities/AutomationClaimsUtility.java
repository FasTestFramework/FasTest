package com.infogain.automation.utilities;

import java.util.Arrays;
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
import com.infogain.automation.exception.AutomationException;
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

    private Response tokenResponse;

    private String inputJSONfolderPath;
    private String baseClaimUrl;

    private final AutomationEndpointHitUtility automationEndpointHitUtility;
    private final AutomationHeadersUtility automationHeadersUtility;
    private final AutomationProperties automationProperties;
    private final AutomationUtility automationUtility;
    private final AutomationJsonUtility automationJsonUtility;

    @Autowired
    public AutomationClaimsUtility(final AutomationProperties automationProperties,
                    final AutomationEndpointHitUtility automationEndpointHitUtility,
                    final AutomationHeadersUtility automationHeadersUtility, final AutomationUtility automationUtility,
                    final AutomationJsonUtility automationJsonUtility) {
        this.automationProperties = automationProperties;
        this.automationEndpointHitUtility = automationEndpointHitUtility;
        this.automationUtility = automationUtility;
        this.automationHeadersUtility = automationHeadersUtility;
        this.automationJsonUtility = automationJsonUtility;
        baseClaimUrl = automationProperties.getProperty(AutomationConstants.FASTEST_HOST_NAME) + ":"
                        + automationProperties.getProperty(AutomationConstants.FASTEST_PORT);
        inputJSONfolderPath =
                        automationProperties.getPropertyAsString(AutomationConstants.FASTEST_INPUT_JSON_FOLDER_PATH);
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
        String tokenPropertyNames = automationProperties
                        .getPropertyAsString(AutomationConstants.FASTEST_TOKEN_REPLACE_PROPERTY_NAMES);
        Pair<Headers, String> updatedHeaders = replaceKeysInRequest(tokenPropertyNames, automationInputDTO.getHeaders(),
                        automationInputDTO.getTestCaseInputJson(), null);
        automationInputDTO.setHeaders(updatedHeaders.getFirst());
    }

    public void checkIfTokenResponse(String requestUrl, String requestType, Response response) {
        if (automationProperties.getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN)
                        .equalsIgnoreCase("true")) {
            String urlWithType = requestUrl + "|" + requestType;
            String generateTokenUrl = automationProperties.getPropertyAsString(
                            automationProperties.getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN_URL));
            if (urlWithType.equalsIgnoreCase(generateTokenUrl) && validateTokenResponse(response)) {
                tokenResponse = response;
            }
        }
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
            if (!validateTokenResponse(tokenResponse)) {
                tokenResponse = null;
                throw new AutomationException("Invalid token response is received as Status code recieved is not "
                                + automationProperties.getPropertyAsString(
                                                AutomationConstants.FASTEST_GENERATE_TOKEN_RESPONSE_SUCCESS_STATUS_CODE)
                                + " or required keys " + fetchTokenValidateKeys() + " does not exists in response.");
            }
        }
        logger.traceExit();
    }

    private boolean validateTokenResponse(Response responseToValidate) {
        String statusCode = automationProperties
                        .getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN_RESPONSE_SUCCESS_STATUS_CODE);
        if (!statusCode.equals(String.valueOf(responseToValidate.statusCode()))) {
            return false;
        }
        List<String> keysToValidate = fetchTokenValidateKeys();
        logger.info("Validating keys:{} if they exists in token response", keysToValidate);
        for (String keyToValidate : keysToValidate) {
            if (fetchKeyFromResponseString(responseToValidate.asString(), keyToValidate) == null) {
                return false;
            }
        }
        return true;
    }

    private List<String> fetchTokenValidateKeys() {
        return Arrays.asList(automationProperties
                        .getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN_RESPONSE_KEYS_TO_VALIDATE)
                        .split(","));
    }

    public Object fetchKeyFromResponseString(String responseBody, String keyToFetch) {
        return new JsonPath(responseBody).get(keyToFetch);
    }

    /**
     * This method deletes the generated Claim Id
     * 
     * @since Dec 11, 2019
     */
    public void releaseAutomationServer() {
        logger.traceEntry("releaseAutomationServer method of AutomationClaimsUtility class");
        if (automationProperties.getPropertyAsString(AutomationConstants.FASTEST_RELEASE_TOKEN).equalsIgnoreCase("true")
                        && tokenResponse != null) {
            hitReleaseAutomationServer();
        }
        tokenResponse = null;
        logger.traceExit();
    }

    private Pair<String, HttpMethod> fetchUrlAndType(String url) {
        if (!url.contains("/")) {
            url = automationProperties.getPropertyAsString(url);
        }
        String[] urlWithType = url.split("\\|");
        HttpMethod urlType = automationUtility.getRequestMethodType(urlWithType[1]);
        return Pair.of(urlWithType[0], urlType);
    }

    private String pathCheck(String jsonFilePath) {
        return StringUtils.isNotEmpty(jsonFilePath) ? inputJSONfolderPath + "/" + jsonFilePath : null;
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
                Object tokenValue = ob.get(tokenPropertyNameSplitted[lastPropertyIndex]);
                if (tokenValue != null && tokenValue.toString().contains("{}")) {
                    generateClaimId();
                    logger.info("Updating claim ID from JSON to generated claimID");
                    String tempValue =
                                    tokenValue.toString().replace("{}", fetchValueFromResponse(tokenFetchResponseKey));
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
        String tokenReleaseUrl =
                        automationProperties.getPropertyAsString(AutomationConstants.FASTEST_TOKEN_RELEASE_URL);
        Pair<String, HttpMethod> urlAndType = fetchUrlAndType(tokenReleaseUrl);
        tokenReleaseUrl = urlAndType.getFirst();
        HttpMethod tokenReleaseUrlType = urlAndType.getSecond();
        String headerPath = automationProperties
                        .getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN_HEADER_TO_USE);
        String tokenReleaseHeaderPath = pathCheck(headerPath);
        Headers headers = automationHeadersUtility.fetchHeaders(tokenReleaseHeaderPath);
        String tokenPropertyNames = automationProperties
                        .getPropertyAsString(AutomationConstants.FASTEST_TOKEN_RELEASE_PROPERTY_NAMES);
        String releaseBodyPath =
                        automationProperties.getPropertyAsString(AutomationConstants.FASTEST_RELEASE_TOKEN_BODY_TO_USE);
        String tokenreleaseBodyPath = pathCheck(releaseBodyPath);
        JSONObject requestBody = automationJsonUtility.fetchJSONObject(tokenreleaseBodyPath, true);
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
        String value = null;
        if (tokenResponse != null) {
            value = fetchKeyFromResponseString(tokenResponse.asString(), key).toString();
        }
        if (value == null) {
            throw new AutomationException("Error while fetching key:'" + key + "' from token response.");
        }
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
        String claimUrl = automationProperties.getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN_URL);
        String headerPath = automationProperties
                        .getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN_HEADER_TO_USE);
        String tokenGenerateHeaderPath = pathCheck(headerPath);
        Pair<String, HttpMethod> urlAndType = fetchUrlAndType(claimUrl);
        claimUrl = urlAndType.getFirst();
        HttpMethod claimUrlType = urlAndType.getSecond();
        Headers headers = automationHeadersUtility.fetchHeaders(tokenGenerateHeaderPath);
        String claimJsonPath = inputJSONfolderPath + "/" + automationProperties
                        .getPropertyAsString(AutomationConstants.FASTEST_GENERATE_TOKEN_BODY_TO_USE);
        return logger.traceExit(
                        automationEndpointHitUtility
                                        .hitEndpoint(baseClaimUrl, claimUrl, headers, claimUrlType,
                                                        automationJsonUtility.fetchJSONObject(claimJsonPath, true))
                                        .getFirst());
    }

}
