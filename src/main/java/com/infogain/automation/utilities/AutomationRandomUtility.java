/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is a Utility which helps to generate Random values
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
package com.infogain.automation.utilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Arrays;
import org.springframework.stereotype.Component;

import com.infogain.automation.exception.RandomGenerationAutomationException;

@Component
public class AutomationRandomUtility {
    Random random = new Random();
    int[] specialCharcters = {33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 64,
                    91, 92, 93, 94, 95, 96, 123, 124, 125, 126};
    int[] capitalLetters = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87,
                    88, 89, 90};
    int[] smallLetters = {97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115,
                    116, 117, 118, 119, 120, 121, 122};
    int space = 32;
    int[] numbers = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
    int[] allLetters = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88,
                    89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115,
                    116, 117, 118, 119, 120, 121, 122};
    String characters =
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/? ";
    int[] allCharacters = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54,
                    55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
                    80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103,
                    104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123,
                    124, 125, 126};
    int[] allLettersAndNumbers = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75,
                    76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104,
                    105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};

    /*
     * public String generateRandomSpecialCharacter(int count, Character characterToGenerate) { if (characterToGenerate
     * != null) { int asciiCode = characterToGenerate; return RandomStringUtils.random(count, asciiCode, asciiCode + 1,
     * false, false); } else { return generateRandomSpecialCharacter(count); } }
     */

    public String generateRandomSpecialCharacter() {
        return generateRandomSpecialCharacter(random.nextInt(40));
    }

    public String generateRandomSpecialCharacter(int count) {
        return RandomStringUtils.random(count, 33, 48, false, false);
    }

    public String generateRandomSpecialCharactersRandomLength(char[] exclusions) {
        return generateRandomSpecialCharacters(random.nextInt(), exclusions);
    }

    public String generateRandomSpecialCharacters(int length, char[] exclusions) {
        char[] specialCharactersAllowed = generateCharactersWithExclusions(exclusions, specialCharcters);
        return RandomStringUtils.random(length, specialCharactersAllowed);
    }

    public int generateRandomInt() {
        return random.nextInt();
    }

    public int generateRandomInt(int bound) {
        return random.nextInt(bound);
    }

    public int generateRandomIntRange(int min, int max) {
        return random.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    public int generateRandomIntRangeWithExclusion(int start, int end, int... exclude) {
        java.util.Arrays.sort(exclude);
        int randomValue = 0;
        try {
            System.out.println(end);
            System.out.println(start);
            System.out.println(end - start + 1);
            System.out.println(exclude.length);
            randomValue = start + random
                            .nextInt((end - start + 1) == 0 ? Integer.MAX_VALUE : end - start + 1 - exclude.length);
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println(illegalArgumentException.getMessage());
            throw new RandomGenerationAutomationException("Upper bound must be greater than lower bound");
        }
        for (int ex : exclude) {
            if (!(ex >= start && ex <= end)) {
                throw new RandomGenerationAutomationException("Exclusions must be between specified range");
            }
            if (randomValue < ex) {
                break;
            }
            randomValue++;
        }
        return randomValue;
    }

    public String generateRandomStringSmallLetters() {
        return generateRandomStringSmallLetters(random.nextInt(40));
    }

    public String generateRandomStringSmallLetters(int length) {
        return RandomStringUtils.random(length, 97, 123, true, false);
    }

    public String generateRandomStringSmallLettersConstantCharRandomLength(Character character) {
        return generateRandomStringSmallLettersConstantChar(random.nextInt(40), character);
    }

    public String generateRandomStringSmallLettersConstantChar(int length, Character character) {
        int ascii = character;
        if (ascii >= 97 || ascii < 123) {
            return RandomStringUtils.random(length, ascii, ascii + 1, true, false);
        } else {
            throw new RandomGenerationAutomationException("small characters ascii starts at 97 and ends at 123");
        }
    }

    public String generateRandomStringSmallLettersRangeRandomLength(Character startCharacter, Character endCharacter) {
        return generateRandomStringSmallLettersRange(random.nextInt(40), startCharacter, endCharacter);
    }

    public String generateRandomStringSmallLettersRange(int length, Character startCharacter, Character endCharacter) {
        int startCharAscii = startCharacter;
        int endCharAscii = endCharacter;
        if (startCharAscii <= endCharAscii) {
            return RandomStringUtils.random(length, startCharAscii, endCharAscii, true, false);
        } else {
            throw new RandomGenerationAutomationException("Upper bound of range must be greater than lower bound");
        }
    }

    public String generateRandomStringSmallLettersWithExclusionRandomLength(char[] exclusions) {
        return generateRandomStringSmallLettersWithExclusion(random.nextInt(40), exclusions);
    }

    public String generateRandomStringSmallLettersWithExclusion(int length, char[] exclusions) {
        char[] smallLettersAllowed = generateCharactersWithExclusions(exclusions, smallLetters);
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, smallLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringSmallLettersRangeWithInclusionRandomLength(Character startCharacter,
                    Character endCharacter, char[] inclusions) {
        return generateRandomStringSmallLettersRangeWithInclusion(random.nextInt(40), startCharacter, endCharacter,
                        inclusions);
    }

    public String generateRandomStringSmallLettersRangeWithInclusion(int length, Character startCharacter,
                    Character endCharacter, char[] inclusions) {
        List<Object> smallLettersInitialList = Arrays.asList(smallLetters);
        List<Object> smallLettersList = Arrays.asList(smallLetters);
        int startCharacterascii = startCharacter;
        int endCharacterascii = endCharacter;
        for (Iterator iterator = smallLettersInitialList.iterator(); iterator.hasNext();) {
            int currentValue = (int) iterator.next();

            if (currentValue < startCharacterascii || currentValue > endCharacterascii) {
                smallLettersList.remove(Integer.valueOf(currentValue));
            }

        }
        int[] allowed = new int[smallLettersList.size()];
        for (int i = 0; i < allowed.length; i++) {
            allowed[i] = (Integer) smallLettersList.get(i);
        }
        char[] smallLettersAllowed = generateCharactersWithInclusions(inclusions, allowed);
        StringBuilder returnString = new StringBuilder();
        returnString.append(RandomStringUtils.random(length, smallLettersAllowed));
        return returnString.toString();
    }

    public String generateRandomStringSmallLettersRangeWithExclusionRandomLength(Character startCharacter,
                    Character endCharacter, char[] exclusions) {
        return generateRandomStringSmallLettersRangeWithExclusion(random.nextInt(40), startCharacter, endCharacter,
                        exclusions);
    }

    public String generateRandomStringSmallLettersRangeWithExclusion(int length, Character startCharacter,
                    Character endCharacter, char[] exclusions) {
        List<Object> smallLettersInitialList = Arrays.asList(smallLetters);
        List<Object> smallLettersList = Arrays.asList(smallLetters);
        int startCharacterascii = ((int) ((char) startCharacter));
        int endCharacterascii = ((int) ((char) endCharacter));
        for (Iterator iterator = smallLettersInitialList.iterator(); iterator.hasNext();) {
            int currentValue = (int) iterator.next();

            if (currentValue < startCharacterascii || currentValue > endCharacterascii) {
                smallLettersList.remove(Integer.valueOf(currentValue));
            }

        }
        int[] allowed = new int[smallLettersList.size()];
        for (int i = 0; i < allowed.length; i++) {
            allowed[i] = (Integer) smallLettersList.get(i);
        }
        char[] smallLettersAllowed = generateCharactersWithExclusions(exclusions, allowed);
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, smallLettersAllowed));
            length--;
        }
        return returnString.toString();
    }



    public String generateRandomStringCapitalLetters() {
        return RandomStringUtils.random(random.nextInt(20), 65, 91, true, false);

    }

    public String generateRandomStringCapitalLetters(int length) {
        return RandomStringUtils.random(length, 65, 91, true, false);

    }

    public String generateRandomStringCapitalLettersRandomLength(Character character) {
        return generateRandomStringCapitalLetters(random.nextInt(40), character);
    }

    public String generateRandomStringCapitalLetters(int length, Character character) {
        int ascii = character;
        if (ascii >= 65 && ascii < 91) {
            return RandomStringUtils.random(length, ascii, ascii + 1, true, false);
        } else {
            throw new RandomGenerationAutomationException("capital characters ascii starts at 65 and ends at 91");
        }

    }

    public String generateRandomStringCapitalLettersRangeRandomLength(Character startCharacter,
                    Character endCharacter) {
        return generateRandomStringCapitalLettersRange(random.nextInt(40), startCharacter, endCharacter);
    }

    public String generateRandomStringCapitalLettersRange(int length, Character startCharacter,
                    Character endCharacter) {
        int startCharAscii = startCharacter;
        int endCharAscii = endCharacter;
        if (startCharAscii <= endCharAscii) {
            return RandomStringUtils.random(length, startCharAscii, endCharAscii, true, false);
        } else {
            throw new RandomGenerationAutomationException("Upper bound of range must be greater than lower bound");
        }
    }

    public String generateRandomStringCapitalLettersWithExclusionRandomLength(char[] exclusions) {
        return generateRandomStringCapitalLettersWithExclusion(random.nextInt(), exclusions);
    }

    public String generateRandomStringCapitalLettersWithExclusion(int length, char[] exclusions) {
        char[] capitalLettersAllowed = generateCharactersWithExclusions(exclusions, capitalLetters);
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, capitalLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringSpecialCharacterWithExclusion(int length, char[] exclusions) {
        char[] capitalLettersAllowed = generateCharactersWithExclusions(exclusions, specialCharcters);
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, capitalLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomNumericStringWithExclusions(char[] exclusions) {
        return generateRandomNumericStringWithExclusions(random.nextInt(40), exclusions);
    }

    public String generateRandomNumericStringWithExclusions(int length, char[] exclusions) {
        char[] numbersAllowed = generateCharactersWithExclusions(exclusions, numbers);
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, numbersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringCapitalLettersRangeWithExclusionRandomLength(Character startCharacter,
                    Character endCharacter, char[] exclusions) {
        return generateRandomStringCapitalLettersRangeWithExclusion(random.nextInt(), startCharacter, endCharacter,
                        exclusions);
    }

    public String generateRandomStringCapitalLettersRangeWithExclusion(int length, Character startCharacter,
                    Character endCharacter, char[] exclusions) {
        List<Object> capitalLettersInitialList = Arrays.asList(capitalLetters);
        List<Object> capitalLettersList = Arrays.asList(capitalLetters);
        int startCharacterascii = ((int) ((char) startCharacter));
        int endCharacterascii = ((int) ((char) endCharacter));
        for (Iterator iterator = capitalLettersInitialList.iterator(); iterator.hasNext();) {
            int currentValue = (int) iterator.next();

            if (currentValue < startCharacterascii || currentValue > endCharacterascii) {
                capitalLettersList.remove(Integer.valueOf(currentValue));
            }
        }
        int[] allowed = new int[capitalLettersList.size()];
        for (int i = 0; i < allowed.length; i++) {
            allowed[i] = (Integer) capitalLettersList.get(i);
        }
        char[] capitalLettersAllowed = generateCharactersWithExclusions(exclusions, allowed);
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, capitalLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringOutOfGivenCharacters(char... characterSet) {
        return generateRandomStringOutOfGivenCharacters(random.nextInt(40), characterSet);
    }

    public String generateRandomStringOutOfGivenCharacters(int length, char... characterSet) {
        return RandomStringUtils.random(length, characterSet);
    }

    public String generateRandomStringOutOfGivenCharactersWithoutLength(char... characterSet) {
        return RandomStringUtils.random(random.nextInt(40), characterSet);
    }

    public String generateRandomStringOutOfGivenCharactersFromStringWithoutLength(String characterSet) {
        return generateRandomStringOutOfGivenCharactersFromString(random.nextInt(40), characterSet);
    }

    public String generateRandomStringOutOfGivenCharactersFromString(int length, String characterSet) {
        return RandomStringUtils.random(length, characterSet);
    }

    public String generateRandomStringOfNumbers() {
        return generateRandomStringOfNumbers(random.nextInt(40));
    }

    public String generateRandomStringOfNumbers(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public String generateRandomStringCapitalSmallMix() {
        return generateRandomStringCapitalSmallMix(random.nextInt(40));
    }

    public String generateRandomStringCapitalSmallMix(int length) {
        return RandomStringUtils.random(length, true, false);
    }

    public String generateRandomStringCapitalSmallMix(char[] exclusions) {
        return generateRandomStringCapitalSmallMix(random.nextInt(40), exclusions);
    }

    public String generateRandomStringCapitalSmallMix(int length, char[] exclusions) {
        char[] allLettersAllowed = generateCharactersWithExclusions(exclusions, allLetters);
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, allLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomSentence(int length) {
        StringBuilder sentence = new StringBuilder();
        while (sentence.length() < length) {
            sentence.append(generateRandomStringCapitalSmallMix(random.nextInt(13))).append(" ");
        }

        return sentence.substring(0, length);
    }

    public String generateRandomSentence() {
        return generateRandomSentence(random.nextInt());
    }
    // public double getRandomDoubleBetweenRangeWith(double min, double max, RoundingMode roundingMode, int scale) {
    // // RandomStringUtils.
    // // return BigDecimal.valueOf((Math.random() * ((max - min) + 1)) + min).doubleValue();
    //
    // /*
    // * return BigDecimal.valueOf((Math.random() * ((max - min) + 1)) + min).setScale(scale, roundingMode)
    // * .doubleValue();
    // */
    // /*
    // * StringBuilder as= new StringBuilder(); as.insert(index, str, offset, len) random.nextDouble();
    // */
    // return BigDecimal.valueOf((min + (max - min) * random.nextDouble())).setScale(scale, roundingMode)
    // .doubleValue();
    //
    // }

    public String getRandomDoubleBetweenRangeWithRandomRangeAndScale() {
        return getRandomDoubleBetweenRangeWithScaleAndRandomRange(random.nextInt(7));
    }

    public String getRandomDoubleBetweenRangeWithScaleAndRandomRange(int decimalPoint) {
        return getRandomDoubleBetweenRangeWithScale(Integer.MIN_VALUE, Integer.MAX_VALUE, decimalPoint);
    }

    public String getRandomDoubleBetweenRangeWithScale(double min, double max, int decimalPoint) {
        if (max < min) {
            throw new RandomGenerationAutomationException(
                            "upper bound of the range should be greater than the lower bound");
        }
        DecimalFormat decimalFormater = new DecimalFormat("#.00");
        decimalFormater.setMaximumIntegerDigits(330);
        decimalFormater.setMaximumFractionDigits(330);
        return String.format("%." + decimalPoint + "f", (min + ((max - min) * random.nextDouble())));
    }

    public String getRandomDoubleBetweenRange(double min, double max) {
        if (max < min) {
            throw new RandomGenerationAutomationException(
                            "upper bound of the range should be greater then th elower bound");
        }
        DecimalFormat decimalFormatter = new DecimalFormat("#.#");
        decimalFormatter.setMaximumIntegerDigits(330);
        decimalFormatter.setMaximumFractionDigits(330);
        return decimalFormatter.format((min + ((max - min) * random.nextDouble())));
    }

    public String generateRandomStringAlphaNumeric() {
        return generateRandomStringAlphaNumeric(random.nextInt(40));
    }

    public String generateRandomStringAlphaNumeric(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public String generateRandomStringAlphaNumericWithExclusions(char[] exclusions) {
        return generateRandomStringAlphaNumericWithExclusions(random.nextInt(40), exclusions);
    }

    public String generateRandomStringAlphaNumericWithExclusions(int length, char[] exclusions) {
        char[] allLettersAndNumbersAllowed = generateCharactersWithExclusions(exclusions, allLettersAndNumbers);
        return RandomStringUtils.random(length, allLettersAndNumbersAllowed);
    }

    private char[] generateCharactersWithExclusions(char[] exclusions, int[] asciiValues) {
        List<Object> allLettersAndNumbersList = Arrays.asList(asciiValues);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        allLettersAndNumbersList.removeAll(exclusionsAsciiCodes);
        char[] allLettersAndNumbersAllowed = new char[allLettersAndNumbersList.size()];

        for (int i = 0; i < allLettersAndNumbersAllowed.length; i++) {
            allLettersAndNumbersAllowed[i] = (char) ((int) allLettersAndNumbersList.get(i));
        }
        return allLettersAndNumbersAllowed;
    }

    private char[] generateCharactersWithInclusions(char[] inclusions, int[] asciiValues) {
        List<Object> allLettersAndNumbersList = Arrays.asList(asciiValues);
        for (int i = 0; i < inclusions.length; i++) {
            allLettersAndNumbersList.add((int) inclusions[i]);
        }
        char[] allLettersAndNumbersAllowed = new char[allLettersAndNumbersList.size()];
        for (int i = 0; i < allLettersAndNumbersAllowed.length; i++) {
            allLettersAndNumbersAllowed[i] = (char) ((int) allLettersAndNumbersList.get(i));
        }
        return allLettersAndNumbersAllowed;
    }

    public String generateRandomStringEverything() {
        return generateRandomStringEverything(random.nextInt(40));
    }

    public String generateRandomStringEverything(int length) {
        char[] allCharactersAllowed = generateCharactersWithExclusions(new char[] {}, allCharacters);
        return RandomStringUtils.random(length, allCharactersAllowed);
    }

    public String generateRandomStringEverythingWithExclusions(char[] exclusions) {
        return generateRandomStringEverythingWithExclusions(random.nextInt(40), exclusions);
    }

    public String generateRandomStringEverythingWithExclusions(int length, char[] exclusions) {
        char[] allCharactersAllowed = generateCharactersWithExclusions(exclusions, allCharacters);
        return RandomStringUtils.random(length, allCharactersAllowed);
    }

    public static void main(String[] args) {
        AutomationRandomUtility obj = new AutomationRandomUtility();
        String abc = obj.generateRandomStringSmallLettersRangeWithInclusion(125, 'a', 'c', new char[] {'p'});
        System.out.println(abc);
        System.out.println(abc.matches("[^abcp]*"));

        System.out.println(obj.getRandomDoubleBetweenRangeWithScale(Integer.MIN_VALUE, Integer.MAX_VALUE, 8));
        /*
         * int count = 0; for (int i = 0; i < 10000; i++) { int a = obj.generateRandomIntRangeWithExclusion(800, 8000,
         * new int[] {1000}); System.out.println(a);
         * 
         * if (a < 800 || a > 8000) { System.out.println("galat"); count++; }
         * 
         * } System.out.println(count);
         */
        // System.out.println(obj.generateRandomStringSpecialCharacterWithExclusion(44, new char[]{'#','!','@'}));
        // System.out.println(obj.generateRandomNumericStringWithExclusions(20, exclusions));

        /*
         * double random = new Random().nextDouble(); double SP = 0.0 + (random * (700.0 - 0.0)); SP =
         * Double.parseDouble(String.format("%.6f", SP)); //now do format System.out.println(SP);
         */
        /*
         * DecimalFormat dm = new DecimalFormat("#.#"); dm.setMaximumIntegerDigits(330);
         * dm.setMaximumFractionDigits(330); System.out.println(dm.format(222213222.2112));
         */
        // System.out.println(Double.parseDouble(dm.format(22.2112)));
        // int count = 0;
        // for (int i = 0; i < 13600; i++) {
        // String d = obj.getRandomDoubleBetweenRangeWithScale(1, 22, 2);
        // /*
        // * if (d < -2.05 || d > 7.98) { // System.out.println("fail: " + d); } else { count++; }
        // */
        // System.out.println(d);
        //
        // }
        // System.out.println(count);

        // System.out.println("asdsad" + java.util.Arrays.toString(new char[1]));
        // char[] a = new char[] {};
        // for (int i = 0; i < a.length; i++) {
        // System.out.println("asd");
        // System.out.println("'"+a[i]+"'");
        // }
        // Map<String, Map<String, String>> outer = new LinkedHashMap<>();
        // Map<String, String> inner = new LinkedHashMap<>();
        //
        // AutomationProperties automationProperties = new AutomationProperties();
        // String tokenPropertyNames =
        // "body:{claimId=output.claim.claimId,exptime=output.claim.time}|header:{jti=output.claim.jti}|url:{param=variable.claim.jti}";
        // String[] dgsdg = StringUtils.splitByWholeSeparator(tokenPropertyNames, "},");
        // for (String f : dgsdg) {
        // String[] bnghr = StringUtils.split(f, ":");
        // String[] qwqw = bnghr[1].split("\\,");
        // for (String ef : qwqw) {
        // String[] ppoqw = ef.split("\\=");
        //
        // if (ppoqw[0].startsWith("{")) {
        // ppoqw[0] = ppoqw[0].substring(1);
        // }
        //
        // inner.put(ppoqw[0], ppoqw[1]);
        // }
        // outer.put(bnghr[0], inner);
        // inner = new LinkedHashMap<>();
        // }
        // System.out.println(outer);
        //
        // char[] temp = tokenPropertyNames.toCharArray();
        // int minValue = 0, maxValue = 0;
        // int i = 0;
        // String outerKey = "", innerKey = "", innerValue = "";
        //
        // while (i < temp.length) {
        // if (temp[i] == ':') {
        // outerKey = tokenPropertyNames.substring(minValue == 0 ? minValue : minValue + 1, i);
        // } else if (temp[i] == '{') {
        // minValue = i + 1;
        // } else if (temp[i] == '=') {
        // maxValue = i;
        // innerKey = tokenPropertyNames.substring(minValue, maxValue);
        // minValue = i + 1;
        // } else if (temp[i] == ',') {
        // maxValue = i;
        // innerValue = tokenPropertyNames.substring(minValue, maxValue);
        // inner.put(innerKey, innerValue);
        // minValue = i + 1;
        // } else if (temp[i] == '}') {
        // maxValue = i;
        // innerValue = tokenPropertyNames.substring(minValue, maxValue);
        // inner.put(innerKey, innerValue);
        // outer.put(outerKey, inner);
        // inner = new HashMap<>();
        // minValue = i + 1;
        // }
        // i++;
        // }
        //
        //
        // Set<String> outerKeySet = outer.keySet();
        // for (String outerKeyKey : outerKeySet) {
        // System.out.println("\n\nouterKey: " + outerKeyKey);
        // Map<String, String> innerMap = outer.get(outerKeyKey);
        // Set<String> innerKeySet = innerMap.keySet();
        // for (String innerKeyKey : innerKeySet) {
        // System.out.print(innerKeyKey + ":" + innerMap.get(innerKeyKey) + ",");
        // }
        // }
        // System.out.println();
        // System.out.println();
        // System.out.println(outer);
        // AutomationRandomUtility.print();
    }

    public static void print() {
        AutomationRandomUtility obj = new AutomationRandomUtility();
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-Examples Start-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println();
        System.out.println();
        System.out.println("-------Special Characters Start---------");
        // System.out.println(obj.generateRandomSpecialCharacter(5, '%'));
        System.out.println(obj.generateRandomSpecialCharacter());
        System.out.println(obj.generateRandomSpecialCharacter(5));
        System.out.println(obj.generateRandomSpecialCharactersRandomLength(new char[] {'*', '/'}));
        System.out.println(obj.generateRandomSpecialCharacters(22, new char[] {'*', '/'}));
        System.out.println("-------Special Characters End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Integers  Start---------");
        System.out.println(obj.generateRandomInt());
        System.out.println(obj.generateRandomInt(1));
        System.out.println(obj.generateRandomIntRange(22, 77));
        System.out.println(obj.generateRandomIntRangeWithExclusion(-22, 23, new int[] {23}));
        System.out.println("-------Integers End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Capital Letters  Start---------");
        System.out.println(obj.generateRandomStringCapitalLetters());
        System.out.println(obj.generateRandomStringCapitalLetters(12));
        System.out.println(obj.generateRandomStringCapitalLettersRandomLength('A'));
        System.out.println(obj.generateRandomStringCapitalLetters(12, 'A'));
        System.out.println(obj.generateRandomStringCapitalLettersRangeRandomLength('A', 'L'));
        System.out.println(obj.generateRandomStringCapitalLettersRange(14, 'A', 'L'));
        System.out.println(obj.generateRandomStringCapitalLettersRangeWithExclusionRandomLength('F', 'T',
                        new char[] {'G', 'J', ' '}));
        System.out.println(obj.generateRandomStringCapitalLettersRangeWithExclusion(22, 'F', 'T',
                        new char[] {'G', 'J', ' '}));
        System.out.println(obj.generateRandomStringCapitalLettersWithExclusionRandomLength(new char[] {'A', 'B', 'L'}));

        System.out.println(obj.generateRandomStringCapitalLettersWithExclusion(10, new char[] {'A', 'B', 'L'}));

        System.out.println("-------Capital Letters End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Small Letters  Start---------");
        System.out.println(obj.generateRandomStringSmallLetters());
        System.out.println(obj.generateRandomStringSmallLetters(34));
        System.out.println(obj.generateRandomStringSmallLettersConstantCharRandomLength('a'));
        System.out.println(obj.generateRandomStringSmallLettersConstantChar(12, 'a'));
        System.out.println(obj.generateRandomStringSmallLettersRangeRandomLength('b', 'k'));
        System.out.println(obj.generateRandomStringSmallLettersRange(14, 'b', 'k'));
        System.out.println(obj.generateRandomStringSmallLettersWithExclusionRandomLength(new char[] {'a', 'c', 'l'}));
        System.out.println(obj.generateRandomStringSmallLettersWithExclusion(21, new char[] {'a', 'c', 'l'}));
        System.out.println(obj.generateRandomStringSmallLettersRangeWithExclusionRandomLength('f', 't',
                        new char[] {'g', 'j', 'l'}));
        System.out.println(obj.generateRandomStringSmallLettersRangeWithExclusion(21, 'f', 't',
                        new char[] {'g', 'j', 'l'}));
        System.out.println(obj.generateRandomStringSmallLettersRangeWithInclusionRandomLength('f', 't',
                        new char[] {'g', 'j', 'l'}));
        System.out.println(obj.generateRandomStringSmallLettersRangeWithInclusion(21, 'f', 't',
                        new char[] {'g', 'j', 'l'}));
        System.out.println("-------Small Letters End---------");


        System.out.println();
        System.out.println();
        System.out.println("-------Random String Out Of Given Characters Start---------");
        System.out.println(obj.generateRandomStringOutOfGivenCharactersWithoutLength('a', 'c', 'U'));
        System.out.println(obj.generateRandomStringOutOfGivenCharacters(33, 'J', 'n', 'M'));
        System.out.println(obj.generateRandomStringOutOfGivenCharactersFromString(44, "AxDTk"));
        System.out.println(obj.generateRandomStringOutOfGivenCharactersFromStringWithoutLength("AxDTk"));
        System.out.println("-------Random String Out Of Given Characters End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Random String of Numbers  Start---------");
        System.out.println(obj.generateRandomStringOfNumbers());
        System.out.println(obj.generateRandomStringOfNumbers(14));
        System.out.println(obj.generateRandomNumericStringWithExclusions(15, new char[] {12, 78, 45, 32}));
        System.out.println("-------Random String of Numbers End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Random String CapitalSmallMix  Start---------");
        System.out.println(obj.generateRandomStringCapitalSmallMix());
        System.out.println(obj.generateRandomStringCapitalSmallMix(33));
        System.out.println(obj.generateRandomStringCapitalSmallMix(33, new char[] {'g', 'G', 'l', 'S'}));
        System.out.println("-------Random String CapitalSmallMix End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Random Sentence  Start---------");
        System.out.println(obj.generateRandomSentence(66));
        System.out.println("-------Random Sentence End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Double  Start---------");
        System.out.println(obj.getRandomDoubleBetweenRange(2.05, 7.98));
        System.out.println("-------Double End---------");
        System.out.println();
        System.out.println();
        System.out.println("------ String Without Special Chars Start----------");
        System.out.println(obj.generateRandomStringAlphaNumeric());
        System.out.println(obj.generateRandomStringAlphaNumeric(22));
        System.out.println(obj.generateRandomStringAlphaNumericWithExclusions(23,
                        new char[] {'g', 'G', 'l', 'S', '3', '7'}));
        System.out.println("-------String Without Special Chars End---------");
        System.out.println();
        System.out.println();
        System.out.println("------ Random String Start----------");
        System.out.println(obj.generateRandomStringEverything());
        System.out.println(obj.generateRandomStringEverything(12));
        System.out.println(obj.generateRandomStringEverythingWithExclusions(
                        new char[] {'g', 'G', 'l', 'S', '3', '7', '%', '?'}));
        System.out.println(obj.generateRandomStringEverythingWithExclusions(15,
                        new char[] {'g', 'G', 'l', 'S', '3', '7', '%', '?'}));
        System.out.println("-------Random String End---------");
        System.out.println();
        System.out.println();
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-Examples End-*-*-*-*-*-*-*-*-*-*-*-");
    }

    public static void abc(char... a) {
        System.out.println("jhinga");
    }

    public static void abc(char a) {
        System.out.println("nahi");

    }
}
