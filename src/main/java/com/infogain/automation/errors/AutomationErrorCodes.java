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
                    "'inputExcelSheetName' field in 'rowData' is required. Please try again."),
    REGEX_FORMAT_EXCEPTION("FASTTEST.REGEXFORMAT.EXCEPTION",
                    "'instructionsToGenerateRandomData' field has invalid data please refer to message: '{0}' . Please try again."),

    AUTOMATION_RANDOM_GENERATION_LENGTH_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDLENGTHEXCEPTION",
                    "'length' field has invalid data : '{0}'. Length must be positive"),

    AUTOMATION_RANDOM_GENERATION_INVALID_DATA_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDDATAEXCEPTION",
                    "'{0}' field has invalid data: '{1}'. Please try again."),

    AUTOMATION_RANDOM_GENERATION_INCLUSION_EXCLUSION_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDCHAREXCEPTION",
                    "Cannot have both 'inclusion' and 'exclusion' arrays."),

    AUTOMATION_RANDOM_FIELD_MISSING_EXCEPTION("FASTTEST.RANDOMGENERATION.MISSINGEXCEPTION",
                    "'{0}' field is required. Please try again."),

    AUTOMATION_RANDOM_GENERATION_RANGE_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDRANGEEXCEPTION",
                    "range operations must have both lower and upper bound"),

    AUTOMATION_RANDOM_GENERATION_END_RANGE_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDRANGEEXCEPTION",
                    "must have an end range"),

    AUTOMATION_RANDOM_STRING_GENERATION_CONSTANT_CHARACTER_EXCEPTION(
                    "AUTOMATION.RANDOMGENERATION.INVALIDRANGEEXCEPTION",
                    "cannot have constant char with any other char"),

    AUTOMATION_RANDOM_STRING_GENERATION_INVALID_ALPHABETS_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDCHAREXCEPTION",
                    "Characters in field(s) '{0}' should be '{1}' alphabets"),

    AUTOMATION_RANDOM_GENERATION_PRECISION_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDPRECISIONEXCEPTION",
                    "'precision' field has invalid data : '{0}'. It cannot be negative"),

    AUTOMATION_RANDOM_GENERATION_INVALID_RANGE_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDRANGEEXCEPTION",
                    "lower bound : '{0}' should be less than the  upper bound '{1}' "),

    AUTOMATION_RANDOM_GENERATION_INVALID_INTEGER_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDRANGEEXCEPTION",
                    "One of numbers provided in '{0}' does not lie within permissible range of integers"),

    AUTOMATION_RANDOM_GENERATION_CONSTANT_CHARACTER_STRING_EXCEPTION("AUTOMATION.RANDOMGENERATION.INVALIDCHAREXCEPTION",
                    "cannot include inclusions,exclusions or range in case of constant character"),

    AUTOMATION_BVA_INVALID_DATA_EXCEPTION("AUTOMATION.BOUNDARYVALUEANALYSIS.INVALIDDATAEXCEPTION",
                    "'{0}' field has invalid data: '{1}'. {2} Please try again."),

    AUTOMATION_BVA_MISSING_DATA_EXCEPTION("AUTOMATION.BOUNDARYVALUEANALYSIS.MISSINGEXCEPTION",
                    "'{0}' field is required. Please try again."),

    AUTOMATION_BVA_INVAID_MIN_MAX_DATA_EXCEPTION("AUTOMATION.BOUNDARYVALUEANALYSIS.INVALIDDATAEXCEPTION",
                    "'min' field in 'metadata' has invalid data({0}) as it cannot be greater than given 'max'({1}). Please try again."),

    AUTOMATION_SEND_MAIL_INVALID_BASE64_DATA_EXCEPTION("AUTOMATION.SENDMAIL.INVALIDDATAEXCEPTION",
                    "'base64String' field has invalid data({0}). {1} Please try again."),
    AUTOMATION_SEND_MAIL_MISSING_BASE64_DATA_EXCEPTION("AUTOMATION.SENDMAIL.MISSINGEXCEPTION",
                    "'base64String' field is required. Please try again.");

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
