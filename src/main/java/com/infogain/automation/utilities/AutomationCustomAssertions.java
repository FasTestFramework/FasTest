package com.infogain.automation.utilities;

public class AutomationCustomAssertions {
    private Object actualObj;
    private Object expectedObj;

    public AutomationCustomAssertions(Object actualObj, Object expectedObj) {
        this.actualObj = actualObj;
        this.expectedObj = expectedObj;
    }

    public void ignore() {
        // This method does no validations
    }

    public void notNull() {
        if (actualObj == null) {
            throw new AssertionError("Expecting actual not to be null");
        }
    }

    public void hasSameContent() {
        hasSameContent(expectedObj);
    }

    public void isEqual() {
        if (!actualObj.getClass().equals(expectedObj.getClass())
                        || !String.valueOf(actualObj).equals(String.valueOf(expectedObj))) {
            throw new AssertionError("Expecting actual not to be null");
        }
    }

    public void hasSameContent(Object expected) {
        String actualObjString = String.valueOf(actualObj);
        String expectedObjString = String.valueOf(expected);
        if (!actualObjString.equals(expectedObjString)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expecting:\n<\"");
            sb.append(actualObjString);
            sb.append("\">\nto be equal to:\n<\"");
            sb.append(expectedObjString);
            sb.append("\">\nbut was not.");
            throw new AssertionError(sb.toString());
        }
    }
}
