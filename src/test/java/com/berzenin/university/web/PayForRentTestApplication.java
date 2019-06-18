package com.berzenin.university.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class PayForRentTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayForRentTestApplication.class, args);
	}
}
