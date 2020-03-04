package com.infogain.automation.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationRandomIntegerGeneratorDTO;

public class AutomationRandomIntegerGeneratorDTOValidatorImpl implements
                ConstraintValidator<AutomationRandomIntegerGeneratorDTOValidator, AutomationRandomIntegerGeneratorDTO> {


    private static final Logger logger = LogManager.getLogger(AutomationRandomIntegerGeneratorDTOValidatorImpl.class);


    @Override
    public boolean isValid(AutomationRandomIntegerGeneratorDTO value, ConstraintValidatorContext context) {
      return true;
}
}