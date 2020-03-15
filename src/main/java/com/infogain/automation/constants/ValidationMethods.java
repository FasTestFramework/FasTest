package com.infogain.automation.constants;

public enum ValidationMethods {
    CONTAINS("contains(String)"),
    IS_EQUAL_TO("isEqualTo(Object)"),
    IS_NOT_EQUAL_TO("isNotEqualTo(Object)"),
    does_Not_Contain("doesNotContain(String)"),
    HAS_SIZE("hasSize(int)"),
    IS_BLANK("isBlank()"),
    IS_EMPTY("isEmpty()"),
    IS_NULL("isNull()"),
    IS_NULL_OR_EMPTY("isNullOrEmpty()"),
    IS_SUBSTRING_OF("isSubstringOf(String)"),
    MATCHES("matches(String)"),
    IS_IN("isIn(String[])"),
    IS_NOT_IN("isNotIn(String[])"),
    IS_NOT_NULL("isNotNull()"),
    IS_NOT_BLANK("isNotBlank()"),
    IS_NOT_EMPTY("isNotEmpty()"),
    IGNORE("ignore()"),
    NOT_NULL("notNull()"),
    IS_ARRAY("isArray()"),
    IS_OBJECT("isObject()");

    private String method;

    private ValidationMethods(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

}
