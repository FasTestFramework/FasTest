package com.infogain.automation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.infogain.automation.dto.AutomationRandomStringSmallLetterGeneratorDTO;
import com.infogain.automation.dto.AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO;
import com.infogain.automation.dto.AutomationResponseStringDTO;
import com.infogain.automation.service.RandomService;
import com.infogain.automation.utilities.AutomationUtility;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/generateRandomData/")
public class AutomationRandomDataController {

    private final RandomService randomService;
    private final AutomationUtility automationUtility;

    @Autowired
    public AutomationRandomDataController(final RandomService randomService,
                    final AutomationUtility automationUtility) {
        this.randomService = randomService;
        this.automationUtility = automationUtility;
    }

    @Validated
    @PostMapping(path = "/specialCharacterString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomSpecialChar(
                    @RequestBody @Valid AutomationRandomSpecialCharGeneratorDTO automationRandomSpecialCharGeneratorDTO) {
        return automationUtility.generateResponse(
                        randomService.generateRandomSpecialChar(automationRandomSpecialCharGeneratorDTO));
    }

    @Validated
    @PostMapping(path = "/integerValueAsString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomInt(
                    @RequestBody @Valid AutomationRandomIntegerGeneratorDTO automationRandomIntegerGeneratorDTO) {
        return automationUtility.generateResponse(
                        String.valueOf(randomService.generateRandomInteger(automationRandomIntegerGeneratorDTO)));
    }

    @Validated
    @PostMapping(path = "/capitalLettersString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomStringCapitalLetters(
                    @RequestBody @Valid AutomationRandomStringCapitalLetterGeneratorDTO automationRandomStringCapitalLetterGeneratorDTO) {
        return automationUtility.generateResponse(randomService
                        .generateRandomStringCapitalLetter(automationRandomStringCapitalLetterGeneratorDTO));
    }

    @Validated
    @PostMapping(path = "/smallLettersString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomStringSmallLetters(
                    @RequestBody @Valid AutomationRandomStringSmallLetterGeneratorDTO automationRandomStringSmallLetterGeneratorDTO) {
        return automationUtility.generateResponse(
                        randomService.generateRandomStringSmallLetter(automationRandomStringSmallLetterGeneratorDTO));
    }

    @Validated
    @PostMapping(path = "/StringofNumbers", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomString(
                    @RequestBody @Valid AutomationRandomStringGeneratorDTO automationRandomStringGeneratorDTO) {
        return automationUtility.generateResponse(
                        randomService.generateRandomStringOfNumbers(automationRandomStringGeneratorDTO));
    }

    @Validated
    @PostMapping(path = "/SmallCapitalLettersMixString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomStringWithSmallAndCapitalChar(
                    @RequestBody @Valid AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO automationRandomStringWithSmallAndCapitalCharGeneratorDTO) {
        return automationUtility.generateResponse(randomService.generateRandomStringWithSmallAndCapitalChar(
                        automationRandomStringWithSmallAndCapitalCharGeneratorDTO));
    }

    @Validated
    @PostMapping(path = "/anyString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomStringGenerateEverything(
                    @RequestBody @Valid AutomationRandomStringEverythingDTO automationRandomStringEverythingDTO) {

        return automationUtility
                        .generateResponse(randomService.generateRandomEveryThing(automationRandomStringEverythingDTO));
    }

    @Validated
    @PostMapping(path = "/alphanumericString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomStringGenerateAplhaNumeric(
                    @RequestBody @Valid AutomationRandomGenerateAlphaNumericDTO automationRandomGenerateAlphaNumericDTO) {
        return automationUtility.generateResponse(
                        randomService.generateRandomAlphaNumeric(automationRandomGenerateAlphaNumericDTO));
    }

    @Validated
    @PostMapping(path = "/doubleValueAsString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomStringGenerateRandomDouble(
                    @RequestBody @Valid AutomationRandomDoubleGenerateDTO automationRandomDoubleGenerateDTO) {
        return automationUtility
                        .generateResponse(randomService.generateRandomDouble(automationRandomDoubleGenerateDTO));
    }

    @Validated
    @PostMapping(path = "/randomSentence", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> randomStringGenerateRandomSentence(
                    @RequestBody @Valid AutomationGenerateRandomSentenceDTO automationGenerateRandomSentenceDTO) {
        return automationUtility
                        .generateResponse(randomService.generateRandomSentence(automationGenerateRandomSentenceDTO));
    }

    @Validated
    @PostMapping(path = "/instructionsToGenerateRandomData", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutomationResponseStringDTO> ramdomString(
                    @RequestBody @Valid AutomationRandomGeneratorDTO automationRandomGeneratorDTO) {
        return automationUtility.generateResponse(randomService.generateRandomString(automationRandomGeneratorDTO));
    }
}
