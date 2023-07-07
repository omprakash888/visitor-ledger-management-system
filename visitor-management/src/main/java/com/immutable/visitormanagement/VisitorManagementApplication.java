package com.immutable.visitormanagement;

import com.immutable.visitormanagement.configuration.OpenAPIConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = "com.immutable.visitormanagement")
@EnableAsync
@Import(OpenAPIConfiguration.class)
public class VisitorManagementApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(VisitorManagementApplication.class, args);
	}

}
