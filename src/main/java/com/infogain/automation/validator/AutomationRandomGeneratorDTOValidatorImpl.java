package com.infogain.automation.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.infogain.automation.dto.AutomationRandomGeneratorDTO;
import com.infogain.automation.dto.ErrorCodesDTO;
import com.infogain.automation.errors.AutomationErrorCodes;
import com.infogain.automation.exception.FastTestBadRequestException;
import com.infogain.automation.model.AutomationRegexData;
import com.infogain.automation.utilities.AutomationRegex;

public class AutomationRandomGeneratorDTOValidatorImpl
                implements ConstraintValidator<AutomationRandomGeneratorDTOValidator, AutomationRandomGeneratorDTO> {
    private static final int MAX_LENGTH_OF_TOKEN_TO_BE_GENERATED = 40;
    private static final char OPTION_START_BRACKET = '(';
    private static final char LENGTH_END_BRACE = '}';
    private static final char LENGTH_START_BRACE = '{';
    private static final char ESCAPE_CHARACTER = '%';
    private static final char OPTIONS_END_BRACKET = ')';
    private boolean isLengthGiven = false;
    List<ErrorCodesDTO> errorCodes = new ArrayList<>();



    @Override
    public boolean isValid(AutomationRandomGeneratorDTO value, ConstraintValidatorContext context) {
        value.setAutomationRegexDatalist(validateAndParseRegex(value.getInstructionsToGenerateRandomData()));
        return true;
    }

    public List<AutomationRegexData> validateAndParseRegex(String regex) {
        errorCodes = new ArrayList<>();
        List<AutomationRegexData> automationRegexDataList = new ArrayList<>();
        char[] regChar = regex.toCharArray();
        int indexForNextLookup = 0;
        int braceStartIndex;
        int braceEndIndex;
        int optionStartIndex;
        int optionEndIndex;
        int indexForNextOpenBrace = 0;
        char regexSymbol;
        int lengthOfStringToBeGenerated = new Random().nextInt(MAX_LENGTH_OF_TOKEN_TO_BE_GENERATED);
        String options = "";
        braceStartIndex = regex.indexOf(LENGTH_START_BRACE, indexForNextLookup);
        if (braceStartIndex == -1 || regex.indexOf(LENGTH_END_BRACE, indexForNextLookup) == -1
                        || braceStartIndex - 1 == -1) {
            errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.REGEX_FORMAT_EXCEPTION,
                            "There was no regex in the body to evaluate. e.g. of regex C{}, curly braces are mandotory and must be prefixed by a regex symbol"));
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
                } else {
                    regexSymbol = regChar[braceStartIndex - 1];
                }
                if (braceStartIndex + 1 == braceEndIndex) {
                    lengthOfStringToBeGenerated = new Random().nextInt(MAX_LENGTH_OF_TOKEN_TO_BE_GENERATED);
                    isLengthGiven = false;
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
                automationRegexDataList
                                .add(createAutomationRegexData(regexSymbol, options, lengthOfStringToBeGenerated));
                indexForNextLookup = braceEndIndex + 1;
                indexForNextOpenBrace = indexForNextLookup;
                options = "";
            }
            braceStartIndex = regex.indexOf(LENGTH_START_BRACE, indexForNextOpenBrace);
        }
        if (!errorCodes.isEmpty()) {
            throw new FastTestBadRequestException(errorCodes);
        }
        return automationRegexDataList;
    }

    private String generateOptionsErrorMessage(String options) {
        return !options.isEmpty() ? "(" + options + ")" : "";
    }

    private String generateLengthErrorMessage(int lengthOfStringToBeGenerated) {
        return isLengthGiven ? "{" + lengthOfStringToBeGenerated + "}" : "{}";
    }

    public AutomationRegexData createAutomationRegexData(char regexSymbol, String options,
                    int lengthOfStringToBeGenerated) {
        AutomationRegexData automationRegexData = null;
        try {
            automationRegexData = new AutomationRegexData(AutomationRegex.valueOf(String.valueOf(regexSymbol)), options,
                            lengthOfStringToBeGenerated);
            if (!automationRegexData.validateRegex()) {
                errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.REGEX_FORMAT_EXCEPTION,
                                "InvalidRegex: " + regexSymbol + generateOptionsErrorMessage(options)
                                                + generateLengthErrorMessage(lengthOfStringToBeGenerated) + " "
                                                + "Regex Expression is not correct"));
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            errorCodes.add(new ErrorCodesDTO(AutomationErrorCodes.REGEX_FORMAT_EXCEPTION,
                            regexSymbol + generateOptionsErrorMessage(options)
                                            + generateLengthErrorMessage(lengthOfStringToBeGenerated)
                                            + " is not a valid regex symbol"));
        }
        return automationRegexData;
    }
}
