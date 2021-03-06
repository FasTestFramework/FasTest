package com.infogain.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import com.infogain.automation.dto.Pair;

import static io.restassured.RestAssured.given;

@Component
public class AutomationEndpointHitUtility {

    private static final Logger logger = LogManager.getLogger(AutomationEndpointHitUtility.class);

    /**
     * This method takes Endpoint URL and its Type and return response after
     * 
     * @param automationInputDTO of {@link AutomationInputDTO}
     * @return response
     * @since Dec 11, 2019
     */
    public Pair<Response, Double> hitEndpoint(String baseUrl, String url, Headers headers, HttpMethod methodType,
                    String requestBody) {
        RestAssured.baseURI = baseUrl;
        logger.traceEntry("hitEndpoint method of AutomationEndpointHitUtility class");
        logger.info("Request URL: {}, Type: {}", url, methodType.name());
        RequestSpecification requestSpecification = given().headers(headers);
        if (requestBody != null) {
            logger.info("Request body: {}", requestBody);
            requestSpecification = requestSpecification.body(requestBody);
        }
        Long startTime = System.currentTimeMillis();
        Response response = requestSpecification.when().request(methodType.name(), url).then().extract().response();
        Long endTime = System.currentTimeMillis();
        logger.info("Response status code: {}", response.statusCode());
        logger.info("Response body: {}", response.asString());
        return logger.traceExit(Pair.of(response, (double) (endTime - startTime) / 1000));
    }

    /**
     * This method takes Endpoint URL and its Type and return response after
     * 
     * @param automationInputDTO of {@link AutomationInputDTO}
     * @return response
     * @since Dec 11, 2019
     */
    public Pair<Response, Double> hitEndpoint(String baseUrl, String url, Headers headers, HttpMethod methodType,
                    JSONObject requestBody) {
        String jsonString = null;
        if (requestBody != null) {
            jsonString = requestBody.toJSONString();
        }
        return hitEndpoint(baseUrl, url, headers, methodType, jsonString);
    }

}
