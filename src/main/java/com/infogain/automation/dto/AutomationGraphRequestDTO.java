package com.infogain.automation.dto;

import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is Peripheral Graph Request DTO which contains start
 * Date and End Date for creating graph
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */

@ApiModel(value = "AutomationGraphRequestDTO", description = "Request body for graph request which contains start Date and End Date")
public class AutomationGraphRequestDTO {

	@ApiModelProperty(value = "Name of the sheet", required = true, example = "Peripheral Server Test.xlsx")
	private String sheets;
	@ApiModelProperty(value = "Start Date", required = true, example = "2020-01-03")
	private String startDate;
	@ApiModelProperty(value = "End Date", required = true, example = "2020-02-20")
	private String endDate;

	public AutomationGraphRequestDTO(String sheets, String startDate, String endDate) {
		this.sheets = sheets;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public AutomationGraphRequestDTO() {
	}

	/**
	 * @return the testInputFiles
	 */
	public String getSheets() {
		return sheets;
	}

	/**
	 * @param testInputFiles the testInputFiles to set
	 */
	public void setSheets(String sheets) {
		this.sheets = sheets;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * This method returns a string representation of
	 * {@link AutomationGraphRequestDTO}
	 * 
	 * @return string representation of {@link AutomationGraphRequestDTO}
	 * @since 02-Dec-2019
	 */
	@Override
	public String toString() {
		return "AutomationGraphRequestDTO [testInputFiles=" + sheets + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}

	/**
	 * This method generates hash code for {@link AutomationGraphRequestDTO}
	 * 
	 * @return hash code for {@link AutomationGraphRequestDTO}
	 * @since 02-Dec-2019
	 */
	@Override
	public int hashCode() {
		return Objects.hash(endDate, sheets, startDate);
	}

	/**
	 * This method checks for equality of two objects of type
	 * {@link AutomationGraphRequestDTO}
	 * 
	 * @param obj {@link AutomationGraphRequestDTO} instance
	 * @return {@code true} both objects are equal<br>
	 *         {@code false} both objects are not equal
	 * @since 02-Dec-2019
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AutomationGraphRequestDTO)) {
			return false;
		}
		AutomationGraphRequestDTO other = (AutomationGraphRequestDTO) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(sheets, other.sheets)
				&& Objects.equals(startDate, other.startDate);
	}

}
