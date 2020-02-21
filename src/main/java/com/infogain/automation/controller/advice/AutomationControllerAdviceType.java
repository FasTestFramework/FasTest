package com.infogain.automation.controller.advice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationError;
import com.infogain.automation.dto.ErrorCodesDTO;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * <br>
 * Theme - Automation<br>
 * Feature - Peripheral Services - Design and Architecture<br>
 * Description - This interface has the basic controller advice methods checks the status of scanner etc
 * 
 * @author Neeraj Jain [3719097]
 * @version 1.0.0
 * @since 13-Jan-2020
 */
public interface AutomationControllerAdviceType {

    /**
     * This method construct the IP address of the incoming http request.
     * 
     * @param ip address of remote client from the incoming request
     * @return construct the IP address if the request is originated locally else return the ip passed in parameters as
     *         it is
     * @since 13-Jan-2020
     */
    default String getRequestOriginAddress(String ip) {
        String requestOriginIP = "Unable to fetch IP";
        try {
            if (InetAddress.getByName(ip).isLoopbackAddress()) {
                requestOriginIP = InetAddress.getLocalHost().getHostAddress();
            } else {
                requestOriginIP = ip;
            }
        } catch (UnknownHostException unknownHostException) {
            Logger logger = LogManager.getLogger(AutomationControllerAdviceType.class);
            logger.error("UnknownHostException Occured while fetching IP from incoming request {}",
                            ExceptionUtils.getStackTrace(unknownHostException));
        }
        return requestOriginIP;
    }

    /**
     * This method converts list of {@link ErrorCodesDTO} to list of {@link AutomationError}
     * 
     * @param errorCodesList list of {@link ErrorCodesDTO}
     * @return converted list of {@link AutomationError} with error code and error message
     * @since 26-Dec-2019
     */
    default List<AutomationError> convertErrorCodesDtoListToCXSErrorList(List<ErrorCodesDTO> errorCodesList) {
        return errorCodesList.stream().map(ErrorCodesDTO::convertToAutomationError)
                        .collect(Collectors.<AutomationError>toList());
    }
}
