package com.infogain.automation.utilities;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.infogain.automation.exception.CustomValidationFailure;

import static org.assertj.core.api.Assertions.assertThat;

public class AutomationCustomValidationUtility {
    private static final Logger logger = LogManager.getLogger(AutomationCustomValidationUtility.class);

    private StringBuilder errorMessage;
    private StringBuilder successMessage;
    private Object actualObj;
    private Object expectedObj;

    public static void main(String[] args) {
        String expectedXml =
                        "<rings>\n" +
                            "  <bearer>\n" +
                            "    <name>Frodo</name>\n" +
                            "    <ring>\n" +
                            "      <name>one ring</name>\n" +
                            "      <createdBy>Sauron</createdBy>\n" +
                            "    </ring>\n" +
                            "  </bearer>\n" +
                            "</rings>";

                    // No matter how your xml string is formated, isXmlEqualTo is able to compare it's content with another xml String.
                    String oneLineXml = "<rings><bearer><name>Frodo</name><ring><name>one ring</name><createdBy>Sauron</createdBy></ring></bearer></rings>";
                    //assertThat(oneLineXml).isXmlEqualTo(expectedXml);

                    String xmlWithNewLine =
                                    "<rings>\n\n" +
                                                    "  <bearer>\n" +
                                                    "    <name>Frodo</name>\n" +
                                                    "    <ring>\n" +
                                                    "      <name>one ring</name>\n" +
                                                    "      <createdBy>Sauron</createdBy>\n" +
                                                    "    </ring>\n" +
                                                    "  </bearer>\n" +
                                                    "</rings>";
                    assertThat(xmlWithNewLine).isXmlEqualTo(expectedXml);

                    // You can compare it with oneLineXml
                    assertThat(xmlWithNewLine).isXmlEqualTo(oneLineXml);

//        // to be removed
//        Map<Object, List<String>> inputMap = new LinkedHashMap<>();
//        // inputMap.put(null, "notNull();isNotNull();containsOnlyDigits()");
//        inputMap.put("myName",
//                        Arrays.asList("contains(\"Name\",\"my22\")", "matches(\".*\")", "isEqualTo(\"aa\")", "startsWith(\"my\")", "asdgdf()", "hasSizeBetween(10,12)", "matches(234)", "isSubstringOf(\"hellomyName is Namit\")"));
//        // inputMap.put("myName", "hasSizeBetween(10,12)");
//        // inputMap.put(true, "isNotEqualTo(\"false\");isNotNull()");
//        // inputMap.put(12, "isGreaterThan(13);isLessThan(13)");
//        // inputMap.put(12.55, "isEqualTo(12.5)");
//        // inputMap.put(new SendMailRequestDTO(), "hasFieldOrProperty(\"base64String\")");
//        AutomationCustomValidationUtility testObject = new AutomationCustomValidationUtility();
//        inputMap.forEach((k, v) -> {
//            try {
//                testObject.validate(k, k, v);
//                System.out.println("Success");
//            } catch (CustomValidationFailure cvf) {
//                System.out.print(cvf.getMessage());
//            }
//            System.out.println("------------------------------");
//        });
    }

    public void validate(Object actualObj, Object expectedObj, List<String> methodsToExecute) throws CustomValidationFailure {
        logger.info("\nValidating value: {} against custom validations: {}", String.valueOf(actualObj),
                        methodsToExecute);
        this.actualObj = actualObj;
        this.expectedObj = expectedObj;
        errorMessage = new StringBuilder();
        successMessage = new StringBuilder();
        Object assertObject = checkTestClass(actualObj);
        Class<?> assertClass = assertObject.getClass();
        for (String methodNameWithParam : methodsToExecute) {
            if (!validateMethodSyntax(methodNameWithParam)) {
                continue;
            }
            String methodName = null;
            String paramString = null;
            Matcher matcher2 = Pattern.compile("^(.*?)\\((.*)\\)$").matcher(methodNameWithParam);
            while (matcher2.find()) {
                methodName = matcher2.group(1);
                paramString = matcher2.group(2);
            }
            Object[] paramsObject = new Object[0];
            Class<?>[] paramClasses = new Class[0];
            if (StringUtils.isNotEmpty(paramString)) {
                String[] paramsString = paramString.split(",");
                paramsObject = new Object[paramsString.length];
                paramClasses = new Class[paramsString.length];
                convertParamsToObjectsAndClasses(paramsObject, paramClasses, paramsString);
            }
            methodValidation(assertObject, assertClass, methodNameWithParam, methodName, paramsObject, paramClasses);
        }
        if (errorMessage.length() != 0) {
            String errorMsg = successMessage.append("\n").append(errorMessage).toString();
            errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
            throw new CustomValidationFailure(errorMsg);
        }
    }

    private boolean validateMethodSyntax(String methodNameWithParam) {
        String paramRegex = "(true|false|\\d+(\\.[\\d]*)?|\\\".*?\\\")";
        String commaSeperatedParamRegex = BeanUtil.getBean(AutomationUtility.class).fetchCommaSeperatedValueRegex(paramRegex);
        String methodRegex = "^(.*?)\\(("+commaSeperatedParamRegex+ ")?\\)$";
        boolean matches = methodNameWithParam.matches(methodRegex);
        if(!matches) {
            errorMessage.append(methodNameWithParam).append(" : Invalid method Syntax\n\n");
        }
        return matches;
    }

    @SuppressWarnings("unchecked")
    private Object checkTestClass(Object test) {
        Object assertObject = null;
        if (test == null) {
            assertObject = assertThat(test);
        } else {
            switch (test.getClass().getSimpleName()) {
                case "String":
                    String s = (String) test;
                    assertObject = assertThat(s);
                    break;
                case "Boolean":
                    Boolean b = (Boolean) test;
                    assertObject = assertThat(b);
                    break;
                case "Integer":
                    Integer i = (Integer) test;
                    assertObject = assertThat(i);
                    break;
                case "Double":
                    Double d = (Double) test;
                    assertObject = assertThat(d);
                    break;
                case "JSONArray":
                    JSONArray jsonArray = (JSONArray) test;
                    assertObject = assertThat(jsonArray);
                    break;
                case "JSONObject":
                    JSONObject jsonObject = (JSONObject) test;
                    assertObject = assertThat(jsonObject);
                    break;
                default:
                    assertObject = assertThat(test);
            }
        }
        return assertObject;
    }

    private void convertParamsToObjectsAndClasses(Object[] paramsObject, Class<?>[] paramClasses,
                    String[] paramsString) {
        for (int x = 0; x < paramsString.length; x++) {
            Object temp = paramsString[x];
            String tempString = (String) temp;
            if (tempString.startsWith("\"") && tempString.endsWith("\"")) {
                paramsObject[x] = tempString.substring(1, tempString.length() - 1);
                paramClasses[x] = String.class;
            } else if (tempString.equals("true") || tempString.equals("false")) {
                paramsObject[x] = Boolean.parseBoolean(tempString);
                paramClasses[x] = Boolean.class;
            } else if (Pattern.compile("^\\d+$").matcher(tempString).matches()) {
                paramsObject[x] = Integer.parseInt(tempString);
                paramClasses[x] = Integer.class;
            } else if (Pattern.compile("^\\d+(\\.[\\d]*)?$").matcher(tempString).matches()) {
                paramsObject[x] = Double.parseDouble(tempString);
                paramClasses[x] = Double.class;
            } else {
                paramsObject[x] = temp;
                paramClasses[x] = Object.class;
            }
        }
    }

    private void methodValidation(Object assertObject, Class<?> assertClass, String methodNameWithParam,
                    String methodName, Object[] paramsObject, Class<?>[] paramClasses) {
        Object testObjectClass;
        Method methodFetched = null;
        Method[] customAssertionsMethods = AutomationCustomAssertions.class.getDeclaredMethods();
        Method customAssertionsMethodFound =
                        extractMethodIfFound(methodName, paramsObject, paramClasses, customAssertionsMethods);
        try {
            if (customAssertionsMethodFound != null) {
                methodFetched = customAssertionsMethodFound;
                testObjectClass = AutomationCustomAssertions.class.getConstructor(Object.class, Object.class)
                                .newInstance(actualObj, expectedObj);
            } else {
                Method[] assertJMethods = assertClass.getMethods();
                methodFetched = extractMethodIfFound(methodName, paramsObject, paramClasses, assertJMethods);
                testObjectClass = assertObject;
            }
            paramsObject = Arrays.stream(paramsObject).filter(Objects::nonNull).toArray(Object[]::new);
            if (methodFetched != null) {
                methodFetched.invoke(testObjectClass, paramsObject);
                successMessage.append(methodNameWithParam).append(" : Validations Pass!\n");
            } else {
                errorMessage.append(methodNameWithParam).append(" : Invalid method\n\n");
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException
                        | NoSuchMethodException | SecurityException e) {
            if (e.getCause() instanceof AssertionError) {
                AssertionError assertionError = (AssertionError) e.getCause();
                assertionErrorHandling(methodNameWithParam, assertionError);
            } else {
                errorMessage.append(methodNameWithParam + " : Invalid method\n\n");
            }
        }
    }

    private Method extractMethodIfFound(String methodName, Object[] paramsObject, Class<?>[] paramClasses,
                    Method[] methodsFetched) {
        Method methodFetched = null;
        for (int j = 0; j < methodsFetched.length; j++) {
            if (Modifier.isPublic(methodsFetched[j].getModifiers()) && methodsFetched[j].getName().equals(methodName)
                            && isMethodFound(paramsObject, paramClasses, methodsFetched[j])) {
                methodFetched = methodsFetched[j];
                break;
            }
        }
        return methodFetched;
    }

    private void assertionErrorHandling(String methodNameWithParam, AssertionError assertionError) {
        String assertionErrorMessage = assertionError.getMessage();
        errorMessage.append(methodNameWithParam).append(" : ");
        boolean insertNewLine = true;
        int i;
        boolean doBreak = false;
        for (i = assertionErrorMessage.length() - 1; i >= 0; i--) {
            char lastChar = assertionErrorMessage.charAt(i);
            if (lastChar == '\n') {
                insertNewLine = false;
                doBreak = true;
            } else if (lastChar != ' ') {
                doBreak = true;
            }
            if (doBreak) {
                break;
            }
        }
        if (!insertNewLine) {
            assertionErrorMessage = assertionErrorMessage.substring(0, i - 1);
        }
        errorMessage.append(assertionErrorMessage).append("\n\n");
    }

    private boolean isMethodFound(Object[] paramsObject, Class<?>[] paramClasses, Method methodFetched) {
        Class<?>[] parameters = methodFetched.getParameterTypes();
        if (parameters.length == 0) {
            return paramClasses.length == 0;
        } else if (parameters.length > paramClasses.length) {
            return false;
        }
        for (int k = 0; k < parameters.length; k++) {
            if (parameters[k].isArray()) {
                for (int l = k; l < paramClasses.length; l++) {
                    if (isClassEqualsOrAssignable(parameters[k].getComponentType(), paramClasses[l])) {
                        return false;
                    }
                }
                convertParams(paramsObject, k, parameters[k].getComponentType());
                return true;
            } else if (isClassEqualsOrAssignable(parameters[k], paramClasses[k])) {
                return false;
            }
        }
        return true;
    }

    private boolean isClassEqualsOrAssignable(Class<?> expectedClass, Class<?> actualClass) {
        return !expectedClass.equals(actualClass) && !expectedClass.isAssignableFrom(actualClass)
                        && !(expectedClass.isPrimitive() && getWrapperClass(expectedClass).equals(actualClass));
    }

    private Class<?> getWrapperClass(Class<?> primitiveClass) {
        return Array.get(Array.newInstance(primitiveClass, 1), 0).getClass();
    }

    private <T> void convertParams(Object[] paramsObject, int k, Class<T> classToConvert) {
        int z = 0;
        Object paramArray = Array.newInstance(classToConvert, paramsObject.length - k);
        for (int i = 0; i < paramsObject.length; i++) {
            if (i >= k) {
                Array.set(paramArray, z++, safeCastTo(paramsObject[i], classToConvert));
                paramsObject[i] = null;
            }
        }
        paramsObject[k] = paramArray;
    }

    private <T> T safeCastTo(Object obj, Class<T> to) {
        if (obj != null) {
            Class<?> c = obj.getClass();
            if (to.isAssignableFrom(c)) {
                return to.cast(obj);
            }
        }
        return null;
    }


}
