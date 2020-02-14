package com.infogain.automation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infogain.automation.dto.RandomAttributeDTO;
import com.infogain.automation.service.RandomService;

@RestController
public class RandomStringController {

    private final RandomService randomService;

    @Autowired
    public RandomStringController(final RandomService randomService) {
        this.randomService = randomService;
    }

    @PostMapping(path = "/random", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String ramdomString(@RequestBody RandomAttributeDTO randomAttributeDTO) {
        return randomService.generateRandomString(randomAttributeDTO);
    }
}
