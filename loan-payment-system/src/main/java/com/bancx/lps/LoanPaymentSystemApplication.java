package com.bancx.lps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication
public class LoanPaymentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanPaymentSystemApplication.class, args);
	}

}
