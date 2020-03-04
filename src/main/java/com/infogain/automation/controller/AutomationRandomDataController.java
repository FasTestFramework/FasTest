package com.infogain.automation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
import com.infogain.automation.service.RandomService;

@CrossOrigin(origins = "*")
@RestController
public class AutomationRandomDataController {

    private final RandomService randomService;

    @Autowired
    public AutomationRandomDataController(final RandomService randomService) {
        this.randomService = randomService;
    }

    @PostMapping(path = "/random", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomString(@RequestBody AutomationRandomGeneratorDTO regexDTO) {
        System.out.println(regexDTO);
        return randomService.generateRandomString(regexDTO);
    }
    @Validated
    @PostMapping(path = "/randomspecialchar", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomSpecialChar(
                    @RequestBody @Valid AutomationRandomSpecialCharGeneratorDTO automationRandomSpecialCharGeneratorDTO) {
        return randomService.generateRandomSpecialChar(automationRandomSpecialCharGeneratorDTO);
    }
    @Validated
    @PostMapping(path = "/randominteger", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public int randomInt(@RequestBody @Valid AutomationRandomIntegerGeneratorDTO automationRandomIntegerGeneratorDTO) {
        return randomService.generateRandomInteger(automationRandomIntegerGeneratorDTO);
    }
    @Validated
    @PostMapping(path = "/randomstringcapitalletters", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomStringCapitalLetters(
                    @RequestBody @Valid AutomationRandomStringCapitalLetterGeneratorDTO automationRandomStringCapitalLetterGeneratorDTO) {
        return randomService.generateRandomStringCapitalLetter(automationRandomStringCapitalLetterGeneratorDTO);
    }
    @Validated
    @PostMapping(path = "/randomstringsmallletters", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomStringSmallLetters(
                    @RequestBody @Valid AutomationRandomStringSmallLetterGeneratorDTO automationRandomStringSmallLetterGeneratorDTO) {
        return randomService.generateRandomStringSmallLetter(automationRandomStringSmallLetterGeneratorDTO);
    }
    @Validated
    @PostMapping(path = "/randomStringOutOfGivenChar", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomStringOutOfGivenChar(
                    @RequestBody @Valid AutomationRandomStringOutOfGivenCharGeneratorDTO automationRandomStringOutOfGivenCharGeneratorDTO) {
        return randomService.generateRandomStringOutOfGivenChar(automationRandomStringOutOfGivenCharGeneratorDTO);
    }
    @Validated
    @PostMapping(path = "/randomstringofnumbers", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomString(@RequestBody @Valid AutomationRandomStringGeneratorDTO automationRandomStringGeneratorDTO) {
        return randomService.generateRandomStringOfNumbers(automationRandomStringGeneratorDTO);
    }
    @Validated
    @PostMapping(path = "/randomstringsmallcapitalmix", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomStringWithSmallAndCapitalChar(
                    @RequestBody @Valid AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO automationRandomStringWithSmallAndCapitalCharGeneratorDTO) {
        return randomService.generateRandomStringWithSmallAndCapitalChar(
                        automationRandomStringWithSmallAndCapitalCharGeneratorDTO);
    }
    @Validated
    @PostMapping(path = "/randomEverything", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomStringGenerateEverything(
                    @RequestBody @Valid AutomationRandomStringEverythingDTO automationRandomStringEverythingDTO) {

        return randomService.generateRandomEveryThing(automationRandomStringEverythingDTO);
    }

    @Validated
    @PostMapping(path = "/randomAlphanumeric", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomStringGenerateAplhaNumeric(
                    @RequestBody @Valid AutomationRandomGenerateAlphaNumericDTO automationRandomGenerateAlphaNumericDTO) {
        return randomService.generateRandomAlphaNumeric(automationRandomGenerateAlphaNumericDTO);
    }
    @Validated
    @PostMapping(path = "/randomDouble", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomStringGenerateRandomDouble(
                    @RequestBody @Valid AutomationRandomDoubleGenerateDTO automationRandomDoubleGenerateDTO) {
        return randomService.generateRandomDouble(automationRandomDoubleGenerateDTO);
    }
    @Validated
    @PostMapping(path = "/randomSentence", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String randomStringGenerateRandomSentence(
                    @RequestBody @Valid AutomationGenerateRandomSentenceDTO automationGenerateRandomSentenceDTO) {
        return randomService.generateRandomSentence(automationGenerateRandomSentenceDTO);
    }
}
