package com.infogain.automation.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.constants.AutomationConstants;
import com.infogain.automation.dto.AutomationGenerateRandomSentenceDTO;
import com.infogain.automation.dto.AutomationRandomDoubleGenerateDTO;
import com.infogain.automation.dto.AutomationRandomGenerateAlphaNumericDTO;
import com.infogain.automation.dto.AutomationRandomGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomIntegerGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomSpecialCharGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringCapitalLetterGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringEverythingDTO;
import com.infogain.automation.dto.AutomationRandomStringGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringSmallLetterGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO;
import com.infogain.automation.model.AutomationRegexData;
import com.infogain.automation.utilities.AutomationRandomUtility;

@Service
public class RandomService {

    private AutomationRandomUtility automationRandomUtility;

    @Autowired
    public RandomService(AutomationRandomUtility automationRandomUtility) {
        this.automationRandomUtility = automationRandomUtility;
    }

    public String generateRandomString(AutomationRandomGeneratorDTO automationRandomGeneratorDTO) {
        return generateString(automationRandomGeneratorDTO.getAutomationRegexDatalist());
    }

    private String generateString(List<AutomationRegexData> automationRegexDataList) {
        StringBuilder generatedString = new StringBuilder(automationRegexDataList.size());
        for (AutomationRegexData automationRegexData : automationRegexDataList) {
            generatedString.append(automationRegexData.getRegexSymbol().generate(automationRegexData.getLength(),
                            automationRegexData.getOptions()));
        }
        return generatedString.toString();
    }

    public String generateRandomSpecialChar(
                    AutomationRandomSpecialCharGeneratorDTO automationRandomSpecialCharGeneratorDTO) {
        Integer length = automationRandomSpecialCharGeneratorDTO.getLength();
        String exclusions = automationRandomSpecialCharGeneratorDTO.getExclusions();
        String inclusions = automationRandomSpecialCharGeneratorDTO.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        char[] exclusionsOrExclusions = convertStringToCharArray(inclusionsFlag ? inclusions : exclusions);
        if (length != null) {
            if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, exclusionsOrExclusions);
            } else if (exclusionsFlag) {
                return automationRandomUtility.generateRandomSpecialCharactersWithExclusions(length,
                                exclusionsOrExclusions);
            }
            return automationRandomUtility.generateRandomSpecialCharacter(length);
        } else {
            if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(exclusionsOrExclusions);
            } else if (exclusionsFlag) {
                return automationRandomUtility.generateRandomSpecialCharactersWithExclusions(exclusionsOrExclusions);
            }
            return automationRandomUtility.generateRandomSpecialCharacter();
        }
    }

    public int generateRandomInteger(AutomationRandomIntegerGeneratorDTO automationRandomIntegerGeneratorDTO) {
        Integer minValue = automationRandomIntegerGeneratorDTO.getMinValue();
        Integer maxValue = automationRandomIntegerGeneratorDTO.getMaxValue();
        String exclusions = automationRandomIntegerGeneratorDTO.getExclusions();
        int[] exclusion = null;
        if (exclusions != null && !exclusions.isEmpty()) {
            exclusion = Arrays.stream(exclusions.split(AutomationConstants.COMMA_REGEX)).mapToInt(Integer::parseInt)
                            .toArray();
        }
        if (minValue == null && maxValue == null && exclusion == null) {
            return automationRandomUtility.generateRandomInt();
        } else if (minValue != null && maxValue != null && exclusion == null) {
            return automationRandomUtility.generateRandomIntRange(minValue, maxValue);
        } else {
            return automationRandomUtility.generateRandomIntRangeWithExclusion(minValue, maxValue, exclusion);
        }
    }

    public String generateRandomStringCapitalLetter(
                    AutomationRandomStringCapitalLetterGeneratorDTO automationRandomStringCapitalLetterGeneratorDTO) {
        final char EMPTY = Character.MIN_VALUE;
        Integer length = automationRandomStringCapitalLetterGeneratorDTO.getLength();
        char startCharacter = automationRandomStringCapitalLetterGeneratorDTO.getStartCharacter();
        char endCharacter = automationRandomStringCapitalLetterGeneratorDTO.getEndCharacter();
        char constCharacter = automationRandomStringCapitalLetterGeneratorDTO.getConstantCharacter();
        String exclusions = automationRandomStringCapitalLetterGeneratorDTO.getExclusions();
        String inclusions = automationRandomStringCapitalLetterGeneratorDTO.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        char[] exclusionsOrExclusions = convertStringToCharArray(inclusionsFlag ? inclusions : exclusions);
        if (length != null) {
            if (constCharacter != EMPTY) {
                return automationRandomUtility.generateRandomStringCapitalLetters(length, constCharacter);
            } else if (startCharacter != EMPTY && endCharacter != EMPTY) {
                if (exclusionsOrExclusions == null) {
                    return automationRandomUtility.generateRandomStringCapitalLettersRange(length, startCharacter,
                                    endCharacter);
                } else if (inclusionsFlag) {
                    return automationRandomUtility.generateRandomStringAllLettersRangeWithInclusion(length,
                                    startCharacter, endCharacter, exclusionsOrExclusions);
                } else {
                    return automationRandomUtility.generateRandomStringCapitalLettersRangeWithExclusion(length,
                                    startCharacter, endCharacter, exclusionsOrExclusions);
                }
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringAllLettersRangeWithInclusion(length, startCharacter,
                                endCharacter, exclusionsOrExclusions);
            } else if (exclusionsFlag) {
                return automationRandomUtility.generateRandomStringCapitalLettersWithExclusion(length,
                                exclusionsOrExclusions);
            }
            return automationRandomUtility.generateRandomStringCapitalLetters(length);
        } else {
            if (constCharacter != EMPTY) {
                return automationRandomUtility.generateRandomStringCapitalLetters(constCharacter);
            } else if (startCharacter != EMPTY && endCharacter != EMPTY) {
                if (exclusionsOrExclusions == null) {
                    return automationRandomUtility.generateRandomStringCapitalLettersRange(startCharacter,
                                    endCharacter);
                } else if (inclusionsFlag) {
                    return automationRandomUtility.generateRandomStringAllLettersRangeWithInclusion(startCharacter,
                                    endCharacter, exclusionsOrExclusions);
                } else {
                    return automationRandomUtility.generateRandomStringCapitalLettersRangeWithExclusion(startCharacter,
                                    endCharacter, exclusionsOrExclusions);
                }
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringAllLettersRangeWithInclusion(startCharacter,
                                endCharacter, exclusionsOrExclusions);
            } else if (exclusionsFlag) {
                return automationRandomUtility.generateRandomStringCapitalLettersWithExclusion(exclusionsOrExclusions);
            }
            return automationRandomUtility.generateRandomStringCapitalLetters();
        }
    }

    public String generateRandomStringSmallLetter(
                    AutomationRandomStringSmallLetterGeneratorDTO automationRandomStringSmallLetterGeneratorDTO) {
        final char EMPTY = Character.MIN_VALUE;
        Integer length = automationRandomStringSmallLetterGeneratorDTO.getLength();
        char startCharacter = automationRandomStringSmallLetterGeneratorDTO.getStartCharacter();
        char endCharacter = automationRandomStringSmallLetterGeneratorDTO.getEndCharacter();
        char constCharacter = automationRandomStringSmallLetterGeneratorDTO.getConstantCharacter();
        String exclusions = automationRandomStringSmallLetterGeneratorDTO.getExclusions();
        String inclusions = automationRandomStringSmallLetterGeneratorDTO.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        char[] exclusionsOrExclusions = convertStringToCharArray(inclusionsFlag ? inclusions : exclusions);
        if (length != null) {
            if (constCharacter != EMPTY) {
                return automationRandomUtility.generateRandomStringSmallLetters(length, constCharacter);
            } else if (startCharacter != EMPTY && endCharacter != EMPTY) {
                if (exclusionsOrExclusions == null) {
                    return automationRandomUtility.generateRandomStringSmallLettersRange(length, startCharacter,
                                    endCharacter);
                } else if (inclusionsFlag) {
                    return automationRandomUtility.generateRandomStringAllLettersRangeWithInclusion(length,
                                    startCharacter, endCharacter, exclusionsOrExclusions);
                } else {
                    return automationRandomUtility.generateRandomStringSmallLettersRangeWithExclusion(length,
                                    startCharacter, endCharacter, exclusionsOrExclusions);
                }
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringAllLettersRangeWithInclusion(length, startCharacter,
                                endCharacter, exclusionsOrExclusions);
            } else if (exclusionsFlag) {
                return automationRandomUtility.generateRandomStringSmallLettersWithExclusion(length,
                                exclusionsOrExclusions);
            }
            return automationRandomUtility.generateRandomStringSmallLetters(length);
        } else {
            if (constCharacter != EMPTY) {
                return automationRandomUtility.generateRandomStringSmallLetters(constCharacter);
            } else if (startCharacter != EMPTY && endCharacter != EMPTY) {
                if (exclusionsOrExclusions == null) {
                    return automationRandomUtility.generateRandomStringSmallLettersRange(startCharacter, endCharacter);
                } else if (inclusionsFlag) {
                    return automationRandomUtility.generateRandomStringAllLettersRangeWithInclusion(startCharacter,
                                    endCharacter, exclusionsOrExclusions);
                } else {
                    return automationRandomUtility.generateRandomStringSmallLettersRangeWithExclusion(startCharacter,
                                    endCharacter, exclusionsOrExclusions);
                }
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringAllLettersRangeWithInclusion(startCharacter,
                                endCharacter, exclusionsOrExclusions);
            } else if (exclusionsFlag) {
                return automationRandomUtility.generateRandomStringSmallLettersWithExclusion(exclusionsOrExclusions);
            }
            return automationRandomUtility.generateRandomStringSmallLetters();
        }
    }

    public String generateRandomStringOfNumbers(AutomationRandomStringGeneratorDTO automationRandomStringGeneratorDTO) {
        Integer length = automationRandomStringGeneratorDTO.getLength();
        String exclusions = automationRandomStringGeneratorDTO.getExclusions();
        String inclusions = automationRandomStringGeneratorDTO.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        char[] exclusionsOrExclusions = convertStringToCharArray(inclusionsFlag ? inclusions : exclusions);
        if (length != null) {
            if (exclusionsOrExclusions == null) {
                return automationRandomUtility.generateRandomStringOfNumbers(length);
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, exclusionsOrExclusions);
            } else {
                return automationRandomUtility.generateRandomNumericStringWithExclusions(length,
                                exclusionsOrExclusions);
            }
        } else {
            if (exclusionsOrExclusions == null) {
                return automationRandomUtility.generateRandomStringOfNumbers();
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(exclusionsOrExclusions);
            } else {
                return automationRandomUtility.generateRandomNumericStringWithExclusions(exclusionsOrExclusions);
            }
        }
    }

    public String generateRandomStringWithSmallAndCapitalChar(
                    AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO automationRandomStringWithSmallAndCapitalCharGeneratorDTO) {
        Integer length = automationRandomStringWithSmallAndCapitalCharGeneratorDTO.getLength();
        String exclusions = automationRandomStringWithSmallAndCapitalCharGeneratorDTO.getExclusions();
        String inclusions = automationRandomStringWithSmallAndCapitalCharGeneratorDTO.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        char[] exclusionsOrExclusions = convertStringToCharArray(inclusionsFlag ? inclusions : exclusions);
        if (length != null) {
            if (exclusionsOrExclusions == null) {
                return automationRandomUtility.generateRandomStringCapitalSmallMix(length);
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, exclusionsOrExclusions);
            } else {
                return automationRandomUtility.generateRandomStringCapitalSmallMix(length, exclusionsOrExclusions);
            }
        } else {
            if (exclusionsOrExclusions == null) {
                return automationRandomUtility.generateRandomStringCapitalSmallMix();
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(exclusionsOrExclusions);
            } else {
                return automationRandomUtility.generateRandomStringCapitalSmallMix(exclusionsOrExclusions);
            }
        }
    }

    public String generateRandomEveryThing(AutomationRandomStringEverythingDTO automationRandomStringEverythingDTO) {
        Integer length = automationRandomStringEverythingDTO.getLength();
        String exclusions = automationRandomStringEverythingDTO.getExclusions();
        String inclusions = automationRandomStringEverythingDTO.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        char[] exclusionsOrExclusions = convertStringToCharArray(inclusionsFlag ? inclusions : exclusions);
        if (length != null) {
            if (exclusionsOrExclusions == null) {
                return automationRandomUtility.generateRandomStringEverything(length);
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, exclusionsOrExclusions);
            } else {
                return automationRandomUtility.generateRandomStringEverythingWithExclusions(length,
                                exclusionsOrExclusions);
            }
        } else {
            if (exclusionsOrExclusions == null) {
                return automationRandomUtility.generateRandomStringEverything();
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(exclusionsOrExclusions);
            } else {
                return automationRandomUtility.generateRandomStringEverythingWithExclusions(exclusionsOrExclusions);
            }
        }
    }

    public String generateRandomAlphaNumeric(
                    AutomationRandomGenerateAlphaNumericDTO automationRandomGenerateAlphaNumericDTO) {
        Integer length = automationRandomGenerateAlphaNumericDTO.getLength();
        String exclusions = automationRandomGenerateAlphaNumericDTO.getExclusions();
        String inclusions = automationRandomGenerateAlphaNumericDTO.getInclusions();
        boolean inclusionsFlag = inclusions != null && !inclusions.isEmpty();
        boolean exclusionsFlag = exclusions != null && !exclusions.isEmpty();
        char[] exclusionsOrExclusions = convertStringToCharArray(inclusionsFlag ? inclusions : exclusions);
        if (length != null) {
            if (exclusionsFlag) {
                return automationRandomUtility.generateRandomStringAlphaNumericWithExclusions(length,
                                exclusionsOrExclusions);
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(length, exclusionsOrExclusions);
            } else {
                return automationRandomUtility.generateRandomStringAlphaNumeric(length);
            }
        } else {
            if (exclusionsFlag) {
                return automationRandomUtility.generateRandomStringAlphaNumericWithExclusions(exclusionsOrExclusions);
            } else if (inclusionsFlag) {
                return automationRandomUtility.generateRandomStringOutOfGivenCharacters(exclusionsOrExclusions);
            } else {
                return automationRandomUtility.generateRandomStringAlphaNumeric();
            }
        }
    }

    public String generateRandomDouble(AutomationRandomDoubleGenerateDTO automationRandomDoubleGenerateDTO) {
        Double min = automationRandomDoubleGenerateDTO.getMin();
        Double max = automationRandomDoubleGenerateDTO.getMax();
        Integer precision = automationRandomDoubleGenerateDTO.getPrecision();
        if (precision != null) {
            if (min != null && max != null) {
                return automationRandomUtility.getRandomDoubleBetweenRangeWithScale(min, max, precision);
            } else {
                return automationRandomUtility.getRandomDoubleBetweenRangeWithScaleAndRandomRange(precision);
            }
        } else {
            if (min != null && max != null) {
                return automationRandomUtility.getRandomDoubleBetweenRange(min, max);
            } else {
                return automationRandomUtility.getRandomDoubleBetweenRangeWithRandomRangeAndScale();
            }
        }
    }

    public String generateRandomSentence(AutomationGenerateRandomSentenceDTO automationGenerateRandomSentenceDTO) {
        if (automationGenerateRandomSentenceDTO.getLength() != null) {
            return automationRandomUtility.generateRandomSentence(automationGenerateRandomSentenceDTO.getLength());
        } else {
            return automationRandomUtility.generateRandomSentence();
        }
    }

    private char[] convertStringToCharArray(String stringToConvert) {
        char[] charArray = null;
        if (stringToConvert != null && !stringToConvert.isEmpty()) {
            String[] spl = stringToConvert.split(AutomationConstants.COMMA_REGEX);
            charArray = new char[spl.length];
            for (int i = 0; i < charArray.length; i++) {
                charArray[i] = spl[i].charAt(0);
            }
        }
        return charArray;
    }
}
