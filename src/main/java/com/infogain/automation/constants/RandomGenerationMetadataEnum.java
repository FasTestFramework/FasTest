package com.infogain.automation.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.infogain.automation.dto.AutomationKeyMetadataDTO;
import com.infogain.automation.dto.Pair;
import com.infogain.automation.dto.AutomationRandomGenerationMetadataDTO;

public enum RandomGenerationMetadataEnum {
    ALPHANUMERIC("AlphaNumeric", InputType.TEXT, JavaDataType.STRING, RandomGenerationUrlsEnum.ALPHANUMERIC_STRING.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.EXCLUSIONS, this.getType());
            automationKeyMetadataDTO.setPattern("(.,)*.");
            automationKeyMetadataDTO.setRequired(true);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.ALPHANUMERIC_COMMA_SEPERATED);
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.LENGTH, InputType.NUMBER);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.STRING_LENGTH);
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return new ArrayList<>();
        }
    },
    DECIMAL("Decimal", InputType.DECIMAL, JavaDataType.DOUBLE, RandomGenerationUrlsEnum.DOUBLE_VALUE_AS_STRING.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.MIN, this.getType());
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.RANDOM_DECIMAL_MIN);
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.MAX, this.getType());
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.RANDOM_DECIMAL_MAX);
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.PRECISION, InputType.NUMBER);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.DECIMAL_PRECISION_LENGTH);
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList(AutomationRandomGenerationConstants.MIN, AutomationRandomGenerationConstants.MAX), Arrays.asList(AutomationRandomGenerationConstants.MIN, AutomationRandomGenerationConstants.MAX, AutomationRandomGenerationConstants.PRECISION),
                            Arrays.asList(AutomationRandomGenerationConstants.PRECISION), new ArrayList<>());
        }
    },
    UPPERCASELETTERS("Upper Case Letters", InputType.TEXT, JavaDataType.STRING, RandomGenerationUrlsEnum.CAPITAL_LETTERS_STRING.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.LENGTH, this.getType());
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.CAPITAL_STRING_LENGTH);
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.START_CHARACTER, InputType.TEXT);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.CAPITAL_STRING_MAX);
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.END_CHARACTER, InputType.TEXT);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.CAPITAL_PRECISION_LENGTH);
            keyMetadataList.add(automationKeyMetadataDTO);
            
            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.EXCLUSIONS, this.getType());
            automationKeyMetadataDTO.setPattern("(.,)*.");
            automationKeyMetadataDTO.setRequired(true);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.CAPITAL_COMMA_SEPERATED);
            keyMetadataList.add(automationKeyMetadataDTO);
            
            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.INCLUSIONS, this.getType());
            automationKeyMetadataDTO.setPattern("(.,)*.");
            automationKeyMetadataDTO.setRequired(true);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.CAPITAL_COMMA_SEPERATED);
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    RANDOM("Random", InputType.DECIMAL, JavaDataType.DOUBLE, RandomGenerationUrlsEnum.INSTRUCTIONS_TO_GENERATE_RANDOM_DATA.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO("min", this.getType());
            automationKeyMetadataDTO.setDescription("min value of decimal. For ex: - 1.35, 76.76");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("max", this.getType());
            automationKeyMetadataDTO.setDescription("max value of decimal. For ex: - 12, 100.3455");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("precision", InputType.NUMBER);
            automationKeyMetadataDTO.setDescription("precision length of decimal. For ex: - 12, 1");
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    INTEGER("Integer", InputType.NUMBER, JavaDataType.INTEGER, RandomGenerationUrlsEnum.INTEGER_VALUE_STRING.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO("min", this.getType());
            automationKeyMetadataDTO.setDescription("min value of decimal. For ex: - 1.35, 76.76");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("max", this.getType());
            automationKeyMetadataDTO.setDescription("max value of decimal. For ex: - 12, 100.3455");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("precision", InputType.NUMBER);
            automationKeyMetadataDTO.setDescription("precision length of decimal. For ex: - 12, 1");
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    SENTENCE("Sentence", InputType.TEXT, JavaDataType.STRING, RandomGenerationUrlsEnum.RANDOM_SENTENCE.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO("min", this.getType());
            automationKeyMetadataDTO.setDescription("min value of decimal. For ex: - 1.35, 76.76");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("max", this.getType());
            automationKeyMetadataDTO.setDescription("max value of decimal. For ex: - 12, 100.3455");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("precision", InputType.NUMBER);
            automationKeyMetadataDTO.setDescription("precision length of decimal. For ex: - 12, 1");
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    CUSTOM("Custom", InputType.TEXT, JavaDataType.STRING, RandomGenerationUrlsEnum.ANY_STRING.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO("min", this.getType());
            automationKeyMetadataDTO.setDescription("min value of decimal. For ex: - 1.35, 76.76");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("max", this.getType());
            automationKeyMetadataDTO.setDescription("max value of decimal. For ex: - 12, 100.3455");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("precision", InputType.NUMBER);
            automationKeyMetadataDTO.setDescription("precision length of decimal. For ex: - 12, 1");
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    MIXLETTERS("Mix Letters", InputType.TEXT, JavaDataType.STRING, RandomGenerationUrlsEnum.SMALL_CAPITAL_LETTERS_MIX_STRING.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO("min", this.getType());
            automationKeyMetadataDTO.setDescription("min value of decimal. For ex: - 1.35, 76.76");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("max", this.getType());
            automationKeyMetadataDTO.setDescription("max value of decimal. For ex: - 12, 100.3455");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("precision", InputType.NUMBER);
            automationKeyMetadataDTO.setDescription("precision length of decimal. For ex: - 12, 1");
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    LOWERCASELETTERS("Lower Case Letters", InputType.TEXT, JavaDataType.STRING, RandomGenerationUrlsEnum.SMALL_LETTERS_STRING.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
        	List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.LENGTH, this.getType());
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.CAPITAL_STRING_LENGTH);
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.START_CHARACTER, InputType.TEXT);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.CAPITAL_STRING_MAX);
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.END_CHARACTER, InputType.TEXT);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.CAPITAL_PRECISION_LENGTH);
            keyMetadataList.add(automationKeyMetadataDTO);
            
            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.EXCLUSIONS, this.getType());
            automationKeyMetadataDTO.setPattern("(.,)*.");
            automationKeyMetadataDTO.setRequired(true);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.SMALL_COMMA_SEPERATED);
            keyMetadataList.add(automationKeyMetadataDTO);
            
            automationKeyMetadataDTO = new AutomationKeyMetadataDTO(AutomationRandomGenerationConstants.INCLUSIONS, this.getType());
            automationKeyMetadataDTO.setPattern("(.,)*.");
            automationKeyMetadataDTO.setRequired(true);
            automationKeyMetadataDTO.setDescription(AutomationRandomGenerationConstants.SMALL_COMMA_SEPERATED);
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    SPECIALCHARACTERS("Special Characters", InputType.TEXT, JavaDataType.STRING, RandomGenerationUrlsEnum.SPECIAL_CHARACTER_STRING.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO("min", this.getType());
            automationKeyMetadataDTO.setDescription("min value of decimal. For ex: - 1.35, 76.76");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("max", this.getType());
            automationKeyMetadataDTO.setDescription("max value of decimal. For ex: - 12, 100.3455");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("precision", InputType.NUMBER);
            automationKeyMetadataDTO.setDescription("precision length of decimal. For ex: - 12, 1");
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    STRINGOFNUMBERS("String of Numbers", InputType.NUMBER, JavaDataType.INTEGER, RandomGenerationUrlsEnum.STRING_OF_NUMBERS.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            List<AutomationKeyMetadataDTO> keyMetadataList = new ArrayList<>();
            AutomationKeyMetadataDTO automationKeyMetadataDTO = new AutomationKeyMetadataDTO("min", this.getType());
            automationKeyMetadataDTO.setDescription("min value of decimal. For ex: - 1.35, 76.76");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("max", this.getType());
            automationKeyMetadataDTO.setDescription("max value of decimal. For ex: - 12, 100.3455");
            keyMetadataList.add(automationKeyMetadataDTO);

            automationKeyMetadataDTO = new AutomationKeyMetadataDTO("precision", InputType.NUMBER);
            automationKeyMetadataDTO.setDescription("precision length of decimal. For ex: - 12, 1");
            keyMetadataList.add(automationKeyMetadataDTO);
            return keyMetadataList;
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return Arrays.asList(Arrays.asList("min", "max"), Arrays.asList("min", "max", "precision"),
                            Arrays.asList("precision"), new ArrayList<>());
        }
    },
    UUID("UUID", InputType.TEXT, JavaDataType.STRING, RandomGenerationUrlsEnum.UUID.getUrl()) {
        @Override
        protected List<AutomationKeyMetadataDTO> getKeys() {
            return new ArrayList<>();
        }

        @Override
        protected List<List<String>> getRequiredSet() {
            return new ArrayList<>();
        }
    };
	
	

    private String name;
    private InputType type;
    private JavaDataType dataType;
    private String url;

    protected abstract List<AutomationKeyMetadataDTO> getKeys();

    protected abstract List<List<String>> getRequiredSet();

    public Pair<String, AutomationRandomGenerationMetadataDTO> getData() {
        return Pair.of(this.getName(), new AutomationRandomGenerationMetadataDTO(this.getType(), this.getDataType(), this.getUrl(),
                        this.getRequiredSet(), this.getKeys()));
    }

    private RandomGenerationMetadataEnum(String name, InputType type, JavaDataType dataType, String url) {
        this.name = name;
        this.type = type;
        this.dataType = dataType;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public InputType getType() {
        return type;
    }

    public JavaDataType getDataType() {
        return dataType;
    }


    public String getUrl() {
        return url;
    }


}
