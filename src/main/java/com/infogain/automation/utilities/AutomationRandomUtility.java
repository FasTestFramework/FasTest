package com.infogain.automation.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.infogain.automation.exception.RandomGenerationAutomationException;

@Component
public class AutomationRandomUtility {
    Random random = new Random();

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

    public int generateRandomInt() {
        return random.nextInt();
    }

    public int generateRandomInt(int bound) {
        return random.nextInt(bound);
    }

    public int generateRandomInt(int min, int max) {
        return random.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
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

    public String generateRandomSentence(int length) {
        StringBuilder sentence = new StringBuilder();
        while (sentence.length() < length) {
            sentence.append(generateRandomStringCapitalSmallMix(random.nextInt(13))).append(" ");
        }

        return sentence.substring(0, length);

    }
    
    public  double getRandomDoubleBetweenRange(double min, double max,RoundingMode roundingMode,int scale){
        
        return BigDecimal.valueOf((Math.random()*((max-min)+1))+min)
                        .setScale(scale, roundingMode)
                        .doubleValue();
    }

    public static void main(String[] args) {
        AutomationRandomUtility obj = new AutomationRandomUtility();
        System.out.println(obj.generateRandomSpecialCharacter(5, '%'));
        System.out.println(obj.generateRandomSpecialCharacter(5, null));
        System.out.println(obj.generateRandomSpecialCharacter(5));
        System.out.println(obj.generateRandomSpecialCharacter());
        System.out.println(obj.generateRandomInt());
        System.out.println(obj.generateRandomInt(22));
        System.out.println(obj.generateRandomInt(22, 77));
        System.out.println(obj.generateRandomStringCapitalLetters());
        System.out.println(obj.generateRandomStringCapitalLetters(12));
        System.out.println(obj.generateRandomStringCapitalLetters(12, 'A'));
        System.out.println(obj.generateRandomStringCapitalLetters(14, 'A', 'L'));
        System.out.println(obj.generateRandomStringSmallLetters());
        System.out.println(obj.generateRandomStringSmallLetters(12));
        System.out.println(obj.generateRandomStringSmallLetters(12, 'a'));
        System.out.println(obj.generateRandomStringSmallLetters(14, 'b', 'k'));
        System.out.println(obj.generateRandomStringOutOfGivenCharacters('a', 'c', 'U'));
        System.out.println(obj.generateRandomStringOutOfGivenCharacters(33, 'J', 'n', 'M'));
        System.out.println(obj.generateRandomStringOutOfGivenCharactersFromString("AxDTk"));
        System.out.println(obj.generateRandomStringOutOfGivenCharactersFromString(44, "AxDTk"));
        System.out.println(obj.generateRandomStringOfNumbers());
        System.out.println(obj.generateRandomStringOfNumbers(14));
        System.out.println(obj.generateRandomStringCapitalSmallMix());
        System.out.println(obj.generateRandomStringCapitalSmallMix(33));
        System.out.println(obj.generateRandomSentence(66));
        System.out.println(obj.getRandomDoubleBetweenRange(2.05, 7.98, RoundingMode.FLOOR, 5));

    }
}
