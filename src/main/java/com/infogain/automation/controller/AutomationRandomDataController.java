package com.infogain.automation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
import com.infogain.automation.dto.AutomationResponseRandomDataDTO;
import com.infogain.automation.service.RandomService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/generateRandomData/")
public class AutomationRandomDataController {

    private final RandomService randomService;

    @Autowired
    public AutomationRandomDataController(final RandomService randomService) {
        this.randomService = randomService;
    }

    @PostMapping(path = "/specialCharacterString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/specialCharacterString",
                    notes = "This API is used to generate random string of special characters only ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomSpecialChar(
                    @RequestBody @Valid AutomationRandomSpecialCharGeneratorDTO automationRandomSpecialCharGeneratorDTO) {
        return generateResponse(randomService.generateRandomSpecialChar(automationRandomSpecialCharGeneratorDTO));
    }

    @PostMapping(path = "/integerValueAsString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/integerValueAsString",
                    notes = "This API is used to generate random integer value as string  ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomInt(
                    @RequestBody @Valid AutomationRandomIntegerGeneratorDTO automationRandomIntegerGeneratorDTO) {
        return generateResponse(
                        String.valueOf(randomService.generateRandomInteger(automationRandomIntegerGeneratorDTO)));
    }

    @PostMapping(path = "/capitalLettersString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/capitalLettersString",
                    notes = "This API is used to generate random string of capital letters only ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomStringCapitalLetters(
                    @RequestBody @Valid AutomationRandomStringCapitalLetterGeneratorDTO automationRandomStringCapitalLetterGeneratorDTO) {
        return generateResponse(randomService
                        .generateRandomStringCapitalLetter(automationRandomStringCapitalLetterGeneratorDTO));
    }

    @PostMapping(path = "/smallLettersString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/smallLettersString",
                    notes = "This API is used to generate random string of small letters only  ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomStringSmallLetters(
                    @RequestBody @Valid AutomationRandomStringSmallLetterGeneratorDTO automationRandomStringSmallLetterGeneratorDTO) {
        return generateResponse(
                        randomService.generateRandomStringSmallLetter(automationRandomStringSmallLetterGeneratorDTO));
    }

    @PostMapping(path = "/StringofNumbers", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/StringofNumbers",
                    notes = "This API is used to generate random string of number letters only  ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomString(
                    @RequestBody @Valid AutomationRandomStringGeneratorDTO automationRandomStringGeneratorDTO) {
        return generateResponse(randomService.generateRandomStringOfNumbers(automationRandomStringGeneratorDTO));
    }

    @PostMapping(path = "/SmallCapitalLettersMixString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/SmallCapitalLettersMixString",
                    notes = "This API is used to generate random string of small and capital mix letters only ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomStringWithSmallAndCapitalChar(
                    @RequestBody @Valid AutomationRandomStringWithSmallAndCapitalCharGeneratorDTO automationRandomStringWithSmallAndCapitalCharGeneratorDTO) {
        return generateResponse(randomService.generateRandomStringWithSmallAndCapitalChar(
                        automationRandomStringWithSmallAndCapitalCharGeneratorDTO));
    }

    @PostMapping(path = "/anyString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/anyString",
                    notes = "This API is used to generate any random string including numbers, special characters, small and capital letters ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomStringGenerateEverything(
                    @RequestBody @Valid AutomationRandomStringEverythingDTO automationRandomStringEverythingDTO) {

        return generateResponse(randomService.generateRandomEveryThing(automationRandomStringEverythingDTO));
    }

    @PostMapping(path = "/alphanumericString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/alphanumericString",
                    notes = "This API is used to generate random string including numbers, small and capital letters ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomStringGenerateAplhaNumeric(
                    @RequestBody @Valid AutomationRandomGenerateAlphaNumericDTO automationRandomGenerateAlphaNumericDTO) {
        return generateResponse(randomService.generateRandomAlphaNumeric(automationRandomGenerateAlphaNumericDTO));
    }

    @PostMapping(path = "/doubleValueAsString", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/doubleValueAsString",
                    notes = "This API is used to generate random double (decimal) value within given range as string  ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomStringGenerateRandomDouble(
                    @RequestBody @Valid AutomationRandomDoubleGenerateDTO automationRandomDoubleGenerateDTO) {
        return generateResponse(randomService.generateRandomDouble(automationRandomDoubleGenerateDTO));
    }

    @PostMapping(path = "/randomSentence", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/randomSentence",
                    notes = "This API is used to generate random sentence of given or random length ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> randomStringGenerateRandomSentence(
                    @RequestBody @Valid AutomationGenerateRandomSentenceDTO automationGenerateRandomSentenceDTO) {
        return generateResponse(randomService.generateRandomSentence(automationGenerateRandomSentenceDTO));
    }

    @PostMapping(path = "/instructionsToGenerateRandomData", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/instructionsToGenerateRandomData",
                    notes = "This API is used to generate random integer value as string  ",
                    response = AutomationResponseRandomDataDTO.class, protocols = "http,https")
    @ApiResponses({@ApiResponse(code = 201, message = "String Generated sucessfully"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutomationResponseRandomDataDTO> ramdomString(
                    @RequestBody @Valid AutomationRandomGeneratorDTO automationRandomGeneratorDTO) {
        return generateResponse(randomService.generateRandomString(automationRandomGeneratorDTO));
    }

    private ResponseEntity<AutomationResponseRandomDataDTO> generateResponse(String responseString) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new AutomationResponseRandomDataDTO(responseString));
    }
}
