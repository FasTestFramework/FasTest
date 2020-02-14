package com.infogain.automation.utilities;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This class generated Claim Id <br>
 * <ul>
 * <li>If Claim Id is already generated then it will release it</li>
 * </ul>
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationClaimsUtility {
    private static final Logger logger = LogManager.getLogger(AutomationClaimsUtility.class);
    private final AutomationRequestBodyAndHeadersUtility automationRequestBodyAndHeadersUtility;

    private String claimId;
    private String releaseUrl;
    private String claimUrl;
    private String claimJsonPath;
    private String claimJsonType;
    private String inputJSONfolderPath;
    private String tokenGenerateHeaderPath;
    private String tokenPropertyName;
    private String responseKey;
    private String baseClaimUrl;
    private final AutomationEndpointHitUtility automationEndpointHitUtility;

    @Autowired
    public AutomationClaimsUtility(final AutomationProperties automationProperties,
                    final AutomationEndpointHitUtility automationEndpointHitUtility,
                    final AutomationRequestBodyAndHeadersUtility automationRequestBodyAndHeadersUtility) {
        this.automationEndpointHitUtility = automationEndpointHitUtility;
        this.automationRequestBodyAndHeadersUtility = automationRequestBodyAndHeadersUtility;
        Properties properties = automationProperties.getProps();
        baseClaimUrl = properties.getProperty(AutomationConstants.FASTEST_HOST_NAME) + ":"
                        + properties.getProperty(AutomationConstants.FASTEST_PORT);
        releaseUrl = properties.getProperty(AutomationConstants.FASTEST_RELEASE_URL).split("\\|")[0];
        claimUrl = properties.getProperty(properties.getProperty(AutomationConstants.FASTEST_URL_TO_USE))
                        .split("\\|")[0];
        responseKey = properties.getProperty(AutomationConstants.FASTEST_RESPONSE_KEY);
        claimJsonType = properties.getProperty(AutomationConstants.FASTEST_ADD_TOKEN_IN);
        inputJSONfolderPath = properties.getProperty(AutomationConstants.FASTEST_INPUT_JSON_FOLDER_PATH);
        tokenGenerateHeaderPath = inputJSONfolderPath
                        + properties.getProperty(AutomationConstants.FASTEST_HEADER_TO_USE);
        claimJsonPath = inputJSONfolderPath + "/"
                        + properties.getProperty(AutomationConstants.FASTEST_BODY_TO_USE);
        tokenPropertyName = properties.getProperty(AutomationConstants.FASTEST_TOKEN_PROPERTY_NAME);
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
        if (claimId == null) {
            generateClaimId();
        }
        String[] tokenPropertyNameSplitted = tokenPropertyName.split("\\.");
        int lastPropertyIndex = tokenPropertyNameSplitted.length - 1;
        if (claimJsonType.equalsIgnoreCase("header") && automationInputDTO.getHeaders() != null) {
            Headers headers = automationInputDTO.getHeaders();
            List<Header> headersList = headers.asList().stream().map(header -> {
                if (header.getName().equals(tokenPropertyNameSplitted[lastPropertyIndex])
                                && header.getValue().contains("{}")) {
                    String tempValue = header.getValue().replace("{}", claimId);
                    header = new Header(header.getName(), tempValue);
                }
                return header;
            }).collect(Collectors.toList());
            headers = new Headers(headersList);
            automationInputDTO.setHeaders(headers);
        } else if (claimJsonType.equalsIgnoreCase("body") && automationInputDTO.getTestCaseInputJson() != null) {
            JSONObject testCaseInputJson = automationInputDTO.getTestCaseInputJson();
            if (testCaseInputJson != null) {
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
                    logger.info("Updating claim ID from JSON to generated claimID");
                    String tempValue = ob.get(tokenPropertyNameSplitted[lastPropertyIndex]).toString().replace("{}",
                                    claimId);
                    ob.remove(tokenPropertyNameSplitted[lastPropertyIndex]);
                    ob.put(tokenPropertyNameSplitted[lastPropertyIndex], tempValue);
                }
            }
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
        if (claimId != null) {
            hitReleaseAutomationServer(claimId);
        }
        claimId = null;
        logger.traceExit();
    }

    /**
     * This method deletes the generated Claim Id
     * 
     * @param res - Response
     * @since Dec 11, 2019
     */
    public void releaseAutomationServer(Response res) {
        logger.traceEntry("releaseAutomationServer method of AutomationClaimsUtility class");

        String claimIdKey = fetchClaimIdFromResponse(res);
        if (claimIdKey != null) {
            hitReleaseAutomationServer(claimIdKey);
        }
        logger.traceExit();
    }

    /**
     * This method deletes the ClaimID
     * 
     * @param claimId which needs to be released
     * @since Dec 11, 2019
     */
    private void hitReleaseAutomationServer(String claimId) {
        logger.traceEntry("hitReleaseAutomationServer method of AutomationClaimsUtility class");

        String releaseUrlWithClaimId = new StringBuilder(releaseUrl).append(claimId).toString();
        Headers headers = automationRequestBodyAndHeadersUtility.fetchHeaders(tokenGenerateHeaderPath);
        automationEndpointHitUtility.hitEndpoint(baseClaimUrl, releaseUrlWithClaimId, headers, HttpMethod.DELETE);
        logger.info(" generated Claim Id released");
        logger.traceExit();
    }

    /**
     * This method fetches claim ID by generating Claim Id if not generated already <br>
     * else it will release the previously generated ClaimId and generate new Claim Id from Server
     * 
     * @since Nov 27, 2019
     */
    public void generateClaimId() {
        logger.traceEntry("generateClaimId method of AutomationClaimsUtility class");
        if (claimId == null) {
            Response response = getClaimIdFromServer(claimJsonPath);
            claimId = fetchClaimIdFromResponse(response);
            if (claimId != null) {
                logger.info(claimId);
            } else {
                throw new AutomationException("Unable To Fetch Claim Id");
            }
        }
        logger.traceExit();
    }

    /**
     * This method fetch Claim Id from Response
     * 
     * @param response contains generated Claim Id
     * @return Claim Id as String
     * @since Nov 27, 2019
     */
    private String fetchClaimIdFromResponse(Response response) {
        logger.traceEntry("fetchClaimIdFromResponse method of AutomationClaimsUtility class");
        String claimIdKey = new JsonPath(response.asString()).get(responseKey);
        return logger.traceExit(claimIdKey);
    }

    /**
     * This method generates Claim Id from Server
     * 
     * @param requestBodyFile contains JSON input for generating Claim ID from Server
     * @return Response which contains Claim Id
     * @since Nov 27, 2019
     */
    private Response getClaimIdFromServer(String jsonPath) {
        logger.traceEntry("getClaimIdFromServer method of AutomationClaimsUtility class");
        Headers headers = automationRequestBodyAndHeadersUtility.fetchHeaders(tokenGenerateHeaderPath);
        return logger.traceExit(automationEndpointHitUtility.hitEndpoint(baseClaimUrl, claimUrl, headers,
                        HttpMethod.POST, automationRequestBodyAndHeadersUtility.fetchJSONObject(jsonPath)));
    }

}
