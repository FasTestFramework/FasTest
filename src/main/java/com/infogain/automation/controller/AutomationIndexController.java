package com.infogain.automation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is MVC Controller class which helps to load UI
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
@CrossOrigin(origins = "*")
@Controller
public class AutomationIndexController {

    @GetMapping(path = "/")
    public String index() {
        return "index";
    }

}
