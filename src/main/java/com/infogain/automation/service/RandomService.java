package com.infogain.automation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.dto.AutomationRandomGeneratorDTO;
import com.infogain.automation.utilities.RegexConverter;

@Service
public class RandomService {


    private RegexConverter regexConverter;

    @Autowired
    public RandomService(RegexConverter regexConverter) {
        this.regexConverter = regexConverter;
    }

    public String generateRandomString(AutomationRandomGeneratorDTO automationRandomGeneratorDTO) {
        return regexConverter.start(automationRandomGeneratorDTO.getinstructionsToGenerateRandomData());
    }
}
