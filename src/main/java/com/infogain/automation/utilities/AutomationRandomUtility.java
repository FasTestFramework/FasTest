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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public String generateRandomSpecialCharacter(int count, Character characterToGenerate) {


        if (characterToGenerate != null) {
            int asciiCode = characterToGenerate;
            return RandomStringUtils.random(count, asciiCode, asciiCode + 1, false, false);
        } else {
            return generateRandomSpecialCharacter(count);
        }

    }

    public String generateRandomSpecialCharacter(int count) {

        return RandomStringUtils.random(count, 33, 48, false, false);
    }

    public String generateRandomSpecialCharacter() {

        return RandomStringUtils.random(random.nextInt(20), 33, 48, false, false);
    }

    public String generateRandomSpecialCharacters(int length, Character[] exclusions) {

        List<Object> specialCharctersList = Arrays.asList(specialCharcters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        specialCharctersList.removeAll(exclusionsAsciiCodes);
        char[] specialCharactersAllowed = new char[specialCharctersList.size()];

        for (int i = 0; i < specialCharactersAllowed.length; i++) {
            specialCharactersAllowed[i] = (char) ((int) specialCharctersList.get(i));
        }
        return RandomStringUtils.random(length, specialCharactersAllowed);
    }

    public String generateRandomSpecialCharacters(Character[] exclusions) {

        List<Object> specialCharctersList = Arrays.asList(specialCharcters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        specialCharctersList.removeAll(exclusionsAsciiCodes);
        char[] specialCharactersAllowed = new char[specialCharctersList.size()];

        for (int i = 0; i < specialCharactersAllowed.length; i++) {
            specialCharactersAllowed[i] = (char) ((int) specialCharctersList.get(i));
        }
        return RandomStringUtils.random(random.nextInt(20), specialCharactersAllowed);
    }

    public int generateRandomInt() {
        return random.nextInt();
    }

    public int generateRandomInt(int bound) {
        return random.nextInt(bound);
    }

    public int generateRandomInt(int min, int max) {
        return random.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    public int generateRandomInt(int start, int end, int... exclude) {
        int randomValue = start + random.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (randomValue < ex) {
                break;
            }
            randomValue++;
        }
        return randomValue;
    }

    public String generateRandomStringSmallLetters() {
        return RandomStringUtils.random(random.nextInt(20), 97, 123, true, false);

    }

    public String generateRandomStringSmallLetters(int length) {
        return RandomStringUtils.random(length, 97, 123, true, false);

    }

    public String generateRandomStringSmallLetters(int length, Character character) {

        int ascii = character;
        if (ascii >= 97 || ascii < 123) {
            return RandomStringUtils.random(length, ascii, ascii + 1, true, false);
        } else {
            throw new RandomGenerationAutomationException("small characters ascii starts at 97 and ends at 123");
        }

    }

    public String generateRandomStringSmallLetters(int length, Character startCharacter, Character endCharacter) {
        int startCharAscii = startCharacter;
        int endCharAscii = endCharacter;
        if ((startCharAscii >= 97 || startCharAscii < 122) && (endCharAscii > 98 || endCharAscii <= 123)
                        && endCharAscii > startCharAscii) {
            return RandomStringUtils.random(length, startCharAscii, endCharAscii, true, false);
        } else {
            throw new RandomGenerationAutomationException("small characters ascii starts at 97 and ends at 123");
        }
    }

    public String generateRandomStringSmallLetters(int length, Character[] exclusions) {

        List<Object> smallLettersList = Arrays.asList(smallLetters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        smallLettersList.removeAll(exclusionsAsciiCodes);
        char[] smallLettersAllowed = new char[smallLettersList.size()];

        for (int i = 0; i < smallLettersAllowed.length; i++) {
            smallLettersAllowed[i] = (char) ((int) smallLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, smallLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringSmallLetters(Character[] exclusions) {

        List<Object> smallLettersList = Arrays.asList(smallLetters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        smallLettersList.removeAll(exclusionsAsciiCodes);
        char[] smallLettersAllowed = new char[smallLettersList.size()];

        for (int i = 0; i < smallLettersAllowed.length; i++) {
            smallLettersAllowed[i] = (char) ((int) smallLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        int length = random.nextInt(20);
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, smallLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringSmallLetters(int length, Character startCharacter, Character endCharacter,
                    Character[] exclusions) {
        List<Object> smallLettersInitialList = Arrays.asList(smallLetters);
        List<Object> smallLettersList = Arrays.asList(smallLetters);
        int startCharacterascii = ((int) ((char) startCharacter));
        int endCharacterascii = ((int) ((char) endCharacter));
        for (Iterator iterator = smallLettersInitialList.iterator(); iterator.hasNext();) {
            int currentValue = (int) iterator.next();

            if (currentValue < startCharacterascii || currentValue > endCharacterascii) {
                smallLettersList.remove(new Integer(currentValue));
            }

        }
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        smallLettersList.removeAll(exclusionsAsciiCodes);
        char[] smallLettersAllowed = new char[smallLettersList.size()];

        for (int i = 0; i < smallLettersAllowed.length; i++) {
            smallLettersAllowed[i] = (char) ((int) smallLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, smallLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringSmallLetters(Character startCharacter, Character endCharacter,
                    Character[] exclusions) {
        List<Object> smallLettersInitialList = Arrays.asList(smallLetters);
        List<Object> smallLettersList = Arrays.asList(smallLetters);
        int startCharacterascii = ((int) ((char) startCharacter));
        int endCharacterascii = ((int) ((char) endCharacter));
        for (Iterator iterator = smallLettersInitialList.iterator(); iterator.hasNext();) {
            int currentValue = (int) iterator.next();

            if (currentValue < startCharacterascii || currentValue > endCharacterascii) {
                smallLettersList.remove(new Integer(currentValue));
            }

        }
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        smallLettersList.removeAll(exclusionsAsciiCodes);
        char[] smallLettersAllowed = new char[smallLettersList.size()];

        for (int i = 0; i < smallLettersAllowed.length; i++) {
            smallLettersAllowed[i] = (char) ((int) smallLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        int length = random.nextInt(20);
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

    public String generateRandomStringCapitalLetters(int length, Character character) {

        int ascii = character;
        if (ascii <= 65 || ascii > 91) {
            return RandomStringUtils.random(length, ascii, ascii + 1, true, false);
        } else {
            throw new RandomGenerationAutomationException("small characters ascii starts at 97 and ends at 123");
        }

    }

    public String generateRandomStringCapitalLetters(int length, Character startCharacter, Character endCharacter) {
        int startCharAscii = startCharacter;
        int endCharAscii = endCharacter;
        if ((startCharAscii <= 65 || startCharAscii > 91) && (endCharAscii < 92 || endCharAscii > 65)
                        && endCharAscii > startCharAscii) {
            return RandomStringUtils.random(length, startCharAscii, endCharAscii, true, false);
        } else {
            throw new RandomGenerationAutomationException("small characters ascii starts at 97 and ends at 123");
        }
    }

    public String generateRandomStringCapitalLetters(int length, Character[] exclusions) {

        List<Object> capitalLettersList = Arrays.asList(capitalLetters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        capitalLettersList.removeAll(exclusionsAsciiCodes);
        char[] capitalLettersAllowed = new char[capitalLettersList.size()];

        for (int i = 0; i < capitalLettersAllowed.length; i++) {
            capitalLettersAllowed[i] = (char) ((int) capitalLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, capitalLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringCapitalLetters(Character[] exclusions) {

        List<Object> capitalLettersList = Arrays.asList(capitalLetters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        capitalLettersList.removeAll(exclusionsAsciiCodes);
        char[] capitalLettersAllowed = new char[capitalLettersList.size()];

        for (int i = 0; i < capitalLettersAllowed.length; i++) {
            capitalLettersAllowed[i] = (char) ((int) capitalLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        int length = random.nextInt(20);
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, capitalLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringCapitalLetters(int length, Character startCharacter, Character endCharacter,
                    Character[] exclusions) {
        List<Object> capitalLettersInitialList = Arrays.asList(capitalLetters);
        List<Object> capitalLettersList = Arrays.asList(capitalLetters);
        int startCharacterascii = ((int) ((char) startCharacter));
        int endCharacterascii = ((int) ((char) endCharacter));
        for (Iterator iterator = capitalLettersInitialList.iterator(); iterator.hasNext();) {
            int currentValue = (int) iterator.next();

            if (currentValue < startCharacterascii || currentValue > endCharacterascii) {
                capitalLettersList.remove(new Integer(currentValue));
            }

        }
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        capitalLettersList.removeAll(exclusionsAsciiCodes);
        char[] capitalLettersAllowed = new char[capitalLettersList.size()];

        for (int i = 0; i < capitalLettersAllowed.length; i++) {
            capitalLettersAllowed[i] = (char) ((int) capitalLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, capitalLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringCapitalLetters(Character startCharacter, Character endCharacter,
                    Character[] exclusions) {
        List<Object> capitalLettersInitialList = Arrays.asList(capitalLetters);
        List<Object> capitalLettersList = Arrays.asList(capitalLetters);
        int startCharacterascii = ((int) ((char) startCharacter));
        int endCharacterascii = ((int) ((char) endCharacter));
        for (Iterator iterator = capitalLettersInitialList.iterator(); iterator.hasNext();) {
            int currentValue = (int) iterator.next();

            if (currentValue < startCharacterascii || currentValue > endCharacterascii) {
                capitalLettersList.remove(new Integer(currentValue));
            }

        }
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        capitalLettersList.removeAll(exclusionsAsciiCodes);
        char[] capitalLettersAllowed = new char[capitalLettersList.size()];

        for (int i = 0; i < capitalLettersAllowed.length; i++) {
            capitalLettersAllowed[i] = (char) ((int) capitalLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        int length = random.nextInt(20);
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, capitalLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringOutOfGivenCharacters(int length, char... characterSet) {
        return RandomStringUtils.random(length, characterSet);
    }

    public String generateRandomStringOutOfGivenCharactersFromString(int length, String characterSet) {
        return RandomStringUtils.random(length, characterSet);
    }

    public String generateRandomStringOutOfGivenCharacters(char... characterSet) {
        return RandomStringUtils.random(random.nextInt(20), characterSet);
    }

    public String generateRandomStringOutOfGivenCharactersFromString(String characterSet) {
        return RandomStringUtils.random(random.nextInt(20), characterSet);
    }

    public String generateRandomStringOfNumbers(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public String generateRandomStringOfNumbers() {
        return RandomStringUtils.randomNumeric(random.nextInt(20));
    }

    public String generateRandomStringCapitalSmallMix(int length) {
        return RandomStringUtils.random(length, true, false);
    }

    public String generateRandomStringCapitalSmallMix() {
        return RandomStringUtils.random(random.nextInt(20), true, false);
    }

    public String generateRandomStringCapitalSmallMix(int length, Character[] exclusions) {

        List<Object> allLettersList = Arrays.asList(allLetters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        allLettersList.removeAll(exclusionsAsciiCodes);
        char[] allLettersAllowed = new char[allLettersList.size()];

        for (int i = 0; i < allLettersAllowed.length; i++) {
            allLettersAllowed[i] = (char) ((int) allLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        while (length != 0) {
            returnString.append(RandomStringUtils.random(1, allLettersAllowed));
            length--;
        }
        return returnString.toString();
    }

    public String generateRandomStringCapitalSmallMix(Character[] exclusions) {

        List<Object> allLettersList = Arrays.asList(allLetters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        allLettersList.removeAll(exclusionsAsciiCodes);
        char[] allLettersAllowed = new char[allLettersList.size()];

        for (int i = 0; i < allLettersAllowed.length; i++) {
            allLettersAllowed[i] = (char) ((int) allLettersList.get(i));
        }
        StringBuilder returnString = new StringBuilder();
        int length = random.nextInt(20);
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

    public double getRandomDoubleBetweenRange(double min, double max, RoundingMode roundingMode, int scale) {

        return BigDecimal.valueOf((Math.random() * ((max - min) + 1)) + min).setScale(scale, roundingMode)
                        .doubleValue();
    }

    public String generateRandomStringWithoutSpecialChars(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public String generateRandomStringWithoutSpecialChars() {
        return RandomStringUtils.random(random.nextInt(20), true, true);
    }

    public String generateRandomStringWithoutSpecialChars(int length, Character[] exclusions) {

        List<Object> allLettersAndNumbersList = Arrays.asList(allLettersAndNumbers);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        allLettersAndNumbersList.removeAll(exclusionsAsciiCodes);
        char[] allLettersAndNumbersAllowed = new char[allLettersAndNumbersList.size()];

        for (int i = 0; i < allLettersAndNumbersAllowed.length; i++) {
            allLettersAndNumbersAllowed[i] = (char) ((int) allLettersAndNumbersList.get(i));
        }
        return RandomStringUtils.random(length, allLettersAndNumbersAllowed);
    }

    public String generateRandomStringWithoutSpecialChars(Character[] exclusions) {

        List<Object> allLettersAndNumbersList = Arrays.asList(allLettersAndNumbers);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        allLettersAndNumbersList.removeAll(exclusionsAsciiCodes);
        char[] allLettersAndNumbersAllowed = new char[allLettersAndNumbersList.size()];

        for (int i = 0; i < allLettersAndNumbersAllowed.length; i++) {
            allLettersAndNumbersAllowed[i] = (char) ((int) allLettersAndNumbersList.get(i));
        }
        return RandomStringUtils.random(random.nextInt(20), allLettersAndNumbersAllowed);
    }

    public String generateRandomString(int length) {
        return RandomStringUtils.random(length, characters);
    }

    public String generateRandomString() {
        return RandomStringUtils.random(random.nextInt(20), characters);
    }

    public String generateRandomString(int length, Character[] exclusions) {

        List<Object> allCharactersList = Arrays.asList(allCharacters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        allCharactersList.removeAll(exclusionsAsciiCodes);
        char[] allCharactersAllowed = new char[allCharactersList.size()];

        for (int i = 0; i < allCharactersAllowed.length; i++) {
            allCharactersAllowed[i] = (char) ((int) allCharactersList.get(i));
        }
        return RandomStringUtils.random(length, allCharactersAllowed);
    }

    public String generateRandomString(Character[] exclusions) {

        List<Object> allCharactersList = Arrays.asList(allCharacters);
        List<Integer> exclusionsAsciiCodes = new ArrayList<>();
        for (int i = 0; i < exclusions.length; i++) {
            exclusionsAsciiCodes.add((int) exclusions[i]);
        }
        allCharactersList.removeAll(exclusionsAsciiCodes);
        char[] allCharactersAllowed = new char[allCharactersList.size()];

        for (int i = 0; i < allCharactersAllowed.length; i++) {
            allCharactersAllowed[i] = (char) ((int) allCharactersList.get(i));
        }
        return RandomStringUtils.random(random.nextInt(20), allCharactersAllowed);
    }

    public static void main(String[] args) {
        AutomationRandomUtility obj = new AutomationRandomUtility();
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-Examples Start-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println();
        System.out.println();
        System.out.println("-------Special Characters Start---------");
        System.out.println(obj.generateRandomSpecialCharacter(5, '%'));
        System.out.println(obj.generateRandomSpecialCharacter(5));
        System.out.println(obj.generateRandomSpecialCharacter());
        System.out.println(obj.generateRandomSpecialCharacters(22, new Character[] {'*', '/'}));
        System.out.println(obj.generateRandomSpecialCharacters(new Character[] {'*', '/'}));
        System.out.println("-------Special Characters End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Integers  Start---------");
        System.out.println(obj.generateRandomInt());
        System.out.println(obj.generateRandomInt(22));
        System.out.println(obj.generateRandomInt(22, 77));
        System.out.println(obj.generateRandomInt(22, 44, new int[] {23, 25, 33, 42}));
        System.out.println("-------Integers End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Capital Letters  Start---------");
        System.out.println(obj.generateRandomStringCapitalLetters());
        System.out.println(obj.generateRandomStringCapitalLetters(12));
        System.out.println(obj.generateRandomStringCapitalLetters(12, 'A'));
        System.out.println(obj.generateRandomStringCapitalLetters(14, 'A', 'L'));
        System.out.println(obj.generateRandomStringCapitalLetters(12, new Character[] {'A', 'C', 'L'}));
        System.out.println(obj.generateRandomStringCapitalLetters(new Character[] {'A', 'C', 'L'}));
        System.out.println(obj.generateRandomStringCapitalLetters(22, 'F', 'T', new Character[] {'G', 'J', 'L'}));
        System.out.println(obj.generateRandomStringCapitalLetters('F', 'T', new Character[] {'G', 'J', 'L'}));
        System.out.println("-------Capital Letters End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Small Letters  Start---------");
        System.out.println(obj.generateRandomStringSmallLetters());
        System.out.println(obj.generateRandomStringSmallLetters(12));
        System.out.println(obj.generateRandomStringSmallLetters(12, 'a'));
        System.out.println(obj.generateRandomStringSmallLetters(14, 'b', 'k'));
        System.out.println(obj.generateRandomStringSmallLetters(21, new Character[] {'a', 'c', 'l'}));
        System.out.println(obj.generateRandomStringSmallLetters(new Character[] {'a', 'c', 'l'}));
        System.out.println(obj.generateRandomStringSmallLetters(21, 'f', 't', new Character[] {'g', 'j', 'l'}));
        System.out.println(obj.generateRandomStringSmallLetters('f', 't', new Character[] {'g', 'j', 'l'}));
        System.out.println("-------Small Letters End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Random String Out Of Given Characters Start---------");
        System.out.println(obj.generateRandomStringOutOfGivenCharacters('a', 'c', 'U'));
        System.out.println(obj.generateRandomStringOutOfGivenCharacters(33, 'J', 'n', 'M'));
        System.out.println(obj.generateRandomStringOutOfGivenCharactersFromString("AxDTk"));
        System.out.println(obj.generateRandomStringOutOfGivenCharactersFromString(44, "AxDTk"));
        System.out.println("-------Random String Out Of Given Characters End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Random String of Numbers  Start---------");
        System.out.println(obj.generateRandomStringOfNumbers());
        System.out.println(obj.generateRandomStringOfNumbers(14));
        System.out.println("-------Random String of Numbers End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Random String CapitalSmallMix  Start---------");
        System.out.println(obj.generateRandomStringCapitalSmallMix());
        System.out.println(obj.generateRandomStringCapitalSmallMix(33));
        System.out.println(obj.generateRandomStringCapitalSmallMix(33, new Character[] {'g', 'G', 'l', 'S'}));
        System.out.println(obj.generateRandomStringCapitalSmallMix(new Character[] {'g', 'G', 'l', 'S'}));
        System.out.println("-------Random String CapitalSmallMix End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Random Sentence  Start---------");
        System.out.println(obj.generateRandomSentence(66));
        System.out.println("-------Random Sentence End---------");
        System.out.println();
        System.out.println();
        System.out.println("-------Double  Start---------");
        System.out.println(obj.getRandomDoubleBetweenRange(2.05, 7.98, RoundingMode.FLOOR, 5));
        System.out.println("-------Double End---------");
        System.out.println();
        System.out.println();
        System.out.println("------ String Without Special Chars Start----------");
        System.out.println(obj.generateRandomStringWithoutSpecialChars());
        System.out.println(obj.generateRandomStringWithoutSpecialChars(22));
        System.out.println(obj.generateRandomStringWithoutSpecialChars(new Character[] {'g', 'G', 'l', 'S', '3', '7'}));
        System.out.println(obj.generateRandomStringWithoutSpecialChars(23,
                        new Character[] {'g', 'G', 'l', 'S', '3', '7'}));
        System.out.println("-------String Without Special Chars End---------");
        System.out.println();
        System.out.println();
        System.out.println("------ Random String Start----------");
        System.out.println(obj.generateRandomString());
        System.out.println(obj.generateRandomString(12));
        System.out.println(obj.generateRandomString(new Character[] {'g', 'G', 'l', 'S', '3', '7', '%', '?'}));
        System.out.println(obj.generateRandomString(15, new Character[] {'g', 'G', 'l', 'S', '3', '7', '%', '?'}));
        System.out.println("-------Random String End---------");
        System.out.println();
        System.out.println();
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-Examples End-*-*-*-*-*-*-*-*-*-*-*-");
    }
}
