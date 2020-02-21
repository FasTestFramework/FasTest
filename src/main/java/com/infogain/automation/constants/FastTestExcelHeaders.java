package com.infogain.automation.constants;

public enum FastTestExcelHeaders {
    SERIAL_NO("Sno"),
    TEST_CASE_DESCRIPTION("Test case Description"),
    SKIP_TEST("Skip Test"),
    URL_PARAMETER("URL Parameter"),
    PARAMS("Params"),
    HEADERS("Header Json"),
    INPUT_JSON("Input json"),
    KEYS_VALIDATION("Key Validation"),
    EXPECTED_OUTPUT("Expected Output"),
    EXPECTED_HTTP_STATUS("Expected http status"),
    ACTUAL_OUTPUT("Actual Output"),
    ACTUAL_HTTP_STATUS("Actual http status"),
    TEST_CASE_RESULT("Test case Result"),
    EXECUTION_DATE_TIME("Execution date time");

    private String text;

    @SuppressWarnings("squid:UnusedPrivateMethod")
    private FastTestExcelHeaders(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
