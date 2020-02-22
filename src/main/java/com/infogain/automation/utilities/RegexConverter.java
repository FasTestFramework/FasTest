package com.infogain.automation.utilities;

public class RegexConverter {
    private static final char OPTION_START_BRACKET = '(';
    private static final char LENGTH_END_BRACE = '}';
    private static final char LENGHT_START_BRACE = '{';
    private static final char ESCAPE_CHRACTER = '%';
    private final static char OPTIONS_END_BRACKET = ')';
    private final static char[] specialCharacter =
                    {'*', '#', '/', '\\', '@', '$', '!', '(', ')', '{', '}', '&', '.', '?', ':', ';', '%', '[', ']'};

    public static void main(String[] args) {
        RegexConverter rc = new RegexConverter();
        String regex = "asdasjcj uwda!@#!E{}()C{10}   " + "c{3}  c(a,c,d,e,rt){64} C(a){7} "
                        + "S(%{){2} S(}){4} S{6} S((){2} S()){3}";
        System.out.println("Initial  : " + regex);
        System.out.println();
        rc.start(regex);
    }

    public void start(String regex) {
        char regChar[] = regex.toCharArray();
        int indexForNextLookup = 0;
        int braceStartIndex;
        int braceEndIndex;
        int optionStartIndex;
        int optionEndIndex;
        int lastIndexOfConstant;
        int indexForNextOpenBrace = 0;
        char regexSymbol;
        int lengthOfStringToBeGenerated;
        StringBuilder generatedString = new StringBuilder();
        String options = null;
        braceStartIndex = regex.indexOf(LENGHT_START_BRACE, indexForNextLookup);
        while (braceStartIndex != -1 && indexForNextLookup < regex.length()) {
            if (regChar[braceStartIndex - 1] == ESCAPE_CHRACTER) {
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
                    lengthOfStringToBeGenerated = 20;
                } else {
                    lengthOfStringToBeGenerated = Integer.parseInt(regex.substring(braceStartIndex + 1, braceEndIndex));
                }
                generatedString.append(regex.substring(indexForNextLookup, lastIndexOfConstant + 1));
                generatedString.append(generateString(regexSymbol, lengthOfStringToBeGenerated, options));
                indexForNextLookup = braceEndIndex + 1;
                indexForNextOpenBrace = indexForNextLookup;
                options = null;
            }
            braceStartIndex = regex.indexOf(LENGHT_START_BRACE, indexForNextOpenBrace);
        }
        System.out.println(generatedString.toString());
    }

    public String generateString(char regexSymbol, int lengthOfStringToBeGenerated, String options) {
        switch (regexSymbol) {
            case 'C':
                return generateMixedAlphabates(lengthOfStringToBeGenerated, options).toUpperCase();
            case 'c':
                return generateMixedAlphabates(lengthOfStringToBeGenerated, options).toLowerCase();
            case 'm':
                return generateMixedAlphabates(lengthOfStringToBeGenerated, options);
            case 'S':
                return generateSpecialCharacter(lengthOfStringToBeGenerated, options);
            default:
                // throw unspporeted regex exception
                break;
        }
        return null;
    }

    public String generateMixedAlphabates(int lengthOfStringToBeGenerated, String options) {
        String optionsArray[];
        AutomationRandomUtility obj = new AutomationRandomUtility();
        if (options != null && !options.isEmpty()) {
            optionsArray = options.split(",");
            if (optionsArray[0] == "^") {
                return null;
            } else {
                options = options.replace(",", "");
                return obj.generateRandomStringOutOfGivenCharactersFromString(lengthOfStringToBeGenerated, options);
            }
        } else {
            return obj.generateRandomStringCapitalSmallMix(lengthOfStringToBeGenerated);
        }
    }

    public String generateSpecialCharacter(int lengthOfStringToBeGenerated, String options) {
        String optionsArray[];
        AutomationRandomUtility obj = new AutomationRandomUtility();
        if (options != null && !options.isEmpty()) {
            optionsArray = options.split(",");
            if (optionsArray[0] == "^") {
                return null;
            } else {
                options = options.replace(",", "");
                return obj.generateRandomSpecialCharacter(lengthOfStringToBeGenerated, options.toCharArray()[0]);
            }
        } else {
            return obj.generateRandomSpecialCharacter(lengthOfStringToBeGenerated);
        }
    }
}
