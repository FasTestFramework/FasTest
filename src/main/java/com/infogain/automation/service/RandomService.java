package com.infogain.automation.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.infogain.automation.dto.RandomAttributeDTO;

@Service
public class RandomService {

    public String generateRandomString(RandomAttributeDTO randomAttributeDTO) {
        if (!(randomAttributeDTO.isUseLetters() || randomAttributeDTO.isUseNumbers())) {
            return "Invalid Request";
        }
        return RandomStringUtils.random(randomAttributeDTO.getLength(), randomAttributeDTO.isUseLetters(),
                        randomAttributeDTO.isUseNumbers());

    }
}
