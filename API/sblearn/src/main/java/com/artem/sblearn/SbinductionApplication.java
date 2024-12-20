package com.artem.sblearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SbinductionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbinductionApplication.class, args);
	}

}
