package com.infogain.automation.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.dto.AutomationGraphRequestDTO;
import com.infogain.automation.dto.AutomationSprintDTO;
import com.infogain.automation.mapper.AutomationGraphRequestDTOToTestCaseDAOInputModel;
import com.infogain.automation.mapper.AutomationSprintInfoModelToAutomationSprintDTO;
import com.infogain.automation.model.AutomationOutputModel;
import com.infogain.automation.model.AutomationSprintInfoModel;
import com.infogain.automation.model.TestCaseDAOInputModel;
import com.infogain.automation.model.TestCaseDAOOutputModel;
import com.infogain.automation.repository.AutomationReportingRepository;
import com.infogain.automation.repository.AutomationSprintRepository;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is to get total Pass , Fail and Executed Test Cases
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Service
public class AutomationReportService {
    private static final Logger logger = LogManager.getLogger(AutomationReportService.class);
    @PersistenceContext
    private EntityManager entityManager;

    private AutomationReportingRepository automationReportingRepository;
    private AutomationSprintRepository automationSprintRepository;

    @Autowired
    public AutomationReportService(AutomationReportingRepository automationReportingRepository,
                    AutomationSprintRepository automationSprintRepository) {
        this.automationReportingRepository = automationReportingRepository;
        this.automationSprintRepository = automationSprintRepository;
    }

    /**
     * This method returns distinct Input Excel File Names
     * 
     * @return list of distinct Input Excel File Names
     * @since Dec 13, 2019
     */
    public List<String> distinctExcelName() {
        return automationReportingRepository.findAllDistinctpaiInputExcelName();
    }

    /**
     * This method saves the data to Database Model class
     * 
     * @param automationOutputModel instance of {@link AutomationOutputModel} through which we insert data into it
     * @return object of {@link AutomationOutputModel}
     * @since Dec 13, 2019
     */
    public AutomationOutputModel insertData(AutomationOutputModel automationOutputModel) {
        return automationReportingRepository.save(automationOutputModel);
    }


    /**
     * This method takes start Date and End Date from {@link AutomationGraphRequestDTO} and return the list of total
     * Pass Test cases
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO}
     * @return list of total Pass Test Cases
     * @since Dec 11, 2019
     */
    public List<TestCaseDAOOutputModel> getPassTestCaseList(AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry("getPassTestCaseList method of AutomationReportService class");
        TestCaseDAOInputModel testCaseDAOInputModel = AutomationGraphRequestDTOToTestCaseDAOInputModel
                        .convertAutomationGraphDTOToTestInputModel(automationGraphRequestDTO);
        List<TestCaseDAOOutputModel> testCaselist = automationReportingRepository.getInputExcelNameTotalPassTestCase(
                        testCaseDAOInputModel.getStartlocalDate(), testCaseDAOInputModel.getEndlocalDate(),
                        testCaseDAOInputModel.getInputexlname());
        return logger.traceExit(testCaselist);
    }


    /**
     * This method takes start Date and End Date from {@link AutomationGraphRequestDTO} and return the list of total
     * Fail Test cases
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO}
     * @return list of total fail Test Cases
     * @since Dec 11, 2019
     */
    public List<TestCaseDAOOutputModel> getFailTestCaseList(AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry("getFailTestCaseList method of AutomationReportService class");
        TestCaseDAOInputModel testCaseDAOInputModel = AutomationGraphRequestDTOToTestCaseDAOInputModel
                        .convertAutomationGraphDTOToTestInputModel(automationGraphRequestDTO);
        List<TestCaseDAOOutputModel> testCaselist = automationReportingRepository.getInputExcelNameTotalFailedTestCase(
                        testCaseDAOInputModel.getStartlocalDate(), testCaseDAOInputModel.getEndlocalDate(),
                        testCaseDAOInputModel.getInputexlname());
        return logger.traceExit(testCaselist);
    }

    /**
     * This method takes start Date and End Date from {@link AutomationGraphRequestDTO} and return the list of total
     * Executed Test cases
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO}
     * @return list of total Executed Test Cases
     * @since Dec 11, 2019
     */

    public List<TestCaseDAOOutputModel> getExecutedTestCaseList(AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry("getExecutedTestCaseList method of AutomationReportService class");
        TestCaseDAOInputModel testCaseDAOInputModel = AutomationGraphRequestDTOToTestCaseDAOInputModel
                        .convertAutomationGraphDTOToTestInputModel(automationGraphRequestDTO);
        List<TestCaseDAOOutputModel> testCaselist = automationReportingRepository
                        .getInputExcelNameTotalExecutedTestCase(testCaseDAOInputModel.getStartlocalDate(),
                                        testCaseDAOInputModel.getEndlocalDate(),
                                        testCaseDAOInputModel.getInputexlname());
        return logger.traceExit(testCaselist);
    }

    /**
     * This method takes start Date and End Date from {@link AutomationGraphRequestDTO} and return the list of total
     * Last Executed Test cases
     * 
     * @param automationGraphRequestDTO of {@link AutomationGraphRequestDTO}
     * @return list of total last Executed Test Cases
     * @since Dec 16, 2019
     */
    public List<TestCaseDAOOutputModel> getExecutedLastTestCaseList(
                    AutomationGraphRequestDTO automationGraphRequestDTO) {
        logger.traceEntry("getExecutedLastTestCaseList method of AutomationReportService class");
        TestCaseDAOInputModel testCaseDAOInputModel = AutomationGraphRequestDTOToTestCaseDAOInputModel
                        .convertAutomationGraphDTOToTestInputModel(automationGraphRequestDTO);
        List<TestCaseDAOOutputModel> testCaselist = automationReportingRepository
                        .getInputExcelNameTotalLastExecutedTestCase(testCaseDAOInputModel.getStartlocalDate(),
                                        testCaseDAOInputModel.getEndlocalDate(),
                                        testCaseDAOInputModel.getInputexlname());
        return logger.traceExit(testCaselist);
    }

    /**
     * This method is for getting the last Executed test cases count
     * 
     * @param inputExcel - Input Excel File Name
     * @param inputSheet - Input Excel Sheet Name
     * @return last Test Case Executed Count
     * @since Dec 11, 2019
     */
    public Integer getLastTestCaseExecuted(String inputExcel, String inputSheet) {
        logger.traceEntry("getLastTestCaseExecuted method of AutomationReportService class");
        String lastTestCaseExecutedCount =
                        automationReportingRepository.getLastTestCasesExecuted(inputExcel, inputSheet);
        return logger.traceExit(lastTestCaseExecutedCount != null ? Integer.parseInt(lastTestCaseExecutedCount)
                        : new Integer(0));
    }

    /**
     * This method is to get all information for a sprint in a list
     * 
     * @return listDto of {@link AutomationSprintDTO}
     * @since Dec 11, 2019
     */
    public List<AutomationSprintDTO> getAllSprintInfo() {
        logger.traceEntry("getAllSprintInfo method of AutomationReportService class");
        List<AutomationSprintInfoModel> list = automationSprintRepository.findAll();
        List<AutomationSprintDTO> listDto = AutomationSprintInfoModelToAutomationSprintDTO
                        .convertAutomationSprintInfoModelToSprintDto(list);

        return logger.traceExit(listDto);
    }


}
