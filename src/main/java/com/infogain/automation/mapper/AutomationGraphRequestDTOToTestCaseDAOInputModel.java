package com.infogain.automation.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationGraphRequestDTO;
import com.infogain.automation.model.TestCaseDAOInputModel;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is for mapping data from Graph Request DTO to Test Case DAO Input Model
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 16, 2019
 */
public class AutomationGraphRequestDTOToTestCaseDAOInputModel {

    private static final Logger logger = LogManager.getLogger(AutomationGraphRequestDTOToTestCaseDAOInputModel.class);

    /**
     * This method takes the data from graph Request DTO {@link AutomationGraphRequestDTO} and convert it to
     * {@link TestCaseDAOInputModel}
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO}
     * @return TestCaseDAOInputModel
     * @since Dec 16, 2019
     */
    public static TestCaseDAOInputModel convertAutomationGraphDTOToTestInputModel(
                    AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry(
                        "convertAutomationGraphDTOToTestInputModel method of AutomationGraphRequestDTOToTestCaseDAOInputModel class");
        String startDate = automationGraphRequestDTO.getStartDate();
        String endDate = automationGraphRequestDTO.getEndDate();
        String[] inputexcelname = automationGraphRequestDTO.getSheets().split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // convert String to LocalDate
        LocalDate startlocalDate = LocalDate.parse(startDate, formatter);
        LocalDate endlocalDate = LocalDate.parse(endDate, formatter);
        logger.info("Start Date is {} , End Date is {} , excel Names are {}", startlocalDate, endlocalDate,
                        inputexcelname);
        Set<String> inputexlname = new HashSet<>();
        for (String s : inputexcelname) {
            inputexlname.add(s);
        }
        return logger.traceExit(new TestCaseDAOInputModel(startlocalDate, endlocalDate, inputexlname));


    }
}
