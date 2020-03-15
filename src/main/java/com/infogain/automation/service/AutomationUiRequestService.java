package com.infogain.automation.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationInputDTO;
import com.infogain.automation.dto.AutomationUiRequestDTO;
import com.infogain.automation.dto.AutomationUiResponseDTO;
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.utilities.AutomationClaimsUtility;
import com.infogain.automation.utilities.AutomationEndpointHitUtility;
import com.infogain.automation.utilities.AutomationHeadersUtility;
import com.infogain.automation.utilities.AutomationJsonUtility;

@Service
public class AutomationUiRequestService {
    private static final Logger logger = LogManager.getLogger(AutomationUiRequestService.class);

    private final AutomationEndpointHitUtility automationEndpointHitUtility;
    private final AutomationProperties automationProperties;
    private final AutomationHeadersUtility automationHeadersUtility;
    private final AutomationClaimsUtility automationClaimsUtility;
    private final AutomationJsonUtility automationJsonUtility;

    @Autowired
    public AutomationUiRequestService(final AutomationEndpointHitUtility automationEndpointHitUtility,
                    final AutomationProperties automationProperties,
                    final AutomationHeadersUtility automationHeadersUtility,
                    final AutomationClaimsUtility automationClaimsUtility,
                    final AutomationJsonUtility automationJsonUtility) {
        this.automationEndpointHitUtility = automationEndpointHitUtility;
        this.automationProperties = automationProperties;
        this.automationHeadersUtility = automationHeadersUtility;
        this.automationClaimsUtility = automationClaimsUtility;
        this.automationJsonUtility = automationJsonUtility;
    }

    public AutomationUiResponseDTO hitEndpointFromUI(AutomationUiRequestDTO automationUiRequestDTO) {
        String requestUrl = automationUiRequestDTO.getRequestURL();
        String baseClaimUrl;
        String inputjsonFolderPath =
                        automationProperties.getPropertyAsString(AutomationConstants.FASTEST_INPUT_JSON_FOLDER_PATH);
        String body = automationUiRequestDTO.getBody();
        JSONObject bodyJson = null;
        if (body != null) {
            try {
                body = body.toLowerCase().endsWith(".json") ? inputjsonFolderPath + "/" + body : body;
                bodyJson = automationJsonUtility.fetchJSONObject(body);
                body = bodyJson.toJSONString();
            } catch (AutomationException e) {
                if (!(e.getCause() instanceof ParseException)) {
                    throw e;
                }
            }
        }
        if (requestUrl.toLowerCase().startsWith("http")) {
            baseClaimUrl = requestUrl.substring(0, StringUtils.ordinalIndexOf(requestUrl, "/", 3));

        } else {
            baseClaimUrl = automationProperties.getPropertyAsString(AutomationConstants.FASTEST_HOST_NAME) + ":"
                            + automationProperties.getPropertyAsString(AutomationConstants.FASTEST_PORT);
        }
        Headers headers = automationHeadersUtility.fetchHeaders(automationUiRequestDTO.getHeader());


        AutomationInputDTO automationInputDTO = new AutomationInputDTO();
        automationInputDTO.setTestCaseInputJson(bodyJson);
        automationInputDTO.setHeaders(headers);
        automationClaimsUtility.updateToken(automationInputDTO);

        Response resp;
        if (automationInputDTO.getTestCaseInputJson() != null) {
            resp = automationEndpointHitUtility.hitEndpoint(baseClaimUrl, automationUiRequestDTO.getRequestURL(),
                            automationInputDTO.getHeaders(), automationUiRequestDTO.getRequestType(),
                            automationInputDTO.getTestCaseInputJson()).getFirst();
        } else {
            resp = automationEndpointHitUtility.hitEndpoint(baseClaimUrl, automationUiRequestDTO.getRequestURL(),
                            automationInputDTO.getHeaders(), automationUiRequestDTO.getRequestType(), body).getFirst();
        }
        automationClaimsUtility.releaseAutomationServer();
        return logger.traceExit(new AutomationUiResponseDTO(resp.asString(), resp.getStatusCode()));
    }

}
