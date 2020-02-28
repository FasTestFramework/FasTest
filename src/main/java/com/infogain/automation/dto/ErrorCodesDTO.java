package com.infogain.automation.dto;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.errors.ErrorCodes;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * <br>
 * Theme - Automation<br>
 * Feature - Peripheral Services - Design and Architecture<br>
 * Description - This class is a DTO class for Error Codes
 * 
 * @author Namit Jain [3696360]
 * @version 1.0.0
 * @since 24-Dec-2019
 */
public class ErrorCodesDTO implements Serializable {

    private static final long serialVersionUID = -2580883535345000380L;
    private static Logger logger = LogManager.getLogger(ErrorCodesDTO.class);

    private static final int MAX_ERROR_DATA_LENGTH = 200;

    private ErrorCodes errorCode;
    private String message;

    /**
     * This method returns error message
     * 
     * @return error message
     * @since 27-Dec-2019
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method returns error code
     * 
     * @return error code
     * @since 27-Dec-2019
     */
    public String getCode() {
        return errorCode.getCode();
    }

    /**
     * This method returns new instance of {@link ErrorCodesDTO} using Error code's code and formatted message using
     * data provided as varargs
     * 
     * @param errorCode {@link ErrorCodes} containing error code and unformatted error message
     * @param data varargs which contains all arguments data which will format our error message
     */
    public ErrorCodesDTO(ErrorCodes errorCode, Object... data) {
        this.errorCode = errorCode;
        String formattedMessage = errorCode.getMessage();
        if (data != null && data.length > 0) {
            try {
                int dataLength = new MessageFormat(formattedMessage.replace("'", ""))
                                .getFormatsByArgumentIndex().length;
                Object[] stringData = new Object[dataLength];
                for (int i = 0; i < dataLength; i++) {
                    if (i < data.length) {
                        stringData[i] = String.valueOf(data[i]);
                    } else {
                        stringData[i] = "";
                    }
                }
                logger.error(() -> toString(errorCode.getCode(), formatString(errorCode.getMessage(), stringData)));
                for (int i = 0; i < stringData.length; i++) {
                    String tempString = (String) stringData[i];
                    if (tempString.length() > MAX_ERROR_DATA_LENGTH) {
                        tempString = tempString.substring(0, MAX_ERROR_DATA_LENGTH - 3).concat("...");
                    }
                    stringData[i] = tempString;
                }
                formattedMessage = formatString(formattedMessage, stringData);
            } catch (IllegalArgumentException e) {
                // No handling or logging is required as if MessageFormat is not able to format then error code's
                // message will be taken
            }
        }
        this.message = formattedMessage;
    }

    /**
     * This method converts {@link ErrorCodesDTO} to {@link AutomationError}
     * 
     * @return converted instance of {@link AutomationError} with error code and error message
     * @since 26-Dec-2019
     */
    public AutomationError convertToAutomationError() {
        return new AutomationError(getCode(), getMessage());
    }

    @Override
    public String toString() {
        return toString(getCode(), getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, message);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ErrorCodesDTO)) {
            return false;
        }
        ErrorCodesDTO other = (ErrorCodesDTO) obj;
        return Objects.equals(errorCode.getCode(), other.errorCode.getCode()) && Objects.equals(message, other.message);
    }

    /**
     * This method returns String representation of {@link ErrorCodesDTO}
     * 
     * @param code the error code
     * @param message the error message
     * @return the string representation of {@link ErrorCodesDTO}
     * @since 27-Dec-2019
     */
    private String toString(String code, String message) {
        return "ErrorCode [code=" + code + ", message=" + message + "]";
    }

    /**
     * This method formats the pattern with the help of arguments provided using {@link MessageFormat}
     * 
     * @param pattern the message which needs to be formatted
     * @param arguments varargs which contains all arguments data which will format our pattern
     * @return formatted string
     * @since 27-Dec-2019
     */
    private String formatString(String pattern, Object... arguments) {
        pattern = pattern.replace("\"", "'").replace("'", "''");
        return MessageFormat.format(pattern, arguments);
    }

}
