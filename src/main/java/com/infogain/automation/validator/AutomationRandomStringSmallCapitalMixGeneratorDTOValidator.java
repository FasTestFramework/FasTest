package com.infogain.automation.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = AutomationRandomStringSmallCapitalMixGeneratorDTOValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutomationRandomStringSmallCapitalMixGeneratorDTOValidator {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
