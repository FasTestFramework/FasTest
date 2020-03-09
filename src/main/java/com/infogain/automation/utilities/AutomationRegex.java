package com.infogain.automation.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.infogain.automation.exception.RandomGenerationAutomationException;

public enum AutomationRegex {
    A {
        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            options = options.replace(",", "");
            char[] optionsArray = options.toCharArray();
            List<Character> range = isRangeOperation(optionsArray);
            if (optionsArray[0] == '^' && !range.isEmpty()) {
                return getAutomationRandomUtilityObject().generateRandomStringCapitalLettersRangeWithExclusion(
                                lengthOfStringToBeGenerated, range.get(0), range.get(1),
                                Arrays.copyOfRange(optionsArray, 1, optionsArray.length - 3));
            }
            if (!range.isEmpty()) {
                return getAutomationRandomUtilityObject().generateRandomStringLettersRange(lengthOfStringToBeGenerated,
                                range.get(0), range.get(1));
            } else if (optionsArray[0] == '^') {
                return getAutomationRandomUtilityObject().generateRandomStringCapitalLettersWithExclusion(
                                lengthOfStringToBeGenerated, Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
            } else {
                return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                lengthOfStringToBeGenerated, options);
            }
        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            return getAutomationRandomUtilityObject().generateRandomStringCapitalLetters(lengthOfStringToBeGenerated);
        }

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_A);
        }
    },

    a {
        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            options = options.replace(",", "");
            char[] optionsArray = options.toCharArray();
            List<Character> range = isRangeOperation(optionsArray);
            if (optionsArray[0] == '^' && !range.isEmpty()) {
                return getAutomationRandomUtilityObject().generateRandomStringSmallLettersRangeWithExclusion(
                                lengthOfStringToBeGenerated, range.get(0), range.get(1),
                                Arrays.copyOfRange(optionsArray, 1, optionsArray.length - 3));
            }
            if (!range.isEmpty()) {
                return getAutomationRandomUtilityObject().generateRandomStringLettersRange(lengthOfStringToBeGenerated,
                                range.get(0), range.get(1));
            } else if (optionsArray[0] == '^') {
                return getAutomationRandomUtilityObject().generateRandomStringSmallLettersWithExclusion(
                                lengthOfStringToBeGenerated, Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
            } else {
                return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                lengthOfStringToBeGenerated, options);
            }
        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            return getAutomationRandomUtilityObject().generateRandomStringCapitalLetters(lengthOfStringToBeGenerated);
        }

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_SMALL_A);
        }

    },
    M {

        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            options = options.replace(",", "");
            char[] optionsArray = options.toCharArray();
            List<Character> range = isRangeOperation(optionsArray);
            if (optionsArray[0] == '^' && !range.isEmpty()) {
                return getAutomationRandomUtilityObject().generateRandomStringCapitalSmallMixRangeWithExclusions(
                                lengthOfStringToBeGenerated, range.get(0), range.get(1),
                                Arrays.copyOfRange(optionsArray, 1, optionsArray.length - 3));
            }
            if (!range.isEmpty()) {
                return getAutomationRandomUtilityObject().generateRandomStringCapitalSmallMixRange(
                                lengthOfStringToBeGenerated, range.get(0), range.get(1));
            } else if (optionsArray[0] == '^') {
                return getAutomationRandomUtilityObject().generateRandomStringCapitalSmallMixWithExclusions(
                                lengthOfStringToBeGenerated, Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
            } else {
                return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                lengthOfStringToBeGenerated, options);
            }
        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            return getAutomationRandomUtilityObject().generateRandomStringCapitalLetters(lengthOfStringToBeGenerated);
        }

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_M);
        }

    },
    S {
        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            options = options.replace(",", "");
            char[] optionsArray = options.toCharArray();
            if (optionsArray[0] == '^') {
                return getAutomationRandomUtilityObject().generateRandomStringSpecialCharacterWithExclusion(
                                lengthOfStringToBeGenerated, Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
            } else {
                return getAutomationRandomUtilityObject()
                                .generateRandomStringOutOfGivenCharacters(lengthOfStringToBeGenerated, optionsArray);
            }
        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            return getAutomationRandomUtilityObject().generateRandomSpecialCharacter(lengthOfStringToBeGenerated);
        }

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_S);
        }

    },
    I {
        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            String[] optionsArray = options.split(",");
            List<String> range = isRangeOperation(optionsArray);
            if (optionsArray[0].equals("^")) {
                int[] exclusions = new int[optionsArray.length - 2];
                for (int i = 1; i < optionsArray.length - 1; i++) {
                    exclusions[i - 1] = Integer.valueOf(optionsArray[i]);
                }
                if (!range.isEmpty()) {
                    return String.valueOf(getAutomationRandomUtilityObject().generateRandomIntRangeWithExclusion(
                                    Integer.parseInt(range.get(0)), Integer.parseInt(range.get(1)), exclusions));
                } else {
                    return String.valueOf(getAutomationRandomUtilityObject().generateRandomIntRangeWithExclusion(
                                    Integer.MIN_VALUE, Integer.MAX_VALUE, exclusions));
                }
            } else {
                return String.valueOf(getAutomationRandomUtilityObject().generateRandomIntRange(
                                Integer.parseInt(range.get(0)), Integer.parseInt(range.get(1))));
            }
        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            return String.valueOf(getAutomationRandomUtilityObject().generateRandomInt());
        }

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_I);
        }
    },

    N {
        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            options = options.replace(",", "");
            char[] optionsArray = options.toCharArray();
            if (optionsArray[0] == '^') {
                return getAutomationRandomUtilityObject().generateRandomNumericStringWithExclusions(
                                lengthOfStringToBeGenerated, Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
            } else {
                return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                lengthOfStringToBeGenerated, options);
            }
        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            return getAutomationRandomUtilityObject().generateRandomStringOfNumbers(lengthOfStringToBeGenerated);
        }

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_N);
        }
    },

    Z {

        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            options = options.replace(",", "");
            char[] optionsArray = options.toCharArray();
            if (optionsArray[0] == '^') {
                return getAutomationRandomUtilityObject().generateRandomStringAlphaNumericWithExclusions(
                                lengthOfStringToBeGenerated, Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
            } else {
                return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                lengthOfStringToBeGenerated, options);
            }
        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            return getAutomationRandomUtilityObject().generateRandomStringAlphaNumeric(lengthOfStringToBeGenerated);

        }

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_Z);
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

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_D);
        }
    },

    C {

        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            options = options.replace(",", "");
            char[] optionsArray = options.toCharArray();
            if (optionsArray[0] == '^') {
                return getAutomationRandomUtilityObject().generateRandomStringEverythingWithExclusions(
                                lengthOfStringToBeGenerated, Arrays.copyOfRange(optionsArray, 1, optionsArray.length));
            } else {
                return getAutomationRandomUtilityObject().generateRandomStringOutOfGivenCharactersFromString(
                                lengthOfStringToBeGenerated, options);
            }

        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            return getAutomationRandomUtilityObject().generateRandomStringEverything(lengthOfStringToBeGenerated);
        }

        @Override
        public boolean validateRegex(String options) {
            return options.isEmpty() || options.matches(INPUT_VALIDATION_FOR_C);
        }

    },
    W {
        @Override
        protected String generateWithOptions(int lengthOfStringToBeGenerated, String options) {
            StringBuilder generated = new StringBuilder(lengthOfStringToBeGenerated);
            while (lengthOfStringToBeGenerated-- > 0) {
                generated = generated.append(options);
            }
            return generated.toString();
        }

        @Override
        protected String generateWithoutOptions(int lengthOfStringToBeGenerated) {
            throw new IllegalArgumentException();
        }

        @Override
        public boolean validateRegex(String options) {
            return !options.isEmpty();
        }
    };

    protected abstract String generateWithOptions(int lengthOfStringToBeGenerated, String options);

    protected abstract String generateWithoutOptions(int lengthOfStringToBeGenerated);

    public abstract boolean validateRegex(String options);

    private static AutomationRandomUtility getAutomationRandomUtilityObject() {
        return new AutomationRandomUtility();
    }

    private static List<Character> isRangeOperation(char[] optionsArray) {
        List<Character> range = Collections.emptyList();
        int indexOfRangeOperator = optionsArray.length - 2;
        if (optionsArray.length > 2 && optionsArray[indexOfRangeOperator] == '-') {
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

    private static final String INPUT_VALIDATION_FOR_A =
                    "(([A-Z]-[A-Z])|((\\^,)((([A-Z],)+(([A-Z]-[A-Z])|[A-Z]))|[A-Z]))|(([A-Z],)+[A-Z])|([A-Z]))";
    private static final String INPUT_VALIDATION_FOR_SMALL_A =
                    "(([a-z]-[a-z])|((\\\\^,)((([a-z],)+(([a-z]-[a-z])|[a-z]))|[a-z]))|(([a-z],)+[a-z])|([a-z]))";
    private static final String INPUT_VALIDATION_FOR_I = "(-?\\d+--?\\d+)|(\\^,(-?\\d+,)+((-?\\d+--?\\d+)|(-?\\d+)))";
    private static final String INPUT_VALIDATION_FOR_S =
                    "(\\^,)?([\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7E],)*[\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7E]";
    private static final String INPUT_VALIDATION_FOR_N = "(\\^,)?(\\d,)*\\d";
    private static final String INPUT_VALIDATION_FOR_Z = "(\\^,)?([a-zA-Z0-9],)*[a-zA-Z0-9]";
    private static final String INPUT_VALIDATION_FOR_C = "(?:[\\x20-\\x7E],)*[\\x20-\\x7E]";
    private static final String INPUT_VALIDATION_FOR_D =
                    "((\\d+,\\d+(\\.\\d+)?-\\d+(\\.\\d+)?)|(\\d+(\\.\\d+)?-\\d+(\\.\\d+)?)|\\d+)";
    private static final String INPUT_VALIDATION_FOR_M =
                    "^((([A-Z]-[a-zA-Z])|([a-z]-[a-z]))|((\\^,)((([a-zA-Z],)+((([A-Z]-[a-zA-Z])|([a-z]-[a-z]))|[A-Z]))|[a-zA-Z]))|((([a-zA-Z],)+[a-zA-Z])|([a-zA-Z])))$";
}
