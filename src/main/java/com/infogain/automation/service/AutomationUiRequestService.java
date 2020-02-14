package com.infogain.automation.service;

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
import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.properties.AutomationProperties;
import com.infogain.automation.utilities.AutomationClaimsUtility;
import com.infogain.automation.utilities.AutomationEndpointHitUtility;
import com.infogain.automation.utilities.AutomationRequestBodyAndHeadersUtility;

@Service
public class AutomationUiRequestService {
    private static final Logger logger = LogManager.getLogger(AutomationUiRequestService.class);

    private final AutomationEndpointHitUtility automationEndpointHitUtility;
    private final AutomationProperties automationProperties;
    private final AutomationRequestBodyAndHeadersUtility automationRequestBodyAndHeadersUtility;
    private final AutomationClaimsUtility automationClaimsUtility;

    @Autowired
    public AutomationUiRequestService(final AutomationEndpointHitUtility automationEndpointHitUtility,
                    final AutomationProperties automationProperties,
                    final AutomationRequestBodyAndHeadersUtility automationRequestBodyAndHeadersUtility,
                    final AutomationClaimsUtility automationClaimsUtility) {
        this.automationEndpointHitUtility = automationEndpointHitUtility;
        this.automationProperties = automationProperties;
        this.automationRequestBodyAndHeadersUtility = automationRequestBodyAndHeadersUtility;
        this.automationClaimsUtility = automationClaimsUtility;
    }

    public String hitEndpointFromUI(AutomationUiRequestDTO automationUiRequestDTO) {
        String body = automationUiRequestDTO.getBody();
        JSONObject bodyJson = null;
        if (body != null) {
            try {
                bodyJson = automationRequestBodyAndHeadersUtility.fetchJSONObject(body);
                body = bodyJson.toJSONString();
            } catch (AutomationException e) {
                if (!(e.getCause() instanceof ParseException)) {
                    throw e;
                }
            }
        }
        Headers headers = automationRequestBodyAndHeadersUtility.fetchHeaders(automationUiRequestDTO.getHeader());
        String baseClaimUrl = automationProperties.getProps()
                        .getProperty(AutomationConstants.FASTEST_HOST_NAME) + ":"
                        + automationProperties.getProps().getProperty(AutomationConstants.FASTEST_PORT);

        AutomationInputDTO automationInputDTO = new AutomationInputDTO();
        automationInputDTO.setTestCaseInputJson(bodyJson);
        automationInputDTO.setHeaders(headers);
        automationClaimsUtility.updateToken(automationInputDTO);

        Response resp;
        if (automationInputDTO.getTestCaseInputJson() != null) {
            resp = automationEndpointHitUtility.hitEndpoint(baseClaimUrl, automationUiRequestDTO.getRequestURL(),
                            automationInputDTO.getHeaders(), automationUiRequestDTO.getRequestType(),
                            automationInputDTO.getTestCaseInputJson());
        } else {
            resp = automationEndpointHitUtility.hitEndpoint(baseClaimUrl, automationUiRequestDTO.getRequestURL(),
                            automationInputDTO.getHeaders(), automationUiRequestDTO.getRequestType(), body);
        }
        return logger.traceExit(resp.asString());
    }

}
