package com.infogain.automation.errors;

import java.io.Serializable;

/**
 * Copyright (c) 2019 FedEx. All Rights Reserved.<br>
 * <br>
 * Theme - Core Retail Peripheral Services<br>
 * Feature - Peripheral Services - Design and Architecture<br>
 * Description - This is an interface for error codes enum
 * 
 * @author Namit Jain [3696360]
 * @version 1.0.0
 * @since 24-Dec-2019
 */
public interface ErrorCodes extends Serializable {
    public String getCode();

    public String getMessage();
}
