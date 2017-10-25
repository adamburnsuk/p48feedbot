package com.walmart.qe.mobilebot;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

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

/**
 * The purpose of this application is to manage the devices in the device lab.  At first
 * the application will only manage the hardware devices, but over time it will expand to 
 * manage dynamic emulators/simulators and VDIs for automated testing.
 * 
 * @author a2burns
 *
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

	/**
	 * Starts the springboot application.  Also starts MongoDB.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException  {
		
		//Start up mongoDB before doing anything else
		Runtime runTime = Runtime.getRuntime();
		Process process = runTime.exec("mongod");
		
		System.out.println(process.toString());
		
		//Start springboot application
		SpringApplication.run(Application.class, args);
			
	}
	
	/**
	 * This method initializes storage service for uploading and working with files.  It
	 * also deletes all old files.
	 * 
	 * @param storageService storageservice object
	 * @return
	 */
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
	
	/**
	 * This method starts ADB on the host machine.
	 * 
	 * @return returns a commandlinerunner object 
	 * @throws IOException
	 */
	@Bean
	CommandLineRunner startADB() throws IOException{
		
		return (args) -> {
			
			Runtime runTime = Runtime.getRuntime();
			Process process = runTime.exec("adb start-server");
			System.out.println(process.toString());
			
		};
	}

	/**
	 * This method creates Swagger documentation.
	 * 
	 * @return returns a Docket object containing path data for Swagger
	 */
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.regex("/svc.*"))
		.build()
		.apiInfo(metaData());
	}

	/**
	 * This method adds metadata to the Swagger api documentation.
	 * 
	 * @return returns ApiInfo object containing metadata for the Swagger documentation
	 */
	private ApiInfo metaData() {
		return new ApiInfoBuilder()
        .title("STE - Device Cloud")
        .description("This application is used to manage the devices in the mobile device cloud for WalmartLabs STE team.")
        .contact(new Contact("Adam Burns", "http://wmlink/qeauto","adam.burns@walmartlabs.com"))
        .version("1.0")
        .build();
	}
	
}
