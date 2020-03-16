package com.infogain.automation.utilities;

import java.util.LinkedHashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static org.assertj.core.api.Assertions.assertThat;

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
        try {
            assertThat(actualObj).isEqualTo(expectedObj);
        } catch (AssertionError e) {
            throw new AssertionError(e.getMessage());
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

    public void isArray() {
        if (!(actualObj instanceof JSONArray)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expecting to be Array but was ");
            sb.append(getNameOfClass(actualObj));
            sb.append(".");
            throw new AssertionError(sb.toString());
        }
    }

    public void isObject() {
        if (!(actualObj instanceof JSONObject)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expecting to be Object but was ");
            sb.append(getNameOfClass(actualObj));
            sb.append(".");
            throw new AssertionError(sb.toString());
        }
    }

    public void contains() {
        if (actualObj != null || expectedObj != null) {
            if (actualObj instanceof JSONArray && expectedObj instanceof JSONArray) {
                assertThat(((JSONArray) actualObj).toArray()).contains(((JSONArray) expectedObj).toArray());
            } else if (actualObj instanceof JSONObject && expectedObj instanceof JSONObject) {
                assertObjectContains((JSONObject) actualObj, (JSONObject) expectedObj);
            } else {
                throw new AssertionError("Invalid method");
            }
        } else {
            throw new AssertionError("Invalid method");
        }
    }

    public void containsArrayEntries() {
        if (actualObj != null || expectedObj != null) {
            if (actualObj instanceof JSONArray && expectedObj instanceof JSONArray) {
                assertArrayContains((JSONArray) actualObj, (JSONArray) expectedObj);
            } else {
                throw new AssertionError("Invalid method");
            }
        } else {
            throw new AssertionError("Invalid method");
        }
    }

    private void assertArrayContains(JSONArray actual, JSONArray values) {
        if (actual.isEmpty() && values.isEmpty()) {
            return;
        }
        Set<Object> notFound = new LinkedHashSet<>();
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            if (!arrayContains(actual, value)) {
                notFound.add(value);
            }
        }
        if (!notFound.isEmpty()) {
            throw new AssertionError("not found: " + notFound);
        }
    }

    private boolean arrayContains(JSONArray array, Object value) {
        for (int i = 0; i < array.size(); i++) {
            Object element = array.get(i);
            if (containsOrEqual(element, value)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsOrEqual(Object o1, Object o2) {
        boolean toReturn = false;
        if (o1 == null) {
            toReturn = (o2 == null);
        } else if (o1.equals(o2)) {
            toReturn = true;
        } else if (o1 instanceof JSONObject && o2 instanceof JSONObject) {
            try {
                assertObjectContains((JSONObject) o1, (JSONObject) o2);
                toReturn = true;
            } catch (AssertionError e) {
                // return false
            }
        }
        return toReturn;
    }

    private void assertObjectContains(JSONObject o1, JSONObject o2) {
        assertThat(o1).containsAllEntriesOf(o2);
    }

    private String getNameOfClass(Object obj) {
        String name = obj.getClass().getSimpleName();
        if (name.equals("JSONObject")) {
            name = "Object";
        } else if (name.equals("JSONArray")) {
            name = "Array";
        }
        return name;
    }
}