package com.infogain.automation.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.exception.RandomGenerationAutomationException;

@Service
public class RegexConverter {
    private static final int MAX_LENGTH_OF_TOKEN_TO_BE_GENERATED = 40;
    private static final char OPTION_START_BRACKET = '(';
    private static final char LENGTH_END_BRACE = '}';
    private static final char LENGTH_START_BRACE = '{';
    private static final char ESCAPE_CHARACTER = '%';
    private static final char OPTIONS_END_BRACKET = ')';
    private static final char[] specialCharacter =
                    {'!', '"', '#', '$', '%', '&', '/', '(', ')', ',', '*', '+', ',', '-', '.', '/', '0'};

    private List<ErrorCodesDTO> errorCodes;
    private static final String characterRegex = "";
    private boolean isLengthGiven = false;
    private static final String INPUT_VALIDATION_FOR_A =
                    "([A-Z]-[A-Z])|((\\^,)?((([A-Z],)+(([A-Z]-[A-Z])|[A-Z]))|[A-Z]))";
    private static final String INPUT_VALIDATION_FOR_I = "(-?\\d+--?\\d+)|(\\^,(-?\\d+,)+((-?\\d+--?\\d+)|(\\d)))";
    private static final String INPUT_VALIDATION_FOR_S =
                    "(\\^,)?([\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7E],)*[\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7E]";
    private static final String INPUT_VALIDATION_FOR_N = "(\\^,)?(\\d,)*\\d";
    private static final String INPUT_VALIDATION_FOR_Z = "(\\^,)?([[:alnum:]],)*[[:alnum:]]";
    private static final String INPUT_VALIDATION_FOR_C = "(?:[\\x20-\\x7E],)*[\\x20-\\x7E]";
    private static final String INPUT_VALIDATION_FOR_D =
                    "((\\d+,\\d+(\\.\\d+)?-\\d+(\\.\\d+)?)|(\\d+(\\.\\d+)?-\\d+(\\.\\d+)?)|\\d+)";

    private enum AutomationRegex {

        A {
            @Override
            protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
                if (!options.matches(INPUT_VALIDATION_FOR_A)) {
                    throw new RandomGenerationAutomationException("regex expression is not correct");
                }
                options = options.replace(",", "");
                char[] optionsArray = options.toCharArray();
                List<Character> range = isRangeOperation(optionsArray);
                if (optionsArray[0] == '^' && !range.isEmpty()) {
                    return getAutomationRandomUtilityObject().generateRandomStringCapitalLettersRangeWithExclusion(
                                    lengthOfStringToBeGenerated, range.get(0), range.get(1),
                                    Arrays.copyOfRange(optionsArray, 1, optionsArray.length - 3));
                }
                if (!range.isEmpty()) {
                    return getAutomationRandomUtilityObject().generateRandomStringCapitalLettersRange(
                                    lengthOfStringToBeGenerated, range.get(0), range.get(1));
                } else if (optionsArray[0] == '^') {
                    return getAutomationRandomUtilityObject().generateRandomStringCapitalLettersWithExclusion(
                                    lengthOfStringToBeGenerated,
                                    Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
                } else {
                    return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                    lengthOfStringToBeGenerated, options);
                }
            }

            @Override
            protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
                return getAutomationRandomUtilityObject()
                                .generateRandomStringCapitalLetters(lengthOfStringToBeGenerated);
            }
        },

        a {
            @Override
            protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
                return A.generateWithOptions(lengthOfStringToBeGenerated, options).toLowerCase();
            }

            @Override
            protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
                return A.generateWithoutOptions(lengthOfStringToBeGenerated).toLowerCase();
            }

        },
        S {
            @Override
            protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
                if (!options.matches(INPUT_VALIDATION_FOR_S)) {
                    throw new RandomGenerationAutomationException("regex expression is not correct");
                }
                options = options.replace(",", "");
                char[] optionsArray = options.toCharArray();
                if (optionsArray[0] == '^') {
                    return getAutomationRandomUtilityObject().generateRandomStringSpecialCharacterWithExclusion(
                                    lengthOfStringToBeGenerated,
                                    Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
                } else {
                    return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharacters(
                                    lengthOfStringToBeGenerated, optionsArray);
                }
            }

            @Override
            protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
                return getAutomationRandomUtilityObject().generateRandomSpecialCharacter(lengthOfStringToBeGenerated);
            }

        },
        I {
            @Override
            protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
                if (!options.matches(INPUT_VALIDATION_FOR_I)) {
                    throw new RandomGenerationAutomationException("regex expression is not correct");
                }
                String[] optionsArray = options.split(",");
                List<String> range = isRangeOperation(optionsArray);
                System.out.println(range);
                if (optionsArray[0].equals("^") && !range.isEmpty()) {
                    int[] exclusions = new int[optionsArray.length - 2];
                    for (int i = 1; i < optionsArray.length - 1; i++) {
                        exclusions[i - 1] = Integer.valueOf(optionsArray[i]);
                    }
                    return String.valueOf(getAutomationRandomUtilityObject().generateRandomIntRangeWithExclusion(
                                    Integer.parseInt(range.get(0)), Integer.parseInt(range.get(1)), exclusions));
                }
                if (!range.isEmpty()) {
                    return String.valueOf(getAutomationRandomUtilityObject().generateRandomIntRange(
                                    Integer.parseInt(range.get(0)), Integer.parseInt(range.get(1))));
                } else {
                    System.out.println("yahhaaa");
                    int[] exclusions = new int[optionsArray.length - 1];
                    for (int i = 1; i < optionsArray.length; i++) {
                        exclusions[i - 1] = Integer.valueOf(optionsArray[i]);
                    }
                    return String.valueOf(getAutomationRandomUtilityObject().generateRandomIntRangeWithExclusion(
                                    Integer.MIN_VALUE, Integer.MAX_VALUE, exclusions));
                }
            }

            @Override
            protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
                return String.valueOf(getAutomationRandomUtilityObject().generateRandomInt());
            }
        },

        N {
            @Override
            protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
                if (!options.matches(INPUT_VALIDATION_FOR_N)) {
                    throw new RandomGenerationAutomationException("regex expression is not correct");
                }
                options = options.replace(",", "");
                char[] optionsArray = options.toCharArray();
                if (optionsArray[0] == '^') {
                    return getAutomationRandomUtilityObject().generateRandomNumericStringWithExclusions(
                                    lengthOfStringToBeGenerated,
                                    Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
                } else {
                    return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                    lengthOfStringToBeGenerated, options);
                }
            }

            @Override
            protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
                return getAutomationRandomUtilityObject().generateRandomStringOfNumbers(lengthOfStringToBeGenerated);
            }
        },

        Z {

            @Override
            protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
                if (!options.matches(INPUT_VALIDATION_FOR_Z)) {
                    throw new RandomGenerationAutomationException("regex expression is not correct");
                }
                options = options.replace(",", "");
                char[] optionsArray = options.toCharArray();
                if (optionsArray[0] == '^') {
                    return getAutomationRandomUtilityObject().generateRandomStringAlphaNumericWithExclusions(
                                    lengthOfStringToBeGenerated,
                                    Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
                } else {
                    return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                    lengthOfStringToBeGenerated, options);
                }
            }

            @Override
            protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
                return getAutomationRandomUtilityObject().generateRandomStringAlphaNumeric(lengthOfStringToBeGenerated);

            }

        },

        D {

            @Override
            protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
                if (!options.matches(INPUT_VALIDATION_FOR_D)) {
                    throw new RandomGenerationAutomationException("regex expression is not correct");
                }
                String[] optionsArray = options.split(",");
                List<String> range = isRangeOperation(optionsArray);
                if (!range.isEmpty() && optionsArray.length > 1) {
                    return getAutomationRandomUtilityObject().getRandomDoubleBetweenRangeWithScale(
                                    Double.parseDouble(range.get(0)), Double.parseDouble(range.get(1)),
                                    Integer.parseInt(optionsArray[0]));
                } else if (!range.isEmpty()) {
                    return String.valueOf(getAutomationRandomUtilityObject().getRandomDoubleBetweenRange(
                                    Double.parseDouble(range.get(0)), Double.parseDouble(range.get(1))));
                } else {
                    return getAutomationRandomUtilityObject().getRandomDoubleBetweenRangeWithScale(Integer.MIN_VALUE,
                                    Integer.MAX_VALUE, Integer.parseInt(optionsArray[0]));
                }
            }

            @Override
            protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
                return String.valueOf(getAutomationRandomUtilityObject().getRandomDoubleBetweenRange(Integer.MIN_VALUE,
                                Integer.MAX_VALUE));
            }
        },

        C {

            @Override
            protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
                if (!options.matches(INPUT_VALIDATION_FOR_C)) {
                    throw new RandomGenerationAutomationException("regex expression is not correct");
                }
                options = options.replace(",", "");
                char[] optionsArray = options.toCharArray();
                if (optionsArray[0] == '^') {
                    return getAutomationRandomUtilityObject().generateRandomStringEverythingWithExclusions(
                                    lengthOfStringToBeGenerated,
                                    Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
                } else {
                    return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                    lengthOfStringToBeGenerated, options);
                }

            }

            @Override
            protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
                return getAutomationRandomUtilityObject().generateRandomStringEverything(lengthOfStringToBeGenerated);
            }

        };


        protected abstract String generateWithOptions(int lengthOfStringToBeGenerated, String options);

        protected abstract String generateWithoutOptions(int lengthOfStringToBeGenerated);

        private static AutomationRandomUtility getAutomationRandomUtilityObject() {
            return new AutomationRandomUtility();
        }

        private static List<Character> isRangeOperation(char[] optionsArray) {
            List<Character> range = Collections.emptyList();
            int indexOfRangeOperator = optionsArray.length - 2;
            if (optionsArray.length > 3 && optionsArray[indexOfRangeOperator] == '-') {
                range = new ArrayList<>();
                range.add(0, optionsArray[indexOfRangeOperator - 1]);
                range.add(1, optionsArray[indexOfRangeOperator + 1]);
            }
            return range;
        }

        private static List<String> isRangeOperation(String[] optionsArray) {
            List<String> range = Collections.emptyList();
            String lastElement = optionsArray[optionsArray.length - 1];
            String[] rangeOperation = lastElement.split("-");
            if (rangeOperation.length == 2) {
                range = new ArrayList<>();
                range.add(rangeOperation[0]);
                range.add(rangeOperation[1]);
            } else if (rangeOperation.length == 3) {
                range = new ArrayList<>();
                range.add("-" + rangeOperation[1]);
                range.add(rangeOperation[2]);
            } else if (rangeOperation.length == 4) {
                range = new ArrayList<>();
                range.add("-" + rangeOperation[1]);
                range.add("-" + rangeOperation[3]);
            }
            return range;
        }

        public String generate(int lengthOfStringToBeGenerated, String options) {
            if (options != null && !options.isEmpty()) {
                return this.generateWithOptions(lengthOfStringToBeGenerated, options);
            } else {
                return this.generateWithoutOptions(lengthOfStringToBeGenerated);
            }
        }
    }

    public static void main(String[] args) {
        RegexConverter rc = new RegexConverter();
        String regex = "Dqjdn32iyr9281pBUC .WL3qo";
        System.out.println("Initial  : " + regex);
        System.out.println();
        rc.start(regex);
    }

    public String start(String regex) {
        errorCodes = new ArrayList<>();
        char[] regChar = regex.toCharArray();
        int indexForNextLookup = 0;
        int braceStartIndex;
        int braceEndIndex;
        int optionStartIndex;
        int optionEndIndex;
        int lastIndexOfConstant;
        int indexForNextOpenBrace = 0;
        char regexSymbol;
        int lengthOfStringToBeGenerated = new Random().nextInt(MAX_LENGTH_OF_TOKEN_TO_BE_GENERATED);
        StringBuilder generatedString = new StringBuilder(regex.length());
        String options = "";
        braceStartIndex = regex.indexOf(LENGTH_START_BRACE, indexForNextLookup);
        if (braceStartIndex == -1 || regex.indexOf(LENGTH_END_BRACE, indexForNextLookup) == -1) {
            errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.REGEX_FORMAT_EXCEPTION,
                            "There was no regex in the body to evaluate. e.g. of regex C{}, curly braces are mandotory"));
            throw new FastTestBadRequestException(errorCodes);
        }
        while (braceStartIndex != -1 && indexForNextLookup < regex.length()) {
            if (regChar[braceStartIndex - 1] == ESCAPE_CHARACTER) {
                StringBuilder temp = new StringBuilder(regex);
                temp.deleteCharAt(braceStartIndex - 1);
                regex = temp.toString();
                regChar = regex.toCharArray();
                indexForNextOpenBrace = braceStartIndex;
            } else {
                braceEndIndex = regex.indexOf(LENGTH_END_BRACE, braceStartIndex);
                if (regChar[braceStartIndex - 1] == OPTIONS_END_BRACKET) {
                    optionStartIndex = regex.indexOf(OPTION_START_BRACKET, indexForNextLookup);
                    optionEndIndex = braceStartIndex - 1;
                    options = regex.substring(optionStartIndex + 1, optionEndIndex);
                    regexSymbol = regChar[optionStartIndex - 1];
                    lastIndexOfConstant = optionStartIndex - 2;
                } else {
                    regexSymbol = regChar[braceStartIndex - 1];
                    lastIndexOfConstant = braceStartIndex - 2;
                }
                if (braceStartIndex + 1 == braceEndIndex) {
                    lengthOfStringToBeGenerated = new Random().nextInt(MAX_LENGTH_OF_TOKEN_TO_BE_GENERATED);
                    isLengthGiven = false;
                    System.out.println(lengthOfStringToBeGenerated);
                } else {
                    String length = regex.substring(braceStartIndex + 1, braceEndIndex);
                    isLengthGiven = true;
                    try {
                        lengthOfStringToBeGenerated = Integer.parseInt(length);
                    } catch (NumberFormatException numberFormatException) {
                        errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.REGEX_FORMAT_EXCEPTION,
                                        "Length of the string to be generated must be an integer value please check: "
                                                        + regexSymbol + generateOptionsErrorMessage(options) + "{"
                                                        + length + "}"));
                    }
                }
                generatedString.append(regex.substring(indexForNextLookup, lastIndexOfConstant + 1));
                generatedString.append(generateString(regexSymbol, lengthOfStringToBeGenerated, options));
                indexForNextLookup = braceEndIndex + 1;
                indexForNextOpenBrace = indexForNextLookup;
                options = "";
            }
            braceStartIndex = regex.indexOf(LENGTH_START_BRACE, indexForNextOpenBrace);
        }
        System.out.println(generatedString.toString());
        if (!errorCodes.isEmpty()) {
            throw new FastTestBadRequestException(errorCodes);
        }
        return generatedString.toString();
    }

    public String generateString(char regexSymbol, int lengthOfStringToBeGenerated, String options) {
        if (regexSymbol == 'W' && options.isEmpty()) {
            return "";
        } else if (regexSymbol == 'W' && !options.isEmpty()) {
            return generateConstantString(lengthOfStringToBeGenerated, options);
        } else {
            try {
                return AutomationRegex.valueOf(String.valueOf(regexSymbol)).generate(lengthOfStringToBeGenerated,
                                options);
            } catch (IllegalArgumentException illegalArgumentException) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.REGEX_FORMAT_EXCEPTION,
                                regexSymbol + generateOptionsErrorMessage(options)
                                                + generateLengthErrorMessage(lengthOfStringToBeGenerated)
                                                + " is not a valid regex symbol"));
                return "";
            } catch (RandomGenerationAutomationException randomGenerationAutomationException) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.REGEX_FORMAT_EXCEPTION,
                                "InvalidRegex: " + regexSymbol + generateOptionsErrorMessage(options)
                                                + generateLengthErrorMessage(lengthOfStringToBeGenerated) + " "
                                                + randomGenerationAutomationException.getMessage()));
                return "";
            }
        }
    }

    private String generateOptionsErrorMessage(String options) {
        return !options.isEmpty() ? "(" + options + ")" : "";
    }

    private String generateLengthErrorMessage(int lengthOfStringToBeGenerated) {
        return isLengthGiven ? "{" + lengthOfStringToBeGenerated + "}" : "{}";
    }

    public String generateConstantString(int lengthOfStringToBeGenerated, String options) {
        StringBuilder generated = new StringBuilder(lengthOfStringToBeGenerated);
        while (lengthOfStringToBeGenerated-- > 0) {
            generated = generated.append(options);
        }
        return generated.toString();
    }

}
