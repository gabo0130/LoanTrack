package com.LoanTrack.Backend.infrastructure.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(scanBasePackages = { "com.LoanTrack.Backend" }, exclude = DataSourceAutoConfiguration.class)
@OpenAPIDefinition(info = @Info(title = "Report API", version = "1.0", description = "Report API Process"))
public class BackendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApiApplication.class, args);
	}

}
