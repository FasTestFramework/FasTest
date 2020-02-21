package com.infogain.automation.constants;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class contains all the application constants
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
public final class AutomationConstants {

    private AutomationConstants() {
        throw new UnsupportedOperationException(AutomationConstants.class.getName());
    }

    public static final String CLAIM_ID_JSON_PATH = "claimId/ClaimTest.json";
    public static final String CLAIM_TEST = "PeripheralClaimTest";
    public static final String TEST_CLASS_PACKAGE_NAME = "com.infogain.automation.tests.";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";
    public static final String TEST_CASE_RESULT_PASS = "PASS";
    public static final String TEST_CASE_RESULT_FAIL = "FAIL";
    public static final String AUTOMATIONSERVER_DEFAULT_TEST = "AutomationDefaultTest";
    
    public static final String FASTEST_SERVER_PORT = "server.port";
    public static final String APPLICATION_PROPERTIES_SHEET_NAME = "fastest.testClass";
    public static final String FASTEST_EXCEL_SHEET_NAME = "fastest.excelSheetName";
    public static final String FASTEST_OUTPUT_FOLDER_PATH = "fastest.outputFolderPath";
    public static final String FASTEST_INPUT_JSON_FOLDER_PATH = "fastest.inputJsonFolderPath";
    public static final String FASTEST_INPUT_EXCEL_FOLDER_PATH = "fastest.inputExcelFolderPath";
    public static final String FASTEST_HEADER_TO_USE = "fastest.headersToUse";
    public static final String FASTEST_HOST_NAME = "fastest.HostName";
    public static final String FASTEST_PORT = "fastest.Port";
    public static final String FASTEST_URL_PREFIX = "fastest.";
    public static final String FASTEST_URL_TO_USE = "fastest.tokenGenerateUrl";
    public static final String FASTEST_BODY_TO_USE = "fastest.bodyToUse";
    public static final String FASTEST_RESPONSE_KEY = "fastest.tokenFetchResponseKey";
    public static final String FASTEST_ADD_TOKEN_IN = "fastest.addTokenIn";
    public static final String FASTEST_TOKEN_PROPERTY_NAME = "fastest.tokenReplaceRequestKey";

    public static final String FASTEST_RELEASE_URL = "fastest.releaseUrl";
    public static final String FASTEST_CLAIM_URL = "fastest.claimUrl";
    public static final String FASTEST_SERVER_INFO_URL = "fastest.serverInfoUrl";
    public static final String FASTEST_HEALTH_CHECK_URL = "fastest.serverHealthCheckUrl";
    public static final String FASTEST_CONNECTED_DEVICE_URL = "fastest.serverConnectedDeviceUrl";
    public static final String FASTEST_PRINT_BIN_LABEL_URL = "fastest.printDymoLabelUrl";
    public static final String FASTEST_SCAN_BARCODE_URL = "fastest.scanBarcodeUrl";
    public static final String FASTEST_OPEN_CASHDRAWER_URL = "fastest.openCashDrawerUrl";
    public static final String FASTEST_PRINT_SHIPPING_LABEL_URL = "fastest.printZebraLabelUrl";
    public static final String FASTEST_SUBSCRIPTIONS = "fastest.subscribeToautomationServer";
    public static final String FASTEST_DELETE_SUBSCRIPTIONS = "fastest.deleteSubscriptionsUrl";
    public static final String FASTEST_PRINT_JSON_RECEIPT_URL = "fastest.printJsonReceiptUrl";
    public static final String FASTEST_PRINT_XML_RECEIPT_URL = "fastest.printXMLReceiptUrl";
    public static final String FASTEST_PRINT_DELIVERY_LABEL_URL = "fastest.printDymoDeliveryLabelUrl";
}
