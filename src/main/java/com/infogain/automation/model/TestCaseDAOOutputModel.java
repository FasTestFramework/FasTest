package com.infogain.automation.model;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is the model class for getting Test Case data for
 * Report generation
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
@ApiModel(value = "TestCaseDAOOutputModel", description = "Response body for test case output model for report generation contains InputExcelName, date and testCount")
public class TestCaseDAOOutputModel {

	@ApiModelProperty(value = "The Input Excel name", example = "Peripheral Server Test.xls")
	private String inputExcelName;
	@ApiModelProperty(value = "Date of testing", example = "2020-02-15")
	private LocalDate date;
	@ApiModelProperty(value = "Number of test case", example = "5")
	private long testCaseCount;

	public TestCaseDAOOutputModel(String inputExcelName, LocalDate date, long testCaseCount) {
		this.inputExcelName = inputExcelName;
		this.date = date;
		this.testCaseCount = testCaseCount;
	}

	/**
	 * @return the inputExcelName
	 */
	public String getInputExcelName() {
		return inputExcelName;
	}

	/**
	 * @param inputExcelName the inputExcelName to set
	 */
	public void setInputExcelName(String inputExcelName) {
		this.inputExcelName = inputExcelName;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return the testCaseCount
	 */
	public long getTestCaseCount() {
		return testCaseCount;
	}

	/**
	 * @param testCaseCount the testCaseCount to set
	 */
	public void setTestCaseCount(long testCaseCount) {
		this.testCaseCount = testCaseCount;
	}

	@Override
	public String toString() {
		return "TestCaseDAOOutputModel [inputExcelName=" + inputExcelName + ", date=" + date + ", testCaseCount="
				+ testCaseCount + "]";
	}

}
