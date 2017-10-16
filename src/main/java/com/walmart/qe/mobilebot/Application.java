package com.walmart.qe.mobilebot;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.walmart.qe.mobilebot.properties.StorageProperties;
import com.walmart.qe.mobilebot.service.StorageService;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

	public static void main(String[] args) throws IOException  {
		
		//Start up mongoDB before doing anything else
		Runtime runTime = Runtime.getRuntime();
		Process process = runTime.exec("mongod");
		
		System.out.println(process.toString());
		
		//Start springboot application
		SpringApplication.run(Application.class, args);
			
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
	
	@Bean
	CommandLineRunner startADB() throws IOException{
		
		return (args) -> {
			
			Runtime runTime = Runtime.getRuntime();
			Process process = runTime.exec("adb start-server");
			System.out.println(process.toString());
			
		};
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.regex("/svc.*"))
		.build()
		.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
        .title("STE - Device Cloud")
        .description("This application is used to manage the devices in the mobile device cloud for WalmartLabs STE team.")
        .contact(new Contact("Adam Burns", "http://wmlink/qeauto","adam.burns@walmartlabs.com"))
        .version("1.0")
        .build();
	}
	
}
