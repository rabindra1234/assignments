package com.vmware.interview;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class NumberGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberGeneratorApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.vmware.interview.controller"))
				.paths(PathSelectors.regex("/api.*"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails(){
		return new ApiInfo("Number Generate",
				"The purpose of this Module is to generate number as per the provided goal and step.",
				"1.0","Info",
				new springfox.documentation.service.Contact("Number Generate Service", "NA", "NA" ),
				"NA",
				"NA",
				new ArrayList<>());
	}

}
