package com.infogain.automation.errors;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * <br>
 * Theme - Automation<br>
 * Description - This class consists of automation testing framework related error codes. Description - This class is
 * 
 * @author Namit Jain [3696360]
 * @version 1.0.0
 * @since 08-Feb-2020
 */
public enum AutomationErrorCodes implements ErrorCodes {

    AUTOMATION_REQUESTBODY_INVALID_EXCEPTION("AUTOMATION.REQUESTBODY.INVALIDDATAEXCEPTION",
                    "Request body is invalid. Please try again."),
    AUTOMATION_REQUESTBODY_MISSING_EXCEPTION("AUTOMATION.REQUESTBODY.MISSINGEXCEPTION",
                    "Request body is missing. Please try again."),
    AUTOMATION_UNRECOGNIZED_PROPERTY_EXCEPTION("UNRECOGNIZED.PROPERTY.EXCEPTION",
                    "'{0}' property is not recognised as valid input parameter. Please try again."),
    AUTOMATION_BLANK_PROPERTY_EXCEPTION("UNRECOGNIZED.PROPERTY.EXCEPTION",
                    "Blank property is not a valid input parameter. Please try again."),
    AUTOMATION_INVALID_INPUT_EXCEPTION("INVALID.INPUT.EXCEPTION", "{0} field has invalid data. Please try again."),
    AUTOMATION_HOST_EXCEPTION("AUTOMATION.HOST.EXCEPTION",
                    "An error in fetching host IP or hostname has occured. Please try again."),
    AUTOMATION_IO_EXCEPTION("AUTOMATION.IO.EXCEPTION", "Input Exception Occurred. Please try again."),
    AUTOMATION_REQUEST_METHOD_NOT_SUPPORTED("AUTOMATION.REQUESTMETHOD.NOTSUPPORTEDEXCEPTION",
                    "Requested Method not supported"),
    AUTOMATION_INERNAL_SERVER_ERROR("AUTOMATION.INTERNALSERVER.ERROR", "An exception occured. Please try again"),
    AUTOMATION_INERNAL_SERVER_ERROR_WITH_CUSTOM_MESSAGE("AUTOMATION.INTERNALSERVER.ERROR", "{0}"),

    EXCEL_ROW_DATA_EMPTY_EXCEPTION("FASTTEST.ROWDATA.EMPTYEXCEPTION", "'rowData' cannot be empty. Please try again."),
    INPUT_EXCEL_FILE_NAME_EXCEPTION("FASTTEST.EXCELFILENAME.MISSINGEXCEPTION",
                    "'inputExcelFileName' field in 'rowData' is required. Please try again."),
    INPUT_EXCEL_SHEET_NAME_EXCEPTION("FASTTEST.EXCELSHEETNAME.MISSINGEXCEPTION",
                    "'inputExcelSheetName' field in 'rowData' is required. Please try again.");

    private String message;
    private String code;

    private AutomationErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
