package com.example.dairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DairyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DairyApplication.class, args);
		System.out.println("The server has started");
	}

}
