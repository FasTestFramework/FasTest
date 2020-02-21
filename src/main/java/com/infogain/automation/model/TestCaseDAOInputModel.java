package com.infogain.automation.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is Test Case Input Model which contains Local start and End Date with Excel Names
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 16, 2019
 */
public class TestCaseDAOInputModel {

    private LocalDate startlocalDate;
    private LocalDate endlocalDate;
    private Set<String> inputexlname = new HashSet<>();

    public TestCaseDAOInputModel(LocalDate startlocalDate, LocalDate endlocalDate, Set<String> inputexlname) {
        this.startlocalDate = startlocalDate;
        this.endlocalDate = endlocalDate;
        this.inputexlname = inputexlname;
    }

    /**
     * @return the startlocalDate
     */
    public LocalDate getStartlocalDate() {
        return startlocalDate;
    }

    /**
     * @param startlocalDate the startlocalDate to set
     */
    public void setStartlocalDate(LocalDate startlocalDate) {
        this.startlocalDate = startlocalDate;
    }

    /**
     * @return the endlocalDate
     */
    public LocalDate getEndlocalDate() {
        return endlocalDate;
    }

    /**
     * @param endlocalDate the endlocalDate to set
     */
    public void setEndlocalDate(LocalDate endlocalDate) {
        this.endlocalDate = endlocalDate;
    }

    /**
     * @return the inputexlname
     */
    public Set<String> getInputexlname() {
        return inputexlname;
    }

    /**
     * @param inputexlname the inputexlname to set
     */
    public void setInputexlname(Set<String> inputexlname) {
        this.inputexlname = inputexlname;
    }



}
