package com.infogain.automation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infogain.automation.model.AutomationId;
import com.infogain.automation.model.AutomationOutputModel;
import com.infogain.automation.model.TestCaseDAOOutputModel;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * 
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Automation and Testing<br>
 * Description - This interface is a repository which contains all queries for graph data
 * 
 * @author Rudhra Koul [5173824]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
@Repository
public interface AutomationReportingRepository extends CrudRepository<AutomationOutputModel, AutomationId> {

    /**
     * This method returns distinct Input Excel File Names
     * 
     * @return list of distinct Input Excel File Names
     * @since Dec 13, 2019
     */
    @Query("SELECT DISTINCT a.pai.inputExcelName FROM AutomationOutputModel a")
    public List<String> findAllDistinctpaiInputExcelName();

    /**
     * This method is to get passed test cases count between given dates for Input Excel Files
     * 
     * @param startDate - date from which we want to get passed test cases count for a excel Files
     * @param endDate - date till which we want to get passed test cases count for a excel Files
     * @param inputexcelname - file names for which we need to find written test cases count
     * @return list of {@link TestCaseDAOOutputModel}
     * @since Dec 16, 2019
     */
    @Query("SELECT new com.infogain.automation.model.TestCaseDAOOutputModel(a.pai.inputExcelName, a.executionDate, sum(a.totalPassTestCases) as testCaseCount)"
                    + " FROM AutomationOutputModel a " + "WHERE a.pai.executionDataTime IN ("
                    + " SELECT  MAX(b.pai.executionDataTime) FROM AutomationOutputModel b"
                    + " WHERE b.executionDate BETWEEN :startDate AND :endDate AND b.pai.inputExcelName IN :inputexcelname"
                    + " GROUP BY b.pai.inputSheetName, b.executionDate, b.pai.inputExcelName )"
                    + " GROUP BY a.pai.inputExcelName, a.executionDate")
    public List<TestCaseDAOOutputModel> getInputExcelNameTotalPassTestCase(@Param("startDate") LocalDate startDate,
                    @Param("endDate") LocalDate endDate, @Param("inputexcelname") Set<String> inputexcelname);

    /**
     * This method is to get failed test cases count between given dates for Input Excel Files
     * 
     * @param startDate - date from which we want to get failed test cases count for a excel Files
     * @param endDate - date till which we want to get failed test cases count for a excel Files
     * @param inputexcelname - file names for which we need to find written test cases count
     * @return list of {@link TestCaseDAOOutputModel}
     * @since Dec 16, 2019
     */
    @Query("SELECT new com.infogain.automation.model.TestCaseDAOOutputModel(a.pai.inputExcelName, a.executionDate, sum(a.totalFailedTestCases) as testCaseCount)"
                    + " FROM AutomationOutputModel a " + "WHERE a.pai.executionDataTime IN ("
                    + " SELECT  MAX(b.pai.executionDataTime) FROM AutomationOutputModel b"
                    + " WHERE b.executionDate BETWEEN :startDate AND :endDate AND b.pai.inputExcelName IN :inputexcelname"
                    + " GROUP BY b.pai.inputSheetName, b.executionDate, b.pai.inputExcelName )"
                    + " GROUP BY a.pai.inputExcelName, a.executionDate")
    public List<TestCaseDAOOutputModel> getInputExcelNameTotalFailedTestCase(@Param("startDate") LocalDate startDate,
                    @Param("endDate") LocalDate endDate, @Param("inputexcelname") Set<String> inputexcelname);

    /**
     * This method is to get total Executed test cases between given dates for Input Excel Files
     * 
     * @param startDate - date from which we want to get total Executed test cases for a excel Files
     * @param endDate - date till which we want to get total Executed test cases for a excel Files
     * @param inputexcelname - file names for which we need to find Executed test cases
     * @return list of {@link TestCaseDAOOutputModel}
     * @since Dec 13, 2019
     */
    @Query("SELECT new com.infogain.automation.model.TestCaseDAOOutputModel(a.pai.inputExcelName,a.executionDate,sum(a.totalExecutedTestCases) as testCaseCount) "
                    + "FROM AutomationOutputModel a GROUP BY a.pai.inputExcelName,a.executionDate"
                    + " HAVING a.executionDate BETWEEN :startDate AND :endDate AND"
                    + " a.pai.inputExcelName IN :inputexcelname")
    public List<TestCaseDAOOutputModel> getInputExcelNameTotalExecutedTestCase(@Param("startDate") LocalDate startDate,
                    @Param("endDate") LocalDate endDate, @Param("inputexcelname") Set<String> inputexcelname);

    /**
     * This method is to get total written test cases count between given dates for Input Excel Files
     * 
     * @param startDate - date from which we want to get total written test cases count for a excel Files
     * @param endDate - date till which we want to get total written test cases count for a excel Files
     * @param inputexcelname - file names for which we need to find written test cases count
     * @return list of {@link TestCaseDAOOutputModel}
     * @since Dec 16, 2019
     */
    @Query("SELECT new com.infogain.automation.model.TestCaseDAOOutputModel(a.pai.inputExcelName, a.executionDate, sum(a.totalExecutedTestCases) as testCaseCount)"
                    + " FROM AutomationOutputModel a " + "WHERE a.pai.executionDataTime IN ("
                    + " SELECT  MAX(b.pai.executionDataTime) FROM AutomationOutputModel b"
                    + " WHERE b.executionDate BETWEEN :startDate AND :endDate AND b.pai.inputExcelName IN :inputexcelname"
                    + " GROUP BY b.pai.inputSheetName, b.executionDate, b.pai.inputExcelName )"
                    + " GROUP BY a.pai.inputExcelName, a.executionDate")
    public List<TestCaseDAOOutputModel> getInputExcelNameTotalLastExecutedTestCase(
                    @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                    @Param("inputexcelname") Set<String> inputexcelname);

    /**
     * This method is to get last Executed test cases for each excel sheet in a Excel File
     * 
     * @param inputExcel file name for which we need to find Last Executed test cases
     * @param inputSheet Excel sheet of a Excel File
     * @return list of Excel name with total executed test cases
     * @since Dec 13, 2019
     */
    @Query("SELECT p.totalExecutedTestCases as count from AutomationOutputModel p where p.pai.inputExcelName=:inputExcel and p.pai.inputSheetName"
                    + "=:inputSheet and p.pai.executionDataTime=(SELECT max(pe.pai.executionDataTime) from AutomationOutputModel pe where pe.pai.inputExcelName=:inputExcel"
                    + " and pe.pai.inputSheetName=:inputSheet)")
    public String getLastTestCasesExecuted(@Param("inputExcel") String inputExcel,
                    @Param("inputSheet") String inputSheet);

}
