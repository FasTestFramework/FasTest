package com.infogain.automation.constants;

public enum RandomGenerationUrlsEnum {
	SPECIAL_CHARACTER_STRING ("/generateRandomData/specialCharacterString"),
	INTEGER_VALUE_STRING ("/generateRandomData/integerValueAsString"),
	CAPITAL_LETTERS_STRING ("/generateRandomData/capitalLettersString"),
	SMALL_LETTERS_STRING ("/generateRandomData/smallLettersString"),
	STRING_OF_NUMBERS ("/generateRandomData/StringofNumbers"),
	SMALL_CAPITAL_LETTERS_MIX_STRING ("/generateRandomData/SmallCapitalLettersMixString"),
	ANY_STRING ("/generateRandomData/anyString"),
	ALPHANUMERIC_STRING ("/generateRandomData/alphanumericString"),
	DOUBLE_VALUE_AS_STRING ("/generateRandomData/doubleValueAsString"),
	RANDOM_SENTENCE ("/generateRandomData/randomSentence"),
	INSTRUCTIONS_TO_GENERATE_RANDOM_DATA ("/generateRandomData/instructionsToGenerateRandomData"),
	UUID ("/generateRandomData/uuid");

	private String url;

	RandomGenerationUrlsEnum(String url){
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

};
