package com.sidecar.ecms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import com.sidecar.ecms.core.config.UserNamePasswordAuthProperties;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(UserNamePasswordAuthProperties.class)

public class EcmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcmsApplication.class, args);
	}

}