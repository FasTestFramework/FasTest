package com.infogain.automation.constants;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class contains all the application constants
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
public final class AutomationRandomGenerationConstants {

    private AutomationRandomGenerationConstants() {
        throw new UnsupportedOperationException(AutomationRandomGenerationConstants.class.getName());
    }
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String CUSTOM_EXPRESSION = "Custom Expression";
    public static final String LENGTH = "length";
    public static final String EXCLUSIONS = "exclusions";
    public static final String INCLUSIONS = "inclusions";
    public static final String PRECISION = "precision";
    public static final String START_CHARACTER = "startCharacter";
    public static final String END_CHARACTER = "endCharacter";
    
    public static final String STRING_LENGTH_DESCRIPTION = "length of string. <br>For ex: - <br>12<br>1";
    public static final String RANDOM_DECIMAL_MAX_DESCRIPTION = "Max value of decimal. <br>For ex: - <br>12<br>100.3455";
    public static final String RANDOM_DECIMAL_MIN_DESCRIPTION = "Min value of decimal. <br>For ex:- <br>1.35<br>76.76";
    public static final String INTEGER_MAX_DESCRIPTION = "Max value of decimal. <br>For ex: - <br>12<br>455";
    public static final String INTEGER_MIN_DESCRIPTION = "Min value of decimal. <br>For ex: - <br>11<br>76";
    public static final String CAPITAL_STRING_MIN_DESCRIPTION = "Min value of capital string. <br>For ex: - <br>C<br>I";
    public static final String CAPITAL_STRING_MAX_DESCRIPTION = "Max value of capital string. <br>For ex: - <br>C<br>R";
    public static final String SMALL_STRING_MIN_DESCRIPTION = "Min value of capital string. <br>For ex: - <br>C<br>H";
    public static final String SMALL_STRING_MAX_DESCRIPTION = "Max value of capital string. <br>For ex: - <br>X<br>R";
    public static final String CAPITAL_PRECISION_LENGTH_DESCRIPTION = "Precision length of capital string. <br>For ex: - <br>12<br>1";
    public static final String CAPITAL_END_CHARACTER_DESCRIPTION_DESCRIPTION = "Capital letters string start character. <br>For ex: - <br>A<br>D;";
    public static final String CAPITAL_START_CHARACTER_DESCRIPTION = "Capital letters string end character. <br>For ex: - <br>S<br>R";
    public static final String SMALLL_END_CHARACTER_DESCRIPTION = "Small letters string start character. <br>For ex: - <br>a<br>d";
    public static final String SMALL_START_CHARACTER_DESCRIPTION = "Small letters string end character. <br>For ex: - <br>s<br>r";
    public static final String CAPITAL_COMMA_SEPERATED_DESCRIPTION = "UpperCase letters characters seperated by comma(,). <br>For ex: - <br>A,G,T<br>K,F,Q";
    public static final String SMALL_COMMA_SEPERATED_DESCRIPTION = "Lowercase letters characters seperated by comma(,). <br>For ex: - <br>a,g,j,m<br>k,f,q";
    public static final String ALPHANUMERIC_COMMA_SEPERATED_DESCRIPTION = "Alphanumeric characters seperated by comma(,). <br>For ex: - <br>A,1,2,3<br>a,A,1";
    public static final String ALL_STRING_COMMA_SEPERATED_DESCRIPTION = "Any character seperated by comma(,). <br>For ex: - <br>A,1,@,d<br>a,A,h,8,&";
    public static final String SPECIAL_CHAR_COMMA_SEPERATED_DESCRIPTION = "Special characters seperated by comma(,). <br>For ex: - <br>#,@,><br>*,%,&";
    public static final String SMALL_CAPITAL_MIX_COMMA_SEPERATED_DESCRIPTION = "Any small or capital character seperated by comma(,). <br>For ex: - <br>A,a,u,d<br>a,A,h,y,v";
    public static final String INTEGER_COMMA_SEPERATED_DESCRIPTION = "Any number seperated by comma(,). <br>For ex: -<br>4,25,-8<br>20,-45,56";
    public static final String INSTRUCTION_TO_GENERATE_RANDOM_DATA_STRING_DESCRIPTION = "Instruction to generate random data. <br>For ex: - <br>W(claimId: ){1}C(){5}";
    public static final String DECIMAL_PRECISION_LENGTH_DESCRIPTION = "precision length of decimal. <br>For ex: - <br>12<br>1";

}
