package com.infogain.automation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class AutomationSwaggerConfig {

    @Bean
    public Docket deviceIntegrationApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("FasTest")
                        .apiInfo(apiInfo()).select()
                        .apis(RequestHandlerSelectors.basePackage("com.infogain.automation.controller"))
                        .paths(regex("/.+")).build();
    }
    
    public ApiInfo apiInfo() {

        return new ApiInfoBuilder().title("FasTest Automation")
                        .description("Testing Framework")
                        .contact(new Contact("Infogain", "", "www.infogain.com")).version("0.1").build();

    }
    
}
