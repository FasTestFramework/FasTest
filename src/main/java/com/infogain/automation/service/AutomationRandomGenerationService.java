package com.infogain.automation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infogain.automation.dto.AutomationRandomGeneratorDTO;
import com.infogain.automation.model.AutomationRegexData;

@Service
public class AutomationRandomGenerationService {

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
}
