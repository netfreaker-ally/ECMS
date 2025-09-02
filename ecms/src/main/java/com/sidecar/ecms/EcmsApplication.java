package com.sidecar.ecms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
public class EcmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcmsApplication.class, args);
	}

}
