package com.infogain.automation.constants;

public enum FastTestExcelHeaders {
    SERIAL_NO("Sno", 1100),
    TEST_CASE_DESCRIPTION("Test case Description", 5100),
    SKIP_TEST("Skip Test", 1400),
    URL_PARAMETER("URL Parameter", 3600),
    PARAMS("Params", 4100),
    HEADERS("Header Json", 5400),
    INPUT_JSON("Input json", 6800),
    KEYS_VALIDATION("Key Validation", 6000),
    EXPECTED_OUTPUT("Expected Output", 10000),
    EXPECTED_HTTP_STATUS("Expected http status", 2500),
    ACTUAL_OUTPUT("Actual Output", 10000),
    ACTUAL_HTTP_STATUS("Actual http status", 2500),
    TEST_CASE_RESULT("Test case Result", 3600),
    FAILURES("Failures/Errors", 8000),
    RUNTIME("API Response Time", 5000),
    EXECUTION_DATE_TIME("Execution date time", 4900),
    COMMENTS("Comments", 5000);
    
    private String name;
    private int width;

    @SuppressWarnings("squid:UnusedPrivateMethod")
    private FastTestExcelHeaders(String name, int width) {
        this.name = name;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    /**
     * This method is finds the name of data type when given data type code
     * 
     * @param code data type code of enum
     * @return symbology
     * @since Aug 23, 2019
     */
    public static FastTestExcelHeaders getEnumValueByText(String text) {
        FastTestExcelHeaders fastTestExcelHeaders = null;
        for (FastTestExcelHeaders header : FastTestExcelHeaders.values()) {
            if (header.getName().equals(text)) {
                fastTestExcelHeaders = header;
            }
        }
        return fastTestExcelHeaders;
    }
}
