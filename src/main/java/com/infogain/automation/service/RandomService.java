package com.infogain.automation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.dto.AutomationGenerateRandomSentenceDTO;
import com.infogain.automation.dto.AutomationRandomDoubleGenerateDTO;
import com.infogain.automation.dto.AutomationRandomGenerateAlphaNumericDTO;
import com.infogain.automation.dto.AutomationRandomGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomIntegerGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomSpecialCharGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringCapitalLetterGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringEverythingDTO;
import com.infogain.automation.dto.AutomationRandomStringGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringOutOfGivenCharGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringSmallLetterGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO;
import com.infogain.automation.utilities.AutomationRandomUtility;
import com.infogain.automation.utilities.RegexConverter;

@Service
public class RandomService {


    private RegexConverter regexConverter;
    private AutomationRandomUtility automationRandomUtility;

    @Autowired
    public RandomService(RegexConverter regexConverter, AutomationRandomUtility automationRandomUtility) {
        this.regexConverter = regexConverter;
        this.automationRandomUtility = automationRandomUtility;
    }

    public String generateRandomString(AutomationRandomGeneratorDTO automationRandomGeneratorDTO) {
        return regexConverter.start(automationRandomGeneratorDTO.getinstructionsToGenerateRandomData());
    }

    public String generateRandomSpecialChar(
                    AutomationRandomSpecialCharGeneratorDTO automationRandomSpecialCharGeneratorDTO) {
        char[] exclusions = automationRandomSpecialCharGeneratorDTO.getExclusions();
        Integer length = automationRandomSpecialCharGeneratorDTO.getLength();
        if (length != 0 && exclusions == null) {
            return automationRandomUtility.generateRandomSpecialCharacter(length);
        } else if (length != 0 && exclusions != null) {
            return automationRandomUtility.generateRandomSpecialCharacters(length, exclusions);
        } else if (exclusions == null) {
            return automationRandomUtility.generateRandomSpecialCharacter();
        } else {
            return null;
        }
    }

    public int generateRandomInteger(AutomationRandomIntegerGeneratorDTO automationRandomIntegerGeneratorDTO) {
        int minValue = automationRandomIntegerGeneratorDTO.getMinValue();
        int maxValue = automationRandomIntegerGeneratorDTO.getMaxValue();
        int[] exclude = automationRandomIntegerGeneratorDTO.getExclude();

        if (minValue == 0 && maxValue == 0 && exclude.length == 0) {
            return automationRandomUtility.generateRandomInt();
        } else if (minValue == 0 && maxValue != 0 && exclude.length == 0) {
            return automationRandomUtility.generateRandomInt(maxValue);
        } else if (minValue != 0 && maxValue != 0 && exclude.length == 0) {
            return automationRandomUtility.generateRandomIntRange(minValue, maxValue);
        } else {
            return automationRandomUtility.generateRandomIntRangeWithExclusion(minValue, maxValue, exclude);
        }
    }

    public String generateRandomStringCapitalLetter(
                    AutomationRandomStringCapitalLetterGeneratorDTO automationRandomStringCapitalLetterGeneratorDTO) {
        Integer length = automationRandomStringCapitalLetterGeneratorDTO.getLength();
        char startCharacter = automationRandomStringCapitalLetterGeneratorDTO.getStartCharacter();
        char endCharacter = automationRandomStringCapitalLetterGeneratorDTO.getEndCharacter();
        char[] exclude = automationRandomStringCapitalLetterGeneratorDTO.getExclusions();
        if (length == null && (int) startCharacter == 0 && (int) endCharacter == 0 && exclude == null) {
            return automationRandomUtility.generateRandomStringCapitalLetters();
        } else if (length != 0 && (int) startCharacter == 0 && (int) endCharacter == 0 && exclude == null) {
            return automationRandomUtility.generateRandomStringCapitalLetters(length);
        } else if (length != 0 && (int) startCharacter != 0 && (int) endCharacter == 0 && exclude == null) {
            return automationRandomUtility.generateRandomStringCapitalLetters(length, startCharacter);
        } else if (length != 0 && (int) startCharacter != 0 && (int) endCharacter != 0 && exclude == null) {
            return automationRandomUtility.generateRandomStringCapitalLettersRange(length, startCharacter,
                            endCharacter);
        } else if (length != 0 && (int) startCharacter == 0 && (int) endCharacter == 0 && exclude != null) {
            return automationRandomUtility.generateRandomStringCapitalLettersWithExclusion(length, exclude);
        } else {
            return automationRandomUtility.generateRandomStringCapitalLettersRangeWithExclusion(length, startCharacter,
                            endCharacter, exclude);
        }

    }

    public String generateRandomStringSmallLetter(
                    AutomationRandomStringSmallLetterGeneratorDTO automationRandomStringSmallLetterGeneratorDTO) {
        int length = automationRandomStringSmallLetterGeneratorDTO.getLength();
        char startCharacter = automationRandomStringSmallLetterGeneratorDTO.getStartCharacter();
        char endCharacter = automationRandomStringSmallLetterGeneratorDTO.getEndCharacter();
        char[] exclude = automationRandomStringSmallLetterGeneratorDTO.getExclusions();
        if (length != 0 && (int) startCharacter == 0 && (int) endCharacter == 0 && exclude == null) {
            return automationRandomUtility.generateRandomStringSmallLetters(length);
        } else if (length != 0 && (int) startCharacter != 0 && (int) endCharacter == 0 && exclude == null) {
            return automationRandomUtility.generateRandomStringSmallLetters(length, startCharacter);
        } else if (length != 0 && (int) startCharacter != 0 && (int) endCharacter != 0 && exclude == null) {
            return automationRandomUtility.generateRandomStringSmallLettersRange(length, startCharacter, endCharacter);
        } else if (length != 0 && (int) startCharacter == 0 && (int) endCharacter == 0 && exclude != null) {
            return automationRandomUtility.generateRandomStringSmallLettersWithExclusion(length, exclude);
        } else if (length != 0 && (int) startCharacter != 0 && (int) endCharacter != 0 && exclude != null) {
            return automationRandomUtility.generateRandomStringSmallLettersRangeWithExclusion(length, startCharacter,
                            endCharacter, exclude);
        } else {
            return null;
        }

    }

    public String generateRandomStringOutOfGivenChar(
                    AutomationRandomStringOutOfGivenCharGeneratorDTO automationRandomStringOutOfGivenCharGeneratorDTO) {
        int length = automationRandomStringOutOfGivenCharGeneratorDTO.getLength();
        char[] characters = automationRandomStringOutOfGivenCharGeneratorDTO.getCharacters();
        String stringOfChars = automationRandomStringOutOfGivenCharGeneratorDTO.getStringOfChars();
        if (length != 0 && characters != null && stringOfChars == null) {
            return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, characters);
        } else if (length == 0 && characters != null && stringOfChars == null) {
            return automationRandomUtility.generateRandomStringOutOfGivenCharactersWithoutLength(characters);
        } else {
            return automationRandomUtility.generateRandomStringOutOfGivenCharactersFromString(length, stringOfChars);
        }

    }

    public String generateRandomStringOfNumbers(AutomationRandomStringGeneratorDTO automationRandomStringGeneratorDTO) {
        Integer length = automationRandomStringGeneratorDTO.getLength();
        char[] exclusions = automationRandomStringGeneratorDTO.getExclusions();
        char[] inclusions = automationRandomStringGeneratorDTO.getExclusions();

          if (length != null) {
              if (inclusions == null && exclusions == null) {
                  return automationRandomUtility.generateRandomStringOfNumbers(length);

              } else if (inclusions != null && exclusions == null) {
                  return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, inclusions);
              } else if (inclusions == null && exclusions != null) {
                  return automationRandomUtility.generateRandomNumericStringWithExclusions(length, exclusions);
              }
          } else {
              if (inclusions != null && exclusions == null) {
                  return automationRandomUtility.generateRandomStringOutOfGivenCharacters(inclusions);
              } else if (inclusions == null && exclusions != null) {
                  return automationRandomUtility.generateRandomNumericStringWithExclusions(exclusions);
              }
          }
          return automationRandomUtility.generateRandomStringOfNumbers();
      }

    public String generateRandomStringWithSmallAndCapitalChar(
                    AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO automationRandomStringWithSmallAndCapitalCharGeneratorDTO) {
        Integer length = automationRandomStringWithSmallAndCapitalCharGeneratorDTO.getLength();
        char[] exclusions = automationRandomStringWithSmallAndCapitalCharGeneratorDTO.getExclusions();
        char[] inclusions = automationRandomStringWithSmallAndCapitalCharGeneratorDTO.getInclusions();
        if (length != null) {
            if (inclusions == null && exclusions == null) {
                return automationRandomUtility.generateRandomStringCapitalSmallMix(length);

            } else if (inclusions != null && exclusions == null) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, inclusions);
            } else if (inclusions == null && exclusions != null) {
                return automationRandomUtility.generateRandomStringCapitalSmallMix(length, exclusions);
            }
        } else {
            if (inclusions != null && exclusions == null) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(inclusions);
            } else if (inclusions == null && exclusions != null) {
                return automationRandomUtility.generateRandomStringCapitalSmallMix(exclusions);
            }
        }
        return automationRandomUtility.generateRandomStringCapitalSmallMix();
    }

    public String generateRandomEveryThing(AutomationRandomStringEverythingDTO automationRandomStringEverythingDTO) {

        Integer length = automationRandomStringEverythingDTO.getLength();
        char[] exclusions = automationRandomStringEverythingDTO.getExclusions();
        char[] inclusions = automationRandomStringEverythingDTO.getInclusions();
        if (length != null) {
            if (inclusions == null && exclusions == null) {
                return automationRandomUtility.generateRandomStringEverything(length);

            } else if (inclusions != null && exclusions == null) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, inclusions);
            } else if (inclusions == null && exclusions != null) {
                return automationRandomUtility.generateRandomStringEverythingWithExclusions(length, exclusions);
            }
        } else {
            if (inclusions != null && exclusions == null) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(inclusions);
            } else if (inclusions == null && exclusions != null) {
                return automationRandomUtility.generateRandomStringEverythingWithExclusions(exclusions);
            }
        }
        return automationRandomUtility.generateRandomStringEverything();
    }

    public String generateRandomAlphaNumeric(
                    AutomationRandomGenerateAlphaNumericDTO automationRandomGenerateAlphaNumericDTO) {
        Integer length = automationRandomGenerateAlphaNumericDTO.getLength();
        char[] exclusions = automationRandomGenerateAlphaNumericDTO.getExclusions();
        if (length != null) {
            if (exclusions == null) {
                return automationRandomUtility.generateRandomStringAlphaNumeric(length);
            } else {
                return automationRandomUtility.generateRandomStringAlphaNumericWithExclusions(length, exclusions);
            }
        } else {
            if (exclusions == null) {
                return automationRandomUtility.generateRandomStringAlphaNumeric();
            } else {
                return automationRandomUtility.generateRandomStringAlphaNumericWithExclusions(exclusions);
            }
        }
    }

    public String generateRandomDouble(AutomationRandomDoubleGenerateDTO automationRandomDoubleGenerateDTO) {
        return automationRandomUtility.getRandomDoubleBetweenRange(automationRandomDoubleGenerateDTO.getMin(),
                        automationRandomDoubleGenerateDTO.getMax());
    }

    public String generateRandomSentence(AutomationGenerateRandomSentenceDTO automationGenerateRandomSentenceDTO) {
        if (automationGenerateRandomSentenceDTO.getLength() != null) {
            return automationRandomUtility.generateRandomSentence(automationGenerateRandomSentenceDTO.getLength());
        } else {
            return automationRandomUtility.generateRandomSentence();
        }
    }

}

